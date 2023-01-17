package banana_project.server.thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.StringTokenizer;

import com.google.common.collect.ImmutableBiMap.Builder;

import banana_project.server.logic.MemberLogic;
import banana_project.server.vo.UserVO;

public class ServerThread extends Thread {
  MemberLogic memberLogic = null;
  Server server = null;
  Socket client = null;
  ObjectOutputStream oos = null;
  ObjectInputStream ois = null;
  // 현재 서버에 입장한 클라이언트 스레드의 닉네임 저장
  String chatName = null;

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
      oos = new ObjectOutputStream(client.getOutputStream()); // 말하기
      ois = new ObjectInputStream(client.getInputStream()); // 듣기
      // 현재 서버에 입장한 클라이언트 스레드 추가하기
      server.globalList.add(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 현재 입장해 있는 친구들 모두에게 메시지 전송하기 구현
   * 
   * @param msg
   */
  public void broadCasting(String msg) {
    for (ServerThread serverThread : server.globalList) {
      serverThread.send(msg);
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
                oos.writeObject(Protocol.LOGIN_S
                    + Protocol.seperator + userId);
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
            UserVO user = memberLogic.findUserId(UserVO.builder().user_name(userName).user_hp(userHp).build());
            // 아이디 존재 303
            if (user.getUser_id() != null) {
              oos.writeObject(Protocol.EXIST_FID
                  + Protocol.seperator + userName
                  + Protocol.seperator + user.getUser_id());
            }
            // 아이디가 존재하지 않음 302
            else {
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
            // 계정 존재 403
            // 계정 존재하지 않음402
          }
            break;
          /**
           * PwFindDialog 스레드
           */
          // 비밀번호 재설정 404#아이디#새로운비번
          case Protocol.RESET_PW: {
            String userId = st.nextToken();
            String userPw = st.nextToken();
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
          // case Protocol.CHANGE: {
          // String nickName = st.nextToken();
          // String afterName = st.nextToken();
          // String message = st.nextToken();
          // this.chatName = afterName;
          // broadCasting(Protocol.CHANGE + Protocol.seperator + nickName +
          // Protocol.seperator + afterName
          // + Protocol.seperator + message);
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