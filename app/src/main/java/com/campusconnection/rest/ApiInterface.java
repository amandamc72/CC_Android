package com.campusconnection.rest;

import com.campusconnection.model.GenericResponse;
import com.campusconnection.model.LoginRequest;
import com.campusconnection.model.MemberListResponse;
import com.campusconnection.model.MemberResponse;
import com.campusconnection.model.MemberSessionResponse;
import com.campusconnection.model.RegisterRequest;
import com.campusconnection.model.SignupRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("login")
    Call<GenericResponse> checkLogin(@Body LoginRequest login);

    @POST("register")
    Call<GenericResponse> register(@Body RegisterRequest register);

    @POST("signup")
    Call<GenericResponse> signup(@Body SignupRequest signup);

    @GET("status/{code}")
    Call<GenericResponse> checkStatus(@Path("code") String code);

    @GET("members/list")
    Call<MemberListResponse> getMembers(@Query("offset") int offset);

//    @GET("logout")
//    Call<GenericResponse> logout();
//
//    @GET("member")
//    Call<MemberSessionResponse> getSession();

    @GET("profile/{id}")
    Call<MemberResponse> getProfile(@Path("id") int id);

//    @PUT("profile/{id}")
//    Call<MemberResponse> updateProfile(@Path("id") int id, @Body MemberResponse memberResponse);
}
