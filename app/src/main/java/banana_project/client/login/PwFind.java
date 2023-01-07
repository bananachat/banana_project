package banana_project.client.login;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;

public class PwFind implements ActionListener, FocusListener {
  /**
   * 서버 연결부 선언
   */
  Client client = null;
  String userName = null; // 유저가 입력한 이름
  String userId = null; // 유저가 입력한 아이디
  String userHp = null; // 유저가 입력한 핸드폰번호
  // 테스트용 이름, 아이디, 핸드폰번호
  String dbName = "바나나";
  String dbId = "banana@email.com";
  String dbHp = "01012341234";

  /**
   * 화면부 선언
   */
  JPanel jp_pwFind = new JPanel(null);
  // 이미지 설정
  String imgPath = "./app\\src\\main\\java\\banana_project\\image\\";
  ImageIcon img_pwFind = new ImageIcon(imgPath + "logo_find.png"); // 찾기 로고 이미지
  ImageIcon img_info = new ImageIcon(imgPath + "mini_info.png"); // JOp 인포 이미지
  ImageIcon img_confirm = new ImageIcon(imgPath + "mini_confirm.png"); // JOp 확인 이미지
  ImageIcon img_notFound = new ImageIcon(imgPath + "mini_notFound.png"); // JOp 취소 이미지
  // 폰트
  Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통12 폰트
  Font b12 = new Font("맑은 고딕", Font.BOLD, 12); // 볼드12 폰트
  Font b14 = new Font("맑은 고딕", Font.BOLD, 14); // 볼드14 폰트
  // jtf
  JTextField jtf_userName = new JTextField(" 이름");
  JTextField jtf_userId = new JTextField(" example@email.com");
  JTextField jtf_userHp = new JTextField(" 핸드폰 번호 (-없이 숫자만 입력)");
  // Jbtn
  JButton jbtn_back = new JButton("돌아가기"); // 돌아가기 버튼
  JButton jbtn_findPw = new JButton("비밀번호 찾기"); // 비밀번호찾기 버튼
  JButton jbtn_main = new JButton(img_pwFind); // 찾기 이미지용 버튼

  /**
   * 생성자
   * 
   * @param client
   */
  public PwFind(Client client) {
    this.client = client;
  }

