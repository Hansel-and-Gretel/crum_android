//maybe i dont need it, just save it for random siutation

package kr.ac.konkuk.cookiehouse.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.ac.konkuk.cookiehouse.MyPage.MyPageActivity;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.Home.MainActivity;
import kr.ac.konkuk.cookiehouse.Path.PathActivity;
import kr.ac.konkuk.cookiehouse.Places.PlacesActivity;
import kr.ac.konkuk.cookiehouse.Social.SocialActivity;

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationView bottomNavigationView){

    }

    public static void enableNavigation(final Context context, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){


                    case R.id.home:
                        Intent intent1 = new Intent(context, MainActivity.class); //ACTIVITY_NUM=0
                        context.startActivity(intent1);
                        break;

                    case R.id.path:
                        Intent intent2 = new Intent(context, PathActivity.class); //ACTIVITY_NUM=1
                        context.startActivity(intent2);
                        break;

                    case R.id.social:
                        Intent intent3 = new Intent(context, SocialActivity.class); //ACTIVITY_NUM=2
                        context.startActivity(intent3);
                        break;

                    case R.id.places:
                        Intent intent4 = new Intent(context, PlacesActivity.class); //ACTIVITY_NUM=3
                        context.startActivity(intent4);
                        break;

                    case R.id.mypage:
                        Intent intent5 = new Intent(context, MyPageActivity.class); //ACTIVITY_NUM=4
                        context.startActivity(intent5);
                        break;

                }


                return false;
            }
        });
    }



}
