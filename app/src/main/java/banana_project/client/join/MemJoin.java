package banana_project.client.join;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.LineBorder;
import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.client.login.Client;
import banana_project.server.thread.Protocol;

public class MemJoin implements ActionListener, FocusListener {
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
   JPanel jp_join = new JPanel(null);
   // Jtf
   JTextField jtf_userName = new JTextField(); // 이름 입력창
   JTextField jtf_userHp = new JTextField("-없이 숫자만 입력"); // 핸드폰번호 입력창
   JTextField jtf_userId = new JTextField("example@email.com"); // 아이디 입력창
   JTextField jtf_nickName = new JTextField(); // 닉네임 입력창
   JPasswordField jtf_userPw = new JPasswordField(); // 비밀번호 입력창
   JPasswordField jtf_userPwRe = new JPasswordField(); // 비밀번호 확인 입력창
   // Jbtn
   JButton jbtn_checkId = new JButton("중복확인"); // 아이디 중복검사 버튼
   JButton jbtn_checkNick = new JButton("중복확인"); // 닉네임 중복검사 버튼
   JButton jbtn_join = new JButton("회원가입");// 회원가입 버튼
   JButton jbtn_cancel = new JButton("돌아가기");// 돌아가기 버튼
   JButton jbtn_main = new JButton(setImage.img_join);// 회원가입 로고용 버튼
   // Jlb
   JLabel jlb_name = new JLabel("이름");
   JLabel jlb_hp = new JLabel("핸드폰번호");
   JLabel jlb_id = new JLabel("아이디");
   JLabel jlb_nickName = new JLabel("닉네임");
   JLabel jlb_pw = new JLabel("비밀번호");
   JLabel jlb_pwRe = new JLabel("비밀번호 확인");
   JLabel jlb_pwtxt = new JLabel("숫자, 영문자포함 8~16자리");
   // 중복체크
   boolean idTnF = false;
   boolean nickTnF = false;

   /**
    * 생성자
    * 
    * @param client
    */
   public MemJoin(Client client) {
      this.client = client;
   }

   /**
    * 화면부 메소드
    */
   public void initDisplay() {
      // 이벤트리스너
      jbtn_join.addActionListener(this);
      jbtn_cancel.addActionListener(this);
      jbtn_checkId.addActionListener(this);
      jbtn_checkNick.addActionListener(this);
      jbtn_main.addActionListener(this);
      jtf_userName.addActionListener(this);
      jtf_userId.addActionListener(this);
      jtf_userHp.addActionListener(this);
      jtf_nickName.addActionListener(this);
      jtf_userPw.addActionListener(this);
      jtf_userPwRe.addActionListener(this);
      jtf_userName.addFocusListener(this);
      jtf_userId.addFocusListener(this);
      jtf_userHp.addFocusListener(this);
      jtf_nickName.addFocusListener(this);
      jtf_userPw.addFocusListener(this);
      jtf_userPwRe.addFocusListener(this);
      // 패널에 추가
      jp_join.add(jtf_userName);
      jp_join.add(jtf_userId);
      jp_join.add(jtf_userHp);
      jp_join.add(jtf_nickName);
      jp_join.add(jtf_userPw);
      jp_join.add(jtf_userPwRe);
      jp_join.add(jbtn_join);
      jp_join.add(jbtn_cancel);
      jp_join.add(jbtn_checkId);
      jp_join.add(jbtn_checkNick);
      jp_join.add(jlb_name);
      jp_join.add(jlb_hp);
      jp_join.add(jlb_id);
      jp_join.add(jlb_nickName);
      jp_join.add(jlb_pw);
      jp_join.add(jlb_pwRe);
      jp_join.add(jlb_pwtxt);
      jp_join.add(jbtn_main);
      // Jtf 설정
      jtf_userName.setForeground(Color.black);
      jtf_userId.setForeground(Color.gray);
      jtf_userHp.setForeground(Color.gray);
      jtf_nickName.setForeground(Color.black);
      jtf_userPw.setForeground(Color.black);
      jtf_userPwRe.setForeground(Color.black);
      jtf_userName.setBounds(100, 135, 183, 40);
      jtf_userHp.setBounds(100, 187, 183, 40);
      jtf_userId.setBounds(100, 239, 183, 40);
      jtf_nickName.setBounds(100, 291, 183, 40);
      jtf_userPw.setBounds(100, 343, 183, 40);
      jtf_userPwRe.setBounds(100, 395, 183, 40);
      jtf_userName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      jtf_userId.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      jtf_userHp.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      jtf_nickName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      jtf_userPw.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      jtf_userPwRe.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      // 아이디 중복검사 버튼 설정
      jbtn_checkId.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      jbtn_checkId.setBorderPainted(false);
      jbtn_checkId.setBackground(new Color(130, 65, 60));
      jbtn_checkId.setForeground(Color.WHITE);
      jbtn_checkId.setFont(setFontNJOp.b12);
      jbtn_checkId.setBounds(295, 241, 75, 36);
      // 닉네임 중복검사 버튼 설정
      jbtn_checkNick.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      jbtn_checkNick.setBorderPainted(false);
      jbtn_checkNick.setBackground(new Color(130, 65, 60));
      jbtn_checkNick.setForeground(Color.WHITE);
      jbtn_checkNick.setFont(setFontNJOp.b12);
      jbtn_checkNick.setBounds(295, 293, 75, 36);
      // 돌아가기 버튼 설정
      jbtn_cancel.setBorderPainted(false);
      jbtn_cancel.setBackground(new Color(130, 65, 60));
      jbtn_cancel.setForeground(Color.white);
      jbtn_cancel.setFont(setFontNJOp.b14);
      jbtn_cancel.setBounds(70, 480, 120, 45);
      // 회원가입 버튼 설정
      jbtn_join.setBorderPainted(false);
      jbtn_join.setBackground(new Color(130, 65, 60));
      jbtn_join.setForeground(Color.white);
      jbtn_join.setFont(setFontNJOp.b14);
      jbtn_join.setBounds(210, 480, 120, 45);
      // Jlb
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
      jlb_name.setBounds(65, 135, 100, 40);
      jlb_hp.setBounds(30, 187, 100, 40);
      jlb_id.setBounds(53, 239, 100, 40);
      jlb_nickName.setBounds(53, 291, 100, 40);
      jlb_pw.setBounds(42, 343, 100, 40);
      jlb_pwRe.setBounds(16, 395, 100, 40);
      jlb_pwtxt.setBounds(104, 426, 150, 40);
      // 로고 이미지 설정
      jbtn_main.setBackground(new Color(255, 230, 120));
      jbtn_main.setBorderPainted(false); // 버튼 외곽선 없애기
      jbtn_main.setBounds(85, 22, 210, 90); // 바나나 이미지 고정
      // Jp
      jp_join.setBackground(new Color(255, 230, 120));
      // Jf 설정
      client.setTitle("회원가입");
      client.setContentPane(jp_join);
      client.setVisible(true);
   }

