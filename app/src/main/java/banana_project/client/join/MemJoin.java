package banana_project.client.join;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

import banana_project.server.util.DBConnectionMgr;
//회원가입 DB연동 

public class MemJoin extends JFrame implements ActionListener {
   boolean isIdCheck = false;
   //이미지
   String imgPath = "./app\\src\\main\\java\\banana_project\\image\\"; // 경로+
   // 폰트
   Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통12 폰트
   Font f_join = new Font("맑은 고딕", Font.BOLD, 25);//볼드25 폰트
   JFrame jdl_join = new JFrame(); // 회원가입 프레임
   JPanel jp_join = new JPanel(null); // 회원가입 도화지
   //Jlb
   JLabel jlb_name = new JLabel("이름");
   JLabel jlb_id = new JLabel("아이디");
   JLabel jlb_pw = new JLabel("비밀번호");
   JLabel jlb_pw2 = new JLabel("비밀번호확인");
   JLabel jlb_birth = new JLabel("생년월일");
   JLabel jlb_phone = new JLabel("전화번호");
   JLabel jlb_nickName = new JLabel("닉네임");
   JLabel jlb_idAvble = new JLabel("사용가능한 아이디 입니다.");
   JLabel jlb_idNotAvble = new JLabel("중복된 아이디 입니다.");
   JLabel jlb_title = new JLabel("회원가입");// 회원가입 , title 라벨
   //Jtf
   JTextField jtf_userName = new JTextField("BananaTalk"); // 이름
   JTextField jtf_userId = new JTextField("example@email.com"); // 아이디
   JPasswordField jtf_userPw = new JPasswordField(" password"); // 비밀번호 입력창
   JPasswordField jtf_userPwRe = new JPasswordField(" password"); // 비밀번호 확인 입력창
   JTextField jtf_birth = new JTextField("2000-01-01"); // 생년월일
   JTextField jtf_phone = new JTextField("010-0000-0000"); // 폰번호
   JTextField jtf_nickName = new JTextField("Banana"); // 닉네임
   ImageIcon imgic_join = new ImageIcon(imgPath + "mini_exist_account.png"); // 로그인 버튼 이미지
   //Jbtn
   JButton jbtn_checkId = new JButton("중복검사"); // 아이디 중복검사 버튼
   JButton jbtn_checkNick = new JButton("중복검사"); // 닉네임 중복검사 버튼
   JButton jbtn_join = new JButton(imgic_join);// 회원가입 버튼

   public void initDisplay() {

      jbtn_join.addActionListener(this);
      jbtn_checkId.addActionListener(this);
      jbtn_checkNick.addActionListener(this);
      // 타이틀 부분
      ImageIcon bookIcon = new ImageIcon(imgPath + "logo_title.png");
      jdl_join.setIconImage(bookIcon.getImage());
      jdl_join.setTitle("바나나톡");
      // 정보입력 부분
      jp_join.add(jtf_userName);// 이름
      jp_join.add(jlb_name);
      jtf_userName.setBounds(95, 100, 180, 35);
      jlb_name.setBounds(57, 100, 200, 35);
      jlb_name.setFont(p12);
      jp_join.add(jtf_userId);// 아이디
      jp_join.add(jlb_id);
      jtf_userId.setForeground(Color.gray);//예시 글자색 회색
      jtf_userPw.setForeground(Color.gray);
      jtf_userPwRe.setForeground(Color.gray);
      jtf_birth.setForeground(Color.gray);
      jtf_phone.setForeground(Color.gray);
      jtf_nickName.setForeground(Color.gray);
      jtf_userName.setForeground(Color.gray);
      jtf_userId.setBounds(95, 145, 180, 35);
      jlb_id.setBounds(45, 145, 200, 35);
      jlb_id.setFont(p12);
      jp_join.add(jlb_idAvble);// 아이디 중복검사 결과
      jlb_idAvble.setVisible(false);
      jp_join.add(jlb_idNotAvble);
      jlb_idAvble.setBounds(95, 180, 180, 35);
      jlb_idNotAvble.setBounds(95, 180, 200, 35);
      jlb_idNotAvble.setVisible(false);
      jp_join.add(jtf_userPw);// 비밀번호
      jp_join.add(jlb_pw);
      jtf_userPw.setBounds(95, 210, 180, 35);
      jlb_pw.setBounds(35, 210, 200, 35);
      jlb_pw.setFont(p12);
      jp_join.add(jtf_userPwRe);// 비밀번호확인
      jp_join.add(jlb_pw2);
      jtf_userPwRe.setBounds(95, 255, 180, 35);
      jlb_pw2.setBounds(10, 255, 200, 35);
      jlb_pw2.setFont(p12);
      jp_join.add(jtf_birth);// 생년월일
      jp_join.add(jlb_birth);
      jtf_birth.setBounds(95, 300, 180, 35);
      jlb_birth.setBounds(35, 300, 200, 35);
      jlb_birth.setFont(p12);
      jp_join.add(jtf_phone);// 전화번호
      jp_join.add(jlb_phone);
      jtf_phone.setBounds(95, 345, 180, 35);
      jlb_phone.setBounds(35, 345, 200, 35);
      jlb_phone.setFont(p12);
      jp_join.add(jtf_nickName);// 닉네임
      jp_join.add(jlb_nickName);
      jtf_nickName.setBounds(95, 390, 180, 35);
      jlb_nickName.setBounds(45, 390, 200, 35);

      jbtn_checkId.setBorderPainted(false); // 아이디 중복검사 버튼 외곽 라인 없애기
      jbtn_checkId.setForeground(Color.WHITE); // 아이디 중복검사 버튼 텍스트 색깔 (흰색)
      jbtn_checkId.setBackground(new Color(130, 65, 60)); // 아이디 중복검사 버튼 색깔 넣기 (지정색)
      jbtn_checkId.setBounds(285, 145, 90, 35);

      jbtn_checkNick.setBorderPainted(false); // 닉네임 중복검사 버튼 외곽 라인 없애기
      jbtn_checkNick.setForeground(Color.WHITE); // 닉네임 중복검사 버튼 텍스트 색깔 (흰색)
      jbtn_checkNick.setBackground(new Color(130, 65, 60)); // 닉네임 중복검사 버튼 색깔 넣기 (지정색)
      jbtn_checkNick.setBounds(285, 390, 90, 35);

      jbtn_join.setBounds(42, 450, 300, 45);
      jlb_title.setFont(f_join);// 회원가입 라벨 붙이기
      jlb_title.setBounds(20, 30, 125, 45);
      jp_join.add(jlb_title);// 회원가입 라벨 왼쪽 상단에 붙이기
      jp_join.add(jbtn_join);// 회원가입 버튼
      jp_join.add(jbtn_checkId);// 아이디 중복검사 버튼
      jp_join.add(jbtn_checkNick);// 닉네임 중복검사 버튼
      jp_join.setBackground(new Color(255, 230, 120)); // 도화지 색깔 노란색
      // JFrame, 회원가입 메인창 정의
      jdl_join.setTitle("회원가입");
      jdl_join.setContentPane(jp_join);
      jdl_join.setSize(400, 600);
      jdl_join.setLocationRelativeTo(null);// 창 가운데서 띄우기
      jdl_join.setVisible(true);
   }

