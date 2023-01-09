package banana_project.client.main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

public class FListDialog extends JDialog implements ActionListener, ListSelectionListener, FocusListener {
    ////////////////////////// [선언부] //////////////////////////
    Main main = null;

    // [NORTH]
    JPanel jp_north = new JPanel();
    JTextField jtf_search = new JTextField(28);
    JButton jbtn_search = new JButton("검색");

    // [CENTER]
    JPanel jp_center = new JPanel();
    JScrollPane jsp_display = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                                                , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    // 친구리스트를 JList로
    DefaultListModel<String> dlm = new DefaultListModel<String>();
    JList<String> jl_list = null;
    Vector<String> copy_list = new Vector<>();                                          // 선택한 친구들 리스트

    // SOUTH
    JPanel jp_south = new JPanel();
    JButton jbtn_add = new JButton("추가");


    Font p12 = new Font("맑은 코딕", Font.PLAIN, 12);    // 보통 12폰트
    Font b12 = new Font("맑은 코딕", Font.BOLD, 12);    // 볼드 12폰트
    Font b14 = new Font("맑은 코딕", Font.BOLD, 14);    // 볼드 14폰트


    ////////////////////////// [생성자] //////////////////////////
    public FListDialog() {
        // TEST -다이얼로그 자체 컴파일용
        this.getContentPane().setBackground(new Color(255,230,120));
        this.setSize(400, 300);
        this.setLocation(850, 500);
        this.setVisible(false);
    }
    public FListDialog(Main main) {
        this();
        this.main = main;
    }


    ////////////////////////// [메소드] //////////////////////////
    // 친구리스트(JList) 생성

    /**
     *
     */
    public void createList() {
        // TODO: 현재 임의의 리스트 출력
        for (int i=0; i<10; i++) {
            dlm.addElement(Integer.toString(i));
        }
        jl_list = new JList(dlm);
    } // end of createList()


    ////////////////////////// [화면출력] //////////////////////////

    /**
     *
     * @param title     다이얼로그 타이틀
     * @param isView    다이얼로그 출력 유무
     */
    public void setDialog(String title, boolean isView) {
        // [North]
        jp_north.setLayout(new BorderLayout());
        jp_north.setBounds(30, 30, 340, 50);
        jp_north.setBorder(BorderFactory.createEmptyBorder(5 , 5, 5 , 5));

        jtf_search.addActionListener(this);                                         // jtf_search : 친구 검색
        jtf_search.addFocusListener(this);
        jtf_search.setForeground(Color.GRAY);
        jtf_search.setText("친구 이름를 입력하세요");
        jtf_search.requestFocus(false);
        jp_north.add("West",jtf_search);

        jbtn_search.setSize(50, 40);                                      // jbtn_search : 친구 검색 버튼
        jbtn_search.setBorderPainted(false);
        jbtn_search.setBackground(new Color(130, 65, 60));
        jbtn_search.setForeground(Color.WHITE);
        jbtn_search.setFont(b12);
        jbtn_search.addActionListener(this);
        jp_north.add("East", jbtn_search);

        // [Center]
        jsp_display.setBorder(BorderFactory.createEmptyBorder(5 , 5, 5 , 5));
        jsp_display.getVerticalScrollBar().setUnitIncrement(16);
        jp_center.removeAll();

        // 친구 리스트 출력
        createList();
        jp_center.setLayout(new GridLayout(jl_list.getMaxSelectionIndex(), 1));

        // 리스트로 출력
        jl_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);   // 단일 선택 모드
        jl_list.addListSelectionListener(this);

        jp_center.add(jl_list);


        // [South]
        jp_south.setLayout(new BorderLayout());
        jp_south.setBorder(BorderFactory.createEmptyBorder(5 , 5, 5 , 5));
        jbtn_add.setSize(50, 40);
        jbtn_add.setBorderPainted(false);
        jbtn_add.setBackground(new Color(130, 65, 60));
        jbtn_add.setForeground(Color.WHITE);
        jbtn_add.setFont(b12);
        jbtn_add.addActionListener(this);
        jp_south.add("East", jbtn_add);


        this.add(jp_north, BorderLayout.NORTH);
        this.add(jsp_display, BorderLayout.CENTER);
        this.add(jp_south, BorderLayout.SOUTH);
//        this.getContentPane().setBackground(new Color(255,230,120));
        this.setResizable(false);
        this.setTitle(title);
        this.setVisible(isView);
    }


    ////////////////////////// [이벤트] //////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj == jbtn_search || obj == jtf_search) {
            // 친구 검색 이벤트 호출
            System.out.println("search 이벤트 호출");
            System.out.println("입력값 : \"" + jtf_search.getText() + "\"");

            // TODO: 검색한 친구 리스트 호출

        } // end of 친구 검색

        else if (obj == jbtn_add) {
            // "친구 추가 | 새 채팅" 이벤트 호출
            System.out.println("jbtn_add 클릭");
            String msg = "";            // 출력할 메시지
            String num = "";            // JList에서 선택한 친구 목록

            // 선택한 친구들 String
            if (copy_list.size() == 0) {
                // 친구 선택을 안했을 경우
                System.out.println("선택한 친구가 없음");
                msg = "친구를 선택하세요";
                JOptionPane.showMessageDialog(this, msg,"info",JOptionPane.INFORMATION_MESSAGE);
            } else {
                // 선택한 친구들 리스트, num 변수에 추가
                for (int i=0; i<copy_list.size()-1; i++) {
                    num += (copy_list.get(i) + ", ");
                }
                num += (copy_list.get(copy_list.size()-1) + " ");

                // 상황별 메시지 변경
                if ("친구 추가".equals(main.jbtn_firChan.getText())) {
                    System.out.println("친구 추가...");

                    msg = num + "을(를) 친구 추가합니다";
                } else if ("새 채팅".equals(main.jbtn_firChan.getText())) {
                    System.out.println("새 채팅...");

                    msg = num + "와(과) 채팅 시작합니다";
                }

                // 복사한 친구들 리스트 출력
                System.out.println("선택한 친구들 : " + num);

                JOptionPane.showMessageDialog(this, msg, "info", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(msg);

                // 선택한 친구 리스트 삭제
                this.dispose();

                System.out.println("친구검색 다이얼로그 종료");
            } // end of "친구 추가 | 새 채팅" 이벤트

        } // end of 친구|채팅 추가 이벤트

    } // end of ActionPerformed


    // JList 이벤트 호출
    @Override
    public void valueChanged(ListSelectionEvent e) {
//        // TODO: 이중 이벤트 발생

        if (!e.getValueIsAdjusting()) {
            // 선택한 친구들 정보 수집
            System.out.println("선택 : " + jl_list.getSelectedValue());

            // 다중 선택 모드
            copy_list.add((String)jl_list.getSelectedValue());
            System.out.println("다중 선택 : " + copy_list);

        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == jtf_search) {
            jtf_search.setForeground(Color.BLACK);

            if ("친구 이름를 입력하세요".equals(jtf_search.getText())) {
                jtf_search.setText("");
            }
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == jtf_search) {
            if ("".equals(jtf_search.getText())) {
                jtf_search.setForeground(Color.GRAY);
                jtf_search.setText("친구 이름를 입력하세요");
            }
        }
    }
}