package com.example.android.muhta;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by adirez18 on 12/01/2016.
 */

//Singleton
public class MuhtaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        FacebookSdk.sdkInitialize(this);
        ParseFacebookUtils.initialize(getApplicationContext());

    }
}
