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
import java.util.List;

import kr.ac.dju.launch.db.Element;
import kr.ac.dju.launch.db.LunchDbAdapter;
import kr.ac.dju.launch.db.Preset;

public class EditPresetActivity extends ActionBarActivity implements View.OnClickListener {
    private Button addButton = null;
    private Button okButton = null;

    private LinearLayout foodLinearView = null;

    private EditText presetName;
    private ArrayList<EditText> foodList = new ArrayList<EditText>();
    private LunchDbAdapter dbAdapter;
    private Preset preset;

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

        dbAdapter = new LunchDbAdapter(this);

        setInterface();

        if (savedInstanceState != null) {
            restoreFoods(savedInstanceState);
        }
    }

    private void setInterface() {
        Intent intent = getIntent();
        long presetRowId = intent.getLongExtra(C.EXTRA_PRESET_ROWID, -1);
        preset = dbAdapter.getPreset(presetRowId);

        if (preset != null) {
            presetName.setText(preset.getName());
            okButton.setText(R.string.preset_update_button);

            for (Element element : preset.getElementList()) {
                EditText edit = addFoodInput(true);
                edit.setText(element.getContent());
            }
        } else {
            preset = new Preset();
            for (int i = 0; i < 2; i++) {
                addFoodInput(false);
            }
        }
    }

    private EditText addFoodInput(boolean requestFocus) {
        EditText foodEditText = new EditText(this);
        foodEditText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        foodList.add(foodEditText);
        foodLinearView.addView(foodEditText);

        if (requestFocus) {
            foodEditText.requestFocus();
        }

        return foodEditText;
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

        if (presetNameString.trim().equals("")) {
            ToastErrorString(R.string.prset_name_empty_error);
        } else {
            preset.setName(presetNameString);
            preset.setElementList(makeElementList());
            dbAdapter.updatePreset(preset);

            finish();
        }
    }

    private List<Element> makeElementList() {
        ArrayList<Element> elements = new ArrayList<Element>();

        for (String food : makeArrayList()) {
            Element element = new Element();
            element.setContent(food);
            elements.add(element);
        }

        return elements;
    }

    private void ToastErrorString(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}
