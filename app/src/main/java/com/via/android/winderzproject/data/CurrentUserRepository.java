package com.via.android.winderzproject.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CurrentUserRepository {
    private final CurrentUserLiveData currentUser;
    private final Application app;
    private static  CurrentUserRepository instance;

    private CurrentUserRepository(Application app){
        this.app = app;
        currentUser = new CurrentUserLiveData();
    }

    public static synchronized CurrentUserRepository getInstance(Application app){
        if(instance == null)
            instance = new CurrentUserRepository(app);
        return instance;
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return currentUser;
    }

    public void signOut(){
        AuthUI.getInstance()
                .signOut(app.getApplicationContext());
    }
}
