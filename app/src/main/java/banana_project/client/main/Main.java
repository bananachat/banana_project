package banana_project.client.main;

import banana_project.client.common.*;
import banana_project.client.login.Client;
import banana_project.client.mypage.MyPage;
import banana_project.client.room.ChatRoom;
import banana_project.server.thread.Protocol;
import banana_project.server.vo.UserVO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class Main implements ActionListener, MouseListener {
    ////////////////////////// [선언부] //////////////////////////
    public Client client = null;
    public MyPage myPage = null; // 마이페이지 선언
    public FListDialog flDialog = null; // "친구 추가" 다이얼로그
    public ChatRoom chatRoom = null; // 1:1채팅 선언
    String logMsg = ""; // 로그 기록용
    // 유저정보
    String userId = null;
    String userNick = null;
    // 친구, 채팅 삭제용
    String selNick = "";
    String selChat = "";
    // 돌아가기 화면전환용 타이틀
    public String title = "";

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
    public JLabel jlb_secChan = new JLabel(); // "친구 목록 | 채팅 목록"으로 텍스트 변환
    JLabel jlb_del = new JLabel("<HTML><U>삭제하기</U></HTML>");
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
    public Main(Client client, String userId, String userNick) {
        this.client = client;
        this.userId = userId;
        this.userNick = userNick;
        jl_list = new JList<String>(dlm);
        // 사용자의 친구목록 불러오기 500#아이디
        try {
            client.oos.writeObject(Protocol.PRT_FRDLIST
                    + Protocol.seperator + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ////////////////////////// [화면 출력] //////////////////////////
    public void initDisplay() {
        // [north]
        // jbtn_myPage 설정
        jbtn_myPage.addActionListener(this);
        jbtn_myPage.setBorderPainted(false);
        jbtn_myPage.setBackground(new Color(130, 65, 60));
        jbtn_myPage.setForeground(Color.WHITE);
        jbtn_myPage.setFont(setFontNJOp.b14);
        jbtn_myPage.setBounds(20, 20, 160, 45);
        jp_main.add(jbtn_myPage);

        // jbtn_firChan 설정
        jbtn_firChan.addActionListener(this);
        jbtn_firChan.setBorderPainted(false);
        jbtn_firChan.setBackground(new Color(130, 65, 60));
        jbtn_firChan.setForeground(Color.WHITE);
        jbtn_firChan.setFont(setFontNJOp.b14);
        jbtn_firChan.setBounds(200, 20, 160, 45);
        jp_main.add(jbtn_firChan);

        // [center]
        // 친구|채팅 Jlb설정
        jlb_secChan.setForeground(new Color(135, 90, 75));
        jlb_secChan.setFont(setFontNJOp.b12);
        jlb_secChan.setBounds(25, 70, 200, 20);
        jlb_secChan.setText(userNick + "님의 친구");
        jp_main.add(jlb_secChan);

        jlb_del.addMouseListener(this);
        jlb_del.setForeground(new Color(135, 90, 75));
        jlb_del.setFont(setFontNJOp.b12);
        jlb_del.setBounds(308, 470, 200, 20);
        jp_main.add(jlb_del);

        // 중앙 리스트 출력
        jsp_display.setBorder(new LineBorder(Color.white, 0));
        jsp_display.setBounds(20, 93, 340, 375);
        jsp_display.getVerticalScrollBar().setUnitIncrement(16);
        jl_list.addMouseListener(this);
        jl_list.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        // jbtn_friends 설정
        jbtn_friends.addActionListener(this);
        jbtn_friends.setBorderPainted(false);
        jbtn_friends.setForeground(new Color(130, 65, 60));
        jbtn_friends.setBackground(Color.white);
        jbtn_friends.setFont(setFontNJOp.b14);
        jbtn_friends.setBounds(20, 500, 160, 45);
        jp_main.add(jbtn_friends);

        // jbtn_chat 설정
        jbtn_chat.addActionListener(this);
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

        // 타이틀 저장
        this.title = client.getTitle();
    }

    /**
     * 친구가 없을때 메소드
     */
    public void nf_frdlist() {
        jl_list.setFont(setFontNJOp.b14);
        dlm.removeAllElements();
        dlm.addElement("새로운 친구를 추가해보세요.");
        jl_list.setEnabled(false);
    }

    /**
     * 친구가 있을때 메소드
     */
    public void prt_frdlist(Vector<String> fList) {
        jl_list.setFont(setFontNJOp.b16);
        jl_list.setEnabled(true);
        dlm.removeAllElements();
        for (int i = 0; i < fList.size(); i++) {
            dlm.addElement(fList.get(i));
        }
    }

    /**
     * 채팅리스트 없을때 메소드
     */
    public void nf_chatList() {
        jl_list.setFont(setFontNJOp.b14);
        dlm.removeAllElements();
        dlm.addElement("새로운 채팅방을 만들어보세요.");
        jl_list.setEnabled(false);
    }

    /**
     * 채팅리스트 있을때 메소드
     * 
     * @param cList
     */
    public void print_chatList(Vector<String> cList) {
        jl_list.setFont(setFontNJOp.b16);
        jl_list.setEnabled(true);
        dlm.removeAllElements();

        for (int i = 0; i < cList.size(); i++) {
            dlm.addElement(cList.get(i)); // 번호|타이틀
        }
    }

    /**
     * 친구 삭제 성공
     */
    public void del_friend() {
        JOptionPane.showMessageDialog(client, "친구 삭제가 완료되었습니다.", "친구 목록", JOptionPane.WARNING_MESSAGE,
                setImage.img_add);
        selNick = "";
    }

    /**
     * 친구 삭제 실패
     */
    public void fail_del_friend() {
        JOptionPane.showMessageDialog(client, "친구 삭제에 실패하였습니다..", "친구 목록", JOptionPane.WARNING_MESSAGE,
                setImage.img_delete);
        selNick = "";
    }

    /**
     * 채팅방 삭제 성공
     */
    public void del_chat() {
        JOptionPane.showMessageDialog(client, "채팅방 삭제가 완료되었습니다.", "채팅 목록", JOptionPane.WARNING_MESSAGE,
                setImage.img_add);
        selChat = "";
    }

    /**
     * 채팅방 삭제 실패
     */
    public void fail_del_chat() {
        JOptionPane.showMessageDialog(client, "채팅방 삭제에 실패하였습니다..", "채팅 목록", JOptionPane.WARNING_MESSAGE,
                setImage.img_delete);
        selChat = "";
    }

    /**
     * 마이페이지 닉네임 받아오기
     * 
     * @param newNick
     */
    public void setNick(String newNick) {
        this.userNick = newNick;
    }

    ////////////////////////// [이벤트] //////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        // 마이페이지 버튼 클릭
        if (obj == jbtn_myPage) {
            myPage = new MyPage(client, userId, title);
            myPage.initDisplay();
        }

        // Main 내 이벤트 발생
        // "친구 추가 / 새 채팅" 버튼 클릭
        else if (obj == jbtn_firChan) {
            flDialog = new FListDialog(this, title);
            System.out.println("jbtn_firChan(" + jbtn_firChan.getText() + ") 클릭");
            // 친구추가 버튼 클릭
            if ("친구 추가".equals(jbtn_firChan.getText())) {
                System.out.println("친구추가 로직 시작...");
                flDialog.setDialog(jbtn_firChan.getText(), true);
                // TODO: 친구추가 로직
            }
            // 새 채팅 버튼 클릭
            else if ("새 채팅".equals(jbtn_firChan.getText())) {
                System.out.println("새 채팅 로직 시작...");
                flDialog.setDialog(jbtn_firChan.getText(), true);
                // TODO: 새 채팅 로직
            }
        } // end of Main 내 이벤트

        // 친구목록 버튼 클릭
        else if (obj == jbtn_friends) {
            System.out.println("jbtn_friends(친구리스트) 클릭");
            client.setTitle("친구 목록");
            // 타이틀 저장
            this.title = "친구 목록";
            jbtn_firChan.setText("친구 추가");
            jlb_secChan.setText(userNick + "님의 친구");
            jsp_display.getVerticalScrollBar().setValue(0);

            // 사용자의 친구목록 불러오기 500#아이디
            try {
                client.oos.writeObject(Protocol.PRT_FRDLIST
                        + Protocol.seperator + userId);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            // 활성화 버튼 색 변경
            jbtn_friends.setBackground(Color.white);
            jbtn_friends.setForeground(new Color(130, 65, 60));
            jbtn_chat.setBackground(new Color(130, 65, 60));
            jbtn_chat.setForeground(Color.WHITE);
        }

        // 채팅목록 버튼 클릭
        else if (obj == jbtn_chat) {
            UserVO userVO = new UserVO();
            System.out.println("jbtn_chat(채팅방) 클릭");
            client.setTitle("채팅 목록");
            // 타이틀 저장
            this.title = "채팅 목록";
            jbtn_firChan.setText("새 채팅");
            jlb_secChan.setText(userNick + "님의 채팅방");
            jsp_display.getVerticalScrollBar().setValue(0);
            // 사용자 채팅리스트 출력 502#아이디
            try {
                client.oos.writeObject(Protocol.PRT_CHATLIST
                        + Protocol.seperator + userId);
            } catch (Exception e2) {
                e2.printStackTrace();
            }

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
                // String msg = jl_list.getSelectedValue() + "을 두번 눌렀습니다.";
                // JOptionPane.showMessageDialog(client, msg, "info",
                // JOptionPane.INFORMATION_MESSAGE);
                // 채팅방을 더블클릭 -> 채팅방열기
                if ("채팅 목록".equals(client.getTitle())) {
                    selChat = (String) jl_list.getSelectedValue();
                    StringTokenizer selChatTok = new StringTokenizer(selChat, "|");
                    String chatNum = selChatTok.nextToken(); // 채팅방 번호
                    String userList = selChatTok.nextToken(); // 채팅방이름(유저리스트)
                    System.out.println("유저리스트" + userList);
                    // 채팅방 열림
                    chatRoom = new ChatRoom(client, userId, userNick, chatNum, userList, title, "old");
                    chatRoom.initDisplay();
                }
            }
        }

        // 친구 목록일 경우
        if ("친구 목록".equals(client.getTitle())) {
            selNick = (String) jl_list.getSelectedValue();
        }

        // 채팅 목록일 경우
        else if ("채팅 목록".equals(client.getTitle())) {
            selChat = (String) jl_list.getSelectedValue();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object obj = e.getSource();
        // 삭제 라벨 눌렀을 때
        if (obj == jlb_del) {
            // 친구 목록일 경우
            if ("친구 목록".equals(client.getTitle())) {
                // 선택한 값이 없을 경우
                if ("".equals(selNick) || selNick == null) {
                    JOptionPane.showMessageDialog(client, "삭제할 친구를 선택해주세요.", "친구 목록", JOptionPane.WARNING_MESSAGE,
                            setImage.img_info);
                }
                // 선택한 값이 있을 경우
                else {
                    // 확인하는 JOP
                    int result = JOptionPane.showConfirmDialog(client, selNick + "님을 삭제하시겠습니까?", "친구 목록",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, setImage.img_delete);
                    // yes를 눌렀을 때
                    if (result == JOptionPane.YES_OPTION) {
                        // 서버로 닉네임 전달 511#아이디#친구닉네임
                        try {
                            client.oos.writeObject(Protocol.DEL_FRIEND
                                    + Protocol.seperator + userId
                                    + Protocol.seperator + selNick);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
            // 채팅 목록일 경우
            else if ("채팅 목록".equals(client.getTitle())) {
                // 선택한 값이 없을 경우
                if ("".equals(selChat) || selChat == null) {
                    JOptionPane.showMessageDialog(client, "삭제할 채팅방을 선택해주세요.", "채팅 목록", JOptionPane.WARNING_MESSAGE,
                            setImage.img_info);
                }
                // 선택한 값이 있을 경우
                else {
                    // 확인하는 JOP
                    int result = JOptionPane.showConfirmDialog(client, "채팅방을 삭제하시겠습니까?", "채팅 목록",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, setImage.img_delete);
                    // yes를 눌렀을 때
                    if (result == JOptionPane.YES_OPTION) {
                        // 채팅리스트 "번호|타이틀"
                        // print_chatList() 참조
                        StringTokenizer selChatTok = new StringTokenizer(selChat, "|");
                        int chatNum = Integer.parseInt(selChatTok.nextToken()); // 채팅방 번호
                        // 서버로 닉네임 전달 512#채팅방번호
                        try {
                            client.oos.writeObject(Protocol.DEL_CHAT
                                    + Protocol.seperator + userId
                                    + Protocol.seperator + chatNum);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
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

    ////////////////////////// [테스트용 메인메소드] //////////////////////////
    public static void main(String[] args) {
        Client c = new Client();
        Main m = new Main(c, "test", "test");
        c.initDisplay();
        m.initDisplay();
    }
}