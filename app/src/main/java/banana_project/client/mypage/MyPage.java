package banana_project.client.mypage;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.client.login.Client;
import banana_project.client.main.Main;

public class MyPage implements ActionListener, FocusListener {
  /**
   * 서버 연결부 선언
   */
  Client client = null;// 회원가입 프레임
  Main main = null; // 메인화면 프레임
  boolean nickTnF = false;

  /**
   * 화면부 선언
   */
  // 이미지, 폰트, JOp 세팅 불러오기
  SetImg setImage = new SetImg();
  SetFontNJOp setFontNJOp = new SetFontNJOp();
  // JP
  JPanel jp_mypage = new JPanel(null);
  // Jtf
  JTextField jtf_userName = new JTextField("이름"); // 이름
  JTextField jtf_userHP = new JTextField("핸드폰번호"); // 핸드폰번호
  JTextField jtf_userId = new JTextField("아이디(이메일)"); // 아이디
  JTextField jtf_Nickname = new JTextField("닉네임"); // 닉네임
  JPasswordField jtf_userPw = new JPasswordField("password");
  JPasswordField jtf_userPwcheck = new JPasswordField("password");
  JTextField jtf_userStatMsg = new JTextField("상태메시지를 입력하세요.");
  // Jbtn
  JButton jbtn_checkNick = new JButton("중복확인"); // 닉네임 중복체크버튼
  JButton jbtn_resign = new JButton("탈퇴하기"); // 탈퇴 버튼
  JButton jbtn_save = new JButton("확인"); // 확인 버튼
  // Jlb
  JLabel jlb_mypage = new JLabel("마이페이지");
  // JDialog
  JDialog jd_resign = new JDialog();
  JPanel jp_resign = new JPanel(null);
  JPasswordField jtf_resign = new JPasswordField();
  JButton jbtn_realresign = new JButton("탈퇴하기");
  JLabel jlb_resign = new JLabel("비밀번호 확인");

  /**
   * 생성자
   * 
   * @param client
   */
  public MyPage(Client client) {
    this.client = client;
  }

