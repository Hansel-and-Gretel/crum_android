package kr.ac.konkuk.cookiehouse.Places;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;


import kr.ac.konkuk.cookiehouse.BuildConfig;

public class PlacesDBControl {
    private final String SERVER_ADDRESS = "http://34.64.192.218/journey.db";
    public final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;  // 서비스명 바뀔 수도 있어서..

    // DB 파일 받기 위한
    URL url;
    URLConnection conn;
    File target;

    // DB 기본, 쿼리 처리 위해서
    PlacesDBHelper helper;
    SQLiteDatabase placesDB;
    Cursor cursor;

    // DB 수신 스레드용 핸들러
    Handler handler = new Handler();


    public PlacesDBControl(PlacesDBHelper helper){
        this.helper = helper;
        final String DB_ADDRESS = Environment.getDataDirectory().getAbsolutePath() +"/data/"+ PACKAGE_NAME + "/databases/places.db";

//        Runnable(new Runnable() {
//
//            public void run() {
//                // TODO Auto-generated method stub
//
//                handler.post(new Runnable() {
//
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        try {
//                            url = new URL(SERVER_ADDRESS);
//                            conn = url.openConnection();
//                            is = conn.getInputStream();
//
//                            target = new File(DB_ADDRESS);
//                            fos = new FileOutputStream(target);
//                            bos = new BufferedOutputStream(fos);
//
//                            bufferLength = 0;
//                            buffer = new byte[1024];
//
//                            while((bufferLength = is.read(buffer)) > 0)
//                                bos.write(buffer);
//
//                            bos.close();
//                            fos.close();
//
//                            Toast.makeText(Blog_Android_SQLiteActivity.this, "DB 받아오기 성공!", Toast.LENGTH_SHORT).show();
//                        } catch(Exception e) {
//                            Log.e("URL Error", e.getMessage());
//                        } finally {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//            }
//        });
    }

    // id 쪽 수정필요
    public void insert(int id, String place_name, String time, String longitude, String latitude, String photo, String note, String category, int flag, int path_id){
        placesDB = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(helper.PLACE_ID, id);

        values.put(helper.PLACE_NAME, place_name);
        values.put(helper.PLACE_TIME, time);
        values.put(helper.PLACE_LONGITUDE, longitude);
        values.put(helper.PLACE_LATITUDE, latitude);
        values.put(helper.PLACE_PHOTO, photo);
        values.put(helper.PLACE_NOTE, note);
        values.put(helper.PLACE_CATEGORY, category);
        values.put(helper.PLACE_FLAG, flag);

        // 이ㅓㅀ게 하면 fk는 어떻게 할거지? 할 필요 없나? (나중에 서버로 업로드해주는 곳에서 fk 붙여서 나갈거야)
        placesDB.insert(helper.DATABASE_NAME, null, values);

        // 실행은 (PlacesActivity.java)의 백그라운드 스레드에서 !!!!!!!!!
    }

    // search()의 파라미터 값---?? View일지 아니면 placesDB의 직접적인 id값일지
    // 그래 , 그냥 searchType로 다 다르게 만들어두자
    public Cursor search(String searchBy, String value){
        String[] args = {};         // [0]:찾을 것   [1]:뭘로(열)   [2]:값

        // query varies by caller
        switch (searchBy){
            case "path_id": // home(4.1) AND path(5.0.*)         case0,1 겹치므로 통합
                args[0] = "place_name, time, flag";
                args[1] = "path_id";
                args[2] = value;
                break;

            case "places_id":  // 2:sns OR 3:places OR 4:my page (none, 직접접근X(10.0.1.2))
                args[0] = "*";
                args[1] = "places_id";
                args[2] = value;
                break;
        }
        cursor = placesDB.rawQuery("SELECT ? FROM places WHERE ?=?;", args);

        return cursor;
    }


    // 시간으로 삭제할까.. id를 어떻게
    public void delete(String time) {
        placesDB = helper.getWritableDatabase();
        placesDB.delete(helper.DATABASE_NAME, "place_name=?", new String[]{time});
    }

    // SQLite Close
    public void db_close() {
        placesDB.close();
        helper.close();
    }


}
