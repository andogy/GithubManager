import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static boolean loginStat = false;
    public static String userName;
    public static String userPAT;

    public static void main(String[] args) {

        JFrame window = new JFrame("Github Manager");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(850, 480);
        window.setLayout(new GridLayout());

        JTextField name = new JTextField();
        name.addActionListener(e -> userName = name.getText());
        window.add(name);

        JTextField PAT = new JTextField();
        PAT.addActionListener(e -> userPAT = PAT.getText());
        window.add(PAT);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> System.out.println(API.getUserProfile("andogy")));
        window.add(submit);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}