package com.mostafabor3e.mychat.Activity.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mostafabor3e.mychat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {
    public static final int image_return=1;
    EditText username;
    EditText Email;
    EditText password;
    CircleImageView imageProfile;
    Button register;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
StorageTask upload;
StorageReference storageReference;
    Uri imageUri;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.et_name_reg);
        Email=findViewById(R.id.ed_reg_email);
        password=findViewById(R.id.ed_reg_password);
        imageProfile=findViewById(R.id.iv_regiset);
        firebaseAuth=FirebaseAuth.getInstance();
        register=findViewById(R.id.bt_reg_reg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String Gmail=Email.getText().toString();
                String pass=password.getText().toString();
                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(Gmail)||TextUtils.isEmpty(pass)){
                    Toast.makeText(Register.this, "please enter success data ", Toast.LENGTH_SHORT).show();
                }
                else {
                    RegisterUser(Gmail,pass,name);
                }

            }
        });
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_image();

            }
        });
    }

    private void RegisterUser(String Email, String password, final String username){
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                    String id=firebaseUser.getUid();

                  updateUserInfo(username,imageUri,firebaseUser);
                }
                else {
                  task.addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(Register.this, "con't create new user"+e.getMessage(), Toast.LENGTH_SHORT).show();

                      }
                  });
                }
            }
        });

    }
    private void open_image() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,image_return);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==image_return&&resultCode==RESULT_OK&&data!=null){

                imageUri=data.getData();
            imageProfile.setImageURI(imageUri);
        }
    }
  /*  private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }*/

   /* public  void uploadImage(Uri imageUri){
        final StorageReference storage= FirebaseStorage.getInstance().getReference("uploads");
        final UploadTask uploadTask=storage.putFile(imageUri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return storage.getDownloadUrl();
            }
        });

    }*/
    public void updateUserInfo(final String name, final Uri pickedImgUri, final FirebaseUser currentUser) {

    StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
    final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
    imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        //upload image success
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    UserProfileChangeRequest  profileChangeRequest=new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .setPhotoUri(uri).build();

                    currentUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent=new Intent(getBaseContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            });

        }
    });

    }
}