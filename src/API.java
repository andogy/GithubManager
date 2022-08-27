import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class API {
    public static String OAuthKey = "";
    public static boolean loginState = false;

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
            process = Runtime.getRuntime().exec("curl -H 'Authorization: token " + OAuthKey + "' "  + url);

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
        if (!data.has("message") || data.isNull("message")) {
            data.put("message", "YES DADDY");
        }
        return !data.getString("message").equals("Not Found");
    }

    public static JSONObject searchUsers(String q){
        return new JSONObject(request("https://api.github.com/search/users?q="+q));
    }

    public static JSONObject searchRepositories(String q){
        return new JSONObject(request("https://api.github.com/search/repositories?q="+q));
    }

    public static void refreshLoginState(){
        JSONObject data = new JSONObject(request("https://api.github.com/user"));
        if (!data.has("message") || data.isNull("message")) {
            data.put("message", "YES DADDY");
        }
        loginState = !data.getString("message").equals("Requires authentication");
    }
}
