package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.EditText;

import java.util.ArrayList;

public class Initializing extends AppCompatActivity {
    private Button btnPlus;
    private Button btnMinus;
    private TextView numberOfPlayers;
    private CheckBox gameMode_501;
    private CheckBox gameMode_301;
    private int startingPoints;
    private int players;
    private Button btnSubmit;
    private EditText player1;
    private EditText player2;
    private EditText player3;
    private EditText player4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initializng_game);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        numberOfPlayers = (TextView) findViewById((R.id.numbersOfPlayers));
        gameMode_501 =(CheckBox) findViewById(R.id.gameMode_501);
        gameMode_301 = (CheckBox) findViewById((R.id.gameMode_301));
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        player1 = (EditText) findViewById(R.id.txtPlayer1);
        player2 = (EditText) findViewById(R.id.txtPlayer2);
        player3 = (EditText) findViewById(R.id.txtPlayer3);
        player4 = (EditText) findViewById(R.id.txtPlayer4);
    }

    public int getNumberOfPlayers() {
        return Integer.parseInt(numberOfPlayers.getText().toString());
    }

    public void enableOneGameMode(View caller){
        if(gameMode_501.isChecked() == true){
            gameMode_301.setEnabled(false);
        }
        else{
            gameMode_301.setEnabled(true);
        }

        if(gameMode_301.isChecked() == true){
            gameMode_501.setEnabled(false);
        }
        else{
            gameMode_501.setEnabled(true);
        }
    }

    public int getStartingPoints(){
        if(gameMode_501.isChecked() == true) {
            return 501;
        }


        if(gameMode_301.isChecked() == true){
            return 301;
        }
        return -1;
    }

    public void onBtnPlus_Clicked(View caller){
        int players = Integer.parseInt(numberOfPlayers.getText().toString()) + 1;
        numberOfPlayers.setText(Integer.toString(players));
        btnMinus.setEnabled(true);
    }

    public void onBtnMinus_Clicked(View caller){
        int players = Integer.parseInt(numberOfPlayers.getText().toString()) - 1;
        if(players <= 1){
            btnMinus.setEnabled(false);
            numberOfPlayers.setText(Integer.toString(players));
        }
        else {
            numberOfPlayers.setText(Integer.toString(players));
        }
    }

    public void onBtnSubmit_Clicked(View caller){
        players = Integer.parseInt(numberOfPlayers.getText().toString());
        startingPoints = getStartingPoints();
        Intent intent = new Intent(this, GameScreen.class);
        intent.putExtra("numberOfPlayers", getNumberOfPlayers());
        intent.putExtra("startingPoints", getStartingPoints());
        intent.putExtra("nameOfPlayer1", player1.getText().toString());
        intent.putExtra("nameOfPlayer2", player2.getText().toString());
        intent.putExtra("nameOfPlayer3", player3.getText().toString());
        intent.putExtra("nameOfPlayer4", player4.getText().toString());
        startActivity(intent);
    }
}