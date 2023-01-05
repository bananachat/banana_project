package banana_project.login;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends JFrame implements ActionListener, MouseListener {
  // 클래스 연결
  // Main main = null; //메인화면 연결
  // Memjoin memjoin = null; //회원가입 연결
  IdFind idFind = null; // 아이디찾기 연결
  PwFind pwFind = null; // 비밀번호찾기 연결

  // 서버 연결부 선언
  Socket socket = null;
  ObjectOutputStream oos = null;// 말하기
  ObjectInputStream ois = null;// 듣기
  String userId = null; // 유저가입력한 아이디
  String userPw = null; // 유저가 입력한 비밀번호

  // 화면부 선언
  JFrame jf_login = new JFrame(); // 메인 프레임
  JPanel jp_login = new JPanel(null); // 로그인 패널
  JLabel jlb_idText = new JLabel("  example@email.com"); // jtf위에 표시될 jlb
  JLabel jlb_pwText = new JLabel("  password"); // jtf위에 표시될 jlb
  JLabel jlb_findId = new JLabel(); // 아이디찾기 라벨
  JLabel jlb_findPw = new JLabel(); // 비밀번호 찾기 라벨
  // 아이디, 비밀번호 입력을 위한 JTextField (테두리선을 지우기위해 클래스 재정의)
  JTextField jtf_userId = new JTextField() { // 아이디 입력창
    @Override
    public void setBorder(Border border) {
    }
  };
  JPasswordField jtf_userPw = new JPasswordField() { // 비밀번호 입력창
    @Override
    public void setBorder(Border border) {
    }
  };
  // 폰트 설정
  Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통 폰트
  Font b14 = new Font("맑은 고딕", Font.BOLD, 14); // 볼드 폰트
  // 이미지 설정
  String imgPath = "D:\\banana_project\\app\\src\\main\\java\\banana_project\\image\\"; // 이미지파일 위치
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
    // 로그인, 패스워드 라벨 설정
    jlb_idText.setForeground(Color.gray);
    jlb_pwText.setForeground(Color.gray);
    jlb_idText.setBounds(60, 300, 270, 45);
    jlb_pwText.setBounds(60, 340, 270, 45);
    jlb_idText.setFont(p12);
    jlb_pwText.setFont(p12);
    jp_login.add(jlb_idText);
    jp_login.add(jlb_pwText);
    // 패널에 추가
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
    jbtn_login.setBackground(new Color(130, 65, 60));
    jbtn_login.setForeground(Color.white);
    jbtn_login.setFont(b14);
    jbtn_login.setBounds(200, 400, 130, 45);
    // KeyListener-엔터키 누르면 로그인 버튼 눌림
    jbtn_login.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          // main = new Main(this); //메인화면 연결
          // main.initDisplay();
        }
      }
    });
    // 회원가입버튼 정의
    jbtn_join.setBorderPainted(false);
    jbtn_join.setBackground(new Color(130, 65, 60));
    jbtn_join.setForeground(Color.white);
    jbtn_join.setFont(b14);
    jbtn_join.setBounds(60, 400, 130, 45);
    // 아이디/비밀번호 찾기 라벨버튼 정의
    jlb_findId.setText("<HTML><U>아이디 찾기</U></HTML>");
    jlb_findPw.setText("<HTML><U>비밀번호 찾기</U></HTML>");
    jlb_findId.setForeground(new ColorUIResource(135, 90, 75));
    jlb_findPw.setForeground(new ColorUIResource(135, 90, 75));
    jlb_findId.setFont(p12);
    jlb_findPw.setFont(p12);
    jlb_findId.setBounds(100, 460, 70, 20);
    jlb_findPw.setBounds(220, 460, 80, 20);
    jlb_findId.addMouseListener(this);
    jlb_findPw.addMouseListener(this);
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
    jf_login.setVisible(true);
  }

  public static void main(String[] args) {
    Client client = new Client();
    client.initDisplay();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 로그인 버튼을 눌렀을 때
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
        // main = new Main(this); //메인화면 연결
        // main.initDisplay();
      } catch (Exception e1) {
        System.out.println(e1.toString());
      }
    }
    // 회원가입 버튼을 눌렀을 때
    else if (jbtn_join == obj) {
      // memjoin = new Memjoin(this); //회원가입 연결
      // memjoin.initDisplay();
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    Object obj = e.getSource();
    // 아이디찾기 라벨 눌렀을 때
    if (jlb_findId == obj) {
      idFind = new IdFind(this); // 아이디찾기 연결
      idFind.initDisplay();
    }
    // 비밀번호찾기 라벨 눌렀을 때
    else if (jlb_findPw == obj) {
      pwFind = new PwFind(this); // 비밀번호찾기 연결
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
}