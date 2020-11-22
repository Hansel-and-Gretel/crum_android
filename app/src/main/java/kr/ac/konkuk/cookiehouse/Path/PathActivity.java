package kr.ac.konkuk.cookiehouse.Path;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;

public class PathActivity extends AppCompatActivity {
    private static final String TAG = "PathActivity";
    private static final int ACTIVITY_NUM=1;
    private Context mContext = PathActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


}
