package com.example.android.muhta;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by adirez18 on 12/01/2016.
 */


public class MuhtaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        FacebookSdk.sdkInitialize(this);
        ParseFacebookUtils.initialize(getApplicationContext());

    }
}
