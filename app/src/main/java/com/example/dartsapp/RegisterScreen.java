package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener{
    
    EditText nameBox;
    EditText emailBox;
    EditText passwordBox;
    EditText repeatPasswordBox;
    TextView errorMessageBox;

    Button registerButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        
        nameBox = findViewById(R.id.RegisterScreen_NameBox);
        emailBox = findViewById(R.id.RegisterScreen_EmailBox);
        passwordBox = findViewById(R.id.RegisterScreen_PassWordBox);
        repeatPasswordBox = findViewById(R.id.RegisterScreen_RepeatPassWordBox);
        errorMessageBox = findViewById(R.id.RegisterScreen_ErrorMessageBox);

        registerButton = findViewById(R.id.RegisterScreen_RegisterButton);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.RegisterScreen_RegisterButton) {
            registerButtonPressed();
        }
    }
    
    public void registerButtonPressed(){
        errorMessageBox.setText("");
        //check if email already has account linked
        /*
        if(checkExistingEmail()){
            String error = "Email already registered to a user";
            errorMessageBox.setText(error);

            //clear passwords
            passwordBox.setText("");
            repeatPasswordBox.setText("");
            return;
        }
         */
        
        //check if passwords match
        if(!passwordBox.getText().toString().equals(repeatPasswordBox.getText().toString())){
            String error = "Passwords do not match";
            errorMessageBox.setText(error);

            //clear passwords
            passwordBox.setText("");
            repeatPasswordBox.setText("");
            return;
        }

        String name = nameBox.getText().toString();
        String email = emailBox.getText().toString();
        String passWord = passwordBox.getText().toString();

        //now register in the database
        User tmp = new User(name, email, passWord);
        registerUser(tmp);

        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("SigninType", "Manual");
        intent.putExtra("Name", name);
        intent.putExtra("Email", email);
        startActivity(intent);
    }

    /* Ik ben niet zeker hoe ik dit best check? Asynchroon werken is nie simpel voor dit soort dingen
    private boolean checkExistingEmail() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        boolean existing = true;

        String requestURL = "https://studev.groept.be/api/a20sd111/checkExistingEmail/" + emailBox.getText().toString();

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> {
                    try {
                        JSONObject cur = response.getJSONObject(0);
                        if(cur.get("email").equals(emailBox.getText().toString())){
                            existing = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    Log.e("Register", "Register user failed");
                }
        );

        requestQueue.add(submitRequest);
    }
    */

    public void registerUser(User user){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String requestURL = "https://studev.groept.be/api/a20sd111/registerUser/" + user.getName() + "/" + user.getEmail() + "/" + user.getPassHash();

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> {},
                error -> Log.e("Register", "Register user failed")
        );

        requestQueue.add(submitRequest);
    }
}