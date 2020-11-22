package kr.ac.konkuk.cookiehouse.General;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    private String name;
    private String email;
    private String lifeStyle;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLifeStyle() {
        return lifeStyle;
    }

}
