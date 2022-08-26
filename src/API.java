import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class API {
    public static String getOAuthKey(String code){
        StringBuilder response = new StringBuilder();
        try {
            String url = "https://github.com/login/oauth/access_token";
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            con.setRequestMethod("POST");

            String urlParameters = "code="+code+"&client_id="+Main.CLIENT_ID+"&client_secret=3970928b9355934e7192bc314fd6fbaaedceb967";

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return response.toString();
    }
    private static String request(String url){
        Process process;
        StringBuilder processResult = new StringBuilder();
        try {
            process = Runtime.getRuntime().exec("curl  -u '" + Main.userName + ":" + Main.userPAT + "' " + url);

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

    public static JSONObject getOtherUsersProfile(String name){
        JSONObject data = new JSONObject(request("https://api.github.com/users/" + name));
        if (isUserExist(name)){
            return data;
        }
        return null;
    }

    public static JSONObject getUserProfile(){
        return new JSONObject(request("https://api.github.com/user"));
    }

    public static boolean isUserExist(String userName){
        JSONObject data = new JSONObject(request("https://api.github.com/users/" + userName));
        data.put("massage", "YES DADDY");
        return !data.getString("massage").equals("Not Found");
    }

    public static JSONObject searchUsers(String q){
        return new JSONObject(request("https://api.github.com/search/users?q="+q));
    }

    public static JSONObject searchRepositories(String q){
        return new JSONObject(request("https://api.github.com/search/repositories?q="+q));
    }
}
