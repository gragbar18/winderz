package com.via.android.winderzproject.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SessionsLiveData extends LiveData<List<Session>> {

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            List<Session> tmp = new ArrayList<>();
            for (DataSnapshot ds : snapshot.getChildren()) {
                Session session = ds.getValue(Session.class);
                if (session != null) {
                    session.setKey(ds.getKey());
                    tmp.add(session);
                }
            }
            setValue(tmp);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.d("error", "ValueEventListener in SessionsLiveData");
        }
    };

    DatabaseReference databaseReference;

    public SessionsLiveData(DatabaseReference ref) {
        databaseReference = ref;
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }

}
