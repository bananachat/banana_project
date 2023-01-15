package banana_project.client.main;

import banana_project.client.common.*;
import banana_project.client.login.Client;
import banana_project.client.mypage.MyPage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

import java.awt.*;
import java.awt.event.*;

import java.util.Vector;

public class Main implements ActionListener, MouseListener {
    ////////////////////////// [선언부] //////////////////////////
    MyPage myPage = null; // 마이페이지 선언
    FListDialog flDialog = null; // "친구 추가" 다이얼로그
    Client client = null;
    String logMsg = ""; // 로그 기록용

    /**
     * 화면부 선언
     */
    // 이미지, 폰트, JOp 세팅 불러오기
    SetImg setImage = new SetImg();
    SetFontNJOp setFontNJOp = new SetFontNJOp();

    // JP
    public JPanel jp_main = new JPanel(null);

    // [NORTH]
    JButton jbtn_myPage = new JButton("마이페이지");
    JButton jbtn_firChan = new JButton("친구 추가"); // "친구추가 | 새 채팅"으로 텍스트 변환

    // [CENTER]
    JLabel jlb_secChan = new JLabel("친구 목록"); // "친구 목록 | 채팅 목록"으로 텍스트 변환
    JPanel jp_center = new JPanel(); // 리스트 출력
    JScrollPane jsp_display = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    // 리스트를 JList 사용
    DefaultListModel<String> dlm = new DefaultListModel<String>();
    JList<String> jl_list = null;

    // [SOUTH]
    JButton jbtn_friends = new JButton("친구리스트");
    JButton jbtn_chat = new JButton("채팅방");

    ////////////////////////// [생성자] //////////////////////////
    public Main(Client client) {
        this.client = client;
        // JList 생성
        for (int i = 0; i < 20; i++) { // TODO: DB 테이블 정보를 받아와야한다 (현재 임의리스트)
            dlm.addElement(Integer.toString(i));
        }
        jl_list = new JList(dlm);
    }

