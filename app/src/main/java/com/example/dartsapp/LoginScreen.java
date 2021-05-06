package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN =  1;
    GoogleSignInClient mGoogleSignInClient;

    private EditText emailField;
    private EditText passwordField;
    private RequestQueue requestQueue;

    Button manualSigninButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

        //Setup login and register buttons
        manualSigninButton = findViewById(R.id.LoginScreen_ManualSigninButton);
        registerButton = findViewById(R.id.LoginScreen_RegisterAccountButton);
        manualSigninButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        //setup the account info text fields
        emailField = (EditText) findViewById(R.id.Manual_Signin_Email);
        passwordField = (EditText) findViewById(R.id.Manual_Signin_Password);
    }

    @Override
    protected void onStart() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        goToDashboardGoogleSignin(account);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            googleSignIn();
        }
        if(v.getId() == R.id.LoginScreen_ManualSigninButton){
            manualSignIn();
        }
        if(v.getId() == R.id.LoginScreen_RegisterAccountButton){
            goToRegisterScreen();
        }
    }

    private void goToRegisterScreen() {
        Intent intent = new Intent(this, RegisterScreen.class);
        startActivity(intent);
    }

    public void manualSignIn() {
        // check if the account is valid and registered
        String currentEmail = emailField.getText().toString();
        String currentPassWord = passwordField.getText().toString();

        //put passhash check in here
        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("SigninType", "Manual");
        intent.putExtra("email", currentEmail);
        startActivity(intent);

        checkPassHash();
    }

    private void checkPassHash()
    {
        requestQueue = Volley.newRequestQueue( this );
        String requestURL = "https://studev.groept.be/api/a20sd111/checkPassHash";

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> {
                    try {
                        StringBuilder responseString = new StringBuilder();
                        for( int i = 0; i < response.length(); i++ )
                        {
                            JSONObject curObject = response.getJSONObject( i );
                            responseString.append(curObject.getString("email")).append(" : ").append(curObject.getString("passhash")).append("\n");
                        }
                        Log.d("Database", String.valueOf(responseString));
                    }
                    catch( JSONException e )
                    {
                        Log.e( "Database", e.getMessage(), e );
                    }
                },

                error -> {
                    //handle error lmao
                }
        );

        requestQueue.add(submitRequest);
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            goToDashboardGoogleSignin(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
            goToDashboardGoogleSignin(null);
        }
    }

    private void goToDashboardGoogleSignin(GoogleSignInAccount account) {
        if(account == null){return;}

        emailField.setText(account.getEmail());
        passwordField.setText(account.getId());

        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("SigninType", "Google");
        startActivity(intent);
    }
}