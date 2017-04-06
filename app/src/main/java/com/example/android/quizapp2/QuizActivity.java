package com.example.android.quizapp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.quizapp2.StartActivity.USER_NAME;

public class QuizActivity extends AppCompatActivity {

    public static final String SAVE_SCORE = "saveScore";
    public static final String SAVE_CURRENT_QUESTION = "save_current_question";
    public static final String SAVE_NAME = "save_name";
    public static final String ALL_CARDS = "all_cards";
    public static final String SAVE_CARD_ONE = "save_card_one";
    int score = 0;
    int currentQuestion = 0;


    CardView cardViewOne;
    CardView cardViewTwo;
    CardView cardViewThree;
    CardView cardViewFour;
    CardView cardViewFive;
    CardView cardViewSix;
    CardView cardViewSeven;
    CardView cardViewEight;
    CardView cardViewNine;
    CardView cardViewLast;
    CardView cards[];
    Button buttonNext;

    CheckBox ansQone[];
    CheckBox ansQtwo[];


    private TextView yourScore;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Save the user's current game state
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SAVE_SCORE, score);
            currentQuestion = savedInstanceState.getInt(SAVE_CURRENT_QUESTION, currentQuestion);
            name = savedInstanceState.getString(SAVE_NAME, name);
//            cardViewOne = savedInstanceState.getParcelable(SAVE_CARD_ONE);


//            cards = savedInstanceState.getBundle(ALL_CARDS);
        }


        // Pass an Intent of the name
        Intent intent = getIntent();
        name = intent.getStringExtra(USER_NAME);
        TextView yourName = (TextView) findViewById(R.id.name_text);
        yourName.setText(name);

        // Find views from the xml
        cardViewOne = (CardView) findViewById(R.id.cardView_1);
        cardViewTwo = (CardView) findViewById(R.id.cardView_2);
        cardViewThree = (CardView) findViewById(R.id.cardView_3);
        cardViewFour = (CardView) findViewById(R.id.cardView_4);
        cardViewFive = (CardView) findViewById(R.id.cardView_5);
        cardViewSix = (CardView) findViewById(R.id.cardView_6);
        cardViewSeven = (CardView) findViewById(R.id.cardView_7);
        cardViewEight = (CardView) findViewById(R.id.cardView_8);
        cardViewNine = (CardView) findViewById(R.id.cardView_9);
        cardViewLast = (CardView) findViewById(R.id.cardView_last);
        yourScore = (TextView) findViewById(R.id.score_text);
        buttonNext = (Button) findViewById(R.id.button_next);

        // Set cards to be GONE, except for the first one
        cards = new CardView[]{cardViewOne, cardViewTwo, cardViewThree, cardViewFour, cardViewFive,
                cardViewSix, cardViewSeven, cardViewEight, cardViewNine};
        for (int i = 1; i < 9; i++) {
            cards[i].setVisibility(View.GONE);
        }
        cardViewLast.setVisibility(View.GONE);

    }

    // Save state when rotating
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(SAVE_SCORE, score);
        savedInstanceState.putInt(SAVE_CURRENT_QUESTION, currentQuestion);
        savedInstanceState.putString(SAVE_NAME, name);
