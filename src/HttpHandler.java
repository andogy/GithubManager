import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class HttpHandler implements com.sun.net.httpserver.HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        handleResponse(exchange);
    }

    HashMap<String, String> getRequest(HttpExchange exchange){
        HashMap<String, String> response = new HashMap<>();

        String[] url = exchange.getRequestURI().toString().split("/.*\\?")[1].split("&");
        for (String s : url) {
            String[] kv = s.split("=");
            response.put(kv[0], kv[1]);
        }

        return response;
    }

    void handleResponse(HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();
        StringBuilder body = new StringBuilder();

        body.append("<html><head><title>")
                .append("FeedBack")
                .append("</title></head><body>")
                .append(getRequest(exchange))
                .append("</body></html>");

//        String response = escapeHTML(body.toString());
        String response = body.toString();

        exchange.sendResponseHeaders(200, response.length());
        os.write(response.getBytes());
        os.flush();
        os.close();
    }
}
