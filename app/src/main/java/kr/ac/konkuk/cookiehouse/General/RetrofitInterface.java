package kr.ac.konkuk.cookiehouse.General;

import com.google.android.gms.common.internal.HideFirstParty;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.ac.konkuk.cookiehouse.models.ModelJourney;
import kr.ac.konkuk.cookiehouse.models.ModelUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
/*
 * Call<객체타입>
 *
 * @GET("/login")
 * 이렇게 하는법도 잇음
 Call<LoginResult> executeLogin(@Query("email") String email,@Query("pw") String pw) ;
 * *

 Get 방식일때 @Query 와 @QueryMap 을 사용한다.
 Post 방식일때 @Field 와 @FieldMap 을 사용한다.
 Interceptor 개념은 OkHttpClient 라이브러리가 적용된 프로젝트에만 국한된 개념이다.
 Interceptor 는 크게 Applicaion 과 Network 로 나눌수 있다.
  */

public interface RetrofitInterface {


    @POST("/api/user/login")
    Call<ModelUser> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/user/register")
    Call<Void> executeSignup(@Body HashMap<String, String> map);

//    @POST("/api/journey/upload")
//    Call<ModelJourney> createJourney(@Body JSONObject newJourney);

    @FormUrlEncoded
    @POST("/api/journey/upload")
    Call<ModelJourney> createJourney(
            @Field("journeyName") String name,
            @Field("type") String type,
            @Field("accompany") String party,
            @Field("pinFrequency") int frequency,
            @Field("status") boolean status,
            @Field("sharedFlag") boolean shared,
            @Field("userId") int userId,
            @Field("userName") String userName);

    @GET("/api/journey/main")
    Call<List<ModelJourney>> getJourney();


}
