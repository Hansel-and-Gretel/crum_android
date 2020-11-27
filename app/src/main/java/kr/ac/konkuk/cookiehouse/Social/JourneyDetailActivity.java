package kr.ac.konkuk.cookiehouse.Social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ac.konkuk.cookiehouse.General.RetrofitConnection;
import kr.ac.konkuk.cookiehouse.General.RetrofitInterface;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.adapters.AdapterJourneys;
import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JourneyDetailActivity extends AppCompatActivity {

    int jId;
    ImageView jImage;
    TextView jSum, uName, jType, jWith,jTitle;

    RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_detail);


        Intent intent = getIntent();
        jId = intent.getExtras().getInt("journeyId");


        jImage = findViewById(R.id.journey_image);
        jSum = findViewById(R.id.journey_description);
        jWith = findViewById(R.id.with_tv);
        jType = findViewById(R.id.journey_type_tv);
        uName = findViewById(R.id.uName_tv);
        jTitle = findViewById(R.id.title_tv);


        //현재 여정 정보 불러오기
        loadJourneyInfo(jId);


    }

    private void loadJourneyInfo(int id) {

        Log.d("POST ID", "loadJourneyInfo 이친구의 id는 "+id);

        retrofitInterface = RetrofitConnection.getApiClient().create(RetrofitInterface.class);

        Call<ModelJourney> call = retrofitInterface.getOneJourney(id);

        call.enqueue(new Callback<ModelJourney>() {
            @Override
            public void onResponse(Call<ModelJourney> call, Response<ModelJourney> response) {
                if(!response.isSuccessful()){ // 실패시
                    Log.d("상세페이지", "왜?"+response.code());
                    return;
                }
                ModelJourney journey = response.body();

                String post_title, post_with, post_type, post_username, post_image, post_sum;

//                for(ModelJourney journey : journeyList){

                post_title = journey.getName();
                jTitle.setText(post_title);
                post_with = journey.getParty();
                jWith.setText(post_with);
                post_type = journey.getType();
                jType.setText(post_type);
                post_username = journey.getUserName();
                uName.setText(post_username);
                post_image = journey.getImage();
                jImage.setVisibility(View.VISIBLE);
                try{
                    Picasso.get().load(post_image).placeholder(R.drawable.test_image).into(jImage);
                }catch (Exception e){

                }
                post_sum = journey.getSummary();
                jSum.setText(post_sum);
//                }


            }

            @Override
            public void onFailure(Call<ModelJourney> call, Throwable t) {

            }
        });


    }
}