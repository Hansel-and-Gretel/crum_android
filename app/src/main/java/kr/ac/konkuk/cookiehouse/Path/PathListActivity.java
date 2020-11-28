package kr.ac.konkuk.cookiehouse.Path;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.ac.konkuk.cookiehouse.DataStorage.PlacesDBControl;
import kr.ac.konkuk.cookiehouse.DataStorage.PlacesDBHelper;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.adapters.AdapterLocation;
import kr.ac.konkuk.cookiehouse.models.Places;

public class PathListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    PlacesDBControl myDb;
    ArrayList<String> place_id, place_name, place_time, place_lon, place_lat;
    AdapterLocation adapterLocation;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_list);
        setToolbar();

        recyclerView = findViewById(R.id.recyclerview);

        myDb = new PlacesDBControl(PathListActivity.this);
        place_id = new ArrayList<>();
        place_name = new ArrayList<>();
        place_time = new ArrayList<>();
        place_lon = new ArrayList<>();
        place_lat = new ArrayList<>();

        storePathInArrays();

        adapterLocation = new AdapterLocation(PathListActivity.this, place_id, place_name, place_time, place_lon, place_lat);
        recyclerView.setAdapter(adapterLocation);
        recyclerView.setLayoutManager(new LinearLayoutManager(PathListActivity.this));


    }

    public void storePathInArrays(){

        Cursor cursor = myDb.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "기록된 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                place_id.add(cursor.getString(0));
                place_name.add(cursor.getString(1));
                place_time.add(cursor.getString(2));
                place_lon.add(cursor.getString(3));
                place_lat.add(cursor.getString(4));

            }
        }

    }






    /*
     * Toolbar 세팅
     * */

    private void setToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbar_title.setText("Your sweetrail");
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