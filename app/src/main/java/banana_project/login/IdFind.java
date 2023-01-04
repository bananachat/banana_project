package banana_project.login;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IdFind extends JFrame implements ActionListener, MouseListener {
  // 클라이언트 연결
  Client client = null;
  // 화면부 선언
  JPanel jp_idFind = new JPanel(null);
  // JLabel jlb_nameText = new JLabel("이름"); // 이름 라벨
  // JLabel jlb_hpText = new JLabel("핸드폰 번호"); // 번호 라벨
  Font fPlain = new Font("맑은 고딕", Font.PLAIN, 12);
  // 아이디, 비밀번호 입력을 위한 JTextField (테두리선을 지우기위해 클래스 재정의)
  JTextField jtf_userName = new JTextField("  이름") {
    @Override
    public void setBorder(Border border) {
    }
  };
  JTextField jtf_userHp = new JTextField("  핸드폰 번호") {
    @Override
    public void setBorder(Border border) {
    }
  };
  String imgPath = "D:\\banana_project\\app\\src\\main\\java\\banana_project\\image\\"; // 이미지파일 위치
  ImageIcon img_idFind = new ImageIcon(imgPath + "banana_find.png"); // 아이디찾기 이미지
  JButton jbtn_back = new JButton("돌아가기");
  JButton jbtn_findId = new JButton("아이디 찾기");
  JButton jbtn_main = new JButton(img_idFind); // 메인 로고 이미지 붙이기

  // 생성자
  public IdFind(Client client) {
    this.client = client;
  }

  // 화면부 메소드
  public void initDisplay() {
    // 이벤트리스너 연결
    jtf_userHp.addMouseListener(this);
    jtf_userName.addMouseListener(this);
    jbtn_back.addActionListener(this);
    jbtn_findId.addActionListener(this);
    // JTextField(ip,pw입력), JLabel(분실정보찾기), JButton(바나나이미지, 로그인, 가입버튼) 붙임
    jp_idFind.add(jtf_userName);
    jp_idFind.add(jtf_userHp);
    jp_idFind.add(jbtn_back);
    jp_idFind.add(jbtn_findId);
    jp_idFind.add(jbtn_main);
    // 이름, 전화번호 입력창
    jtf_userName.setForeground(Color.gray);
    jtf_userHp.setForeground(Color.gray);
    jtf_userName.setBounds(60, 300, 270, 45);
    jtf_userHp.setBounds(60, 360, 270, 45);

    // 돌아가기 버튼 정의
    jbtn_back.setBorderPainted(false);
    jbtn_back.setBounds(60, 420, 130, 45);

    // 아이디찾기 버튼 정의
    jbtn_findId.setForeground(Color.BLACK);
    jbtn_findId.setBounds(200, 420, 130, 45);

    // KeyListener : 엔터키 누르면아이디찾기 버튼 눌림
    jbtn_back.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          // join.initDisplay();
        }
      }
    });
    // 바나나 이미지 정의
    jbtn_main.setBackground(new Color(255, 230, 120));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(60, 35, 270, 250); // 바나나 이미지 고정

    // JFrame, 메인프레임을 client에서 가져옴
    client.jf_login.setTitle("아이디 찾기");
    client.jf_login.setContentPane(jp_idFind); // 액자에 도화지 끼우기
    jp_idFind.setBackground(new Color(255, 230, 120)); // 패널색 노란색
    client.jf_login.setSize(400, 600);
    client.jf_login.setVisible(true);

    // 테스트용
    client.jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    client.jf_login.setLocationRelativeTo(null);// 창 가운데서 띄우기
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    if (jbtn_back == obj) {
      client.initDisplay();
    }
  }

  // 마우스이벤트
  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    Object obj = e.getSource();
    if (jtf_userName == obj) {
      jtf_userName.setForeground(Color.black);
      if ("  이름".equals(jtf_userName.getText())) {
        jtf_userName.setText("");
      }
    } else if (jtf_userHp == obj) {
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

  // // 테스트용메인
  // public static void main(String[] args) {
  //   Client c = new Client();
  //   IdFind i = new IdFind(c);
  //   i.initDisplay();
  // }
}