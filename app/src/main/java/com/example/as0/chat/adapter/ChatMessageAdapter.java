package com.example.as0.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.as0.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as0.chat.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter
{
    private static final int VIEW_TYPE_ME = 0 ;
    private static final int VIEW_TYPE_COUNTERPART = 1 ;

    private FirebaseUser firebaseUser;
    private Context context;
    private List<Message> list;

    public ChatMessageAdapter(Context context, List<Message> list)
    {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        this.context=context;
        this.list=list;
    }

    @Override
    public int getItemViewType(int position)
    {
        Message message = (Message)list.get(position);

        if (message.getUid_User().equals(firebaseUser.getUid()))
        {
            return VIEW_TYPE_ME;
        }
        else
        {
            return VIEW_TYPE_COUNTERPART;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view;

        if (viewType== VIEW_TYPE_ME)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_me_chat,parent,false);

            return new MyMessageHolder(view);
        }
        else if (viewType == VIEW_TYPE_COUNTERPART)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_counterpart_chat,parent,false);

            return new CounterPartMessageHolder(view);

        }
        else return new MyMessageHolder(null);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        Message message = (Message)list.get(position);

        switch (holder.getItemViewType())
        {
            case VIEW_TYPE_ME:
                ((MyMessageHolder)holder).bind(message);
                break;
            case VIEW_TYPE_COUNTERPART:
                ((CounterPartMessageHolder)holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    private class MyMessageHolder extends RecyclerView.ViewHolder
    {
        TextView textView_Message;
        TextView textView_Time;

        public MyMessageHolder(@NonNull View itemView)
        {
            super(itemView);

            textView_Message=itemView.findViewById(R.id.message_me_text);
            textView_Time=itemView.findViewById(R.id.message_me_time);
        }

        void bind (Message message)
        {
            textView_Message.setText(message.getContents());
            textView_Time.setText(message.getTime());
        }
    }

    private class CounterPartMessageHolder extends RecyclerView.ViewHolder
    {
        TextView textView_Message;
        TextView textView_Time;
        TextView textView_NickName;
        ImageView imageView_ProfileImage;

        public CounterPartMessageHolder(@NonNull View itemView)
        {
            super(itemView);

            textView_Message=itemView.findViewById(R.id.message_counterpart_text);
            textView_Time=itemView.findViewById(R.id.message_counterpart_time);
            textView_NickName=itemView.findViewById(R.id.message_counterpart_nickname);
            imageView_ProfileImage=itemView.findViewById(R.id.message_counterpart_profileimage);
        }

        void bind(Message message)
        {
            textView_Message.setText(message.getContents());
            textView_Time.setText(message.getTime());
            textView_NickName.setText(message.getNickName());
        }
    }
}
