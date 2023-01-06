package banana_project.login;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IdFind implements ActionListener, FocusListener {
  // 클라이언트 연결
  Client client = null;
  // 서버 연결부 선언
  String userName = null; // 유저가 입력한 이름
  String userHp = null; // 유저가 입력한 핸드폰번호
  // 화면부 선언
  JPanel jp_idFind = new JPanel(null); // 아이디찾기 패널
  // 폰트 설정
  Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통 폰트
  Font b14 = new Font("맑은 고딕", Font.BOLD, 14); // 볼드 폰트
  // 이름, 핸드폰번호 입력을 위한 JTextField (테두리선을 지우기위해 클래스 재정의)
  JTextField jtf_userName = new JTextField(" 이름");
  JTextField jtf_userHp = new JTextField(" 핸드폰 번호");
  // 이미지 설정
  String imgPath = "./app\\src\\main\\java\\banana_project\\image\\"; // 이미지파일 위치
  ImageIcon img_idFind = new ImageIcon(imgPath + "banana_find.png"); // 아이디찾기 이미지
  // 버튼 설정
  JButton jbtn_back = new JButton("돌아가기"); // 돌아가기 버튼
  JButton jbtn_findId = new JButton("아이디 찾기"); // 아이디찾기 버튼
  JButton jbtn_main = new JButton(img_idFind); // 메인 찾기 이미지 붙이기용 버튼

  // 생성자
  public IdFind(Client client) {
    this.client = client;
  }

  // 화면부 메소드
  public void initDisplay() {
    // 이벤트리스너 연결
    jtf_userHp.addFocusListener(this);
    jtf_userName.addFocusListener(this);
    jbtn_back.addActionListener(this);
    jbtn_findId.addActionListener(this);
    jtf_userHp.addActionListener(this);
    jtf_userName.addActionListener(this);
    // 패널에 추가
    jp_idFind.add(jtf_userName);
    jp_idFind.add(jtf_userHp);
    jp_idFind.add(jbtn_back);
    jp_idFind.add(jbtn_findId);
    jp_idFind.add(jbtn_main);
    // 이름, 전화번호 입력창
    jtf_userName.setForeground(Color.gray);
    jtf_userHp.setForeground(Color.gray);
    jtf_userName.setFont(p12);
    jtf_userHp.setFont(p12);
    jtf_userName.setBounds(60, 300, 270, 45);
    jtf_userHp.setBounds(60, 360, 270, 45);
    // jtf 보더라인 설정
    jtf_userName.setBorder(new LineBorder(Color.white, 8));
    jtf_userHp.setBorder(new LineBorder(Color.white, 8));
    // 돌아가기 버튼 정의
    jbtn_back.setBorderPainted(false);
    jbtn_back.setBackground(new Color(130, 65, 60));
    jbtn_back.setForeground(Color.white);
    jbtn_back.setFont(b14);
    jbtn_back.setBounds(60, 420, 130, 45);
    // 아이디찾기 버튼 정의
    jbtn_findId.setBorderPainted(false);
    jbtn_findId.setBackground(new Color(130, 65, 60));
    jbtn_findId.setForeground(Color.white);
    jbtn_findId.setFont(b14);
    jbtn_findId.setBounds(200, 420, 130, 45);
    // KeyListener-엔터키 누르면 아이디찾기 버튼 눌림
    jbtn_findId.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          // 아이디찾기버튼 이벤트
        }
      }
    });
    // 바나나 이미지 정의
    jbtn_main.setBackground(new Color(255, 230, 120));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(60, 35, 270, 250); // 바나나 이미지 고정
    jp_idFind.setBackground(new Color(255, 230, 120)); // 패널색 노란색
    // JFrame, 메인프레임을 client에서 가져옴
    client.jf_login.setTitle("아이디 찾기");
    client.jf_login.setContentPane(jp_idFind); // 액자에 도화지 끼우기
    client.jf_login.setSize(400, 600);
    client.jf_login.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 돌아가기 버튼을 눌렀을 때
    if (obj == jbtn_back) {
      client.initDisplay();
    }
    // 아이디찾기 버튼을 눌렀을 때
    else if (obj == jbtn_findId || obj == jtf_userName || obj == jtf_userHp) {
      userName = jtf_userName.getText();
      userHp = jtf_userHp.getText();
      if (" 이름".equals(userName) || "".equals(userName) || " 핸드폰 번호".equals(userHp) || "".equals(userHp)) {
        JOptionPane.showMessageDialog(client.jf_login, "이름과 핸드폰번호를 입력해주세요", "info", JOptionPane.WARNING_MESSAGE);
      }
    }
  }

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
    // 핸드폰번호 jtf를 클릭했을 때
    else if (obj == jtf_userHp) {
      jtf_userHp.setForeground(Color.black);
      if (" 핸드폰 번호".equals(jtf_userHp.getText())) {
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
    // 핸드폰번호 jtf를 공백으로두고 벗어났을 때
    else if (obj == jtf_userHp) {
      if ("".equals(jtf_userHp.getText())) {
        jtf_userHp.setForeground(Color.gray);
        jtf_userHp.setText(" 핸드폰 번호");
      }
    }
  }

  // 테스트용메인
  public static void main(String[] args) {
    Client c = new Client();
    IdFind i = new IdFind(c);
    i.initDisplay();
    i.client.jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    i.client.jf_login.setLocationRelativeTo(null);
  }
}