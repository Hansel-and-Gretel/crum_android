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
import static kr.ac.konkuk.cookiehouse.models.ModelUser.USER;


public class MyPageActivity extends AppCompatActivity {
    private static final String TAG = "MyPageActivity";
    private static final int ACTIVITY_NUM = 4;    //bottomnavigationviewhelper
    private Context mContext = MyPageActivity.this;


    RecyclerView recyclerView_public, recyclerView_private;
    TextView usernameTv, followerTv, followingTv, journeyTv, lifeTv;
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

        usernameTv.setText(USER.getUserName());
        journeyTv.setText(USER.getjourneyType());
        lifeTv.setText(USER.getLifeStyle());

    
        getFollow();
    }

    private void getFollow() {
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
