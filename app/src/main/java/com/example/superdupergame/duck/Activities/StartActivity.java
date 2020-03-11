package com.example.superdupergame.duck.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.superdupergame.MainActivity;
import com.example.superdupergame.R;
import com.example.superdupergame.duck.models.AppConstant;

public class StartActivity extends AppCompatActivity {

    private boolean isMute;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private AppConstant appConstant = new AppConstant();

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_duck);

        radioGroup = findViewById(R.id.radioGroup);

        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return To Main menu
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });


        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Setting the level (egg=easy, duckling=medium, adult=hard)
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                if(radioButton.getText().equals("Egg")){
                    appConstant.modeEgg();
                }else if(radioButton.getText().equals("Duckling")){
                    appConstant.modeDuckling();
                }else if(radioButton.getText().equals("Adult")){
                    appConstant.modeAdult();
                }
                startActivity(new Intent(StartActivity.this, GameActivity.class));
            }
        });



        TextView highScoreTxt = findViewById(R.id.highScoreTxt);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("HighScore: " + prefs.getInt("highscore", 0));

        // Volume stuff I found on youtube
        isMute = prefs.getBoolean("isMute", false);
        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);
        if (isMute)
            volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
        else
            volumeCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);
        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMute = !isMute;
                if (isMute)
                    volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp);
                else
                    volumeCtrl.setImageResource(R.drawable.ic_volume_up_black_24dp);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();
            }
        });

    }
}
