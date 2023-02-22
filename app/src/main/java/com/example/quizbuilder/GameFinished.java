package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameFinished extends AppCompatActivity {
    TextView tvScore;
    String score;
    Button restartGame;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);
        tvScore = findViewById(R.id.tvFinalScoreNum);
        restartGame = findViewById(R.id.btnPlayAgain);
        score = getIntent().getStringExtra("score");
        tvScore.setText(score);
        restartGame.setOnClickListener(onButtonClicked);
    }

    public View.OnClickListener onButtonClicked = v -> {
        switch (v.getId()) {
            case R.id.btnPlayAgain:
                changeView();
        }
    };

    public void changeView() {
        i = new Intent(GameFinished.this, GameStart.class);
        //i.putExtra("user", ptEntrantName.getText().toString());
        startActivity(i);
    }
}