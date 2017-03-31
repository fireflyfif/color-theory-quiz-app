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
    Button bNext;

    CheckBox ansQone[];
    CheckBox ansQtwo[];




    private TextView yourScore;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


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
        bNext = (Button) findViewById(R.id.button_next);

        // Set cards to be GONE, except for the first one
        cards = new CardView[] {cardViewOne, cardViewTwo,cardViewThree, cardViewFour, cardViewFive,
         cardViewSix, cardViewSeven, cardViewEight, cardViewNine};
        for (int i = 1; i < 9; i++) {
            cards[i].setVisibility(View.GONE);
        }
        cardViewLast.setVisibility(View.GONE);

    }

//    public void displayScore() {
//        String scoreString = getString(R.string.plus) + String.valueOf(score);
//        scoreCount.setText(scoreString);
//    }

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

//        for(int i = 0; i < ansQone.length; i++) {
//            if(!ansQone[i].isChecked()) {
//                bNext.setEnabled(true);
//                Toast.makeText(QuizActivity.this, "You must answer at least one question!",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }

        // Disable the Submit Button if the user did not answer any question
        if (score < 1) {
            bNext.setEnabled(true);
            Toast.makeText(QuizActivity.this, "You must answer at least one question!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Show the last CardView with the Result
        cards[currentQuestion].setVisibility(View.GONE);
        cardViewLast.setVisibility(View.VISIBLE);
        yourScore.setText("Hey " + name);
        if (score <= 20) {
            yourScore.setText("Hey " + name + "\nYou score is " + score + " out of 90" +
                    "\nYou can do better!");
            ImageView looserImage = (ImageView) findViewById(R.id.result_image);
            looserImage.setImageResource(R.drawable.coffee_gallery_2);
        } else {
            yourScore.setText("Hey " + name + "\nYou score is " + score + " out of 90" +
                    "\nYou know your Colors!");
            ImageView winnerImage = (ImageView) findViewById(R.id.result_image);
            winnerImage.setImageResource(R.drawable.color_quiz_splash_01);
        }
    }

    // Button Restart
    public void buttonRestart(View view) {
        calculateScore();
        finish();
        startActivity(new Intent(this, StartActivity.class));
    }

    private String shareResultsMsg(int score, String name) {
        String intentResultMsg = "Hey, check out my score on The Color Theory Quiz";
        intentResultMsg += "\nI got " + score + "out of 90 points!";
        intentResultMsg += "Yours, " + name;
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
        ansQone = new CheckBox[]{ans1, ans2, ans3, ans4, ans5};

        if (ans1.isChecked() && !ans2.isChecked() && ans3.isChecked() &&
                !ans4.isChecked() && ans5.isChecked()) {
            score += 10;
//            Toast.makeText(this, "You are correct!", Toast.LENGTH_SHORT).show();
        }
    }

    // Score Question No.2
    public void scoringQuestionTwo() {
        CheckBox ans1 = (CheckBox) findViewById(R.id.q2_ans1);
        CheckBox ans2 = (CheckBox) findViewById(R.id.q2_ans2);
        CheckBox ans3 = (CheckBox) findViewById(R.id.q2_ans3);
        CheckBox ans4 = (CheckBox) findViewById(R.id.q2_ans4);
        CheckBox ans5 = (CheckBox) findViewById(R.id.q2_ans5);
        ansQtwo = new CheckBox[] {ans1, ans2, ans3, ans4, ans5};

        if (ans1.isChecked() && !ans2.isChecked() && ans3.isChecked() && ans4.isChecked()
                && !ans5.isChecked()) {
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
        RadioButton ans1 = (RadioButton) findViewById(R.id.q7_ans_1);
        if (ans1.isChecked()) {
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
