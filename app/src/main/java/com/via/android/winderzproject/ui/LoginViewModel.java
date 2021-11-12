package com.via.android.winderzproject.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.via.android.winderzproject.data.CurrentUserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final CurrentUserRepository currentUserRepository;

    public LoginViewModel(Application app){
        super(app);
        currentUserRepository = CurrentUserRepository.getInstance(app);
    }

    public LiveData<FirebaseUser> getCurrentUser(){ return currentUserRepository.getCurrentUser();}

}
