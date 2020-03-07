package com.example.superdupergame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.superdupergame.duck.views.GameActivity;
import com.example.superdupergame.duck.views.StartActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void pacman(View v){
        Intent intent = new Intent(this, com.example.superdupergame.pacman.GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void anvil(View v)
    {
        Intent intent = new Intent(this, com.example.superdupergame.anvil.GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void duck(View v)
    {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
