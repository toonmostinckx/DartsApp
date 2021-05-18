package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Results extends AppCompatActivity {
    private int numberOfPlayers;

    private TextView firstPlayerName;
    private TextView secondPlayerName;
    private TextView thirdPlayerName;
    private TextView fourthPlayerName;
    private ArrayList<TextView> rankingPlayersTextView;
    private ArrayList<String> rankingOfPlayersNames;

    private TextView numberOfThrowsPlayer1;
    private TextView numberOfThrowsPlayer2;
    private TextView numberOfThrowsPlayer3;
    private TextView numberOfThrowsPlayer4;
    private ArrayList<TextView> numberOfThrowsTextView;
    private ArrayList<Integer> numberOfThrowsInteger;

    private TextView highestScorePlayer1;
    private TextView highestScorePlayer2;
    private TextView highestScorePlayer3;
    private TextView highestScorePlayer4;
    private ArrayList<TextView> highestScoreAllPlayersTextView;
    private ArrayList<Integer> highestScoreAllPlayersInteger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle extras = getIntent().getExtras();

        rankingPlayersTextView = new ArrayList<>();
        firstPlayerName = (TextView) findViewById(R.id.firstPlayerName);
        secondPlayerName = (TextView) findViewById(R.id.secondPlayerName);
        thirdPlayerName = (TextView) findViewById(R.id.thirdPlayerName);
        fourthPlayerName = (TextView) findViewById(R.id.fourthPlayerName);
        rankingPlayersTextView.add(firstPlayerName);
        rankingPlayersTextView.add(secondPlayerName);
        rankingPlayersTextView.add(thirdPlayerName);
        rankingPlayersTextView.add(fourthPlayerName);
        rankingOfPlayersNames = new ArrayList<>();
        rankingOfPlayersNames = extras.getStringArrayList("ranking");
        numberOfPlayers = extras.getInt("numberOfPlayers");

        setTextViewsVisible(rankingPlayersTextView, rankingOfPlayersNames);

        numberOfThrowsTextView = new ArrayList<>();
        numberOfThrowsPlayer1 = (TextView) findViewById(R.id.numberOfThrowsPlayer1);
        numberOfThrowsPlayer2 = (TextView) findViewById(R.id.numberOfThrowsPlayer2);
        numberOfThrowsPlayer3 = (TextView) findViewById(R.id.numberOfThrowsPlayer3);
        numberOfThrowsPlayer4 = (TextView) findViewById(R.id.numberOfThrowsPlayer4);
        numberOfThrowsTextView.add(numberOfThrowsPlayer1);
        numberOfThrowsTextView.add(numberOfThrowsPlayer2);
        numberOfThrowsTextView.add(numberOfThrowsPlayer3);
        numberOfThrowsTextView.add(numberOfThrowsPlayer4);
        numberOfThrowsInteger = extras.getIntegerArrayList("numberOfThrowsOfAllPlayers");

        setTextViewsVisible(numberOfThrowsTextView, changeArrayListIntegerToArrayListString(numberOfThrowsInteger));

        highestScoreAllPlayersTextView = new ArrayList<>();
        highestScorePlayer1 = (TextView) findViewById(R.id.highestScorePlayer1);
        highestScorePlayer2 = (TextView) findViewById(R.id.highestScorePlayer2);
        highestScorePlayer3 = (TextView) findViewById(R.id.highestScorePlayer3);
        highestScorePlayer4 = (TextView) findViewById(R.id.highestScorePlayer4);
        highestScoreAllPlayersTextView.add(highestScorePlayer1);
        highestScoreAllPlayersTextView.add(highestScorePlayer2);
        highestScoreAllPlayersTextView.add(highestScorePlayer3);
        highestScoreAllPlayersTextView.add(highestScorePlayer4);
        highestScoreAllPlayersInteger = extras.getIntegerArrayList("highestScoreInOneThrowOFAllPlayers");

        setTextViewsVisible(highestScoreAllPlayersTextView, changeArrayListIntegerToArrayListString(highestScoreAllPlayersInteger));
    }

    private ArrayList<String> changeArrayListIntegerToArrayListString(ArrayList<Integer> arrayListInteger){
        ArrayList<String> arrayListString = new ArrayList<>();
        int i = 0;
        for(Integer numberOfThrows: arrayListInteger){
            i++;
            if(i ==  numberOfPlayers) {
                arrayListString.add("not finished");
            }
            else{
                arrayListString.add(numberOfThrows.toString());
            }
        }
        return arrayListString;
    }
    
    private void setTextViewsVisible(ArrayList<TextView> textViewArrayList, ArrayList<String> listWithText){
        for(int i = 0; i < numberOfPlayers; i++){
            textViewArrayList.get(i).setVisibility(TextView.VISIBLE);
            textViewArrayList.get(i).setText(listWithText.get(i));
        }
    }

    public void clickedOnBtnStartNewGame(View caller){
        Intent newGameIntent = new Intent (this, InitializingGame.class);
        startActivity(newGameIntent);
    }

//    public void clickedOnBtnMenu(View caller){ aan gijs vragen hoe daqhboard starten
//        Intent menuIntent = new Intent(this, Dashboard.class);
//        startActivity(menuIntent);
//    }
}