    ////////////////////////// [화면 출력] //////////////////////////
    public void initDisplay() {
        // [north]
        jbtn_myPage.addActionListener(this); // jbtn_myPage 설정
        jbtn_myPage.setBorderPainted(false);
        jbtn_myPage.setBackground(new Color(130, 65, 60));
        jbtn_myPage.setForeground(Color.WHITE);
        jbtn_myPage.setFont(setFontNJOp.b14);
        jbtn_myPage.setBounds(20, 20, 160, 45);
        jp_main.add(jbtn_myPage);

        jbtn_firChan.addActionListener(this); // jbtn_firChan 설정
        jbtn_firChan.setBorderPainted(false);
        jbtn_firChan.setBackground(new Color(130, 65, 60));
        jbtn_firChan.setForeground(Color.WHITE);
        jbtn_firChan.setFont(setFontNJOp.b14);
        jbtn_firChan.setBounds(200, 20, 160, 45);
        jp_main.add(jbtn_firChan);

        // [center]
        jlb_secChan.setForeground(new Color(135, 90, 75));
        jlb_secChan.setFont(setFontNJOp.b12);
        jlb_secChan.setBounds(25, 73, 200, 20);
        jp_main.add(jlb_secChan);

        // 중앙 리스트 출력
        jsp_display.setBorder(new LineBorder(Color.white, 0));
        jsp_display.setBounds(20, 95, 340, 390);
        jsp_display.getVerticalScrollBar().setUnitIncrement(16);

        jl_list.addMouseListener(this);
        jl_list.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jl_list.setFont(setFontNJOp.b16);
        jl_list.setForeground(new Color(135, 90, 75));

        jp_center.setLayout(new GridLayout(jl_list.getMaxSelectionIndex(), 1));
        jp_center.add(jl_list);
        jp_main.add(jsp_display);

        // 스크롤바 설정
        jsp_display.getVerticalScrollBar().setBackground(Color.white);
        jsp_display.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(130, 65, 60);
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(Color.white);
                button.setBorder(new LineBorder(Color.white, 0));
                return button;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(Color.white);
                button.setBorder(new LineBorder(Color.white, 0));
                return button;
            }
        });

        // [south]
        jbtn_friends.addActionListener(this); // jbtn_friends 설정
        jbtn_friends.setBorderPainted(false);
        jbtn_friends.setForeground(new Color(130, 65, 60));
        jbtn_friends.setBackground(Color.white);
        jbtn_friends.setFont(setFontNJOp.b14);
        jbtn_friends.setBounds(20, 500, 160, 45);
        jp_main.add(jbtn_friends);

        jbtn_chat.addActionListener(this); // jbtn_chat 설정
        jbtn_chat.setBorderPainted(false);
        jbtn_chat.setBackground(new Color(130, 65, 60));
        jbtn_chat.setForeground(Color.WHITE);
        jbtn_chat.setFont(setFontNJOp.b14);
        jbtn_chat.setBounds(200, 500, 160, 45);
        jp_main.add(jbtn_chat);

        // [창 설정]
        client.setTitle("친구 목록");
        client.setContentPane(jp_main);
        client.setVisible(true);
    }

    ////////////////////////// [테스트용 메인메소드] //////////////////////////
    public static void main(String[] args) {
        Client c = new Client();
        Main main = new Main(c);
        c.initDisplay();
        main.initDisplay();
    } // end of main()

    ////////////////////////// [이벤트] //////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        // 마이페이지 버튼 클릭
        if (obj == jbtn_myPage) {
            myPage = new MyPage(client);
            myPage.initDisplay();
        }
        // Main 내 이벤트 발생
        // "친구 추가 / 새 채팅" 버튼 클릭
        else if (obj == jbtn_firChan) {
            flDialog = new FListDialog(this);
            System.out.println("jbtn_firChan(" + jbtn_firChan.getText() + ") 클릭");

            if ("친구 추가".equals(jbtn_firChan.getText())) {
                System.out.println("친구추가 로직 시작...");
                flDialog.setDialog(jbtn_firChan.getText(), true);
                // TODO: 친구추가 로직
            } else if ("새 채팅".equals(jbtn_firChan.getText())) {
                System.out.println("새 채팅 로직 시작...");
                flDialog.setDialog(jbtn_firChan.getText(), true);
                // TODO: 새 채팅 로직
            }
        } // end of Main 내 이벤트
          // 친구목록 버튼 클릭
        else if (obj == jbtn_friends) {
            System.out.println("jbtn_myPage(내 화면) 클릭");
            client.setTitle("친구 목록");
            jbtn_firChan.setText("친구 추가");
            jlb_secChan.setText("친구 목록");
            jsp_display.getVerticalScrollBar().setValue(0);

            // 활성화 버튼 색 변경
            jbtn_friends.setBackground(Color.white);
            jbtn_friends.setForeground(new Color(130, 65, 60));

            jbtn_chat.setBackground(new Color(130, 65, 60));
            jbtn_chat.setForeground(Color.WHITE);
        }
        // 채팅목록 버튼 클릭
        else if (obj == jbtn_chat) {
            System.out.println("jbtn_chat(채팅방) 클릭");
            client.setTitle("채팅 목록");
            jbtn_firChan.setText("새 채팅");
            jlb_secChan.setText("채팅 목록");
            jsp_display.getVerticalScrollBar().setValue(0);

            // 활성화 버튼 색 변경
            jbtn_chat.setBackground(Color.white);
            jbtn_chat.setForeground(new Color(130, 65, 60));

            jbtn_friends.setBackground(new Color(130, 65, 60));
            jbtn_friends.setForeground(Color.WHITE);
        }
    } // end of actionPerformed()

    // [[마우스 클릭 이벤트]] //
    @Override
    public void mouseClicked(MouseEvent e) {
        // jl_list 목록을 눌렀을때 이벤트
        System.out.println("jl_list(" + jl_list.getSelectedValue() + ") 클릭 이벤트");
        if (e.getComponent() == jl_list) {
            // jl_list 목록을 두번 눌렀을때 이벤트
            if (e.getClickCount() == 2) {
                String msg = jl_list.getSelectedValue() + "을 두번 눌렀습니다.";
                JOptionPane.showMessageDialog(client, msg, "info", JOptionPane.INFORMATION_MESSAGE);
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