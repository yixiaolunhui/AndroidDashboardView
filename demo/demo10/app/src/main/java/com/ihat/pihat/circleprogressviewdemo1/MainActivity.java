package com.ihat.pihat.circleprogressviewdemo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvFinance;
    private List<FinanceBean> beanList = new ArrayList<>();
    private FinanceAdapter financeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }
    private void initView() {
        rvFinance = (RecyclerView) findViewById(R.id.rv_finance);
        rvFinance.setLayoutManager(new LinearLayoutManager(this));
        rvFinance.setItemAnimator(new DefaultItemAnimator());

        initData();
        financeAdapter = new FinanceAdapter(this, beanList);
        rvFinance.setAdapter(financeAdapter);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            FinanceBean fb = new FinanceBean("货品" + i, 100 - i);
            beanList.add(fb);
        }
    }
}
