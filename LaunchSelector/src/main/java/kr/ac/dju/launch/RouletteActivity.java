package kr.ac.dju.launch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kr.ac.dju.launch.db.Element;
import kr.ac.dju.launch.db.LunchDbAdapter;
import kr.ac.dju.launch.db.Preset;

public class RouletteActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView rouletteView;
    private Button restartButton;

    private Timer rouletteScheduler;
    List<Element> foods;
    LunchDbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);

        dbAdapter = new LunchDbAdapter(this);

        rouletteView = (TextView) findViewById(R.id.rouletteView);
        restartButton = (Button) findViewById(R.id.restartButton);

        rouletteView.setOnClickListener(this);
        restartButton.setOnClickListener(this);


        long rowid = getIntent().getLongExtra(C.EXTRA_PRESET_ROWID, -1);
        foods = dbAdapter.fetchAllElements(rowid);

        startRoulette();
    }

    private void startRoulette() {
        restartButton.setVisibility(View.INVISIBLE);
        rouletteScheduler = new Timer();
        RouletteRotator rotator = new RouletteRotator(foods);
        rouletteScheduler.scheduleAtFixedRate(rotator, 0, 10);
    }

    private void stopRoulette() {
        restartButton.setVisibility(View.VISIBLE);
        rouletteScheduler.cancel();
    }

    @Override
    public void onClick(View view) {
        if (rouletteView.getId() == view.getId()) {
            stopRoulette();
        } else if (restartButton.getId() == view.getId()) {
            startRoulette();
        }
    }

    private class RouletteRotator extends TimerTask {

        private List<Element> items;
        private int index = 0;

        public RouletteRotator(List<Element> items) {
            this.items = items;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rouletteView.setText(items.get(index).getContent());
                }
            });
            index = (index + 1) % items.size();
        }
    }

}
