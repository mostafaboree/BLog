package com.mostafabor3e.mychat.Activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mostafabor3e.mychat.Activity.ui.slideshow.Login;
import com.mostafabor3e.mychat.R;

public class Start extends AppCompatActivity {
Button login;
Button Register;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
Intent intent=new Intent(getBaseContext(), Home.class);
startActivity(intent);
finish();
        }
        login=findViewById(R.id.bt_start_login);
        Register=findViewById(R.id.bt_start_register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), com.mostafabor3e.mychat.Activity.ui.Register.class);
                startActivity(intent);
            }
        });
    }
}