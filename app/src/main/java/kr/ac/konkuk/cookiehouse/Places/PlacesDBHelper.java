package kr.ac.konkuk.cookiehouse.Places;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlacesDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;

    public static final String DATABASE_NAME = "places.db";
    public final String PLACE_ID = "place_id";              //PK
    public final String PLACE_NAME = "place_name";
    public final String PLACE_TIME = "time";
    public final String PLACE_LONGITUDE = "longitude";
    public final String PLACE_LATITUDE = "latitude";
    public final String PLACE_PHOTO = "photo";
    public final String PLACE_NOTE = "note";
    public final String PLACE_CATEGORY = "category";
    public final String PLACE_FLAG = "flag";       // 해당 핀에 대해 기록을 했는지 여부


    public PlacesDBHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ DATABASE_NAME +" (" +
                PLACE_ID +" PRIMARY KEY, " +
                PLACE_NAME + " TEXT, " +
                PLACE_TIME + " TEXT, " +
                PLACE_LONGITUDE + " TEXT," +
                PLACE_LATITUDE+ " TEXT, " +
                PLACE_PHOTO + " TEXT, " +
                PLACE_NOTE + " TEXT, " +
                PLACE_CATEGORY + " TEXT, " +
                PLACE_FLAG + " INTEGER, " +
                "FOREIGN KEY (path_id) REFERENCES journeys (path_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS places");
        // re-create table(places.db)
        onCreate(db);
    }
}
