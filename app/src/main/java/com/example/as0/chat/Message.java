package com.example.as0.chat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 1. user A text message to B-> message object be creating
 * 2. using counterpart uid and chatroom uid in chat room, system send message to databse
 * 3. as soon as message be sent to database, database send event to user B-app
 * 4. eventlistener in user B -app catch the event and upload message from database
 *
 **/
public class Message
{
    private String uid_User;
    //private String uid_Counterpart;
    private String nickName;
    private String imageUri;
    private String contents;
    private String date;
    private String time;

    public Message(String uid_User, String nickName, String contents)
    {
        this.uid_User = uid_User;
        this.nickName = nickName;
        //this.uid_Counterpart = uid_Counterpart;
        //this.imageUri = imageUri;
        this.contents = contents;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"));
    }

    public void setUid_User(String uid_User) { this.uid_User = uid_User; }
    //public void setUid_Counterpart(String uid_Counterpart) { this.uid_Counterpart = uid_Counterpart; }
    //public void setImageUri(String imageUri) { this.imageUri = imageUri; }
    public void setNickName(String nickName) { this.nickName = nickName; }
    public void setContents(String contents) { this.contents = contents; }
    //public void setDate(String date) { this.date = date; }
    //public void setTime(String time) { this.time = time; }

    public String getUid_User() { return uid_User; }
    //public String getUid_Counterpart() { return uid_Counterpart; }
    //public String getImageUri() { return imageUri; }
    public String getNickName() { return nickName; }
    public String getContents() { return contents; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}
