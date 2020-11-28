package kr.ac.konkuk.cookiehouse.Path;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import kr.ac.konkuk.cookiehouse.DataStorage.PlacesDBControl;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.models.ModelPlaces;
import kr.ac.konkuk.cookiehouse.models.Places;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    PlacesDBControl db, myDb;
    Button closeBtn;
    public static final int DEFAULT_ZOOM = 15;
    private FusedLocationProviderClient mLocationClient;
    private LocationCallback mLocationCallback;

    private ImageButton mBtnLocate;
    private TextView mOutputText;
    private Button mBtnBatchLocation;
    private EditText mSearchAddress;


    //건대 위치 기본
    private LatLng ku = new LatLng(50.0, 8.58275032043457);

    // 마커리스트
    List<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment_container);
        mapFragment.getMapAsync(this);


        mSearchAddress = findViewById(R.id.et_address);
        mBtnLocate = findViewById(R.id.btn_locate);
        mOutputText = findViewById(R.id.tv_location);

        mBtnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        closeBtn = findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeButtonClicked(v);
            }
        });


    }




    private void gotoLocation(double lat, double lng) {

        LatLng latLng = new LatLng(lat, lng);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM);

        mMap.moveCamera(cameraUpdate);
//        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }







    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        db = new PlacesDBControl(MapsActivity.this);



        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);

        markerList = new ArrayList<>();
        List<ModelPlaces> placesList = db.getAllPlaces();

        for(ModelPlaces p : placesList){
            String myInfo = "ID: "+p.getId()+" Lat: "+p.getLatitude() + " Lng: "+p.getLongitude()+" place NAME "+p.getName();
            Log.d("myinfo", "위치!"+myInfo);

            markerList.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(p.getLatitude(), p.getLongitude()))
                    .title(String.valueOf(p.getId())
                    )
            ));

        }


        markerList.add(mMap.addMarker(new MarkerOptions().position(ku).title("KONKUK")));

        //마커를 여러개 돌면서 위한 루프
        for (Marker m : markerList) {
            // Add a marker and move the camera
            LatLng latLng = new LatLng(m.getPosition().latitude, m.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng).title("KONKUK"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM)); //카메라줌

        }





    }


    //마커 클릭시 이벤트 처리
    @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(this, "현재 마커 위치:"+marker.getPosition().toString(), Toast.LENGTH_SHORT).show();


        return false;
    }

    //맵 길게 눌럿을때,핀찍기 + 인텐트 오픈 하는거 나중에 추가하면될듯
    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("NEW PLACE")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }


    private void closeButtonClicked(View view) {
        finish();
    }

//    public void storePathInArrays(){
//
//        Cursor cursor = myDb.readAllData();
//        if(cursor.getCount() == 0){
//            Toast.makeText(this, "기록된 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
//        }else{
//            while(cursor.moveToNext()){
//                place_id.add(cursor.getString(0));
//                place_name.add(cursor.getString(1));
//                place_time.add(cursor.getString(2));
//                place_lon.add(cursor.getString(3));
//                place_lat.add(cursor.getString(4));
//
//            }
//        }
//
//
//    }




}