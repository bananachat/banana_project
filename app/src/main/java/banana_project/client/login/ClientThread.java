package banana_project.client.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import banana_project.server.thread.Protocol;
import banana_project.server.vo.ChatListVO;

public class ClientThread extends Thread {
  /**
   * 서버연결부 선언
   */
  Client client = null;
  // 메세지 출력 조건용
  String chatNo = "";
  String userNick = "";

  /**
   * 생성자
   * 
   * @param client
   */
  public ClientThread(Client client) {
    this.client = client;
  }

  @Override
  public void run() {
    boolean isStop = false;
    while (!isStop) {
      try {
        String msg = null;
        msg = String.valueOf(client.ois.readObject()); // 서버스레드가 클라이언트에게 전송한 메시지
        StringTokenizer st = null;
        int protocol = 0;
        // 토큰 설정 및 전송받은 프로토콜
        if (msg != null) {
          st = new StringTokenizer(msg, "#");
          protocol = Integer.parseInt(st.nextToken());
        }
        // 프로토콜 switch문 시작
        switch (protocol) {
          /**
           * Client 스레드
           */
          // 로그인 성공 -> 101#아이디
          case Protocol.LOGIN_S: {
            String userId = st.nextToken();
            String userNick = st.nextToken();
            // 유저닉네임 담기
            this.userNick = userNick;
            client.login_s(userId, userNick);
          }
            break;

          // 비번 틀림 103
          case Protocol.WRONG_PW: {
            client.wrong_pw();
          }
            break;

          // 비번 시도횟수 초과 104
          case Protocol.OVER_FAIL_CNT: {
            client.over_fail_cnt();
          }
            break;

          // 계정없음 102
          case Protocol.WRONG_ID: {
            client.wrong_id();
          }
            break;

          /**
           * MemJoin 스레드
           */
          // 사용가능한 아이디 201
          case Protocol.MAIL_CHK: {
            client.memJoin.mail_chk();
          }
            break;

          // 이미 존재하는 아이디 202
          case Protocol.EXIST_MAIL: {
            client.memJoin.exist_mail();
          }
            break;

          // 사용가능한 닉네임 203
          case Protocol.NICK_CHK: {
            client.memJoin.nick_chk();
          }
            break;

          // 이미 존재하는 닉네임 204
          case Protocol.EXIST_NICK: {
            client.memJoin.exist_nick();
          }
            break;

          // 계정(핸드폰) 존재 206
          case Protocol.EXIST_ACNT: {
            client.memJoin.exist_acnt();
          }
            break;

          // 회원가입 성공 207#이름
          case Protocol.SIGN_SUS: {
            String userName = st.nextToken();
            client.memJoin.sign_sus(userName);
          }
            break;

          // 회원가입 실패 208
          case Protocol.SIGN_ERR: {
            client.memJoin.sign_err();
          }
            break;

          /**
           * IdFind 스레드
           */
          // 아이디 존재 303#이름#아이디
          case Protocol.EXIST_FID: {
            String userName = st.nextToken();
            String userId = st.nextToken();
            client.idFind.exist_fid(userName, userId);
          }
            break;

          // 아이디가 존재하지 않음 302
          case Protocol.NF_FID: {
            client.idFind.nf_fid();
          }
            break;

          /**
           * PwFind스레드
           */
          // 계정이 존재할 때 403
          case Protocol.EXIST_FACNT: {
            String userId = st.nextToken();
            client.pwfind.exist_facnt(userId);
            break;
          }

          // 계정이 존재하지 않을때 402
          case Protocol.NF_FACNT: {
            client.pwfind.nf_facnt();
            break;
          }

          /**
           * PwFindDialog 스레드
           */
          // 비밀번호 재설정 성공 404
          case Protocol.RESET_PW: {
            client.pwfind.pwFindDialog.reset_pw();
            break;
          }

          // 비밀번호 재설정 실패 405
          case Protocol.RESETFAIL_PW: {
            client.pwfind.pwFindDialog.fail_pw();
            break;
          }

          /**
           * Main 스레드
           */
          // 친구가 존재하지 않음 501
          case Protocol.NF_FRDLIST: {
            client.main.nf_frdlist();
          }
            break;

          // 친구가 존재함 500 -> 친구
          case Protocol.PRT_FRDLIST: {
            Vector<String> vList = new Vector<>();
            while (st.hasMoreTokens()) {
              String fList = st.nextToken();
              vList.add(fList);
            }
            client.main.prt_frdlist(vList);
          }
            break;

          /**
           * Main 채팅리스트 출력
           */
          // 채팅리스트 존재 502
          case Protocol.PRT_CHATLIST: {
            System.out.println("채팅리스트 존재");
            ArrayList<ChatListVO> chatList = new ArrayList<>();
            ArrayList<Integer> countList = new ArrayList<>();

            while (st.hasMoreTokens()) {
              String list = st.nextToken();
              String[] sl = list.split("\\|");
              ChatListVO cv = new ChatListVO(Integer.parseInt(sl[0]), sl[1]);
              chatList.add(cv);
              countList.add(Integer.parseInt(sl[2]));
            }
            System.out.println("채팅리스트 : " + chatList);

            client.main.print_chatList(chatList, countList);
          }
            break;

          // 채팅리스트 없음 503
          case Protocol.NF_CHATLIST: {
            System.out.println("채팅리스트 없음");
            client.main.nf_chatList();
          }
            break;

          // main 친구 삭제 511#아이디
          case Protocol.DEL_FRIEND: {
            String userId = st.nextToken();
            // 친구목록 다시 불러오기
            client.oos.writeObject(Protocol.PRT_FRDLIST
                + Protocol.seperator + userId);
            client.main.del_friend();
          }
            break;

          // main 친구 삭제 실패 524
          case Protocol.FAIL_DEL_FRIEND: {
            client.main.fail_del_friend();
          }
            break;

          // main 채팅방 삭제 512#아이디
          case Protocol.DEL_CHAT: {
            String userId = st.nextToken();
            // 채팅방목록 다시 불러오기
            client.oos.writeObject(Protocol.PRT_CHATLIST
                + Protocol.seperator + userId);
            client.main.del_chat();
          }
            break;

          // main 채팅방 삭제 실패 525
          case Protocol.FAIL_DEL_CHAT: {
            client.main.fail_del_chat();
          }
            break;

          /**
           * Main다이얼로그 친구 목록 출력
           */
          // PRT_FRIENDS : 친구검색 출력 602
          case Protocol.PRT_FRIENDS: {
            System.out.println("다이얼로그 - 친구 출력");
            Vector<String> vList = new Vector<>();

            while (st.hasMoreTokens()) {
              String fList = st.nextToken();
              vList.add(fList);
            }

            client.main.flDialog.prt_frdList(vList);
          }
            break;

          /*
           * NULL_FRIENDS: 친구목록이 없음 609
           * NULL_USER: 해당 사용자 없음 610
           * NF_RESULT: 친구 검색 결과가 없음 604
           */
          case Protocol.NULL_FRIENDS, Protocol.NULL_USER, Protocol.NF_RESULT: {
            System.out.println("다이얼로그 - 검색 결과 없음");
            client.main.flDialog.nf_frdList();
          }
            break;

          /**
           * Main다이얼로그 사용자 검색
           */
          // 해당 사용자 존재 611
          case Protocol.EXIST_USER: {
            System.out.println("다이얼로그 - 검색한 사용자 출력");
            Vector<String> vList = new Vector<>();
            String userId = st.nextToken();

            vList.add(userId);

            client.main.flDialog.prt_frdList(vList);
          }
            break;

          /**
           * Main다이얼로그 친구 검색
           */
          // 검색 결과 존재 607
          case Protocol.EXIST_FRIEND: {
            System.out.println("다이얼로그 - 검색한 친구 출력");
            Vector<String> vList = new Vector<>();
            while(st.hasMoreTokens()) {
              String userId = st.nextToken();
              vList.add(userId);
            }
            client.main.flDialog.prt_frdList(vList);
          }
            break;

          /**
           * Main다이얼로그 친구 추가
           */
          // 친구 추가 이벤트 605
          case Protocol.ADD_FRIEND: {
            System.out.println("다이얼로그 - 친구 추가");
            client.main.flDialog.add_friend();
          }
            break;

          // 친구 추가 실패 612
          case Protocol.FAIL_ADD_FRIEND: {
            System.out.println("다이얼로그 - 친구 추가 실패");
            client.main.flDialog.fail_add_friend();
          }
            break;

          /**
           * Main다이얼로그 채팅방 만들기
           */
          // 채팅방 만들기 성공 606
          case Protocol.CREATE_CHAT: {
            String chatNo = st.nextToken();
            String userList = st.nextToken();
            // 채팅방번호 담기
            this.chatNo = chatNo;
            System.out.println("다이얼로그 - 채팅방 만들기");
            client.main.flDialog.create_chatroom(userList, chatNo);
          }
            break;

          // 채팅방 만들기 실패 608
          case Protocol.FAIL_CRE_CHAT: {
            System.out.println("다이얼로그 - 채팅방 만들기 실패");
            client.main.flDialog.fail_create_chatroom();
          }
            break;

          /**
           * MyPage 스레드
           */
          // 사용자 정보 가져오기 성공 504#이름#핸드폰번호#아이디#닉네임
          case Protocol.BTN_MYPAGE: {
            String userName = st.nextToken();
            String userHp = st.nextToken();
            String userId = st.nextToken();
            String nickName = st.nextToken();
            client.main.myPage.btn_mypage(userName, userHp, userId, nickName);
          }
            break;

          // 가져오기 실패 513
          case Protocol.NF_MYPAGE: {
            client.main.myPage.nf_mypage();
          }
            break;

          // 사용가능한 닉네임 514
          case Protocol.NICK_MCHK: {
            client.main.myPage.nick_mchk();
          }
            break;

          // 이미 존재하는 닉네임 515
          case Protocol.EXIST_MNICK: {
            client.main.myPage.exist_mnick();
          }
            break;

          // 닉네임 수정 성공 516
          case Protocol.EDIT_MNICK: {
            String newNick = st.nextToken();
            // 유저 닉네임 담기
            this.userNick = newNick;
            client.main.myPage.edit_mypage(newNick);
          }
            break;

          // 닉네임 수정 실패 517
          case Protocol.FAIL_MNICK: {
            client.main.myPage.fail_mypage();
          }
            break;

          // 비밀번호 수정 성공 518
          case Protocol.EDIT_MPW: {
            client.main.myPage.edit_mpw();
          }
            break;

          // 비밀번호 수정 실패 519
          case Protocol.FAIL_MPW: {
            client.main.myPage.fail_mpw();
          }
            break;

          // 닉네임, 비번 수정 성공 520
          case Protocol.EDIT_MBOTH: {
            String newNick = st.nextToken();
            // 유저 닉네임 담기
            this.userNick = newNick;
            client.main.myPage.edit_mboth(newNick);
          }
            break;

          // 닉네임, 비번 수정 실패 521
          case Protocol.FAIL_MBOTH: {
            client.main.myPage.fail_mboth();
          }
            break;

          // 마이페이지 회원탈퇴 성공 522
          case Protocol.DEL_ACNT: {
            client.main.myPage.del_acnt();
          }
            break;

          // 마이페이지 회원탈퇴 실패 523
          case Protocol.FAIL_DACNT: {
            client.main.myPage.fail_dacnt();
          }
            break;

          /**
           * ChatRoom 스레드
           */
          // 채팅방 불러오기 700#채팅방번호
          case Protocol.CHAT_START: {
            String chatNo = st.nextToken();
            // 채팅방번호 담기
            this.chatNo = chatNo;
            List<Map<String, String>> rList = new ArrayList<>();
            Map<String, String> rMap = null;
            // list map에 담기
            while (st.hasMoreTokens()) {
              String chatDate = st.nextToken();
              String chatNick = st.nextToken();
              String chatCont = st.nextToken();
              rMap = new HashMap<>();
              rMap.put("chatDate", chatDate);
              rMap.put("chatNick", chatNick);
              rMap.put("chatCont", chatCont);
              rList.add(rMap);
            }
            client.main.chatRoom.chat_start(rList);
          }
            break;

          // 새로 만든 채팅방의 경우
          case Protocol.F_CHAT_START: {
            String chatNo = st.nextToken();
            // 채팅방번호 담기
            this.chatNo = chatNo;
          }
            break;

          // 메시지 출력 701#닉네임#메시지내용
          case Protocol.SEND_MSG: {
            String recvNo = st.nextToken();
            String recvNick = st.nextToken();
            String recvMsg = st.nextToken();
            // 채팅방 번호가 같고 닉네임은 다른 경우 메시지 전송
            if (chatNo.equals(recvNo) && !userNick.equals(recvNick)) {
              client.main.chatRoom.recv_msg(recvNick, recvMsg);
            }
          }
            break;

          // 메시지 출력 실패 702
          case Protocol.FAIL_MSG: {
            client.main.chatRoom.fail_msg();
          }
            break;

          // 퇴장 메시지 출력 706#채팅방번호#퇴장메시지(닉네임님이 나갔습니다.)
          case Protocol.EXIT_MEM: {
            String recvNo = st.nextToken();
            String recvMsg = st.nextToken();
            // 채팅방 번호가 같을경우 메시지 전송
            if (chatNo.equals(recvNo)) {
              client.main.chatRoom.exit_mem(recvMsg);
            }
          }
            break;

        } // end of switch
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
