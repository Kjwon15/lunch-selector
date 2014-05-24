package kr.ac.dju.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import kr.ac.dju.launch.alarm.AlarmReceiver;

public class SplashActivity extends ActionBarActivity implements View.OnClickListener {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        AlarmReceiver.setAlarm(this);
    }

    @Override
    public void onClick(View view) {
        if (startButton.getId() == view.getId()) {
            Intent intent = new Intent(this, PresetListActivity.class);
            startActivity(intent);
        }
    }
}
