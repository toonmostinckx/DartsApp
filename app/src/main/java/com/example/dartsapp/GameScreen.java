package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class GameScreen extends AppCompatActivity {
    private TextView scorePlayer1;
    private TextView scorePlayer2;
    private TextView scorePlayer3;
    private TextView scorePlayer4;

    private TextView nameFirstPlayer;
    private TextView nameSecondPlayer;
    private TextView nameThirdPlayer;
    private TextView nameFourthPlayer;
    private Bundle extras;
    private ArrayList<TextView> nameOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        nameOfPlayers = new ArrayList<>();
        extras = getIntent().getExtras();
        scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);
        scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);
        scorePlayer3 = (TextView) findViewById(R.id.scorePlayer3);
        scorePlayer4 = (TextView) findViewById(R.id.scorePlayer4);
        setStartingPoints(extras);

        nameFirstPlayer = (TextView) findViewById(R.id.nameFirstPlayer);
        nameOfPlayers.add(nameFirstPlayer);
        nameSecondPlayer = (TextView) findViewById(R.id.nameSecondPlayer);
        nameOfPlayers.add(nameSecondPlayer);
        nameThirdPlayer = (TextView) findViewById(R.id.nameThirdPlayer);
        nameOfPlayers.add(nameThirdPlayer);
        nameFourthPlayer = (TextView) findViewById(R.id.nameFourthPlayer);
        nameOfPlayers.add(nameFourthPlayer);

        setPlayersVisible();
    }

    private void setStartingPoints(Bundle extras){
        scorePlayer1.setText(extras.get("startingPoints").toString());
        scorePlayer2.setText(extras.get("startingPoints").toString());
        scorePlayer3.setText(extras.get("startingPoints").toString());
        scorePlayer4.setText(extras.get("startingPoints").toString());
    }

    private void setPlayersVisible(){
        for(int i = 0; i < Integer.parseInt(extras.get("numberOfPlayers").toString()); i++){
            TextView nameOfPlayer = nameOfPlayers.get(i);
            nameOfPlayer.setVisibility(View.VISIBLE);
        }
    }
}