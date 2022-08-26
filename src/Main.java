import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static String userName = "andogy";
    public static String userPAT = "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";
    public static final WebBrowser loginDialog = new WebBrowser();

    public static final String CLIENT_ID = "1f6fd4e559ecc4fd554b";

    public static void main(String[] args) {

        JFrame window = new JFrame("Github Manager");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(425, 240);
//        window.setLayout(null);

        JButton login = new JButton("login");
        login.setFocusPainted(false);
        login.addActionListener(e -> githubLoginHandler());
        window.add(login);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private static void githubLoginHandler(){
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

        String scopes = "repo,admin:repo_hook,gist,project,write:packages,delete_repo,admin:org,admin:public_key,admin:org_hook,notifications,workflow,codespace,admin:gpg_key,delete:packages,read:packages";

        loginDialog.WebDialog("https://github.com/login/oauth/authorize?scope=" + scopes + "&allow_signup=false&client_id="+CLIENT_ID);
    }
}