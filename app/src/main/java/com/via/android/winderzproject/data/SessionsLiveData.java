package com.via.android.winderzproject.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SessionsLiveData extends LiveData<List<Session>> {
    List<Session> tmp = new ArrayList<>();

    private final ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Session addedSession = snapshot.getValue(Session.class);
            addedSession.setKey(snapshot.getKey());
            tmp.add(addedSession);
            setValue(tmp);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Session deletedSession = snapshot.getValue(Session.class);
            deletedSession.setKey(snapshot.getKey());
            tmp.remove(deletedSession);
            setValue(tmp);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    DatabaseReference databaseReference;

    public SessionsLiveData(DatabaseReference ref) {
        databaseReference = ref;
    }

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

}
