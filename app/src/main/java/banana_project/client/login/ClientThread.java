package banana_project.client.login;

import java.util.StringTokenizer;

import banana_project.server.thread.Protocol;

public class ClientThread extends Thread {
  Client client = null;

  public ClientThread(Client client) {
    this.client = client;
  }

  @Override
  public void run() {
    boolean isStop = false;
    while (!isStop) {
      try {
        String msg = "";
        msg = (String) client.ois.readObject(); // 서버가 클라이언트에게 전송한 메시지
        StringTokenizer st = null;
        int protocol = 0;
        // 토큰 설정 및 전송받은 프로토콜
        if (msg != null) {
          st = new StringTokenizer(msg, "#");
          protocol = Integer.parseInt(st.nextToken());
        }
        // 프로토콜 switch문 시작
        switch (protocol) {
          // 로그인 성공
          case Protocol.LOGIN_S: {
            // main = new Main(client);
            // main.initDisplay();
          }
          // 아이디 틀림
          case Protocol.WRONG_ID: {
          }
          // 비밀번호 틀림
          case Protocol.WRONG_PW: {
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
