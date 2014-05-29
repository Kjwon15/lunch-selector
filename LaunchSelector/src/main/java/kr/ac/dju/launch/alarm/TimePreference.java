package kr.ac.dju.launch.alarm;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by kjwon15 on 2014. 5. 29..
 */
public class TimePreference extends DialogPreference {
    TimePicker timePicker;

    public TimePreference(Context context, AttributeSet attributes) {
        super(context, attributes);
        setPersistent(false);
    }

    @Override
    protected View onCreateDialogView() {
        timePicker = new TimePicker(getContext());
        timePicker.setIs24HourView(true);
        return timePicker;
    }
}
