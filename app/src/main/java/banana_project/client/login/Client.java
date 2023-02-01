package banana_project.client.login;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.client.join.MemJoin;
import banana_project.client.main.Main;
import banana_project.server.thread.Protocol;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.regex.Pattern;

public class Client extends JFrame implements ActionListener, MouseListener, FocusListener {
  /**
   * 서버 연결부 선언
   */
  Socket socket = null;
  public ObjectOutputStream oos = null;// 말하기
  ObjectInputStream ois = null;// 듣기
  ClientThread clientThread = null;
  MemJoin memJoin = null;
  IdFind idFind = null;
  PwFind pwfind = null;
  public Main main = null;

  /**
   * 화면부 선언
   */
  // 이미지, 폰트, JOp 세팅 불러오기
  SetImg setImage = new SetImg();
  SetFontNJOp setFontNJOp = new SetFontNJOp();

  // JP
  public JPanel jp_login = new JPanel(null);

  // Jtf
  JTextField jtf_userId = new JTextField("example@email.com"); // 아이디 입력창
  JPasswordField jtf_userPw = new JPasswordField("password"); // 비밀번호 입력창

  // Jbtn
  JButton jbtn_login = new JButton("로그인"); // 로그인 버튼
  JButton jbtn_join = new JButton("회원가입"); // 회원가입 버튼
  JButton jbtn_main = new JButton(setImage.img_main); // 메인 로고용 버튼

  // Jlb
  JLabel jlb_findId = new JLabel();
  JLabel jlb_findPw = new JLabel();

  /**
   * 화면부 메소드
   */
  public void initDisplay() {
    // 이벤트리스너
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

    // Jtf 설정
    jtf_userId.setForeground(Color.gray);
    jtf_userPw.setForeground(Color.lightGray);
    jtf_userId.setBounds(60, 300, 270, 45);
    jtf_userPw.setBounds(60, 360, 270, 45);
    jtf_userId.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userPw.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

    // 로그인 버튼 설정
    jbtn_login.setBorderPainted(false);
    jbtn_login.setBackground(new Color(130, 65, 60));
    jbtn_login.setForeground(Color.white);
    jbtn_login.setFont(setFontNJOp.b14);
    jbtn_login.setBounds(200, 420, 130, 45);

    // 회원가입버튼 설정
    jbtn_join.setBorderPainted(false);
    jbtn_join.setBackground(new Color(130, 65, 60));
    jbtn_join.setForeground(Color.white);
    jbtn_join.setFont(setFontNJOp.b14);
    jbtn_join.setBounds(60, 420, 130, 45);

    // Jlb
    jlb_findId.setText("<HTML><U>아이디 찾기</U></HTML>");
    jlb_findPw.setText("<HTML><U>비밀번호 찾기</U></HTML>");
    jlb_findId.setForeground(new Color(135, 90, 75));
    jlb_findPw.setForeground(new Color(135, 90, 75));
    jlb_findId.setFont(setFontNJOp.p12);
    jlb_findPw.setFont(setFontNJOp.p12);
    jlb_findId.setBounds(100, 480, 70, 20);
    jlb_findPw.setBounds(220, 480, 80, 20);

    // 로고 이미지 설정
    jbtn_main.setBackground(new Color(255, 230, 120));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(60, 35, 270, 250); // 바나나 이미지 고정

    // Jp 설정
    jp_login.setBackground(new Color(255, 230, 120));

    // JF 설정
    this.setTitle("바나나톡");
    this.setIconImage(setImage.img_title.getImage()); // 타이틀창 이미지
    this.setContentPane(jp_login); // 액자에 도화지 끼우기
    this.setSize(400, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null); // 창 가운데서 띄우기
    this.setResizable(false);
    this.setVisible(true);
  }

