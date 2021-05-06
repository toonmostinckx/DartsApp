package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class RegisterScreen extends AppCompatActivity implements View.OnClickListener{
    
    EditText nameBox;
    EditText emailBox;
    EditText passwordBox;
    EditText repeatPasswordBox;
    Button registerButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        
        nameBox = findViewById(R.id.RegisterScreen_NameBox);
        emailBox = findViewById(R.id.RegisterScreen_EmailBox);
        passwordBox = findViewById(R.id.RegisterScreen_PassWordBox);
        repeatPasswordBox = findViewById(R.id.RegisterScreen_RepeatPassWordBox);

        registerButton = findViewById(R.id.RegisterScreen_RegisterButton);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.RegisterScreen_RegisterButton) {
            registerAccount();
        }
    }
    
    public void registerAccount(){
        //check if email already has account linked
        if(checkExistingEmail(emailBox.getText().toString())){
            return;
        }
        
        //check if passwords match
        if(!passwordBox.getText().toString().equals(repeatPasswordBox.getText().toString())){
            return;
        }

        byte[] hashedPassword = null;

        //Hash the password
        try {
            hashedPassword = hashPassword();
        }catch (Exception e){
            Log.e("TAG", "hashingError", e);
            return;
        }

        //now register in the database
        //morgen zal dat wel duidelijk worden

        //go to dashboard with this information
        String name = nameBox.getText().toString();
        String email = emailBox.getText().toString();

        Intent intent = new Intent(this, Dashboard.class);
        intent.putExtra("SigninType", "Manual");
        intent.putExtra("Name", name);
        intent.putExtra("Email", email);
        startActivity(intent);
    }

    private byte[] hashPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        //generate Salt
        byte[] salt = generateSalt();

        KeySpec spec = new PBEKeySpec(passwordBox.getText().toString().toCharArray(), salt, 32768, 64);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    private byte[] generateSalt() {
        long seed = generateLongFromString(emailBox.getText().toString());
        Random randomGen = new Random(seed);

        byte[] salt = new byte[16];
        randomGen.nextBytes(salt);
        return salt;
    }

    private long generateLongFromString(String givenString) {
        char[] chararray = givenString.toCharArray();
        long seed = 0;
        for (char c : chararray) {
            seed += (int) c;
        }
        return seed;
    }

    private boolean checkExistingEmail(String text) {
        //just check the email
        return false;
    }
}