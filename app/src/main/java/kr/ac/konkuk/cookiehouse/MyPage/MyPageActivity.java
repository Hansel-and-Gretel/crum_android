package kr.ac.konkuk.cookiehouse.MyPage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;

import static android.content.ContentValues.TAG;
import static kr.ac.konkuk.cookiehouse.models.ModelUser.USER;


public class MyPageActivity extends AppCompatActivity {
    private static final String TAG = "MyPageActivity";
    private static final int ACTIVITY_NUM = 4;    //bottomnavigationviewhelper
    private Context mContext = MyPageActivity.this;


    RecyclerView recyclerView_public, recyclerView_private;
    TextView usernameTv, followerTv, followingTv, journeyTv, lifeTv;
    String username, follower, following, journeyType, lifeStyle;
    ImageView avatarIv;






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

        username = USER.getUserName();
        journeyType = USER.getJourneyType();
        lifeStyle = USER.getLifeStyle();

        usernameTv.setText(username);
        journeyTv.setText(journeyType);
        lifeTv.setText(lifeStyle);

    
        getFollow();
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
