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

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener {

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

    public void registerButtonPressed() {
        errorMessageBox.setText("");

        //check if passwords match
        if (!passwordBox.getText().toString().equals(repeatPasswordBox.getText().toString())) {
            String errorMessage = "Passwords do not match";
            errorMessageBox.setText(errorMessage);

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
        checkExistingEmail(tmp);
    }


    private void checkExistingEmail(User user) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String requestURL = "https://studev.groept.be/api/a20sd111/checkExistingEmail/" + user.getEmail();

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> {
                    try {
                        JSONObject cur = response.getJSONObject(0);
                        if(cur.get("Email") != null){
                            Log.e("Register", "Register user failed");
                            String errorMessage = "Email already registered";
                            errorMessageBox.setText(errorMessage);

                            //clear passwords
                            passwordBox.setText("");
                            repeatPasswordBox.setText("");
                        }
                    } catch (JSONException e) {
                        registerUser(user);
                    }
                },
                error -> {
                    Log.e("Register", "Register user failed");
                    String errorMessage = "No connectivity to the database";
                    errorMessageBox.setText(errorMessage);

                    //clear passwords
                    passwordBox.setText("");
                    repeatPasswordBox.setText("");
                }
        );

        requestQueue.add(submitRequest);
    }

    public void registerUser(User user) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String requestURL = "https://studev.groept.be/api/a20sd111/registerUser/" + user.getName() + "/" + user.getEmail() + "/" + user.getPassHash();

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> goGetID(user),
                error -> {
                    Log.e("Register", "Register user failed");
                    String errorMessage = "Registering user failed";
                    errorMessageBox.setText(errorMessage);
                }
        );

        requestQueue.add(submitRequest);
    }

    public void goGetID(User user) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String requestURL = "https://studev.groept.be/api/a20sd111/getID/" + user.getEmail();
        Log.e("TAG", "Email: " + requestURL);
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> {
                    try {
                        JSONObject tmp = response.getJSONObject(0);
                        Log.d("IMPORTANT", tmp.get("UserID").toString());
                        user.setID(tmp.get("UserID").toString());
                        goToDashboardManual(user);
                    } catch (JSONException e) {
                        Log.e("Register", "Register user failed");
                        String errorMessage = "Getting ID failed";
                        errorMessageBox.setText(errorMessage);
                    }

                },
                error -> {
                    Log.e("Register", "Register user failed");
                    String errorMessage = "Getting ID failed";
                    errorMessageBox.setText(errorMessage);
                }
        );

        requestQueue.add(submitRequest);
    }

    public void goToDashboardManual(User user){
        String ID = user.getID();

        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("SigninType", "Manual");
        intent.putExtra("ID", ID);
        startActivity(intent);
    }
}