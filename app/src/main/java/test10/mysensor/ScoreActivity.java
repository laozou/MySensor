package test10.mysensor;

import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 123 on 2016/3/24.
 */
public class ScoreActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mScoreText;
    private TextView mResultText;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);

        mScoreText = (TextView) findViewById(R.id.score_number);
        mResultText= (TextView) findViewById(R.id.result_text);
        mImageView= (ImageView) findViewById(R.id.result_imageView);

        Button backButton = (Button) findViewById(R.id.return_button);

        backButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent =getIntent();
        RefreshData(intent);
    }

    private void RefreshData(Intent intent) {
        float scoredata = intent.getFloatExtra(MainActivity.SCORE,0);


        mScoreText.setText(String.valueOf(scoredata));

        if(scoredata>1500&&scoredata<=1600){
            mResultText.setText("弱爆了");
            mImageView.setBackgroundResource(R.drawable.xiaolizi);
        }else if(scoredata>1600&&scoredata<=1650){
            mResultText.setText("兄弟，再用力点啊");
            mImageView.setBackgroundResource(R.drawable.yaoming);
        }else if(scoredata>1650&&scoredata<=1680){
            mResultText.setText("正常人");
            mImageView.setBackgroundResource(R.drawable.pikaqiu);
        }else if(scoredata>1680&&scoredata<=1700){
            mResultText.setText("大力出奇迹");
            mImageView.setBackgroundResource(R.drawable.dalige);
        }else if(scoredata>1700){
            mResultText.setText("哲学家");
            mImageView.setBackgroundResource(R.drawable.biliwang);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_button:
                Intent intent =new Intent(ScoreActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
