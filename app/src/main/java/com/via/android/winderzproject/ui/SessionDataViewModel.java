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

public class SessionDataViewModel extends AndroidViewModel {
    private final CurrentUserRepository currentUserRepository;
    private final SessionRepository sessionRepository;


    public SessionDataViewModel(@NonNull Application application) {
        super(application);
        currentUserRepository = CurrentUserRepository.getInstance(application);
        sessionRepository = SessionRepository.getInstance();
    }

    public void init() {
        FirebaseUser firebaseUser = currentUserRepository.getCurrentUser().getValue();
        assert firebaseUser != null;
        String currentUserId = firebaseUser.getUid();
        sessionRepository.init(currentUserId);
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUserRepository.getCurrentUser();
    }

    public LiveData<List<Session>> getSessions() {
        return sessionRepository.getSessions();
    }

    public void deleteSession(String key) {
        sessionRepository.deleteSession(key);
    }

    public void updateFavoriteSession(String key, Boolean isChecked) {
        sessionRepository.updateFavoriteSession(key, isChecked);
    }

    public void saveCurrentSession(Session session) {
        sessionRepository.saveCurrentSession(session);
    }
}
