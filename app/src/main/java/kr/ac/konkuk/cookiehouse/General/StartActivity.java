package kr.ac.konkuk.cookiehouse.General;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.HashMap;
import java.util.List;

import kr.ac.konkuk.cookiehouse.Home.MainActivity;
import kr.ac.konkuk.cookiehouse.Path.CreateJourneyActivity;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.models.ModelUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static kr.ac.konkuk.cookiehouse.models.ModelUser.USER;

public class StartActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSIONS_GRANTED = 100;
//11.22

    RetrofitConnection retrofitConnection = new RetrofitConnection();
    RetrofitInterface retrofitInterface = RetrofitConnection.getApiClient().create(RetrofitInterface.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //11.29 TODO 위치 변경
        checkPermission();


        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLoginDialog();
            }
        });

        findViewById(R.id.signupBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignupDialog();
            }
        });

    }

    private void handleLoginDialog() {



        View view = getLayoutInflater().inflate(R.layout.activity_login, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.btn_login);
        final EditText emailEdit= view.findViewById(R.id.input_email);
        final EditText passwordEdit = view.findViewById(R.id.input_password);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<ModelUser> userLoginCall = retrofitInterface.executeLogin(map);

                //콜백
                userLoginCall.enqueue(new Callback<ModelUser>() {
                    @Override
                    public void onResponse(Call<ModelUser> userLoginCall, Response<ModelUser> loginResponse) {


                        if(loginResponse.code() == 200){
                            USER = null;
                            USER = loginResponse.body();    // store user info into model
                            Log.i("USER_ID", String.valueOf(USER.getId()));
                            // TODO 지워야대 --> 테스트용
                            if(USER.getId()!=0){
                                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(StartActivity.this, "아이디 또는 비밀번호가 잘못되었거나 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }

                        }else if(loginResponse.code() == 400){
                            Toast.makeText(StartActivity.this, "아이디 또는 비밀번호가 잘못되었거나 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ModelUser> call, Throwable t) {
                        Toast.makeText(StartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


    }




    private void handleSignupDialog() {

        View view = getLayoutInflater().inflate(R.layout.activity_signup, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button signupBtn  = view.findViewById(R.id.btn_signup);
        final EditText nameEdit = view.findViewById(R.id.input_username);
        final EditText emailEdit = view.findViewById(R.id.input_email);
        final EditText passwordEdit = view.findViewById(R.id.input_password);
        final EditText lstyleEdit= view.findViewById(R.id.input_lifestyle);
        final EditText jtypeEdit = view.findViewById(R.id.input_journeytype);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> map = new HashMap<>();

                map.put("name", nameEdit.getText().toString());
                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());
                map.put("lifestyle", lstyleEdit.getText().toString());
                map.put("journeytype", jtypeEdit.getText().toString());


                //Call<Void> call = retrofitInterface.executeSignup(map);
                Call<Void> call = retrofitInterface.executeSignup(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if(response.code() != 500){
                            Toast.makeText(StartActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        }else if (response.code() == 500) {
                            Toast.makeText(StartActivity.this, "이미 가입되어 있습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(StartActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });




    }

    // 카메라 권한 설정 체크를 위한 함수
    public void checkPermission() {        // ted 라이브러리 사용해서 권한 체크
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(StartActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(StartActivity.this, "권한 거부: " + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("ZEKAK 사용을 위해서는 카메라, 갤러리 접근 권한이 필요합니다")
                .setDeniedMessage("[설정] > [권한] 에서 권한을 다시 허용할 수 있습니다")
                .setDeniedMessage(Manifest.permission.CAMERA)
                .setPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA})
                .check();
    }
}