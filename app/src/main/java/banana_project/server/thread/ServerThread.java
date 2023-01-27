package banana_project.server.thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

import banana_project.server.logic.ChatListLogic;
import banana_project.server.logic.ChatLogic;
import banana_project.server.vo.ChatContentsVO;
import banana_project.server.vo.ChatListVO;
import com.google.common.collect.ImmutableBiMap.Builder;

import banana_project.server.logic.FriendLogic;
import banana_project.server.logic.MemberLogic;
import banana_project.server.vo.UserVO;

public class ServerThread extends Thread {
  MemberLogic memberLogic = null;
  FriendLogic friendLogic = null;
  ChatListLogic chatListLogic = null;
  ChatLogic chatLogic = null;
  Server server = null;
  Socket client = null;
  ObjectOutputStream oos = null;
  ObjectInputStream ois = null;
  String userId = null;
  String userNick = null;
  String chatNo = null;

  /**
   * 생성자
   */
  public ServerThread() {
  }

  /**
   * 생성자-서버연결
   *
   * @param server
   */
  public ServerThread(Server server) {
    this.server = server;
    this.client = server.socket;
    try {
      memberLogic = new MemberLogic();
      friendLogic = new FriendLogic();
      chatLogic = new ChatLogic();
      oos = new ObjectOutputStream(client.getOutputStream()); // 말하기
      ois = new ObjectInputStream(client.getInputStream()); // 듣기
      // 현재 서버에 입장한 클라이언트 스레드 추가하기
      server.globalList.add(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 클라이언트에게 말하기 구현
   *
   * @param msg
   */
  public void send(String msg) {
    try {
      oos.writeObject(msg);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 단톡방에서 말하기 구현
   * 
   * 1. 일단 모두에게 보내고 서버스레드 전역변수 chatNo로 거르기
   *
   * @param msg
   */
  public void broadCasting(String chatNo, String sendNick, String sendMsg) {
    for (ServerThread serverThread : server.globalList) {
      serverThread.send(Protocol.SEND_MSG
          + Protocol.seperator + chatNo
          + Protocol.seperator + sendNick
          + Protocol.seperator + sendMsg);
    }
  }

  /**
   * 단톡방에서 말하기 구현
   * 
   * 2.채팅넘버와 서버스레드 같이 저장한다 가정
   * 
   * @param msg
   * @param roomTitle
   */
  public void roomCasting(String chatNo, String sendId, String sendMsg, List<Map<String, Object>> userList) {
    for (int i = 0; i < userList.size(); i++) {
      // 만약 채팅방넘버가 같다면 해당 서버스레드에 메시지 전송
      if (chatNo.equals(userList.get(i).get("chatNo"))) {
        ServerThread serverThread = (ServerThread) userList.get(i).get("ServerThread");
        try {
          // 프로토콜#채팅방번호#아이디#메시지
          serverThread.send(Protocol.SEND_MSG
              + Protocol.seperator + chatNo
              + Protocol.seperator + sendId
              + Protocol.seperator + sendMsg);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Thread 메소드
   */
  @Override
  public void run() {
    String msg = null;
    boolean isStop = false;
    try {
      while (!isStop) {
        msg = String.valueOf(ois.readObject()); // 클라이언트가 서버에게 전송한 메시지
        server.jta_log.append(msg + "\n");
        server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
        StringTokenizer st = null;
        int protocol = 0;
        if (msg != null) {
          st = new StringTokenizer(msg, "#");
          protocol = Integer.parseInt(st.nextToken());
        }
        server.jta_log.append("Protocol: " + protocol + "\n");
        switch (protocol) {
          /**
           * Client 스레드
           */
          // 클라이언트 시작 100#아이디#비밀번호
          case Protocol.CLIENT_START: {
            String userId = st.nextToken();
            String userPw = st.nextToken();
            // DB와 같은지 체크
            server.jta_log.append("로그인 DB 체크 시작" + "\n");
            Map<String, Object> resultMap = memberLogic
                .loginUser(UserVO.builder().user_id(userId).user_pw(userPw).build());
            int result = (Integer) resultMap.get("result");
            server.jta_log.append("Result: " + result + "\n");
            // 체크 결과 switch문
            switch (result) {
              // 로그인 성공 101 -> 아이디
              case Protocol.LOGIN_S: {
                UserVO resultVO = memberLogic.getUserInfo(UserVO.builder().user_id(userId).build());
                String userNick = resultVO.getUser_nickname();
                this.userId = userId;
                this.userNick = userNick;
                oos.writeObject(Protocol.LOGIN_S
                    + Protocol.seperator + userId
                    + Protocol.seperator + userNick);
              }
                break;
              // 비번 틀림 103
              case Protocol.WRONG_PW: {
                oos.writeObject(Protocol.WRONG_PW);
              }
                break;
              // 비번 시도횟수 초과 104
              case Protocol.OVER_FAIL_CNT: {
                oos.writeObject(Protocol.OVER_FAIL_CNT);
              }
                break;
              // 계정없음 102 -> 디폴트로설정
              default: {
                oos.writeObject(Protocol.WRONG_ID);
              }
            }
          }
            break;

          /**
           * MemJoin 스레드
           */
          // 아이디 중복확인 201#아이디
          case Protocol.MAIL_CHK: {
            String userId = st.nextToken();
            // DB와 같은지 체크
            server.jta_log.append(" 아이디중복 DB 체크 시작" + "\n");
            int result = memberLogic.checkDuplId(UserVO.builder().user_id(userId).build());
            server.jta_log.append("Result: " + result + "\n");
            // 체크 결과 switch문
            switch (result) {
              // 아이디 중복 아님 201
              case 1: {
                oos.writeObject(Protocol.MAIL_CHK);
              }
                break;
              // 아이디 중복됨 202
              case -1: {
                oos.writeObject(Protocol.EXIST_MAIL);
              }
                break;
            }
          }
            break;
          // 닉네임 중복확인 203#닉네임
          case Protocol.NICK_CHK: {
            String userNick = st.nextToken();
            // DB와 같은지 체크
            server.jta_log.append("닉네임 중복 DB 체크 시작" + "\n");
            int result = memberLogic.checkDuplNickname(UserVO.builder().user_nickname(userNick).build());
            server.jta_log.append("Result: " + result + "\n");
            // 체크 결과 switch문
            switch (result) {
              // 닉네임 중복 아님 203
              case 1: {
                oos.writeObject(Protocol.NICK_CHK);
              }
                break;
              // 닉네임 중복됨 204
              case -1: {
                oos.writeObject(Protocol.EXIST_NICK);
              }
                break;
            }
          }
            break;
          // 회원가입 시작 200#아이디#비밀번호#이름#핸드폰번호#닉네임
          case Protocol.SIGN_UP: {
            String userId = st.nextToken();
            String userPw = st.nextToken();
            String userName = st.nextToken();
            String userHp = st.nextToken();
            String userNick = st.nextToken();
            // DB등록 및 체크
            server.jta_log.append("회원가입 DB 체크 시작" + "\n");
            int result = memberLogic.joinUser(UserVO.builder().user_id(userId).user_pw(userPw).user_name(userName)
                .user_hp(userHp).user_nickname(userNick).build());
            server.jta_log.append("Result: " + result + "\n");
            switch (result) {
              // 계정(핸드폰) 존재 206
              case 0: {
                oos.writeObject(Protocol.EXIST_ACNT);
              }
                break;
              // 회원가입 성공 207 -> 이름
              case 1: {
                oos.writeObject(Protocol.SIGN_SUS
                    + Protocol.seperator + userName);
              }
                break;
              // 회원가입 실패 208
              case -1: {
                oos.writeObject(Protocol.SIGN_ERR);
              }
                break;
            }
          }
            break;

          /**
           * IdFind 스레드
           */
          // 아이디찾기 시작 300#이름#핸드폰번호
          case Protocol.FID_START: {
            String userName = st.nextToken();
            String userHp = st.nextToken();
            // DB등록 및 체크
            server.jta_log.append("아이디찾기 DB 체크 시작" + "\n");
            UserVO userVO = memberLogic.findUserId(UserVO.builder().user_name(userName).user_hp(userHp).build());
            // 아이디 존재 303
            if (userVO.getUser_id() != null) {
              server.jta_log.append("Result: " + Protocol.EXIST_FID + "\n");
              oos.writeObject(Protocol.EXIST_FID
                  + Protocol.seperator + userName
                  + Protocol.seperator + userVO.getUser_id());
            }
            // 아이디가 존재하지 않음 302
            else {
              server.jta_log.append("Result: " + Protocol.NF_FID + "\n");
              oos.writeObject(Protocol.NF_FID);
            }
          }
            break;

          /**
           * PwFind 스레드
           */
          // 비밀번호찾기 시작 400#이름#아이디#핸드폰번호
          case Protocol.FPW_START: {
            String userName = st.nextToken();
            String userId = st.nextToken();
            String userHp = st.nextToken();
            // DB등록 및 체크
            server.jta_log.append("비밀번호찾기 DB 체크 시작" + "\n");
            int result = memberLogic
                .findUserPW(UserVO.builder().user_name(userName).user_id(userId).user_hp(userHp).build());
            server.jta_log.append("Result: " + result + "\n");
            switch (result) {
              // 재설정 시작 403
              case Protocol.EXIST_FACNT: {
                // 계정이 존재함 프로토콜 반환
                oos.writeObject(Protocol.EXIST_FACNT
                    + Protocol.seperator + userId);
                break;
              }
              // 재설정 실패 402
              case Protocol.NF_FACNT: {
                // 계정 존재하지 않음 프로토콜 반환
                oos.writeObject(Protocol.NF_FACNT);
                break;
              }
            }
          }
            break;

          /**
           * PwFindDialog 스레드
           */
          // 비밀번호 재설정 404#아이디#새로운비번
          case Protocol.RESET_PW: {
            String userId = st.nextToken();
            String userPw = st.nextToken();
            String userNw = st.nextToken();
            int result = memberLogic.updateUserPW(UserVO.builder().user_pw(userNw).user_id(userId).build());
            oos.writeObject(Protocol.RESET_PW);
            if (result > 0) {
              // 비밀번호 재설정 성공
            } else {
              // 비밀번호 재설정 실패
            }
          }

            break;

          /**
           * Main 스레드
           */ ///////////////////////////////////////////////////////////////////////////////////
          // 사용자 친구목록 출력 500#아이디
          case Protocol.PRT_FRDLIST: {
            String userId = st.nextToken();

            // DB등록 및 체크
            server.jta_log.append("친구목록 DB 체크 시작" + "\n");

            List<Object> list = friendLogic.printFriend(UserVO.builder().user_id(userId).build());

            // 결과 프로토콜
            int result = Integer.parseInt(list.get(0).toString());
            String fList = String.valueOf(list.get(1));
            server.jta_log.append("Result: " + result + "\n");

            // 친구 검색 결과가 없음 604
            switch (result) {
              case Protocol.NF_RESULT: {
                oos.writeObject(Protocol.NF_FRDLIST); // 501
              }
                break;
              // 친구 검색 결과 존재 607
              case Protocol.EXIST_FRIEND: {
                oos.writeObject(Protocol.PRT_FRDLIST
                    + Protocol.seperator + fList); // 500
              }
                break;
              case Protocol.FAIL_CONN: { // 800: 데이터베이스 접속 실패
                System.out.println("DB 연결 실패");
                oos.writeObject(Protocol.NF_FRDLIST); // 501로 전달 (친구리스트 없음)
              }
                break;
            }
          }
            break;

          /**
           * [Main 스레드 - 채팅 리스트 출력]
           * 502: 채팅리스트 출력
           * 503: 채팅리스트 없음
           * 800: 데이터베이스 접속 실패
           */
          // 사용자 채팅리스트 출력 502#아이디
          case Protocol.PRT_CHATLIST: {
            String userId = st.nextToken();

            // DB 내 사용자 채팅리스트 확인
            server.jta_log.append("채팅리스트 출력" + "\n");

            Map<String, Object> mChatList = chatListLogic.printChatList(UserVO.builder().user_id(userId).build());

            // 결과 프로토콜
            int result = Integer.parseInt(mChatList.get("PROTOCOL").toString());
            server.jta_log.append("Result: " + result + "\n");

            switch (result) {
              case Protocol.PRT_CHATLIST: { // 502: 채팅리스트 출력
                // 조회된 채팅리스트
                String resultList = "";

                String chNo = ""; // 채팅방 번호
                String ctitle = ""; // 채팅방 타이틀
                List<ChatListVO> lChatList = new ArrayList<>();
                lChatList = (List<ChatListVO>) mChatList.get("CHAT_LIST");

                for (int i = 0; i < lChatList.size() - 1; i++) {
                  chNo = Integer.toString(lChatList.get(i).getChat_no());
                  ctitle = lChatList.get(i).getChat_title();

                  resultList += (chNo + "|" + ctitle + "#");
                }
                chNo = Integer.toString(lChatList.get(lChatList.size() - 1).getChat_no());
                ctitle = lChatList.get(lChatList.size() - 1).getChat_title();

                resultList += (chNo + "|" + ctitle);

                oos.writeObject(Protocol.PRT_CHATLIST
                    + Protocol.seperator + resultList); // 502: 채팅리스트 출력

              }
                break;

              case Protocol.NF_CHATLIST: { // 503: 채팅리스트 없음
                oos.writeObject(Protocol.NF_CHATLIST); // 503로 전달 (채팅리스트 없음)
              }
                break;

              case Protocol.FAIL_CONN: { // 800: 데이터베이스 접속 실패
                // TODO: 로그 작성
                System.out.println("DB 연결 실패");
                oos.writeObject(Protocol.NF_CHATLIST); // 503로 전달 (채팅리스트 없음)
              }
                break;
            }
          }
            break; // end of (Main 스레드 내 채팅 리스트 출력)

          /**
           * main 친구 삭제
           */
          case Protocol.DEL_FRIEND: {
            String userId = st.nextToken();
            String selNick = st.nextToken();
            // DB등록 및 체크
            server.jta_log.append("친구삭제 DB 체크 시작" + "\n");
            int result = friendLogic.delFriend(UserVO.builder().user_id(userId).build(), selNick);
            server.jta_log.append("result: " + result + "\n");
            switch (result) {
              // 친구 삭제 성공
              case Protocol.DEL_FRIEND: {
                oos.writeObject(Protocol.DEL_FRIEND);
              }
                break;
              // 친구 삭제 실패
              case Protocol.NF_RESULT: {
                oos.writeObject(Protocol.FAIL_DEL_FRIEND);
              }
                break;
            }
          }
            break;

          /**
           * main 채팅방 삭제
           */
          case Protocol.DEL_CHAT: {
            String selChat = st.nextToken();
            server.jta_log.append("채팅방 삭제 DB 체크 시작" + "\n");
            int result = chatLogic.delChatContents(null);
            server.jta_log.append("result: " + result + "\n");
            switch (result) {
              // 채팅방 삭제 성공
              case 1: {
                oos.writeObject(Protocol.DEL_CHAT);
              }
                break;
              // 채팅방 삭제 실패
              case 0, -1: {
                oos.writeObject(Protocol.FAIL_DEL_CHAT);
              }
                break;
            }
          }
            break;

          /**
           * [Main 다이얼로그 - 친구 목록 출력]
           * 600 : PRT_USERS = 친구검색 출력
           * 607 : EXIST_FRIEND = 친구 검색 존재
           * 604 : NF_RESULT = 친구 검색 결과가 없음
           * 800 : FAIL_CONN = 데이터베이스 접속 실패
           */
          case Protocol.PRT_USERS, Protocol.PRT_FRIENDS: {
            String userId = st.nextToken();

            // DB등록 및 체크
            server.jta_log.append("main다이얼로그 친구목록 출력" + "\n");

            List<Object> list = friendLogic.printFriend(UserVO.builder().user_id(userId).build());

            // 결과 프로토콜
            int result = Integer.parseInt(list.get(0).toString());
            server.jta_log.append("Result: " + result + "\n");

            switch (result) { // 향상된 switch문
              case Protocol.EXIST_FRIEND -> { // 607 : 친구 검색 존재
                String fList = String.valueOf(list.get(1));
                oos.writeObject(Protocol.PRT_FRIENDS
                    + Protocol.seperator + fList); // 602로 전달 (친구목록 출력)
              }

              case Protocol.NF_RESULT -> { // 604 : 친구 검색 결과가 없음
                oos.writeObject(Protocol.NULL_FRIENDS); // 609로 전달 (친구목록이 없음)
              }

              case Protocol.FAIL_CONN -> { // 800 : 데이터베이스 접속 실패
                System.out.println("DB 연결 실패");
                oos.writeObject(Protocol.NULL_FRIENDS); // 609로 전달 (친구목록이 없음)
              }
            }
          }
            break;

          /**
           * [Main 다이얼로그 - 검색버튼(모든 사용자 중에 검색)]
           * 601 : SRCH_USERS = 검색버튼(친구추가 → 모든 사용자 중에 검색)
           * 607 : EXIST_FRIEND = 친구 검색 존재
           * 604 : NF_RESULT = 친구 검색 결과가 없음
           * 800 : FAIL_CONN = 데이터베이스 접속 실패
           */
          case Protocol.SRCH_USERS: {
            String user_id = st.nextToken();
            String friend_id = st.nextToken();

            // DB등록 및 체크
            server.jta_log.append("main다이얼로그 사용자 검색\n");
            List<Object> list = friendLogic.findFriend(friend_id);

            // 결과 프로토콜
            int result = Integer.parseInt(list.get(0).toString());

            switch (result) {
              case Protocol.EXIST_FRIEND: { // 607 : EXIST_FRIEND = 친구 검색 존재
                String findFri = String.valueOf(list.get(1));

                oos.writeObject(Protocol.EXIST_USER
                    + Protocol.seperator + findFri); // 611로 전달 (해당 사용자 존재)
              }
                break;

              case Protocol.NF_RESULT: { // 604 : NF_RESULT = 친구 검색 결과가 없음
                oos.writeObject(Protocol.NF_RESULT); // 610로 전달 (해당 사용자 없음)
              }
                break;

              case Protocol.FAIL_CONN: { // 800 : FAIL_CONN = 데이터베이스 접속 실패
                System.out.println("DB 연결 실패");
                oos.writeObject(Protocol.NF_RESULT); // 604로 전달 (친구 검색 결과가 없음)
              }
                break;

            }
          }
            break;

          /**
           * [Main 다이얼로그 - 친구검색 출력(새채팅 → 친구목록)]
           * 602 : PRT_FRIENDS = 친구검색 출력(새채팅 → 친구목록)
           * 604 : NF_RESULT = 친구 검색 결과가 없음
           * 800 : FAIL_CONN = 데이터베이스 접속 실패
           */
          // case Protocol.PRT_FRIENDS: {
          // String userId = st.nextToken();
          //
          // // DB등록 및 체크
          // server.jta_log.append("친구목록 DB 체크 시작" + "\n");
          //
          // List<Object> list =
          // friendLogic.printFriend(UserVO.builder().user_id(userId).build());
          //
          // // 결과 프로토콜
          // int result = Integer.parseInt(list.get(0).toString());
          // server.jta_log.append("Result: " + result + "\n");
          //
          // switch (result) {
          // case Protocol.EXIST_FRIEND: { // 607 : 친구 검색 존재
          // String fList = String.valueOf(list.get(1));
          // oos.writeObject(Protocol.PRT_FRDLIST
          // + Protocol.seperator + fList); // 500로 전달 (친구목록 출력)
          // }
          // break;
          // case Protocol.NF_RESULT: { // 604 : 친구 검색 결과가 없음
          // oos.writeObject(Protocol.NF_FRDLIST); // 501로 전달 (친구리스트 없음)
          // }
          // break;
          // case Protocol.FAIL_CONN: { // 800 : 데이터베이스 접속 실패
          // System.out.println("DB 연결 실패");
          // oos.writeObject(Protocol.NF_RESULT); // 501로 전달 (친구리스트 없음)
          // }
          // break;
          // }
          // }
          // break;

          /**
           * [Main 다이얼로그 - 검색버튼(친구 중에 검색)]
           * 603 : SRCH_FRIEDNDS = 검색버튼(친구추가 → 모든 사용자 중에 검색)
           * 604 : NF_RESULT = 친구 검색 결과가 없음
           * 800 : FAIL_CONN = 데이터베이스 접속 실패
           */
          case Protocol.SRCH_FRIEDNDS: {
            String user_id = st.nextToken();
            String friend_id = st.nextToken();

            // DB등록 및 체크
            server.jta_log.append("main다이얼로그 친구 검색\n");
            List<Object> list = friendLogic.findFriend(UserVO.builder().user_id(user_id).build(), friend_id);

            // 결과 프로토콜
            int result = Integer.parseInt(list.get(0).toString());

            switch (result) {
              case Protocol.EXIST_FRIEND: { // 607 : EXIST_FRIEND = 친구 검색 존재
                String findFri = String.valueOf(list.get(1));

                oos.writeObject(Protocol.EXIST_FRIEND
                    + Protocol.seperator + findFri); // 607로 전달 (친구 검색 존재)
              }
                break;

              case Protocol.NF_RESULT: { // 604 : NF_RESULT = 친구 검색 결과가 없음
                oos.writeObject(Protocol.NF_RESULT); // 604로 전달 (친구 검색 결과가 없음)
              }
                break;

              case Protocol.FAIL_CONN: { // 800 : FAIL_CONN = 데이터베이스 접속 실패
                System.out.println("DB 연결 실패");
                oos.writeObject(Protocol.NF_RESULT); // 604로 전달 (친구 검색 결과가 없음)
              }
                break;

            }
          }
            break;

          /**
           * [Main 다이얼로그 - 친구 추가 이벤트]
           * 605 : ADD_FRIEND = 검색버튼(친구추가 → 모든 사용자 중에 검색)
           * 604 : NF_RESULT = 친구 검색 결과가 없음
           * 800 : FAIL_CONN = 데이터베이스 접속 실패
           */
          case Protocol.ADD_FRIEND: {
            String userId = st.nextToken();
            String friendId = st.nextToken();

            // DB등록 및 체크
            server.jta_log.append("main다이얼로그 친구 추가\n");

            int result = 0;// 초기값 대충,,,

            // 친구 목록 생성
            String[] friendList = friendId.split(",");
            for (int i = 0; i < friendList.length - 1; i++) {
              result = friendLogic.addFriend(UserVO.builder().user_id(userId).build(), friendList[i]);
            }
            switch (result) {
              case 1 -> { // 이벤트 성공
                oos.writeObject(Protocol.ADD_FRIEND); // 605로 전달 (검색버튼(친구추가 → 모든 사용자 중에 검색))
              }
              case -1 -> { // 이벤트 실패
                oos.writeObject(Protocol.FAIL_ADD_FRIEND); // 612로 전달 (친구 추가 실패)
              }
            }
          }
            break;

          /**
           * [Main 다이얼로그 - 채팅방 만들기]
           * 606 : CREATE_CHAT = 채팅방 만들기 성공
           * 608 : FAIL_CRE_CHAT = 채팅방 만들기 실패
           * 800 : FAIL_CONN = 데이터베이스 접속 실패
           */
          case Protocol.CREATE_CHAT: {
            String userId = st.nextToken();
            String userList = st.nextToken();

            // DB와 같은지 체크
            server.jta_log.append("채팅방 만들기\n");

            int result = chatListLogic.createChat(userList);

            switch (result) {
              case Protocol.CREATE_CHAT -> {
                oos.writeObject(Protocol.CREATE_CHAT); // 606로 전달 (채팅방 만들기 성공)
              }

              case Protocol.FAIL_CRE_CHAT -> {
                oos.writeObject(Protocol.FAIL_CRE_CHAT); // 608로 전달 (채팅방 만들기 실패)
              }

              case Protocol.FAIL_CONN -> {
                System.out.println("DB 연결 실패");
                oos.writeObject(Protocol.FAIL_CRE_CHAT); // 608로 전달 (채팅방 만들기 실패)
              }
            }
          }
            break;

          ///////////////////////////////////////////////////////////////////////////////////
          /**
           * MyPage 스레드
           */
          // 사용자 정보 가져오기 504#아이디
          case Protocol.BTN_MYPAGE: {
            String userId = st.nextToken();
            // DB등록 및 체크
            server.jta_log.append("사용자조회 DB 체크 시작" + "\n");
            UserVO userVO = memberLogic.getUserInfo(UserVO.builder().user_id(userId).build());
            String userName = userVO.getUser_name();
            String userHp = userVO.getUser_hp();
            String nickName = userVO.getUser_nickname();
            // 값이 있을 경우
            if (userHp != null) {
              server.jta_log.append("Result: " + Protocol.BTN_MYPAGE + "\n");
              oos.writeObject(Protocol.BTN_MYPAGE
                  + Protocol.seperator + userName
                  + Protocol.seperator + userHp
                  + Protocol.seperator + userId
                  + Protocol.seperator + nickName);
            }
            // 값이 없을 경우
            else {
              server.jta_log.append("Result: " + Protocol.NF_MYPAGE + "\n");
              oos.writeObject(Protocol.NF_MYPAGE);
            }
          }
            break;

          // 새로운 닉네임 중복체크 514#닉네임
          case Protocol.NICK_MCHK: {
            String newNick = st.nextToken();
            // DB와 같은지 체크
            server.jta_log.append("닉네임 중복 DB 체크 시작" + "\n");
            int result = memberLogic.checkDuplNickname(UserVO.builder().user_nickname(newNick).build());
            server.jta_log.append("Result: " + result + "\n");
            // 체크 결과 switch문
            switch (result) {
              // 새 닉네임 중복 아님 514
              case 1: {
                oos.writeObject(Protocol.NICK_MCHK);
              }
                break;
              // 새 닉네임 중복됨 515
              case -1: {
                oos.writeObject(Protocol.EXIST_MNICK);
              }
                break;
            }
          }
            break;

          // 사용자 정보 변경 516#닉네임#비밀번호
          case Protocol.EDIT_MNICK: {
            String newNick = st.nextToken();
            String userId = st.nextToken();
            // DB체크
            server.jta_log.append("닉네임변경 DB 체크 시작" + "\n");
            int result = memberLogic.updateUserNick(UserVO.builder().user_nickname(newNick).user_id(userId).build());
            server.jta_log.append("Result: " + result + "\n");
            // 체크 결과 switch문
            switch (result) {
              // 닉네임 수정 성공 516
              case 1: {
                this.userNick = newNick;
                oos.writeObject(Protocol.EDIT_MNICK
                    + Protocol.seperator + newNick);
              }
                break;
              // 닉네임 수정 실패 517
              case -1, 0: {
                oos.writeObject(Protocol.FAIL_MNICK);
              }
                break;
            }
          }
            break;

          // DB에 있는 비밀번호와 닉네임을 둘 다 변경 520#닉네임#비밀번호#아이디
          case Protocol.EDIT_MBOTH: {
            String newNick = st.nextToken();
            String newPw = st.nextToken();
            String userId = st.nextToken();
            // DB체크
            server.jta_log.append("닉네임, 비밀번호 변경 DB 체크 시작" + "\n");
            int result = memberLogic.updateUserNick(UserVO.builder().user_nickname(newNick).user_id(userId).build());
            int result2 = memberLogic.updateUserPW(UserVO.builder().user_pw(newPw).user_id(userId).build());
            server.jta_log.append("Result: " + result + result2 + "\n");

            // 체크 결과 if문
            if (result == 1 && result2 == 1) {
              oos.writeObject(Protocol.EDIT_MBOTH + Protocol.seperator + newNick);
            } else {
              oos.writeObject(Protocol.FAIL_MBOTH);
            }
          }
            break;

          // 비밀번호만 변경 518#아이디#비밀번호
          case Protocol.EDIT_MPW: {
            String userId = st.nextToken();
            String newPw = st.nextToken();
            // DB체크
            server.jta_log.append("비밀번호변경 DB 체크 시작" + "\n");
            int result = memberLogic.updateUserPW(UserVO.builder().user_pw(newPw).user_id(userId).build());
            server.jta_log.append("Result: " + result + "\n");
            // 체크 결과 switch문
            switch (result) {
              // 비밀번호 수정 성공 518
              case 1: {
                oos.writeObject(Protocol.EDIT_MPW);
              }
                break;
              // 비밀번호 수정 실패 519
              case -1, 0: {
                oos.writeObject(Protocol.FAIL_MPW);
              }
                break;
            }
          }
            break;

          // 마이페이지 회원탈퇴 성공 522#아이디#비밀번호
          case Protocol.DEL_ACNT: {
            String userId = st.nextToken();
            String userPw = st.nextToken();
            // DB체크
            server.jta_log.append("회원탈퇴 DB 체크 시작" + "\n");
            int result = memberLogic.deleteAccount(UserVO.builder().user_id(userId).user_pw(userPw).build());
            server.jta_log.append("Result: " + result + "\n");
            // 체크 결과 swith문
            switch (result) {
              // 마이페이지 회원탈퇴 성공 522
              case 1: {
                oos.writeObject(Protocol.DEL_ACNT);
              }
                break;
              // 마이페이지 회원탈퇴 실패 523
              case -1, 0, 523: {
                oos.writeObject(Protocol.FAIL_DACNT);
              }
                break;
            }
          }
            break;

          /**
           * ChatRoom 스레드
           */
          // 채팅방 불러오기 700#채팅방번호
          case Protocol.CHAT_START: {
            int chatNo = Integer.parseInt(st.nextToken());
            // DB체크
            server.jta_log.append("채팅방 불러오기 DB 체크 시작" + "\n");

            // 로직 수정할것!
            // 날짜(2023/01/21)#닉네임#채팅내용 String형식으로 준다고 가정
            List<ChatContentsVO> result = chatLogic.ChatCall(chatNo);

            if (result != null) {
              String resultList = "";
              String date = "";
              String nick = "";
              String ccon = ""; // 채팅 내용
              for (int i = 0; i < result.size() - 1; i++) {// -1인 이유? 문자열을 바꿀때 한번 더 돈다. 리스트 마지막번호

                date = result.get(i).getChat_date();
                ;
                nick = result.get(i).getUser_id();
                ;
                ccon = result.get(i).getChat_content();
                ;
                resultList += (date + "#" + nick + "#" + ccon + "#");
              }
              date = result.get(result.size() - 1).getChat_date();
              nick = result.get(result.size() - 1).getUser_id();
              ccon = result.get(result.size() - 1).getChat_content();
              resultList += (date + "#" + nick + "#" + ccon);
              // 클라이언트에 전송 700#결과(날짜#닉네임#채팅내용)
              oos.writeObject(Protocol.CHAT_START
                  + Protocol.seperator + resultList);
              this.chatNo = String.valueOf(chatNo);
            } else {
              server.jta_log.append("result: null" + "\n");
            }
          }
            break;

          // 대화내용 저장 707#채팅방넘버#아이디#닉네임#메시지
          case Protocol.SAVE_CHAT: {
            String chatNo = st.nextToken();
            String userId = st.nextToken();
            String userNick = st.nextToken();
            String chatCont = st.nextToken();
            server.jta_log.append("그룹채팅 저장 DB 체크 시작" + "\n");
            int result = chatLogic
                .insertChat(ChatContentsVO.builder().chat_no(Integer.parseInt(chatNo)).user_id(userId)
                    .chat_content(chatCont).build());
            server.jta_log.append("result: " + result + "\n");
            // 메시지 저장 및 전달
            switch (result) {
              // 저장성공
              case 1: {
                // 같은 단톡방에 말 전달하기 701#채팅방넘버#닉네임#메시지
                // 서버스레드를 다 저장하고 모든 서버스레드에 전달하되 챗넘버가 같은경우에만 메시지전달
                broadCasting(chatNo, userNick, chatCont);
                server.jta_log.append("그룹채팅 메시지 저장 성공" + "\n");
              }
                break;
              // 저장실패 702
              case 0, -1: {
                oos.writeObject(Protocol.FAIL_MSG);
                server.jta_log.append("그룹채팅 메시지 저장 실패" + "\n");
              }
                break;
              }
          }
            break;

            // 메시지 출력 701#닉네임#메시지내용
          case Protocol.SEND_MSG: {
            String recvNo = st.nextToken();
            String recvNick = st.nextToken();
            String recvMsg = st.nextToken();
            // 자기가 보낸 메시지가 아니고 채팅방넘버가 같으면 클라이언트로 전송
            if (!userNick.equals(recvNick) && chatNo.equals(recvNo)) {
              oos.writeObject(Protocol.SEND_MSG
                  + Protocol.seperator + recvNick
                  + Protocol.seperator + recvMsg);
            }
          }
            break;
          // case Protocol.WHISPER: {
          // String nickName = st.nextToken();// 보내는 넘
          // // insert here - 받는 넘
          // String otherName = st.nextToken();// 보내는 넘
          // // 귓속말로 보내진 메시지
          // String msg1 = st.nextToken();
          // for (ServerThread serverThread : server.globalList) {
          // if (otherName.equals(serverThread.chatName)) {
          // serverThread.send(Protocol.WHISPER + Protocol.seperator + nickName +
          // Protocol.seperator + otherName
          // + Protocol.seperator + msg1);
          // break;
          // }
          // } // end of for
          // this.send(Protocol.WHISPER + Protocol.seperator + nickName +
          // Protocol.seperator + otherName
          // + Protocol.seperator + msg1);
          // }
          // break;
          // case Protocol.TALK_OUT: {
          // String nickName = st.nextToken();
          // server.globalList.remove(this);
          // broadCasting(Protocol.TALK_OUT + Protocol.seperator + nickName);
          // }
          // break run_start;
        } // end of switch
      } // end of while
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}