package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private ArrayList<Integer> numberOfThrowsString;
    
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
        numberOfPlayers = rankingOfPlayersNames.size();

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
        numberOfThrowsString = extras.getIntegerArrayList("numberOfThrowsOfAllPlayers");

        //setTextViewsVisible(numberOfThrowsTextView, numberOfThrowsString);
    }

    
    private void setTextViewsVisible(ArrayList<TextView> textViewArrayList, ArrayList<String> listWithText){
        for(int i = 0; i < numberOfPlayers; i++){
            textViewArrayList.get(i).setVisibility(TextView.VISIBLE);
            textViewArrayList.get(i).setText(listWithText.get(i));
        }
    }
}