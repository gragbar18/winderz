package com.via.android.winderzproject.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SessionRepository {
    private static SessionRepository instance;
    private DatabaseReference myRef;
    private SessionsLiveData sessions;
    private Session currentSession;

    private SessionRepository() {
    }

    public static synchronized SessionRepository getInstance() {
        if (instance == null)
            instance = new SessionRepository();
        return instance;
    }

    public void init(String userId) {
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        sessions = new SessionsLiveData(myRef);
    }

    //Save sessions in database
    public void saveSessions(Session session) {
        myRef.push().setValue(session);
    }

    //Get sessions from database as LiveData
    public SessionsLiveData getSessions() {
        return sessions;
    }

    //Delete session in database using session's id
    public void deleteSession(String key) {
        myRef.child(key).removeValue();
    }

    //Update favorite attribute of wanted session
    public void updateFavoriteSession(String key, boolean isChecked) {
        myRef.child(key).child("favorite").setValue(isChecked);
    }

    //Update wanted sessions with a dictionnary containing the wanted attributes
    public void updateSession(String key, Session session) {
        myRef.child(key).setValue(session.toMap());
    }

    //Save a session as current session to use it in different activities/fragments
    public void saveCurrentSession(Session session) {
        currentSession = new Session(session);
    }

    //Return current session
    public Session getCurrentSession() {
        return currentSession;
    }
}