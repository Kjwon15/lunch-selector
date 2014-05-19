package kr.ac.dju.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import kr.ac.dju.launch.db.Element;
import kr.ac.dju.launch.db.LunchDbAdapter;
import kr.ac.dju.launch.db.Preset;

public class EditPresetActivity extends ActionBarActivity implements View.OnClickListener {
    private Button addButton = null;
    private Button okButton = null;

    private LinearLayout foodLinearView = null;

    private EditText presetNameEdit;
    private ArrayList<EditText> foodArray = new ArrayList<EditText>();

    private static final String BUNDLE_KEY_FOODS = "foods";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_preset);

        addButton = (Button) findViewById(R.id.addButton);
        okButton = (Button) findViewById(R.id.add_complete);

        presetNameEdit = (EditText) findViewById(R.id.preset_name);

        okButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        foodLinearView = (LinearLayout) findViewById(R.id.foodLinearView);

        Intent intent = getIntent();

        String[] foodList = intent.getStringArrayExtra("food_list");
        int editTextCount = foodList != null ? foodList.length : 2;

        for (int i = 0; i < editTextCount; i++) {
            addFoodInput(false);
        }

        setFoodArrayDefaultText(foodList);

        if (savedInstanceState != null) {
            restoreFoods(savedInstanceState);
        }
    }

    private void setFoodArrayDefaultText(String[] foodList) {
        if (foodList != null) {
            int count = 0;

            for (EditText editText : foodArray) {
                editText.setText(foodList[count++]);
            }
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

            checkValidNameAndInsertDB();
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

    private void checkValidNameAndInsertDB() {
        String presetName = presetNameEdit.getText().toString();

        if (presetName.equals("") || presetName.matches("^\\s+$")) {
            ToastErrorString(R.string.prset_name_empty_error);
        } else {
            insertPresetDB(presetName);
        }
    }

    private void insertPresetDB(String presetName) {
        LunchDbAdapter dbAdapter = new LunchDbAdapter(this);
        Preset preset = new Preset();

        ArrayList<Element> elements = new ArrayList<Element>();
        ArrayList<String> foods = makeArrayList();

        preset.setName(presetName);

        for (String food : foods) {
            Element element = new Element();
            elements.add(element);
        }

        preset.setElementList(elements);
        dbAdapter.createPreset(preset);
    }

    private void ToastErrorString(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}
