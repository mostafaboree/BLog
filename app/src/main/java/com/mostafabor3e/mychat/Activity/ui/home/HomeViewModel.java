package com.mostafabor3e.mychat.Activity.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.mychat.model.Poast;

import java.util.ArrayList;
import java.util.List;


public class HomeViewModel extends ViewModel {

     MutableLiveData<List<Poast>> postslist=new MutableLiveData<>();
    MutableLiveData<String> err=new MutableLiveData<>();

    List<Poast>poasts=new ArrayList<>();
    DatabaseReference databaseReference;

    public HomeViewModel() {

        databaseReference= FirebaseDatabase.getInstance().getReference("posts");

        //Observable observable=databaseReference;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                poasts.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Poast poast=dataSnapshot.getValue(Poast.class);
                    poasts.add(poast);
                }
                postslist.setValue(poasts);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
err.setValue(error.getMessage());
            }
        });



    }
    public LiveData<List<Poast>>getposts(){
        return postslist;
    }





}