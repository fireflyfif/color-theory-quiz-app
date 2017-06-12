package com.example.android.quizapp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
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
    public static final String SAVE_RESULT_TEXT = "save_result_text";
    public static final String IMAGE_RESULT = "image_result";

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

    private TextView yourScore;
    private String resultText;
    private ImageView resultImage;
    private String name;
    private int resultImageInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Save the user's current game state
        if (savedInstanceState != null) {
            score = savedInstanceState.getInt(SAVE_SCORE, score);
            currentQuestion = savedInstanceState.getInt(SAVE_CURRENT_QUESTION, currentQuestion);
            name = savedInstanceState.getString(SAVE_NAME, name);
            resultText = savedInstanceState.getString(SAVE_RESULT_TEXT, resultText);
        }

        // Pass an Intent of the name
        Intent intent = getIntent();
        name = intent.getStringExtra(USER_NAME);
        TextView yourName = (TextView) findViewById(R.id.name_text);
        yourName.setText(name);

        resultImageInt = 1;

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

        // Set cards to be GONE, except for the first one
        cards = new CardView[]{cardViewOne, cardViewTwo, cardViewThree, cardViewFour, cardViewFive,
                cardViewSix, cardViewSeven, cardViewEight, cardViewNine, cardViewLast};
        for (int i = 0; i < 10; i++) {
            if (currentQuestion != i) {
                cards[i].setVisibility(View.GONE);
            }
        }
    }

    // Restore state when rotating
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.getInt(SAVE_SCORE);
        savedInstanceState.getInt(SAVE_CURRENT_QUESTION);
        savedInstanceState.getString(SAVE_NAME);
        savedInstanceState.getInt(IMAGE_RESULT);
        // Restore state of dynamic text
        savedInstanceState.getString(SAVE_RESULT_TEXT);
        yourScore.setText(savedInstanceState.getString(SAVE_RESULT_TEXT));

        super.onRestoreInstanceState(savedInstanceState);
    }

    // Save state when rotating
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVE_SCORE, score);
        outState.putInt(SAVE_CURRENT_QUESTION, currentQuestion);
        outState.putString(SAVE_NAME, name);
        outState.putInt(IMAGE_RESULT, resultImageInt);
        // Save state of dynamic text
        outState.putString(SAVE_RESULT_TEXT, resultText);
        outState.putString(SAVE_RESULT_TEXT, yourScore.getText().toString());

        super.onSaveInstanceState(outState);
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
        currentQuestion += 1;
        calculateScore();

        // Show the last CardView with the Result
        cards[currentQuestion].setVisibility(View.GONE);
        cardViewLast.setVisibility(View.VISIBLE);

        // String for the final result
        resultText = resultMsg();
        displayResultMsg(resultText);
        resultImage = (ImageView) findViewById(R.id.result_image);

        if (score == 0) {
            // Toast message "Please, try again!"
            Toast.makeText(this, getString(R.string.result_zero) + score +
                            getString(R.string.result_final) + "\n" +
                            getString(R.string.toast_try_again), Toast.LENGTH_LONG).show();
            // Change image with Empty Trophy
            resultImage.setImageResource(R.drawable.color_trophy_empty);
        } else if (score <= 20) {
            // Toast message "You can do better!"
            Toast.makeText(this, getString(R.string.result_score) + score +
                            getString(R.string.result_final) + "\n" +
                            getString(R.string.toast_do_better), Toast.LENGTH_LONG).show();
            // Change image with Empty Trophy
            resultImage.setImageResource(R.drawable.color_trophy_empty);
        } else if (score <= 60) {
            // Toast message "Nice! Almost there!"
            Toast.makeText(this, getString(R.string.result_score) + score +
                            getString(R.string.result_final) + "\n" +
                            getString(R.string.toast_almost_there), Toast.LENGTH_LONG).show();
            // Change image with Half full Trophy
            resultImage.setImageResource(R.drawable.color_trophy_half);
        } else {
            // Toast message "Awesome! You know your Colors!"
            Toast.makeText(this, getString(R.string.result_score) + score +
                            getString(R.string.result_final) + "\n" +
                            getString(R.string.toast_awesome), Toast.LENGTH_LONG).show();
            // Change image with Full Trophy
            resultImage.setImageResource(R.drawable.color_trophy_full);
        }
    }

    // Button Restart
    public void buttonRestart(View view) {
        calculateScore();
        finish();
        startActivity(new Intent(this, StartActivity.class));
    }

    // Display Message with the final result
    private void displayResultMsg(String message) {
        yourScore.setText(message);
    }

    // Create message with the final result
    private String resultMsg() {
        String messageResult = getString(R.string.result_hey) + name;
        messageResult += "\n" + getString(R.string.result_score) + score +
                getString(R.string.result_final);
        return messageResult;
    }

    // Message that is passed with the Intent
    private String shareResultsMsg() {
        String intentResultMsg = getString(R.string.share_result_1);
        intentResultMsg += getString(R.string.share_result_2) + score +
                getString(R.string.share_result_3);
        intentResultMsg += getString(R.string.share_result_4) + name;
        return intentResultMsg;
    }

    // Button Share
    public void buttonShare(View view) {
        String intentResultMsg = shareResultsMsg();

        Intent shareIntent = new Intent(android.content.Intent.ACTION_SENDTO);
        shareIntent.setData(Uri.parse(getString(R.string.intent_mailTo)));
        shareIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.intent_extra_mail));
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

        if (ans1.isChecked() && !ans2.isChecked() && ans3.isChecked() &&
                !ans4.isChecked() && ans5.isChecked() && !ans6.isChecked()) {
            score += 10;
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
        if (editQuestion.getText().toString().trim().equals(getString(R.string.q4_ans))) {
            score += 10;
        }
    }

    // Score Question No.5
    public void scoringQuestionFive() {
        EditText editQuestionFive = (EditText) findViewById(R.id.editText_q5);
        if (editQuestionFive.getText().toString().trim().equals(getString(R.string.q5_ans))) {
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
