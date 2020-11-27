package kr.ac.konkuk.cookiehouse.models;

import com.google.gson.annotations.SerializedName;


public class ModelUser {
    @SerializedName("userId")
            int id;

    @SerializedName("email")
            String email;

    @SerializedName("userName")
            String userName;

    @SerializedName("password")
            String password;

    @SerializedName("image")
            String image;

    @SerializedName("lifeStyle")
            String lifeStyle;

    @SerializedName("journeyType")
            String journeyType;

    // 유일하게 존재 --> 현재 사용자의 account info
    public static ModelUser USER;

    public ModelUser() {
    }

    public ModelUser(int id, String email, String userName, String password, String image, String token, String lifeStyle, String journeyType) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.image = image;
        this.lifeStyle = lifeStyle;
        this.journeyType= journeyType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLifeStyle() { return lifeStyle; }

    public void setLifeStyle(String lifeStyle) {
        this.lifeStyle = lifeStyle;
    }

    public String getjourneyType() {
        return journeyType;
    }

    public void setjourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
