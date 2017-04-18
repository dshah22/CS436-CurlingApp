package armcurl.armcurltest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class CurlListener implements SensorEventListener {

    float upper_threshold, lower_threshold;
    CurlManager manager;
    int score;

    public CurlListener(float upper_threshold, float lower_threshold){
        this.upper_threshold = upper_threshold;
        this.lower_threshold = lower_threshold;

        score = 0;

        manager = new CurlManager() {
            @Override
            protected void onRepIncrease() {
                score++;
            }
        };
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_GRAVITY){
            manager.updateEvent(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    public String getScoreString(){
        Integer scoreString = score;
        return scoreString.toString();
    }
}

