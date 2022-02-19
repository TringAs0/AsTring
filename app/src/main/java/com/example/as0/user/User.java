package com.example.as0.user;

import java.util.List;

public class User
{
    private static User instance = new User();
    private String phoneNumber;
    private String name;
    private String gender;
    private String nickName;

    private String self_Introduction;
    private List<String> tripDestination;
    private List<String> tripStyle;

    private User () {}

    public static User getInstance() { return instance; }

    public void setPhoneNumber(String phoneNumber) { instance.phoneNumber = phoneNumber; }
    public void setName(String name) { instance.name = name; }
    public void setGender(String gender) { instance.gender = gender; }
    public void setNickName(String nickName) { instance.nickName = nickName; }
    public void setSelf_Introduction(String self_Introduction) { instance.self_Introduction = self_Introduction; }
    public void setTripDestination(List<String> tripDestination) { instance.tripDestination = tripDestination; }
    public void setTripStyle(List<String> tripStyle) { instance.tripStyle = tripStyle; }

    public String getPhoneNumber()
    {
        return instance.phoneNumber;
    }

    public String getName()
    {
        return instance.name;
    }

    public String getGender()
    {
        return instance.gender;
    }

    public String getNickName()
    {
        return instance.nickName;
    }

    public String getSelf_Introduction()
    {
        return instance.self_Introduction;
    }

    public List<String> getTripDestination()
    {
        return instance.tripDestination;
    }

    public List<String> getTripStyle() {
        return instance.tripStyle;
    }
}
