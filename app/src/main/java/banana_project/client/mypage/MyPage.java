package banana_project.client.mypage;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.regex.Pattern;

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
import banana_project.server.thread.Protocol;

public class MyPage implements ActionListener {
  /**
   * 서버 연결부 선언
   */
  Client client = null;// 회원가입 프레임
  // Main main = null; // 메인화면 프레임
  String dbNick = null;
  String dbId = null;
  boolean nickTnF = false;
  String tempNick = null;
  // 돌아가기 setTitle용
  String title = "";

  /**
   * 화면부 선언
   */
  // 이미지, 폰트, JOp 세팅 불러오기
  SetImg setImage = new SetImg();
  SetFontNJOp setFontNJOp = new SetFontNJOp();
  // JP
  JPanel jp_mypage = new JPanel(null);
  // Jtf
  JTextField jtf_userName = new JTextField(); // 이름 입력창
  JTextField jtf_userHp = new JTextField(); // 핸드폰번호 입력창
  JTextField jtf_userId = new JTextField(); // 아이디 입력창
  JTextField jtf_nickName = new JTextField(); // 닉네임 입력창
  JPasswordField jtf_userPw = new JPasswordField(); // 비밀번호 입력창
  JPasswordField jtf_userPwRe = new JPasswordField(); // 비밀번호 확인 입력창
  // Jbtn
  JButton jbtn_checkNick = new JButton("중복확인"); // 닉네임 중복체크버튼
  JButton jbtn_resign = new JButton("탈퇴하기"); // 탈퇴 버튼
  JButton jbtn_save = new JButton("확인"); // 확인 버튼
  JButton jbtn_main = new JButton(setImage.img_mypage);// 마이페이지 로고용 버튼
  // Jlb
  JLabel jlb_name = new JLabel("이름");
  JLabel jlb_hp = new JLabel("핸드폰번호");
  JLabel jlb_id = new JLabel("아이디");
  JLabel jlb_nickName = new JLabel("닉네임");
  JLabel jlb_pw = new JLabel("새로운 비밀번호");
  JLabel jlb_pwRe = new JLabel("비밀번호 확인");
  JLabel jlb_pwtxt = new JLabel("숫자, 영문자포함 8~16자리");
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
  public MyPage(Client client, String userId, String title) {
    this.client = client;
    this.title = title;
    // 사용자정보 불러오기 504#아이디
    try {
      client.oos.writeObject(Protocol.BTN_MYPAGE
          + Protocol.seperator + userId);
    } catch (Exception e) {
      e.printStackTrace();
    }
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
    // 패널에 추가
    jp_mypage.add(jtf_userName);
    jp_mypage.add(jtf_userHp);
    jp_mypage.add(jtf_userId);
    jp_mypage.add(jtf_nickName);
    jp_mypage.add(jtf_userPw);
    jp_mypage.add(jtf_userPwRe);
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
    jp_mypage.add(jbtn_main);
    // Jtf 설정
    jtf_userName.setForeground(Color.gray);
    jtf_userId.setForeground(Color.gray);
    jtf_userHp.setForeground(Color.gray);
    jtf_nickName.setForeground(Color.black);
    jtf_userPw.setForeground(Color.black);
    jtf_userPwRe.setForeground(Color.black);
    jtf_userName.setBounds(105, 135, 183, 40);
    jtf_userHp.setBounds(105, 187, 183, 40);
    jtf_userId.setBounds(105, 239, 183, 40);
    jtf_nickName.setBounds(105, 291, 183, 40);
    jtf_userPw.setBounds(105, 343, 183, 40);
    jtf_userPwRe.setBounds(105, 395, 183, 40);
    jtf_userName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userId.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userHp.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_nickName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userPw.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userPwRe.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jtf_userName.setEditable(false);
    jtf_userHp.setEditable(false);
    jtf_userId.setEditable(false);
    // 닉네임 중복확인 버튼 설정
    jbtn_checkNick.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    jbtn_checkNick.setBorderPainted(false);
    jbtn_checkNick.setBackground(new Color(130, 65, 60));
    jbtn_checkNick.setForeground(Color.WHITE);
    jbtn_checkNick.setFont(setFontNJOp.b12);
    jbtn_checkNick.setBounds(295, 293, 75, 36);
    // 탈퇴하기 버튼 설정
    jbtn_resign.setBorderPainted(false);
    jbtn_resign.setBackground(new Color(130, 65, 60));
    jbtn_resign.setForeground(Color.WHITE);
    jbtn_resign.setFont(setFontNJOp.b14);
    jbtn_resign.setBounds(70, 480, 120, 45);
    // 확인 버튼 설정
    jbtn_save.setBorderPainted(false);
    jbtn_save.setBackground(new Color(130, 65, 60));
    jbtn_save.setForeground(Color.WHITE);
    jbtn_save.setFont(setFontNJOp.b14);
    jbtn_save.setBounds(210, 480, 120, 45);
    // Jlb 설정
    jlb_name.setForeground(new Color(135, 90, 75));
    jlb_hp.setForeground(new Color(135, 90, 75));
    jlb_id.setForeground(new Color(135, 90, 75));
    jlb_nickName.setForeground(new Color(135, 90, 75));
    jlb_pw.setForeground(new Color(135, 90, 75));
    jlb_pwRe.setForeground(new Color(135, 90, 75));
    jlb_pwtxt.setForeground(new Color(135, 90, 75));
    jlb_name.setFont(setFontNJOp.b12);
    jlb_hp.setFont(setFontNJOp.b12);
    jlb_id.setFont(setFontNJOp.b12);
    jlb_nickName.setFont(setFontNJOp.b12);
    jlb_pw.setFont(setFontNJOp.b12);
    jlb_pwRe.setFont(setFontNJOp.b12);
    jlb_pwtxt.setFont(setFontNJOp.p12);
    jlb_name.setBounds(71, 135, 100, 40);
    jlb_hp.setBounds(36, 187, 100, 40);
    jlb_id.setBounds(59, 239, 100, 40);
    jlb_nickName.setBounds(59, 291, 100, 40);
    jlb_pw.setBounds(10, 343, 100, 40);
    jlb_pwRe.setBounds(20, 395, 100, 40);
    jlb_pwtxt.setBounds(108, 426, 150, 40);
    // 로고 이미지 설정
    jbtn_main.setBackground(new Color(255, 230, 120));
    jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
    jbtn_main.setBounds(75, 22, 240, 100); // 바나나 이미지 고정
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

  /**
   * 사용자 정보 마이페이지 출력 메소드
   * 
   * @param userName
   * @param userHp
   * @param userId2
   * @param nickName
   */
  public void btn_mypage(String userName, String userHp, String userId, String nickName) {
    jtf_userName.setText(userName);
    jtf_userHp.setText(userHp);
    jtf_userId.setText(userId);
    jtf_nickName.setText(nickName);
    dbNick = nickName;
    dbId = userId;
  }

  /**
   * 사용자 정보 불러오기 실패
   */
  public void nf_mypage() {
    JOptionPane.showMessageDialog(client, "정보를 불러오지 못했습니다.", "마이페이지", JOptionPane.WARNING_MESSAGE,
        setImage.img_delete);
  }

  /**
   * 사용 가능한 닉네임
   */
  public void nick_mchk() {
    nickTnF = true;
    tempNick = jtf_nickName.getText();
    JOptionPane.showMessageDialog(client, "중복확인이 완료되었습니다.", "마이페이지", JOptionPane.WARNING_MESSAGE,
        setImage.img_confirm);
  }

  /**
   * 이미 있는 닉네임
   */
  public void exist_mnick() {
    nickTnF = false;
    JOptionPane.showMessageDialog(client, "이미 존재하는 닉네임입니다.", "마이페이지",
        JOptionPane.WARNING_MESSAGE, setImage.img_exist);
  }

  /**
   * 닉네임 수정 성공
   *
   * @param userNick
   */
  public void edit_mypage(String userNick) {
    dbNick = userNick;
    client.main.setNick(userNick);
    JOptionPane.showMessageDialog(client, "닉네임 변경이 완료되었습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
        setImage.img_add);
    client.setContentPane(client.main.jp_main);
    client.setTitle(title);
    if ("친구 목록".equals(title)) {
      client.main.jlb_secChan.setText(userNick + "님의 친구");
    } else if ("채팅 목록".equals(title)) {
      client.main.jlb_secChan.setText(userNick + "님의 채팅방");
    }
    client.revalidate();
  }

  /**
   * 닉네임 수정 실패
   */
  public void fail_mypage() {
    JOptionPane.showMessageDialog(client, "닉네임 변경에 실패하였습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
        setImage.img_delete);
  }

  /**
   * 닉네임, 비번 둘 다 수정 성공
   */
  public void edit_mboth(String userNick) {
    dbNick = userNick;
    client.main.setNick(userNick);
    JOptionPane.showMessageDialog(client, "닉네임, 비밀번호 변경이 완료되었습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
        setImage.img_add);
    client.setContentPane(client.main.jp_main);
    client.setTitle(title);
    if ("친구 목록".equals(title)) {
      client.main.jlb_secChan.setText(userNick + "님의 친구");
    } else if ("채팅 목록".equals(title)) {
      client.main.jlb_secChan.setText(userNick + "님의 채팅방");
    }
    client.revalidate();
  }

  /**
   * 닉네임, 비번 둘 다 수정 실패
   */
  public void fail_mboth() {
    JOptionPane.showMessageDialog(client, "닉네임,비밀번호 변경에 실패하였습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
        setImage.img_delete);
  }

  /**
   * 비밀번호만 변경 성공
   */
  public void edit_mpw() {
    JOptionPane.showMessageDialog(client, "비밀번호 변경이 완료되었습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
        setImage.img_add);
    client.setContentPane(client.main.jp_main);
    client.setTitle(title);
    client.revalidate();
  }

  /**
   * 비밀번호만 변경 실패
   */

  public void fail_mpw() {
    JOptionPane.showMessageDialog(client, "비밀번호 변경에 실패하였습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
        setImage.img_delete);
  }

  // 회원탈퇴
  public void del_acnt() {
    JOptionPane.showMessageDialog(jd_resign, "바나나톡 탈퇴가 완료되었습니다.", "회원탈퇴",
        JOptionPane.WARNING_MESSAGE,
        setImage.img_packed);
    jd_resign.dispose();
    client.setContentPane(client.jp_login);
    client.setTitle("바나나톡");
    client.revalidate();

  }

  public void fail_dacnt() {
    JOptionPane.showMessageDialog(jd_resign, "비밀번호가 일치하지 않습니다.", "회원탈퇴", JOptionPane.WARNING_MESSAGE,
        setImage.img_notFound);
  }

  /**
   * ActionListener 메소드
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();
    String userNick = jtf_nickName.getText();
    String pwFirst = jtf_userPw.getText();
    String pwSecond = jtf_userPwRe.getText();
    // 닉네임, 비밀번호 정규식
    String nickCheck = "^[a-zA-Z가-힣ㄱ-ㅎ0-9]{2,16}"; // 영문, 한글, 숫자 닉네임 2~10자
    String pwCheck = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$"; // 숫자,영문자포함 8~16자 비밀번호
    // 닉네임 중복확인 버튼 눌렀을 때
    if (obj == jbtn_checkNick) {
      // 원래 닉네임과 같은 경우
      if (dbNick.equals(userNick)) {
        JOptionPane.showMessageDialog(client, "기존에 사용하던 닉네임입니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
            setImage.img_notFound);
      }
      // 닉네임이 형식에 안맞음
      else if (!Pattern.matches(nickCheck, userNick)) {
        JOptionPane.showMessageDialog(client, "닉네임은 2~10자의 영문, 한글, 숫자로 입력해주세요.", "회원가입",
            JOptionPane.WARNING_MESSAGE, setImage.img_info);
      }
      // 그 외의 경우
      else {
        try {
          client.oos.writeObject(Protocol.NICK_MCHK
              + Protocol.seperator + userNick);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    }

    // 확인버튼을 눌렀을 때
    if (obj == jbtn_save || obj == jtf_nickName || obj == jtf_userPw || obj == jtf_userPwRe) {
      // 비밀번호 1, 2가 다를 경우
      if (!pwFirst.equals(pwSecond)) {
        JOptionPane.showMessageDialog(client, "비밀번호가 일치하지 않습니다.", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
            setImage.img_notFound);
      }
      // 입력 닉네임이 DB와 다를 경우
      else if (!dbNick.equals(userNick)) {
        // 아이디 중복확인 안했을 경우
        if (!nickTnF) {
          JOptionPane.showMessageDialog(client, "닉네임 중복확인을 해주세요", "마이페이지", JOptionPane.INFORMATION_MESSAGE,
              setImage.img_info);
        }

        // 닉네임, 비밀번호 변경
        else if (!pwFirst.equals("")) {
          // 비밀번호 형식이 아닐 경우
          if (!Pattern.matches(pwCheck, pwFirst)) {
            JOptionPane.showMessageDialog(client, "비밀번호는 숫자와 영문자를 포함하여 8~16자로 입력해주세요.", "마이페이지",
                JOptionPane.WARNING_MESSAGE, setImage.img_info);
          } else { // 해야하는것
            // 비밀번호 형식일 경우 -> DB에 있는 비밀번호와 닉네임을 둘 다 변경
            try {
              client.oos.writeObject(Protocol.EDIT_MBOTH
                  + Protocol.seperator + userNick
                  + Protocol.seperator + pwFirst
                  + Protocol.seperator + dbId);
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          }
        }
        // 닉네임만 변경 516#닉네임#아이디
        else {
          try {
            client.oos.writeObject(Protocol.EDIT_MNICK
                + Protocol.seperator + userNick
                + Protocol.seperator + dbId);
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      }
      // 비밀번호만 변경 518#아이디#비밀번호
      else if (!pwFirst.equals("")) {
        try {
          client.oos.writeObject(Protocol.EDIT_MPW
              + Protocol.seperator + dbId
              + Protocol.seperator + pwFirst);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }

      // 변경사항이 없는 경우
      else {
        client.setContentPane(client.main.jp_main);
        client.setTitle(title);
        client.revalidate();
      }
    }

    // 탈퇴하기 버튼을 눌렀을 때 524#
    else if (obj == jbtn_resign) {
      jd_resign.setVisible(true);
      jtf_resignId.setText(dbId);
      jtf_resignId.setEditable(false);

    }

    // JDg 속 탈퇴하기 버튼 눌렀을 때
    else if (obj == jbtn_realresign || obj == jtf_resignId || obj == jtf_resignPw) {
      String userId = jtf_resignId.getText();
      String userPw = jtf_resignPw.getText();

      // 아이디가 로그인 아이디와 다를경우
      if (!userId.equals(dbId)) {
        JOptionPane.showMessageDialog(jd_resign, "아이디가 일치하지 않습니다.", "회원탈퇴", JOptionPane.WARNING_MESSAGE,
            setImage.img_notFound);
      }
      // 비밀번호가 공백일 경우 경고문 띄우기
      else if (userPw.equals("")) {
        JOptionPane.showMessageDialog(jd_resign, "비밀번호를 입력해주세요.", "회원탈퇴",
            JOptionPane.WARNING_MESSAGE, setImage.img_info);
      }
      // 그 외의경우 탈퇴시작
      else {
        // 확인하는 JOP
        int result = JOptionPane.showConfirmDialog(jd_resign, "정말로 바나나톡을 탈퇴하시겠습니까?", "회원탈퇴",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, setImage.img_delete);
        // yes를 눌렀을 때
        if (result == JOptionPane.YES_OPTION) {
          // 아이디 비밀번호 DB체크
          try {
            client.oos.writeObject(Protocol.DEL_ACNT + Protocol.seperator + userId + Protocol.seperator + userPw);
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        }
      }
    }
    // 돌아가기 버튼을 눌렀을 때
    else if (obj == jbtn_back) {
      jd_resign.dispose();
    }
  }

  /**
   * 테스트용메인
   *
   * @param args
   */
  public static void main(String[] args) {
    Client c = new Client();
    MyPage myPage = new MyPage(c, "test", "test");
    c.initDisplay();
    myPage.initDisplay();
  }
}