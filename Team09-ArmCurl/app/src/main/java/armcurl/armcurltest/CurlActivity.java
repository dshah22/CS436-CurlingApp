package armcurl.armcurltest;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CurlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curl);

        SensorManager sensorService = (SensorManager) getApplicationContext().getSystemService(getApplicationContext().SENSOR_SERVICE);
        Sensor sensor = sensorService.getDefaultSensor(Sensor.TYPE_GRAVITY);
        final CurlListener listener = new CurlListener(8.0f, 0.0f);
        sensorService.registerListener(listener, sensorService.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_FASTEST);

        new CountDownTimer(11000, 1000) {

            TextView timer = (TextView)findViewById(R.id.timer);
            public void onTick(long millisUntilFinished) {
                timer.setText("Seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                String score = listener.getScoreString();

                Intent intent = new Intent();
                intent.putExtra("score", score);
                setResult(RESULT_OK, intent);
                finish();
            }

        }.start();

    }
}

