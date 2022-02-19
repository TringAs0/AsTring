package com.example.as0.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as0.R;
import com.example.as0.chat.ChatActivity;
import com.example.as0.chat.ChatRoom;
import com.example.as0.user.User;

import java.util.List;

/**
 * case 1)
 * 1. A like B
 * 2. B also like A
 * 3. as soon as B like A,
 * 4. create chat room (A and B) on each chat room list
 * case 2)
 * 1. A like B
 * 2. A use a cash item to open
 * 3. open chat room (A and B) on each chat room list

 so how does data base work by stage?
 case 1)
 1. A like B -> B's Uid will be added to A liked-user list in FireStore
 2. service message will be sent to B
 3. B also like A -> A's Uid will be added to B liked-user list in FireStore
 4. as soon as above 3, chat room(A and B) will be created on each chat room list in Firebase realtime database

 case 2)
 1. A open a chat room with cash item -> B will be added to A's cash item use list
 2. as soon as added, chat room(A and B) will be created on each chat room list in Firebase realtime database
 **/
public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>
{
    private Context context;
    private List<ChatRoom> list;

    private RoomClickListener roomClickListener;

    public interface RoomClickListener
    {
        void onRoomClick(int position);
    }

    public ChatRoomAdapter(Context context,List<ChatRoom> list, RoomClickListener roomClickListener)
    {
        this.context=context;
        this.list=list;
        this.roomClickListener=roomClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_room,parent,false);

        return new ViewHolder(view, roomClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.getTextView_CounterpartNickName().setText(list.get(position).getCounterPart_NickName());
        holder.getTextView_LatestMessage().setText(list.get(position).getCounterPart_Uid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView imageView_ProfileImage;
        private TextView textView_CounterpartNickName;
        private TextView textView_LatestMessage;

        private RoomClickListener roomClickListener_ViewHolder;

        public ViewHolder(@NonNull View itemView, RoomClickListener roomClickListener_ViewHolder)
        {
            super(itemView);

            imageView_ProfileImage=itemView.findViewById(R.id.item_chat_room_counterpart_profile_image);
            textView_CounterpartNickName=itemView.findViewById(R.id.item_chat_room_counterpart_nickname);
            textView_LatestMessage=itemView.findViewById(R.id.item_chat_room_latest_message);


            this.roomClickListener_ViewHolder=roomClickListener_ViewHolder;
            itemView.setOnClickListener(this);
        }

        public ImageView getImageView_ProfileImage() { return imageView_ProfileImage; }
        public TextView getTextView_CounterpartNickName() { return textView_CounterpartNickName; }
        public TextView getTextView_LatestMessage() { return textView_LatestMessage; }

        @Override
        public void onClick(View v)
        {
            roomClickListener_ViewHolder.onRoomClick(getAdapterPosition());
        }
    }
}
