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
  JTextField jtf_userName = new JTextField("바나나"); // 이름 입력창
  JTextField jtf_userHp = new JTextField("010-1111-1111"); // 핸드폰번호 입력창
  JTextField jtf_userId = new JTextField("banana@email.com"); // 아이디 입력창
  JTextField jtf_nickName = new JTextField(); // 닉네임 입력창
  JPasswordField jtf_userPw = new JPasswordField(); // 비밀번호 입력창
  JPasswordField jtf_userPwRe = new JPasswordField(); // 비밀번호 확인 입력창
  JTextField jtf_userStatMsg = new JTextField("상태메시지를 입력해주세요.", 13); // 상태메시지 입력창
  // Jbtn
  JButton jbtn_checkNick = new JButton("중복확인"); // 닉네임 중복체크버튼
  JButton jbtn_resign = new JButton("탈퇴하기"); // 탈퇴 버튼
  JButton jbtn_save = new JButton("확인"); // 확인 버튼
  // Jlb
  JLabel jlb_name = new JLabel("이름");
  JLabel jlb_hp = new JLabel("핸드폰번호");
  JLabel jlb_id = new JLabel("아이디");
  JLabel jlb_nickName = new JLabel("닉네임");
  JLabel jlb_pw = new JLabel("새로운 비밀번호");
  JLabel jlb_pwRe = new JLabel("비밀번호 확인");
  JLabel jlb_pwtxt = new JLabel("숫자, 영문자포함 8~16자리");
  JLabel jlb_userStatMsg = new JLabel("상태메시지");

  // JDialog
  JDialog jd_resign = new JDialog();
  // JP
  JPanel jp_resign = new JPanel(null);
  // Jtf
  JTextField jtf_resignId = new JTextField();
  JPasswordField jtf_resignPw = new JPasswordField();
  // Jbtn
  JButton jbtn_back = new JButton("돌아가기");
  JButton jbtn_realresign = new JButton("탈퇴하기");
  // Jlb
  JLabel jlb_resignId = new JLabel("아이디");
  JLabel jlb_resignPw = new JLabel("비밀번호");

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
    jtf_nickName.addActionListener(this);
    jtf_userPw.addActionListener(this);
    jtf_userPwRe.addActionListener(this);
    jtf_userStatMsg.addActionListener(this);
    jtf_userStatMsg.addFocusListener(this);
    // 패널에 추가
    jp_mypage.add(jtf_userName);
    jp_mypage.add(jtf_userHp);
    jp_mypage.add(jtf_userId);
    jp_mypage.add(jtf_nickName);
    jp_mypage.add(jtf_userPw);
    jp_mypage.add(jtf_userPwRe);
    jp_mypage.add(jtf_userStatMsg);
    jp_mypage.add(jbtn_checkNick);
    jp_mypage.add(jbtn_resign);
    jp_mypage.add(jbtn_save);
    jp_mypage.add(jlb_name);
    jp_mypage.add(jlb_hp);
    jp_mypage.add(jlb_id);
    jp_mypage.add(jlb_nickName);
    jp_mypage.add(jlb_pw);
    jp_mypage.add(jlb_pwRe);
    jp_mypage.add(jlb_pwtxt);
    jp_mypage.add(jlb_userStatMsg);
    // Jtf 설정
    jtf_userName.setForeground(Color.gray);
    jtf_userId.setForeground(Color.gray);
    jtf_userHp.setForeground(Color.gray);
    jtf_nickName.setForeground(Color.black);
    jtf_userPw.setForeground(Color.black);
    jtf_userPwRe.setForeground(Color.black);
    jtf_userStatMsg.setForeground(Color.gray);
    jtf_userName.setBounds(105, 55, 183, 40);
    jtf_userHp.setBounds(105, 110, 183, 40);
    jtf_userId.setBounds(105, 165, 183, 40);
    jtf_nickName.setBounds(105, 220, 183, 40);
    jtf_userPw.setBounds(105, 275, 183, 40);
    jtf_userPwRe.setBounds(105, 330, 183, 40);
    jtf_userStatMsg.setBounds(105, 400, 183, 40);
    jtf_userName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userId.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userHp.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_nickName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userPw.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userPwRe.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userStatMsg.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userName.setEditable(false);
    jtf_userHp.setEditable(false);
    jtf_userId.setEditable(false);
    // 닉네임 중복확인 버튼 설정
    jbtn_checkNick.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jbtn_checkNick.setBorderPainted(false);
    jbtn_checkNick.setBackground(new Color(130, 65, 60));
    jbtn_checkNick.setForeground(Color.WHITE);
    jbtn_checkNick.setFont(setFontNJOp.b12);
    jbtn_checkNick.setBounds(298, 222, 75, 36);
    // 탈퇴하기 버튼 설정
    jbtn_resign.setBorderPainted(false);
    jbtn_resign.setBackground(new Color(130, 65, 60));
    jbtn_resign.setForeground(Color.WHITE);
    jbtn_resign.setFont(setFontNJOp.b14);
    jbtn_resign.setBounds(70, 475, 120, 45);
    // 확인 버튼 설정
    jbtn_save.setBorderPainted(false);
    jbtn_save.setBackground(new Color(130, 65, 60));
    jbtn_save.setForeground(Color.WHITE);
    jbtn_save.setFont(setFontNJOp.b14);
    jbtn_save.setBounds(210, 475, 120, 45);
    // Jlb 설정
    jlb_name.setForeground(new Color(135, 90, 75));
    jlb_hp.setForeground(new Color(135, 90, 75));
    jlb_id.setForeground(new Color(135, 90, 75));
    jlb_nickName.setForeground(new Color(135, 90, 75));
    jlb_pw.setForeground(new Color(135, 90, 75));
    jlb_pwRe.setForeground(new Color(135, 90, 75));
    jlb_pwtxt.setForeground(new Color(135, 90, 75));
    jlb_userStatMsg.setForeground(new Color(135, 90, 75));
    jlb_name.setFont(setFontNJOp.b12);
    jlb_hp.setFont(setFontNJOp.b12);
    jlb_id.setFont(setFontNJOp.b12);
    jlb_nickName.setFont(setFontNJOp.b12);
    jlb_pw.setFont(setFontNJOp.b12);
    jlb_pwRe.setFont(setFontNJOp.b12);
    jlb_pwtxt.setFont(setFontNJOp.p12);
    jlb_userStatMsg.setFont(setFontNJOp.b12);
    jlb_name.setBounds(71, 55, 100, 40);
    jlb_hp.setBounds(36, 110, 100, 40);
    jlb_id.setBounds(59, 165, 100, 40);
    jlb_nickName.setBounds(59, 220, 100, 40);
    jlb_pw.setBounds(10, 275, 100, 40);
    jlb_pwRe.setBounds(20, 330, 100, 40);
    jlb_pwtxt.setBounds(108, 360, 150, 40);
    jlb_userStatMsg.setBounds(36, 400, 150, 40);
    // JP 설정
    jp_mypage.setBackground(new Color(255, 230, 120));
    // JF 설정
    client.setTitle("마이페이지");
    client.setContentPane(jp_mypage);
    client.setVisible(true);

    // Jdg_resign 설정
    // 이벤트리스너
    jtf_resignId.addActionListener(this);
    jtf_resignPw.addActionListener(this);
    jbtn_back.addActionListener(this);
    jbtn_realresign.addActionListener(this);
    // 패널에 추가
    jp_resign.add(jtf_resignId);
    jp_resign.add(jtf_resignPw);
    jp_resign.add(jbtn_back);
    jp_resign.add(jbtn_realresign);
    jp_resign.add(jlb_resignId);
    jp_resign.add(jlb_resignPw);
    // Jtf 설정
    jtf_resignId.setBounds(50, 90, 235, 45);
    jtf_resignPw.setBounds(50, 190, 235, 45);
    jtf_resignId.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_resignPw.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    // 돌아가기 버튼 설정
    jbtn_back.setBorderPainted(false);
    jbtn_back.setBackground(new Color(130, 65, 60));
    jbtn_back.setForeground(Color.WHITE);
    jbtn_back.setFont(setFontNJOp.b14);
    jbtn_back.setBounds(55, 270, 100, 40);
    // 탈퇴하기 버튼 설정
    jbtn_realresign.setBorderPainted(false);
    jbtn_realresign.setBackground(new Color(130, 65, 60));
    jbtn_realresign.setForeground(Color.WHITE);
    jbtn_realresign.setFont(setFontNJOp.b14);
    jbtn_realresign.setBounds(180, 270, 100, 40);
    // Jlb 설정
    jlb_resignId.setForeground(new Color(135, 90, 75));
    jlb_resignPw.setForeground(new Color(135, 90, 75));
    jlb_resignId.setFont(setFontNJOp.b12);
    jlb_resignPw.setFont(setFontNJOp.b12);
    jlb_resignId.setBounds(50, 50, 90, 40);
    jlb_resignPw.setBounds(50, 150, 90, 40);
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
      String userNick = jtf_nickName.getText();
      // DB의 닉네임 중복검사
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
    if (obj == jbtn_save || obj == jtf_nickName || obj == jtf_userPw || obj == jtf_userPwRe || obj == jtf_userStatMsg) {
      String userNick = jtf_nickName.getText();
      String pwFirst = jtf_userPw.getText();
      String pwSecond = jtf_userPwRe.getText();
      String statMsg = jtf_userStatMsg.getText();
      // 상태메시지가 20자를 초과할 경우
      if (statMsg.length() > 20) {
        JOptionPane.showMessageDialog(client, "상태메시지는 20자 이하로 적어주세요.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
            setImage.img_info);
      }
      // 비밀번호 1, 2가 다를 경우
      else if (!pwFirst.equals(pwSecond)) {
        JOptionPane.showMessageDialog(client, "비밀번호가 일치하지 않습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
            setImage.img_notFound);
      }
      // 입력내용이 DB와 다를 경우
      else if (!userNick.equals("") || !pwFirst.equals("") || !statMsg.equals("상태메시지를 입력해주세요.")) {
        // 아이디 중복확인 안했을 경우
        if (!nickTnF) {
          JOptionPane.showMessageDialog(client, "닉네임 중복확인을 해주세요", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
              setImage.img_info);
        } else {
          // 비밀번호, 중복확인 후 DB로 정보 업데이트
          JOptionPane.showMessageDialog(client, "변경이 완료되었습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
              setImage.img_confirm);
          client.setContentPane(client.main.jp_main);
          client.setTitle("친구 목록");
          client.revalidate();
        }
      }
      // 변경사항이 없는 경우
      else {
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
      String userId = jtf_resignId.getText();
      String userPw = jtf_resignPw.getText();
      // 아이디가 로그인 아이디와 다를경우
      if (!userId.equals("DB")) {
        JOptionPane.showMessageDialog(jd_resign, "아이디가 일치하지 않습니다.", "회원탈퇴", JOptionPane.WARNING_MESSAGE,
            setImage.img_notFound);
      }
      // 비밀번호가 로그인 아이디와 다를경우
      else if (!userPw.equals("DB")) {
        JOptionPane.showMessageDialog(jd_resign, "비밀번호가 일치하지 않습니다.", "회원탈퇴", JOptionPane.WARNING_MESSAGE,
            setImage.img_notFound);
      }
      // 아이디와 비밀번호가 로그인한것과 같을 경우
      else if (userId.equals("DB") || userPw.equals("DB")) {
        JOptionPane.showMessageDialog(jd_resign, "바나나톡 탈퇴가 완료되었습니다.", "회원탈퇴", JOptionPane.WARNING_MESSAGE,
            setImage.img_delete);
        jd_resign.dispose();
        client.setContentPane(client.jp_login);
        client.setTitle("바나나톡");
        client.revalidate();
      }
    }
    // 돌아가기 버튼을 눌렀을 때
    else if (obj == jbtn_back) {
      jd_resign.dispose();
    }
  }

  /**
   * FocusListener 메소드
   */
  @Override
  public void focusGained(FocusEvent e) {
    Object obj = e.getSource();
    // 상태메시지 jtf를 클릭했을 때
    if (obj == jtf_userStatMsg) {
      jtf_userStatMsg.setForeground(Color.black);
      if ("상태메시지를 입력해주세요.".equals(jtf_userStatMsg.getText())) {
        jtf_userStatMsg.setText("");
      }
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    Object obj = e.getSource();
    // 상태메시지 jtf를 공백으로두고 벗어났을 때
    if (obj == jtf_userStatMsg) {
      if ("".equals(jtf_userStatMsg.getText())) {
        jtf_userStatMsg.setForeground(Color.gray);
        jtf_userStatMsg.setText("상태메시지를 입력해주세요.");
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