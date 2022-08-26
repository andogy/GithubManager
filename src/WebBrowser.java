import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WebBrowser extends JFrame{
    WebBrowser(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                JDialog exitingDialog = new JDialog();
                exitingDialog.setSize(240,170);
                exitingDialog.setTitle("Cancel login?");
                exitingDialog.setLayout(null);

                JLabel label = new JLabel("Are you sure to cancel the login?");
                label.setSize(240 , 20);
                label.setVisible(true);
                exitingDialog.add(label);

                JButton exitButton = new JButton("Yes");
                exitButton.setBounds((240-65), (170-55), 60, 20);
                exitButton.setContentAreaFilled(false);
                exitButton.setBorderPainted(false);
                exitButton.setOpaque(true);
                exitButton.setFocusPainted(false);
                exitButton.setHorizontalAlignment(SwingConstants.RIGHT);
                exitButton.setVerticalAlignment(SwingConstants.BOTTOM);
                exitButton.addActionListener(e -> {
                    exitingDialog.dispose();
                    destroyDialog();
                });
                exitingDialog.add(exitButton);
                exitButton.setVisible(true);

                exitingDialog.setLocationRelativeTo(null);
                exitingDialog.setVisible(true);
            }
        });
    }

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

    void destroyDialog(){
        setVisible(false);
        dispose();
    }

    void hideDialog(){
        setVisible(false);
        removeAll();
        repaint();
    }
}
