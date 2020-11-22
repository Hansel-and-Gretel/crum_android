package kr.ac.konkuk.cookiehouse.models;

public class ModelJourney {

    String journeyName, type, accompany, summary, image;
    int pinFrequency;
    boolean status, sharedFlag;

    public ModelJourney() {
    }

    public ModelJourney(String journeyName, String type, String accompany, String summary, String image, int pinFrequency, boolean status, boolean sharedFlag) {
        this.journeyName = journeyName;
        this.type = type;
        this.accompany = accompany;
        this.summary = summary;
        this.image = image;
        this.pinFrequency = pinFrequency;
        this.status = status;
        this.sharedFlag = sharedFlag;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccompany() {
        return accompany;
    }

    public void setAccompany(String accompany) {
        this.accompany = accompany;
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

    public int getPinFrequency() {
        return pinFrequency;
    }

    public void setPinFrequency(int pinFrequency) {
        this.pinFrequency = pinFrequency;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isSharedFlag() {
        return sharedFlag;
    }

    public void setSharedFlag(boolean sharedFlag) {
        this.sharedFlag = sharedFlag;
    }
}
