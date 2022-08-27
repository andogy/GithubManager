import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static WebBrowser webBrowser = new WebBrowser();
    public static JLabel loginState;

    public static final String CLIENT_ID = "1f6fd4e559ecc4fd554b";

    public static final JFrame window = new JFrame("Github Manager");

    public static void main(String[] args) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(425, 240);
        window.setLayout(null);

        JButton login = new JButton("Login");
        login.setFocusPainted(false);
        login.setVisible(true);
        login.setSize(70, 23);
        login.addActionListener(e -> {
            if (!webBrowser.isOpening()) {
                githubLoginHandler();
            }
        });
        window.add(login);

        loginState = new JLabel("State: Loading...");
        loginState.setBounds(login.getX()+75, 0, 220, 23);
        loginState.setVisible(true);
        new Thread(() -> {
            API.refreshLoginState();
            if (API.loginState) {
                loginState.setText("State: Login success");
            } else {
                loginState.setText("State: Login needed / OAuth outdated");
            }
        }).start();
        loginState.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loginState.setText("State: Loading...");
                new Thread(() -> {
                    API.refreshLoginState();
                    if (API.loginState) {
                        loginState.setText("State: Login success");
                    } else {
                        loginState.setText("State: Login needed / Login outdated");
                    }
                }).start();
            }
        });
        window.add(loginState);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private static void githubLoginHandler(){
        webBrowser = new WebBrowser("Cancel login?", "Are you sure to cancel the login?");

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

        webBrowser.WebDialog("https://github.com/login/oauth/authorize?scope=" + scopes + "&allow_signup=false&client_id="+CLIENT_ID);
    }
}