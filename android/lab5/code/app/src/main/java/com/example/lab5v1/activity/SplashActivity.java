package com.example.lab5v1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab5v1.R;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private static int TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        makeFullScreen();
        setAnimation();
        moveToMainActivity(TIME_OUT);


    }

    private void moveToMainActivity(int TIME_OUT) {
        new Handler().postDelayed(() -> {
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            startActivity(i);
            finish();
        }, TIME_OUT);
    }

    public void makeFullScreen(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void setAnimation(){
        earthRotate();
        tittleFlip();
    }

    private void tittleFlip() {
        TextView textView = findViewById(R.id.text_splash);
        Animation grow = AnimationUtils.loadAnimation(this, R.anim.growl);
        textView.startAnimation(grow);
        Animation shrink = AnimationUtils.loadAnimation(this, R.anim.shrink);
        textView.startAnimation(shrink);


    }

    private void earthRotate() {

        ImageView imageView = findViewById(R.id.img_splash);
        Animation rotate =  AnimationUtils.loadAnimation(
                this,R.anim.rotate);
        imageView.setAnimation(rotate);
    }



}
