package kr.ac.konkuk.cookiehouse.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class Places implements Parcelable {     // 시스템 위한 구조체
    public final int id;              //PK
    public final String name;
    public final long time;      //TODO: data type - Date? or int?
    public final float longitude;
    public final float latitude;
    public final String photo;
    public final String note;
    public final String category;
    public final boolean status;



    // Places 구조체 객체 만들 때 사용
    public Places(int id, String name, long time, float longitude, float latitude, String photo, String note, String category, boolean status){
        this.id = id;
        this.name = name;
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.photo = photo;
        this.note = note;
        this.category = category;
        this.status = status;
    }

    // intent로 넘어온 Places 객체 처리할 때 사용
    public Places(Parcel src){
        id = src.readInt();
        name = src.readString();
        time = src.readInt();
        longitude = src.readFloat();
        latitude = src.readFloat();
        photo = src.readString();
        note = src.readString();
        category = src.readString();
        if(Build.VERSION.SDK_INT >= 29)
            status = src.readBoolean();
        else {
            status = src.readByte() == 1;  // readBoolean 버전 문제: boolean으로 다시 변환
        }
    }




    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNote() {
        return note;
    }

    public String getCategory() {
        return category;
    }

    public boolean isStatus() {
        return status;
    }

    // Parcelable 구조체에 기록하는 함수 (주의: 순서 같아야 함)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.time);
        dest.writeFloat(this.longitude);
        dest.writeFloat(this.latitude);
        dest.writeString(this.photo);
        dest.writeString(this.note);
        dest.writeString(this.category);
        if(Build.VERSION.SDK_INT >= 29)
            dest.writeBoolean(this.status);
        else {
            dest.writeByte((byte)(this.status ? 1 : 0));  // writeBoolean 버전 문제: byte로 변환
        }
    }

    public static final Creator<Places> CREATOR = new Creator<Places>() {
        @Override
        public Places createFromParcel(Parcel in) {
            return new Places(in);
        }

        @Override
        public Places[] newArray(int size) {
            return new Places[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
