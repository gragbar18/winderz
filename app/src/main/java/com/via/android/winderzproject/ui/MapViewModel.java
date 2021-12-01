package com.via.android.winderzproject.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.via.android.winderzproject.data.CurrentUserRepository;
import com.via.android.winderzproject.data.Session;
import com.via.android.winderzproject.data.SessionRepository;

import java.util.List;

public class MapViewModel extends AndroidViewModel {

    private final CurrentUserRepository currentUserRepository;
    private final SessionRepository sessionRepository;


    public MapViewModel(@NonNull Application application) {
        super(application);
        currentUserRepository = CurrentUserRepository.getInstance(application);
        sessionRepository = SessionRepository.getInstance();
    }

    public void init() {
        String currentUserId = currentUserRepository.getCurrentUser().getValue().getUid();
        sessionRepository.init(currentUserId);
    }

    public LiveData<List<Session>> getSessions() {
        return sessionRepository.getSessions();
    }

}
