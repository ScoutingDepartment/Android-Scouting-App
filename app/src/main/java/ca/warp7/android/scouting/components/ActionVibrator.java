package ca.warp7.android.scouting.components;

import android.content.Context;
import android.os.Vibrator;

import ca.warp7.android.scouting.interfaces.AbstractActionVibrator;

public class ActionVibrator implements AbstractActionVibrator {
    private Vibrator mActualVibrator;
    private boolean mVibrationOn;

    public ActionVibrator(Context context, boolean vibrationOn) {
        mActualVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mVibrationOn = vibrationOn;
    }

    @Override
    public void vibrateStart() {
        if (mVibrationOn) {
            mActualVibrator.vibrate(kStartVibration, -1);
        }
    }

    @Override
    public void vibrateAction() {
        if (mVibrationOn) {
            mActualVibrator.vibrate(kActionEffectVibration);
        }
    }

    private static final long[] kStartVibration = new long[]{0, 20, 30, 20};
    private static final int kActionEffectVibration = 30;
}
