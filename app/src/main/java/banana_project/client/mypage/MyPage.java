package banana_project.client.mypage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Font;
import java.awt.Color;

public class MyPage extends JFrame implements ActionListener{
  //선언부
  JTextField jtf_userName = new JTextField("이름"); //이름
  JTextField jtf_userHP = new JTextField("핸드폰번호"); //핸드폰번호
  JTextField jtf_userId = new JTextField("아이디(이메일)"); //아이디
  JTextField jtf_Nickname = new JTextField("닉네임"); //닉네임
  JButton jbtn_checkNick = new JButton("중복확인"); //닉네임 중복체크버튼
  JTextField jtf_userPw = new JTextField("비밀번호",10); //비밀번호
  JTextField jtf_userPwcheck = new JTextField("비밀번호 확인",10); //비밀번호 확인
  JTextField jtf_userStatMsg = new JTextField("상태메시지를 입력하세요."); //상태메시지
  JButton jbtn_resign = new JButton("탈퇴하기"); //탈퇴버튼
  JButton jbtn_save = new JButton("확인"); //확인버튼
  JPanel jp_south = new JPanel();
  JPanel jp_center = new JPanel();
  
  Font p12 = new Font("맑은고딕", Font.PLAIN, 12);
  Font b12 = new Font("맑은고딕", Font.BOLD, 12);
  Font b14 = new Font("맑은고딕", Font.BOLD, 14);

  //생성부
  MyPage(){
    initDisplay();
  }
  //화면출력부
  public void initDisplay(){
    jp_center.setLayout(new FlowLayout());
    jp_center.add("Center",jtf_userName);
    jtf_userName.setBounds(95, 100, 180, 35);
    jtf_userName.setForeground(Color.GRAY);
    jtf_userName.setEditable(false);
    jp_center.add("Center",jtf_userHP);
    jtf_userHP.setBounds(95, 100, 180, 35);
    jtf_userHP.setForeground(Color.GRAY);
    jtf_userHP.setEditable(false);
    jp_center.add("Center",jtf_userId);
    jtf_userId.setBounds(95, 100, 180, 35);
    jtf_userId.setForeground(Color.GRAY);
    jtf_userId.setEditable(false);
    jp_center.add("Center",jtf_Nickname);
    jtf_Nickname.setBounds(95, 100, 180, 35);
    jtf_Nickname.setForeground(Color.GRAY);
    jp_center.add("Center",jbtn_checkNick);
    jbtn_checkNick.setBorderPainted(false);
    jbtn_checkNick.setBackground(new Color(130,65,60));
    jbtn_checkNick.setForeground(Color.WHITE);
    jbtn_checkNick.setFont(b14);
    jp_center.add("Center",jtf_userPw);
    jtf_userPw.setBounds(95, 100, 180, 35);
    jtf_userPw.setForeground(Color.GRAY);
    jp_center.add("Center",jtf_userPwcheck);
    jtf_userPwcheck.setBounds(95, 100, 180, 35);
    jtf_userPwcheck.setForeground(Color.GRAY);
    jp_south.add(jtf_userStatMsg);
    jtf_userStatMsg.setBounds(95, 100, 200, 35);
    jtf_userStatMsg.setForeground(Color.GRAY);
    jp_south.add(jbtn_resign);
    jbtn_resign.setBorderPainted(false);
    jbtn_resign.setBackground(new Color(130,65,60));
    jbtn_resign.setForeground(Color.WHITE);
    jbtn_resign.setFont(b14);
    jp_south.add(jbtn_save);
    jbtn_save.setBorderPainted(false);
    jbtn_save.setBackground(new Color(130,65,60));
    jbtn_save.setForeground(Color.WHITE);
    jbtn_save.setFont(b14);
  
    
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(400,600);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setBackground(new Color(255, 230, 120));
    this.add("Center",jp_center);
    this.add("South",jp_south);
    
    
  }
  //메인
  public static void main(String[] args) {
    new MyPage();
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    Object obj = e.getSource();

  }
}
