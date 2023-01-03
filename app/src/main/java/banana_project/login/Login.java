package banana_project.login;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame implements ActionListener {
  // 화면부 선언
  JFrame jf_login = new JFrame();
  JPanel jp_login = new JPanel(null);
  // 아이디, 비밀번호 입력을 위한 JTextField (테두리선을 지우기위해 클래스 재정의)
  JTextField jtf_id = new JTextField(); // {
  // @Override
  // public void setBorder(Border border) {
  // }
  // };
  JPasswordField jtf_pw = new JPasswordField(); // {
  // @Ov/erride
  // public void setBorder(Border border) {
  // }
  // };
  JLabel jlMsg = new JLabel();
  JLabel jlb_idtext = new JLabel("example@email.com");
  JLabel jlb_pwtext = new JLabel("password");
  JLabel jlb_findId = new JLabel(); // 아이디찾기
  JLabel jlb_findPw = new JLabel(); // 비밀번호 찾기
  Font ft = new Font("맑은 고딕", Font.PLAIN, 12);
  String imgPath = "";
  ImageIcon img_main = new ImageIcon(imgPath + "");
  ImageIcon img_login = new ImageIcon(imgPath + "");
  ImageIcon img_join = new ImageIcon(imgPath + "");
  JButton jbtn_login = new JButton("로그인" + img_login);
  JButton jbtn_join = new JButton("회원가입" + img_join);
  JButton jbtn_main = new JButton(img_main); // 메인 이미지 붙이기

  public void initDisplay() {
    // ActionListener 설정
    jbtn_login.addActionListener(this);
    jbtn_join.addActionListener(this);
    // 아이디를 입력해주세요, 비밀번호를 입력해주세요. 미리 보여질 글자를 담고있는 JLabel 정의
    jlb_idtext.setFont(ft);
    jlb_pwtext.setFont(ft);
    // 로그인, 패스워드 라벨 설정
    jlb_idtext.setForeground(Color.GRAY);
    jlb_pwtext.setForeground(Color.GRAY);
    jlb_idtext.setBounds(60, 300, 270, 45);
    jlb_pwtext.setBounds(60, 340, 270, 45);
    jp_login.add(jlb_idtext);
    jp_login.add(jlb_pwtext);
    // JTextField(ip,pw입력), JLabel(분실정보찾기), JButton(바나나이미지, 로그인, 가입버튼) 붙임
    jp_login.add(jlMsg);
    jp_login.add(jtf_id);
    jp_login.add(jtf_pw);
    jp_login.add(jbtn_login);
    jp_login.add(jbtn_join);
    jp_login.add(jlb_findId);
    jp_login.add(jlb_findPw);
    jp_login.add(jbtn_main);
    // 아이디 비밀번호 입력창 고정 및 비밀번호 암호 *로 표시
    jtf_id.setBounds(60, 300, 270, 45);
    jtf_pw.setBounds(60, 340, 270, 45);
    jtf_pw.setEchoChar('*');

    // 로그인 버튼 정의
    jbtn_login.setBorderPainted(false);
    jbtn_login.setBounds(200, 400, 135, 45);
    // jbtn_login.setBounds(175, 285, 120, 40);
    // KeyListener : 엔터키 누르면 로그인 버튼 눌림
    jbtn_login.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          // join.initDisplay();
        }
      }
    });

    // 회원가입버튼 정의
    jbtn_join.setForeground(Color.BLACK);
    jbtn_join.setFont(ft);
    jbtn_join.setBounds(60, 400, 135, 45);

    // jbtn_join.setBounds(45, 285, 120, 40);
    jbtn_join.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        Join join = new Join();
        join.initDisplay();
      }
    });

    // 아이디/비밀번호 찾기 라벨버튼 정의
    jlb_findId.setText("<HTML><U>아이디 찾기</U></HTML>");
    jlb_findPw.setText("<HTML><U>비밀번호 찾기</U></HTML>");
    jlb_findId.setForeground(Color.BLACK);
    jlb_findPw.setForeground(Color.BLACK);
    jlb_findId.setFont(ft);
    jlb_findPw.setFont(ft);
    jlb_findId.setBounds(100, 460, 200, 20);
    jlb_findPw.setBounds(220, 460, 200, 20);
    // jlb_findId.addMouseListener(new MouseAdapter() {
    // @Override
    // public void mousePressed(MouseEvent e) {
    // super.mousePressed(e);
    // FindIdPwView fipv = new FindIdPwView();
    // fipv.initDisplay();
    // }
    // });
    // }

    // 바나나 이미지 정의
    jbtn_main.setBackground(new Color(253, 220, 81));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(60, 35, 270, 250); // 바나나 이미지 고정
    jp_login.setBackground(new Color(253, 220, 81)); // 도화지 색깔 노란색

    // JFrame, 메인프레임 정의
    jf_login.setTitle("바나나톡");
    jf_login.setContentPane(jp_login); // 액자에 도화지 끼우기
    jf_login.setSize(400, 600);
    jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf_login.setLocationRelativeTo(null);// 창 가운데서 띄우기
    jf_login.setVisible(true);
  }

  public static void main(String[] args) {
    Login lg = new Login();
    lg.initDisplay();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
  }
}