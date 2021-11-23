package com.via.android.winderzproject.data;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionRepository {
    private static SessionRepository instance;
    private DatabaseReference myRef;
    private SessionsLiveData sessions;
    private Session currentSession;

    private SessionRepository(){

    }

    public static synchronized SessionRepository getInstance(){
        if(instance == null)
            instance = new SessionRepository();
        return instance;
    }

    public void init(String userId){
        myRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
        sessions = new SessionsLiveData(myRef);
    }

    public void saveSessions(Session session) {
        myRef.push().setValue(session);
    }

    public SessionsLiveData getSessions(){
        return sessions;
    }

    public void deleteSession(String key) {
        myRef.child(key).removeValue();
    }

    public void updateFavoriteSession(String key , boolean isChecked) {myRef.child(key).child("favorite").setValue(isChecked); }

    public void updateSession(String key, Session session){
        myRef.child(key).setValue(session.toMap());
    }

    public void saveCurrentSession(Session session){
        currentSession = new Session(session);
    }

    public Session getCurrentSession(){
        return currentSession;
    }
}