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

public class InitializingGame extends AppCompatActivity {
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
    private ArrayList<EditText> playersName;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initializing_game);
        Bundle extras = getIntent().getExtras();
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnMinus.setEnabled(false);
        numberOfPlayers = (TextView) findViewById((R.id.numbersOfPlayers));
        gameMode_501 =(CheckBox) findViewById(R.id.gameMode_501);
        gameMode_301 = (CheckBox) findViewById((R.id.gameMode_301));
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        playersName = new ArrayList<>();
        player1 = (EditText) findViewById(R.id.txtPlayer1);
        player2 = (EditText) findViewById(R.id.txtPlayer2);
        player3 = (EditText) findViewById(R.id.txtPlayer3);
        player4 = (EditText) findViewById(R.id.txtPlayer4);
        playersName.add(player1);
        playersName.add(player2);
        playersName.add(player3);
        playersName.add(player4);

        userID = extras.getString("userID");
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
        playersName.get(players - 1).setVisibility(View.VISIBLE);
        if(players == 4){
            btnPlus.setEnabled(false);
        }
    }

    public void onBtnMinus_Clicked(View caller){
        int players = Integer.parseInt(numberOfPlayers.getText().toString()) - 1;
        if(players == 2){
            btnMinus.setEnabled(false);
            btnPlus.setEnabled(true);
            numberOfPlayers.setText(Integer.toString(players));
            playersName.get(2).setVisibility(View.GONE);
        }
        else {
            numberOfPlayers.setText(Integer.toString(players));
            playersName.get(players).setVisibility(View.GONE);
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
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}