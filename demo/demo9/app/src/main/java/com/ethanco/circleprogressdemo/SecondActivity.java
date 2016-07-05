package com.ethanco.circleprogressdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ethanco.circleprogresslibrary.TextCircleProgress;

import java.util.Random;

public class SecondActivity extends AppCompatActivity {

    private TextCircleProgress mTextCircleProgress;
    private Button btnSetProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mTextCircleProgress = (TextCircleProgress) findViewById(R.id.myTextCircleProgress);
        btnSetProgress = (Button) findViewById(R.id.btnSetProgress);
        btnSetProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextCircleProgress.setMainProgress(new Random().nextInt(100));
                mTextCircleProgress.setSubProgress(new Random().nextInt(100));
                mTextCircleProgress.setHead("标题");
                mTextCircleProgress.setSubHead("this is subHead");
                mTextCircleProgress.setBottomHead("BOTTOM");
            }
        });
    }
}
