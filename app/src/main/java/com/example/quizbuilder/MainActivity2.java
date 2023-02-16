package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    String name = "";
    PlayerObject player = new PlayerObject(name);

    TextView participantName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name = getIntent().getStringExtra("user");
        player.setName("Name: "+name);
        participantName = findViewById(R.id.tvParticipantName);
        participantName.setText(player.getName());
    }



}