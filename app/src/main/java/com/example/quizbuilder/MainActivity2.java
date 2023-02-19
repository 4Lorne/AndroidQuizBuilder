package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Scanner;

public class MainActivity2 extends AppCompatActivity {
    String name = "";
    PlayerObject player = new PlayerObject(name);
    TextView participantName;
    TextView tvQuestion;

    ArrayList<String> term = new ArrayList<>();
    ArrayList <String> definition = new ArrayList<>();
    HashMap<String,String> dictionary = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name = getIntent().getStringExtra("user");
        player.setName("Name: "+name);
        participantName = findViewById(R.id.tvParticipantName);
        tvQuestion = findViewById(R.id.tvQuestion);
        participantName.setText(player.getName());
        tvQuestion.setText("");
        readFile();
    }



    //TODO: Fix file IO
    public void readFile() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.terms));
        scan.useDelimiter("[\\r\\n,]+");
        //Breaks the file into terms and definitions
        while (scan.hasNext()){
            String token = scan.next();
            if (token.contains("Question")){
                term.add(token);
            } else {
                definition.add(token);
            }
        }

        for (int i = 0; i < term.size();i++){
            dictionary.put(term.get(i), definition.get(i));
        }

        Random r = new Random();
        int index = r.nextInt(term.size());

        tvQuestion.setText(term.get(index));
    }
}