  /**
   * 화면부 메소드
   */
  public void initDisplay() {
    // 이벤트리스너 연결
    jtf_userName.addFocusListener(this);
    jtf_userId.addFocusListener(this);
    jtf_userHp.addFocusListener(this);
    jbtn_back.addActionListener(this);
    jbtn_findPw.addActionListener(this);
    jtf_userName.addActionListener(this);
    jtf_userId.addActionListener(this);
    jtf_userHp.addActionListener(this);
    // 패널에 추가
    jp_pwFind.add(jtf_userName);
    jp_pwFind.add(jtf_userId);
    jp_pwFind.add(jtf_userHp);
    jp_pwFind.add(jbtn_back);
    jp_pwFind.add(jbtn_findPw);
    jp_pwFind.add(jbtn_main);
    // Jtf 설정
    jtf_userName.setForeground(Color.gray);
    jtf_userId.setForeground(Color.gray);
    jtf_userHp.setForeground(Color.gray);
    jtf_userName.setFont(p12);
    jtf_userId.setFont(p12);
    jtf_userHp.setFont(p12);
    jtf_userName.setBounds(60, 285, 270, 45);
    jtf_userId.setBounds(60, 345, 270, 45);
    jtf_userHp.setBounds(60, 405, 270, 45);
    jtf_userName.setBorder(new LineBorder(Color.white, 8));
    jtf_userId.setBorder(new LineBorder(Color.white, 8));
    jtf_userHp.setBorder(new LineBorder(Color.white, 8));
    // 돌아가기 버튼 설정
    jbtn_back.setBorderPainted(false);
    jbtn_back.setBackground(new Color(130, 65, 60));
    jbtn_back.setForeground(Color.white);
    jbtn_back.setFont(b14);
    jbtn_back.setBounds(60, 465, 130, 45);
    // 비밀번호찾기 버튼 설정
    jbtn_findPw.setBorderPainted(false);
    jbtn_findPw.setBackground(new Color(130, 65, 60));
    jbtn_findPw.setForeground(Color.white);
    jbtn_findPw.setFont(b14);
    jbtn_findPw.setBounds(200, 465, 130, 45);
    // 로고 이미지 설정
    jbtn_main.setBackground(new Color(255, 230, 120));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(60, 20, 270, 250); // 바나나 이미지 고정
    // Jp 설정
    jp_pwFind.setBackground(new Color(255, 230, 120)); // 패널색 노란색
    // JF설정
    client.setTitle("비밀번호 찾기");
    client.setContentPane(jp_pwFind); // 액자에 도화지 끼우기
    client.setVisible(true);
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
   * ActionListener 메소드
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 돌아가기 버튼을 눌렀을 때
    if (obj == jbtn_back) {
      client.setContentPane(client.jp_login);
      client.revalidate();
    }
    // 비밀번호찾기 버튼을 눌렀을 때
    else if (obj == jbtn_findPw || obj == jtf_userName || obj == jtf_userId || obj == jtf_userHp) {
      userName = jtf_userName.getText();
      userId = jtf_userId.getText();
      userHp = jtf_userHp.getText();
      String idCheck = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"; // 이메일 형식
      String hpCheck = userHp.replaceAll("[^0-9]", "-"); // 숫자가 아닌 문자들을 -로 치환
      // 이름을 입력하지 않았을 경우
      if (" 이름".equals(userName) || "".equals(userName)) {
        JOptionPane.showMessageDialog(client, "이름을 입력해주세요", "비밀번호 찾기", JOptionPane.WARNING_MESSAGE, img_info);
      }
      // 이메일을 입력하지 않았을 경우
      else if (" example@email.com".equals(userId) || "".equals(userId)) {
        JOptionPane.showMessageDialog(client, "이메일을 입력해주세요", "비밀번호 찾기", JOptionPane.WARNING_MESSAGE, img_info);
      }
      // 이메일 형식이 아닐 경우
      else if (!Pattern.matches(idCheck, userId)) {
        JOptionPane.showMessageDialog(client, "example@email.com 형식으로 입력해주세요", "비밀번호 찾기",
            JOptionPane.WARNING_MESSAGE, img_info);
      }
      // 핸드폰번호를 입력하지 않았을 경우
      else if (" 핸드폰 번호 (-없이 숫자만 입력)".equals(userHp) || "".equals(userHp)) {
        JOptionPane.showMessageDialog(client, "핸드폰번호를 입력해주세요", "비밀번호 찾기", JOptionPane.WARNING_MESSAGE, img_info);
      }
      // 핸드폰번호에 숫자가 아닌 것을 넣었을 경우
      else if (hpCheck.contains("-")) {
        JOptionPane.showMessageDialog(client, "핸드폰번호는 -를 제외한 숫자만 입력해주세요.", "비밀번호 찾기", JOptionPane.WARNING_MESSAGE,
            img_info);
      } else {
        // 테스트용 if문
        // 이름, 아이디, 전화번호가 맞을 경우
        if (userName.equals(dbName) && userId.equals(dbId) && userHp.equals(dbHp)) {
          PwFindDialog pwFindDialog = new PwFindDialog(this);
          pwFindDialog.initDisplay();
        }
        // 틀릴 경우
        else {
          JOptionPane.showMessageDialog(client, "계정을 찾을 수 없습니다.", "비밀번호 찾기", JOptionPane.ERROR_MESSAGE,
              img_notFound);
        }
      }
    }
  }

  /**
   * FocusListener 메소드
   */
  @Override
  public void focusGained(FocusEvent e) {
    Object obj = e.getSource();
    // 이름 jtf를 클릭했을 때
    if (obj == jtf_userName) {
      jtf_userName.setForeground(Color.black);
      if (" 이름".equals(jtf_userName.getText())) {
        jtf_userName.setText("");
      }
    }
    // 아이디 jtf를 클릭했을 때
    else if (obj == jtf_userId) {
      jtf_userId.setForeground(Color.black);
      if (" example@email.com".equals(jtf_userId.getText())) {
        jtf_userId.setText("");
      }
    }
    // 핸드폰번호 jtf를 클릭했을 때
    else if (obj == jtf_userHp) {
      jtf_userHp.setForeground(Color.black);
      if (" 핸드폰 번호 (-없이 숫자만 입력)".equals(jtf_userHp.getText())) {
        jtf_userHp.setText("");
      }
    }
  }

  @Override
  public void focusLost(FocusEvent e) {
    Object obj = e.getSource();
    // 이름 jtf를 공백으로두고 벗어났을 때
    if (obj == jtf_userName) {
      if ("".equals(jtf_userName.getText())) {
        jtf_userName.setForeground(Color.gray);
        jtf_userName.setText(" 이름");
      }
    }
    // 아이디 jtf를 공백으로두고 벗어났을 때
    else if (obj == jtf_userId) {
      if ("".equals(jtf_userId.getText())) {
        jtf_userId.setForeground(Color.gray);
        jtf_userId.setText(" example@email.com");
      }
    }
    // 핸드폰번호 jtf를 공백으로두고 벗어났을 때
    else if (obj == jtf_userHp) {
      if ("".equals(jtf_userHp.getText())) {
        jtf_userHp.setForeground(Color.gray);
        jtf_userHp.setText(" 핸드폰 번호 (-없이 숫자만 입력)");
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
    c.initDisplay();
  }
}