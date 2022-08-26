import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    private static String request(String url){
        Process process;
        try {
            process = Runtime.getRuntime().exec("curl -u '" + Main.userName + ":" + Main.userPAT + "' " + url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputStream in = process.getInputStream();
        return in.toString();
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
        if (!data.getString("massage").equals("Not Found")){
            return true;
        }
        return false;
    }
}
