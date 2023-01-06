package banana_project.client.login;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import banana_project.client.join.MemJoin;
import banana_project.client.main.Main;
import banana_project.server.Protocol;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends JFrame implements ActionListener, MouseListener, FocusListener {
  // 서버 연결부 선언
  Socket socket = null;
  ObjectOutputStream oos = null;// 말하기
  ObjectInputStream ois = null;// 듣기
  String userId = null; // 유저가입력한 아이디
  String userPw = null; // 유저가 입력한 비밀번호
  // 테스트용 아이디와 비밀번호
  String dbId = "test";
  String dbPw = "1234";

  // 화면부 선언
  JFrame jf_login = new JFrame(); // 메인 프레임
  JPanel jp_login = new JPanel(null); // 로그인 패널
  JLabel jlb_findId = new JLabel(); // 아이디찾기 라벨
  JLabel jlb_findPw = new JLabel(); // 비밀번호 찾기 라벨
  JTextField jtf_userId = new JTextField(" example@email.com"); // 아이디 입력창
  JPasswordField jtf_userPw = new JPasswordField(" password"); // 비밀번호 입력창
  // 폰트 설정
  Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통 폰트
  Font b14 = new Font("맑은 고딕", Font.BOLD, 14); // 볼드 폰트
  // 이미지 설정
  String imgPath = "./app\\src\\main\\java\\banana_project\\image\\"; // 이미지파일 위치
  ImageIcon img_main = new ImageIcon(imgPath + "banana_main.png"); // 메인 로고 이미지
  ImageIcon img_title = new ImageIcon(imgPath + "banana_title.png"); // 타이틀창 이미지
  ImageIcon img_login = new ImageIcon(imgPath + ""); // 로그인 버튼 이미지
  ImageIcon img_join = new ImageIcon(imgPath + ""); // 회원가입 버튼 이미지
  // 버튼 설정
  JButton jbtn_login = new JButton("로그인"); // 로그인 버튼
  JButton jbtn_join = new JButton("회원가입"); // 회원가입 버튼
  JButton jbtn_main = new JButton(img_main); // 메인 로그인 이미지 붙이기용 버튼

  // 화면부 메소드
  public void initDisplay() {
    // 이벤트리스너 연결
    jbtn_login.addActionListener(this);
    jbtn_join.addActionListener(this);
    jtf_userId.addActionListener(this);
    jtf_userPw.addActionListener(this);
    jtf_userId.addFocusListener(this);
    jtf_userPw.addFocusListener(this);
    jlb_findId.addMouseListener(this);
    jlb_findPw.addMouseListener(this);

    // 패널에 추가
    jp_login.add(jtf_userId);
    jp_login.add(jtf_userPw);
    jp_login.add(jbtn_login);
    jp_login.add(jbtn_join);
    jp_login.add(jlb_findId);
    jp_login.add(jlb_findPw);
    jp_login.add(jbtn_main);
    // 아이디 비밀번호 입력창 설정, 비밀번호 암호 *로 표시
    jtf_userId.setForeground(Color.gray);
    jtf_userPw.setForeground(Color.gray);
    jtf_userId.setBounds(60, 300, 270, 45);
    jtf_userPw.setBounds(60, 360, 270, 45);
    // 입력창에 아무것도 없는 상태로 호출된 경우
    if ("".equals(jtf_userId.getText())) {
      jtf_userId.setText(" example@email.com");
    }
    if ("".equals(jtf_userPw.getText())) {
      jtf_userPw.setText(" password");
    }
    // jtf 보더라인 설정
    jtf_userId.setBorder(new LineBorder(Color.white, 8));
    jtf_userPw.setBorder(new LineBorder(Color.white, 8));
    // jtf_userPw.setEchoChar('*');
    // 로그인 버튼 정의
    jbtn_login.setBorderPainted(false);
    jbtn_login.setBackground(new Color(130, 65, 60));
    jbtn_login.setForeground(Color.white);
    jbtn_login.setFont(b14);
    jbtn_login.setBounds(200, 420, 130, 45);
    // 회원가입버튼 정의
    jbtn_join.setBorderPainted(false);
    jbtn_join.setBackground(new Color(130, 65, 60));
    jbtn_join.setForeground(Color.white);
    jbtn_join.setFont(b14);
    jbtn_join.setBounds(60, 420, 130, 45);
    // 아이디/비밀번호 찾기 라벨버튼 정의
    jlb_findId.setText("<HTML><U>아이디 찾기</U></HTML>");
    jlb_findPw.setText("<HTML><U>비밀번호 찾기</U></HTML>");
    jlb_findId.setForeground(new ColorUIResource(135, 90, 75));
    jlb_findPw.setForeground(new ColorUIResource(135, 90, 75));
    jlb_findId.setFont(p12);
    jlb_findPw.setFont(p12);
    jlb_findId.setBounds(100, 480, 70, 20);
    jlb_findPw.setBounds(220, 480, 80, 20);
    // 바나나 이미지 정의
    jbtn_main.setBackground(new Color(255, 230, 120));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(60, 35, 270, 250); // 바나나 이미지 고정
    jp_login.setBackground(new Color(255, 230, 120)); // 도화지 색깔 노란색
    // JFrame, 메인프레임 정의
    jf_login.setTitle("바나나톡");
    jf_login.setIconImage(img_title.getImage());
    jf_login.setContentPane(jp_login); // 액자에 도화지 끼우기
    jf_login.setSize(400, 600);
    jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf_login.setLocationRelativeTo(null);// 창 가운데서 띄우기
    jf_login.setResizable(false);
    jf_login.setVisible(true);
  }

  // 서버연결부 메소드
  public void init() {
    try {
      socket = new Socket("127.0.0.1", 3000);
      oos = new ObjectOutputStream(socket.getOutputStream()); // 말하기
      ois = new ObjectInputStream(socket.getInputStream()); // 듣기
      ClientThread clientThread = new ClientThread(this); // 클라이언트 스레드와 연결
      clientThread.start(); // clientThread의 run() 호출
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void main(String[] args) {
    Client client = new Client();
    client.initDisplay();
    client.init();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 로그인 버튼을 눌렀을 때
    if (obj == jbtn_login || obj == jtf_userId || obj == jtf_userPw) {
      userId = jtf_userId.getText();
      userPw = jtf_userPw.getText();
      // 아이디를 입력하지 않았을 경우
      if ("".equals(userId) || " example@email.com".equals(userId)) {
        JOptionPane.showMessageDialog(jf_login, "이메일을 입력해주세요", "info", JOptionPane.WARNING_MESSAGE);
      }
      // 비밀번호를 입력하지 않았을 경우
      else if ("".equals(userPw) || " password".equals(userPw)) {
        JOptionPane.showMessageDialog(jf_login, "비밀번호를 입력해주세요", "info", JOptionPane.WARNING_MESSAGE);
      } else {
        try {
          // 로그인 시도시 100#아이디#패스워드 형태로 서버에 전달
          oos.writeObject(Protocol.CLIENT_START
              + Protocol.seperator + userId
              + Protocol.seperator + userPw);
        } catch (Exception e2) {
          e2.printStackTrace();
        }
        // 테스트용 if문
        if (userId.equals(dbId) && userPw.equals(dbPw)) {
          jf_login.setVisible(false);
          Main main = new Main();
          main.initDisplay();
        }
      }
    }
    // 회원가입 버튼을 눌렀을 때
    else if (obj == jbtn_join) {
      jf_login.setVisible(false);
      MemJoin memJoin = new MemJoin();
      memJoin.initDisplay();
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    Object obj = e.getSource();
    // 아이디찾기 라벨 눌렀을 때
    if (obj == jlb_findId) {
      IdFind idFind = new IdFind(this);
      idFind.initDisplay();
    }
    // 비밀번호찾기 라벨 눌렀을 때
    else if (obj == jlb_findPw) {
      PwFind pwFind = new PwFind(this);
      pwFind.initDisplay();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void focusGained(FocusEvent e) {
    Object obj = e.getSource();
    // 아이디 jtf를 클릭했을 때
    if (obj == jtf_userId) {
      jtf_userId.setForeground(Color.black);
      if (" example@email.com".equals(jtf_userId.getText())) {
        jtf_userId.setText("");
      }
    }
    // 비밀번호 jtf를 클릭했을 때
    else if (obj == jtf_userPw) {
      jtf_userPw.setForeground(Color.black);
      if (" password".equals(jtf_userPw.getText())) {
        jtf_userPw.setText("");
      }
    }

  }

  @Override
  public void focusLost(FocusEvent e) {
    Object obj = e.getSource();
    // 아이디 jtf를 공백으로두고 벗어났을 때
    if (obj == jtf_userId) {
      if ("".equals(jtf_userId.getText())) {
        jtf_userId.setForeground(Color.gray);
        jtf_userId.setText(" example@email.com");
      }
    }
    // 비밀번호 jtf를 공백으로두고 벗어났을 때
    else if (obj == jtf_userPw) {
      if ("".equals(jtf_userPw.getText())) {
        jtf_userPw.setForeground(Color.gray);
        jtf_userPw.setText(" password");
      }
    }
  }
}