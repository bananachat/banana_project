package banana_project.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class FListDialog implements ActionListener {
    // [선언부]
    JDialog jdg = new JDialog();
    Main main = null;
    Vector<JButton> vList = new Vector<JButton>();


    JPanel jp_north = new JPanel();
    JTextField jtf_search = new JTextField("친구를 검색", 28);
    JButton jbtn_search = new JButton("검색");


    JPanel jp_center = new JPanel();
    JScrollPane jsp_display = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);    // TODO: 리스트를 스크롤
    //    JTextArea jta_display = new JTextArea("테스트");
    JButton jbtn_list = null;    // TODO: 친구 | 채팅 리스트

    JPanel jp_south = new JPanel();
    JButton jbtn_add = new JButton("추가");

    // [생성자]
    public FListDialog() {
        // TEST -다이얼로그 자체 컴파일용
        jdg.setSize(400, 300);
        jdg.setLocation(850, 500);
        jdg.setVisible(false);
    }
    public FListDialog(Main main) {
        this.main = main;
        jdg.setSize(400, 300);
        jdg.setLocation(850, 500);
        jdg.setVisible(false);
    }

    // 버튼 리스트 출력
    public void createList() {
        for (int i=0; i<10; i++) {
            jbtn_list = new JButton(Integer.toString(i));
            jbtn_list.addActionListener(this);
            vList.add(jbtn_list);
        }
    } // end of createList()

    // [메소드]
    public void setDialog(String title, boolean isView) {
        // [North]
        jp_north.setLayout(new BorderLayout());
        jp_north.setBounds(30, 30, 340, 50);
        jp_north.setBorder(BorderFactory.createEmptyBorder(5 , 5, 5 , 5));
        jtf_search.addActionListener(this);
        jp_north.add("West",jtf_search);

        jbtn_search.setSize(50, 40);
        jbtn_search.addActionListener(this);
        jp_north.add("East", jbtn_search);

        // [Center]
        jp_center.setLayout(new GridLayout(vList.size(), 1));
        jsp_display.setBorder(BorderFactory.createEmptyBorder(5 , 5, 5 , 5));
        // 버튼을 벡터에 삽입
        createList();
        for (int i=0; i<vList.size(); i++) {
            jp_center.add(vList.get(i));
        }


        // [South]
        jp_south.setLayout(new BorderLayout());
        jp_south.setBorder(BorderFactory.createEmptyBorder(5 , 5, 5 , 5));
        jbtn_add.setSize(50, 40);
        jbtn_add.addActionListener(this);
        jp_south.add("East", jbtn_add);



        jdg.add(jp_north, BorderLayout.NORTH);
        jdg.add(jsp_display, BorderLayout.CENTER);
        jdg.add(jp_south, BorderLayout.SOUTH);
        jdg.setTitle(title);
        jdg.setVisible(isView);
    }


//    public static void main(String[] args) {
//        ChatListViewDialog d = new ChatListViewDialog();
//        new ChatListViewDialog();
//        d.setDialog("test", true);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == main.jbtn_firChan) {
            // 친추/새채팅 클릭
            System.out.println("jbtn_firChan(" + main.jbtn_firChan.getText() +") 클릭");
            if ("친구추가".equals(main.jbtn_firChan.getText())) {
                System.out.println("친구추가 로직 시작...");

                setDialog(main.jbtn_firChan.getText(), true);
                // TODO: 친구추가 로직
            } else if ("새 채팅".equals(main.jbtn_firChan.getText())) {
                System.out.println("새 채팅 로직 시작...");

                setDialog(main.jbtn_firChan.getText(), true);
                // TODO: 새 채팅 로직
            }
        } else if (obj == jbtn_search || obj == jtf_search) {
            System.out.println("search 이벤트 호출");
            System.out.println("입력값 : \"" + jtf_search.getText() + "\"");

        } else if (obj == jbtn_add) {
            System.out.println("jbtn_add 클릭");
            JOptionPane.showMessageDialog(jdg, "추가합니다.","info",JOptionPane.INFORMATION_MESSAGE);
            jdg.dispose();
            System.out.println("친구검색 다이얼로그 종료");
        }

        // 친구 목록버튼 클릭
        for (int i=0; i<vList.size(); i++) {
            if (obj == vList.get(i)) {
                System.out.println("jbtn_list(" + vList.get(i).getText() +") 클릭");
            }
        }

    }
}