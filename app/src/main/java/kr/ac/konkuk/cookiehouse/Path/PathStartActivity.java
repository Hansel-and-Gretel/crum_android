package kr.ac.konkuk.cookiehouse.Path;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;

public class PathStartActivity extends AppCompatActivity {

    private static final String TAG = "PathStartActivity";
    private static final int ACTIVITY_NUM=1;
    private Context mContext = PathStartActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_journey_new);
        Log.d(TAG, "onCreate : started");
        setupBottomNavigationView();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
