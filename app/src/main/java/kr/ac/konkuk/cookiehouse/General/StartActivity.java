package kr.ac.konkuk.cookiehouse.General;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import kr.ac.konkuk.cookiehouse.Home.MainActivity;
import kr.ac.konkuk.cookiehouse.R;
import kr.ac.konkuk.cookiehouse.models.ModelUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static kr.ac.konkuk.cookiehouse.models.ModelUser.USER;

public class StartActivity extends AppCompatActivity {
//11.22
    RetrofitConnection retrofitConnection = new RetrofitConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


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

                Call<ModelUser> userLoginCall = retrofitConnection.server.executeLogin(map);

                //콜백
                userLoginCall.enqueue(new Callback<ModelUser>() {
                    @Override
                    public void onResponse(Call<ModelUser> userLoginCall, Response<ModelUser> loginResponse) {


                        if(loginResponse.code() == 200){
                            Log.i("결과", loginResponse.toString());
                            getUserInfo();

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

            private void getUserInfo() {
                //로그인 성공 시 사용자 정보
                Call<ModelUser> getUserInfoCall = retrofitConnection.server.getUserInfo();
                // 콜백2
                getUserInfoCall.enqueue(new Callback<ModelUser>() {
                    @Override
                    public void onResponse(Call<ModelUser> getUserInfoCall, Response<ModelUser> userInfoResponse) {

                        Log.i("결과", userInfoResponse.toString());
                        Log.i("켜짐2", "ㅏㅇ아");
                        if(userInfoResponse.code() == 200) {
                            //USER = userInfoResponse.body();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(StartActivity.this);
                            builder1.setTitle(USER.getUserName());
                            builder1.setMessage(USER.getEmail());
                            builder1.show();
                        } else if(userInfoResponse.code() == 400){
                            // TODO 메세지 출력
                            Toast.makeText(StartActivity.this, "블라블라", Toast.LENGTH_SHORT).show();
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
                Call<Void> call = retrofitConnection.server.executeSignup(map);

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
}