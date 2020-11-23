package kr.ac.konkuk.cookiehouse.Path;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;

import static kr.ac.konkuk.cookiehouse.models.ModelJourney.currentJourney;

public class MainJourneyActivity extends AppCompatActivity {
    private static final String TAG = "MainJourneyActivity";
    private static final int ACTIVITY_NUM = 1;
    private static final String PREFS_NAME = "Journey_Status";     // Current journey info, one at a time


    //이거 잇어야 하단nav바 작동해요
    private Context mContext = MainJourneyActivity.this;
    // TODO 아래 load(), save() 함수의 preferences key 이름 여기에서 선언

    SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadJourney();
        setContentView(R.layout.activity_journey_main);
        setupBottomNavigationView();

        Button currentJourneyBtn = findViewById(R.id.btn_current_journey);
        Button createJourneyBtn = findViewById(R.id.btn_start_new_journey);
        Button previousJourneyBtn = findViewById(R.id.btn_previous_journey);

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        currentJourneyBtn.setOnClickListener(onClickListener);
        createJourneyBtn.setOnClickListener(onClickListener);
        previousJourneyBtn.setOnClickListener(onClickListener);


    }


    class BtnOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_current_journey:
                    //Intent intent = new Intent(MainJourneyActivity.this, .class);
                    //startActivity(intent);
                    break;
                case R.id.btn_start_new_journey:
                    Intent intent = new Intent(MainJourneyActivity.this, CreateJourneyActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_previous_journey:
                    //???
                    break;
            }
        }
    }



        // 설정값 중 현재 Journey 불러오는 함수
    private boolean loadJourney() {
        appData = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        currentJourney.status = appData.getBoolean(PREFS_NAME, false);     // Current journey, null if empty
        if(!currentJourney.status){        // No journeys active
            return false;
        }
        // 그 journey 기록 끝나야 입력 가능한 것들도 일단 뺌
        //currentJourney.path = appData.getInt("Journey_Path", 0);    // path ID
        currentJourney.name = appData.getString("Journey_Name", null);
        currentJourney.type = appData.getString("Journey_Type", null);
        currentJourney.party = appData.getString("Journey_Party", null);
        currentJourney.frequency = appData.getInt("Journey_Frequency", 0);

        return true;
    }



    /*
     * Bottom Navigation view setup
     * */
    private void setupBottomNavigationView(){
        Log.d(TAG,"SetupBottomNavigationView : setting up BottomNavigationView");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
