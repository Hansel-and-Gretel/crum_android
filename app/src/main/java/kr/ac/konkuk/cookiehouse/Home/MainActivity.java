package kr.ac.konkuk.cookiehouse.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import kr.ac.konkuk.cookiehouse.DataStorage.Places;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    // this a TAG just for organising my log
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM=0;
    // 현재 context
    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "OnCreate: Starting");

        //toolbar
        setToolbar();

        //nav bar
        setupBottomNavigationView();
        setUpViewPager();

    }


    /*
    *  viewpager : add home, mypage fragments
    *  삭제예정
    * */
    private void setUpViewPager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment() );
        adapter.addFragment(new MyPageFragment());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container); //layout_center_viewpager 내부
        viewPager.setAdapter(adapter);


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


    /*
    * Toolbar 세팅
    * */

    private void setToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView)findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        toolbar_title.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }




//    //툴바 메누
//    //메뉴 등록하기
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//        return true;
//    }
//
//
//    //앱바 메뉴 클릭 이벤트
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mypage:
//                // User chose the "Settings" item, show the app settings UI...
//                Toast.makeText(getApplicationContext(),"마이페이지", Toast.LENGTH_SHORT).show();
//                return true;
//
////            case R.id.menu:
////                Toast.makeText(getApplicationContext(),"메뉴1 클릭", Toast.LENGTH_SHORT).show();
////                return true;
//
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//        }
//    }






}