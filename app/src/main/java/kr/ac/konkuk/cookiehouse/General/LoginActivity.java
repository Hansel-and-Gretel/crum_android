package kr.ac.konkuk.cookiehouse.General;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import kr.ac.konkuk.cookiehouse.Home.MainActivity;
import kr.ac.konkuk.cookiehouse.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    //10.0.2.2 -> 에물레이터 3000은 우리 포트넘버, 애뮬레이터 아니면 실제 ip주소 10.0.2.2 대신 실제 ip주소 3000 대신 다른 포트넘버
    private String BASE_URL ="http://10.0.2.2:3000";

    private String SWEET_URL ="https://sweetrail.ml";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder()
                .baseUrl(SWEET_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        Button loginBtn = findViewById(R.id.btn_login);
        final EditText emailEdit= findViewById(R.id.input_email);
        final EditText passwordEdit = findViewById(R.id.input_password);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> map = new HashMap<>();

                map.put("email", emailEdit.getText().toString());
                map.put("password", passwordEdit.getText().toString());

                Call<LoginResult> call = retrofitInterface.executeLogin(map);

                //콜백
                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {

                        if(response.code() == 200){

                            LoginResult result = response.body();
                            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                            intentMain.putExtra("username",result.getName());
                            Log.d("login", "onResponse: "+ result.getName());
                            startActivity(intentMain);
                            finish();


                        }else if(response.code() ==400){
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 잘못되었거나 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    }


}
