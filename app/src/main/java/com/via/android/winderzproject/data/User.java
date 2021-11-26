package com.via.android.winderzproject.data;

//Object not used yet but can be integrated in the future to store more data on the user
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
