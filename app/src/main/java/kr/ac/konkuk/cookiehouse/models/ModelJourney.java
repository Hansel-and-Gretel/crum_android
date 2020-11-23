package kr.ac.konkuk.cookiehouse.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import static kr.ac.konkuk.cookiehouse.models.ModelUser.USER;

public class ModelJourney {
    public static ModelJourney currentJourney = null;

//    @SerializedName("")
//    public int id;                //PK
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

    public ModelJourney() {
    }



    public ModelJourney(String name, String type, String party, int frequency, String summary, String image, boolean status, boolean shared) {
        // 서버 자동생성: this.id = id;
        //아직this.path = path;
        this.name = name;
        this.type = type;
        this.party = party;
        this.frequency = frequency;
        this.summary = summary;
        this.image = image;
        this.status = status;
        this.shared = shared;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getPath() {
//        return path;
//    }
//
//    public void setPath(int path) {
//        this.path = path;
//    }

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

    JSONObject requestJourney = new JSONObject();
    public JSONObject transferNewJourney(String name, String type, String party, int frequency){
        try {
            requestJourney.put("journeyName", name);
            requestJourney.put("type", type);
            requestJourney.put("accompany", party);
            requestJourney.put("pinFrequency", frequency);
            requestJourney.put("status", false);
            requestJourney.put("sharedFlag", false);
            //requestJourney.put("userName", USER.getUserName());
            // TODO
            requestJourney.put("userName", "test99");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestJourney;
    }


}
