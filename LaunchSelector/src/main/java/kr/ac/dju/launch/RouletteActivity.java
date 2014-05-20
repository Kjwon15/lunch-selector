package kr.ac.dju.launch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kr.ac.dju.launch.db.Element;
import kr.ac.dju.launch.db.LunchDbAdapter;
import kr.ac.dju.launch.db.Preset;

public class RouletteActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView rouletteTitleView;
    private TextView rouletteView;
    private Button restartButton;

    private Timer rouletteScheduler;
    Preset foods;
    LunchDbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);

        dbAdapter = new LunchDbAdapter(this);

        rouletteTitleView = (TextView) findViewById(R.id.rouletteTitleView);
        rouletteView = (TextView) findViewById(R.id.rouletteView);
        restartButton = (Button) findViewById(R.id.restartButton);

        rouletteView.setOnClickListener(this);
        restartButton.setOnClickListener(this);


        long rowid = getIntent().getLongExtra(C.EXTRA_PRESET_ROWID, -1);
        foods = dbAdapter.getPreset(rowid);

        setRouletteTitle();

        startRoulette();
    }

    private void setRouletteTitle() {
        String title = MessageFormat.format(
                getString(R.string.text_title_roulette),
                foods.getName()
        );
        rouletteTitleView.setText(title);
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

        private Preset preset;
        private int index = 0;

        public RouletteRotator(Preset preset) {
            this.preset = preset;
        }

        @Override
        public void run() {
            final List<Element> list = preset.getElementList();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rouletteView.setText(list.get(index).getContent());
                }
            });
            index = (index + 1) % list.size();
        }
    }

}
