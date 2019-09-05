package co.djphy.glance.model;

import com.google.android.libraries.places.api.model.Place;

/**
 * Created by DJphy on 01-08-2017.
 */

public class UserInfo {

    public String uniqueId;
    public String name;
    public String emailId;
    public String profileImgUrl;
    public String birthday;
    public String gender;
    public String location;
    public String password;
    public Place place;
    private Rewards rewards;

    public UserInfo(String name, String emailId, String password, Place place) {
        this.name = name;
        this.emailId = emailId;
        this.password = password;
        this.place = place;
    }

    public UserInfo() {
    }

    public Rewards getRewards() {
        return rewards;
    }

    public static class Rewards{
        public int item1;
        public int item2;
        public int item3;
    }
}



