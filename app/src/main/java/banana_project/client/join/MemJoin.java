package banana_project.client.join;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.LineBorder;
import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.client.login.Client;

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
   JTextField jtf_userName = new JTextField(" 바나나"); // 이름 입력창
   JTextField jtf_userHp = new JTextField(" -없이 숫자만 입력"); // 핸드폰번호 입력창
   JTextField jtf_userId = new JTextField(" example@email.com"); // 아이디 입력창
   JTextField jtf_nickName = new JTextField(" banana"); // 닉네임 입력창
   JPasswordField jtf_userPw = new JPasswordField(" password"); // 비밀번호 입력창
   JPasswordField jtf_userPwRe = new JPasswordField(" password"); // 비밀번호 확인 입력창
   // Jbtn
   JButton jbtn_checkId = new JButton("중복검사"); // 아이디 중복검사 버튼
   JButton jbtn_checkNick = new JButton("중복검사"); // 닉네임 중복검사 버튼
   JButton jbtn_join = new JButton("회원가입");// 회원가입 버튼
   JButton jbtn_cancel = new JButton("돌아가기");// 돌아가기 버튼
   // Jlb
   JLabel jlb_name = new JLabel("이름");
   JLabel jlb_hp = new JLabel("핸드폰번호");
   JLabel jlb_id = new JLabel("아이디");
   JLabel jlb_nickName = new JLabel("닉네임");
   JLabel jlb_pw = new JLabel("비밀번호");
   JLabel jlb_pwRe = new JLabel("비밀번호 확인");
   JLabel jlb_pwtxt = new JLabel("숫자, 영문자포함 8~16자리");

   /**
    * 생성자
    * 
    * @param client
    */
   public MemJoin(Client client) {
      this.client = client;
   }

   public void initDisplay() {
      // 이벤트리스너
      jbtn_join.addActionListener(this);
      jbtn_cancel.addActionListener(this);
      jbtn_checkId.addActionListener(this);
      jbtn_checkNick.addActionListener(this);
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
      // Jtf 설정
      jtf_userName.setForeground(Color.gray);
      jtf_userId.setForeground(Color.gray);
      jtf_userHp.setForeground(Color.gray);
      jtf_nickName.setForeground(Color.gray);
      jtf_userPw.setForeground(Color.gray);
      jtf_userPwRe.setForeground(Color.gray);
      jtf_userName.setBounds(95, 90, 180, 45);
      jtf_userHp.setBounds(95, 145, 180, 45);
      jtf_userId.setBounds(95, 200, 180, 45);
      jtf_nickName.setBounds(95, 255, 180, 45);
      jtf_userPw.setBounds(95, 310, 180, 45);
      jtf_userPwRe.setBounds(95, 365, 180, 45);
      jtf_userName.setBorder(new LineBorder(Color.white, 8));
      jtf_userId.setBorder(new LineBorder(Color.white, 8));
      jtf_userHp.setBorder(new LineBorder(Color.white, 8));
      jtf_nickName.setBorder(new LineBorder(Color.white, 8));
      jtf_userPw.setBorder(new LineBorder(Color.white, 8));
      jtf_userPwRe.setBorder(new LineBorder(Color.white, 8));
      // 아이디 중복검사 버튼 설정
      jbtn_checkId.setBorderPainted(false); // 아이디 중복검사 버튼 외곽 라인 없애기
      jbtn_checkId.setBackground(new Color(130, 65, 60)); // 아이디 중복검사 버튼 색깔 넣기 (지정색)
      jbtn_checkId.setForeground(Color.WHITE); // 아이디 중복검사 버튼 텍스트 색깔 (흰색)
      jbtn_checkId.setFont(setFontNJOp.b12);
      jbtn_checkId.setBounds(285, 200, 85, 40);
      // 닉네임 중복검사 버튼 설정
      jbtn_checkNick.setBorderPainted(false); // 닉네임 중복검사 버튼 외곽 라인 없애기
      jbtn_checkNick.setBackground(new Color(130, 65, 60)); // 닉네임 중복검사 버튼 색깔 넣기 (지정색)
      jbtn_checkNick.setForeground(Color.WHITE); // 닉네임 중복검사 버튼 텍스트 색깔 (흰색)
      jbtn_checkNick.setFont(setFontNJOp.b12);
      jbtn_checkNick.setBounds(285, 255, 85, 40);
      // 회원가입 버튼 설정
      jbtn_join.setBorderPainted(false);
      jbtn_join.setBackground(new Color(130, 65, 60));// 회원가입버튼 배경색
      jbtn_join.setForeground(Color.white);
      jbtn_join.setFont(setFontNJOp.b14);
      jbtn_join.setBounds(200, 450, 130, 45);
      // 돌아가기 버튼 설정
      jbtn_cancel.setBorderPainted(false);
      jbtn_cancel.setBackground(new Color(130, 65, 60));// 취소버튼 배경색
      jbtn_cancel.setForeground(Color.white);
      jbtn_cancel.setFont(setFontNJOp.b14);
      jbtn_cancel.setBounds(60, 450, 130, 45);
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
      jlb_name.setBounds(57, 90, 200, 45);
      jlb_hp.setBounds(35, 145, 200, 45);
      jlb_id.setBounds(45, 200, 200, 45);
      jlb_nickName.setBounds(45, 255, 200, 45);
      jlb_pw.setBounds(35, 310, 200, 45);
      jlb_pwRe.setBounds(35, 365, 200, 45);
      jlb_pwtxt.setBounds(100, 400, 200, 45);
      // Jp 설정
      jp_join.setBackground(new Color(255, 230, 120));
      // Jf 설정
      client.setTitle("회원가입");
      client.setContentPane(jp_join);
      client.setVisible(true);
   }

   // 단위테스트용
   public static void main(String[] args) {
      Client c = new Client();
      c.initDisplay();
   }

   /**
    * ActionListener 메소드
    */
   @Override
   public void actionPerformed(ActionEvent ae) {
      Object obj = ae.getSource();
      // 회원가입버튼을 눌렀을 때
      if (obj == jbtn_join || obj == jtf_userId) {// || obj == jtf_userId
         System.out.println("회원가입 버튼 클릭");
         String userName = jtf_userName.getText();
         String userId = jtf_userId.getText();
         String userPw = jtf_userPw.getText();
         String userPwRe = jtf_userPwRe.getText();

         String userHp = jtf_userHp.getText();
         String userNick = jtf_nickName.getText();
         String idCheck = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$"; // 이메일 형식
         String pwCheck = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$"; // 비밀번호 형식(8~16자 숫자,영문자포함)
         // 이름 입력 안함
         if ("".equals(userName) || "BananaTalk".equals(userName)) {
            JOptionPane.showMessageDialog(client, "이름을 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE, setImage.img_info);
         } // 아이디 입력안함
         else if ("".equals(userId) || "example@email.com".equals(userId)) {
            JOptionPane.showMessageDialog(client, "이메일을 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         } // 아이디 형식안맞음, 이메일 형식이 아닐 경우
         else if (!Pattern.matches(idCheck, userId)) {
            JOptionPane.showMessageDialog(client, "example@email.com 형식으로 입력해주세요", "회원가입",
                  JOptionPane.WARNING_MESSAGE, setImage.img_info);
         } // 비밀번호, 비밀번호확인을 입력 안한경우
         else if ("".equals(userPw) || "".equals(userPwRe)) {
            JOptionPane.showMessageDialog(client, "비밀번호를 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         } // 비밀번호 확인 불일치
         else if (!userPw.equals(userPwRe)) {
            JOptionPane.showMessageDialog(client, "비밀번호 확인이 일치하지 않습니다.", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         } // 전화번호 입력안한경우
         else if ("".equals(userHp) || "010-0000-0000".equals(userHp)) {
            JOptionPane.showMessageDialog(client, "전화번호를 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         } // 닉네임을 입력안함
         else if ("".equals(userNick) || "Banana".equals(userNick)) {
            JOptionPane.showMessageDialog(client, "닉네임을 입력해주세요", "회원가입", JOptionPane.WARNING_MESSAGE,
                  setImage.img_info);
         } else {

         } // end of 입력안한경우들
      } // end of if 회원가입 버튼눌렀을때

      // 취소버튼
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
      // 이름 jtf를 클릭했을 때
      if (obj == jtf_userName) {
         if ("BananaTalk".equals(jtf_userName.getText())) {
            jtf_userName.setText("");
         }
      }
      // 아이디 jtf를 클릭했을 때
      if (obj == jtf_userId) {
         jtf_userId.setForeground(Color.black);
         if ("example@email.com".equals(jtf_userId.getText())) {
            jtf_userId.setText("");
         }
      }
      // 비밀번호 jtf를 클릭했을 때
      else if (obj == jtf_userPw) {
         jtf_userPw.setForeground(Color.black);
         if ("password".equals(jtf_userPw.getText())) {
            jtf_userPw.setText("");
         }
      }
      // 비밀번호확인 jtf를 클릭했을 때ㅎㅎ
      else if (obj == jtf_userPwRe) {
         jtf_userPwRe.setForeground(Color.black);
         if ("password".equals(jtf_userPwRe.getText())) {
            jtf_userPwRe.setText("");
         }
      }
      // 전화번호 jtf를 클릭했을 때
      else if (obj == jtf_userHp) {
         jtf_userHp.setForeground(Color.black);
         if ("010-0000-0000".equals(jtf_userHp.getText())) {
            jtf_userHp.setText("");
         }
      }
      // 닉네임 jtf를 클릭했을 때
      else if (obj == jtf_nickName) {
         jtf_nickName.setForeground(Color.black);
         if ("Banana".equals(jtf_nickName.getText())) {
            jtf_nickName.setText("");
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
            jtf_userName.setText("BananaTalk");
         }
      } // 아이디 공백 두고갔을때
      else if (obj == jtf_userId) {
         if ("".equals(jtf_userId.getText())) {
            jtf_userId.setForeground(Color.gray);
            jtf_userId.setText("example@email.com");
         }
      }
      // 비밀번호 jtf를 공백으로두고 벗어났을 때
      else if (obj == jtf_userPw) {
         if ("".equals(jtf_userPw.getText())) {
            jtf_userPw.setForeground(Color.gray);
            jtf_userPw.setText("password");
         }
      } // 비번확인
      else if (obj == jtf_userPwRe) {
         if ("".equals(jtf_userPwRe.getText())) {
            jtf_userPwRe.setForeground(Color.gray);
            jtf_userPwRe.setText("password");
         }
      } // 전화번호
      else if (obj == jtf_userHp) {
         if ("".equals(jtf_userHp.getText())) {
            jtf_userHp.setForeground(Color.gray);
            jtf_userHp.setText("010-0000-0000");
         }
      } // 닉네임
      else if (obj == jtf_nickName) {
         if ("".equals(jtf_nickName.getText())) {
            jtf_nickName.setForeground(Color.gray);
            jtf_nickName.setText("Banana");
         }
      }
   }
}