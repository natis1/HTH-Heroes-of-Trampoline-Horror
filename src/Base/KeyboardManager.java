package Base;

import java.awt.event.KeyEvent;

public class KeyboardManager
{
    public boolean[] elvenAsciiInput;

    public KeyboardManager() {
        elvenAsciiInput = new boolean[52];
    }

    void keyReleased(int keyYouLetGoOf) {
        elvenAsciiInput[keyYouLetGoOf] = false;
    }
}
