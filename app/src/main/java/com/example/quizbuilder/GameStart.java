package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

//TODO: Add check for Name in name

public class GameStart extends AppCompatActivity {
    Button startGame;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);
        //populateSpinner();
        startGame = findViewById(R.id.button);
        startGame.setOnClickListener(onButtonClicked);

    }


    public View.OnClickListener onButtonClicked = v -> {
        switch (v.getId()) {
            case R.id.button:
                changeView();
        }
    };

    //Switches the view to the quiz fragment
    public void changeView() {
        i = new Intent(GameStart.this, GamePlay.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //i.putExtra("user", ptEntrantName.getText().toString());
        startActivity(i);
    }

    //Populates the spinner with different topics to choose from
//    public void populateSpinner() {
//        Spinner spinner = (Spinner) findViewById(R.id.topicSpinner);
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.topic_spinner, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);
//    }
}