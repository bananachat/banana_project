package banana_project.main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class FListDialog extends JDialog implements ActionListener, ListSelectionListener {
    // [선언부]
//    JDialog jdg = new JDialog();
    Main main = null;
    Vector<JButton> vList = new Vector<JButton>();

    // NORTH
    JPanel jp_north = new JPanel();
//    JLabel jlb_friends = new JLabel("친구 이름를 입력하세요");
    JTextField jtf_search = new JTextField("친구를 검색", 28);
    JButton jbtn_search = new JButton("검색");

    // CENTER
    JPanel jp_center = new JPanel();
    JScrollPane jsp_display = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);    // TODO: 리스트를 스크롤
    // 리스트를 버튼으로
    JButton jbtn_list = null;    // TODO: 친구 | 채팅 리스트
    // 리스트를 JList로
    DefaultListModel<String> dlm = new DefaultListModel<String>();
    JList<String> jl_list = null;
    Vector<String> copy_list = new Vector<>();

    // SOUTH
    JPanel jp_south = new JPanel();
    JButton jbtn_add = new JButton("추가");

    // [생성자]
    public FListDialog() {
        // TEST -다이얼로그 자체 컴파일용
        this.setSize(400, 300);
        this.setLocation(850, 500);
        this.setVisible(false);
    }
    public FListDialog(Main main) {
        this.main = main;
        this.setSize(400, 300);
        this.setLocation(850, 500);
        this.setVisible(false);
        copy_list.add(0, "임시사용자");
    }


    // 친구리스트(JList) 생성
    public void createList() {
        for (int i=0; i<10; i++) {
            dlm.addElement(Integer.toString(i));
        }
        jl_list = new JList(dlm);
    } // end of createList()


    // [메소드]
    public void setDialog(String title, boolean isView) {
        // [North]
        jp_north.setLayout(new BorderLayout());
        jp_north.setBounds(30, 30, 340, 50);
        jp_north.setBorder(BorderFactory.createEmptyBorder(5 , 5, 5 , 5));

//        jlb_friends.setForeground(Color.GRAY);
//        jtf_search.add(jlb_friends);
        jtf_search.addActionListener(this);
        jp_north.add("West",jtf_search);

        jbtn_search.setSize(50, 40);
        jbtn_search.addActionListener(this);
        jp_north.add("East", jbtn_search);

        // [Center]
        jsp_display.setBorder(BorderFactory.createEmptyBorder(5 , 5, 5 , 5));
        jsp_display.getVerticalScrollBar().setUnitIncrement(16);
        createList();
        jp_center.setLayout(new GridLayout(jl_list.getMaxSelectionIndex(), 1));

        // 리스트로 출력
//        // 단일 선택 모드
//        jl_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // 다중 선택 모드
        jl_list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jl_list.addListSelectionListener(this);
        jp_center.add(jl_list);


        // [South]
        jp_south.setLayout(new BorderLayout());
        jp_south.setBorder(BorderFactory.createEmptyBorder(5 , 5, 5 , 5));
        jbtn_add.setSize(50, 40);
        jbtn_add.addActionListener(this);
        jp_south.add("East", jbtn_add);



        this.add(jp_north, BorderLayout.NORTH);
        this.add(jsp_display, BorderLayout.CENTER);
        this.add(jp_south, BorderLayout.SOUTH);
        this.setTitle(title);
        this.setVisible(isView);
    }


//    public static void main(String[] args) {
//        ChatListViewDialog d = new ChatListViewDialog();
//        new ChatListViewDialog();
//        d.setDialog("test", true);
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        // Main 내 이벤트 발생
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
        }
        else if (obj == jbtn_search || obj == jtf_search) {
            // search 이벤트 호출
            System.out.println("search 이벤트 호출");
            System.out.println("입력값 : \"" + jtf_search.getText() + "\"");

        } else if (obj == jbtn_add) {
            // jbtn_add 클릭
            System.out.println("jbtn_add 클릭");

            String num = "";
            for (int i=0; i<copy_list.size()-1; i++) {
                num += (copy_list.get(i) + ", ");
            }
            num += (copy_list.get(copy_list.size()-1));
//            String num = jl_list.getSelectedValue();
            String msg = num + "을 추가합니다";

            // 친구 선택했는지 확인
            if (num == null) {
                msg = "친구를 선택하세요";
                JOptionPane.showMessageDialog(this, msg,"info",JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println(msg);

                for(String str : copy_list) {
                    System.out.println(str);
                }

                JOptionPane.showMessageDialog(this, msg, "info", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("친구검색 다이얼로그 종료");

                msg = "";
                num = "";
                this.dispose();
            }
        }

    }

    // JList 이벤트 호출
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            // 단일 선택 모드
            System.out.println("선택 : " + jl_list.getSelectedValue());

            // 선택한 친구들 정보 수집
            // 다중 선택 모드
            copy_list.add((String)jl_list.getSelectedValue());
        }
    }
}