package com.mostafabor3e.mychat.Activity.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mostafabor3e.mychat.Activity.*;
import com.mostafabor3e.mychat.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment {
CircleImageView imageuser;
TextView name,Email;
FirebaseUser  firebaseUser;
public static final int openRequest=1;
Uri imag;
    private GalleryViewModel galleryViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        imageuser=root.findViewById(R.id.iv_proile_user);
        name=root.findViewById(R.id.tv_nameuser_profile);
        Email=root.findViewById(R.id.tv_Email_profile);

    return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name.setText(firebaseUser.getDisplayName());
        Email.setText(firebaseUser.getEmail());
        Glide.with(this).load(firebaseUser.getPhotoUrl()).into(imageuser);
        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setPhotoUri(imag).build();
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
             imag=data.getData();
        }
    }
}