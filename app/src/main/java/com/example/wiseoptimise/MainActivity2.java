package com.example.wiseoptimise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity2 extends AppCompatActivity {
TextView appname ;
LottieAnimationView lottie ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        appname = findViewById(R.id.logoname);
        lottie = findViewById(R.id.lottie);
        appname.animate()
                .translationYBy(-1700)  // Adjusted to move by -1400 pixels
                .setDuration(5000)
                .setStartDelay(0);
        lottie.animate()
                .translationXBy(2000)  // Adjusted to move by 2000 pixels
                .setDuration(4700)
                .setStartDelay(5000);  // Adjusted start delay to 5000 milliseconds to sync with the main dashboard delay

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        },5000);
    }
}