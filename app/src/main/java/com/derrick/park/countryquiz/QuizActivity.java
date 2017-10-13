package com.derrick.park.countryquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private Random random = new Random();
    private QuizList quizList = new QuizList();
    final int BTNNUM = 4;
    private ArrayList<Button> btnList = new ArrayList<>();
    private List<Color> colorList = new ArrayList<>();
    private TextView mQustionColorTextView;
    private TextView mCountDownTextView;
    private int quizIndex = 0;
    private int mScore;
    private String answerColor;
    int QIndex;

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();
        check(buttonText);

                setQuiz();
        setAnswerBtn(QIndex);
        quizIndex++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnList.add((Button) findViewById(R.id.btn1));
        btnList.add((Button) findViewById(R.id.btn2));
        btnList.add((Button) findViewById(R.id.btn3));
        btnList.add((Button) findViewById(R.id.btn4));

        colorList.add(new Color(getResources().getColor(R.color.colorRed), getString(R.string.c_red), true));
        colorList.add(new Color(getResources().getColor(R.color.colorBlack), getString(R.string.c_black), false));
        colorList.add(new Color(getResources().getColor(R.color.colorBlue), getString(R.string.c_blue), false));
        colorList.add(new Color(getResources().getColor(R.color.colorGreen), getString(R.string.c_green), true));
        colorList.add(new Color(getResources().getColor(R.color.colorPink), getString(R.string.c_pink), true));

        //CountDownTimer
        mCountDownTextView = (TextView) findViewById(R.id.countDown);
        new CountDownTimer(31000, 1000) {

            public void onTick(long millisUntilFinished) {
                mCountDownTextView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                Intent intent = new Intent(getApplication(), ResultActivity.class);
                intent.putExtra("score", mScore);
                startActivity(intent);
            }

        }.start();


        //first quiz
        mQustionColorTextView = (TextView) findViewById(R.id.quiz_color);
        setQuiz();
        setAnswerBtn(QIndex);

        // add click listeners for all buttons
        for (int i = 0; i < BTNNUM; i++) {
            btnList.get(i).setOnClickListener(this);
        }

    }


    private void check(String userAnswer) {
        if (answerColor.equals(userAnswer)) {
            Toast.makeText(QuizActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(QuizActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
        }
    }

    private void setQuiz() {
        QIndex = getRandomNum();
        //set text
        mQustionColorTextView.setText(quizList.getQuizList()[QIndex].getColorNameStringId());
        //set text color
        Color color = colorList.get(getRandomNum());
        answerColor = color.getColorName();
        mQustionColorTextView.setTextColor(color.getColorId());
    }

    private void setAnswerBtn(int currentQIndex) {
        boolean num[] = new boolean[quizList.getQuestionLength()];

        // set other option btns
        int i = 0;
        while (i < BTNNUM) {
            int randomNum = getRandomNum();
            if (num[randomNum] == false && randomNum != currentQIndex) {
                btnList.get(i).setText(quizList.getQuizList()[randomNum].getColorNameStringId());
                num[randomNum] = true;
                i++;
            }
        }
        // set collect answer btn
        btnList.get(random.nextInt(BTNNUM)).setText(answerColor);

        setColorToBtn();
    }


    private void setColorToBtn() {
        boolean num[] = new boolean[BTNNUM];
        int i = 0;
        while (i < BTNNUM) {
            int ranNum = random.nextInt(BTNNUM);
            if (num[ranNum] == false) {
                btnList.get(i).setTextColor(colorList.get(ranNum).getColorId());
                btnList.get(i).setBackgroundColor(colorList.get(ranNum).getColorId());
                if (colorList.get(ranNum).canBeBlackBg()) {
                    btnList.get(i).setBackgroundColor(getResources().getColor(R.color.colorBlack));
                } else {
                    btnList.get(i).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }

                num[ranNum] = true;
                i++;
            }
        }
    }

    private int getRandomNum() {
        return random.nextInt(quizList.getQuestionLength()-1);
    }
}