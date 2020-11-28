package kr.ac.konkuk.cookiehouse.MyPage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.ac.konkuk.cookiehouse.General.RetrofitConnection;
import kr.ac.konkuk.cookiehouse.General.RetrofitInterface;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;
import kr.ac.konkuk.cookiehouse.adapters.AdapterThumbs;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kr.ac.konkuk.cookiehouse.models.ModelUser.USER;


public class MyPageActivity extends AppCompatActivity {
    private static final String TAG = "MyPageActivity";
    private static final int ACTIVITY_NUM = 4;    //bottomnavigationviewhelper
    private Context mContext = MyPageActivity.this;


    RecyclerView recyclerView_public, recyclerView_private;
    TextView usernameTv, followerTv, followingTv, journeyTv, lifeTv;
    String username;
    String follower;
    String following;
    String journeyType;
    String lifeStyle;
    int profileId;
    ImageView avatarIv;
    Button publicBtn, privateBtn;
    RetrofitInterface retrofitInterface;

    List<ModelJourney> journeyList, journeyListPrivate;
    AdapterThumbs adapterThumbs, adapterThumbsPrivate;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        Log.d(TAG, "프로필페이지");

        setupBottomNavigationView();


        usernameTv = findViewById(R.id.usernameTv);
        followerTv = findViewById(R.id.followerTv);
        followingTv = findViewById(R.id.followingTv);
        journeyTv = findViewById(R.id.journeyType);
        lifeTv = findViewById(R.id.lifeStyle);
        publicBtn = findViewById(R.id.public_list);
        privateBtn = findViewById(R.id.private_list);


        recyclerView_public = findViewById(R.id.recyclerView_public);
        recyclerView_public.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerView_public.setLayoutManager(linearLayoutManager);
        journeyList = new ArrayList<>();
        adapterThumbs = new AdapterThumbs(MyPageActivity.this,journeyList);
        recyclerView_public.setAdapter(adapterThumbs);


        recyclerView_private = findViewById(R.id.recyclerView_private);
        recyclerView_private.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new GridLayoutManager(this, 2);
        recyclerView_private.setLayoutManager(linearLayoutManager2);
        journeyListPrivate = new ArrayList<>();
        adapterThumbsPrivate = new AdapterThumbs(MyPageActivity.this,journeyListPrivate);
        recyclerView_private.setAdapter(adapterThumbsPrivate);


        recyclerView_public.setVisibility(View.VISIBLE);
        recyclerView_private.setVisibility(View.GONE);



        username = USER.getUserName();
        journeyType = USER.getJourneyType();
        lifeStyle = USER.getLifeStyle();
        profileId = USER.getId();

        Log.d(TAG, "onCreate: 누가주인?" + profileId);

        usernameTv.setText(username);
        journeyTv.setText(journeyType);
        lifeTv.setText(lifeStyle);

    
        getFollow();
        myPublicThumbs();

        publicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView_public.setVisibility(View.VISIBLE);
                recyclerView_private.setVisibility(View.GONE);
                myPublicThumbs();
            }
        });

        privateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView_public.setVisibility(View.GONE);
                recyclerView_private.setVisibility(View.VISIBLE);
                myPrivateThumbs();
            }
        });


    }

    private void myPrivateThumbs() {
        retrofitInterface = RetrofitConnection.getApiClient().create(RetrofitInterface.class);

        Call<List<ModelJourney>> call = retrofitInterface.getMyJourney(1);

        call.enqueue(new Callback<List<ModelJourney>>() {

            @Override
            public void onResponse(Call<List<ModelJourney>> call, Response<List<ModelJourney>> response) {

                journeyListPrivate.clear();

                if(!response.isSuccessful()){ // 실패시
                    Log.d("Mypage", "왜실패?"+response.code());
                    return;
                }


                Log.d("TAG","Response = "+ journeyListPrivate);


                for(ModelJourney modelJourney: response.body()){

                    if(!modelJourney.isShared()){
                        journeyListPrivate.add(modelJourney);
                        Log.d(TAG, modelJourney+"님은 저희와 함께 갑시다.");
                    }else{
                        Log.d(TAG, "이번 탈락자는!"+modelJourney);
                    }

                }

                Collections.reverse(journeyListPrivate);
                adapterThumbsPrivate.notifyDataSetChanged();
                adapterThumbsPrivate = new AdapterThumbs(MyPageActivity.this, journeyListPrivate);
                recyclerView_private.setAdapter(adapterThumbsPrivate);

                if(journeyListPrivate.isEmpty()){
                    Toast.makeText(MyPageActivity.this, "비엇다", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<ModelJourney>> call, Throwable t) {
                Log.d("MYpage", "error loading from API 실패!");


            }
        });

    }

    private void myPublicThumbs() {

        retrofitInterface = RetrofitConnection.getApiClient().create(RetrofitInterface.class);

        Call<List<ModelJourney>> call = retrofitInterface.getMyJourney(1);

        call.enqueue(new Callback<List<ModelJourney>>() {

            @Override
            public void onResponse(Call<List<ModelJourney>> call, Response<List<ModelJourney>> response) {

                journeyList.clear();

                if(!response.isSuccessful()){ // 실패시
                    Log.d("Mypage", "왜실패?"+response.code());
                    return;
                }


                Log.d("TAG","Response = "+ journeyList);


                for(ModelJourney modelJourney: response.body()){

                   if(modelJourney.isShared()){
                       journeyList.add(modelJourney);
                       Log.d(TAG, modelJourney+"님은 저희와 함께 갑시다.");
                   }else{
                       Log.d(TAG, "이번 탈락자는!"+modelJourney);
                   }


                }

                Collections.reverse(journeyList);
                adapterThumbs.notifyDataSetChanged();
                adapterThumbs = new AdapterThumbs(MyPageActivity.this, journeyList);
                recyclerView_public.setAdapter(adapterThumbs);

                if(journeyList.isEmpty()){
                    Toast.makeText(MyPageActivity.this, "비엇다", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<ModelJourney>> call, Throwable t) {
                Log.d("Mypage", "error loading from API 실패!");


            }
        });


    }








    private void getFollow() {

        Log.d(TAG, "getFollowers: "+ username);


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
