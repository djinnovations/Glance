package dj.example.main.utils;

/**
 * Created by User on 31-07-2017.
 */

public class UserSession {

    private static UserSession ourInstance;
    private String fcmToken;

    public static UserSession getInstance(){
        if (ourInstance == null){
            ourInstance = new UserSession();
        }
        return ourInstance;
    }

    private UserSession(){

    }

    public void clearInstance(){
        ourInstance = null;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
