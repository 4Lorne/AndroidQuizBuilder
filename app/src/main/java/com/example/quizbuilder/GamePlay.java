package com.example.quizbuilder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
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

//TODO: Implement color change on buttons when wrong, when a delay before new questions come
public class GamePlay extends AppCompatActivity {
    String currentScoreString = "Score: 0";

    TextView question, questionNumber, currentScore;
    RadioButton aRadio, bRadio, cRadio, dRadio;
    RadioGroup group;

    Button submitAnswer;

    ArrayList<String> term = new ArrayList<>();
    ArrayList<String> definition = new ArrayList<>();
    HashMap<String, String> dictionary = new HashMap<>();

    int index; // Used for randomizing the index
    int questionCounter = 1; // Appends to the question counter string
    int counter =0; // Counts the iterations
    int score = 0; // Tracks score
    int answerPos; // Tracks the position of the answer in the radio group

    boolean found = false; //Checks if the answer was already inserted in a button


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0275d8"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);

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

        currentScore.setText(currentScoreString);
        readFile();
        regenerateQuestions();
    }

    public View.OnClickListener onButtonClicked = v -> {
        switch (v.getId()) {
            case R.id.tvSubmitAnswer:
                counter++;
                submitAnswer();

        }
    };

    public void submitAnswer() {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.aRadio:
                checkAnswer(aRadio);
                break;
            case R.id.bRadio:
                checkAnswer(bRadio);
                break;
            case R.id.cRadio:
                checkAnswer(cRadio);
                break;
            case R.id.dRadio:
                checkAnswer(dRadio);
                break;
            default:
                emptyAnswer();
                break;

        }
         //Removes the button selection
        group.clearCheck();
    }

    //Checks for no radio button selected
    public void emptyAnswer() {
        if (group.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please select an answer to continue", Toast.LENGTH_LONG).show();
        }
    }

    //Checks for validity
    public void checkAnswer(RadioButton radio) {
        System.out.println(answerPos);
        int radioID = radio.getId();
        System.out.println(radioID);

        switch(answerPos){
            case 0:
                if (radioID == 2131296270){
                    givePoints();
                }
                break;
            case 1:
                if (radioID == 2131296344){
                    givePoints();
                }
                break;
            case 2:
                if (radioID == 2131296359){
                    givePoints();
                }
                break;
            case 3:
                if (radioID == 2131296398){
                    givePoints();
                }
                break;
        }
        regenerateQuestions();
    }

    public void givePoints(){
        score++;
        currentScoreString = "Score: " + score;
        currentScore.setText(currentScoreString);
    }

    //Generates new questions every time it is ran
    public void regenerateQuestions() {
        //Randomize the order
        Collections.shuffle(definition);
        Collections.shuffle(term);

        //Setting the question up
        String questionDefault = "Question ";
        questionNumber.setText(questionDefault);
        String questionNum = questionNumber.getText() + " " + questionCounter + "/10";
        questionNumber.setText(questionNum);

        //Randomizing the outcomes
        Random r = new Random();
        index = r.nextInt(3);

        ArrayList<TextView> radioGroup = new ArrayList<>(Arrays.asList(aRadio, bRadio, cRadio, dRadio));

        if (term.isEmpty()){
            term.add("");
        }
        try {
            question.setText(term.get(0));
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        aRadio.setText(definition.get(0));
        bRadio.setText(definition.get(1));
        cRadio.setText(definition.get(2));
        dRadio.setText(definition.get(3));

        //Places the definitions in the slots, if the answer is not present by the 3rd radio
        //Button the term is forced into a random slot
        for (int i = 0; i < 4; i++) {
            if (radioGroup.get(i).getText().equals(dictionary.get(term.get(0)))) {
                found = true;
                answerPos = i;
            }
        }
        //Randomizes the slot the term takes up
        if (!found) {
            radioGroup.get(index).setText(dictionary.get(term.get(0)));
            answerPos = index;
        }

        try {
            term.remove(0);
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        questionCounter++;
        if (questionCounter == 11){
            questionCounter = 10;
        }
        if (counter == 10) {
            changeView();
        }
        found = false; //Resetting value
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
            Intent i = new Intent(GamePlay.this, GameFinished.class);
            i.putExtra("score", String.valueOf(score));
            startActivity(i);
    }
}

