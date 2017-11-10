package com.campusconnection.rest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import com.campusconnection.R;
import com.campusconnection.util.PreferencesUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://campusconnection.ddns.net:8080/CCService/v1/index.php/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        //Get auth header stuff
        PreferencesUtil prefs = new PreferencesUtil(context);
        final boolean isLoggedIn = prefs.getBooleanPreference("isLoggedIn");
        final String jwt = prefs.getStringPreference("jwt");
        Log.d("D","apiclient isLoggedIn is: " +  isLoggedIn);
        Log.d("D","apiclient jwt is: " +  jwt);
        //Log requests and responses for debuggin
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor
                .setLevel(HttpLoggingInterceptor.Level.BODY)
                .setLevel(HttpLoggingInterceptor.Level.HEADERS);
       // OkHttpClient client = new OkHttpClient.Builder()
        // .addInterceptor(interceptor).build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Accept", "application/json;versions=1");
                        if (!isLoggedIn) {
                            ongoing.addHeader("Authorization", "Bearer " + jwt);
                        }
                        return chain.proceed(ongoing.build());
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
