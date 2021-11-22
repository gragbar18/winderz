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

    /*private final ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Session addedSession = snapshot.getValue(Session.class);
            addedSession.setKey(snapshot.getKey());
            tmp = getValue();
            if (tmp == null)
                tmp = new ArrayList<>();
            tmp.add(addedSession);
            Log.d("test", tmp.toString() + " added");
            setValue(tmp);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Session updatedSession = snapshot.getValue(Session.class);
            for(Session session : tmp){
                if (session.getKey().equals(updatedSession.getKey())){
                    session = new Session(updatedSession);
                    Log.d("test", "updated");
                }
            }
            Log.d("test", tmp.toString() + " updated");
            setValue(tmp);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Session deletedSession = snapshot.getValue(Session.class);
            tmp.remove(deletedSession);
            Log.d("test", tmp.toString() + " deleted");
            setValue(tmp);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };*/

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            tmp = new ArrayList<>();
            for(DataSnapshot ds : snapshot.getChildren()){
                Session session = ds.getValue(Session.class);
                session.setKey(ds.getKey());
                tmp.add(session);
            }
            setValue(tmp);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.d("test", "Error in valueEventListener");
        }
    };

    DatabaseReference databaseReference;

    public SessionsLiveData(DatabaseReference ref) {
        databaseReference = ref;
    }

    @Override
    protected void onActive() {
        super.onActive();
        //databaseReference.addChildEventListener(listener);
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }

}
