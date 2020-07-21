package Controllers;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotType {
    public static void main(String[] args) throws InterruptedException, AWTException {
        Thread.sleep(2000); // Give me time to open up notepad
        Robot r = new Robot();
        for (char c : "I like playing with fire, and Java.".toCharArray()) {
            int code = KeyEvent.getExtendedKeyCodeForChar(c);
            if (Character.isUpperCase(c))
                r.keyPress(KeyEvent.VK_SHIFT);
            r.keyPress(code);
            r.keyRelease(code);
            if (Character.isUpperCase(c))
                r.keyRelease(KeyEvent.VK_SHIFT);
        }
    }
}
