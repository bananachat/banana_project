package banana_project.client.mypage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import banana_project.client.login.Client;

public class MyPage extends JFrame implements ActionListener, FocusListener {
  /**
   * 서버 연결부 선언
   */
  Socket socket = null;
  ObjectOutputStream oos = null;// 말하기
  ObjectInputStream ois = null;// 듣기
  String userId = null; // 유저입력 아이디
  String userPw = null; // 유저입력 비밀번호
  // 이미지
  String imgPath = "./app\\src\\main\\java\\banana_project\\image\\"; // 경로
  ImageIcon img_main = new ImageIcon(imgPath + "logo_main.png"); // 메인 로고 이미지
  ImageIcon img_title = new ImageIcon(imgPath + "logo_title.png"); // 타이틀창 이미지
  ImageIcon img_info = new ImageIcon(imgPath + "mini_info.png"); // JOp 인포 이미지
  ImageIcon img_notFound = new ImageIcon(imgPath + "mini_notFound.png"); // JOp 취소 이미지
  ImageIcon img_delete = new ImageIcon(imgPath + "mini_delete.png");
  // 선언부
  JTextField jtf_userName = new JTextField("이름"); // 이름
  JTextField jtf_userHP = new JTextField("핸드폰번호"); // 핸드폰번호
  JTextField jtf_userId = new JTextField("아이디(이메일)"); // 아이디
  JTextField jtf_Nickname = new JTextField("닉네임"); // 닉네임
  JButton jbtn_checkNick = new JButton("중복확인"); // 닉네임 중복체크버튼
  JTextField jtf_userPw = new JTextField("비밀번호", 10); // 비밀번호
  JTextField jtf_userPwcheck = new JTextField("비밀번호 확인", 10); // 비밀번호 확인
  JTextField jtf_userStatMsg = new JTextField("상태메시지를 입력하세요."); // 상태메시지
  JButton jbtn_resign = new JButton("탈퇴하기"); // 탈퇴버튼
  JButton jbtn_save = new JButton("확인"); // 확인버튼
  JPanel jp_mypage = new JPanel(null);
  JLabel jlb_mypage = new JLabel("마이페이지");
  Font p12 = new Font("맑은 고딕", Font.PLAIN, 12);
  Font b12 = new Font("맑은 고딕", Font.BOLD, 12);
  Font b14 = new Font("맑은 고딕", Font.BOLD, 14);
  Font b25 = new Font("맑은 고딕", Font.BOLD, 25); // 볼드25폰트
  Font b20 = new Font("맑은 고딕", Font.BOLD, 20); // 볼드20폰트
  Client client = new Client(); // 회원가입 프레임
  // JDialog
  JDialog jd_resign = new JDialog();
  JPanel jp_resign = new JPanel(null);
  JLabel jlb_resign = new JLabel("비밀번호 확인");
  JTextField jtf_resign = new JTextField("비밀번호를 입력해주세요.");
  JButton jbtn_realresign = new JButton("탈퇴하기");

  // 생성자
  MyPage() {
  }

  MyPage(Client client) {
    this.client = client;
  }

