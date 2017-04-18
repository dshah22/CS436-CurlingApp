package armcurl.armcurltest;


import android.hardware.SensorEvent;
import static armcurl.armcurltest.CurlManager.Position.EXTEND;
import static armcurl.armcurltest.CurlManager.Position.FLEX;
import static armcurl.armcurltest.CurlManager.Position.MIDDLE;

public abstract class CurlManager {
    protected abstract void onRepIncrease();

    public enum Position {
        EXTEND, MIDDLE, FLEX
    }

    Position arm = EXTEND;
    boolean state = false;

    public void updateEvent(SensorEvent event){
        float value = currentPos(event);

        if (value > 8){
            arm = EXTEND;
        } else if (value <= 8 && value >= -8) {
            arm = MIDDLE;
        } else {
            arm = FLEX;
        }

        // Arm has FLEXed already and is now extended
        if (state && (arm == EXTEND)){
            onRepIncrease();
            state = false;
        }
        // Arm just flexed
        else if (!state && (arm == FLEX)) {
            state = true;
        }
    }

    private float currentPos(SensorEvent event){
        float y = event.values[2];

        return y;
    }

}

