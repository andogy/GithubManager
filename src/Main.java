import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static boolean loginStat = false;
    public static String userName;
    public static String userPAT;

    public static final String CLIENT_ID = "1f6fd4e559ecc4fd554b";

    public static void main(String[] args) {

        JFrame window = new JFrame("Github Manager");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(850, 480);
//        window.setLayout(null);

//        JTextField name = new JTextField();
//        name.setAlignmentX(0);
//        name.setAlignmentY(0);
//        name.setSize(60, 0);
//        window.add(name);
//
//        JTextField PAT = new JTextField();
//        window.add(PAT);
//
//        JButton submit = new JButton("Submit");
//        submit.addActionListener(e -> {
//            userPAT = PAT.getText();
//            userName = name.getText();
//            System.out.println(API.getUserProfile());
//        });
//        window.add(submit);

        JButton login = new JButton("login");
        login.addActionListener(e -> githubLoginHandler());
        window.add(login);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private static void githubLoginHandler(){
        new WebBrowser().WebDialog("https://github.com/login/oauth/authorize?client_id="+CLIENT_ID);

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 9746), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.createContext("/", new HttpHandler());
        server.setExecutor(threadPoolExecutor);
        server.start();
    }
}