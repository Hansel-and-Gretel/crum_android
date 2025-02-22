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

import java.util.List;

import kr.ac.konkuk.cookiehouse.General.RetrofitConnection;
import kr.ac.konkuk.cookiehouse.General.RetrofitInterface;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Social.SocialActivity;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;
import kr.ac.konkuk.cookiehouse.adapters.AdapterJourneys;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import kr.ac.konkuk.cookiehouse.models.ModelUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;


public class MainJourneyActivity extends AppCompatActivity {
    private static final String TAG = "MainJourneyActivity";
    private static final int ACTIVITY_NUM = 1;

    RetrofitInterface retrofitInterface = RetrofitConnection.getApiClient().create(RetrofitInterface.class);

    ModelJourney userJourney;


    Button currentJourneyBtn;
    Button dummy;

    //이거 잇어야 하단nav바 작동해요
    private Context mContext = MainJourneyActivity.this;

    List<ModelJourney> userJourneyList;
    boolean existing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_journey_main);
        setupBottomNavigationView();

        currentJourneyBtn = findViewById(R.id.btn_current_journey);
        dummy = findViewById(R.id.btn_dummy);        // 아무 여정 없을 떄
        Button createJourneyBtn = findViewById(R.id.btn_start_new_journey);
        Button previousJourneyBtn = findViewById(R.id.btn_previous_journey);

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        createJourneyBtn.setOnClickListener(onClickListener);
        previousJourneyBtn.setOnClickListener(onClickListener);
        currentJourneyBtn.setOnClickListener(onClickListener);

        userJourney = new ModelJourney();
        checkValidJourney();
    }


    class BtnOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_current_journey:
                    Intent intent1 = new Intent(MainJourneyActivity.this, RecordJourneyActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.btn_start_new_journey:
                    Intent intent2 = new Intent(MainJourneyActivity.this, CreateJourneyActivity.class);
                    if(existing) {
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
                                Toast.makeText(MainJourneyActivity.this, "Successfully stored to server: " + userJourney.name, Toast.LENGTH_LONG).show();
                                deletingFormer.dismiss();
                                startActivity(intent2);
                                finish();
                            }
                        });
                        deletingFormer.show();
                    } else {
                        startActivity(intent2);
                        finish();
                    }
                    break;

                case R.id.btn_previous_journey:
                    //???
                    break;
            }
        }
    }



    // 서버에서 현재 기록 중인(기록가능한) Journey 불러오는 함수
    private void checkValidJourney() {   // get existing jouney & save it to 'currentJourney', false if none
        //1. 서버에서 유저 아이디 가지고 여정 있는지 검사
        //2. status == true 인거 없으면
        // No journeys active


        Call<List<ModelJourney>> call = retrofitInterface.getMyJourney(ModelUser.USER.getId());
        Log.i("Getting journeys for ID", String.valueOf(ModelUser.USER.getId()));


        call.enqueue(new Callback<List<ModelJourney>>() {

            @Override
            public void onResponse(Call<List<ModelJourney>> call, Response<List<ModelJourney>> response) {

                if (!response.isSuccessful()) { // 실패시
                    Log.d("SocialActivity", "ERROR메세지-" + response.code());
                    return;
                }

                userJourneyList = response.body();
                setResults(userJourneyList);

                Log.d("TAG", "Response = " + userJourneyList);
            }

            @Override
            public void onFailure(Call<List<ModelJourney>> call, Throwable t) {
                Log.d("SocialActivity", "error loading from API 실패!");
            }
        });
    }

    private void setResults(List<ModelJourney> userJourneyList){
        existing = false;   // no vaild(editable) journeys found for this user

        Log.i("Journeys Found for user", String.valueOf(userJourneyList.size()));

        for(int i=0; i<userJourneyList.size(); i++){
            Log.i("찾은 Journey", String.valueOf(userJourneyList.get(i).name));
            if(userJourneyList.get(i).status){     // if there is a valid(기록 중 or 기록 가능) journey to this user
                userJourney = userJourneyList.get(i);    // then save that journey info data to local
                userJourney.status = true;
                ModelJourney.setCurrentJourney(userJourney);        // 변수 저장해둠 --> 수정?
                Log.i("여정 존재함", userJourney.name);
                existing = true;
                //  break;
                // TODO 그냥 true 유일하지 않을 수도 있으니까 그냥 다 출력해보고 맨 마지막꺼 쓰자
            }
        }

        if(existing){
            currentJourneyBtn.setText(ModelJourney.getCurrentJourney().getName());
            currentJourneyBtn.setVisibility(View.VISIBLE);
        } else {
            dummy.setVisibility(View.VISIBLE);
        }
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