  // 화면출력부
  public void initDisplay() {
    // 액션
    jbtn_checkNick.addActionListener(this);
    jbtn_save.addActionListener(this);
    jbtn_resign.addActionListener(this);
    jtf_Nickname.addActionListener(this);
    jtf_Nickname.addFocusListener(this);
    jtf_userPw.addActionListener(this);
    jtf_userPw.addFocusListener(this);
    jtf_userPwcheck.addActionListener(this);
    jtf_userPwcheck.addFocusListener(this);
    jtf_userStatMsg.addActionListener(this);
    jtf_userStatMsg.addFocusListener(this);
    // jdalog 액션
    jtf_resign.addFocusListener(this);
    jtf_resign.addActionListener(this);
    jbtn_realresign.addActionListener(this);
    //
    jp_mypage.add("Center", jtf_userName);
    jtf_userName.setBounds(95, 100, 180, 40);
    jtf_userName.setForeground(Color.GRAY);
    jtf_userName.setEditable(false);
    jp_mypage.add("Center", jtf_userHP);
    jtf_userHP.setBounds(95, 150, 180, 40);
    jtf_userHP.setForeground(Color.GRAY);
    jtf_userHP.setEditable(false);
    jp_mypage.add("Center", jtf_userId);
    jtf_userId.setBounds(95, 200, 180, 40);
    jtf_userId.setForeground(Color.GRAY);
    jtf_userId.setEditable(false);
    jp_mypage.add("Center", jtf_Nickname);
    jtf_Nickname.setBounds(95, 250, 180, 40);
    jtf_Nickname.setBorder(new LineBorder(Color.white, 8));
    jtf_Nickname.setForeground(Color.GRAY);
    jp_mypage.add("Center", jbtn_checkNick);
    jbtn_checkNick.setBounds(280, 250, 95, 40);
    jbtn_checkNick.setBorderPainted(false);
    jbtn_checkNick.setBackground(new Color(130, 65, 60));
    jbtn_checkNick.setForeground(Color.WHITE);
    jbtn_checkNick.setFont(b14);
    jp_mypage.add("Center", jtf_userPw);
    jtf_userPw.setBounds(95, 300, 180, 40);
    jtf_userPw.setBorder(new LineBorder(Color.white, 8));
    jtf_userPw.setForeground(Color.GRAY);
    jp_mypage.add("Center", jtf_userPwcheck);
    jtf_userPwcheck.setBounds(95, 350, 180, 40);
    jtf_userPwcheck.setBorder(new LineBorder(Color.white, 8));
    jtf_userPwcheck.setForeground(Color.GRAY);
    jp_mypage.add(jtf_userStatMsg);
    jtf_userStatMsg.setBounds(95, 400, 180, 40);
    jtf_userStatMsg.setBorder(new LineBorder(Color.white, 8));
    jtf_userStatMsg.setForeground(Color.GRAY);
    jp_mypage.add(jbtn_resign);
    jbtn_resign.setBounds(75, 470, 100, 40);
    jbtn_resign.setBorderPainted(false);
    jbtn_resign.setBackground(new Color(130, 65, 60));
    jbtn_resign.setForeground(Color.WHITE);
    jbtn_resign.setFont(b14);
    jp_mypage.add(jbtn_save);
    jbtn_save.setBounds(200, 470, 100, 40);
    jbtn_save.setBorderPainted(false);
    jbtn_save.setBackground(new Color(130, 65, 60));
    jbtn_save.setForeground(Color.WHITE);
    jbtn_save.setFont(b14);
    jp_mypage.setBackground(new Color(255, 230, 120));
    jp_mypage.add(jlb_mypage);
    jlb_mypage.setFont(b25);
    jlb_mypage.setBounds(20, 35, 150, 45);
    this.setTitle("마이페이지");
    this.setIconImage(img_title.getImage());
    this.setBackground(new Color(255, 230, 120));
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(400, 600);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.add(jp_mypage);
    // JOp 설정
    UIManager UI = new UIManager();
    UI.put("OptionPane.background", new Color(255, 230, 120));
    UI.put("Panel.background", new Color(255, 230, 120));
    UI.put("OptionPane.messageFont", b12);
    UI.put("Button.background", new Color(130, 65, 60));
    UI.put("Button.foreground", Color.white);
    UI.put("Button.font", b12);
    // jd_resign
    jp_resign.setBackground(new Color(255, 230, 120));
    jp_resign.add(jlb_resign);
    jlb_resign.setBounds(100, 100, 150, 30);
    jlb_resign.setFont(b20);
    jp_resign.add(jtf_resign);
    jtf_resign.setBounds(70, 150, 200, 40);
    jtf_resign.setBorder(new LineBorder(Color.white, 8));
    jtf_resign.setForeground(Color.GRAY);
    jp_resign.add(jbtn_realresign);
    jbtn_realresign.setBounds(120, 220, 100, 40);
    jbtn_realresign.setBorderPainted(false);
    jbtn_realresign.setBackground(new Color(130, 65, 60));
    jbtn_realresign.setForeground(Color.WHITE);
    jbtn_realresign.setFont(b14);
    jd_resign.setContentPane(jp_resign);
    jd_resign.setSize(350, 400);
    jd_resign.setTitle("탈퇴하기");
    jd_resign.setIconImage(img_delete.getImage());
    jd_resign.setVisible(false);
    jd_resign.setLocationRelativeTo(null);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 탈퇴하기 버튼을 눌렀을 때
    if (obj == jbtn_resign) {
      jd_resign.setVisible(true);
    }
    // 확인버튼을 눌렀을 때
    // jdialog 속 탈퇴하기 버튼 눌렀을 때
    if (obj == jbtn_realresign) {
      JOptionPane.showMessageDialog(this, "탈퇴가 완료되었습니다.", "탈퇴", JOptionPane.WARNING_MESSAGE, img_delete);
      this.dispose();
      jd_resign.dispose();
      client.initDisplay();
    }
  }

