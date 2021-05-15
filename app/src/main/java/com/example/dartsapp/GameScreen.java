package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class GameScreen extends AppCompatActivity {

    private TextView nameOfPlayer1;
    private TextView nameOfPlayer2;
    private TextView nameOfPlayer3;
    private TextView nameOfPlayer4;
    private ArrayList<TextView> playersNames;

    private TextView scorePlayer1;
    private TextView scorePlayer2;
    private TextView scorePlayer3;
    private TextView scorePlayer4;
    private ArrayList<TextView> pointsOfPlayers;

    private Spinner points1;
    private Spinner points2;
    private Spinner points3;
    private Spinner multiplyFactor1;
    private Spinner multiplyFactor2;
    private Spinner multiplyFactor3;
    private Button throwCompleted;
    private int player = 0;
    private int numberOfPlayers;
    private int numberOfThrows= 0;
    private ArrayList<String> ranking;

    private ArrayList<Integer> numberOfThrowsOfAllPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        pointsOfPlayers = new ArrayList<>();
        ranking = new ArrayList<>();
        playersNames = new ArrayList<>();
        numberOfThrowsOfAllPlayers = new ArrayList<>();

        nameOfPlayer1 = (TextView) findViewById((R.id.namePlayer1));
        nameOfPlayer1.setBackgroundColor(0xFF00FF00);
        nameOfPlayer2 = (TextView) findViewById((R.id.namePlayer2));;
        nameOfPlayer3 = (TextView) findViewById((R.id.namePlayer3));
        nameOfPlayer4 = (TextView) findViewById((R.id.namePlayer4));
        playersNames.add(nameOfPlayer1);
        playersNames.add(nameOfPlayer2);
        playersNames.add(nameOfPlayer3);
        playersNames.add(nameOfPlayer4);

        Bundle extras = getIntent().getExtras();

        setNameOfPlayer(nameOfPlayer1, extras, "nameOfPlayer1");
        setNameOfPlayer(nameOfPlayer2, extras, "nameOfPlayer2");
        setNameOfPlayer(nameOfPlayer3, extras, "nameOfPlayer3");
        setNameOfPlayer(nameOfPlayer4, extras, "nameOfPlayer4");

        scorePlayer1 = (TextView) findViewById(R.id.scorePlayer1);
        scorePlayer2 = (TextView) findViewById(R.id.scorePlayer2);
        scorePlayer3 = (TextView) findViewById(R.id.scorePlayer3);
        scorePlayer4 = (TextView) findViewById(R.id.scorePlayer4);
        pointsOfPlayers.add(scorePlayer1);
        pointsOfPlayers.add(scorePlayer2);
        pointsOfPlayers.add(scorePlayer3);
        pointsOfPlayers.add(scorePlayer4);
        setNamesOfPlayersVisible(extras);

        points1 = (Spinner) findViewById(R.id.points1);
        points2 = (Spinner) findViewById(R.id.points2);
        points3 = (Spinner) findViewById(R.id.points3);

        multiplyFactor1 = (Spinner) findViewById((R.id.multiplyFactor1));
        multiplyFactor2 = (Spinner) findViewById(R.id.multiplyFactor2);
        multiplyFactor3 = (Spinner) findViewById(R.id.multiplyFactor3);

        throwCompleted = (Button) findViewById(R.id.btnThrowCompleted);

        numberOfPlayers = getNumberOfPlayers(extras);
    }

    private void addNumberOfThrowsOfAllPlayers(int numberOfThrows){
        numberOfThrowsOfAllPlayers.add(numberOfThrows);
    }
    private void addPlayerToRanking(TextView player){
        boolean playerInList = false;
        for(String playerName: ranking){
            if(playerName == player.getText().toString()){
                playerInList = true;
            }
        }
        if(playerInList == false){
            ranking.add(player.getText().toString());
            addNumberOfThrowsOfAllPlayers(numberOfThrows);
        }
    }

    private int getNumberOfPlayers(Bundle extras){
        return extras.getInt("numberOfPlayers");
    }

    private void setGreenBackGroundToPlayersName(int playerWhoNeedsToThrow){
        playersNames.get(playerWhoNeedsToThrow).setBackgroundColor(0xFF00FF00);
    }

    private void deleteBackGroundColor(int playerWhoHasThrown){
        playersNames.get(player).setBackgroundColor(0x00000000);
    }

    private void setBackGroundColorForNextPlayer(int playerWhoNeedsToThrow){
        if(playerWhoNeedsToThrow >= numberOfPlayers){
            setGreenBackGroundToPlayersName(0);
        }
        else{
            setGreenBackGroundToPlayersName(playerWhoNeedsToThrow);
        }
    }

    public void clickedOnBtnThrowCompleted(View caller){
        numberOfThrows++;
        if(player >= numberOfPlayers){
            player = 0;
            deleteBackGroundColor(player);
            setPoints(pointsOfPlayers.get(player));
            player++;
            setGreenBackGroundToPlayersName(player);
        }
        else{
            deleteBackGroundColor(player);
            setPoints(pointsOfPlayers.get(player));
            player++;
            setBackGroundColorForNextPlayer(player);
        }
    }

    private void setPoints(TextView scorePlayer){
        Integer score = calculatePoints(scorePlayer);
        scorePlayer.setText(score.toString());
    }

    private int calculatePoints(TextView scorePlayer){
        int pointsBeforeThrow = Integer.parseInt(scorePlayer.getText().toString());
        int pointsOfThrows = getPointsOfThrow();
        int pointsAfterThrow = pointsBeforeThrow - pointsOfThrows;
        if(pointsAfterThrow < 0){
            return pointsBeforeThrow;
        }
        if(pointsAfterThrow == 0){
            addPlayerToRanking(playersNames.get(player));
            checkForTheEndOfTheGame();
            return 0;
        }
        else {
            return pointsAfterThrow;
        }
    }

    private int getPointsOfThrow(){
        int pointsThrow1 = Integer.parseInt(points1.getSelectedItem().toString()) * (multiplyFactor1.getSelectedItemPosition() + 1);
        int pointsThrow2 = Integer.parseInt(points2.getSelectedItem().toString()) * (multiplyFactor2.getSelectedItemPosition() + 1);
        int pointsThrow3 = Integer.parseInt(points3.getSelectedItem().toString()) * (multiplyFactor3.getSelectedItemPosition() + 1);
        return (pointsThrow1 + pointsThrow2 +pointsThrow3);
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

    private void addLastPlayerInRanking(){
        for(TextView playersNameView: playersNames){
            if(playerInRanking(playersNameView.getText().toString()) == false){
                ranking.add(playersNameView.getText().toString());
            }
        }
    }

    private boolean playerInRanking(String playersNameVieW){
        boolean playerInRanking = false;
        for(String nameInRanking: ranking){
            if(nameInRanking == playersNameVieW){
                playerInRanking = true;
            }
        }
        return playerInRanking;
    }

    private void checkForTheEndOfTheGame(){
        if(ranking.size() == numberOfPlayers - 1){
            addLastPlayerInRanking();
            Intent intentGameScreen = new Intent(this, Results.class);
            intentGameScreen.putExtra("ranking", ranking);
            intentGameScreen.putExtra("numberOfThrowsOfAllPlayers", numberOfThrowsOfAllPlayers);
            startActivity(intentGameScreen);
        }
    }
}