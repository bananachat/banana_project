package banana_project.client.common;

import javax.swing.*;
import java.awt.*;

public class SetFontNJOp {
    // 폰트 설정
    public static final Font p11 = new Font("맑은 고딕", Font.PLAIN, 11); // 보통 11폰트
    public static final Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통 12폰트
    public static final Font p14 = new Font("맑은 고딕", Font.PLAIN, 14); // 보통 14폰트
    public static final Font p16 = new Font("맑은 고딕", Font.PLAIN, 16); // 보통 16폰트
    public static final Font p18 = new Font("맑은 고딕", Font.PLAIN, 18); // 보통 18폰트

    public static final Font b12 = new Font("맑은 고딕", Font.BOLD, 12); // 볼드 12폰트
    public static final Font b14 = new Font("맑은 고딕", Font.BOLD, 14); // 볼드 14폰트
    public static final Font b16 = new Font("맑은 고딕", Font.BOLD, 16); // 볼드16폰트
    public static final Font b18 = new Font("맑은 고딕", Font.BOLD, 18); // 볼드18폰트

    // JOp 설정
    public static final Object opBack = new UIManager().put("OptionPane.background", new Color(255, 230, 120));
    public static final Object panBack = new UIManager().put("Panel.background", new Color(255, 230, 120));
    public static final Object opPanMsgFont = new UIManager().put("OptionPane.messageFont", b12);
    public static final Object btnBack = new UIManager().put("Button.background", new Color(130, 65, 60));
    public static final Object btnFore = new UIManager().put("Button.foreground", Color.white);
    public static final Object btnFont = new UIManager().put("Button.font", b12);
}