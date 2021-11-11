package com.via.android.winderzproject.data;

public class User {
    String username;
    String email;
    int age;

    public User() {
        //Default constructor required for calls to Datasnapshot.getValue(User.class)
    }

    public User(String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }
}
