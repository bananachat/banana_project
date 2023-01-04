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

public class IdFind extends JFrame implements ActionListener {
  // 화면부 선언
  JFrame jf_idFind = new JFrame(); // 메인프레임
  JPanel jp_idFind = new JPanel(null);
  // 아이디, 비밀번호 입력을 위한 JTextField (테두리선을 지우기위해 클래스 재정의)
  JTextField jtf_userName = new JTextField() {
    @Override
    public void setBorder(Border border) {
    }
  };
  JPasswordField jtf_userHp = new JPasswordField() {
    @Override
    public void setBorder(Border border) {
    }
  };
  JLabel jlb_nameText = new JLabel("  이름");
  JLabel jlb_hpText = new JLabel("  핸드폰 번호");
  Font fP = new Font("맑은 고딕", Font.PLAIN, 12);
  JButton jbtn_back = new JButton("돌아가기");
  JButton jbtn_findId = new JButton("아이디 찾기");

  public void initDisplay() {
    // 이벤트리스너 연결
    jbtn_back.addActionListener(this);
    jbtn_findId.addActionListener(this);
    // 로그인, 패스워드 라벨 설정
    jlb_nameText.setForeground(Color.GRAY);
    jlb_hpText.setForeground(Color.GRAY);
    jlb_nameText.setBounds(60, 300, 270, 45);
    jlb_hpText.setBounds(60, 340, 270, 45);
    jp_idFind.add(jlb_nameText);
    jp_idFind.add(jlb_hpText);
    // JTextField(ip,pw입력), JLabel(분실정보찾기), JButton(바나나이미지, 로그인, 가입버튼) 붙임
    jp_idFind.add(jtf_userName);
    jp_idFind.add(jtf_userHp);
    jp_idFind.add(jbtn_back);
    jp_idFind.add(jbtn_findId);
    // 아이디 비밀번호 입력창 고정
    jtf_userName.setBounds(60, 300, 270, 45);
    jtf_userHp.setBounds(60, 340, 270, 45);

    // 로그인 버튼 정의
    jbtn_back.setBorderPainted(false);
    jbtn_back.setBounds(200, 400, 130, 45);
    // jbtn_login.setBounds(175, 285, 120, 40);
    // KeyListener : 엔터키 누르면 로그인 버튼 눌림
    jbtn_back.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          // join.initDisplay();
        }
      }
    });

    // 회원가입버튼 정의
    jbtn_findId.setForeground(Color.BLACK);
    jbtn_findId.setFont(fP);
    jbtn_findId.setBounds(60, 400, 130, 45);
    jbtn_findId.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        // Join join = new Join();
        // join.initDisplay();
      }
    });

    // JFrame, 메인프레임 정의
    jf_idFind.setTitle("바나나톡");
    jf_idFind.setContentPane(jp_idFind); // 액자에 도화지 끼우기
    jf_idFind.setSize(400, 600);
    jf_idFind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf_idFind.setLocationRelativeTo(null);// 창 가운데서 띄우기
    jf_idFind.setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
  }
}