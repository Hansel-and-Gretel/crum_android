package kr.ac.konkuk.cookiehouse.models;

public class ModelUser {

    String email, name, password, image, token, lifeStyle, journeyStyle;

    public ModelUser() {
    }

    public ModelUser(String email, String name, String password, String image, String token, String lifeStyle, String journeyStyle) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
        this.token = token;
        this.lifeStyle = lifeStyle;
        this.journeyStyle = journeyStyle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLifeStyle() {
        return lifeStyle;
    }

    public void setLifeStyle(String lifeStyle) {
        this.lifeStyle = lifeStyle;
    }

    public String getJourneyStyle() {
        return journeyStyle;
    }

    public void setJourneyStyle(String journeyStyle) {
        this.journeyStyle = journeyStyle;
    }
}