   // 단위테스트용
   public static void main(String[] args) {
      MemJoin join = new MemJoin();
      join.initDisplay();
   }

   @Override
   public void actionPerformed(ActionEvent ae) {
      Object obj = ae.getSource();
      //회원가입버튼을 눌렀을 때
      if (obj == jbtn_join) {
         System.out.println("회원가입 버튼 클릭");
         DBConnectionMgr dbMgr = new DBConnectionMgr();
         Connection con = null;
         PreparedStatement pstmt = null;
         StringBuilder sql = new StringBuilder();
         sql.append("insert into chat_member(mem_num,mem_id, mem_pw, mem_name, mem_nick");////순서중요한가요? 디비저장이름인가요?
         sql.append(",mem_hp, mem_birth, reg_date)");
         sql.append(" values(seq_cmember_num.nextval, ?,?,?,?,?,?,to_char(sysdate,'YYYY-MM-DD'))");
         int result = 0;
         try {
            con = dbMgr.getConnection();
            pstmt = con.prepareStatement(sql.toString());
            int i = 0;
            pstmt.setString(++i, getId());
            pstmt.setString(++i, getPw());
            pstmt.setString(++i, getName());
            pstmt.setString(++i, getNickName());
            pstmt.setString(++i, getHp());
            pstmt.setString(++i, getBirth());
            result = pstmt.executeUpdate();
         } catch (SQLException se) {
            System.out.println(sql.toString());
            System.out.println(se.toString());
         } catch (Exception e) {
            e.printStackTrace();
         }
         if (result == 1) {
            JOptionPane.showMessageDialog(this, "회원가입 성공");
         }
      } // end of if
   }//// end of actionPerformed
    // 각 컬럼의 값들을 설정하거나 읽어오는 getter/setter 메쏘드입니다.

   public String getName() {
      return jtf_userName.getText();
   }

   public void setName(String strName) {
      jtf_userName.setText(strName);
   }

   public String getId() {
      return jtf_userId.getText();
   }

   public void setId(String strId) {
      jtf_userId.setText(strId);
   }

   public String getPw() {
      return jtf_userPw.getText();
   }

   public void setPw(String strPw) {
      jtf_userPw.setText(strPw);
   }

   public String getNickName() {
      return jtf_nickName.getText();
   }

   public void setNickName(String strNickName) {
      jtf_nickName.setText(strNickName);
   }

   public String getHp() {
      return jtf_phone.getText();
   }

   public void setHp(String strHp) {
      jtf_phone.setText(strHp);
   }

   public String getBirth() {
      return jtf_birth.getText();
   }

   public void setBirth(String strBirth) {
      jtf_birth.setText(strBirth);
   }
}