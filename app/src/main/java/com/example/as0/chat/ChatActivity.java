package com.example.as0.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.as0.R;
import com.example.as0.chat.adapter.ChatMessageAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity
{
    private Context context;

    private RecyclerView recyclerView;
    private ChatMessageAdapter adapter;

    private List<Message> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        context=this;

        list=new ArrayList<>();
        list.add(new Message(FirebaseAuth.getInstance().getCurrentUser().getUid(),"a","Hi My name is john!"));
        list.add(new Message("other___","Scarlett","Hi My name is Scarlett *,*"));
        list.add(new Message(FirebaseAuth.getInstance().getCurrentUser().getUid(),"a","How are you today?"));
        list.add(new Message(FirebaseAuth.getInstance().getCurrentUser().getUid(),"a","I'm nice today"));
        list.add(new Message("other___","Scarlett","me too"));
        list.add(new Message("other___","Scarlett","Have ever been in korea?"));
        list.add(new Message("other___","Scarlett","Actually, I wanna go to korea"));
        list.add(new Message(FirebaseAuth.getInstance().getCurrentUser().getUid(),"a","not yet, but I also"));
        list.add(new Message(FirebaseAuth.getInstance().getCurrentUser().getUid(),"a","Oh, my sister living in korea"));
        list.add(new Message(FirebaseAuth.getInstance().getCurrentUser().getUid(),"a","If you ok, I will tell her about u :)"));
        list.add(new Message("other___","Scarlett","what a kind!!!"));
        list.add(new Message("other___","Scarlett","thx john"));
        list.add(new Message("other___","Scarlett","Im eating apple. do u like apple?"));


        String nickName = getIntent().getStringExtra("counterpart");

        Toast.makeText(this, nickName, Toast.LENGTH_LONG).show();

        recyclerView=findViewById(R.id.chatactivity_recyclerview_messagelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ChatMessageAdapter(context, list);
        recyclerView.setAdapter(adapter);
    }
}