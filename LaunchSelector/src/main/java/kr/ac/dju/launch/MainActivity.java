package kr.ac.dju.launch;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    Button addButton = null;
    Button okButton = null;

    LinearLayout foodLinearView = null;

    ArrayList<EditText> foodArray = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);
        okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        foodLinearView = (LinearLayout) findViewById(R.id.foodLinearView);

        for (int i = 0; i < 2; i++) {
            addFoodInput();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            okButton.setVisibility(View.INVISIBLE);
            addButton.setVisibility(View.INVISIBLE);

        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            okButton.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        if (okButton.getId() == v.getId()) {

        } else if (addButton.getId() == v.getId()) {
            addFoodInput();
        }
    }

    private void addFoodInput() {
        EditText foodEditText = new EditText(this);

        foodEditText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        foodArray.add(foodEditText);
        foodLinearView.addView(foodEditText);
    }
}
