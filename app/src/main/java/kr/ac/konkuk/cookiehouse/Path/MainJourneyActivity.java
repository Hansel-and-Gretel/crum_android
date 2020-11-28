package kr.ac.konkuk.cookiehouse.Path;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.ac.konkuk.cookiehouse.General.RetrofitConnection;
import kr.ac.konkuk.cookiehouse.General.RetrofitInterface;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import kr.ac.konkuk.cookiehouse.models.ModelUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kr.ac.konkuk.cookiehouse.models.ModelJourney.currentJourney;

public class MainJourneyActivity extends AppCompatActivity {
    private static final String TAG = "MainJourneyActivity";
    private static final int ACTIVITY_NUM = 1;

    RetrofitInterface retrofitInterface = RetrofitConnection.getApiClient().create(RetrofitInterface.class);



    //이거 잇어야 하단nav바 작동해요
    private Context mContext = MainJourneyActivity.this;
    // TODO 아래 load(), save() 함수의 preferences key 이름 여기에서 선언

    SharedPreferences appData;
    boolean hasExistingJourney = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_journey_main);
        setupBottomNavigationView();

        Button currentJourneyBtn = findViewById(R.id.btn_current_journey);
        Button createJourneyBtn = findViewById(R.id.btn_start_new_journey);
        Button previousJourneyBtn = findViewById(R.id.btn_previous_journey);

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        createJourneyBtn.setOnClickListener(onClickListener);
        previousJourneyBtn.setOnClickListener(onClickListener);

        hasExistingJourney = checkValidJourney();     // get existing jouney & save it to 'currentJourney', false if none

        if(hasExistingJourney){
            currentJourneyBtn.setText(currentJourney.name);
            currentJourneyBtn.setOnClickListener(onClickListener);      // enable only if journey exists
        } else {
            currentJourneyBtn.setBackgroundColor(0xf1f1f1);
        }

    }


    class BtnOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_current_journey:
                    //Intent intent = new Intent(MainJourneyActivity.this, .class);
                    // TODO 이때 인텐트 할 때, 서버에게 journey.status = true로 바꾸어야됨(나중에 다시 로그인 할 때 이 status값이 true인것만 표시할거거든)
                    //startActivity(intent);
                    break;

                case R.id.btn_start_new_journey:
                    final Dialog deletingFormer = new Dialog(MainJourneyActivity.this);
                    deletingFormer.setContentView(R.layout.layout_replace_journey_dialog);
                    Button cancelBtn = deletingFormer.findViewById(R.id.btn_cancel);
                    Button replaceBtn = deletingFormer.findViewById(R.id.btn_replace);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deletingFormer.dismiss();
                        }
                    });
                    replaceBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO upload current journey to server
                            //
                            Toast.makeText(MainJourneyActivity.this, "Successfully stored to server: "+currentJourney.name, Toast.LENGTH_LONG).show();
                            deletingFormer.dismiss();
                            Intent intent = new Intent(MainJourneyActivity.this, CreateJourneyActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    break;

                case R.id.btn_previous_journey:
                    //???
                    break;
            }
        }
    }



    // 서버에서 현재 기록 중인(기록가능한) Journey 불러오는 함수
    private boolean checkValidJourney() {
        /// TODO
        //1. 서버에서 유저 아이디 가지고 여정 있는지 검사
        //2. status == true 인거 없으면
        // No journeys active
//        Call<ModelJourney> call = retrofitInterface.createJourney(name, type, party, frequency, false, false, ModelUser.USER.getId(), ModelUser.USER.getUserName());
//
//
//        call.enqueue(new Callback<ModelJourney>() {
//            @Override
//            public void onResponse(Call<ModelJourney> call, Response<ModelJourney> response) {
//                if(response.code() == 200){
//                    // TODO
//                    ModelJourney something = response.body();
//                    Toast.makeText(CreateJourneyActivity.this, "Journey Created", Toast.LENGTH_SHORT).show();
//                    saveNewJourney();
//                    Intent intent = new Intent(CreateJourneyActivity.this, RecordJourneyActivity.class);
//                    startActivity(intent);
//                } else if(response.code() == 400) {
//                    Toast.makeText(CreateJourneyActivity.this, "Journey creation failed", Toast.LENGTH_LONG).show();
//                    Log.i("아아아ㅏㅇ", response.message());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelJourney> call, Throwable t) {
//                Toast.makeText(CreateJourneyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//
//        if(none){   // 현재 경로 기록 가능한 여정이 없다면
//            currentJourney.status = false;
//        } else {        // 현재 진행되고 있는 여정이 있다면
//            currentJourney.status = true;
//        }
        return currentJourney.status;
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
