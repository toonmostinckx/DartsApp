package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountScreen extends AppCompatActivity implements View.OnClickListener{
    TextView nameBox;
    TextView emailBox;
    TextView hsBox;

    ImageView profilePicture;

    String signinType;
    String ID;

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_screen);
        nameBox = findViewById(R.id.AccountScreen_NameBox);
        emailBox = findViewById(R.id.AccountScreen_EmailBox);
        hsBox = findViewById(R.id.accountScreen_HighestScoreBox);
        profilePicture = findViewById(R.id.AccountScreen_ProfilePicture);

        backButton = findViewById(R.id.AccountScreen_BackButton);
        backButton.setOnClickListener(this);

        Bundle intent = getIntent().getExtras();
        signinType = intent.get("SigninType").toString();
        if(signinType.equals("Google")){
            UpdateUIGoogle();
        }

        if(signinType.equals("Manual")){
            UpdateUIManual(intent);
        }
    }

    void UpdateUIGoogle(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null) {
            String name = acct.getDisplayName();
            String email = acct.getEmail();
            Uri ProfilePictureURI = acct.getPhotoUrl();

            if(name != null){
                String nameBoxText = "Username: " + name;
                nameBox.setText(nameBoxText);
            }
            if(email != null){
                String emailBoxText = "Email: " + email;
                emailBox.setText(emailBoxText);
            }
            if(ProfilePictureURI != null){
                Picasso.get().load(ProfilePictureURI.toString()).into(profilePicture);
            }

            String hsBoxText = "Make an account to view your highscore";
            hsBox.setText(hsBoxText);
        }
    }

    void UpdateUIManual(Bundle intent){
        ID = intent.get("ID").toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String requestURL = "https://studev.groept.be/api/a20sd111/getUserInfoFromID/" + ID;

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> {
                    try {
                        JSONObject responseJSON = response.getJSONObject(0);
                        String Name = responseJSON.get("Name").toString();
                        String Email = responseJSON.get("Email").toString();
                        String highestScore = responseJSON.get("highestScore").toString();

                        String nameBoxText = "Username: " + Name;
                        nameBox.setText(nameBoxText);

                        String emailBoxText = "Email: " + Email;
                        emailBox.setText(emailBoxText);

                        if(highestScore.equals("null")){
                            highestScore = " No games played yet";
                        }
                        String hsBoxText = "Highscore: " + highestScore;
                        hsBox.setText(hsBoxText);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                ,
                Throwable::printStackTrace
        );

        requestQueue.add(submitRequest);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.AccountScreen_BackButton){
            toDashboard();
        }
    }

    void toDashboard(){
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("SigninType", signinType);
        if(signinType.equals("Manual")){
            intent.putExtra("ID", ID);
        }
        startActivity(intent);
    }
}