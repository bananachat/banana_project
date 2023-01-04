package banana_project.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Vector;

public class Main extends JFrame implements ActionListener {
    // "FListDialog" 호출
    FListDialog flDialog = new FListDialog(this);

    // [선언부]
    Vector<JButton> vList = new Vector<JButton>();

    // NORTH
    JButton jbtn_myPage = new JButton("마이페이지");
    JButton jbtn_firChan = new JButton("새 채팅");   // 친구추가 | 새 채팅

    // CENTER
    JLabel jlb_secChan = new JLabel("채팅 목록");    // 친구 | 채팅 목록
    JPanel jp_secChan = new JPanel();   // TODO: 중앙 패널 <- 리스트
    JButton jbtn_list = null;    // TODO: 친구 | 채팅 리스트
    JScrollPane jsp_display = new JScrollPane(jp_secChan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);    // TODO: 리스트를 스크롤

    // SOUTH
    JPanel jp_south = new JPanel();
    JButton jbtn_main = new JButton("내 화면");
    JButton jbtn_chat = new JButton("채팅방");


    public Main() {
        for (int i=0; i<20; i++) {
            jbtn_list = new JButton(Integer.toString(i));
            vList.add(jbtn_list);
        }
    } // end of ChatListView()

    public void initDisplay() {
        this.setLayout(null);

        // [north]
        jbtn_myPage.addActionListener(this);
        jbtn_myPage.setBounds(10, 10, 175, 40);
        this.add(jbtn_myPage);
        jbtn_firChan.addActionListener(flDialog);
        jbtn_firChan.setBounds(200, 10, 175, 40);
        this.add(jbtn_firChan);


        // [center]
        jlb_secChan.setBounds(10, 60, 330, 20);
        this.add(jlb_secChan);
        // 중앙 리스트 출력, TODO: 스크롤바로 나올 수 있게 해야함
        jsp_display.setBounds(5, 85, 375, 420);
        jp_secChan.setLayout(new GridLayout(vList.size(), 1));

        // 버튼을 벡터에 삽입
        for (int i=0; i<vList.size(); i++) {
            jp_secChan.add(vList.get(i));
            vList.get(i).addActionListener(this);
        }

//        this.add(jp_secChan);
        this.add(jsp_display);


        // [south]
        jbtn_main.addActionListener(this);
        jbtn_main.setBounds(10, 510, 175, 40);
        this.add(jbtn_main);
        jbtn_chat.addActionListener(this);
        jbtn_chat.setBounds(200, 510, 175, 40);
        this.add(jbtn_chat);

        // 창 위치 지정
        this.setLocation(500, 100);
        // 창 크기 설정
//        this.setSize(350, 600);
        this.setSize(400, 600);
        this.setTitle("채팅 목록");
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.out.println("종료합니다.");
                System.exit(0);
            }
        });
    } // end of initDisplay()

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("생성자 시작");
//        new Main();
        System.out.println("화면 출력");
        main.initDisplay();

    } // end of main()

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if(obj == jbtn_myPage) {
            // 마이페이지 클릭
            System.out.println("jbtn_myPage(마이페이지클릭) 클릭");
        }
        else if (obj == jbtn_main) {
            // 친구목록 클릭
            System.out.println("jbtn_myPage(내 화면) 클릭");
            this.setTitle("친구 목록");
            jbtn_firChan.setText("친구추가");
            jlb_secChan.setText("친구 목록");
        }
        else if (obj == jbtn_chat) {
            // 채팅목록 클릭
            System.out.println("jbtn_chat(채팅방) 클릭");
            this.setTitle("채팅 목록");
            jbtn_firChan.setText("새 채팅");
            jlb_secChan.setText("채팅 목록");
        }


        // 목록버튼 클릭
        for (int i=0; i<vList.size(); i++) {
            if (obj == vList.get(i)) {
                System.out.println("jbtn_list(" + vList.get(i).getText() +") 클릭");
            }
        }
    } // end of actionPerformed()
}
