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
   JPanel jp_join = new JPanel(null); // 회원가입 도화지
   // Jlb
   JLabel jlb_name = new JLabel("이름");
   JLabel jlb_id = new JLabel("아이디");
   JLabel jlb_pw = new JLabel("비밀번호");
   JLabel jlb_pw2 = new JLabel("비밀번호확인");
   JLabel jlb_phone = new JLabel("전화번호");
   JLabel jlb_nickName = new JLabel("닉네임");
   JLabel jlb_idAvble = new JLabel("사용가능한 아이디 입니다.");
   JLabel jlb_idNotAvble = new JLabel("중복된 아이디 입니다.");
   // Jtf
   JTextField jtf_userName = new JTextField("BananaTalk"); // 이름
   JTextField jtf_userId = new JTextField("example@email.com"); // 아이디
   JPasswordField jtf_userPw = new JPasswordField("password"); // 비밀번호 입력창
   JPasswordField jtf_userPwRe = new JPasswordField("password"); // 비밀번호 확인 입력창
   JTextField jtf_userHp = new JTextField("010-0000-0000"); // 폰번호
   JTextField jtf_nickName = new JTextField("Banana"); // 닉네임
   // Jbtn
   JButton jbtn_checkId = new JButton("중복검사"); // 아이디 중복검사 버튼
   JButton jbtn_checkNick = new JButton("중복검사"); // 닉네임 중복검사 버튼
   JButton jbtn_join = new JButton("회원가입");// 회원가입 버튼
   JButton jbtn_cancel = new JButton("취소");// 취소 버튼

   /**
    * 생성자
    * 
    * @param client
    */
   public MemJoin(Client client) {
      this.client = client;
   }

   public void initDisplay() {
      jbtn_join.addActionListener(this);
      jbtn_cancel.addActionListener(this);
      jbtn_checkId.addActionListener(this);

      jbtn_checkNick.addActionListener(this);
      jtf_userId.addFocusListener(this);
      jtf_userName.addFocusListener(this);
      jtf_userPw.addFocusListener(this);
      jtf_userPwRe.addFocusListener(this);
      jtf_userHp.addFocusListener(this);
      jtf_nickName.addFocusListener(this);

      // 타이틀 부분
      ImageIcon bookIcon = new ImageIcon(setImage.imgPath + "logo_title.png");
      client.setIconImage(bookIcon.getImage());
      client.setTitle("바나나톡");
      // 정보입력 부분
      jp_join.add(jtf_userName);// 이름
      jp_join.add(jlb_name);
      jtf_userName.setBounds(95, 100, 180, 45);
      jlb_name.setBounds(57, 100, 200, 45);
      jlb_name.setFont(setFontNJOp.p12);
      jtf_userName.setBorder(new LineBorder(Color.white, 8));// 테두리 없애기
      jp_join.add(jtf_userId);// 아이디
      jp_join.add(jlb_id);
      jtf_userId.setForeground(Color.gray);// 예시 글자색 회색
      jtf_userPw.setForeground(Color.gray);
      jtf_userPwRe.setForeground(Color.gray);

      jtf_userId.setBorder(new LineBorder(Color.white, 8));// 테두리 없애기

      jtf_userHp.setForeground(Color.gray);
      jtf_nickName.setForeground(Color.gray);
      jtf_userName.setForeground(Color.gray);
      jtf_userId.setBounds(95, 145, 180, 45);
      jlb_id.setBounds(45, 145, 200, 45);
      jlb_id.setFont(setFontNJOp.p12);
      jp_join.add(jlb_idAvble);// 아이디 중복검사 결과
      jlb_idAvble.setVisible(false);
      jp_join.add(jlb_idNotAvble);
      jlb_idAvble.setBounds(95, 180, 180, 45);
      jlb_idNotAvble.setBounds(95, 180, 200, 45);
      jlb_idNotAvble.setVisible(false);

      jp_join.add(jtf_userPw);// 비밀번호
      jp_join.add(jlb_pw);
      jtf_userPw.setBounds(95, 210, 180, 45);
      jlb_pw.setBounds(35, 210, 200, 45);
      jlb_pw.setFont(setFontNJOp.p12);
      jtf_userPw.setBorder(new LineBorder(Color.white, 8));// 테두리 없애기
      jp_join.add(jtf_userPwRe);// 비밀번호확인
      jp_join.add(jlb_pw2);
      jtf_userPwRe.setBounds(95, 255, 180, 45);
      jlb_pw2.setBounds(10, 255, 200, 45);
      jlb_pw2.setFont(setFontNJOp.p12);
      jtf_userPwRe.setBorder(new LineBorder(Color.white, 8));// 테두리 없애기

      jp_join.add(jtf_userHp);// 전화번호
      jp_join.add(jlb_phone);
      jtf_userHp.setBounds(95, 300, 180, 45);
      jlb_phone.setBounds(35, 300, 200, 45);
      jlb_phone.setFont(setFontNJOp.p12);
      jtf_userHp.setBorder(new LineBorder(Color.white, 8));// 테두리 없애기
      jp_join.add(jtf_nickName);// 닉네임
      jp_join.add(jlb_nickName);
      jtf_nickName.setBounds(95, 340, 180, 45);
      jlb_nickName.setBounds(45, 340, 200, 45);
      jtf_nickName.setBorder(new LineBorder(Color.white, 8));// 테두리 없애기
      // JButton 설정
      jbtn_checkId.setBorderPainted(false); // 아이디 중복검사 버튼 외곽 라인 없애기
      jbtn_checkId.setForeground(Color.WHITE); // 아이디 중복검사 버튼 텍스트 색깔 (흰색)
      jbtn_checkId.setBackground(new Color(130, 65, 60)); // 아이디 중복검사 버튼 색깔 넣기 (지정색)
      jbtn_checkId.setBounds(285, 145, 90, 45);

      jbtn_checkNick.setBorderPainted(false); // 닉네임 중복검사 버튼 외곽 라인 없애기
      jbtn_checkNick.setForeground(Color.WHITE); // 닉네임 중복검사 버튼 텍스트 색깔 (흰색)
      jbtn_checkNick.setBackground(new Color(130, 65, 60)); // 닉네임 중복검사 버튼 색깔 넣기 (지정색)
      jbtn_checkNick.setBounds(285, 340, 90, 45);

      jbtn_join.setBorderPainted(false);
      jbtn_join.setBackground(new Color(130, 65, 60));// 회원가입버튼 배경색
      jbtn_join.setForeground(Color.white);
      jbtn_join.setFont(setFontNJOp.b14);
      jbtn_join.setBounds(200, 450, 130, 45);

      jbtn_cancel.setBorderPainted(false);
      jbtn_cancel.setBackground(new Color(130, 65, 60));// 취소버튼 배경색
      jbtn_cancel.setForeground(Color.white);
      jbtn_cancel.setFont(setFontNJOp.b14);
      jbtn_cancel.setBounds(60, 450, 130, 45);

      jp_join.add(jbtn_join);// 회원가입 버튼
      jp_join.add(jbtn_cancel);// 취소 버튼
      jp_join.add(jbtn_checkId);// 아이디 중복검사 버튼
      jp_join.add(jbtn_checkNick);// 닉네임 중복검사 버튼
      jp_join.setBackground(new Color(255, 230, 120)); // 도화지 색깔 노란색
      // JFrame, 회원가입 메인창 정의
      // client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      client.setTitle("회원가입");
      client.setContentPane(jp_join);
      // client.setSize(400, 600);
      // client.setLocationRelativeTo(null);// 창 가운데서 띄우기
      client.setVisible(true);
   }

   // 단위테스트용
   public static void main(String[] args) {
      Client c = new Client();
      // MemJoin memJoin = new MemJoin(c);
      c.initDisplay();
   }

   /**
    *
    * @param ae the event to be processed
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
   }//// end of actionPerformed

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
   }// end of focus gain

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
   }// end of focusLost
}// end of this class