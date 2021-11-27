package com.via.android.winderzproject.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.via.android.winderzproject.data.CurrentUserRepository;
import com.via.android.winderzproject.data.Session;
import com.via.android.winderzproject.data.SessionRepository;

public class DetailViewModel extends AndroidViewModel {
    private final CurrentUserRepository currentUserRepository;
    private final SessionRepository sessionRepository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        currentUserRepository = CurrentUserRepository.getInstance(application);
        sessionRepository = SessionRepository.getInstance();
    }

    public void init() {
        String currentUserId = currentUserRepository.getCurrentUser().getValue().getUid();
        sessionRepository.init(currentUserId);
    }

    //Get current session of the application
    public Session getCurrentSession() {
        return sessionRepository.getCurrentSession();
    }

    //Save current session of the application
    public void saveCurrentSession(Session session) {
        sessionRepository.saveCurrentSession(session);
    }

    //Update favorite attribute of wanted session
    public void updateFavoriteSession(String key, Boolean isChecked) {
        sessionRepository.updateFavoriteSession(key, isChecked);
    }

    //Delete session in the database
    public void deleteSession(String key) {
        sessionRepository.deleteSession(key);
    }

}
