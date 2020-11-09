package com.mostafabor3e.mychat.Activity.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.mychat.Activity.*;
import com.mostafabor3e.mychat.R;
import com.mostafabor3e.mychat.model.Poast;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
RecyclerView recyclerView;
AdapterPost adapterPost;
List<Poast>poasts;
DatabaseReference reference;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=root.findViewById(R.id.rc_home);
        poasts=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        reference= FirebaseDatabase.getInstance().getReference("posts");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren()){
                    Poast poast=snap.getValue(Poast.class);
                    poasts.add(poast);
                }
                adapterPost=new AdapterPost(getContext(), poasts, new AdapterPost.onclicklisener() {
                                    @Override
                                    public void onclick(View v, int potion) {
                                        Intent intent = new Intent(getContext(), CommentDetilse.class);
                                        Poast p = poasts.get(potion);
                                        intent.putExtra("post", p);
                                        startActivity(intent);
                                    }
                                }, new AdapterPost.onclickShare() {

                                    @Override
                                    public void onShare(View v, int potion) {
                                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                                        sendIntent.setType("image/*");
                                        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(poasts.get(potion).getPostPhoto()));
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, "<---photo blog--->.");
                                        Intent chooser=Intent.createChooser(sendIntent,"share");
                                            startActivity(chooser);



                                    }
                                });
                recyclerView.setAdapter(adapterPost);
                adapterPost.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}