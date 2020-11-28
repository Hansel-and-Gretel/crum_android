package kr.ac.konkuk.cookiehouse.Social;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ac.konkuk.cookiehouse.General.RetrofitConnection;
import kr.ac.konkuk.cookiehouse.General.RetrofitInterface;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;
import kr.ac.konkuk.cookiehouse.adapters.AdapterJourneys;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocialActivity extends AppCompatActivity {
    private static final String TAG = "SocialActivity";
    private static final int ACTIVITY_NUM=2;
    private Context mContext = SocialActivity.this;

    RecyclerView recyclerView;
    AdapterJourneys adapterJourneys;

    private List<String> followingList;

    RetrofitInterface retrofitInterface;

    List<ModelJourney> postList, journeyList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        Log.d(TAG, "SNS");

        setupBottomNavigationView();
        setToolbar();


        postList = new ArrayList<>();

        //recycler view 이랑 속성들
        recyclerView = (RecyclerView)findViewById(R.id.postRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        //최신 포스트 먼저 보여주기
//        layoutManager.setStackFromEnd(true);
//        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);



        //fetchJourney();

    }

//    public void fetchJourney(){
//
//        retrofitInterface = RetrofitConnection.getApiClient().create(RetrofitInterface.class);
//
//        Map<String, String> data = new HashMap<>();
//        Call <List<ModelJourney>> call = retrofitInterface.getJourney();
//
//        call.enqueue(new Callback<List<ModelJourney>>() {
//
//            @Override
//            public void onResponse(Call<List<ModelJourney>> call, Response<List<ModelJourney>> response) {
//
//                if(!response.isSuccessful()){ // 실패시
//                    Log.d("SocialActivity", "왜?"+response.code());
//                    return;
//                }
//
//
//                journeyList = response.body();
//
//
////                for(ModelJourney journey : journeyList){
////                    postList.add(journey);
////                }
//
//                Log.d("TAG","Response = "+journeyList);
//
//
////                for(ModelJourney modelJourney: response.body()){
////                    Log.e(TAG, modelJourney.getName());
////                    journeyList.add(modelJourney);
////
////                }
//
////                ModelJourney journey = response.body();
////                journeyList.add(journey);
////                Log.d(TAG, "이 여행이 추가됨: " + journey.getName());
//
//                adapterJourneys = new AdapterJourneys(SocialActivity.this, journeyList);
////                adapterJourneys.setJourneyList(journeyList);
//                recyclerView.setAdapter(adapterJourneys);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<ModelJourney>> call, Throwable t) {
//                Log.d("SocialActivity", "error loading from API 실패!");
//
//
//            }
//        });
//
//    }




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



    /*
     * Toolbar 세팅
     * */

    private void setToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbar_title.setText("Cookie House");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


}
