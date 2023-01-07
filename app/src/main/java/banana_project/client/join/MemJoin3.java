package banana_project.client.join;

import banana_project.server.util.DBConnectionMgr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//회원가입 DB연동 

public class MemJoin3 extends JFrame implements ActionListener {
   boolean isIdCheck = false;
   String imgPath = "./app\\src\\main\\java\\banana_project\\image\\";// 이미지파일 위치
   JFrame jdl_join = new JFrame(); // 회원가입 프레임
   JPanel jp_join = new JPanel(null); // 회원가입 도화지
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
   JTextField jtf_name = new JTextField("토마토"); // 이름
   JTextField jtf_id = new JTextField("tomato"); // 아이디
   JTextField jtf_pw = new JTextField("123"); // 비밀번호
   JTextField jtf_pw2 = new JTextField(); // 비밀번호 확인
   JTextField jtf_birth = new JTextField("2000-11-12"); // 생년월일
   JTextField jtf_phone = new JTextField("010-555-7777"); // 폰번호
   JTextField jtf_nickName = new JTextField("나신입"); // 닉네임
   ImageIcon imgic_join = new ImageIcon(imgPath + "bt_join.png"); // 로그인 버튼 이미지
   JButton jbtn_idconfirm = new JButton("중복검사"); // 로그인 버튼
   JButton jbtn_join = new JButton(imgic_join);// 회원가입 버튼
   Font f_join = new Font("맑은 고딕", Font.PLAIN, 25);
   Font f_label = new Font("맑은 고딕", Font.PLAIN, 12);

   public void initDisplay() {

      jbtn_join.addActionListener(this);
      jbtn_idconfirm.addActionListener(this);
      // 타이틀 부분
      ImageIcon bookIcon = new ImageIcon(imgPath + "title.png");
      jdl_join.setIconImage(bookIcon.getImage());
      jdl_join.setTitle("바나나톡");
      // 정보입력 부분
      jp_join.add(jtf_name);// 이름
      jp_join.add(jlb_name);
      jtf_name.setBounds(95, 100, 180, 35);
      jlb_name.setBounds(57, 100, 200, 35);
      jlb_name.setFont(f_label);
      jp_join.add(jtf_id);// 아이디
      jp_join.add(jlb_id);
      jtf_id.setBounds(95, 145, 180, 35);
      jlb_id.setBounds(45, 145, 200, 35);
      jlb_id.setFont(f_label);
      jp_join.add(jlb_idAvble);// 아이디 중복검사 결과
      jlb_idAvble.setVisible(false);
      jp_join.add(jlb_idNotAvble);
      jlb_idAvble.setBounds(95, 180, 180, 35);
      jlb_idNotAvble.setBounds(95, 180, 200, 35);
      jlb_idNotAvble.setVisible(false);
      jp_join.add(jtf_pw);// 비밀번호
      jp_join.add(jlb_pw);
      jtf_pw.setBounds(95, 210, 180, 35);
      jlb_pw.setBounds(35, 210, 200, 35);
      jlb_pw.setFont(f_label);
      jp_join.add(jtf_pw2);// 비밀번호확인
      jp_join.add(jlb_pw2);
      jtf_pw2.setBounds(95, 255, 180, 35);
      jlb_pw2.setBounds(10, 255, 200, 35);
      jlb_pw2.setFont(f_label);
      jp_join.add(jtf_birth);// 생년월일
      jp_join.add(jlb_birth);
      jtf_birth.setBounds(95, 300, 180, 35);
      jlb_birth.setBounds(35, 300, 200, 35);
      jlb_birth.setFont(f_label);
      jp_join.add(jtf_phone);// 전화번호
      jp_join.add(jlb_phone);
      jtf_phone.setBounds(95, 345, 180, 35);
      jlb_phone.setBounds(35, 345, 200, 35);
      jlb_phone.setFont(f_label);
      jp_join.add(jtf_nickName);// 닉네임
      jp_join.add(jlb_nickName);
      jtf_nickName.setBounds(95, 390, 180, 35);
      jlb_nickName.setBounds(45, 390, 200, 35);

      jbtn_idconfirm.setBorderPainted(false); // 아이디 중복검사 버튼 외곽 라인 없애기
      jbtn_idconfirm.setForeground(Color.WHITE); // 아이디 중복검사 버튼 텍스트 색깔 (흰색)
      jbtn_idconfirm.setBackground(new Color(64, 64, 64)); // 아이디 중복검사 버튼 색깔 넣기 (갈색)
      jbtn_idconfirm.setBounds(285, 145, 90, 35);
      jbtn_join.setBounds(42, 450, 300, 45);
      jlb_title.setFont(f_join);// 회원가입 라벨 붙이기
      jlb_title.setBounds(20, 30, 125, 45);
      jp_join.add(jlb_title);// 회원가입 라벨 왼쪽 상단에 붙이기
      jp_join.add(jbtn_join);// 회원가입 버튼
      jp_join.add(jbtn_idconfirm);// 아이디 중복검사 버튼
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
      MemJoin3 join = new MemJoin3();
      join.initDisplay();
   }

   @Override
   public void actionPerformed(ActionEvent ae) {
      Object obj = ae.getSource();
      if (obj == jbtn_join) {
         System.out.println("회원가입 버튼 클릭");
         DBConnectionMgr dbMgr = new DBConnectionMgr();
         Connection con = null;
         PreparedStatement pstmt = null;
         StringBuilder sql = new StringBuilder();
         sql.append("insert into chat_member(mem_num,mem_id, mem_pw, mem_name, mem_nick");
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
      return jtf_name.getText();
   }

   public void setName(String strName) {
      jtf_name.setText(strName);
   }

   public String getId() {
      return jtf_id.getText();
   }

   public void setId(String strId) {
      jtf_id.setText(strId);
   }

   public String getPw() {
      return jtf_pw.getText();
   }

   public void setPw(String strPw) {
      jtf_pw.setText(strPw);
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