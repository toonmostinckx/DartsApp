package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class GameScreen extends AppCompatActivity {

    private TextView nameOfPlayer1;
    private TextView nameOfPlayer2;
    private TextView nameOfPlayer3;
    private TextView nameOfPlayer4;
    private TextView scorePlayer1;
    private TextView scorePlayer2;
    private TextView scorePlayer3;
    private TextView scorePlayer4;
    private ArrayList<TextView> pointsOfPlayers;
   // private ArrayList<TextView> playersNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        pointsOfPlayers = new ArrayList<>();
        nameOfPlayer1 = (TextView) findViewById((R.id.namePlayer1));
//        playersNames.add(nameOfPlayer1);
        nameOfPlayer2 = (TextView) findViewById((R.id.namePlayer2));
//        playersNames.add(nameOfPlayer2);
        nameOfPlayer3 = (TextView) findViewById((R.id.namePlayer3));
//        playersNames.add(nameOfPlayer3);
       nameOfPlayer4 = (TextView) findViewById((R.id.namePlayer4));
//        //playersNames.add(nameOfPlayer4);
        Bundle extras = getIntent().getExtras();
        setNameOfPlayer(nameOfPlayer1, extras, "nameOfPlayer1");
        setNameOfPlayer(nameOfPlayer2, extras, "nameOfPlayer2");
        setNameOfPlayer(nameOfPlayer3, extras, "nameOfPlayer3");
        setNameOfPlayer(nameOfPlayer4, extras, "nameOfPlayer4");

        scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);
        //setBeginStartingPointsOfPlayer(scorePlayer1, extras);
        scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);
        scorePlayer3 = (TextView) findViewById(R.id.scorePlayer3);
        scorePlayer4 = (TextView) findViewById(R.id.scorePlayer4);

        pointsOfPlayers.add(scorePlayer1);
        pointsOfPlayers.add(scorePlayer2);
        pointsOfPlayers.add(scorePlayer3);
        pointsOfPlayers.add(scorePlayer4);
        setNamesOfPlayersVisible(extras);
    }

    private void setNamesOfPlayersVisible(Bundle extras){
        for(int i = 0; i < extras.getInt("numberOfPlayers"); i++){
            pointsOfPlayers.get(i).setVisibility(View.VISIBLE);
            setBeginStartingPointsOfPlayer(pointsOfPlayers.get(i), extras);
        }
    }
    private void setBeginStartingPointsOfPlayer(TextView scoreOfPlayer, Bundle extras){
        String startingPoints = extras.get("startingPoints").toString();
        scoreOfPlayer.setText(startingPoints);
    }

    private void setNameOfPlayer(TextView nameOfPlayer, Bundle extras, String idForExtras) {
        String name = extras.get(idForExtras).toString();
        if (name != null) {
            nameOfPlayer.setText(name);
        }
        else {
            nameOfPlayer.setVisibility(View.GONE);
        }
    }
}