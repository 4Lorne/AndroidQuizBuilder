package com.example.quizbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class GamePlay extends AppCompatActivity {
    String name;
    String currentScoreString = "Score: ";

    TextView question, questionNumber, currentScore;
    TextView aRadio, bRadio, cRadio, dRadio;
    RadioGroup group;

    Button submitAnswer;

    ArrayList<String> term = new ArrayList<>();
    ArrayList<String> definition = new ArrayList<>();
    HashMap<String, String> dictionary = new HashMap<>();

    int index; // Used for randomizing the index
    int questionCounter = 1;
    int score = 0;
    boolean found = false;


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
                changeView();
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
        group.clearCheck(); //Removes the button selection

    }

    //Checks for no radio button selected
    public void emptyAnswer() {
        if (group.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please select an answer to continue", Toast.LENGTH_LONG).show();
        }
    }

    //Checks for validity
    public void checkAnswer(TextView radio, String currentScoreString) {
        if (radio.getText().equals(dictionary.get(term.get(index)))) {
            Toast.makeText(getApplicationContext(), "Correct answer", Toast.LENGTH_LONG).show();
            score++;
            currentScoreString = currentScoreString + score;
            currentScore.setText(currentScoreString);
            regenerateQuestions();
        } else {
            regenerateQuestions();
        }
    }

    //TODO: Remove Questions as you answer
    //Generates new questions every time it is ran
    public void regenerateQuestions() {
        Collections.shuffle(definition);

        String questionDefault = "Question ";
        questionNumber.setText(questionDefault);
        String questionNum = questionNumber.getText() + " " + questionCounter + "/10";
        questionNumber.setText(questionNum);

        //Randomizing the outcomes
        Random r = new Random();
        index = r.nextInt(term.size());

        //TODO: Hard coded to always have the answer be in the first position, fix later
        //TODO: Possible for answers to be duplicates
        ArrayList<TextView> radioGroup = new ArrayList<>(Arrays.asList(aRadio,bRadio,cRadio,dRadio));
        question.setText(term.get(index));

//        for (int i = 0; i < 4; i++){
//            if (radioGroup.get(i).getText().equals(dictionary.get(question.getText().toString()))){
//                found = true;
//                System.out.println("found");
//            }
//            if (!found){
//                radioGroup.get(i).setText(dictionary.get(definition.get(index)));
//            }
//            radioGroup.get(i).setText(definition.get(i));
//
//        }
        aRadio.setText(definition.get(0));
        bRadio.setText(definition.get(1));
        cRadio.setText(definition.get(2));
        dRadio.setText(definition.get(3));

        if (Objects.equals(dictionary.get(term.get(index)), aRadio.getText())){
            System.out.println("True");
        }

        //found = false;
        questionCounter++;
    }

    //Reads in the terms.txt file and extracts the file into two arraylists and one hashmap
    public void readFile() {
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.terms));
        scan.useDelimiter("[\\r\\n,]+");

        //Breaks the file into terms and definitions
        while (scan.hasNext()) {
            String token = scan.next();
            if (token.contains("What")) {
                term.add(token);
            } else {
                definition.add(token);
            }
        }

        for (int i = 0; i < term.size(); i++) {
            dictionary.put(term.get(i), definition.get(i));
        }
    }

    //Changes the view to the finished screen
    public void changeView() {
        if (questionCounter > 10){
            Intent i = new Intent(GamePlay.this, GameFinished.class);
            i.putExtra("score", String.valueOf(score));
            startActivity(i);
        }
    }
}

