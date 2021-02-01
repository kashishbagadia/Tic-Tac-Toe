package com.example.tictactoegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true; //starting the game with player 1's turn

    private int roundCount;// keeping a track of chances

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.tv_p1);
        textViewPlayer2 = findViewById(R.id.tv_p2);

        //Receiving the button ID's dynamically in this nested loop which is further assigned to the array buttons
        for(int i =0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID,"id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.reset_button);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals(""))
        {
            return;
        }
        if (player1Turn){
            ((Button)view).setText("X");
        }
        else{
            ((Button)view).setText("O");
        }

        roundCount++;

        if(checkForWin()){
            if(player1Turn){
                player1Wins();
            }
            else{
                player2Wins();
            }
        }
        else if(roundCount == 9){
            draw();
        }
        else{
            player1Turn = !player1Turn;
        }

    }

    //Going through the array/boxes and applying the conditions to check if any player has won
    private boolean checkForWin(){
        String[][] box = new String[3][3];

        for(int i = 0; i<3; i++){
            for(int j = 0; j<3;j ++){
                box[i][j]= buttons[i][j].getText().toString();
            }
        }

        //checking for the rows
        for(int i = 0; i<3; i++){
            if((box[i][0].equals(box[i][1])) && (box[i][0].equals(box[i][2])) && !box[i][0].equals(""))
            {
                return true;
            }
        }

        //checking for the columns
        for(int i = 0; i<3; i++) {
            if ((box[0][i].equals(box[1][i])) && (box[0][i].equals(box[2][i])) && !box[0][i].equals("")) {
                return true;
            }
        }

        //checking for the diagnols
        if((box[0][0].equals(box[1][1])) && (box[0][0].equals(box[2][2])) && !box[0][0].equals(""))
        {
            return true;
        }
        if((box[0][2].equals(box[1][1])) && (box[0][2].equals(box[2][0])) && !box[0][2].equals(""))
        {
            return true;
        }

        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Player 1 wins the game!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Player 2 wins the game!", Toast.LENGTH_SHORT).show();
        updatePoints();
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "No one wins, It's a draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePoints(){
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard(){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePoints();
        resetBoard();
    }
    // keeping the data in the variables as it is even if the rotation of the device is changed
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundcount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}