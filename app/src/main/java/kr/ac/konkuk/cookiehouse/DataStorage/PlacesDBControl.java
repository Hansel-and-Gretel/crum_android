package kr.ac.konkuk.cookiehouse.DataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


import kr.ac.konkuk.cookiehouse.BuildConfig;
import kr.ac.konkuk.cookiehouse.models.ModelPlaces;
import kr.ac.konkuk.cookiehouse.models.Places;

public class PlacesDBControl{
    public final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;  // 서비스명 바뀔 수도 있어서..

    // DB 파일 받기 위한
    URL url;
    URLConnection conn;
    File target;

    // DB 기본, 쿼리 처리 위해서
    Context context;
    PlacesDBHelper helper;
    SQLiteDatabase placesDB;
    Cursor cursor;

    // DB 수신 스레드용 핸들러
    Handler handler = new Handler();


    public PlacesDBControl(Context context) {
        this.context = context;
        helper = new PlacesDBHelper(context);
        placesDB = context.openOrCreateDatabase(helper.PLACES_DATABASE_NAME, Context.MODE_PRIVATE, null);
        placesDB = helper.getWritableDatabase();
        // TODO: db 저장경로 다른 곳으로 하고싶으면 사용하삼
//         final String DB_ADDRESS = Environment.getDataDirectory().getAbsolutePath() +"/data/"+ PACKAGE_NAME + "/databases/places.db";
    }

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


    public boolean insert(Places place){
        // record one set of place info
        ContentValues values = new ContentValues();

        values.put(helper.PLACE_NAME, place.name);
        values.put(helper.PLACE_TIME, place.time);
        values.put(helper.PLACE_LONGITUDE, place.longitude);
        values.put(helper.PLACE_LATITUDE, place.latitude);
        values.put(helper.PLACE_PHOTO, place.photo);
        values.put(helper.PLACE_NOTE, place.note);
        values.put(helper.PLACE_CATEGORY, place.category);
        values.put(helper.PLACE_FLAG, place.status);

        // TODO 이ㅓㅀ게 하면 fk는 어떻게 할거지? 할 필요 없나? (나중에 서버로 업로드해주는 곳에서 fk 붙여서 나갈거야)
        if(placesDB.insert(helper.TABLE_NAME, null, values) > 0){
            Log.i("places.db - Inserted", String.valueOf(place.time));
            return true;
        } else {
            return  false;
        }
    }



    // search()의 파라미터 값---?? View일지 아니면 placesDB의 직접적인 id값일지
    // 그래, 그냥 searchType로 다 다르게 만들어두자
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



//    public boolean edit(Places place, int placeID){
//
//    }

    // 시간으로 삭제할까.. id를 어떻게
    public void delete(String time) {
        //placesDB = helper.getWritableDatabase();
        placesDB.delete(helper.TABLE_NAME, "place_name=?", new String[]{time});
    }


    public boolean flush() {
        // Empty entire places.db --> for memory efficiency
        // when? After uploading to server: corresponding path and its place entities
        placesDB.execSQL("DELETE FROM " + helper.TABLE_NAME);
        return true;
    }


    // SQLite Close
    public void db_close() {
        placesDB.close();
        helper.close();
    }


    /*
    * 현재 데이터 베이스에 있는 데이터 전체 읽어오기
    * list로 보여줄때, adapterlocation이랑 사용
    * */
    public Cursor readAllData(){

        String query = "SELECT * FROM "+helper.TABLE_NAME;
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = null;
        if(db!=null){
           cursor =  db.rawQuery(query, null);
        }
        return cursor;

    }

    /*
     * 현재 데이터 베이스에 있는 데이터 전체 읽어오기
     * map로 보여줄때,
     * [ArrayList형식으로 리턴]
     * */


    public List<ModelPlaces> getAllPlaces(){

        SQLiteDatabase database = helper.getReadableDatabase();
        List<ModelPlaces> placesList = new ArrayList<>();
        String getAll = "SELECT * FROM "+helper.TABLE_NAME;
        Cursor cursor = database.rawQuery(getAll,null);
        if(cursor.moveToFirst()){
            do{
                ModelPlaces place = new ModelPlaces();
                place.setId(cursor.getInt(0));
                place.setLatitude(cursor.getFloat(4));
                place.setLongitude(cursor.getFloat(3));
                place.setName(cursor.getString(1));

                placesList.add(place);
            }while(cursor.moveToNext());
        }

        return placesList;

    }


    //장소 객체 넘겨 받아 id가져와서 삭제함.
    public void deletePlace(Places place){
        SQLiteDatabase database = helper.getReadableDatabase();
        database.delete(helper.TABLE_NAME, "place_id=?",
                new String[]{String.valueOf(place.getId())});
        database.close();
    }



    /*
    *
    * 이건 현재 sqlite파일을 json으로 바꾼 내용입니다.
    *
    * json을 gson으로 전환해서 (Gson gson = new Gson();)
    * 서버에 보내면 되요요
    *
    * 아 근데 그냥 json으로 보내면 되나?
    *
    * https://github.com/google/gson
    * https://re-build.tistory.com/41
    *
   * */

    public JSONArray getResults()
    {

//        String myPath = DB_PATH + helper.PLACES_DATABASE_NAME;// Set path to your database

        String myTable = helper.TABLE_NAME;//Set name of your table


        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(String.valueOf(context.getDatabasePath("places.db")), null, SQLiteDatabase.OPEN_READONLY);

        String searchQuery = "SELECT  * FROM " + myTable;
        Cursor cursor = myDataBase.rawQuery(searchQuery, null );

        JSONArray resultSet = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("TAG_NAME", resultSet.toString() );
        return resultSet;
    }







}
