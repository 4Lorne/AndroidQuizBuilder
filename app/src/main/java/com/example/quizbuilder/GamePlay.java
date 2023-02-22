package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Scanner;

public class GamePlay extends AppCompatActivity {
    String name;
    String currentScoreString = "Score: ";
    PlayerObject player = new PlayerObject("");

    TextView question, questionNumber, currentScore;
    TextView aRadio, bRadio, cRadio, dRadio;
    RadioGroup group;

    Button submitAnswer;

    ArrayList<String> term = new ArrayList<>();
    ArrayList<String> definition = new ArrayList<>();
    HashMap<String, String> dictionary = new LinkedHashMap<>();

    int index; // Used for randomizing the index
    int questionCounter = 0;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        name = getIntent().getStringExtra("user");

        question = findViewById(R.id.tvQuestion);
        questionNumber = findViewById(R.id.tvQuestionNumber);
        submitAnswer = findViewById(R.id.tvSubmitAnswer);
        currentScore = findViewById(R.id.tvCurrentScore);

        aRadio = findViewById(R.id.aRadio);
        bRadio = findViewById(R.id.bRadio);
        cRadio = findViewById(R.id.cRadio);
        dRadio = findViewById(R.id.dRadio);
        group = findViewById(R.id.radioGroup);

        submitAnswer.setOnClickListener(onButtonClicked);

        question.setText("");
        currentScore.setText(currentScoreString);
        readFile();
        regenerateQuestions();
    }

    public View.OnClickListener onButtonClicked = v -> {
        switch (v.getId()) {
            case R.id.tvSubmitAnswer:
                submitAnswer();
        }
    };

    public void submitAnswer() {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.aRadio:
                checkAnswer(aRadio, currentScoreString);
                break;
            case R.id.bRadio:
                checkAnswer(bRadio, currentScoreString);
                break;
            case R.id.cRadio:
                checkAnswer(cRadio, currentScoreString);
                break;
            case R.id.dRadio:
                checkAnswer(dRadio, currentScoreString);
                break;
            default:
                emptyAnswer();
                break;

        }
        group.clearCheck();

    }

    public void emptyAnswer() {
        if (group.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please select an answer to continue", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void checkAnswer(TextView radio, String currentScoreString) {
        if (radio.getText().equals(dictionary.get(term.get(index)))) {
            if (score >= 10){
                return;
            }
            Toast.makeText(getApplicationContext(), "Correct answer", Toast.LENGTH_LONG).show();
            score++;
            currentScoreString = currentScoreString + score;
            currentScore.setText(currentScoreString);
            regenerateQuestions();
        }
    }

    //TODO: Remove Questions as you answer
    public void regenerateQuestions() {
        if (questionCounter >= 10) {
            return;
        }
        questionCounter++;
        String questionDefault = "Question ";
        questionNumber.setText(questionDefault);
        String questionNum = questionNumber.getText() + " " + questionCounter + "/10";
        questionNumber.setText(questionNum);

        //Randomizing the outcomes
        Random r = new Random();
        index = r.nextInt(term.size());

        Collections.shuffle(definition);
        //TODO: Hard coded to always have the answer be in the first position, fix later
        //TODO: Possible for answers to be duplicates
        aRadio.setText(dictionary.get(term.get(index)));
        bRadio.setText(definition.get(r.nextInt(10)));
        cRadio.setText(definition.get(r.nextInt(10)));
        dRadio.setText(definition.get(r.nextInt(10)));

        question.setText(term.get(index));
    }

    //Complete
    //Reads in the terms.txt file and extracts the file into two arraylists and one hashmap
    public void readFile() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.terms));
        scan.useDelimiter("[\\r\\n,]+");

        //Breaks the file into terms and definitions
        while (scan.hasNext()) {
            String token = scan.next();
            if (token.contains("Question")) {
                term.add(token);
            } else {
                definition.add(token);
            }
        }

        for (int i = 0; i < term.size(); i++) {
            dictionary.put(term.get(i), definition.get(i));
        }
    }
}

