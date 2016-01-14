package com.example.android.muhta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.ParseUser;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class FacebookLogin extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginFB;
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();



        setContentView(R.layout.activity_facebook_login);



        user = ParseUser.getCurrentUser();
        loginFB = (LoginButton) findViewById(R.id.login_button);


        List<String> permissions = Arrays.asList("public_profile", "email");

        loginFB.setReadPermissions(permissions);

        loginFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                user.put("facebookId", object.optString("id"));
                                user.put("facebookName", object.optString("name"));
                                user.put("facebookEmail", object.optString("email"));

                                user.saveInBackground();

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

                startActivity(new Intent(FacebookLogin.this, TutorialActivity.class));
                finish();

            }

            @Override
            public void onCancel() {

                Toast.makeText(getApplicationContext(), "Facebook Login Canceled", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException e) {

                Toast.makeText(getApplicationContext(), "Facebook Login Had an Error", Toast.LENGTH_LONG).show();

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
