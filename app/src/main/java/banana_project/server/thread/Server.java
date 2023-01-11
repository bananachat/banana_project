package banana_project.server.thread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class Server extends JFrame implements Runnable, ActionListener {
  /**
   * 서버연결부 선언
   */
  ServerThread serverThread = null;
  List<ServerThread> globalList = null;
  ServerSocket serverSocket = null;
  Socket socket = null;

  /**
   * 화면부 선언
   */
  JTextArea jta_log = new JTextArea(10, 30);
  JScrollPane jsp_log = new JScrollPane(jta_log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  JButton jbtn_log = new JButton("로그저장");

  /**
   * 생성자
   */
  public Server() {
  }

  /**
   * 화면부 메소드
   */
  public void initDisplay() {
    jbtn_log.addActionListener(this);
    this.add("North", jbtn_log);
    this.add("Center", jsp_log);
    this.setSize(500, 400);
    this.setVisible(true);
  }

  /**
   * 메인메소드
   * 
   * @param args
   */
  public static void main(String[] args) {
    Server server = new Server();
    server.initDisplay();
    Thread thread = new Thread(server);
    thread.start();
  }

  /**
   * Thread 메소드
   */
  @Override
  public void run() {
    globalList = new Vector<>();
    boolean isStop = false;
    try {
      serverSocket = new ServerSocket(3000);
      System.out.println("123");
      jta_log.append("Server Ready ...\n");
      while (!isStop) {
        socket = serverSocket.accept();
        jta_log.append("client info: " + socket + "\n");
        ServerThread serverThread = new ServerThread(this);
        serverThread.start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * ActionListener 메소드
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    // 로그를 파일로 저장하기
  }
}