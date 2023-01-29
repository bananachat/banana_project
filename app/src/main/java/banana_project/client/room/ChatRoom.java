package banana_project.client.room;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import banana_project.client.common.SetFontNJOp;
import banana_project.client.common.SetImg;
import banana_project.client.login.Client;
import banana_project.server.thread.Protocol;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class ChatRoom implements ActionListener, FocusListener {
    /**
     * 서버 연결부 선언
     */
    Client client = null;
    String userId = null;
    String userNick = null;
    String chatNo = null;
    String msg = null;
    StringTokenizer setUser = null;
    // 채팅참여유저 담기
    String userList = null;
    // 날짜변수
    String chatDate = "";
    // 돌아가기 setTitle용
    String title = "";

    /**
     * 화면부 선언
     */
    // 이미지, 폰트, JOp 세팅 불러오기
    SetImg setImage = new SetImg();
    SetFontNJOp setFontNJOp = new SetFontNJOp();
    // JP
    JPanel jp_Pchat = new JPanel(null);
    // [North]
    JButton jbtn_back = new JButton(setImage.img_backbtn);
    JButton jbtn_fNick = new JButton("친구 닉네임");

    // [South]
    JTextField jtf_chat = new JTextField(" 메시지를 입력하세요.", 20);
    JButton jbtn_send = new JButton("전송");

    // jtp 설정
    StyledDocument sd_display = new DefaultStyledDocument(new StyleContext());
    JTextPane jtp_chat = new JTextPane(sd_display);
    JScrollPane jsp_display = new JScrollPane(jtp_chat, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    /**
     * 생성자
     */
    public ChatRoom(Client client, String userId, String userNick, String chatNo, String userList, String title,
            String status) {
        this.client = client;
        this.userId = userId;
        this.userNick = userNick;
        this.chatNo = chatNo;
        this.userList = userList; // 닉네임, 닉네임 형식
        this.title = title;
        setUser = new StringTokenizer(userList, ", ");

        // 채팅방 상단 그룹채팅(참여멤버숫자) 표시
        if (setUser.countTokens() > 2) {
            jbtn_fNick.setText("그룹채팅(" + setUser.countTokens() + ")");
            client.setTitle("그룹 채팅");

        }
        // 채팅방 상단 1:1채팅 상대닉네임 표시
        else {
            String tempNick1 = setUser.nextToken();
            String tempNick2 = setUser.nextToken();
            if (!userNick.equals(tempNick1)) {
                jbtn_fNick.setText(tempNick1);
            } else {
                jbtn_fNick.setText(tempNick2);
            }
            client.setTitle("1:1 채팅");
        }
        if (!"new".equals(status)) {
            // 채팅방 정보 불러오기
            try {
                client.oos.writeObject(Protocol.CHAT_START
                        + Protocol.seperator + chatNo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 화면부 메소드
     */
    public void initDisplay() {
        // 이벤트리스너
        jbtn_send.addActionListener(this);
        jbtn_back.addActionListener(this);
        jtf_chat.addActionListener(this);
        jtf_chat.addFocusListener(this);

        // [North]
        // jbtn
        jbtn_back.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jbtn_back.setBorderPainted(false);
        jbtn_back.setBackground(new Color(255, 230, 120));
        jbtn_back.setForeground(new Color(130, 65, 60));
        jbtn_back.setBounds(21, 22, 30, 30);
        jp_Pchat.add(jbtn_back);
        // jlb
        jbtn_fNick.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jbtn_fNick.setBorderPainted(false);
        jbtn_fNick.setBackground(new Color(255, 230, 120));
        jbtn_fNick.setForeground(new Color(130, 65, 60));
        jbtn_fNick.setFont(setFontNJOp.b16);
        jbtn_fNick.setBounds(120, 20, 155, 30);
        jp_Pchat.add(jbtn_fNick);

        // [Center]
        // jsp
        jsp_display.setBorder(new LineBorder(Color.white, 0));
        jsp_display.setBounds(21, 65, 340, 410);
        jsp_display.getVerticalScrollBar().setUnitIncrement(16);
        jp_Pchat.add(jsp_display);

        // jtp 설정
        jtp_chat.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        jtp_chat.setFont(setFontNJOp.b16);
        jtp_chat.setBackground(Color.white);
        jtp_chat.setCaretPosition(jtp_chat.getDocument().getLength());
        jtp_chat.setEditable(false);

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
        // jtf
        jtf_chat.setBounds(21, 496, 268, 40);
        jtf_chat.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        jtf_chat.requestFocus(false);
        jtf_chat.setForeground(Color.gray);
        jp_Pchat.add(jtf_chat);

        jbtn_send.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jbtn_send.setBorderPainted(false);
        jbtn_send.setBackground(new Color(130, 65, 60));
        jbtn_send.setForeground(Color.WHITE);
        jbtn_send.setFont(setFontNJOp.b13);
        jbtn_send.setBounds(304, 497, 58, 38);
        jp_Pchat.add(jbtn_send);

        // Jf 설정
        client.setContentPane(jp_Pchat);
        client.setVisible(true);
    }

    /**
     * 채팅방 불러오기 메소드
     * 
     * @param rList
     */
    public void chat_start(List<Map<String, String>> rList) {
        for (int i = 0; i < rList.size(); i++) {
            String chatCont = rList.get(i).get("chatCont");
            String chatDate2 = rList.get(i).get("chatDate");
            String chatNick = rList.get(i).get("chatNick");
            chatCont = rList.get(i).get("chatCont");
            // 날짜가 바뀔때마다 채팅방에 출력
            if (!chatDate.equals(chatDate2)) {
                this.chatDate = chatDate2;
                StringTokenizer setDate = new StringTokenizer(chatDate, "/");
                while (setDate.hasMoreTokens()) {
                    String year = setDate.nextToken();
                    String month = setDate.nextToken();
                    String day = setDate.nextToken();
                    // 날짜 출력하기
                    StyledDocument doc = jtp_chat.getStyledDocument();
                    SimpleAttributeSet sas = new SimpleAttributeSet();
                    // 폰트색상
                    sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(255, 200, 20));
                    // 가운데정렬
                    // StyleConstants.setAlignment(sas, StyleConstants.ALIGN_CENTER);
                    doc.setParagraphAttributes(0, doc.getLength(), sas, false);
                    try {
                        sd_display.insertString(sd_display.getLength(),
                                "< " + year + "년 " + month + "월" + day + "일 >" + "\n", sas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 로그인유저가 친 채팅일 경우
            if (userNick.equals(chatNick)) {
                msg = userNick + ": " + wrapText(chatCont, userNick.length());
                // jtp text 설정
                StyledDocument doc = jtp_chat.getStyledDocument();
                SimpleAttributeSet sas = new SimpleAttributeSet();
                // 폰트 컬러
                sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(135, 90, 75));
                // 오른쪽 정렬
                // StyleConstants.setAlignment(sas, StyleConstants.ALIGN_RIGHT);
                doc.setParagraphAttributes(0, doc.getLength(), sas, false);
                try {
                    sd_display.insertString(sd_display.getLength(), msg + "\n", sas);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            // 다른 유저가 친 채팅일 경우
            else {
                msg = chatNick + ": " + wrapText(chatCont, chatNick.length());
                // jtp text 설정
                StyledDocument doc = jtp_chat.getStyledDocument();
                SimpleAttributeSet sas = new SimpleAttributeSet();
                // 폰트 컬러
                sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(80, 114, 167));
                // 왼쪽 정렬
                // StyleConstants.setAlignment(sas, StyleConstants.ALIGN_LEFT);
                doc.setParagraphAttributes(0, doc.getLength(), sas, false);
                try {
                    sd_display.insertString(sd_display.getLength(), msg + "\n", sas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        jtp_chat.setCaretPosition(jtp_chat.getDocument().getLength());
    }

    /**
     * 전송받은 메시지 출력
     * 
     * @param recvNick
     * @param recvMsg
     */
    public void recv_msg(String recvNo, String recvNick, String recvMsg) {
        if (!userNick.equals(recvNick) && chatNo.equals(recvNo)) {
            msg = recvNick + ": " + wrapText(recvMsg, recvNick.length());
            // jtp text 설정
            StyledDocument doc = jtp_chat.getStyledDocument();
            SimpleAttributeSet sas = new SimpleAttributeSet();
            // 폰트 컬러
            sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(80, 114, 167));
            // 왼쪽 정렬
            // StyleConstants.setAlignment(sas, StyleConstants.ALIGN_LEFT);
            doc.setParagraphAttributes(0, doc.getLength(), sas, false);
            try {
                sd_display.insertString(sd_display.getLength(), msg + "\n", sas);
            } catch (Exception e) {
                e.printStackTrace();
            }
            jtp_chat.setCaretPosition(jtp_chat.getDocument().getLength());
        }
    }

    // 메시지 전송 실패
    public void fail_msg() {
        JOptionPane.showMessageDialog(client, "메시지 전송에 실패했습니다.", "채팅방", JOptionPane.WARNING_MESSAGE,
                setImage.img_delete);
    }

    /**
     * Jtp 글자 줄바꿈 대체 메소드
     * 
     * @param msg
     * @return
     */
    public String wrapText(String msg, int nickCnt) {
        // 글자 끊어주기
        StringBuffer sb = new StringBuffer();
        // 영어, 숫자
        if (Pattern.matches("^[a-zA-Z0-9]*$", msg) && msg.length() + nickCnt >= 30) {
            sb.append(" " + msg);
            for (int i = 0; i < (msg.length() / 30); i++) {
                sb.insert(((29 - nickCnt) + (29 * i)), "\n");
            }
            msg = String.valueOf(sb);
        }
        // 한글포함
        else if (msg.length() >= 18) {
            sb.append(" " + msg);
            for (int i = 1; i < (msg.length() / 18) + 1; i++) {
                sb.insert((17 * i), "\n");
            }
            msg = String.valueOf(sb);
        }
        return msg;
    }

    /**
     * ActionListener 메소드
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        // 돌아가기 버튼
        if (obj == jbtn_back) {
            client.setContentPane(client.main.jp_main);
            client.setTitle(client.main.title);
            client.revalidate();
        }

        // 전송 버튼
        else if (obj == jbtn_send || obj == jtf_chat) {
            msg = jtf_chat.getText();
            // jtf에 아무것도 입력하지 않았을 경우
            if ("".equals(msg) || " 메시지를 입력하세요.".equals(msg)) {
                // 메시지 입력 경고창
                JOptionPane.showMessageDialog(client, "전송할 메시지를 입력해주세요.", "채팅방", JOptionPane.WARNING_MESSAGE,
                        setImage.img_info);
            }
            // 내용을 입력했을 경우
            else {
                // jtp text 설정
                StyledDocument doc = jtp_chat.getStyledDocument();
                SimpleAttributeSet sas = new SimpleAttributeSet();
                // 폰트 컬러
                sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(135, 90, 75));
                // 오른쪽 정렬
                // StyleConstants.setAlignment(sas, StyleConstants.ALIGN_RIGHT);
                doc.setParagraphAttributes(0, doc.getLength(), sas, false);
                try {
                    sd_display.insertString(sd_display.getLength(),
                            userNick + ": " + wrapText(msg, userNick.length()) + "\n", sas);
                    // 대화저장 707#채팅방넘버#아이디#닉네임#메시지
                    client.oos.writeObject(Protocol.SAVE_CHAT
                            + Protocol.seperator + chatNo
                            + Protocol.seperator + userId
                            + Protocol.seperator + userNick
                            + Protocol.seperator + msg);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                jtf_chat.setText("");
                jtp_chat.setCaretPosition(jtp_chat.getDocument().getLength());
            }
        }
    }

    /**
     * FocusListener 메소드
     */
    @Override
    public void focusGained(FocusEvent e) {
        Object obj = e.getSource();
        // 메시지jtf를 클릭했을 때
        if (obj == jtf_chat) {
            jtf_chat.setForeground(Color.BLACK);
            if (" 메시지를 입력하세요.".equals(jtf_chat.getText())) {
                jtf_chat.setText("");
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object obj = e.getSource();
        // 메시지jtf를 공백으로 두고 벗어났을 때
        if (obj == jtf_chat) {
            if ("".equals(jtf_chat.getText())) {
                jtf_chat.setForeground(Color.GRAY);
                jtf_chat.setText(" 메시지를 입력하세요.");
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
        ChatRoom cr = new ChatRoom(c, "test@email.com", "test", "1", "banana#test", "타이틀", "old");
        c.initDisplay();
        cr.initDisplay();
    }
}