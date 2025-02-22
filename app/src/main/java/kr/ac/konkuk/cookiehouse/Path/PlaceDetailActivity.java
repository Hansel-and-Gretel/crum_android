package kr.ac.konkuk.cookiehouse.Path;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Locale;

import kr.ac.konkuk.cookiehouse.DataStorage.PlacesDBHelper;
import kr.ac.konkuk.cookiehouse.General.RetrofitConnection;
import kr.ac.konkuk.cookiehouse.General.RetrofitInterface;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailActivity extends AppCompatActivity {

    private static final String TAG = "포스트상세" ;
    int placeId;
    String name, timestamp, lat, lon, photo, note, category, status;
    long time;
    boolean status2, what;
    float lon2, lat2;
    TextView latTv, lonTv, timeTv, categoryTv, descriptionTv, nameTv;
    ImageView placeIv;
    PlacesDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        setToolbar();

        Intent intent = getIntent();
        what = intent.getExtras().getBoolean("what");


        placeId = intent.getExtras().getInt("placeId");
        name = intent.getExtras().getString("name");

        timestamp = intent.getExtras().getString("timestamp");
        time = intent.getExtras().getLong("time");//

        lat = intent.getExtras().getString("lat");
        lat2 = intent.getExtras().getFloat("lat");//
        lon = intent.getExtras().getString("lon");
        lon2 = intent.getExtras().getFloat("lon");//

        photo = intent.getExtras().getString("photo");
        note = intent.getExtras().getString("note");
        category = intent.getExtras().getString("category");

        status = intent.getExtras().getString("status");
        status2 = intent.getExtras().getBoolean("status");//


        //init views
        nameTv = findViewById(R.id.title_tv);
        latTv = findViewById(R.id.lat);
        lonTv = findViewById(R.id.lon);
        timeTv = findViewById(R.id.timestamp);
        categoryTv = findViewById(R.id.place_type);
        descriptionTv = findViewById(R.id.place_description);
        placeIv = findViewById(R.id.place_image);


        //set views
        if(what){ // adapter - list

            setView(name, lat, lon, category, note, timestamp, photo);


        }else{ // map


            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeInMillis(time);
            timestamp = DateFormat.format("yyyy/MM/dd hh:mm aa", calendar).toString();

            lat = String.valueOf(lat2);
            lon = String.valueOf(lon2);
            status = String.valueOf(status2);

            setView(name, lat, lon, category, note, timestamp, photo);


        }





    }

    private void setView(String name, String lat, String lon, String category, String note, String timestamp, String photo) {

        nameTv.setText(name);
        latTv.setText(lat);
        lonTv.setText(lon);
        categoryTv.setText(category);
        descriptionTv.setText(note);
        timeTv.setText(timestamp);
        try{
            Picasso.get().load(photo).placeholder(R.drawable.test_image).into(placeIv);
        }catch (Exception e){

        }


    }






    /*
     * Toolbar 세팅
     * */

    private void setToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbar_title.setText("Journey Details");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }




}