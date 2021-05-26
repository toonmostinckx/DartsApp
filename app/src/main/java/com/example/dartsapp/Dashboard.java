package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{

    GoogleSignInClient mGoogleSignInClient;

    Button signOutButton;
    Button newGameButton;
    Button accountButton;
    String name;
    String signinType;
    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Configure a listener on the signout button
        signOutButton = findViewById(R.id.SignoutButton);
        signOutButton.setOnClickListener(this);

        newGameButton = findViewById(R.id.NewGameButton);
        newGameButton.setOnClickListener(this);

        accountButton = findViewById(R.id.AccountScreenButton);
        accountButton.setOnClickListener(this);

        //handle different signin options
        Bundle intent = getIntent().getExtras();
        signinType = intent.get("SigninType").toString();
        if(signinType.equals("Google")){
            UpdateUIGoogle();
        }else if(signinType.equals("Manual")){
            updateUIManual(intent);
        }
    }

    private void updateUIManual(Bundle intent) {
        ID = intent.getString("ID");
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String requestURL = "https://studev.groept.be/api/a20sd111/getName/" + ID;

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> {
                    try {
                        JSONObject responseJSON = response.getJSONObject(0);
                        String Name = responseJSON.get("Name").toString();
                        String welcome = "Welcome " + Name;
                        TextView greeting = findViewById(R.id.WelcomeUser);
                        greeting.setText(welcome);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                Throwable::printStackTrace
        );

        requestQueue.add(submitRequest);
    }

    private void UpdateUIGoogle() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        assert acct != null;
        name = acct.getDisplayName();

        TextView greeting = findViewById(R.id.WelcomeUser);
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
        if(signinType.equals("Google")){
            mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> goToLoginScreen());
        }else if(signinType.equals("Manual")){
            goToLoginScreen();
        }

    }

    private void goToLoginScreen(){
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }

    private void startNewGame(){
        Intent intent = new Intent(this, InitializingGame.class);
        intent.putExtra("SigninType", signinType);
        if(signinType.equals("Manual")){
            intent.putExtra("userID", ID);
        }
        startActivity(intent);
    }

    private void goToAccountScreen(){
        Intent intent = new Intent(this, AccountScreen.class);
        intent.putExtra("SigninType", signinType);
        if(signinType.equals("Manual")){
            intent.putExtra("userID", ID);
        }
        startActivity(intent);
    }
}