  /**
   * 화면부 메소드
   */
  public void initDisplay() {
    // 이벤트리스너
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
    // jdalog 이벤트리스너
    jtf_resign.addFocusListener(this);
    jtf_resign.addActionListener(this);
    jbtn_realresign.addActionListener(this);
    // 패널에 추가
    jp_mypage.add(jtf_userName);
    jp_mypage.add(jtf_userHP);
    jp_mypage.add(jtf_userId);
    jp_mypage.add(jtf_Nickname);
    jp_mypage.add(jtf_userPw);
    jp_mypage.add(jtf_userPwcheck);
    jp_mypage.add(jtf_userStatMsg);
    jp_mypage.add(jbtn_checkNick);
    jp_mypage.add(jbtn_resign);
    jp_mypage.add(jbtn_save);
    jp_mypage.add(jlb_mypage);
    // Jtf 설정
    jtf_userName.setForeground(Color.GRAY);
    jtf_userHP.setForeground(Color.GRAY);
    jtf_userId.setForeground(Color.GRAY);
    jtf_Nickname.setForeground(Color.GRAY);
    jtf_userPw.setForeground(Color.lightGray);
    jtf_userPwcheck.setForeground(Color.lightGray);
    jtf_userStatMsg.setForeground(Color.GRAY);
    jtf_userName.setBounds(95, 100, 180, 40);
    jtf_userHP.setBounds(95, 150, 180, 40);
    jtf_userId.setBounds(95, 200, 180, 40);
    jtf_Nickname.setBounds(95, 250, 180, 40);
    jtf_userPw.setBounds(95, 300, 180, 40);
    jtf_userPwcheck.setBounds(95, 350, 180, 40);
    jtf_userStatMsg.setBounds(95, 400, 180, 40);
    jtf_userName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userHP.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userId.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_Nickname.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userPw.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userPwcheck.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userStatMsg.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userName.setEditable(false);
    jtf_userHP.setEditable(false);
    jtf_userId.setEditable(false);
    // 닉네임 중복확인 버튼 설정
    jbtn_checkNick.setBorderPainted(false);
    jbtn_checkNick.setBackground(new Color(130, 65, 60));
    jbtn_checkNick.setForeground(Color.WHITE);
    jbtn_checkNick.setFont(setFontNJOp.b14);
    jbtn_checkNick.setBounds(280, 250, 95, 40);
    // 탈퇴하기 버튼 설정
    jbtn_resign.setBorderPainted(false);
    jbtn_resign.setBackground(new Color(130, 65, 60));
    jbtn_resign.setForeground(Color.WHITE);
    jbtn_resign.setFont(setFontNJOp.b14);
    jbtn_resign.setBounds(75, 470, 100, 40);
    // 확인 버튼 설정
    jbtn_save.setBorderPainted(false);
    jbtn_save.setBackground(new Color(130, 65, 60));
    jbtn_save.setForeground(Color.WHITE);
    jbtn_save.setFont(setFontNJOp.b14);
    jbtn_save.setBounds(200, 470, 100, 40);
    // Jlb 설정
    jlb_mypage.setFont(setFontNJOp.b25);
    jlb_mypage.setBounds(20, 35, 150, 45);
    // JP 설정
    jp_mypage.setBackground(new Color(255, 230, 120));
    // JF 설정
    client.setTitle("마이페이지");
    client.setContentPane(jp_mypage);
    client.setVisible(true);

    // Jdg_resign 설정
    // 패널에 추가
    jp_resign.add(jtf_resign);
    jp_resign.add(jbtn_realresign);
    jp_resign.add(jlb_resign);
    // Jtf 설정
    jlb_resign.setBounds(100, 100, 150, 30);
    jlb_resign.setFont(setFontNJOp.b20);
    jtf_resign.setBounds(70, 150, 200, 40);
    jtf_resign.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jbtn_realresign.setBounds(120, 220, 100, 40);
    jbtn_realresign.setBorderPainted(false);
    jbtn_realresign.setBackground(new Color(130, 65, 60));
    jbtn_realresign.setForeground(Color.WHITE);
    jbtn_realresign.setFont(setFontNJOp.b14);
    // JP 설정
    jp_resign.setBackground(new Color(255, 230, 120));
    // Jdg 설정
    jd_resign.setTitle("회원탈퇴");
    jd_resign.setIconImage(setImage.img_title.getImage());
    jd_resign.setContentPane(jp_resign);
    jd_resign.setSize(350, 400);
    jd_resign.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    jd_resign.setLocationRelativeTo(client);
    jd_resign.setResizable(false);
    jd_resign.setVisible(false);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 중복확인 버튼 눌렀을 때
    if (obj == jbtn_checkNick) {
      String userNick = jtf_Nickname.getText();
      // try {
      // client.oos.writeObject(Protocol.NICK_CHK
      // + Protocol.seperator + userNick);
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      nickTnF = true;
      JOptionPane.showMessageDialog(client, "중복확인이 완료되었습니다.", "마이페이지", JOptionPane.WARNING_MESSAGE,
          setImage.img_confirm);
    }
    // 확인버튼을 눌렀을 때
    if (obj == jbtn_save) {
      String userNick = jtf_Nickname.getText();
      String pwFirst = jtf_userPw.getText();
      String pwSecond = jtf_userPwcheck.getText();
      String message = jtf_userStatMsg.getText();
      if (!nickTnF) {
        JOptionPane.showMessageDialog(client, "닉네임 중복확인을 해주세요", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
            setImage.img_info);
      }
      // 비밀번호 1, 2가 다를 경우
      else if (!pwFirst.equals(pwSecond)) {
        JOptionPane.showMessageDialog(client, "비밀번호가 일치하지 않습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
            setImage.img_notFound);
      } else if (!userNick.equals(obj) || !pwFirst.equals(obj) || !message.equals(obj)) {
        // DB로 정보 업데이트
        JOptionPane.showMessageDialog(client, "변경이 완료되었습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
            setImage.img_confirm);
        client.setContentPane(client.main.jp_main);
        client.setTitle("친구 목록");
        client.revalidate();
      } else {
        client.setContentPane(client.main.jp_main);
        client.setTitle("친구 목록");
        client.revalidate();
      }
    }
    // 탈퇴하기 버튼을 눌렀을 때
    else if (obj == jbtn_resign) {
      jd_resign.setVisible(true);
    }
    // JDg 속 탈퇴하기 버튼 눌렀을 때
    else if (obj == jbtn_realresign) {
      if (jtf_resign.getText().equals(obj)) {
        JOptionPane.showMessageDialog(jd_resign, "탈퇴가 완료되었습니다.", "회원탈퇴", JOptionPane.WARNING_MESSAGE,
            setImage.img_delete);
        jd_resign.dispose();
        client.setContentPane(client.jp_login);
        client.setTitle("바나나톡");
        client.revalidate();
      } else {
        JOptionPane.showMessageDialog(jd_resign, "비밀번호가 일치하지 않습니다.", "회원탈퇴", JOptionPane.WARNING_MESSAGE,
            setImage.img_notFound);
      }
    }
  }

  @Override
  public void focusGained(FocusEvent e) {
    Object obj = e.getSource();
    // 비밀번호 클릭했을 때
    if (obj == jtf_userPw) {
      jtf_userPw.setText("");
    }
    // 비밀번호 확인 클릭했을 때
    if (obj == jtf_userPwcheck) {
      jtf_userPwcheck.setText("");
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    Object obj = e.getSource();
    // 비밀번호 jtf를 공백으로 두고 벗어났을 때
    if (obj == jtf_userPw) {
      if ("".equals(jtf_userPw.getText())) {
        jtf_userPw.setForeground(Color.GRAY);
        jtf_userPw.setText("password");
      }
    }
    // 비밀번호 확인 jtf를 공백으로 두고 벗어났을 때
    if (obj == jtf_userPwcheck) {
      if ("".equals(jtf_userPwcheck.getText())) {
        jtf_userPwcheck.setForeground(Color.gray);
        jtf_userPwcheck.setText("password");
      }
    }
  }

  /**
   * 테스트용메인
   * 
   * @param args
   */
  public static void main(String[] args) {
    Client c = new Client();
    MyPage myPage = new MyPage(c);
    c.initDisplay();
    myPage.initDisplay();
  }
}