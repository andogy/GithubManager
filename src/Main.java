import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Main {
    public static boolean loginStat = false;
    public static String userName;
    public static String userPAT;

    public static void main(String[] args) {
        JFrame window = new JFrame("Github Manager");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(850, 480);
        window.setLayout(new GridLayout());

//        JLabel isLogin = new JLabel("Github Status: " + loginStat);
//        window.add(isLogin);

//        JLabel profile = new JLabel(Objects.requireNonNull(API.getUserProfile("andogy")).getString("login"));
//        profile.setSize(200, 0);
//        profile.setVerticalAlignment(JLabel.TOP);
//        window.add(profile);



        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}