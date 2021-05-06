package com.example.dartsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{

    GoogleSignInClient mGoogleSignInClient;
    Button signOutButton;
    Button newGameButton;
    Button accountButton;
    String name;
    String email;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        //Configure a listener on the signout button
        signOutButton = (Button) findViewById(R.id.SignoutButton);
        signOutButton.setOnClickListener(this);

        newGameButton = (Button) findViewById(R.id.NewGameButton);
        newGameButton.setOnClickListener(this);

        accountButton = (Button) findViewById(R.id.AccountScreenButton);
        accountButton.setOnClickListener(this);

        //Update greeting text according to the intent given
        TextView greeting = (TextView) findViewById(R.id.WelcomeUser);
        Bundle intent = getIntent().getExtras();
        name = acct.getDisplayName();
        email = acct.getEmail();
        ID = acct.getId();

        String welcome = "Welcome " + name;
        greeting.setText(welcome);

        //Configure GSC for signout
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.SignoutButton) {
            signOut();
        }
        else if(v.getId() == R.id.NewGameButton){
            startNewGame();
        }
        else if(v.getId() == R.id.AccountScreenButton){
            goToAccountScreen();
        }
    }

    //logs the google account out and returns to the starting screen
    private void signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, new OnCompleteListener<Void>
                    () {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    goToLoginScreen();
                }
            });
    }

    private void goToLoginScreen(){
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    private void startNewGame(){
        Intent intent = new Intent(this, InitializingGame.class);
        startActivity(intent);
    }

    private void goToAccountScreen(){
        Intent intent = new Intent(this, AccountScreen.class);
        startActivity(intent);
    }
}