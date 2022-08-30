import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Utils {
    private static final HashMap<JButton, Color> orColor = new HashMap<>();
    public static void setButtonClickable(JButton button, boolean clickable) {
        button.setFocusable(clickable);
        button.setEnabled(clickable);
        if (!clickable) {
            orColor.put(button, button.getBackground());
            button.setBackground(Color.GRAY);
        } else {
            button.setBackground(orColor.get(button));
            orColor.remove(button);
        }
    }

    public static boolean isPortAvailable(int port) {
        Socket s = null;
        try {
            s = new Socket("localhost", port);
            return false;
        } catch (IOException e) {
            return true;
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
