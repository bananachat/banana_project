package banana_project.client.room;

import javax.swing.*;
import javax.swing.text.AbstractDocument.Content;

import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.CharArrayReader;

public class Pchat extends JFrame implements ActionListener {
    // 선언부
    // 기본 설정
    SetImg setImage = new SetImg();
    SetFontNJOp setFontNJOp = new SetFontNJOp();

    // 배경이미지
    String imgPath = "./app\\src\\main\\java\\banana_project\\image\\";
    ImageIcon iicon = new ImageIcon(imgPath + "room_back.png");
    JLabel background = new JLabel(new ImageIcon("room_back.png"));

    // 채팅창
    JTextArea chatArea = new JTextArea();
    JTextField jtf = new JTextField(24);
    JButton jbtn_send = new JButton("보내기");
    JButton jbtn_back = new JButton("이전");
    JPanel jp_Pchat = new JPanel(null);

    // 스크롤바
    JViewport viewport = new JViewport();
    
    String message;

    public Pchat() {// 디폴트생성자
    }

    // 화면그리기
    public void initDisplay() {
        JScrollPane jsp = new JScrollPane( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // 이벤트
        jbtn_send.addActionListener(this);
        jbtn_back.addActionListener(this);

        // NORTH////
        jbtn_back.setBounds(300, 30, 60, 30);
        jp_Pchat.add(jbtn_back);
        jbtn_back.setBorderPainted(false);
        jbtn_back.setBackground(new Color(130, 65, 60));
        jbtn_back.setForeground(Color.WHITE);

        // SOUTH///
        jtf.setBounds(15, 495, 295, 30);
        jbtn_send.setBounds(310, 495, 70, 30);
        jbtn_send.setBackground(new Color(130, 65, 60));
        jbtn_send.setForeground(Color.WHITE);
        jbtn_send.setBorderPainted(false);
        jp_Pchat.add(jtf);
        jp_Pchat.add(jbtn_send);

        // CENTER///
        chatArea.setBackground(new Color(255, 230, 120));
        chatArea.setBounds(3, 80, 350, 400);
        jp_Pchat.add(chatArea);
        
        /// 기본값//
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("바나나톡");
        this.setSize(400, 600);
        this.setVisible(true);
        chatArea.setEditable(false);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setContentPane(jp_Pchat);
        this.add(jsp);
        // this.add(jp_Pchat);
        jtf.addKeyListener(new KeyAdapter() {// 엔터키 입력
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    message = jtf.getText();
                    chatArea.append(message + "\n");
                    jtf.setText("");
                    // System.out.println("엔터키입력");
                }
            }
        });
        jbtn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if (obj == jbtn_send) {
                    message = jtf.getText();
                    chatArea.append(message + "\n");
                    // out.println(jtf.getText());
                    jtf.setText("");
                    // System.out.println("마우스클릭");
                }
            }
        });
    }// 화면 그리기 끝

    public static void main(String[] args) {
        Pchat room = new Pchat();
        room.initDisplay();

    }// main 끝

    @Override
    public void actionPerformed(ActionEvent e1) {
        // TODO Auto-generated method stub
        Object obj = e1.getSource();
        if (obj == jbtn_back) {

            dispose();

        }
    }
}
