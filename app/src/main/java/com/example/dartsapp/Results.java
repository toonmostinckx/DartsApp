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

        setRankingVisible();
    }
    
    private String getNameInRanking(int indexOfPlayerInRanking){
        return rankingOfPlayersNames.get(indexOfPlayerInRanking);
    }
    
    private void setRankingVisible(){
        for(int i = 0; i < numberOfPlayers; i++){
            rankingPlayersTextView.get(i).setVisibility(TextView.VISIBLE);
            rankingPlayersTextView.get(i).setText(getNameInRanking(i));
        }
    }
}