package kr.ac.dju.launch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RouletteActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView rouletteView;
    private Button stopButton;

    private Timer rouletteScheduler = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);

        rouletteView = (TextView) findViewById(R.id.rouletteView);
        stopButton = (Button) findViewById(R.id.stopButton);

        stopButton.setOnClickListener(this);


        ArrayList<String> foods = getIntent().getStringArrayListExtra(C.EXTRA_NAME_FOODS);
        RouletteRotator rotator = new RouletteRotator(foods);

        rouletteScheduler.scheduleAtFixedRate(rotator, 0, 10);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.roulette, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (stopButton.getId() == view.getId()) {
            rouletteScheduler.cancel();
        }
    }

    private class RouletteRotator extends TimerTask {

        private ArrayList<String> items;
        private int index = 0;

        public RouletteRotator(ArrayList<String> items) {
            this.items = items;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rouletteView.setText(items.get(index));
                }
            });
            index = (index + 1) % items.size();
        }
    }

}
