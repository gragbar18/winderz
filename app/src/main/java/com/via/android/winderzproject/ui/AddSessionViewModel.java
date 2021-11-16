package com.via.android.winderzproject.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.via.android.winderzproject.data.CurrentUserRepository;
import com.via.android.winderzproject.data.Session;
import com.via.android.winderzproject.data.SessionRepository;

import java.util.ArrayList;
import java.util.List;

public class AddSessionViewModel extends AndroidViewModel {
    private final CurrentUserRepository currentUserRepository;
    private final SessionRepository sessionRepository;

    public AddSessionViewModel(@NonNull Application application) {
        super(application);
        currentUserRepository = CurrentUserRepository.getInstance(application);
        sessionRepository = SessionRepository.getInstance();
    }

    public void init(){
        String userId = currentUserRepository.getCurrentUser().getValue().getUid();
        sessionRepository.init(userId);
    }

    public void addSession(Session session) {
        List<Session> sessions = sessionRepository.getSessions().getValue();
        if(sessions == null){
            sessions = new ArrayList<>();
        }
        sessions.add(session);
        sessionRepository.saveSessions(sessions);
    }
}
