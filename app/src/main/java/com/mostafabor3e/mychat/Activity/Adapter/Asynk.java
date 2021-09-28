package com.mostafabor3e.mychat.Activity.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.mychat.model.Poast;

import java.util.ArrayList;
import java.util.List;

public class Asynk extends AsyncTask<Void,Void, List<Poast>> {
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
   DatabaseReference databaseReference =firebaseDatabase.getReference("posts");
     List<Poast>poastl=new ArrayList<>();


    @Override
    protected List<Poast> doInBackground(Void... voids) {

        //Observable observable=databaseReference;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // poastl.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Poast poast = data.getValue(Poast.class);
                    poastl.add(poast);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return poastl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);


    }

    @Override
    protected void onPostExecute(List<Poast> poasts) {
        super.onPostExecute(poasts);
        if (poasts == null) {
            Log.e("asyyy","empty");
            return;
        }
        else  {
            Log.e("vvvv","empty");
            poastl=poasts;


            return;
        }


    }
}
