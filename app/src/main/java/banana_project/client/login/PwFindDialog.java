package banana_project.client.login;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.server.thread.Protocol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class PwFindDialog extends JDialog implements ActionListener {
  /**
   * 서버연결부 선언
   */
  PwFind pwFind = null;

  /**
   * 화면부 선언
   */
  // 이미지, 폰트, JOp 세팅 불러오기
  SetImg setImage = new SetImg();
  SetFontNJOp setFontNJOp = new SetFontNJOp();
  // Jp
  JPanel jp_pwReset = new JPanel(null);
  // Jtf
  JPasswordField jtf_pwFirst = new JPasswordField();
  JPasswordField jtf_pwSecond = new JPasswordField();
  // Jbtn
  JButton jbtn_pwReset = new JButton("비밀번호 재설정");
  // Jlb
  JLabel jlb_pwFirst = new JLabel("새로운 비밀번호");
  JLabel jlb_pwSecond = new JLabel("새로운 비밀번호 확인");

  /**
   * 생성자
   * 
   * @param pwFind
   */
  public PwFindDialog(PwFind pwFind) {
    this.pwFind = pwFind;
  }

  /**
   * 화면부 메소드
   */
  public void initDisplay() {
    // 이벤트리스너 연결
    jtf_pwFirst.addActionListener(this);
    jtf_pwSecond.addActionListener(this);
    jbtn_pwReset.addActionListener(this);
    // 패널에 추가
    jp_pwReset.add(jlb_pwFirst);
    jp_pwReset.add(jlb_pwSecond);
    jp_pwReset.add(jtf_pwFirst);
    jp_pwReset.add(jtf_pwSecond);
    jp_pwReset.add(jbtn_pwReset);
    // Jtf 설정
    jtf_pwFirst.setBounds(50, 90, 235, 45);
    jtf_pwSecond.setBounds(50, 190, 235, 45);
    jtf_pwFirst.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_pwSecond.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    // 비밀번호 재설정 버튼
    jbtn_pwReset.setBorderPainted(false);
    jbtn_pwReset.setBackground(new Color(130, 65, 60));
    jbtn_pwReset.setForeground(Color.white);
    jbtn_pwReset.setFont(setFontNJOp.b14);
    jbtn_pwReset.setBounds(85, 270, 160, 45);
    // Jlb 설정
    jlb_pwFirst.setForeground(new Color(135, 90, 75));
    jlb_pwSecond.setForeground(new Color(135, 90, 75));
    jlb_pwFirst.setFont(setFontNJOp.b12);
    jlb_pwSecond.setFont(setFontNJOp.b12);
    jlb_pwFirst.setBounds(50, 50, 90, 45);
    jlb_pwSecond.setBounds(50, 150, 120, 45);
    // Jp 설정
    jp_pwReset.setBackground(new Color(255, 230, 120));
    // JDg 설정
    this.setTitle("비밀번호 재설정");
    this.setIconImage(setImage.img_title.getImage()); // 타이틀창 이미지
    this.setContentPane(jp_pwReset);
    this.setSize(350, 400);
    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    this.setLocationRelativeTo(pwFind.client);
    this.setResizable(false);
    this.setVisible(true);
  }

  public void reset_pw() {
    JOptionPane.showMessageDialog(this, "비밀번호가 재설정되었습니다.", "비밀번호 재설정", JOptionPane.WARNING_MESSAGE,
        setImage.img_confirm);
    this.dispose();
    pwFind.client.setContentPane(pwFind.client.jp_login);
    pwFind.client.revalidate();
  }

  /**
   * ActionListener 메소드
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    // 비밀번호 재설정 버튼을 눌렀을 때
    if (obj == jbtn_pwReset || obj == jtf_pwFirst || obj == jtf_pwSecond) {
      String newPw = jtf_pwFirst.getText();
      String rePw = jtf_pwSecond.getText();
      // 비밀번호 정규식
      String pwCheck = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$"; // 비밀번호 형식(8~16자 숫자,영문자포함)
      // 비밀번호를 입력하지 않았을 경우
      if ("".equals(newPw) || "".equals(rePw)) {
        JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요.", "비밀번호 재설정", JOptionPane.WARNING_MESSAGE,
            setImage.img_info);
      }
      // 비밀번호 형식이 아닐 경우
      else if (!Pattern.matches(pwCheck, newPw)) {
        JOptionPane.showMessageDialog(this, "비밀번호는 숫자와 영문자를 포함하여 8~16자로 입력해주세요.", "로그인",
            JOptionPane.WARNING_MESSAGE, setImage.img_info);
      }
      // 비밀번호1,2가 다를 경우
      else if (!newPw.equals(rePw)) {
        JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "비밀번호 재설정", JOptionPane.WARNING_MESSAGE,
            setImage.img_notFound);
      }
      // 비밀번호 재성정 -> 404#아이디#새로운비번
      else {
        try {

          // 재설정된 메소드에 받아온 아이디정보 추가할것!
          pwFind.client.oos.writeObject(Protocol.RESET_PW
              + Protocol.seperator + newPw);

        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
    }
  }

  /**
   * 테스트용 메인
   * 
   * @param args
   */
  public static void main(String[] args) {
    Client c = new Client();
    PwFind p = new PwFind(c);
    PwFindDialog pd = new PwFindDialog(p);
    pd.initDisplay();
  }
}