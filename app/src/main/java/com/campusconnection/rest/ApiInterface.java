package com.campusconnection.rest;

import com.campusconnection.model.requests.RemoveRequest;
import com.campusconnection.model.responses.GenericResponse;
import com.campusconnection.model.requests.LoginRequest;
import com.campusconnection.model.responses.ImageUploadResponse;
import com.campusconnection.model.responses.MemberListResponse;
import com.campusconnection.model.responses.MemberResponse;
import com.campusconnection.model.requests.RegisterRequest;
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

    @POST("api/search")
    Call<MemberListResponse> createSearch(@Body SearchRequest search);

    @GET("status/{code}")
    Call<GenericResponse> checkStatus(@Path("code") String code);

    @GET("api/members/list")
    Call<MemberListResponse> getMembers(@Query("offset") int offset);

    @GET("api/profile/{id}")
    Call<MemberResponse> getProfile(@Path("id") int id);

    @POST("api/swipe")
    Call<GenericResponse> addSwipe(@Body SwipeRequest swipe);

    @PUT("api/profile")
    Call<GenericResponse> updateProfile(@Body MemberResponse updatedMember);

    @GET("api/settings")
    Call<SettingsBody> getSettings();

    @POST("api/settings")
    Call<GenericResponse> postSettings(@Body SettingsBody settings);

    @Multipart
    @POST("api/upload")
    Call<ImageUploadResponse> uploadPicture(@Part("isDefault") RequestBody isDefault, @Part MultipartBody.Part file);

    @HTTP(method = "DELETE", path = "api/upload", hasBody = true)
    Call<GenericResponse> deletePicture(@Body RemoveRequest picture);

    @HTTP(method = "DELETE", path = "api/remove", hasBody = true)
    Call<GenericResponse> remove(@Body RemoveRequest item);

}
