package banana_project.server.thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread extends Thread {
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
      run_start: while (!isStop) {
        msg = (String) ois.readObject(); //클라이언트가 서버에게 전송한 메시지
        server.jta_log.append(msg + "\n");
        server.jta_log.setCaretPosition(server.jta_log.getDocument().getLength());
        StringTokenizer st = null;
        int protocol = 0;
        if (msg != null) {
          st = new StringTokenizer(msg, "#");
          protocol = Integer.parseInt(st.nextToken());// 100
        }
        switch (protocol) {
          case Protocol.CLIENT_START: {
            String userId = st.nextToken();
            String userPw = st.nextToken();
            broadCasting(Protocol.CLIENT_START
                + Protocol.seperator + userId
                + Protocol.seperator + userPw);
          }
            break;
          // case Protocol.WHISPER: {
          //   String nickName = st.nextToken();// 보내는 넘
          //   // insert here - 받는 넘
          //   String otherName = st.nextToken();// 보내는 넘
          //   // 귓속말로 보내진 메시지
          //   String msg1 = st.nextToken();
          //   for (ServerThread serverThread : server.globalList) {
          //     if (otherName.equals(serverThread.chatName)) {
          //       serverThread.send(Protocol.WHISPER + Protocol.seperator + nickName +
          //           Protocol.seperator + otherName
          //           + Protocol.seperator + msg1);
          //       break;
          //     }
          //   } // end of for
          //   this.send(Protocol.WHISPER + Protocol.seperator + nickName +
          //       Protocol.seperator + otherName
          //       + Protocol.seperator + msg1);
          // }
          //   break;
          // case Protocol.CHANGE: {
          //   String nickName = st.nextToken();
          //   String afterName = st.nextToken();
          //   String message = st.nextToken();
          //   this.chatName = afterName;
          //   broadCasting(Protocol.CHANGE + Protocol.seperator + nickName +
          //       Protocol.seperator + afterName
          //       + Protocol.seperator + message);
          // }
          //   break;
          // case Protocol.TALK_OUT: {
          //   String nickName = st.nextToken();
          //   server.globalList.remove(this);
          //   broadCasting(Protocol.TALK_OUT + Protocol.seperator + nickName);
          // }
          //   break run_start;
        }// end of switch
      } // end of while
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}