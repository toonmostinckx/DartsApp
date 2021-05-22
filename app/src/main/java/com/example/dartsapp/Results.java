package com.example.dartsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private RequestQueue requestQueue;
    private TextView txtResponse;
    private String[] nameOfAllUsersInDB;
    private String[] highestScoreOfAllUsersInDB;
    private int numberOfRequestsToDB;
    private int indexInDB;
    private String userID;

    private Button btnNewGame;

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

        numberOfRequestsToDB = 0;
        userID = extras.getString("userID");

        btnNewGame = (Button) findViewById(R.id.btnNewGame);
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

    public void getHighestScoreInDataBase()
    {
        requestQueue = Volley.newRequestQueue( this );
        String requestURL = "https://studev.groept.be/api/a20sd111/getHighestScoreOfPlayers";

        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL,
                response -> {
                    try {
                        JSONArray responseArray = new JSONArray(response);
                        String responseString = "";
                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject curObject = responseArray.getJSONObject(i);
                            responseString += curObject.getString("highestScore") + ",";
                            getNamesOfPlayersInDB();
                        }
                        Log.e("Database", "rcreateArrayListFromResponseString");
                        highestScoreOfAllUsersInDB = createArrayListFromResponseString(responseString);
                        getNamesOfPlayersInDB();
                    } catch (JSONException e) {
                        Log.e("Database", e.getMessage(), e);
                    }
                },
                error -> txtResponse.setText(error.getLocalizedMessage())
        );


        requestQueue.add(submitRequest);
    }

    private String[] createArrayListFromResponseString(String responseString)
    {
        String responseArray [] = responseString.split(",");
        return responseArray;
    }

    public void getNamesOfPlayersInDB()
    {
        requestQueue = Volley.newRequestQueue( this );

        String requestURL = "https://studev.groept.be/api/a20sd111/getAllNames";
        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL,

                response -> {
                    try {
                        JSONArray responseArray = new JSONArray(response);
                        String responseString = "";
                        for( int i = 0; i < responseArray.length(); i++ )
                        {
                            JSONObject curObject = responseArray.getJSONObject( i );
                            responseString += curObject.getString( "name" ) + ",";
                        }
                        nameOfAllUsersInDB = createArrayListFromResponseString(responseString);
                        playersInDB(rankingOfPlayersNames);
                        //txtResponse.setText(responseString);
                    }
                    catch( JSONException e )
                    {
                        Log.e( "Database", e.getMessage(), e );
                    }
                },

                error -> txtResponse.setText( error.getLocalizedMessage() )
        );

        requestQueue.add(submitRequest);
    }

    public void updateHighestScoreOfUserInDB(String userName, String highestScore)
    {
        RequestQueue requestQueues = Volley.newRequestQueue(this);;

        String requestURL = "https://studev.groept.be/api/a20sd111/updateHighestScoreOfPlayer/" + highestScore + "/" + userName + "/" + highestScore;
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                response -> {
                    try {
                        JSONObject responseJSON = response.getJSONObject(0);
                        String responseString = "";

                    }
                    catch( JSONException e )
                    {
                        Log.e( "Database", e.getMessage(), e );
                    }
                },

                error -> txtResponse.setText( error.getLocalizedMessage() )
        );

        requestQueue.add(submitRequest);
    }

    private void playersInDB(ArrayList<String> nameOFPlayersWhoGetToZeroPoints){
        for(int i = 0; i < numberOfPlayers; i++){
            if(true == playerInDB(nameOFPlayersWhoGetToZeroPoints.get(i))){
                updateHighScore(nameOFPlayersWhoGetToZeroPoints.get(i), highestScoreAllPlayersInteger.get(i));
            }
        }
    }

    private boolean playerInDB(String player){
        boolean playerInDB = false;
        indexInDB = 0;
        for(String userInDB: nameOfAllUsersInDB){
            if(userInDB.equals(player)){
                playerInDB = true;
            }
            if(playerInDB == false) {
                indexInDB++;
            }
        }
        return playerInDB;
    }

    private void updateHighScore(String name, Integer highestScoreOfPlayer){
        updateHighestScoreOfUserInDB(name, highestScoreOfPlayer.toString());
    }

    //(new Handler()).postDelayed(this::yourMethod, 5000);
    public void clickedOnBtnMenu(View caller){
        getHighestScoreInDataBase();

        Intent menuIntent = new Intent(this, Dashboard.class);
        menuIntent.putExtra("SigninType", "Manual");
        menuIntent.putExtra("ID", userID);
        startActivity(menuIntent);
    }

}