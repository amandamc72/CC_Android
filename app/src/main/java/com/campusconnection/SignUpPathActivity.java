package com.campusconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignUpPathActivity extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    private static final String PUBLIC_LOCATION = "user_location";
    // private static final String USER_BIRTHDAY = "user_birthday";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_path);
        mCallbackManager = CallbackManager.Factory.create();

        LoginButton mFacebookBtn = (LoginButton) findViewById(R.id.signupFacebookButton);
        Button mEmailBtn = (Button) findViewById(R.id.signupEmailButton);

        mFacebookBtn.setReadPermissions(Arrays.asList(PUBLIC_LOCATION));


        mEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpPathActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });


        mFacebookBtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("D","Facebook login Success");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i("Response",response.toString());
                                try {

                                    JSONObject userObject = response.getJSONObject();

                                    String firstName = userObject.getString("first_name");
                                    String lastName = userObject.getString("last_name");
                                    String gender = userObject.getString("gender");
                                    JSONObject location = userObject.getJSONObject("location");
                                    String city = location.getString("name");


                                    Log.i("Login"+ "FirstName", firstName);
                                    Log.i("Login" + "LastName", lastName);
                                    Log.i("Login" + "Gender", gender);
                                    Log.i("Login" + "city", city);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                // parameters.putString("fields", "first_name,last_name,gender,location,user_birthday,education");
                parameters.putString("fields", "first_name,last_name,gender,location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("D","Facebook login cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("D","Facebook login error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
