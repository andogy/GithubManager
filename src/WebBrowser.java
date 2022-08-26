import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WebBrowser extends JFrame{
    void WebDialog(String url) {
        JFXPanel panel = new JFXPanel();
        panel.setSize(getWidth(),getHeight());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.setSize(getWidth(),getHeight());
            }
        });

        Platform.runLater(() -> {
            WebView webView = new WebView();
            panel.setScene(new Scene(webView));
            webView.getEngine().load(url);
        });

        add(panel);

        setSize(850, 480);
        setTitle("Github Manager - Login with Github");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
