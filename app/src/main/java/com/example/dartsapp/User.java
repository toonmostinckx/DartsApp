package com.example.dartsapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User implements Parcelable{
    private String name;
    private String email;
    private String password;
    private String passHash;
    private String ID;

    public User (String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;

        try {
            this.passHash = this.hashPassword();
        }catch(Exception e){
            Log.e("Hashing", "Hashing Error", e);
        }
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;

        try {
            this.passHash = this.hashPassword();
        }catch(Exception e){
            Log.e("Hashing", "Hashing Error", e);
        }
    }

    //----------------NETWORKING---------------
    //Pull User info from the database

    public User(String ID){
        this.ID = ID;
        RequestQueue requestQueue = Volley.newRequestQueue(App.getContext());

        String requestURL = "https://studev.groept.be/api/a20sd111/getUserInfoFromID/" + ID;

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> {
                    try {
                        JSONObject content = response.getJSONObject(0);

                        name = content.get("Name").toString();
                        email = content.get("Email").toString();
                        passHash = content.get("PassHash").toString();
                        updateInfo(name, email, passHash);
                        Log.e("TAG", "HI");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace
        );

        requestQueue.add(submitRequest);
    }

    private void updateInfo(String name, String email, String passHash) {
        this.name = name;
        this.email = email;
        this.passHash = passHash;
        Log.e("TAG", "LMAO hi");
    }

    //Create user from Parsable
    public User(Parcel in){
        this.ID = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.passHash = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.passHash);
    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {return new User(in);}
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    //methods

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

    public String getEmail(){
        return email;
    }

    public String getPassHash(){
        return passHash;
    }

    //-----------HASHING----------
    public String hashPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        //generate Salt
        byte[] salt = generateSalt();

        //generate bytearray of the hashed password
        KeySpec spec = new PBEKeySpec(this.password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        //return string from bytearray using Apache commontools Hex
        return Hex.encodeHexString(hash);
    }


    private byte[] generateSalt() {
        //generates seed for PRNG from the users email
        long seed = generateLongFromString(this.email);
        Random randomGen = new Random(seed);

        //fills salt array with random bytes
        byte[] salt = new byte[16];
        randomGen.nextBytes(salt);
        return salt;
    }

    private long generateLongFromString(String givenString) {
        //generates long from the users emails ASCII codes
        char[] chararray = givenString.toCharArray();
        long seed = 0;
        for (char c : chararray) {
            seed += (int) c;
        }
        return seed;
    }
}
