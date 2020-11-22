package kr.ac.konkuk.cookiehouse.General;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/api/user/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

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

    @POST("/api/user/register")
    Call<Void> executeSignup(@Body HashMap<String, String> map);

}
