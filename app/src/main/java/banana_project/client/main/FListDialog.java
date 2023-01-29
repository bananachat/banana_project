package banana_project.client.main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.client.login.Client;
import banana_project.client.room.ChatRoom;
import banana_project.server.thread.Protocol;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

public class FListDialog extends JDialog implements ActionListener, ListSelectionListener, FocusListener {
    ////////////////////////// [선언부] //////////////////////////
    Main main = null;
    String chatNo = null;
    String userList = null;
    String title = null;

    /**
     * 화면부 선언
     */
    // 이미지, 폰트, JOp 세팅 불러오기
    SetImg setImage = new SetImg();
    SetFontNJOp setFontNJOp = new SetFontNJOp();

    // JP
    JPanel jp_fListDialog = new JPanel(null);

    // [NORTH]
    JTextField jtf_search = new JTextField("친구 이름를 입력하세요", 20);
    JButton jbtn_search = new JButton("검색");

    // [CENTER]
    JPanel jp_center = new JPanel();
    JScrollPane jsp_display = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    // 친구리스트를 JList로
    DefaultListModel<String> dlm = new DefaultListModel<String>();
    JList<String> jl_list = null;
    Vector<String> copy_list = new Vector<>(); // 선택한 친구들 리스트

    // SOUTH
    JButton jbtn_add = new JButton("추가");

