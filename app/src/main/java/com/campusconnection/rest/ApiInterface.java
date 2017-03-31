package com.campusconnection.rest;

import com.campusconnection.model.GenericResponse;
import com.campusconnection.model.Member;
import com.campusconnection.model.MemberSession;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    Call<GenericResponse> checkLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<GenericResponse> register(@Field("email") String email);

    @POST("signup")
    Call<GenericResponse> signup(@Body Member member);

    @GET("logout")
    Call<GenericResponse> logout();

    @GET("member")
    Call<MemberSession> getSession();

    @GET("profile/{id}")
    Call<Member> getProfile(@Path("id") int id);

    @PUT("profile/{id}")
    Call<Member> updateProfile(@Path("id") int id, @Body Member member);
}
