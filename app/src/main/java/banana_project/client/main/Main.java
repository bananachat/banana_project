package banana_project.client.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Vector;

public class Main extends JFrame implements ActionListener, MouseListener {
    ////////////////////////// [선언부] //////////////////////////
    // "FListDialog" 호출

    // [NORTH]
    FListDialog flDialog = new FListDialog(this);
    JButton jbtn_myPage = new JButton("마이페이지");
    JButton jbtn_firChan = new JButton("새 채팅");   // "친구추가 | 새 채팅"으로 텍스트 변환

    // [CENTER]
    JLabel jlb_secChan = new JLabel("채팅 목록");    // "친구 목록 | 채팅 목록"
    JPanel jp_center = new JPanel();                    // 리스트 출력
    JScrollPane jsp_display = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    // 리스트를 JList 사용
    DefaultListModel<String> dlm = new DefaultListModel<String>();
    JList<String> jl_list = null;


    // [SOUTH]
    JPanel jp_south = new JPanel();
    JButton jbtn_friends = new JButton("친구리스트");
    JButton jbtn_chat = new JButton("채팅방");


    ////////////////////////// [생성자] //////////////////////////
    public Main() {
        // JList 생성
        for (int i=0; i<100; i++) {                 // TODO: DB 테이블 정보를 받아와야한다 (현재 임의리스트)
            dlm.addElement(Integer.toString(i));
        }
        jl_list = new JList(dlm);
    } // end of Main()


    ////////////////////////// [화면 출력] //////////////////////////
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

        // 중앙 리스트 출력
        jsp_display.setBounds(5, 85, 375, 420);
        jsp_display.getVerticalScrollBar().setUnitIncrement(16);

        jl_list.addMouseListener(this);
        jp_center.setLayout(new GridLayout(jl_list.getMaxSelectionIndex(), 1));
        jp_center.add(jl_list);
        this.add(jsp_display);


        // [south]
        jbtn_friends.addActionListener(this);
        jbtn_friends.setBounds(10, 510, 175, 40);
        this.add(jbtn_friends);
        jbtn_chat.addActionListener(this);
        jbtn_chat.setBounds(200, 510, 175, 40);
        this.add(jbtn_chat);

        // 창 위치 지정
        // this.setLocation(800, 300);
        // 창 크기 설정
        this.setSize(400, 600);
        this.setTitle("채팅 목록");
        this.setLocationRelativeTo(null); // 가운데 위치 -> 수정.위치변경했습니다!
        this.setResizable(false);
        this.setVisible(true);

        // JFrame 종료 시 이벤트
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.out.println("종료합니다.");
                System.exit(0);
            }
        });
    } // end of initDisplay()


    ////////////////////////// [메인메소드] //////////////////////////
    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("생성자 시작");
        System.out.println("화면 출력");
        main.initDisplay();

    } // end of main()


    ////////////////////////// [이벤트] //////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if(obj == jbtn_myPage) {
            // 마이페이지 클릭
            System.out.println("jbtn_myPage(마이페이지클릭) 클릭");
            // TODO: 마이페이지 로직
        }
        else if (obj == jbtn_friends) {
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
    } // end of actionPerformed()


    //              [[마우스 클릭 이벤트]]              //
    @Override
    public void mouseClicked(MouseEvent e) {
        // jl_list 목록을 눌렀을때 이벤트
        System.out.println("jl_list(" + jl_list.getSelectedValue() + ") 클릭 이벤트");
        if(e.getComponent() == jl_list){
            // jl_list 목록을 두번 눌렀을때 이벤트
            if(e.getClickCount() == 2){
                String msg = jl_list.getSelectedValue() + "을 두번 눌렀습니다.";
                JOptionPane.showMessageDialog(this, msg, "info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}