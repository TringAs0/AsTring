package com.example.as0.chat;

import com.example.as0.R;
import com.example.as0.chat.adapter.ChatRoomAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomListActivity extends AppCompatActivity implements ChatRoomAdapter.RoomClickListener
{

    private Context context;

    private RecyclerView recyclerView;
    private ChatRoomAdapter adapter;

    private List<ChatRoom> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);
        context=this;

        list = new ArrayList<>();
        list.add(new ChatRoom("0","0"));
        list.add(new ChatRoom("1","1"));
        list.add(new ChatRoom("2","2"));
        list.add(new ChatRoom("3","3"));

        recyclerView=findViewById(R.id.chatroomlistactivity_recycler_chatroomlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ChatRoomAdapter(context, list, this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRoomClick(int position)
    {
        context.startActivity(new Intent(context, ChatActivity.class).putExtra("counterpart", list.get(position).getCounterPart_NickName()));
    }
}