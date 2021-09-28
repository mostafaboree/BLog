package com.mostafabor3e.mychat.Activity.ui.gallery;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.DialogCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mostafabor3e.mychat.Activity.ui.Home;
import com.mostafabor3e.mychat.Activity.ui.Register;
import com.mostafabor3e.mychat.R;
import com.mostafabor3e.mychat.databinding.FragmentGalleryBinding;
import com.mostafabor3e.mychat.model.Profile;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.mostafabor3e.mychat.R.id.imageView2;
import static com.mostafabor3e.mychat.R.id.toolbar;

public class GalleryFragment extends Fragment implements View.OnClickListener  {

FirebaseUser  firebaseUser;
DatabaseReference databaseReference;
public static final int openRequest=1;
Register register;
   Uri imag;
   ProgressBar progressBar;
   private GalleryViewModel galleryViewModel;
    FragmentGalleryBinding galleryBinding;
    String uid;



    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        galleryBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_gallery,container,false);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        uid=firebaseUser.getUid();
        galleryBinding.ivEdName.setOnClickListener(this);
        userInf();


    return galleryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        galleryBinding.ivProileUser.setOnClickListener(this);

        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setPhotoUri(imag).build();
        register=new Register();
    }

    public void openImage(){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,openRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==openRequest&&resultCode==RESULT_OK){
            if (data != null) {
                imag=data.getData();
                updataImageProfile(imag);

            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_ed_name:
             //   Toast.makeText(register, "", Toast.LENGTH_SHORT).show();
                updateUserInfo(galleryBinding.tvNameuserProfile.getText().toString(),imag,firebaseUser,
                        galleryBinding.tvProfilePhone.getText().toString(),galleryBinding.tvEmailProfile.getText().toString(),
                        galleryBinding.tvBio.getText().toString());

                break;
            case  R.id.iv_proile_user:
                openImage();


        }



    }
    public void updateUserInfo(final String name, final Uri uri, final FirebaseUser currentUser
            , final String phone , final String gmail, final String bio) {

                                    databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(uid);
                                    Map<String, Object> map=new HashMap<>();
                                    map.put("image_url",uri.toString());
                                    map.put("username",name);
                                    map.put("phone",phone);
                                    map.put("bio",bio);
                                    map.put("email",gmail);
                                    databaseReference.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(), "you uP_date your profile", Toast.LENGTH_SHORT).show();
                                            galleryBinding.prIv.setVisibility(View.INVISIBLE);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Try again "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }




    public  void updataImageProfile(final Uri uri){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Are you sure").setMessage("Update Image Profile").
                setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                galleryBinding.prIv.setVisibility(View.VISIBLE);


                StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
                final StorageReference imageFilePath = mStorage.child(uri.getLastPathSegment());
                imageFilePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    //upload image success
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(final Uri uri) {
                                updateUserInfo(galleryBinding.tvNameuserProfile.getText().toString()
                                        ,uri,firebaseUser,galleryBinding.tvProfilePhone.getText().toString()
                                        ,galleryBinding.tvEmailProfile.getText().toString(),galleryBinding.tvBio.getText().toString());

                            }
                        });
                    }
                });
            }
            });
        builder.create();
        builder.show();



    }
    public void userInf(){
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Profile user=snapshot.getValue(Profile.class);
                Profile profile=new Profile(firebaseUser.getUid(),user.getUsername(),user.getImage_url(),user.getGmail(),
                        user.getBio(),user.getPhone());
                galleryBinding.setPrfile(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}