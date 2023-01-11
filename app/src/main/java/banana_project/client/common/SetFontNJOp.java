package banana_project.client.common;

import javax.swing.*;
import java.awt.*;

public class SetFontNJOp {
    // 폰트 설정
    public static final Font p12 = new Font("맑은 고딕", Font.PLAIN, 12); // 보통 12폰트
    public static final Font b12 = new Font("맑은 고딕", Font.BOLD, 12); // 볼드 12폰트
    public static final Font b14 = new Font("맑은 고딕", Font.BOLD, 14); // 볼드 14폰트

    // JOp 설정
    public static final Object opBack = new UIManager().put("OptionPane.background", new Color(255, 230, 120));
    public static final Object panBack = new UIManager().put("Panel.background", new Color(255, 230, 120));
    public static final Object opPanMsgFont = new UIManager().put("OptionPane.messageFont", b12);
    public static final Object btnBack = new UIManager().put("Button.background", new Color(130, 65, 60));
    public static final Object btnFore = new UIManager().put("Button.foreground", Color.white);
    public static final Object btnFont = new UIManager().put("Button.font", b12);
}