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

    private EditText presetName;
    private ArrayList<EditText> foodList = new ArrayList<EditText>();

    private static final String BUNDLE_KEY_FOODS = "foods";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_preset);

        addButton = (Button) findViewById(R.id.addButton);
        okButton = (Button) findViewById(R.id.add_complete);

        presetName = (EditText) findViewById(R.id.preset_name);

        okButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        foodLinearView = (LinearLayout) findViewById(R.id.foodLinearView);

        setInterface();

        if (savedInstanceState != null) {
            restoreFoods(savedInstanceState);
        }
    }

    private void setInterface() {
        Intent intent = getIntent();

        presetName.setText(intent.getStringExtra("preset_name"));
        String[] foodArray = intent.getStringArrayExtra("food_list");

        if (presetName.length() != 0) {
            setInterfaceForUpdate(foodArray);
        } else {
            setInterfaceForInsert();
        }
    }

    private void setInterfaceForUpdate(String[] foodArray) {
        presetName.setEnabled(false);

        for (int i = 0; i < foodArray.length; i++) {
            addFoodInput(false);
            foodList.get(i).setText(foodArray[i]);
        }

        okButton.setText(R.string.preset_update_button);
    }

    private void addFoodInput(boolean requestFocus) {
        EditText foodEditText = new EditText(this);
        foodEditText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        foodList.add(foodEditText);
        foodLinearView.addView(foodEditText);

        if (requestFocus) {
            foodEditText.requestFocus();
        }
    }

    private void setInterfaceForInsert() {
        for (int i = 0; i < 2; i++) {
            addFoodInput(false);
        }
    }

    private void setFoodArrayText(String[] foodList) {
        if (foodList != null) {
            int count = 0;

            for (EditText editText : this.foodList) {
                editText.setText(foodList[count++]);
            }
        }
    }

    private void restoreFoods(Bundle savedInstanceState) {
        ArrayList<String> foods = savedInstanceState.getStringArrayList(BUNDLE_KEY_FOODS);
        for (int i = foodList.size(); i < foods.size(); i++) {
            addFoodInput(false);
        }

        for (int i = 0; i < foods.size(); i++) {
            foodList.get(i).setText(foods.get(i));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outBundle) {
        ArrayList<String> foods = makeArrayList();
        outBundle.putStringArrayList(BUNDLE_KEY_FOODS, foods);
    }

    private ArrayList<String> makeArrayList() {
        ArrayList<String> foods = new ArrayList<String>();
        for (EditText et : foodList) {
            String content = et.getText().toString().trim();
            if (content.length() > 0) {
                foods.add(content);
            }
        }
        return foods;
    }

    @Override
    public void onClick(View v) {
        if (okButton.getId() == v.getId()) {
            checkValidNameAndDBDataManipulation();
        } else if (addButton.getId() == v.getId()) {
            addFoodInput(true);
        }
    }

    private void checkValidNameAndDBDataManipulation() {
        String presetNameString = this.presetName.getText().toString();

        if (presetNameString.equals("") || presetNameString.matches("^\\s+$")) {
            ToastErrorString(R.string.prset_name_empty_error);
        } else {
            if (presetName.isEnabled() == false) {
                updatePresetDB(presetNameString);
            } else {
                insertPresetDB(presetNameString);
            }
            
            startPresetListActivity();
        }
    }

    private void updatePresetDB(String presetName) {
        LunchDbAdapter dbAdapter = new LunchDbAdapter(this);

        Preset preset = new Preset();
        ArrayList<String> foods = makeArrayList();

        preset.setName(presetName);
        preset.setElementList(makeElementList(foods));

        dbAdapter.updatePreset(preset);
    }

    private ArrayList<Element> makeElementList(ArrayList<String> foods) {
        ArrayList<Element> elements = new ArrayList<Element>();

        for (String food : foods) {
            Element element = new Element();
            element.setContent(food);
            elements.add(element);
        }

        return elements;
    }

    private void ToastErrorString(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    private void insertPresetDB(String presetName) {
        LunchDbAdapter dbAdapter = new LunchDbAdapter(this);
        Preset preset = new Preset();

        ArrayList<String> foods = makeArrayList();
        ArrayList<Element> elements = makeElementList(foods);

        preset.setName(presetName);
        preset.setElementList(elements);

        dbAdapter.createPreset(preset);
    }

    private void startPresetListActivity() {
        Intent intent = new Intent(this, PresetListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }
}
