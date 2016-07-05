package com.ethanco.circleprogressdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ethanco.circleprogresslibrary.CircleProgress;
import com.ethanco.circleprogresslibrary.TickCircleProgress;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CircleProgress mCircleProgress;
    private Button btnSetProgress;
    private TickCircleProgress mTickCircleProgress;
    private TickCircleProgress mTickCircleProgress2;
    private CircleProgress mCircleProgress2;
    private TickCircleProgress mTickCircleProgress3;
    private TickCircleProgress mTickCircleProgress4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCircleProgress = (CircleProgress) findViewById(R.id.myCircleProgress);
        mCircleProgress2 = (CircleProgress) findViewById(R.id.myCircleProgress2);
        mTickCircleProgress = (TickCircleProgress) findViewById(R.id.myTickCircleProgress);
        mTickCircleProgress2 = (TickCircleProgress) findViewById(R.id.myTickCircleProgress2);
        mTickCircleProgress3 = (TickCircleProgress) findViewById(R.id.myTickCircleProgress3);
        mTickCircleProgress4 = (TickCircleProgress) findViewById(R.id.myTickCircleProgress4);
        btnSetProgress = (Button) findViewById(R.id.btnSetProgress);
        btnSetProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleProgress.setProgress(new Random().nextInt(100));
                mCircleProgress2.setProgress(new Random().nextInt(100));
                mTickCircleProgress.setProgress(new Random().nextInt(100));
                mTickCircleProgress2.setProgress(new Random().nextInt(100));
                mTickCircleProgress3.setProgress(new Random().nextInt(100));
                mTickCircleProgress4.setProgress(new Random().nextInt(100));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.startSecondActivity:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
