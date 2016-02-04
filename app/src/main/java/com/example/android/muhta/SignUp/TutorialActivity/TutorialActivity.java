package com.example.android.muhta.SignUp.TutorialActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.muhta.MainActivity;
import com.example.android.muhta.R;


public class TutorialActivity extends AppCompatActivity {

    private Button nextB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


//        ImageView imageView = (ImageView) findViewById(R.id.tutImageView);
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.displayImage("http://www.download-free-wallpaper.com/img96/tsvolnstbpmvflweuyjk.jpg",imageView );

        nextB = (Button) findViewById(R.id.next_tutorial_button);
        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TutorialActivity.this, MainActivity.class));

                finish();
            }
        });

    }
}