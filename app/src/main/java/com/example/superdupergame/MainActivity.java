package com.example.superdupergame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.example.superdupergame.duck.Activities.StartActivity;


public class MainActivity extends AppCompatActivity {

    /**
     * <pre>
     *     0 = Anvil
     *     1 = Duck
     *     2 = Pacman
     * </pre>
     */
    private int gameCount = 0;

    private AnimationDrawable llamaAnimation;
    private TextView titleTV, descriptionTV;
    private ImageView imageView, llamaView;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.gameImageView);
        titleTV = findViewById(R.id.titleTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        toggleButton = findViewById(R.id.surpriseButton);

        llamaView = findViewById(R.id.llama);
        llamaView.setBackgroundResource(R.drawable.llama);
        llamaAnimation = (AnimationDrawable) llamaView.getBackground();

        displayGameInfo();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        llamaAnimation.start();
    }

    public void playGame(View v)
    {
        Intent intent = new Intent(this, MainActivity.class); //To not get mad
        switch(gameCount)
        {
            case 0: //Anvil Game
                intent = new Intent(this, com.example.superdupergame.anvil.GameActivity.class);
            break;
            case 1: //Duck Game
                intent = new Intent(this, StartActivity.class);
            break;
            case 2: //Pacman
                intent = new Intent(this, com.example.superdupergame.pacman.GameActivity.class);
                intent.putExtra("neumont",  toggleButton.isChecked());
            break;
        }
        startActivity(intent);
        finish();
    }

    public void nextGame(View v)
    {
        gameCount = gameCount + 1 > 2 ? 0: gameCount + 1;
        displayGameInfo();
    }

    public void lastGame(View v)
    {
        gameCount = gameCount -1 < 0 ? 2: gameCount - 1;
        displayGameInfo();
    }

    public void showLlama(View v)
    {
        if(toggleButton.isChecked())
            llamaView.setVisibility(View.VISIBLE);
        else
            llamaView.setVisibility(View.INVISIBLE);
    }

    private void displayGameInfo()
    {
        switch(gameCount)
        {
            case 0:
                titleTV.setText(("Anvil Rain"));
                imageView.setImageResource(R.drawable.anvil_logo);
                descriptionTV.setText(("You are a slime transported to another world, sadly that world is very plain and anvils are falling from the sky."));
                toggleButton.setVisibility(View.INVISIBLE);
                llamaView.setVisibility(View.INVISIBLE);
                break;
            case 1:
                titleTV.setText(("Duck Duck Goose!"));
                imageView.setImageResource(R.drawable.duck_logo);
                descriptionTV.setText(("The geese have figured out that we are in possession of the goose that lays the golden egg. You must protect this land. Take out the skein of geese headed for our kingdom!"));
                toggleButton.setVisibility(View.INVISIBLE);
                llamaView.setVisibility(View.INVISIBLE);
                break;
            case 2:
                titleTV.setText(("Pacman"));
                imageView.setImageResource(R.drawable.pacman_logo);
                descriptionTV.setText(("Yellow Ball eating more balls. Spooky Ghosts run away!"));
                toggleButton.setVisibility(View.VISIBLE);
                showLlama(null);
                break;
        }
    }


}
