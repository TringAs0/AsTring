package com.example.as0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.as0.chat.ChatRoom;
import com.example.as0.firebase.FireStoreAccess;
import com.example.as0.firebase.RealtimeDatabaseAccess;
import com.example.as0.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VActivity extends AppCompatActivity
{

    private Context context;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v);
        context= this;

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();

        textView=findViewById(R.id.textview);
        textView.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()+" :getCurrentUser");


        FireStoreAccess.Match.getUserMatchingUidList(context,"tripRegion","tripTag");
        //RealtimeDatabaseAccess.Chat.openChatRoom(context,FirebaseAuth.getInstance().getCurrentUser()+"_____");




    }

    @Override
    protected void onStart()
    {
        super.onStart();
        SharedPreferences sharedPreferencesForMatching=
                context.getSharedPreferences(context.getResources().getString(R.string.SP_MATCHING_ALGO_UID_LIST),context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesForMatching.edit();

        Log.d("here", sharedPreferencesForMatching.getStringSet("MatchingAlgoUidList", null).toString());

    }
}