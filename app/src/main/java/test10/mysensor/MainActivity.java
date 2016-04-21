package test10.mysensor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float mScore=0;
    private int mCount=0;
    private Button startButton;
    private Button resultButton;
    private TextView mTimeText;
    private static final int MESSAGE_COUNT=0;
    public static final String SCORE="score";
    public static final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        startButton=(Button)findViewById(R.id.start_button);
        resultButton=(Button)findViewById(R.id.result_button);
        startButton.setOnClickListener(this);
        resultButton.setOnClickListener(this);

        mTimeText=(TextView)findViewById(R.id.number);

    }


    private static class MyHandler extends Handler{
        private WeakReference<MainActivity> mainActivityWeakReference;

        public MyHandler(MainActivity activity){
            mainActivityWeakReference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity= mainActivityWeakReference.get();
            super.handleMessage(msg);
            if(activity!=null){
                if(activity.mCount<100){
                    activity.mCount+=1;
                    activity.mTimeText.setText(String.valueOf(activity.mCount*1.0/10));
                    sendEmptyMessageDelayed(MESSAGE_COUNT,100);
                }else {
                    if(activity.mSensorManager != null){
                        activity.mSensorManager.unregisterListener(activity.listener);
                    }
                    activity.resultButton.setVisibility(View.VISIBLE);
                    removeMessages(MESSAGE_COUNT);
                }
            }
        }
    }

    private MyHandler myHandler = new MyHandler(this);

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue = Math.abs(event.values[0]);
            if(xValue>15&&xValue<=17){
                mScore+=1;
                Log.i(TAG,String.valueOf(xValue));
            }else if(xValue>17&&xValue<=19){
                mScore+=2;
                Log.i(TAG,String.valueOf(xValue));
            }else if(xValue>19&&xValue<=19.5){
                mScore+=3;
                Log.i(TAG,String.valueOf(xValue));
            }else if(xValue>19.5&&xValue<=19.9){
                mScore+=4.5;
                Log.i(TAG,String.valueOf(xValue));
            }else  if(xValue>19.9){
                mScore+=6;
                Log.i(TAG,String.valueOf(xValue));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_button:
                myHandler.sendEmptyMessage(MESSAGE_COUNT);
                mSensorManager.registerListener(listener, mSensor, SensorManager.SENSOR_DELAY_GAME);
                startButton.setVisibility(View.GONE);
                break;
            case R.id.result_button:
                Intent intent = new Intent(MainActivity.this,ScoreActivity.class);
                intent.putExtra(SCORE,mScore);
                startActivity(intent);
                startButton.setVisibility(View.VISIBLE);
                resultButton.setVisibility(View.GONE);
                break;
            default:
                break;

        }
    }
}
