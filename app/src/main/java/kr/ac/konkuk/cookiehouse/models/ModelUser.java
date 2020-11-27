package kr.ac.konkuk.cookiehouse.models;

import com.google.gson.annotations.SerializedName;


public class ModelUser {

    @SerializedName("isAuth")
            boolean isAuth;

    @SerializedName("userId")
            int id;

    @SerializedName("email")
            String email;

    @SerializedName("userName")
            String userName;

    @SerializedName("password")
            String password;

    @SerializedName("userImg")
            String userImg;

    @SerializedName("lifeStyle")
            String lifeStyle;

    @SerializedName("journeyType")
            String journeyType;


    // 유일하게 존재 --> 현재 사용자의 account info
    public static ModelUser USER;


    public ModelUser() {

    }

    public ModelUser(boolean isAuth, int id, String email, String userName, String password, String userImg, String lifeStyle, String journeyType) {
        this.isAuth = isAuth;
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.userImg = userImg;
        this.lifeStyle = lifeStyle;
        this.journeyType = journeyType;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getLifeStyle() {
        return lifeStyle;
    }

    public void setLifeStyle(String lifeStyle) {
        this.lifeStyle = lifeStyle;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public static ModelUser getUSER() {
        return USER;
    }

    public static void setUSER(ModelUser USER) {
        ModelUser.USER = USER;
    }
}
