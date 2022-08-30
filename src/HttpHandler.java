import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {

    public static HashMap<String, String> response = new HashMap<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        handleResponse(exchange);
    }

    HashMap<String, String> getRequest(HttpExchange exchange){
        String[] url = exchange.getRequestURI().toString().split("/.*\\?")[1].split("&");
        for (String s : url) {
            String[] kv = s.split("=");
            response.put(kv[0], kv[1]);
        }

        return response;
    }

    void handleResponse(HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();

        String response = "<html><head><title>" +
                "FeedBack" +
                "</title></head><body>" +
                getRequest(exchange) +
                "</body></html>";

        exchange.sendResponseHeaders(200, response.length());
        os.write(response.getBytes());
        os.flush();
        os.close();

        if (exchange.getRequestURI().toString().split("\\?")[0].equals("/callback")) {
            Main.webBrowser.destroyDialog();
            API.OAuthKey = API.getOAuthKey(getRequest(exchange).get("code")).split("=")[1].split("&")[0];
            Main.loginStateLabel.setText("State: Loading...");
            new Thread(() -> {
                API.refreshLoginState();
                if (API.loginState) {
                    Main.loginStateLabel.setText("State: Login success");
                } else {
                    Main.loginStateLabel.setText("State: Login needed / Login outdated");
                }
            }).start();
        }
    }
}