  @Override
  public void focusGained(FocusEvent e) {
    Object obj = e.getSource();
    // 닉네임 클릭했을 때
    if (obj == jtf_Nickname) {
      jtf_Nickname.setText("");
    }
    // 비밀번호 클릭했을 때
    if (obj == jtf_userPw) {
      jtf_userPw.setText("");
    }
    // 비밀번호 확인 클릭했을 때
    if (obj == jtf_userPwcheck) {
      jtf_userPwcheck.setText("");
    }
    // 상태메세지 클릭했을 때
    if (obj == jtf_userStatMsg) {
      jtf_userStatMsg.setText("");
    }
    // 탈퇴하기 비밀번호를 클릭했을 때
    if (obj == jtf_resign) {
      jtf_resign.setText("");
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    Object obj = e.getSource();
    // 닉네임 jtf를 공백으로 두고 벗어났을 때
    if (obj == jtf_Nickname) {
      if ("".equals(jtf_Nickname.getText())) {
        jtf_Nickname.setForeground(Color.gray);
        jtf_Nickname.setText("닉네임");
      }
    }
    // 비밀번호 jtf를 공백으로 두고 벗어났을 때
    if (obj == jtf_userPw) {
      if ("".equals(jtf_userPw.getText())) {
        jtf_userPw.setForeground(Color.GRAY);
        jtf_userPw.setText("비밀번호");
      }
    }
    // 비밀번호 확인 jtf를 공백으로 두고 벗어났을 때
    if (obj == jtf_userPwcheck) {
      if ("".equals(jtf_userPwcheck.getText())) {
        jtf_userPwcheck.setForeground(Color.gray);
        jtf_userPwcheck.setText("비밀번호 확인");
      }
    }
    // 상태메세지 jtf를 공백으로 두고 벗어났을 때
    if (obj == jtf_userStatMsg) {
      if ("".equals(jtf_userStatMsg.getText())) {
        jtf_userStatMsg.setForeground(Color.gray);
        jtf_userStatMsg.setText("상태메시지를 입력하세요.");
      }
    }
    // 탈퇴하기 비밀번호를 공백으로 두고 벗어났을 때
    if (obj == jtf_resign) {
      if ("".equals(jtf_resign.getText())) {
        jtf_resign.setForeground(Color.GRAY);
        jtf_resign.setText("비밀번호를 입력해주세요.");
      }
    }
  }

  // 메인
  public static void main(String[] args) {
    Client c = new Client();
    MyPage myPage = new MyPage(c);
    myPage.initDisplay();
  }// end of main
}// end of class