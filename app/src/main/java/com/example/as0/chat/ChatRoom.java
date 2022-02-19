package com.example.as0.chat;

import com.example.as0.user.User;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom
{
    private String counterPart_NickName;
    private String counterPart_Uid;
    private String counterPart_PhotoUrl;


    public ChatRoom(String counterPart_NickName, String counterPart_Uid)
    {
        this.counterPart_NickName=counterPart_NickName;
        this.counterPart_Uid=counterPart_Uid;
    }

    public void setCounterPart_NickName(String counterPart_NickName) {
        this.counterPart_NickName = counterPart_NickName;
    }

    public void setCounterPart_Uid(String counterPart_Uid) {
        this.counterPart_Uid = counterPart_Uid;
    }

    public void setCounterPart_PhotoUrl(String counterPart_PhotoUrl) {
        this.counterPart_PhotoUrl = counterPart_PhotoUrl;
    }


    public String getCounterPart_NickName() {
        return counterPart_NickName;
    }

    public String getCounterPart_Uid() {
        return counterPart_Uid;
    }

    public String getCounterPart_PhotoUrl() {
        return counterPart_PhotoUrl;
    }
}
