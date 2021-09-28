package com.mostafabor3e.mychat.Activity.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mostafabor3e.mychat.Activity.Adapter.AdapterPost;
import com.mostafabor3e.mychat.Activity.Adapter.Asynk;
import com.mostafabor3e.mychat.Activity.ui.CommentDetilse;
import com.mostafabor3e.mychat.R;
import com.mostafabor3e.mychat.model.Poast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {
RecyclerView recyclerView;
AdapterPost adapterPost;
List<Poast>posts;
DatabaseReference reference;

   HomeViewModel homeViewModel;
Asynk asynk;
List<Poast> asynkList;




      public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        posts=new ArrayList<>();
        asynk =new Asynk();
        asynk.execute();
        asynkList=new ArrayList<>();

        homeViewModel= ViewModelProviders.of(this).get(HomeViewModel.class);
        recyclerView=root.findViewById(R.id.rc_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        reference= FirebaseDatabase.getInstance().getReference("posts");


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel.postslist.observe(getViewLifecycleOwner(),
                new Observer<List<Poast>>() {


                    @Override
                    public void onChanged(final  List<Poast> poasts) {
                        Toast.makeText(getContext(), "cc"+poasts.get(2).getTitle(), Toast.LENGTH_SHORT).show();

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



                });
        try {
            asynkList= asynk.get();
        //   Toast.makeText(getContext(), "pod"+posts.get(1).getTitle(), Toast.LENGTH_SHORT).show();


        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.e("mvv",e.getMessage()+"");

        }

    }

}