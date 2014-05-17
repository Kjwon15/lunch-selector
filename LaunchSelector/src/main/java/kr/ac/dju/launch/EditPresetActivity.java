package kr.ac.dju.launch;

import android.content.Intent;
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

public class EditPresetActivity extends ActionBarActivity implements View.OnClickListener {
    Button addButton = null;
    Button okButton = null;

    LinearLayout foodLinearView = null;

    EditText nameEdit;
    ArrayList<EditText> foodArray = new ArrayList<EditText>();

    private static final String BUNDLE_KEY_FOODS = "foods";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_preset);

        nameEdit = (EditText) findViewById(R.id.nameEdit);
        addButton = (Button) findViewById(R.id.addButton);
        okButton = (Button) findViewById(R.id.add_complete);

        okButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        foodLinearView = (LinearLayout) findViewById(R.id.foodLinearView);

        for (int i = 0; i < 2; i++) {
            addFoodInput(false);
        }

        if (savedInstanceState != null) {
            restoreFoods(savedInstanceState);
        }
    }

    private void restoreFoods(Bundle savedInstanceState) {
        ArrayList<String> foods = savedInstanceState.getStringArrayList(BUNDLE_KEY_FOODS);
        for (int i = foodArray.size(); i < foods.size(); i++) {
            addFoodInput(false);
        }

        for (int i = 0; i < foods.size(); i++) {
            foodArray.get(i).setText(foods.get(i));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outBundle) {
        ArrayList<String> foods = makeArrayList();
        outBundle.putStringArrayList(BUNDLE_KEY_FOODS, foods);
    }

    @Override
    public void onClick(View v) {

        if (okButton.getId() == v.getId()) {
            ArrayList<String> foods = makeArrayList();

            Intent intent = new Intent(this, RouletteActivity.class);

            intent.putStringArrayListExtra(C.EXTRA_NAME_FOODS, foods);
            startActivity(intent);
        } else if (addButton.getId() == v.getId()) {
            addFoodInput(true);
        }
    }

    private ArrayList<String> makeArrayList() {
        ArrayList<String> foods = new ArrayList<String>();
        for (EditText et : foodArray) {
            String content = et.getText().toString().trim();
            if (content.length() > 0) {
                foods.add(content);
            }
        }
        return foods;
    }

    private void addFoodInput(boolean requestFocus) {
        EditText foodEditText = new EditText(this);

        foodEditText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        foodArray.add(foodEditText);
        foodLinearView.addView(foodEditText);

        if (requestFocus) {
            foodEditText.requestFocus();
        }
    }
}
