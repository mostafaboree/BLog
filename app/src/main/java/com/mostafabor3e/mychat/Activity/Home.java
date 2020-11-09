package com.mostafabor3e.mychat.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mostafabor3e.mychat.R;
import com.mostafabor3e.mychat.model.Poast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mostafabor3e.mychat.Activity.Register.image_return;

public class Home extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    ImageView imageProfile;
    TextView name,Email;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth firebaseAuth;
   private FirebaseUser firebaseUser;
    Dialog popAddPost ;
   private Uri imageUri;
   public static final int image_back=1;
    ImageView postPhoto,addPost;
    CircleImageView userPhoto;
    EditText title,descraption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
     //   Toast.makeText(this, Toast.LENGTH_SHORT).show();
iniPopup();
getpostPhoto();

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popAddPost.show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        updatadata();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    private void updatadata(){
        NavigationView navigationView = findViewById(R.id.nav_view);
View view=navigationView.getHeaderView(0);
        imageProfile=view.findViewById(R.id.imageView_profile);
        name=view.findViewById(R.id.tv_nav_profile);
        Email=view.findViewById(R.id.tv_nav_email);
        assert firebaseUser != null;
        name.setText(firebaseUser.getDisplayName());
Email.setText(firebaseUser.getEmail());
        Glide.with(getBaseContext()).load(firebaseUser.getPhotoUrl()).into(imageProfile);
       // Log.e("Emali",firebaseUser.getDisplayName());
//imageProfile.setImageURI(firebaseUser.getPhotoUrl());


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.nav_logout){
            Intent intent=new Intent(getBaseContext(),Start.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
    private void iniPopup() {
        popAddPost = new Dialog(this);
        popAddPost.setContentView(R.layout.pop_add_post);
        popAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
       // popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,500);
        popAddPost.getWindow().getAttributes().gravity = Gravity.TOP;

         userPhoto=popAddPost.findViewById(R.id.imageView_pop);
         postPhoto=popAddPost.findViewById(R.id.imageView_d_pop);
         addPost=popAddPost.findViewById(R.id.iv_pop_add);
         title=popAddPost.findViewById(R.id.ed_title_pop);
         descraption=popAddPost.findViewById(R.id.ed_descraption_pop);
         Glide.with(this).load(firebaseUser.getPhotoUrl()).into(userPhoto);
         addPost.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 addPost.setVisibility(View.INVISIBLE);
                 if (!TextUtils.isEmpty(title.getText())&&!TextUtils.isEmpty(descraption.getText())
                         &&!TextUtils.isEmpty(imageUri.toString())){
                     StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("blog_images");
                     final StorageReference imageFilePath = storageReference.child(imageUri.getLastPathSegment());
                     imageFilePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                         @Override
                         public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                 @Override
                                 public void onSuccess(Uri uri) {
                                     String dawnloaduri= String.valueOf(uri);
                                     Poast poast=new Poast(firebaseUser.getUid(),title.getText().toString(),
                                             descraption.getText().toString()
                                    ,firebaseUser.getPhotoUrl().toString(),
                                             dawnloaduri);
                                     creatPoast(poast);
                                     addPost.setVisibility(View.VISIBLE);

                                 }

                             });
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Toast.makeText(Home.this, "error", Toast.LENGTH_SHORT).show();
                         }
                     });

                     //creatpost
                 }

                 else {
                     Toast.makeText(Home.this, "complete your data", Toast.LENGTH_SHORT).show();
                 }
             }

         });


        }

    private void creatPoast(Poast poast) {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference("posts").push();
        poast.setPostKey(reference.getKey());
        reference.setValue(poast).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                addPost.setVisibility(View.VISIBLE);
                Toast.makeText(Home.this, "success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addPost.setVisibility(View.VISIBLE);
            }
        });
    }

    private void open_image() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,image_back);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==image_return&&resultCode==RESULT_OK&&data!=null){

            imageUri = data.getData();
           postPhoto.setImageURI(imageUri);
        }
    }
    public void getpostPhoto(){
        postPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_image();
            }
        });
    }
}