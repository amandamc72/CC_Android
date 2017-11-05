package com.campusconnection.rest;

import com.campusconnection.model.requests.DeletePictureRequest;
import com.campusconnection.model.responses.GenericResponse;
import com.campusconnection.model.requests.LoginRequest;
import com.campusconnection.model.responses.MemberListResponse;
import com.campusconnection.model.responses.MemberResponse;
import com.campusconnection.model.requests.RegisterRequest;
import com.campusconnection.model.requests.RemoveRequest;
import com.campusconnection.model.requests.SearchRequest;
import com.campusconnection.model.responses.SettingsBody;
import com.campusconnection.model.requests.SignupRequest;
import com.campusconnection.model.requests.SwipeRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
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

    @POST("search")
    Call<MemberListResponse> createSearch(@Body SearchRequest search);

    @GET("status/{code}")
    Call<GenericResponse> checkStatus(@Path("code") String code);

    @GET("members/list")
    Call<MemberListResponse> getMembers(@Query("offset") int offset);

    @GET("profile/{id}")
    Call<MemberResponse> getProfile(@Path("id") int id);

    @POST("swipe")
    Call<GenericResponse> addSwipe(@Body SwipeRequest swipe);

    @PUT("profile/{id}")
    Call<GenericResponse> updateProfile(@Path("id") int id, @Body MemberResponse updatedMember);

    @GET("settings/{id}")
    Call<SettingsBody> getSettings(@Path("id") int id);

    @POST("settings/{id}")
    Call<GenericResponse> postSettings(@Path("id") int id, @Body SettingsBody settings);

    @Multipart
    @POST("upload")
    Call<GenericResponse> uploadPicture(@Part("isDefault") RequestBody isDefault, @Part MultipartBody.Part file);

    @HTTP(method = "DELETE", path = "upload", hasBody = true)
    Call<GenericResponse> deletePicture(@Body DeletePictureRequest picture);

    @HTTP(method = "DELETE", path = "remove", hasBody = true)
    Call<GenericResponse> remove(@Body RemoveRequest item);

}
