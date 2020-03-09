package com.example.superdupergame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AnvilGameOverActivity extends AppCompatActivity {

    TextView pointsTV;
    TextView highscoreTV;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anvil_game_over);


        Intent intent = getIntent();
        score = intent.getIntExtra("Points", 0);

        pointsTV = findViewById(R.id.pointsTV);
        highscoreTV = findViewById(R.id.highscoreTV);

        pointsTV.setText(score + "");
        highscoreTV.setText("HIGH SCORE : "+getHighscore());

    }

    private int getHighscore(){
        SharedPreferences prefs = this.getSharedPreferences("Anvil_HighScore", Context.MODE_PRIVATE);
        int lastScore = prefs.getInt("Anvil_HighScore", 0);
        if (score > lastScore){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("Anvil_HighScore", score);
            editor.apply();
            lastScore = score;
        }
        return lastScore;
    }

    public void menu_on_Click(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
