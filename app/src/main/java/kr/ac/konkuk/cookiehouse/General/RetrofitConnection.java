package kr.ac.konkuk.cookiehouse.General;

import kr.ac.konkuk.cookiehouse.General.RetrofitInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// 이거 혹시 몰라서 살려놨엉 찬희얌
//    //10.0.2.2 -> 에물레이터 3000은 우리 포트넘버, 애뮬레이터 아니면 실제 ip주소 10.0.2.2 대신 실제 ip주소 3000 대신 다른 포트넘버
//   private String BASE_URL ="http://10.0.2.2:3000";
//
////    private String PG_URL ="http://10.0.2.2:5000";

public class RetrofitConnection {
    public String BASE_URL = "https://sweetrail.ml";
    Retrofit retrofit =new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RetrofitInterface server = retrofit.create(RetrofitInterface.class);
}

