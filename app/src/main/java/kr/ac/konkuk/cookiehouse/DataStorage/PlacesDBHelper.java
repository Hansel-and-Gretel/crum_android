package kr.ac.konkuk.cookiehouse.DataStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlacesDBHelper extends SQLiteOpenHelper {

    public static final String PLACES_DATABASE_NAME = "places.db";
    public static final String TABLE_NAME = "places";
    public static final int DATABASE_VERSION = 1;

    public final String PLACE_ID = "placeID";              //PK
    public final String PLACE_NAME = "place_name";
    public final String PLACE_TIME = "time";
    public final String PLACE_LONGITUDE = "longitude";
    public final String PLACE_LATITUDE = "latitude";
    public final String PLACE_PHOTO = "photo";
    public final String PLACE_NOTE = "note";
    public final String PLACE_CATEGORY = "category";
    public final String PLACE_FLAG = "status";       // 해당 핀에 대해 기록을 했는지 여부


    public PlacesDBHelper(Context context) {super(context, PLACES_DATABASE_NAME, null, DATABASE_VERSION);}

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" (" +
//                PLACE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                PLACE_NAME + " TEXT, " +
//                PLACE_TIME + " INTEGER," +
//                PLACE_LONGITUDE + " REAL," +
//                PLACE_LATITUDE+ " REAL, " +
//                PLACE_PHOTO + " TEXT, " +
//                PLACE_NOTE + " TEXT, " +
//                PLACE_CATEGORY + " TEXT, " +
//                PLACE_FLAG + " INTEGER, " +
//                "FOREIGN KEY(" + PLACE_ID + ") REFERENCES journeys(path_id))");
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("데이터베이스 생성", "places.db");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" (" +
                PLACE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PLACE_NAME + " TEXT, " +
                PLACE_TIME + " INTEGER," +
                PLACE_LONGITUDE + " INTEGER," +
                PLACE_LATITUDE+ " INTEGER, " +
                PLACE_PHOTO + " TEXT, " +
                PLACE_NOTE + " TEXT, " +
                PLACE_CATEGORY + " TEXT, " +
                PLACE_FLAG + " INTEGER)");
               // "FOREIGN KEY(" + PLACE_ID + ") REFERENCES journeys(path_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ PLACES_DATABASE_NAME);
        // re-create table(places.db)
        onCreate(db);
    }
}
