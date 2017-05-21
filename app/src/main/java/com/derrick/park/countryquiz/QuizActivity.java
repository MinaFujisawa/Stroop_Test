package com.derrick.park.countryquiz;

import android.app.Activity;

import android.content.res.Resources;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    Random random = new Random();
    final int BTNNUM = 4;
    private Button mBtnRed;
    private Button mBtnGreen;
    private Button mBtnYellow;
    private Button mBtnBlue;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
//    private Button[] btnList = new Button[BTNNUM];
    private ArrayList<Button> btnList = new ArrayList<>();
    private TextView mQustionColor;

    private TextView mCountDownText;
    private TextView mIndexText;
    private int questionIndex = 0;
    private int point = 1;

    private Question[] questionList = {
            new Question(R.string.c_blue),
            new Question(R.string.c_yellow),
            new Question(R.string.c_black),
            new Question(R.string.c_pink),
            new Question(R.string.c_red),
            new Question(R.string.c_green)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnList.add((Button) findViewById(R.id.btn1));
        btnList.add((Button) findViewById(R.id.btn2));
        btnList.add((Button) findViewById(R.id.btn3));
        btnList.add((Button) findViewById(R.id.btn4));

        mIndexText = (TextView) findViewById(R.id.index);
        mIndexText.setText(String.valueOf(questionIndex));


        //first question
        mQustionColor = (TextView) findViewById(R.id.question_color);
        setQandA();


//        mBtnRed = (Button) findViewById(R.id.btn_red);
//        mBtnRed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                check("Red");
//                showQuestion();
//                countIndex();
//            }
//        });


    }


    private void check(String userAnswer) {
        if ((mQustionColor.getText().toString()).equals(userAnswer)) {
            Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            point++;
        } else {
            Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    private void setQandA() {
        int QIndex = randomNum();
        mQustionColor.setText(questionList[QIndex].getQuestionColor());
        setAnswerBtn(QIndex);
        int colorList[] = {
                getResources().getColor(R.color.colorRed),
                getResources().getColor(R.color.colorBlue),
                getResources().getColor(R.color.colorYellow),
                getResources().getColor(R.color.colorPink),
                getResources().getColor(R.color.colorBlack),
                getResources().getColor(R.color.colorGreen)
        };
        mQustionColor.setTextColor(colorList[randomNum()]);
    }

    private void setAnswerBtn(int currentQIndex) {
        int ranNum = random.nextInt(BTNNUM);
        boolean num[] = new boolean[questionList.length];

        // set other option btns
        for (int i = 0; i < BTNNUM; ) {
            int p = random.nextInt(BTNNUM);
            if (num[p] == false && p != currentQIndex) { //まだ使ってない値か判定
                btnList.get(i).setText(questionList[p].getQuestionColor());
                num[p] = true; //使った値はtrueにしておく
                i++; //ループ用の値をインクリメント
            }
        }

        // set collect answer btn
        btnList.get(ranNum).setText(mQustionColor.getText().toString());

    }

    private void countIndex() {
        questionIndex++;
        mIndexText.setText(String.valueOf(questionIndex));
    }

    private int randomNum() {
        return random.nextInt(questionList.length);
    }

}
