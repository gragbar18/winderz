package com.via.android.winderzproject.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.via.android.winderzproject.data.CurrentUserRepository;

public class MainActivityViewModel extends AndroidViewModel {
    private final CurrentUserRepository currentUserRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        currentUserRepository = CurrentUserRepository.getInstance(application);
    }

    public LiveData<FirebaseUser> getCurrentUser(){return currentUserRepository.getCurrentUser();}
}
