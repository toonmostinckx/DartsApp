package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.net.URL;
import java.net.URLConnection;

public class AccountScreen extends AppCompatActivity implements View.OnClickListener{
    TextView nameBox;
    TextView emailBox;
    TextView IDBox;
    ImageView profilePicture;

    Button backButton;
    private Target mTarget;   //declare variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_screen);

        nameBox = findViewById(R.id.AccountScreen_NameBox);
        emailBox = findViewById(R.id.AccountScreen_EmailBox);
        IDBox = findViewById(R.id.AccountScreen_IDBox);
        profilePicture = findViewById(R.id.AccountScreen_ProfilePicture);

        backButton = findViewById(R.id.AccountScreen_BackButton);
        backButton.setOnClickListener(this);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null) {
            String name = acct.getDisplayName();
            String email = acct.getEmail();
            String ID = acct.getId();
            Uri ProfilePictureURI = acct.getPhotoUrl();
            if(name != null){
                String nameBoxText = "Name: " + name;
                nameBox.setText(nameBoxText);
            }
            if(email != null){
                String emailBoxText = "Email: " + email;
                emailBox.setText(emailBoxText);
            }
            if(ID != null){
                String IDBoxText = "Google ID: " + ID;
                IDBox.setText(IDBoxText);
            }
            if(ProfilePictureURI != null){
                Picasso.get().load(ProfilePictureURI.toString()).into(profilePicture);
            }
        }
    }

    void toDashboard(){
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.AccountScreen_BackButton){
            toDashboard();
        }
    }
}