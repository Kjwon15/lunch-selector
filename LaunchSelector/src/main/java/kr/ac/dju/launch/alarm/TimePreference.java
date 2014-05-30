package kr.ac.dju.launch.alarm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by kjwon15 on 2014. 5. 29..
 */
public class TimePreference extends DialogPreference {
    TimePicker timePicker;

    private static final int DEFAULT_HOUR = 7;
    private static final int DEFAULT_MINUTE = 30;

    public TimePreference(Context context, AttributeSet attributes) {
        super(context, attributes);
        setPersistent(false);
    }

    @Override
    public void onClick(DialogInterface dialog, int button) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(getKey() + ".hour", timePicker.getCurrentHour());
        editor.putInt(getKey() + ".minute", timePicker.getCurrentMinute());
        editor.commit();
    }

    @Override
    protected View onCreateDialogView() {
        timePicker = new TimePicker(getContext());
        timePicker.setIs24HourView(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        timePicker.setCurrentHour(prefs.getInt(getKey() + ".hour", DEFAULT_HOUR));
        timePicker.setCurrentMinute(prefs.getInt(getKey() + ".minute", DEFAULT_MINUTE));
        return timePicker;
    }
}
