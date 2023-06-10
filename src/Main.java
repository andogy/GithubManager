import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Version Pre-Release-0.0.1-F1
 */

public class Main {
    public static WebBrowser webBrowser = new WebBrowser();
    public static JLabel loginStateLabel;
    public static JButton login;
    public static boolean isLoadingLoginState;
    public static boolean loginState;
    public static String userName;

    public static final String CLIENT_ID = "1f6fd4e559ecc4fd554b";

    public static final JFrame window = new JFrame("Github Manager");

    public static void main(String[] args) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(425, 240);
        window.setLayout(null);

        login = new JButton("...");
        login.setFocusPainted(false);
        login.setVisible(true);
        login.setSize(70, 23);
        login.addActionListener(e -> {
            if (!webBrowser.isOpening() && !loginState) {
                githubLoginHandler();
            }
            if (loginState && !isLoadingLoginState){
                githubLogoutHandler();
            }
        });
        window.add(login);

        loginStateLabel = new JLabel("State: Loading...");
        loginStateLabel.setBounds(login.getX()+75, 0, 220, 23);
        loginStateLabel.setVisible(true);
        refreshLoginStateHandler();
        loginStateLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refreshLoginStateHandler();
            }
        });
        window.add(loginStateLabel);

        mainLoop();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private static void githubLoginHandler(){
        webBrowser = new WebBrowser("Cancel login?", "Are you sure to cancel the login?");

        if (Utils.isPortAvailable(9746)) {
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

        String scopes = "repo,admin:repo_hook,gist,project,write:packages,delete_repo,admin:org,admin:public_key,admin:org_hook,notifications,workflow,codespace,admin:gpg_key,delete:packages,read:packages";

        webBrowser.WebDialog("https://github.com/login/oauth/authorize?scope=" + scopes + "&allow_signup=false&client_id="+CLIENT_ID);
    }

    private static void githubLogoutHandler(){
        System.out.println("Oh no! I don't know how to logout, cry face cry face :(");
    }

    public static void refreshLoginStateHandler(){
        if (!isLoadingLoginState) {
            isLoadingLoginState = true;
            loginStateLabel.setText("State: Loading...");
            new Thread(() -> {
                API.refreshLoginState();
                if (API.loginState) {
                    loginState = true;
                    loginStateLabel.setText("State: Login success");
                    login.setText("Logout");
                } else {
                    loginStateLabel.setText("State: Login needed / Login outdated");
                    login.setText("Login");
                    loginState = false;
                }
                isLoadingLoginState = false;
            }).start();
        }
    }

    private static void mainLoop() {
        new Thread(() -> {
            while (true){
                Utils.setButtonClickable(login, loginState || !isLoadingLoginState);
            }
        }).start();
    }
}