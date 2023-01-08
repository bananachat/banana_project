package banana_project.client.login;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PwFindDialog extends JDialog implements ActionListener {
  /**
   * 서버연결부 선언
   */
  PwFind pwFind = null;
  String newPw = null; // 유저가 재설정한 비밀번호

  /**
   * 화면부 선언
   */
  JPanel jp_pwReset = new JPanel(null);
  // 이미지
  String imgPath = "./app\\src\\main\\java\\banana_project\\image\\";
  ImageIcon img_title = new ImageIcon(imgPath + "logo_title.png"); // 타이틀창 이미지
  ImageIcon img_info = new ImageIcon(imgPath + "mini_info.png"); // JOp 인포 이미지
  ImageIcon img_confirm = new ImageIcon(imgPath + "mini_confirm.png"); // JOp 확인 이미지
  ImageIcon img_notFound = new ImageIcon(imgPath + "mini_notFound.png"); // JOp 취소 이미지
  // 폰트
  Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통12 폰트
  Font b12 = new Font("맑은 고딕", Font.BOLD, 12); // 볼드12 폰트
  Font b14 = new Font("맑은 고딕", Font.BOLD, 14); // 볼드14 폰트
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
    jtf_pwFirst.setBounds(75, 90, 250, 45);
    jtf_pwSecond.setBounds(75, 190, 250, 45);
    jtf_pwFirst.setBorder(new LineBorder(Color.white, 8));
    jtf_pwSecond.setBorder(new LineBorder(Color.white, 8));
    // 비밀번호 재설정 버튼
    jbtn_pwReset.setBorderPainted(false);
    jbtn_pwReset.setBackground(new Color(130, 65, 60));
    jbtn_pwReset.setForeground(Color.white);
    jbtn_pwReset.setFont(b14);
    jbtn_pwReset.setBounds(110, 265, 180, 45);
    // Jlb 설정
    jlb_pwFirst.setForeground(new Color(135, 90, 75));
    jlb_pwSecond.setForeground(new Color(135, 90, 75));
    jlb_pwFirst.setFont(b12);
    jlb_pwSecond.setFont(b12);
    jlb_pwFirst.setBounds(75, 50, 90, 45);
    jlb_pwSecond.setBounds(75, 150, 120, 45);
    // Jp 설정
    jp_pwReset.setBackground(new Color(255, 230, 120));
    // JDg 설정
    this.setTitle("비밀번호 재설정");
    this.setIconImage(img_title.getImage()); // 타이틀창 이미지
    this.setContentPane(jp_pwReset);
    this.setSize(400, 400);
    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    this.setLocationRelativeTo(pwFind.client);
    this.setResizable(false);
    this.setVisible(true);
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
    // 비밀번호 재설정 버튼을 눌렀을 때
    if (obj == jbtn_pwReset || obj == jtf_pwFirst || obj == jtf_pwSecond) {
      newPw = jtf_pwFirst.getText();
      String rePw = jtf_pwSecond.getText();
      // 비밀번호를 입력하지 않았을 경우
      if ("".equals(newPw) || "".equals(rePw)) {
        JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요", "비밀번호 재설정", JOptionPane.WARNING_MESSAGE, img_info);
      }
      // 비밀번호1,2가 틀릴 경우
      else if (!newPw.equals(rePw)) {
        JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "비밀번호 재설정", JOptionPane.WARNING_MESSAGE, img_notFound);
      }
      // 올바르게 입력하면 DB전송
      else {
        JOptionPane.showMessageDialog(this, "비밀번호가 재설정되었습니다.", "비밀번호 재설정", JOptionPane.WARNING_MESSAGE, img_confirm);
        this.dispose();
        pwFind.client.setContentPane(pwFind.client.jp_login);
        pwFind.client.revalidate();
        try {
          // 비밀번호 재설정시 서버에 전달
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