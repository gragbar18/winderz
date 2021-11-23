package com.via.android.winderzproject.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.via.android.winderzproject.data.CurrentUserRepository;
import com.via.android.winderzproject.data.Session;
import com.via.android.winderzproject.data.SessionRepository;

public class UpdateSessionViewModel extends AndroidViewModel {
    private final CurrentUserRepository currentUserRepository;
    private final SessionRepository sessionRepository;

    public UpdateSessionViewModel(Application application){
        super(application);
        currentUserRepository = CurrentUserRepository.getInstance(application);
        sessionRepository = SessionRepository.getInstance();
    }

    public void init() {
        String userId = currentUserRepository.getCurrentUser().getValue().getUid();
        sessionRepository.init(userId);
    }

    public void updateSession(Session session){
        String key = session.getKey();
        sessionRepository.updateSession(key, session);
    }
}