    ////////////////////////// [생성자] //////////////////////////
    public FListDialog(Main main, String getTitle) {
        this.main = main;
        jl_list = new JList(dlm);

        // 임시 리스트 출력
        // createList();

        // 채팅목록일 경우에만 친구검색 후 출력
        if ("채팅 목록".equals(getTitle)) {
            // 친구검색 출력 600#아이디
            try {
                this.main.client.oos.writeObject(Protocol.PRT_USERS
                        + Protocol.seperator + this.main.userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    ////////////////////////// [메소드] //////////////////////////
    // TODO: 현재 임의의 리스트 출력
    // 초기 친구리스트 배열을 파라미터로 해야한다
    /**
     * 친구리스트(JList) 생성
     */
    public void createList() {
        for (int i = 0; i < 20; i++) {
            dlm.addElement(Integer.toString(i));
        }
    } // end of createList()

    ////////////////////////// [화면출력] //////////////////////////

    /**
     * 친구검색 다이얼로그 호출
     *
     * @param title  다이얼로그 타이틀
     * @param isView 다이얼로그 출력 유무
     */
    public void setDialog(String title, boolean isView) {
        this.title = title;

        // [North]
        jtf_search.addActionListener(this); // jtf_search : 친구 검색
        jtf_search.addFocusListener(this);
        jtf_search.requestFocus(false);
        jtf_search.setForeground(Color.GRAY);
        jtf_search.setBounds(20, 20, 228, 35);
        jtf_search.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        jp_fListDialog.add(jtf_search);

        jbtn_search.addActionListener(this);
        jbtn_search.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jbtn_search.setBorderPainted(false);
        jbtn_search.setBackground(new Color(130, 65, 60));
        jbtn_search.setForeground(Color.WHITE);
        jbtn_search.setFont(setFontNJOp.b12);
        jbtn_search.setBounds(260, 20, 55, 35);
        jp_fListDialog.add(jbtn_search);

        // [Center]
        // 친구 리스트 출력
        jp_center.removeAll();

        jsp_display.setBorder(new LineBorder(Color.white, 0));
        jsp_display.setBounds(20, 75, 294, 225);
        jsp_display.getVerticalScrollBar().setUnitIncrement(16);

        // 리스트로 출력
        // jl_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION); // 단일
        // 선택 모드
        jl_list.addListSelectionListener(this);
        jl_list.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        jl_list.setFont(setFontNJOp.b16);
        jl_list.setForeground(new Color(135, 90, 75));
        jp_center.setLayout(new GridLayout(jl_list.getMaxSelectionIndex(), 1));
        jp_center.add(jl_list);
        jp_fListDialog.add(jsp_display);

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

        // [South]
        jbtn_add.addActionListener(this);
        jbtn_add.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jbtn_add.setBorderPainted(false);
        jbtn_add.setBackground(new Color(130, 65, 60));
        jbtn_add.setForeground(Color.WHITE);
        jbtn_add.setFont(setFontNJOp.b12);
        jbtn_add.setBounds(258, 313, 55, 35);
        jp_fListDialog.add(jbtn_add);

        this.setTitle(title);
        this.setIconImage(setImage.img_title.getImage()); // 타이틀창 이미지
        this.setContentPane(jp_fListDialog);
        this.setSize(350, 400);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(main.client);
        this.setResizable(false);
        this.setVisible(isView);
    }

    /**
     * 친구가 있을 때 메소드
     * 
     * @param fList
     */
    public void prt_frdList(Vector<String> fList) {
        jl_list.setEnabled(true);
        dlm.removeAllElements();
        for (int i = 0; i < fList.size(); i++) {
            dlm.addElement(fList.get(i));
        }
    }

    /**
     * 친구가 없을 때 메소드
     */
    public void nf_frdList() {
        dlm.removeAllElements();
        dlm.addElement("사용자를 찾을 수 없습니다.");
        jl_list.setEnabled(false);
    }

    /**
     * 친구 추가 성공
     */
    public void add_friend() {
        dlm.clear(); // 친구리스트 초기화
        copy_list.clear(); // 선택한 친구리스트 초기화

        // 메인페이지 내 친구 목록 새로 호출
        // 사용자의 친구목록 불러오기 500#아이디
        try {
            main.client.oos.writeObject(Protocol.PRT_FRDLIST
                    + Protocol.seperator + main.userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("친구검색 다이얼로그 종료");
        this.dispose();
    }

    /**
     * 친구 추가 실패
     */
    public void fail_add_friend() {
        // 실패 후 필요한 작업 작성
        // 대충 이런?
        JOptionPane.showMessageDialog(this, "친구 추가에 실패하였습니다.", "친구 추가", JOptionPane.INFORMATION_MESSAGE,
                setImage.img_delete);
    }

    /**
     * 채팅방 생성 성공
     * 
     * @param userList
     * @param chatNo
     */
    public void create_chatroom(String userList, String chatNo) {
        dlm.clear(); // 친구리스트 초기화
        copy_list.clear(); // 선택한 친구리스트 초기화

        // 채팅방 열림
        main.chatRoom = new ChatRoom(main.client, main.userId, main.userNick, chatNo, userList, "채팅 목록", false);
        main.chatRoom.initDisplay();

        System.out.println("친구검색 다이얼로그 종료");
        this.dispose();
    }

    /**
     * 채팅방 생성 실패
     */
    public void fail_create_chatroom() {
        JOptionPane.showMessageDialog(this, "채팅방 생성 실패하였습니다.", "새 채팅", JOptionPane.INFORMATION_MESSAGE,
                setImage.img_delete);
    }

    ////////////////////////// [이벤트] //////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj == jbtn_search || obj == jtf_search) {
            // 친구 검색 이벤트 호출
            System.out.println("search 이벤트 호출");
            String nickname = jtf_search.getText();

            System.out.println("입력값 : \"" + nickname + "\"");

            if ("친구 추가".equals(title)) {
                // 모든 사용자 중에 검색 601#아이디#사용자닉네임
                try {
                    this.main.client.oos.writeObject(Protocol.SRCH_USERS
                            + Protocol.seperator + this.main.userId
                            + Protocol.seperator + nickname);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else if ("새 채팅".equals(title)) {
                // 친구 중에 검색 603#아이디#사용자닉네임
                try {
                    this.main.client.oos.writeObject(Protocol.SRCH_FRIEDNDS
                            + Protocol.seperator + this.main.userId
                            + Protocol.seperator + nickname);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } // end of 친구 검색

        else if (obj == jbtn_add) {
            // "친구 추가 | 새 채팅" 이벤트 호출
            System.out.println("jbtn_add 클릭");
            String msg = ""; // 출력할 메시지
            String num = ""; // JList에서 선택한 친구 목록

            // 선택한 친구들 String
            if (copy_list.size() == 0) {
                // 친구 선택을 안했을 경우
                System.out.println("선택한 친구가 없음");
                msg = "친구를 선택하세요";
                JOptionPane.showMessageDialog(this, msg, "친구 추가", JOptionPane.INFORMATION_MESSAGE,
                        setImage.img_info);
            } else {
                // 1명 선택했을 경우
                if (copy_list.size() == 1) {
                    num = (copy_list.get(0));
                }
                // 2명 이상 선택했을 경우
                else {
                    // 선택한 친구들 리스트, num 변수에 추가
                    for (int i = 0; i < copy_list.size() - 1; i++) {
                        num += (copy_list.get(i) + ", ");
                    }
                    // num += (copy_list.get(copy_list.size() - 1) + " "); -> ?
                    num += (copy_list.get(copy_list.size() - 1));
                }

                // 친구 추가 창일 경우
                if ("친구 추가".equals(title)) {
                    // if ("친구 추가".equals(main.jbtn_firChan.getText())) {
                    System.out.println("친구 추가...");

                    msg = num + "을(를) 친구 추가합니다";

                    // 서버 스레드로 친구 추가 프로토콜과 해당 유저 아이디, 친구 추가할 목록 전송
                    try {
                        main.client.oos.writeObject(
                                Protocol.ADD_FRIEND +
                                        Protocol.seperator + main.userId +
                                        Protocol.seperator + num);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                // 새 채팅 창일 경우
                else if ("새 채팅".equals(title)) {
                    // } else if ("새 채팅".equals(main.jbtn_firChan.getText())) {
                    System.out.println("새 채팅...");

                    msg = num + "와(과) 채팅 시작합니다";

                    String user = main.userNick; // 사용자 ID
                    num += ", " + user;

                    // 채팅방 만들기 606#아이디#닉네임리스트
                    try {
                        main.client.oos.writeObject(
                                Protocol.CREATE_CHAT +
                                        Protocol.seperator + main.userId +
                                        Protocol.seperator + num);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }

                // 복사한 친구들 리스트 출력
                System.out.println("선택한 친구들 : " + num);

                jl_list.removeListSelectionListener(this); // 이벤트 해제

                // JOptionPane.showMessageDialog(this, msg, "info",
                // JOptionPane.INFORMATION_MESSAGE);
                System.out.println(msg);

                // dlm.clear(); // 친구리스트 초기화
                // copy_list.clear(); // 선택한 친구리스트 초기화
                //
                // System.out.println("친구검색 다이얼로그 종료");
                // this.dispose();
            }
        } // end of 친구|채팅 추가 이벤트
    } // end of ActionPerformed

    // JList 클릭 이벤트 호출
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            // 선택한 계정
            String selValue = (String) jl_list.getSelectedValue();
            System.out.println("계정 : " + selValue);

            // 선택한 값 추가
            if (copy_list.size() == 0) {
                // 처음 선택 시
                copy_list.add(selValue);
            } else {
                // 선택한 리스트 중 중복값이 있는지 확인
                boolean isDup = copy_list.contains(selValue); // 중복 존재 시 true

                if (isDup) {
                    System.out.println("중복되는 값이 존재");
                } else {
                    System.out.println("새로운 계정 추가");
                    copy_list.add(selValue);
                }
            }
            System.out.println("선택한 리스트 : " + copy_list);
        } // end of if (리스트 클릭 이벤트)
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

    /**
     * 테스트용 메인
     * 
     * @param args
     */
    public static void main(String[] args) {
        Client c = new Client();
        Main m = new Main(c, "test", "test");
        c.initDisplay();
        m.initDisplay();
    }
}