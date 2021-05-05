package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class AccountScreen extends AppCompatActivity {
    TextView nameBox;
    TextView emailBox;
    TextView IDBox;
    ImageView profilePicture;
    private Target mTarget;   //declare variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_screen);

        nameBox = (TextView) findViewById(R.id.AccountScreen_NameBox);
        emailBox = (TextView) findViewById(R.id.AccountScreen_EmailBox);
        IDBox = (TextView) findViewById(R.id.AccountScreen_IDBox);
        profilePicture = (ImageView) findViewById(R.id.AccountScreen_ProfilePicture);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null) {
            String name = acct.getDisplayName();
            String email = acct.getEmail();
            String ID = acct.getId();
            Uri ProfilePictureURI = acct.getPhotoUrl();

            assert ProfilePictureURI != null;
            nameBox.setText(name);
            emailBox.setText(email);
            IDBox.setText(ID);

            Picasso.get().load(ProfilePictureURI.toString()).into(profilePicture);
        }
    }
}