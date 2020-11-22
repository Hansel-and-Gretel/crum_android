package kr.ac.konkuk.cookiehouse.models;

public class ModelPlaces {

    String place_name, photo, note, category;
    int time, longitude, latitude,status;

    public ModelPlaces() {
    }

    public ModelPlaces(String place_name, String photo, String note, String category, int time, int longitude, int latitude, int status) {
        this.place_name = place_name;
        this.photo = photo;
        this.note = note;
        this.category = category;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
