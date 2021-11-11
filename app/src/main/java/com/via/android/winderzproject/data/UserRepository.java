package com.via.android.winderzproject.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository {
    private  static UserRepository instance;
    private DatabaseReference myRef;
    private UserLiveData user;

    private UserRepository(){}

    public static synchronized UserRepository getInstance() {
        if(instance == null)
            instance = new UserRepository();
        return instance;
    }

    public void init(String userId){
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        user = new UserLiveData(myRef);
    }

    public void saveUser(User user) {
        myRef.setValue(user);
    }

    public UserLiveData getUser(){ return user; }
}
