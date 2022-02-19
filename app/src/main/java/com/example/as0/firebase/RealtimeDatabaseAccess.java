package com.example.as0.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.as0.R;
import com.example.as0.chat.ChatRoom;
import com.example.as0.chat.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.Set;

public class RealtimeDatabaseAccess
{
    //private SharedPreferences sharedPreferencesForChatRoom;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser firebaseUser = mAuth.getCurrentUser();

    private static FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference= firebaseDatabase.getReference();

    public static class Chat
    {
        public static void openChatRoom(Context context, String counterpart_Uid)
        {
            
            //1. create chat room uid in realtime databse
            DatabaseReference chatRoomUid_Reference=
                    databaseReference
                            .child("ChatRooms")
                            .push();

            String chatRoomUid=chatRoomUid_Reference.getKey();
            //2. add a chat room object under 'ChatRooms' path.
            chatRoomUid_Reference.
                    setValue(new ChatRoom("-",""));
            //3. add a chat room object under 'ChatRoom-ByUser' path. it helps us manage chat room by user easily
            databaseReference
                    .child("ChatRoom-ByUser")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(chatRoomUid)
                    .setValue(new ChatRoom("-",""));
            
            //4. add chat room uid to sharedpreferences to administer a chat uid easily 
            SharedPreferences sharedPreferencesForChatRoom= context.getSharedPreferences(context.getResources().getString(R.string.SP_CHATROOMLIST),context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesForChatRoom.edit();

            Set<String> chatRoomSet;
            Set<String> chatRoomSet_Temp= new HashSet<>();

            if(!sharedPreferencesForChatRoom.contains("ChatRoomList"))
            {
                Toast.makeText(context, "if",Toast.LENGTH_SHORT).show();
                chatRoomSet =  new HashSet<String>();
                chatRoomSet.add(chatRoomUid);
                editor.putStringSet("ChatRoomList",chatRoomSet);
                editor.commit();
                editor.apply();
            }
            else
            {
                Toast.makeText(context, "else",Toast.LENGTH_SHORT).show();
                chatRoomSet = sharedPreferencesForChatRoom.getStringSet("ChatRoomList",null);

                for (String item: chatRoomSet)
                    chatRoomSet_Temp.add(item);

                Log.d("here", chatRoomSet.toString());
                chatRoomSet_Temp.add(chatRoomUid);
                editor.putStringSet("ChatRoomList",chatRoomSet_Temp);
                editor.commit();
                editor.apply();
            }

            Log.d("here", chatRoomSet_Temp.toString());
        }

        private static void getChatRoomList(Context context)
        {
            //1. get chat room uid set from shared preferences
            SharedPreferences sharedPreferencesForChatRoom= context.getSharedPreferences(context.getResources().getString(R.string.SP_CHATROOMLIST),context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesForChatRoom.edit();

            Set<String> chatRoomSet = sharedPreferencesForChatRoom.getStringSet("ChatRoomList",null);
            Set<String> chatRoomSet_Temp= new HashSet<>();

            for (String item: chatRoomSet)
                chatRoomSet_Temp.add(item);

            //Log.d("here", chatRoomSet.toString());
            //2. get chat history from database with chat room uid
            for (String item: chatRoomSet_Temp)
            {
                databaseReference
                        .child("MessageHistory")
                        .child(item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DataSnapshot>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task)
                            {

                            }
                        });

            }

        }
        private static void sendMessage(Message message)
        {
            //databaseReference.child("ChatRooms").child()
        }
    }
}
