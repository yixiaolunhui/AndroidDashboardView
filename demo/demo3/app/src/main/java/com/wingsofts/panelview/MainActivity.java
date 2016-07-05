package com.wingsofts.panelview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private PanelView mPanelView,mPanelView2;
    private SeekBar mSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPanelView = (PanelView) findViewById(R.id.panelView);
        mPanelView2 = (PanelView) findViewById(R.id.panelView2);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mPanelView.setText("已完成");
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPanelView.setPercent(progress);
                mPanelView2.setPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
