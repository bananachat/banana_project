package banana_project.client.login;

import java.util.StringTokenizer;
import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.server.thread.Protocol;

public class ClientThread extends Thread {
  /**
   * 서버연결부 선언
   */
  Client client = null;
  // 테스트용 유저정보
  String nickName = null;

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
        String msg = "";
        msg = (String) client.ois.readObject(); // 서버스레드가 클라이언트에게 전송한 메시지
        StringTokenizer st = null;
        int protocol = 0;
        // 토큰 설정 및 전송받은 프로토콜
        if (msg != null) {
          st = new StringTokenizer(msg, "#");
          protocol = Integer.parseInt(st.nextToken());
        }
        // 프로토콜 switch문 시작
        switch (protocol) {
          // 로그인 성공 -> 101#닉네임
          case Protocol.LOGIN_S: {
            nickName = st.nextToken();
            client.login_s();
          }
          // 아이디 틀림
          case Protocol.WRONG_ID: {
            // 아이디틀림메소드
          }
          // 비밀번호 틀림
          case Protocol.WRONG_PW: {
            // 비번틀림메소드
          }
          default:
            System.out.println("해당하는 프로토콜이 존재하지 않습니다.");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
