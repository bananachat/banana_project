package banana_project.client.mypage;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class JtextPaneTest extends JFrame {

    public static void main(String[] args) {
        new JtextPaneTest();
    }

    Container contentPane;
    JTextPane textPane;

    public JtextPaneTest() {
        super("TextPaneTest Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 50, 500, 500);
        contentPane = getContentPane();

        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        //textPane.setOpaque(false); // 불투명-false (투명)
        textPane.setBackground(Color.WHITE);

        contentPane.add(textPane, BorderLayout.CENTER);

        textPane.setText("<a href=\"\">Google</a>");

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet styleSet = new SimpleAttributeSet();
        StyleConstants.setForeground(styleSet, Color.RED); 	// 전경색 지정
        StyleConstants.setBackground(styleSet, Color.YELLOW);	//배경색 지정
        StyleConstants.setBold(styleSet, true);		//폰트 스타일
        StyleConstants.setUnderline(styleSet, true); // 텍스트 밑줄

        try {
            doc.insertString(doc.getLength(), "\n새로운 문자열 소연소연", styleSet);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
        textPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    System.out.println("링크 클릭함");
                    //새로운 HTML요소나 링크를 추가하는 예
                    HTMLEditorKit kit = new HTMLEditorKit();
                    HTMLDocument doc = new HTMLDocument();
                    textPane.setEditorKit(kit);
                    textPane.setDocument(doc);
                    try {
                        kit.insertHTML(doc, doc.getLength(), "<a href=\"download\">새로운 링크</a>", 0, 0, null);
                        kit.insertHTML(doc, doc.getLength(), "<b>hello", 0, 0, HTML.Tag.B);
                        kit.insertHTML(doc, doc.getLength(), "<font color='red'><u>world</u></font>", 0, 0, null);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

        setVisible(true);
    }
}