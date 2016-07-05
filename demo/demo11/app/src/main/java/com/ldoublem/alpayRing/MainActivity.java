package com.ldoublem.alpayRing;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.ldoublem.alpayRing.view.RingView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RingView rv_view;
    RelativeLayout ly_content;
    ArgbEvaluator evaluator;

    private int startColor = 0XFFfb5338;
    private int centerColor = 0XFF00ff00;
    private int endColor = 0XFF008dfc;
    private int endUseColor = 0;

    List<Integer> valueList = new ArrayList<>();
    List<String> valueNameList = new ArrayList<>();
    private int animDuration = 2500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ly_content = (RelativeLayout) findViewById(R.id.rl_content);
        evaluator = new ArgbEvaluator();
        rv_view = (RingView) findViewById(R.id.rv_view);
        valueList.add(350);
        valueList.add(450);
        valueList.add(550);
        valueList.add(650);
        valueList.add(750);
        valueList.add(850);
        rv_view.setValueList(valueList);
        valueNameList.add("较差");
        valueNameList.add("中等");
        valueNameList.add("良好");
        valueNameList.add("优秀");
        valueNameList.add("极好");
        rv_view.setValueNameList(valueNameList);
//        rv_view.setPointer(true);
        rv_view.setPointer(false);


//        ly_content.setBackgroundColor((Integer) evaluator.evaluate(0f, startColor, endColor));

        start((int) (350 + Math.random() * 500));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                start((int) (350 + Math.random() * 500));
                break;
            default:
                break;

        }
    }

    private void start(int value) {


        float f = (value - valueList.get(0)) * 1.0f / (valueList.get(valueList.size() - 1) - valueList.get(0));
        if (f <= 0.5f) {
            endUseColor = (Integer) evaluator.evaluate(f, startColor, centerColor);

        }
        else
        {
            endUseColor = (Integer) evaluator.evaluate(f, centerColor, endColor);

        }

        rv_view.setValue(value, new RingView.OnProgerssChange() {
            @Override
            public void OnProgerssChange(float interpolatedTime) {
                int evaluate = 0;

                if (interpolatedTime <= 0.5f) {

                    evaluate = (Integer) evaluator.evaluate(interpolatedTime, startColor, endUseColor);

                } else {
                    evaluate = (Integer) evaluator.evaluate(interpolatedTime, centerColor, endUseColor);
                }
                ly_content.setBackgroundColor(evaluate);


            }
        },(int)(f*animDuration));
    }





}
