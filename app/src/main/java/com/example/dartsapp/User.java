package com.example.dartsapp;

import android.util.Log;
import org.apache.commons.codec.binary.Hex;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User{
    private final String email;
    private final String password;
    private String passHash;
    private String ID;

    public User(String email, String password){
        this.email = email;
        this.password = password;

        try {
            this.passHash = this.hashPassword();
        }catch(Exception e){
            Log.e("Hashing", "Hashing Error", e);
        }
    }

    //methods

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
