package com.via.android.winderzproject.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class SessionsLiveData extends LiveData<List<Session>> {

    List<Session> tmpSessions = new ArrayList<>();

    private final ChildEventListener listener = new ChildEventListener() {

        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            /*Session session = snapshot.getValue(Session.class);
            tmpSessions.add(session);
            setValue(tmpSessions);*/

            GetSessions(snapshot);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            GetSessions(snapshot);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            GetSessions(snapshot);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            GetSessions(snapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    DatabaseReference databaseReference;

    public SessionsLiveData(DatabaseReference ref) { databaseReference = ref;}

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addChildEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }

    private void GetSessions(@NonNull DataSnapshot snapshot) {
        tmpSessions.clear();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            Session session = dataSnapshot.getValue(Session.class);
            tmpSessions.add(session);
        }
        setValue(tmpSessions);
    }

}
