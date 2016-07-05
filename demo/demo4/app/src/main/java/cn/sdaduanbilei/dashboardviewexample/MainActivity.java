package cn.sdaduanbilei.dashboardviewexample;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.math.BigDecimal;

import cn.sdaduanbilei.library.DashBoard;
import cn.sdaduanbilei.library.DashboardView;


public class MainActivity extends ActionBarActivity {

    DashboardView mDashoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDashoardView = (DashboardView) findViewById(R.id.dash_view);
        mDashoardView.setDashTitle("0 km/h"); // 设置dashview 的title
        mDashoardView.setDashTitleColor(getResources().getColor(R.color.downy)); // title 颜色
//        mDashoardView.setDashTitleSize(12); title 大小
        mDashoardView.setDasProColor(getResources().getColor(R.color.downy));// 设置进度条的宽度
//        mDashoardView.setDashProWidth(16); 设置进度条的宽度
        mDashoardView.setDashColor(getResources().getColor(R.color.white_dash));// 设置底部圆环颜色
//        mDashoardView.setDashWidth(8);    设置底部圆环的宽度
        mDashoardView.setDashProMax(60);//设置dashboard 的最大值
        mDashoardView.setDashStyle(DashBoard.NOMAL);
        if(mDashoardView.getDashStyle()== DashBoard.NOMAL){
            mDashoardView.setDashIcon(R.drawable.zz); // 设置dashboard中间的图标
        }else{
            mDashoardView.setDashIcon(R.drawable.ic_bike); // 设置dashboard中间的图标
        }

        initSensor();

    }

    private void initSensor() {
        // test sensor
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        int sensorType = Sensor.TYPE_ACCELEROMETER;
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float z = event.values[2];
                BigDecimal bigDecimal = new BigDecimal(z);
                z = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                mDashoardView.setDashProgress(z);
                mDashoardView.setDashTitle(z+" km/h");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
