package banana_project.client.room;

import java.util.Calendar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import banana_project.client.main.Main;
public class Pchat extends JFrame implements ActionListener {
    // 선언부
    private Socket socket = new Socket();
    private PrintWriter out;
    private BufferedReader in;
    // 배경이미지
    String imgPath = "이미지주소";
    ImageIcon iicon = new ImageIcon(imgPath + "bg.png");
    Container con = this.getContentPane();
    // 채팅창
    JTextArea chatArea = new JTextArea();
    JTextField jtf = new JTextField(24);
    JButton jbtn_send = new JButton("보내기");
    JButton jbtn_back = new JButton("이전");
    JPanel jp_center = new JPanel();
    JPanel jp_south = new JPanel();
    JPanel jp_north = new JPanel();
    JPanel timepanel=new JPanel();
    JScrollPane jsp = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    String message ;
    JLabel timeLabel = new JLabel();

    public Pchat() {// 디폴트생성자
    }

    public void JTexPane() {// 배경이미지 생성자
        // setUndecorated(true);
        // AWTUtilities.setWindowOpaque(this, false);
        // this.setContentPane(mp);
        final JTextPane textPane = new JTextPane();
        textPane.setOpaque(false);

        JViewport viewport = new JViewport() {
            @Override
            protected void paintComponent(Graphics g) {
                Image img = iicon.getImage(); //
                // Image grayImage = GrayFilter.createDisabledImage(img);
                setOpaque(false);
                Graphics2D gd = (Graphics2D) g;
                gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g.drawImage(img, 0, 0, this);
                super.paintComponent(g);
            }
        };
        // viewport.setOpaque(false);
        jsp.setOpaque(true);
        jsp.setViewport(viewport);
        jsp.setViewportView(textPane);
        // mp.add("Center",jsp);
        con.add(jsp);
        // frame.pack();
        this.setSize(400, 600);
        // con.setLocationRelativeTo(null);
        this.setVisible(true);

    }// 이미지 생성자 끝

    // 화면그리기
    public void initDisplay() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jbtn_send.addActionListener(this);
        jbtn_back.addActionListener(this);
        this.setLocationRelativeTo(null);
        this.setTitle("바나나톡");
        this.setSize(400, 600);
        this.setVisible(true);
        this.add("Center", chatArea);
        
        this.add("South", jp_south);
        jp_south.add(jtf, BorderLayout.SOUTH);
        jp_south.add(jbtn_send, BorderLayout.EAST);
        chatArea.setEditable(false);
        // 상단 조정
        this.add("North",jp_north);
        //jp_north.setLayout(null); 이거 하면 자꾸 다 사라져요...
        timepanel.setLayout(new FlowLayout());
        timepanel.add(timeLabel);
        jp_north.add(timepanel);
        new Timer(1000, e -> {
            timeLabel.setText(Calendar.getInstance().getTime().toString());
        }).start();
        jp_north.add(jbtn_back);
        jbtn_back.add(jbtn_back, BorderLayout.EAST);
        
        jtf.addKeyListener(new KeyAdapter() {// 엔터키 입력
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    message = jtf.getText();
                    chatArea.append(message + "\n");
                    jtf.setText("");
                    sendMessageToServer(message);
                    System.out.println("엔터키입력");
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
                    //out.println(jtf.getText());
                    jtf.setText("");
                    sendMessageToServer(message);
                    System.out.println("마우스클릭");
                }
            }
        });
    }// 화면 그리기 끝

    private void sendMessageToServer(String message) {
        // 서버로 메시지 보내기
        try {
            socket = new Socket("localhost", 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new IncomingReader()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        // 메시지받기
        public class IncomingReader implements Runnable {
            public void run() {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        chatArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
}
    public static void main(String[] args) {
        Pchat room = new Pchat();
        room.initDisplay();
        room.JTexPane();
    }//main 끝

        @Override
        public void actionPerformed(ActionEvent e1) {
            // TODO Auto-generated method stub
            Object obj = e1.getSource();
            if (obj == jbtn_back) {
    
                dispose();
            
            }
        }
    }