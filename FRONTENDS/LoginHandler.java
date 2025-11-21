import java.util.HashMap;
import java.util.Map;

public class LoginHandler{
    private static final Map<String, String> userData = new HashMap<>();

    static{
        userData.put("admin", "adminpass");
    }

    public static boolean checkCreds(String user, String pass){
        if (user == null || pass == null){
            return false;
        }

        if (userData.containsKey(user)){
            String storedPass = userData.get(user);
            return storedPass.equals(pass);
        }

        return false;
    }
}
