package com.example.android.muhta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.LoginButton;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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


        loginFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> permissions = Arrays.asList("public_profile", "user_about_me", "email", "user_photos");

                if (!ParseFacebookUtils.isLinked(user)) {
                    ParseFacebookUtils.linkWithReadPermissionsInBackground(user, FacebookLogin.this, permissions, new SaveCallback() {
                        @Override
                        public void done(ParseException ex) {
                            if (ParseFacebookUtils.isLinked(user)) {
                                Log.d("MyApp", "Woohoo, user logged in with Facebook!");
                                //Toast.makeText(getApplicationContext(), "You are fucking here", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

                final GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Insert your code here
                               // JSONObject jsonObject = new JSONObject(request);
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,about,photos,email");
                request.setParameters(parameters);
                request.executeAsync();


            }

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        startActivity(new Intent(FacebookLogin.this, TutorialActivity.class));
        finish();
    }
}
