package com.newprojrct.lr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();

        CountDownTimer c=new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startApp();

            }
        }.start();
    }

    private void startApp(){

        if (user !=null){

            startActivity(new Intent(SplashScreen.this,MainActivity.class));
        }else {
            startActivity(new Intent(SplashScreen.this,Registration.class));
        }

        finish();


    }
}