package banana_project.login;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends JFrame implements ActionListener {
  // 서버 연결부 선언
  Socket socket = null;
  ObjectOutputStream oos = null;// 말하기
  ObjectInputStream ois = null;// 듣기
  String userId = null;
  String userPw = null;

  // 화면부 선언
  JFrame jf_login = new JFrame(); // 메인프레임
  JPanel jp_login = new JPanel(null);
  // 아이디, 비밀번호 입력을 위한 JTextField (테두리선을 지우기위해 클래스 재정의)
  JTextField jtf_userId = new JTextField() {
    @Override
    public void setBorder(Border border) {
    }
  };
  JPasswordField jtf_userPw = new JPasswordField() {
    @Override
    public void setBorder(Border border) {
    }
  };
  JLabel jlb_idText = new JLabel("  example@email.com");
  JLabel jlb_pwText = new JLabel("  password");
  JLabel jlb_findId = new JLabel(); // 아이디찾기 라벨
  JLabel jlb_findPw = new JLabel(); // 비밀번호 찾기 라벨
  Font fP = new Font("맑은 고딕", Font.PLAIN, 12);
  Font fB = new Font("맑은 고딕", Font.BOLD, 12);
  String imgPath = "D:\\banana_project\\app\\src\\main\\java\\banana_project\\image\\"; // 이미지파일 위치
  ImageIcon img_main = new ImageIcon(imgPath + "bananaMain.png"); // 메인 로고 이미지
  ImageIcon img_login = new ImageIcon(imgPath + ""); // 로그인 이미지
  ImageIcon img_join = new ImageIcon(imgPath + ""); // 회원가입 이미지
  JButton jbtn_login = new JButton("로그인");
  JButton jbtn_join = new JButton("회원가입");
  JButton jbtn_main = new JButton(img_main); // 메인 로고 이미지 붙이기

  // 화면부 메소드
  public void initDisplay() {
    // 이벤트리스너 연결
    jbtn_login.addActionListener(this);
    jbtn_join.addActionListener(this);
    // 로그인, 패스워드 라벨 설정
    jlb_idText.setForeground(Color.GRAY);
    jlb_pwText.setForeground(Color.GRAY);
    jlb_idText.setBounds(60, 300, 270, 45);
    jlb_pwText.setBounds(60, 340, 270, 45);
    jp_login.add(jlb_idText);
    jp_login.add(jlb_pwText);
    // JTextField(ip,pw입력), JLabel(분실정보찾기), JButton(바나나이미지, 로그인, 가입버튼) 붙임
    jp_login.add(jtf_userId);
    jp_login.add(jtf_userPw);
    jp_login.add(jbtn_login);
    jp_login.add(jbtn_join);
    jp_login.add(jlb_findId);
    jp_login.add(jlb_findPw);
    jp_login.add(jbtn_main);
    // 아이디 비밀번호 입력창 고정 및 비밀번호 암호 *로 표시
    jtf_userId.setBounds(60, 300, 270, 45);
    jtf_userPw.setBounds(60, 340, 270, 45);
    jtf_userPw.setEchoChar('*');

    // 로그인 버튼 정의
    jbtn_login.setBorderPainted(false);
    jbtn_login.setBounds(200, 400, 130, 45);
    // jbtn_login.setBounds(175, 285, 120, 40);
    // KeyListener : 엔터키 누르면 로그인 버튼 눌림
    jbtn_login.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          // join.initDisplay();
        }
      }
    });

    // 회원가입버튼 정의
    jbtn_join.setForeground(Color.BLACK);
    jbtn_join.setFont(fP);
    jbtn_join.setBounds(60, 400, 130, 45);
    jbtn_join.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        // Join join = new Join();
        // join.initDisplay();
      }
    });

    // 아이디/비밀번호 찾기 라벨버튼 정의
    jlb_findId.setText("<HTML><U>아이디 찾기</U></HTML>");
    jlb_findPw.setText("<HTML><U>비밀번호 찾기</U></HTML>");
    jlb_findId.setForeground(Color.BLACK);
    jlb_findPw.setForeground(Color.BLACK);
    jlb_findId.setFont(fP);
    jlb_findPw.setFont(fP);
    jlb_findId.setBounds(100, 460, 200, 20);
    jlb_findPw.setBounds(220, 460, 200, 20);
    jlb_findId.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        IdFind idFind = new IdFind();
        idFind.initDisplay();
      }
    });
    jlb_findPw.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        PwFind pwFind = new PwFind();
        pwFind.initDisplay();
      }
    });

    // 바나나 이미지 정의
    jbtn_main.setBackground(new Color(255, 230, 120));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(60, 35, 270, 250); // 바나나 이미지 고정
    jp_login.setBackground(new Color(255, 230, 120)); // 도화지 색깔 노란색

    // JFrame, 메인프레임 정의
    jf_login.setTitle("바나나톡");
    jf_login.setContentPane(jp_login); // 액자에 도화지 끼우기
    jf_login.setSize(400, 600);
    jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf_login.setLocationRelativeTo(null);// 창 가운데서 띄우기
    jf_login.setVisible(true);
  }

  public static void main(String[] args) {
    Client client = new Client();
    client.initDisplay();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (jbtn_login == obj) {
      userId = jtf_userId.getText();
      userPw = jtf_userPw.getText();
      try {
        socket = new Socket("127.0.0.1", 3000);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        // 101#아이디#패스워드
        // oos.writeObject(Protocol.TALK_IN + Protocol.seperator + userId + userPw);
        // ClientThread clientThread = new ClientThread(this);
        // clientThread.start();
      } catch (Exception e1) {
        System.out.println(e1.toString());
      }
    } else if (jbtn_join == obj) {
    }
  }
}