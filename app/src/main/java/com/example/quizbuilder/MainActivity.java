package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button startGame;
    TextView entrantName;

    PlayerObject player = new PlayerObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateSpinner();
        startGame = findViewById(R.id.button);
        entrantName = findViewById(R.id.ptEntrantName);
        startGame.setOnClickListener(onButtonClicked);
    }

    public View.OnClickListener onButtonClicked = v -> {
        switch (v.getId()) {
            case R.id.button:
                createPlayer();
                changeView();
        }
    };

    public void onFocusChange(View v, boolean hasFocus){

    }
    public void changeView(){
        Intent i = new Intent(MainActivity.this,MainActivity2.class);
        i.putExtra("user",entrantName.getText().toString());
        startActivity(i);
    }
    public void populateSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.topicSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.topic_spinner, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void createPlayer(){
        try {
            player.setName(entrantName.toString());
            System.out.println(player.getName());
        } catch (Exception e){
            e.getCause();
        }



    }
}