package banana_project.login;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PwFind implements ActionListener, MouseListener {
  // 클라이언트 연결
  Client client = null;
  // 서버 연결부 선언
  String userName = null; // 유저가 입력한 이름
  String userId = null; // 유저가 입력한 아이디
  String userHp = null; // 유저가 입력한 핸드폰번호
  // 화면부 선언
  JPanel jp_pwFind = new JPanel(null); // 비밀번호찾기 패널
  // 폰트 설정
  Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통 폰트
  Font b14 = new Font("맑은 고딕", Font.BOLD, 14); // 볼드 폰트
  // 이름, 아이디, 핸드폰번호 입력을 위한 JTextField (테두리선을 지우기위해 클래스 재정의)
  JTextField jtf_userName = new JTextField("  이름") {
    @Override
    public void setBorder(Border border) {
    }
  };
  JTextField jtf_userId = new JTextField("  example@email.com") {
    @Override
    public void setBorder(Border border) {
    }
  };
  JTextField jtf_userHp = new JTextField("  핸드폰 번호") {
    @Override
    public void setBorder(Border border) {
    }
  };
  // 이미지 설정
  String imgPath = "./app\\src\\main\\java\\banana_project\\image\\"; // 이미지파일 위치
  ImageIcon img_pwFind = new ImageIcon(imgPath + "banana_find.png"); // 아이디찾기 이미지
  // 버튼 설정
  JButton jbtn_back = new JButton("돌아가기"); // 돌아가기 버튼
  JButton jbtn_findPw = new JButton("비밀번호 찾기"); // 비밀번호찾기 버튼
  JButton jbtn_main = new JButton(img_pwFind); // 메인 찾기 이미지 붙이기용 버튼

  // 생성자
  public PwFind(Client client) {
    this.client = client;
  }

  // 화면부 메소드
  public void initDisplay() {
    // 이벤트리스너 연결
    jtf_userName.addMouseListener(this);
    jtf_userId.addMouseListener(this);
    jtf_userHp.addMouseListener(this);
    jbtn_back.addActionListener(this);
    jbtn_findPw.addActionListener(this);
    // 패널에 추가
    jp_pwFind.add(jtf_userName);
    jp_pwFind.add(jtf_userId);
    jp_pwFind.add(jtf_userHp);
    jp_pwFind.add(jbtn_back);
    jp_pwFind.add(jbtn_findPw);
    jp_pwFind.add(jbtn_main);
    // 이름, 아이디, 전화번호 입력창
    jtf_userName.setForeground(Color.gray);
    jtf_userId.setForeground(Color.gray);
    jtf_userHp.setForeground(Color.gray);
    jtf_userName.setFont(p12);
    jtf_userId.setFont(p12);
    jtf_userHp.setFont(p12);
    jtf_userName.setBounds(60, 285, 270, 45);
    jtf_userId.setBounds(60, 345, 270, 45);
    jtf_userHp.setBounds(60, 405, 270, 45);
    // 돌아가기 버튼 정의
    jbtn_back.setBorderPainted(false);
    jbtn_back.setBackground(new Color(130, 65, 60));
    jbtn_back.setForeground(Color.white);
    jbtn_back.setFont(b14);
    jbtn_back.setBounds(60, 465, 130, 45);
    // 비밀번호찾기 버튼 정의
    jbtn_findPw.setBorderPainted(false);
    jbtn_findPw.setBackground(new Color(130, 65, 60));
    jbtn_findPw.setForeground(Color.white);
    jbtn_findPw.setFont(b14);
    jbtn_findPw.setBounds(200, 465, 130, 45);
    // KeyListener-엔터키 누르면 비밀번호찾기 버튼 눌림
    jbtn_findPw.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          // 비밀번호버튼 이벤트
        }
      }
    });
    // 바나나 이미지 정의
    jbtn_main.setBackground(new Color(255, 230, 120));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(60, 20, 270, 250); // 바나나 이미지 고정
    jp_pwFind.setBackground(new Color(255, 230, 120)); // 패널색 노란색
    // JFrame, 메인프레임을 client에서 가져옴
    client.jf_login.setTitle("비밀번호 찾기");
    client.jf_login.setContentPane(jp_pwFind); // 액자에 도화지 끼우기
    client.jf_login.setSize(400, 600);
    client.jf_login.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 돌아가기 버튼을 눌렀을 때
    if (jbtn_back == obj) {
      client.initDisplay();
    }
    // 비밀번호찾기 버튼을 눌렀을 때
    else if (jbtn_findPw == obj) {
      userName = jtf_userName.getText();
      userId = jtf_userId.getText();
      userHp = jtf_userHp.getText();
      if ("  이름".equals(userName) || "".equals(userName) || "  example@email.com".equals(userId) || "".equals(userId)
          || "  핸드폰 번호".equals(userHp) || "".equals(userHp)) {
        JOptionPane.showMessageDialog(client.jf_login, "이름, 이메일, 핸드폰번호를 입력해주세요", "info", JOptionPane.WARNING_MESSAGE);
      }
    }
  }

  // 마우스이벤트
  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    Object obj = e.getSource();
    // 이름 jtf를 클릭했을 때
    if (jtf_userName == obj) {
      jtf_userName.setForeground(Color.black);
      if ("  이름".equals(jtf_userName.getText())) {
        jtf_userName.setText("");
      }
    }
    // 아이디 jtf를 클릭했을 때
    else if (jtf_userId == obj) {
      jtf_userName.setForeground(Color.black);
      if ("  example@email.com".equals(jtf_userId.getText())) {
        jtf_userId.setText("");
      }
    }
    // 핸드폰번호 jtf를 클릭했을 때
    else if (jtf_userHp == obj) {
      jtf_userHp.setForeground(Color.black);
      if ("  핸드폰 번호".equals(jtf_userHp.getText())) {
        jtf_userHp.setText("");
      }
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

  // 테스트용메인
  public static void main(String[] args) {
    Client c = new Client();
    PwFind p = new PwFind(c);
    p.initDisplay();
    p.client.jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    p.client.jf_login.setLocationRelativeTo(null);
  }
}