package kr.ac.konkuk.cookiehouse.Path;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import kr.ac.konkuk.cookiehouse.General.LocationResultHelper;
import kr.ac.konkuk.cookiehouse.General.MyBackgroundLocationService;
import kr.ac.konkuk.cookiehouse.Home.MainActivity;
import kr.ac.konkuk.cookiehouse.R;

public class RecordJourneyActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{


    public static final int DEFAULT_ZOOM = 15;
    private final double ISLAMABAD_LAT = 37.541591;
    private final double ISLAMABAD_LNG = 127.078851;
    public static final int PERMISSION_REQUEST_CODE = 9001;
    private static final int PLAY_SERVICES_ERROR_CODE = 9002;
    public static final int GPS_REQUEST_CODE = 9003;
    private boolean mLocationPermissionGranted;


    public static final String TAG = "MyTag";
    private TextView mOutputText;
    private Button mBtnLocationRequest, mBtnStartService, mBtnStopService;
    private FusedLocationProviderClient mLocationClient;
    private LocationCallback mLocationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_journey);

        mOutputText = findViewById(R.id.testresult_tv);

        //수동 로케이션 요청
//        mBtnLocationRequest = findViewById(R.id.btn_location_request);
        mBtnStartService = findViewById(R.id.start_btn);
        mBtnStopService = findViewById(R.id.stop_btn);


        locationinit();


        mLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {


                if (locationResult == null) {
                    Log.d(TAG, "onLocationResult: location error");
                    return;
                }

                List<Location> locations = locationResult.getLocations();

                LocationResultHelper helper = new LocationResultHelper(RecordJourneyActivity.this, locations);

                helper.showNotification();

                helper.saveLocationResults();

//                Toast.makeText(RecordJourneyActivity.this, "Location received: " + locations.size(), Toast.LENGTH_SHORT).show();

                mOutputText.setText(helper.getLocationResultText());

//                Log.d(TAG, "onLocationResult: " + location.getLatitude() + " \n" +
//                        location.getLongitude());


            }
        };

//        mBtnLocationRequest.setOnClickListener(this::requestBatchLocationUpdates);
//        mBtnStartService.setOnClickListener(this::startLocationService);
        mBtnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });
//        mBtnStopService.setOnClickListener(this::stopLocationService);
        mBtnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationService();
            }
        });



    }

    private void locationinit() {

        if (isServicesOk()) {
            if (isGPSEnabled()) {
                if (checkLocationPermission()) {

                    Toast.makeText(this, "모든 설정 정상", Toast.LENGTH_SHORT).show();


                } else {
                    requestLocationPermission();
                }
            }
        }

    }

    private boolean isServicesOk() {

        GoogleApiAvailability googleApi = GoogleApiAvailability.getInstance();

        int result = googleApi.isGooglePlayServicesAvailable(this);

        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApi.isUserResolvableError(result)) {
            Dialog dialog = googleApi.getErrorDialog(this, result, PLAY_SERVICES_ERROR_CODE, task ->
                    Toast.makeText(this, "사용자에의해 Dialog가 취소됩니다.", Toast.LENGTH_SHORT).show());
            dialog.show();
        } else {
            Toast.makeText(this, "현재 앱을 사용하기 위해선, 구글 Play Services가 필요합니다. ", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean isGPSEnabled() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnabled) {
            return true;
        } else {

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS 권환확인")
                    .setMessage("SweeTrail을 사용하기 위해선 GPS사용권한 승인이 필요합니다. 승인하시겠습니까?")
                    .setPositiveButton("Yes", ((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();

        }

        return false;
    }

    private boolean checkLocationPermission() {

        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
            }
        }
    }




    private void startLocationService() {
        //start background location service

        Intent intent = new Intent(RecordJourneyActivity.this, MyBackgroundLocationService.class);
        ContextCompat.startForegroundService(RecordJourneyActivity.this, intent);
        Toast.makeText(this, "위치기록 서비스를 시작합니다.", Toast.LENGTH_SHORT).show();


    }

    private void stopLocationService() {
        //stop background location service

        Intent intent = new Intent(RecordJourneyActivity.this, MyBackgroundLocationService.class);
        stopService(intent);
        Toast.makeText(this, "위치기록 서비스가 종료되었습니다.", Toast.LENGTH_SHORT).show();

    }


    private void requestBatchLocationUpdates(View view) {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval( 15000);
        locationRequest.setFastestInterval(15000);

        locationRequest.setMaxWaitTime(15 * 1000);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            Toast.makeText(this, "권한이 승인되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "권한이 승인되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        //위치 업데이트 삭제
        mLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOutputText.setText(LocationResultHelper.getSavedLocationResults(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        //위치 업데이트 삭제
        mLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals(LocationResultHelper.KEY_LOCATION_RESULTS)) {
            mOutputText.setText(LocationResultHelper.getSavedLocationResults(this));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_REQUEST_CODE) {

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerEnabled) {
                Toast.makeText(this, "GPS is enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS not enabled. Unable to show user location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "Location Services에 연결합니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}