  /**
   * 서버연결부 메소드
   */
  public void init() {
    try {
       socket = new Socket("192.168.10.72", 3000);
      //socket = new Socket("127.0.0.1", 3000);
      //socket = new Socket("192.168.10.70", 3000);
      oos = new ObjectOutputStream(socket.getOutputStream()); // 말하기
      ois = new ObjectInputStream(socket.getInputStream()); // 듣기
      clientThread = new ClientThread(this); // 클라이언트 스레드와 연결
      clientThread.start(); // clientThread의 run() 호출
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }

  /**
   * 로그인성공
   * 
   * @param userId
   * @param userNick
   */
  public void login_s(String userId, String userNick) {
    jtf_userId.setText("example@email.com");
    jtf_userPw.setText("password");
    jtf_userId.setForeground(Color.gray);
    jtf_userPw.setForeground(Color.lightGray);
    main = new Main(this, userId, userNick);
    main.initDisplay();
  }

  /**
   * 비밀번호 틀림
   */
  public void wrong_pw() {
    JOptionPane.showMessageDialog(this, "비밀번호가 틀렸습니다.", "로그인", JOptionPane.ERROR_MESSAGE,
        setImage.img_notFound);
  }

  /**
   * 비밀번호 시도횟수 초과
   */
  public void over_fail_cnt() {
    JOptionPane.showMessageDialog(this, "로그인 시도 횟수가 초과되었습니다.", "로그인", JOptionPane.ERROR_MESSAGE,
        setImage.img_notFound);
  }

  /**
   * 아이디 틀림(계정없음)
   */
  public void wrong_id() {
    JOptionPane.showMessageDialog(this, "계정을 찾을 수 없습니다.", "로그인", JOptionPane.ERROR_MESSAGE,
        setImage.img_notFound);
  }

  /**
   * ActionListener 메소드
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 로그인 버튼을 눌렀을 때
    if (obj == jbtn_login || obj == jtf_userId || obj == jtf_userPw) {
      String userId = jtf_userId.getText();
      String userPw = jtf_userPw.getText();
      // 아이디 정규식
      String idCheck = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"; // 이메일 형식
      // 아이디를 입력하지 않았을 경우
      if ("".equals(userId) || "example@email.com".equals(userId)) {
        JOptionPane.showMessageDialog(this, "이메일을 입력해주세요.", "로그인", JOptionPane.WARNING_MESSAGE, setImage.img_info);
      }
      // 이메일 형식이 아닐 경우
      else if (!Pattern.matches(idCheck, userId)) {
        JOptionPane.showMessageDialog(this, "example@email.com 형식으로 입력해주세요.", "로그인",
            JOptionPane.WARNING_MESSAGE, setImage.img_info);
      }
      // 비밀번호를 입력하지 않았을 경우
      else if ("".equals(userPw) || "password".equals(userPw)) {
        JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요.", "로그인", JOptionPane.WARNING_MESSAGE, setImage.img_info);
      }
      // 형식에 맞게 입력했을경우 DB에서 확인
      else {
        try {
          // 로그인 시도 -> 100#아이디#비밀번호
          oos.writeObject(Protocol.CLIENT_START
              + Protocol.seperator + userId
              + Protocol.seperator + userPw);
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
    }

    // 회원가입 버튼을 눌렀을 때
    else if (obj == jbtn_join) {
      jtf_userId.setText("example@email.com");
      jtf_userPw.setText("password");
      jtf_userId.setForeground(Color.gray);
      jtf_userPw.setForeground(Color.lightGray);
      memJoin = new MemJoin(this);
      memJoin.initDisplay();
    }
  }

  /**
   * MouseListener 메소드
   */
  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    Object obj = e.getSource();
    // 아이디찾기 라벨 눌렀을 때
    if (obj == jlb_findId) {
      jtf_userId.setText("example@email.com");
      jtf_userPw.setText("password");
      jtf_userId.setForeground(Color.gray);
      jtf_userPw.setForeground(Color.lightGray);
      idFind = new IdFind(this);
      idFind.initDisplay();
    }

    // 비밀번호찾기 라벨 눌렀을 때
    else if (obj == jlb_findPw) {
      jtf_userId.setText("example@email.com");
      jtf_userPw.setText("password");
      jtf_userId.setForeground(Color.gray);
      jtf_userPw.setForeground(Color.lightGray);
      pwfind = new PwFind(this);
      pwfind.initDisplay();
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

  /**
   * FocusListener 메소드
   */
  @Override
  public void focusGained(FocusEvent e) {
    Object obj = e.getSource();
    // 아이디 jtf를 클릭했을 때
    if (obj == jtf_userId) {
      jtf_userId.setForeground(Color.black);
      if ("example@email.com".equals(jtf_userId.getText())) {
        jtf_userId.setText("");
      }
    }

    // 비밀번호 jtf를 클릭했을 때
    else if (obj == jtf_userPw) {
      jtf_userPw.setForeground(Color.black);
      if ("password".equals(jtf_userPw.getText())) {
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
        jtf_userId.setText("example@email.com");
      }
    }

    // 비밀번호 jtf를 공백으로두고 벗어났을 때
    else if (obj == jtf_userPw) {
      if ("".equals(jtf_userPw.getText())) {
        jtf_userPw.setForeground(Color.lightGray);
        jtf_userPw.setText("password");
      }
    }
  }

  /**
   * 메인메소드
   * 
   * @param args
   */
  public static void main(String[] args) {
    Client client = new Client();
    client.initDisplay();
    client.init();
  }
}