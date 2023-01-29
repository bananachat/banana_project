package banana_project.client.login;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.server.thread.Protocol;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;

public class IdFind implements ActionListener, FocusListener {
  /**
   * 서버 연결부 선언
   */
  Client client = null;

  /**
   * 화면부 선언
   */
  // 이미지, 폰트, JOp 세팅 불러오기
  SetImg setImage = new SetImg();
  SetFontNJOp setFontNJOp = new SetFontNJOp();
  // JP
  JPanel jp_idFind = new JPanel(null);
  // Jtf
  JTextField jtf_userName = new JTextField("이름"); // 이름 입력창
  JTextField jtf_userHp = new JTextField("핸드폰 번호 (-없이 숫자만 입력)"); // 전화번호 입력창
  // Jbtn
  JButton jbtn_back = new JButton("돌아가기"); // 돌아가기 버튼
  JButton jbtn_findId = new JButton("아이디 찾기"); // 아이디찾기 버튼
  JButton jbtn_main = new JButton(setImage.img_find); // 찾기 이미지용 버튼

  /**
   * 생성자
   * 
   * @param client
   */
  public IdFind(Client client) {
    this.client = client;
  }

  /**
   * 화면부 메소드
   */
  public void initDisplay() {
    // 이벤트리스너
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
    // Jf 설정
    jtf_userName.setForeground(Color.gray);
    jtf_userHp.setForeground(Color.gray);
    jtf_userName.setFont(setFontNJOp.p12);
    jtf_userHp.setFont(setFontNJOp.p12);
    jtf_userName.setBounds(60, 300, 270, 45);
    jtf_userHp.setBounds(60, 360, 270, 45);
    jtf_userName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userHp.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    // 돌아가기 버튼 설정
    jbtn_back.setBorderPainted(false);
    jbtn_back.setBackground(new Color(130, 65, 60));
    jbtn_back.setForeground(Color.white);
    jbtn_back.setFont(setFontNJOp.b14);
    jbtn_back.setBounds(60, 420, 130, 45);
    // 아이디찾기 버튼 설정
    jbtn_findId.setBorderPainted(false);
    jbtn_findId.setBackground(new Color(130, 65, 60));
    jbtn_findId.setForeground(Color.white);
    jbtn_findId.setFont(setFontNJOp.b14);
    jbtn_findId.setBounds(200, 420, 130, 45);
    // 로고 이미지 설정
    jbtn_main.setBackground(new Color(255, 230, 120));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(60, 35, 270, 250); // 바나나 이미지 고정
    // Jp 설정
    jp_idFind.setBackground(new Color(255, 230, 120)); // 패널색 노란색
    // JF설정
    client.setTitle("아이디 찾기");
    client.setContentPane(jp_idFind); // 액자에 도화지 끼우기
    client.setVisible(true);
  }

  /**
   * 존재하는 아이디 메소드
   */
  public void exist_fid(String UserName, String userId) {
    JOptionPane.showMessageDialog(client,
        UserName + "님의 아이디\n" + userId, "아이디 찾기", JOptionPane.INFORMATION_MESSAGE, setImage.img_add);
  }

  /**
   * 존재하지 않는 아이디 메소드
   */
  public void nf_fid() {
    JOptionPane.showMessageDialog(client, "계정을 찾을 수 없습니다.", "아이디 찾기", JOptionPane.ERROR_MESSAGE,
        setImage.img_notFound);
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
      client.setTitle("바나나톡");
      client.revalidate();
    }
    // 아이디찾기 버튼을 눌렀을 때
    else if (obj == jbtn_findId || obj == jtf_userName || obj == jtf_userHp) {
      String userName = jtf_userName.getText();
      String userHp = jtf_userHp.getText();
      // 이름, 핸드폰번호 정규식
      String hpCheck = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
      // 이름을 입력하지 않았을 경우
      if ("이름".equals(userName) || "".equals(userName)) {
        JOptionPane.showMessageDialog(client, "이름을 입력해주세요.", "아이디 찾기", JOptionPane.WARNING_MESSAGE, setImage.img_info);
      }
      // 핸드폰번호를 입력하지 않았을 경우
      else if ("핸드폰 번호 (-없이 숫자만 입력)".equals(userHp) || "".equals(userHp)) {
        JOptionPane.showMessageDialog(client, "핸드폰번호를 입력해주세요.", "아이디 찾기", JOptionPane.WARNING_MESSAGE,
            setImage.img_info);
      }
      // 핸드폰번호가 형식에 안맞음
      else if (!Pattern.matches(hpCheck, userHp)) {
        JOptionPane.showMessageDialog(client, "핸드폰번호는 -를 제외한 숫자만 입력해주세요.", "아이디 찾기", JOptionPane.WARNING_MESSAGE,
            setImage.img_info);
      } else {
        // 아이디찾기 -> 300#이름#핸드폰번호
        try {
          client.oos.writeObject(Protocol.FID_START
              + Protocol.seperator + userName
              + Protocol.seperator + userHp);
        } catch (Exception e2) {
          e2.printStackTrace();
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
      if ("이름".equals(jtf_userName.getText())) {
        jtf_userName.setText("");
      }
    }
    // 핸드폰번호 jtf를 클릭했을 때
    else if (obj == jtf_userHp) {
      jtf_userHp.setForeground(Color.black);
      if ("핸드폰 번호 (-없이 숫자만 입력)".equals(jtf_userHp.getText())) {
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
        jtf_userName.setText("이름");
      }
    }
    // 핸드폰번호 jtf를 공백으로두고 벗어났을 때
    else if (obj == jtf_userHp) {
      if ("".equals(jtf_userHp.getText())) {
        jtf_userHp.setForeground(Color.gray);
        jtf_userHp.setText("핸드폰 번호 (-없이 숫자만 입력)");
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