package kr.ac.konkuk.cookiehouse.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import static kr.ac.konkuk.cookiehouse.models.ModelUser.USER;

public class ModelJourney {
    public static ModelJourney currentJourney = null;

    @SerializedName("id")
    public int id;                //PK
//
//    @SerializedName("")
//    public int path;      // current path, path ID

    @SerializedName("journeyName")
    public String name;           // journeyNmae (서버)

    @SerializedName("type")
    public String type;

    @SerializedName("accompany")
    public String party;          // accompany (서버)

    @SerializedName("pinFrequency")
    public int frequency;         // pinFrequency(서버)

    @SerializedName("summary")
    public String summary;           // journeyNmae (서버)

    @SerializedName("image")
    public String image;

    @SerializedName("status")
    public boolean status;            // [GUIDE] true: active / false: paused, stopped etc.

    @SerializedName("sharedFlag")
    public boolean shared;

    @SerializedName("userId")
    public int userId;

    @SerializedName("userName")
    public String userName;

    @SerializedName("uploadSuccess")
    public static ModelJourney responseMsg;



    public ModelJourney() {
    }


    public ModelJourney(int id, String name, String type, String party, int frequency, String summary, String image, boolean status, boolean shared, int userId, String userName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.party = party;
        this.frequency = frequency;
        this.summary = summary;
        this.image = image;
        this.status = status;
        this.shared = shared;
        this.userId = userId;
        this.userName = userName;
    }

    public ModelJourney(String name, String type, String party, int frequency) {    // for creating new journey
        this.name = name;
        this.type = type;
        this.party = party;
        this.frequency = frequency;
    }

    public static ModelJourney getCurrentJourney() {
        return currentJourney;
    }

    public static void setCurrentJourney(ModelJourney currentJourney) {
        ModelJourney.currentJourney = currentJourney;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // 서버에게 보낼 메세지 ㅠㅐ요


    public JSONObject transferNewJourney(String name, String type, String party, int frequency){
        JSONObject requestJourney = new JSONObject();

        try {
            requestJourney.put("journeyName", name);
            requestJourney.put("type", type);
            requestJourney.put("accompany", party);
            requestJourney.put("pinFrequency", frequency);
            // TODO 지우삼
            //requestJourney.put("summary", "bal");
            //requestJourney.put("image", "something");
            requestJourney.put("status", false);
            requestJourney.put("sharedFlag", false);
            // 로그인에서 받아온 정보
            requestJourney.put("userId", USER.id);
            requestJourney.put("userName", USER.userName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestJourney;
    }



}
