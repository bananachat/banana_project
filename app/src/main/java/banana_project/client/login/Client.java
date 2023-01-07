package banana_project.client.login;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
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
import java.util.regex.Pattern;

public class Client extends JFrame implements ActionListener, MouseListener, FocusListener {
  /**
   * 서버 연결부 선언
   */
  Socket socket = null;
  ObjectOutputStream oos = null;// 말하기
  ObjectInputStream ois = null;// 듣기
  String userId = null; // 유저입력 아이디
  String userPw = null; // 유저입력 비밀번호
  // 테스트용 아이디와 비밀번호
  String dbId = "banana@email.com";
  String dbPw = "1234";

  /**
   * 화면부 선언
   */
  JPanel jp_login = new JPanel(null);
  // 이미지
  String imgPath = "./app\\src\\main\\java\\banana_project\\image\\"; // 경로
  ImageIcon img_main = new ImageIcon(imgPath + "logo_main.png"); // 메인 로고 이미지
  ImageIcon img_title = new ImageIcon(imgPath + "logo_title.png"); // 타이틀창 이미지
  ImageIcon img_info = new ImageIcon(imgPath + "mini_info.png"); // JOp 인포 이미지
  ImageIcon img_notFound = new ImageIcon(imgPath + "mini_notFound.png"); // JOp 취소 이미지
  // 폰트
  Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통12 폰트
  Font b12 = new Font("맑은 고딕", Font.BOLD, 12); // 볼드12 폰트
  Font b14 = new Font("맑은 고딕", Font.BOLD, 14); // 볼드14 폰트
  // Jtf
  JTextField jtf_userId = new JTextField(" example@email.com"); // 아이디 입력창
  JPasswordField jtf_userPw = new JPasswordField(" password"); // 비밀번호 입력창
  // Jbtn
  JButton jbtn_login = new JButton("로그인"); // 로그인 버튼
  JButton jbtn_join = new JButton("회원가입"); // 회원가입 버튼
  JButton jbtn_main = new JButton(img_main); // 메인 로고용 버튼
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
    jtf_userPw.setForeground(Color.gray);
    jtf_userId.setBounds(60, 300, 270, 45);
    jtf_userPw.setBounds(60, 360, 270, 45);
    jtf_userId.setBorder(new LineBorder(Color.white, 8));
    jtf_userPw.setBorder(new LineBorder(Color.white, 8));
    // 로그인 버튼 설정
    jbtn_login.setBorderPainted(false);
    jbtn_login.setBackground(new Color(130, 65, 60));
    jbtn_login.setForeground(Color.white);
    jbtn_login.setFont(b14);
    jbtn_login.setBounds(200, 420, 130, 45);
    // 회원가입버튼 설정
    jbtn_join.setBorderPainted(false);
    jbtn_join.setBackground(new Color(130, 65, 60));
    jbtn_join.setForeground(Color.white);
    jbtn_join.setFont(b14);
    jbtn_join.setBounds(60, 420, 130, 45);
    // Jlb
    jlb_findId.setText("<HTML><U>아이디 찾기</U></HTML>");
    jlb_findPw.setText("<HTML><U>비밀번호 찾기</U></HTML>");
    jlb_findId.setForeground(new Color(135, 90, 75));
    jlb_findPw.setForeground(new Color(135, 90, 75));
    jlb_findId.setFont(p12);
    jlb_findPw.setFont(p12);
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
    this.setIconImage(img_title.getImage()); // 타이틀창 이미지
    this.setContentPane(jp_login); // 액자에 도화지 끼우기
    this.setSize(400, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null); // 창 가운데서 띄우기
    this.setResizable(false);
    this.setVisible(true);
    // JOp 설정
    UIManager UI = new UIManager();
    UI.put("OptionPane.background", new Color(255, 230, 120));
    UI.put("Panel.background", new Color(255, 230, 120));
    UI.put("OptionPane.messageFont", b12);
    UI.put("Button.background", new Color(130, 65, 60));
    UI.put("Button.foreground", Color.white);
    UI.put("Button.font", b12);
  }

  /**
   * 서버연결부 메소드
   */
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

  /**
   * ActionListener 메소드
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 로그인 버튼을 눌렀을 때
    if (obj == jbtn_login || obj == jtf_userId || obj == jtf_userPw) {
      userId = jtf_userId.getText();
      userPw = jtf_userPw.getText();
      String idCheck = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"; // 이메일 형식
      // 아이디를 입력하지 않았을 경우
      if ("".equals(userId) || " example@email.com".equals(userId)) {
        JOptionPane.showMessageDialog(this, "이메일을 입력해주세요", "로그인", JOptionPane.WARNING_MESSAGE, img_info);
      }
      // 이메일 형식이 아닐 경우
      else if (!Pattern.matches(idCheck, userId)) {
        JOptionPane.showMessageDialog(this, "example@email.com 형식으로 입력해주세요", "로그인",
            JOptionPane.WARNING_MESSAGE, img_info);
      }
      // 비밀번호를 입력하지 않았을 경우
      else if ("".equals(userPw) || " password".equals(userPw)) {
        JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요", "로그인", JOptionPane.WARNING_MESSAGE, img_info);
      }
      // 형식에 맞게 입력했을경우 DB에서 확인
      else {
        try {
          // 로그인 시도시 100#아이디#패스워드 형태로 서버에 전달
          oos.writeObject(Protocol.CLIENT_START
              + Protocol.seperator + userId
              + Protocol.seperator + userPw);
        } catch (Exception e2) {
          e2.printStackTrace();
        }
        // 테스트용 if문
        // 아이디, 비번이 맞을 경우
        if (userId.equals(dbId) && userPw.equals(dbPw)) {
          Main main = new Main();
          main.initDisplay();
        }
        // 아이디는 맞는데 비번을 틀릴 경우
        else if (userId.equals(dbId) && !userPw.equals(dbPw)) {
          JOptionPane.showMessageDialog(this, "비밀번호가 틀렸습니다..", "로그인", JOptionPane.ERROR_MESSAGE,
              img_notFound);
        }
        // 전부 틀릴 경우
        else {
          JOptionPane.showMessageDialog(this, "계정을 찾을 수 없습니다.", "로그인", JOptionPane.ERROR_MESSAGE,
              img_notFound);
        }
      }
    }
    // 회원가입 버튼을 눌렀을 때
    else if (obj == jbtn_join) {
      this.dispose();
      // MemJoin memJoin = new MemJoin(this);
      // memJoin.initDisplay();
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
      IdFind idFind = new IdFind(this);
      idFind.initDisplay();
    }
    // 비밀번호찾기 라벨 눌렀을 때
    else if (obj == jlb_findPw) {
      PwFind pwfind = new PwFind(this);
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