//        savedInstanceState.putParcelable(SAVE_CARD_ONE, cardViewOne);
//        savedInstanceState.putParcelable(ALL_CARDS, cards);
        super.onSaveInstanceState(savedInstanceState);
    }

    // Restore state when rotating

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.getInt(SAVE_SCORE);
        savedInstanceState.getInt(SAVE_CURRENT_QUESTION);
        savedInstanceState.getString(SAVE_NAME);
        savedInstanceState.getParcelable(ALL_CARDS);
        super.onRestoreInstanceState(savedInstanceState);
    }

    // Button Next
    public void buttonNext(View view) {
        cards[currentQuestion].setVisibility(View.GONE);
        currentQuestion += 1;
        cards[currentQuestion].setVisibility(View.VISIBLE);
    }

    // Button Back
    public void buttonBack(View view) {
        cards[currentQuestion].setVisibility(View.GONE);
        currentQuestion -= 1;
        cards[currentQuestion].setVisibility(View.VISIBLE);
    }

    // Button Submit
    public void buttonSubmit(View view) {
        calculateScore();

        // Disable the Submit Button if the user did not answer any question
        if (score < 1) {
            buttonNext.setEnabled(true);
            Toast.makeText(QuizActivity.this, "You must answer at least one question!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the last CardView with the Result
        cards[currentQuestion].setVisibility(View.GONE);
        cardViewLast.setVisibility(View.VISIBLE);
        yourScore.setText("Hey " + name);
        if (score <= 20) {
            yourScore.setText("Hey " + name + "\nYou score is " + score + " out of 90." +
                    "\nYou can do better!");
            ImageView loserImage = (ImageView) findViewById(R.id.result_image);
            loserImage.setImageResource(R.drawable.color_trophy_empty);
        } else if (score <= 60) {
            yourScore.setText("Hey " + name + "\nYou score is " + score + " out of 90." +
                    "\nNice! Almost there!");
            ImageView mediocreImage = (ImageView) findViewById(R.id.result_image);
            mediocreImage.setImageResource(R.drawable.color_trophy_half);
        } else {
            yourScore.setText("Hey " + name + "\nYou score is " + score + " out of 90." +
                    "\nAwesome! You know your Colors!");
            ImageView winnerImage = (ImageView) findViewById(R.id.result_image);
            winnerImage.setImageResource(R.drawable.color_trophy_full);
        }
    }

    // Button Restart
    public void buttonRestart(View view) {
        calculateScore();
        finish();
        startActivity(new Intent(this, StartActivity.class));
    }

    private String shareResultsMsg(int score, String name) {
        String intentResultMsg = "Hey, check out my score on The Color Theory Quiz!";
        intentResultMsg += "\nI got " + score + " out of 90 points!";
        intentResultMsg += "\nYours, " + name;
        return intentResultMsg;

    }

    public void buttonShare(View view) {
        String intentResultMsg = shareResultsMsg(score, name);
        Log.v("QuizActivity", "Display message here: " + intentResultMsg);

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SENDTO);
        shareIntent.setData(Uri.parse("mailto: fif.iva@gmail.com"));
        shareIntent.putExtra(Intent.EXTRA_EMAIL, "To fif.iva@gmail.com");
        shareIntent.putExtra(Intent.EXTRA_TEXT, intentResultMsg);
        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareIntent);
        }
    }


    // Calculate score
    public void calculateScore() {
        score = 0;
        scoringQuestionOne();
        scoringQuestionTwo();
        scoringQuestionThree();
        scoringQuestionFour();
        scoringQuestionFive();
        scoringQuestionSix();
        scoringQuestionSeven();
        scoringQuestionEight();
        scoringQuestionNine();
    }

    // Score Question No.1
    public void scoringQuestionOne() {
        CheckBox ans1 = (CheckBox) findViewById(R.id.checkBox_1);
        CheckBox ans2 = (CheckBox) findViewById(R.id.checkBox_2);
        CheckBox ans3 = (CheckBox) findViewById(R.id.checkBox_3);
        CheckBox ans4 = (CheckBox) findViewById(R.id.checkBox_4);
        CheckBox ans5 = (CheckBox) findViewById(R.id.checkBox_5);
        CheckBox ans6 = (CheckBox) findViewById(R.id.checkBox_6);
        ansQone = new CheckBox[]{ans1, ans2, ans3, ans4, ans5, ans6};

        if (ans1.isChecked() && !ans2.isChecked() && ans3.isChecked() &&
                !ans4.isChecked() && ans5.isChecked() && !ans6.isChecked()) {
            score += 10;
//            Toast.makeText(this, "You are correct!", Toast.LENGTH_SHORT).show();
        }
    }

    // Score Question No.2
    public void scoringQuestionTwo() {
        RadioButton ans1 = (RadioButton) findViewById(R.id.q2_ans_1);
        if (ans1.isChecked()) {
            score += 10;
        }
    }

    // Score Question No.3
    public void scoringQuestionThree() {
        RadioButton ans3 = (RadioButton) findViewById(R.id.q3_ans_3);
        if (ans3.isChecked()) {
            score += 10;
        }
    }

    // Score Question No.4
    public void scoringQuestionFour() {
        EditText editQuestion = (EditText) findViewById(R.id.editText_q4);
        if (editQuestion.getText().toString().equals(getString(R.string.q4_ans))) {
            score += 10;
        }
    }

    // Score Question No.5
    public void scoringQuestionFive() {
        EditText editQuestionFive = (EditText) findViewById(R.id.editText_q5);
        if (editQuestionFive.getText().toString().equals(getString(R.string.q5_ans))) {
            score += 10;
        }
    }

    // Score Question No.6
    public void scoringQuestionSix() {
        CheckBox ans1 = (CheckBox) findViewById(R.id.q6_ans1);
        CheckBox ans2 = (CheckBox) findViewById(R.id.q6_ans2);
        CheckBox ans3 = (CheckBox) findViewById(R.id.q6_ans3);
        CheckBox ans4 = (CheckBox) findViewById(R.id.q6_ans4);
        CheckBox ans5 = (CheckBox) findViewById(R.id.q6_ans5);
        CheckBox ans6 = (CheckBox) findViewById(R.id.q6_ans6);

        if (!ans1.isChecked() && !ans2.isChecked() && ans3.isChecked() && !ans4.isChecked()
                && ans5.isChecked() && ans6.isChecked()) {
            score += 10;
        }
    }

    // Score Question No.7
    public void scoringQuestionSeven() {
        CheckBox ans1 = (CheckBox) findViewById(R.id.q7_ans1);
        CheckBox ans2 = (CheckBox) findViewById(R.id.q7_ans2);
        CheckBox ans3 = (CheckBox) findViewById(R.id.q7_ans3);
        CheckBox ans4 = (CheckBox) findViewById(R.id.q7_ans4);
        CheckBox ans5 = (CheckBox) findViewById(R.id.q7_ans5);
        CheckBox ans6 = (CheckBox) findViewById(R.id.q7_ans6);
        ansQtwo = new CheckBox[]{ans1, ans2, ans3, ans4, ans5, ans6};

        if (ans1.isChecked() && !ans2.isChecked() && ans3.isChecked() && ans4.isChecked()
                && !ans5.isChecked() && !ans6.isChecked()) {
            score += 10;
        }
    }

    // Score Question No.8
    public void scoringQuestionEight() {
        CheckBox ans1 = (CheckBox) findViewById(R.id.q8_ans1);
        CheckBox ans2 = (CheckBox) findViewById(R.id.q8_ans2);
        CheckBox ans3 = (CheckBox) findViewById(R.id.q8_ans3);
        CheckBox ans4 = (CheckBox) findViewById(R.id.q8_ans4);
        CheckBox ans5 = (CheckBox) findViewById(R.id.q8_ans5);
        CheckBox ans6 = (CheckBox) findViewById(R.id.q8_ans6);
        CheckBox ans7 = (CheckBox) findViewById(R.id.q8_ans7);
        CheckBox ans8 = (CheckBox) findViewById(R.id.q8_ans8);

        if (ans1.isChecked() && !ans2.isChecked() && ans3.isChecked() && !ans4.isChecked() &&
                !ans5.isChecked() && ans6.isChecked() && ans7.isChecked() && !ans8.isChecked()) {
            score += 10;
        }
    }

    // Score Question No.9
    public void scoringQuestionNine() {
        RadioButton ans2 = (RadioButton) findViewById(R.id.q9_ans_2);
        if (ans2.isChecked()) {
            score += 10;
        }
    }
}