   // 단위테스트용
   public static void main(String[] args) {
      Client c = new Client();
      MemJoin m = new MemJoin(c);
      c.initDisplay();
      m.initDisplay();
   }

   /**
    * 이미 존재하는 계정 메소드
    */
   public void exist_acnt() {
      JOptionPane.showMessageDialog(client, "이미 존재하는 계정입니다.\n 아이디찾기를 진행해주세요.", "회원가입", JOptionPane.ERROR_MESSAGE,
            setImage.img_existAcnt);
   }

   /**
    * 회원가입 성공 메소드
    * 
    * @param userName
    */
   public void sign_sus(String userName) {
      JOptionPane.showMessageDialog(client, userName + "님의 바나나톡 가입을 축하합니다!", "회원가입", JOptionPane.ERROR_MESSAGE,
            setImage.img_confirm);
      client.setContentPane(client.jp_login);
      client.setTitle("바나나톡");
      client.revalidate();
   }

   /**
    * 회원가입 실패 메소드
    */
   public void sign_err() {
      JOptionPane.showMessageDialog(client, "회원가입에 실패하였습니다. 다시 시도해주세요.", "회원가입", JOptionPane.ERROR_MESSAGE,
            setImage.img_delete);
   }

   /**
    * ActionListener 메소드
    */
   @Override
   public void actionPerformed(ActionEvent ae) {
      Object obj = ae.getSource();
      // 회원가입버튼을 눌렀을 때
      if (obj == jbtn_join || obj == jtf_userName || obj == jtf_userHp || obj == jtf_userId || obj == jtf_nickName
            || obj == jtf_userPw || obj == jtf_userPwRe) {
         System.out.println("회원가입 버튼 클릭");
         String userName = jtf_userName.getText();
         String userHp = jtf_userHp.getText();
         String userId = jtf_userId.getText();
         String userNick = jtf_nickName.getText();
         String userPw = jtf_userPw.getText();
         String userPwRe = jtf_userPwRe.getText();
         // 이름, 핸드폰번호, 아이디, 닉네임, 비밀번호 정규식
         String nameCheck = "^[가-힣]{2,6}$"; // 한글 이름 2~6자
         String hpCheck = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$"; // 핸드폰번호 형식
         String idCheck = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"; // 이메일 형식
         String nickCheck = "^[a-zA-Z가-힣ㄱ-ㅎ0-9]{2,16}"; // 영문, 한글, 숫자 닉네임 2~10자
         String pwCheck = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$"; // 8~16자 숫자,영문자포함 8~16자 비밀번호
         // 이름 입력 안함
         if ("".equals(userName)) {
            JOptionPane.showMessageDialog(client, "이름을 입력해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 이름이 형식에 안맞음
         else if (!Pattern.matches(nameCheck, userName)) {
            JOptionPane.showMessageDialog(client, "이름은 2~6자의 한글로 입력해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 핸드폰번호 입력안한경우
         else if ("".equals(userHp) || "-없이 숫자만 입력".equals(userHp)) {
            JOptionPane.showMessageDialog(client, "핸드폰번호를 입력해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 핸드폰번호가 형식에 안맞음
         else if (!Pattern.matches(hpCheck, userHp)) {
            JOptionPane.showMessageDialog(client, "핸드폰번호는 -를 제외한 숫자만 입력해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 아이디 입력안함
         else if ("".equals(userId) || "example@email.com".equals(userId)) {
            JOptionPane.showMessageDialog(client, "이메일을 입력해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 아이디가 형식에 안맞음
         else if (!Pattern.matches(idCheck, userId)) {
            JOptionPane.showMessageDialog(client, "example@email.com 형식으로 입력해주세요.", "회원가입",
                  JOptionPane.WARNING_MESSAGE, setImage.img_info);
         }
         // 닉네임을 입력안함
         else if ("".equals(userNick)) {
            JOptionPane.showMessageDialog(client, "닉네임을 입력해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 닉네임이 형식에 안맞음
         else if (!Pattern.matches(nickCheck, userNick)) {
            JOptionPane.showMessageDialog(client, "닉네임은 2~10자의 영문, 한글, 숫자로 입력해주세요.", "회원가입",
                  JOptionPane.WARNING_MESSAGE, setImage.img_info);
         }
         // 비밀번호, 비밀번호 확인을 입력안한경우
         else if ("".equals(userPw) || "".equals(userPwRe)) {
            JOptionPane.showMessageDialog(client, "비밀번호를 입력해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 비밀번호 형식이 아닐 경우
         else if (!Pattern.matches(pwCheck, userPw)) {
            JOptionPane.showMessageDialog(client, "비밀번호는 숫자와 영문자를 포함하여 8~16자로 입력해주세요.", "로그인",
                  JOptionPane.WARNING_MESSAGE, setImage.img_info);
         }
         // 비밀번호 확인 불일치
         else if (!userPw.equals(userPwRe)) {
            JOptionPane.showMessageDialog(client, "비밀번호가 일치하지 않습니다.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 아이디 중복확인을 하지 않았을 경우
         else if (!idTnF) {
            JOptionPane.showMessageDialog(client, "아이디 중복확인을 해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 닉네임 중복확인을 하지 않았을 경우
         else if (!nickTnF) {
            JOptionPane.showMessageDialog(client, "닉네임 중복확인을 해주세요.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         }
         // 그 외의 경우 회원가입 시도
         else {
            try {
               client.oos.writeObject(Protocol.SIGN_UP
                     + Protocol.seperator + userId
                     + Protocol.seperator + userPw
                     + Protocol.seperator + userName
                     + Protocol.seperator + userHp
                     + Protocol.seperator + userNick);
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
      }
      // 아이디 중복확인 버튼을 눌렀을 때
      else if (obj == jbtn_checkId) {
         // String userId = jtf_userId.getText();
         // try {
         // client.oos.writeObject(Protocol.MAIL_CHK
         // + Protocol.seperator + userId);
         // } catch (IOException e) {
         // e.printStackTrace();
         // }
         idTnF = true;
         JOptionPane.showMessageDialog(client, "중복확인이 완료되었습니다.", "회원가입", JOptionPane.WARNING_MESSAGE,
               setImage.img_confirm);
      }
      // 닉네임 중복확인 버튼을 눌렀을 때
      else if (obj == jbtn_checkNick) {
         // String userNick = jtf_nickName.getText();
         // try {
         // client.oos.writeObject(Protocol.NICK_CHK
         // + Protocol.seperator + userNick);
         // } catch (IOException e) {
         // e.printStackTrace();
         // }
         nickTnF = true;
         JOptionPane.showMessageDialog(client, "중복확인이 완료되었습니다.", "회원가입", JOptionPane.WARNING_MESSAGE,
               setImage.img_confirm);
      }
      // 돌아가기 버튼을 눌렀을 때
      else if (obj == jbtn_cancel) {
         client.setContentPane(client.jp_login);
         client.setTitle("바나나톡");
         client.revalidate();
      }
   }

   /**
    * FocusListener 메소드
    */
   @Override
   public void focusGained(FocusEvent e) {
      Object obj = e.getSource();
      // 핸드폰번호 jtf를 클릭했을 때
      if (obj == jtf_userHp) {
         jtf_userHp.setForeground(Color.black);
         if ("-없이 숫자만 입력".equals(jtf_userHp.getText())) {
            jtf_userHp.setText("");
         }
      }
      // 아이디 jtf를 클릭했을 때
      if (obj == jtf_userId) {
         jtf_userId.setForeground(Color.black);
         if ("example@email.com".equals(jtf_userId.getText())) {
            jtf_userId.setText("");
         }
      }
   }

   @Override
   public void focusLost(FocusEvent e) {
      Object obj = e.getSource();
      // 핸드폰번호 jtf를 공백으로두고 벗어났을 때
      if (obj == jtf_userHp) {
         if ("".equals(jtf_userHp.getText())) {
            jtf_userHp.setForeground(Color.gray);
            jtf_userHp.setText("-없이 숫자만 입력");
         }
      }
      // 아이디 jtf를 공백으로두고 벗어났을 때
      else if (obj == jtf_userId) {
         if ("".equals(jtf_userId.getText())) {
            jtf_userId.setForeground(Color.gray);
            jtf_userId.setText("example@email.com");
         }
      }
   }
}