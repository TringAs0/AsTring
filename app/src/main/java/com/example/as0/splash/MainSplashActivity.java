package com.example.as0.splash;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as0.R;
import com.example.as0.VActivity;
import com.example.as0.chat.ChatRoomListActivity;
import com.example.as0.firebase.FireStoreAccess;
import com.example.as0.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainSplashActivity extends AppCompatActivity
{
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);

        context=this;
        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();

        //SharedPreferences sharedPreferences = getSharedPreferences("app_sharedPreferences", MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (firebaseUser == null)
                    startActivity(new Intent(MainSplashActivity.this, LoginActivity.class));
                else
                    startActivity(new Intent(MainSplashActivity.this, ChatRoomListActivity.class));

            }
        },2540);




    }
}