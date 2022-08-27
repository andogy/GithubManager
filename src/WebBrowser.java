import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WebBrowser extends JFrame{
    WebBrowser(String title, String text, Component relativeTo){
        addQuitDialog(title, text, relativeTo);
    }
    WebBrowser(String title, String text){
        addQuitDialog(title, text, null);
    }

    WebBrowser(){
        addQuitDialog("Quit?", "Are you sure to quit?", null);
    }

    void addQuitDialog(String title, String text, Component relativeTo){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                JDialog exitingDialog = new JDialog();
                exitingDialog.setSize(240,170);
                exitingDialog.setTitle(title);
                exitingDialog.setLayout(null);

                JLabel label = new JLabel(text);
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

                exitingDialog.setLocationRelativeTo(relativeTo);
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
        removeAll();
        setVisible(false);
        repaint();
    }

    boolean isOpening(){
        return isVisible();
    }
}
