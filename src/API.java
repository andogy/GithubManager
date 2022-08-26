import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class API {
    private static String request(String url){
        Process process;
        StringBuilder processResult = new StringBuilder();
        try {
            process = Runtime.getRuntime().exec("curl -u '" + Main.userName + ":" + Main.userPAT + "' " + url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String line;
            while ((line = reader.readLine()) != null) {
                processResult.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return processResult.toString();
    }

    public static JSONObject getUserProfile(String name){
        JSONObject data = new JSONObject(request("https://api.github.com/users/" + name));
        if (isUserExist(name)){
            return data;
        }
        return null;
    }

    public static boolean isUserExist(String userName){
        JSONObject data = new JSONObject(request("https://api.github.com/users/" + userName));
        data.put("massage", "YES DADDY");
        return !data.getString("massage").equals("Not Found");
    }
}
