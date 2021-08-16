package com.mostafabor3e.mychat.Activity.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.mychat.Activity.Adapter.CommentAdapter;
import com.mostafabor3e.mychat.R;
import com.mostafabor3e.mychat.model.Comment;
import com.mostafabor3e.mychat.model.Poast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentDetilse extends AppCompatActivity {
CircleImageView userPhoto,friendPhoto;
ImageView postPhoto;
TextView title,data,description;
EditText writeComment;
Button addComment;
String keyPost;
FirebaseUser firebaseUser;
RecyclerView recyclerViewComment;
CommentAdapter commentAdapter;
List<Comment>comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detilse);
        userPhoto=findViewById(R.id.iv_detlise_user);
        postPhoto=findViewById(R.id.iv_detlise_post);
        friendPhoto=findViewById(R.id.iv_frind_detilse);
        title=findViewById(R.id.tv_title_detilse);
        data=findViewById(R.id.tv_data_detilse);
        description=findViewById(R.id.tv_description_detlise);
        writeComment=findViewById(R.id.ed_comment_detlise);
        addComment=findViewById(R.id.bt_add_detlise);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        comments=new ArrayList<>();
        getSupportActionBar().hide();
getdata();


//inflate recycle;

            recyclerViewComment=findViewById(R.id.rc_comment);
            recyclerViewComment.setHasFixedSize(true);
            recyclerViewComment.setLayoutManager(new LinearLayoutManager(getBaseContext()));

 //retrive comment
 getComment();
addComment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        addComment.setVisibility(View.INVISIBLE);
        addComment();
    }
});



    }
    public void getdata(){
        Intent intent=getIntent();
        Poast poast= (Poast) intent.getSerializableExtra("post");
        assert poast != null;
        title.setText(poast.getTitle());
        description.setText(poast.getDescraption());
        Glide.with(getBaseContext()).load(poast.getPostPhoto()).into(postPhoto);
        Glide.with(getBaseContext()).load(firebaseUser.getPhotoUrl()).into(userPhoto);
        Glide.with(getBaseContext()).load(poast.getUserPhoto()).into(friendPhoto);
         long dat= (long) poast.getTimeStep();
keyPost=poast.getPostKey();
         data.setText(getData(dat));

    }

    private String getData(long time){
        Calendar calendar=Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String data= DateFormat.format("dd_MM_yyyy",calendar).toString();


return data;



    }
    private void addComment(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("comment").child(keyPost).push();
        Comment c=new Comment(firebaseUser.getUid(),firebaseUser.getDisplayName()
                ,writeComment.getText().toString(),
                firebaseUser.getPhotoUrl().toString());
        reference.setValue(c).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                addComment.setVisibility(View.VISIBLE);
                Toast.makeText(CommentDetilse.this, "success", Toast.LENGTH_SHORT).show();
                writeComment.setText("");
            }
        });
    }

    public void getComment(){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("comment").child(keyPost);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren()){
                    Comment comment=snap.getValue(Comment.class);
                    comments.add(comment);
                }
                commentAdapter=new CommentAdapter(comments,getBaseContext());
                recyclerViewComment.setAdapter(commentAdapter);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}