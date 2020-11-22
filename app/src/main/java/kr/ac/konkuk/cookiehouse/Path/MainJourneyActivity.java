package kr.ac.konkuk.cookiehouse.Path;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;

public class MainJourneyActivity extends AppCompatActivity {
    private static final String TAG = "MainJourneyActivity";
    private static final int ACTIVITY_NUM = 1;
    private static final String PREFS_NAME = "Journey_Status";     // Current journey info, one at a time
    // TODO 아래 load(), save() 함수의 preferences key 이름 여기에서 선언

    SharedPreferences appData;
    public Journey currentJourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //loadJourney();
        setContentView(R.layout.layout_journey_main);

        Button currentJourneyBtn = findViewById(R.id.btn_current_journey);
        Button createJourneyBtn = findViewById(R.id.btn_start_new_journey);
        Button previousJourneyBtn = findViewById(R.id.btn_previous_journey);

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        currentJourneyBtn.setOnClickListener(onClickListener);
        createJourneyBtn.setOnClickListener(onClickListener);
        previousJourneyBtn.setOnClickListener(onClickListener);

        //setupBottomNavigationView();
    }


    class BtnOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_current_journey:

                    break;
                case R.id.btn_start_new_journey:
                    Intent intent = new Intent(MainJourneyActivity.this, CreateJourneyActivity.class);
                    break;
                case R.id.btn_previous_journey:
                    //???
                    break;
            }
        }
    }

    public class Journey {
        public int id;                //PK
        public int path;      // current path, path ID
        public String name;           // journeyNmae (서버)
        public String type;
        public String party;          // accompany (서버)
        public int frequency;         // pinFrequency(서버)
        public String summary;           // journeyNmae (서버)
        public String image;
        public boolean status;            // [GUIDE] true: active / false: paused, stopped etc.
        public boolean shared;


        // Places 구조체 객체 만들 때 사용
        public Journey(int id, int path, String name, String type, String party, int frequency, String summary, String image, boolean status, boolean shared){
            //this.id = id;       // TODO: 서버에서 자동 생성인가?
            this.path = path;
            this.name = name;
            this.type = type;
            this.party = party;
            this.frequency = frequency;
            this.summary = summary;
            this.image = image;
            this.status = status;
            this.shared = shared;
        }
    }



        // 설정값 중 현재 Journey 불러오는 함수
    private boolean loadJourney() {
        appData = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        currentJourney = null;
        currentJourney.status = appData.getBoolean(PREFS_NAME, false);     // Current journey, null if empty
        if(!currentJourney.status){        // No journeys active
            return false;
        }
        // TODO 일단id는 뻈음 -  87줄
        // 그 journey 기록 끝나야 입력 가능한 것들도 일단 뺌
        currentJourney.path = appData.getInt("Journey_Path", 0);    // path ID
        currentJourney.name = appData.getString("Journey_Name", null);
        currentJourney.type = appData.getString("Journey_Type", null);
        currentJourney.party = appData.getString("Journey_Party", null);
        currentJourney.frequency = appData.getInt("Journey_Frequency", 0);

        return true;
    }

    // 새로운 Journey 저장
    private void save(Journey newJourney) {        // 지금은 카테고리만 저장하는 역할(설정값 저장할게 이것 밖에없음 아직), 나중에 알림기능이나, 통계 on/off 블라블라
        SharedPreferences.Editor editor = appData.edit();       // SharedPreferences (설정 저장용 파일) 열기
        editor.putBoolean(PREFS_NAME, true);

        //editor.putString("Journey_Path", newJourney.path);// 아직 path 없으므로 생성 ㄴㄴ?
        editor.putString("Journey_Name", newJourney.name);
        editor.putString("Journey_Type", newJourney.type);
        editor.putString("Journey_Party", newJourney.party);
        editor.putInt("Journey_Frequency", newJourney.frequency);

        editor.apply();
    }

    /*
     * Bottom Navigation view setup
     * */
    private void setupBottomNavigationView(){
        Log.d(TAG,"SetupBottomNavigationView : setting up BottomNavigationView");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
