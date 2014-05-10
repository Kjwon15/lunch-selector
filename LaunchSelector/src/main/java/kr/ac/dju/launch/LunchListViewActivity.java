package kr.ac.dju.launch;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.ac.dju.launch.db.LunchDbAdapter;
import kr.ac.dju.launch.db.Preset;

/**
 * Created by hayan on 14. 5. 11.
 */
public class LunchListViewActivity extends ListActivity implements View.OnClickListener {
    private static final String KEY_PRESET = "preset";

    ListView lunchListView = null;
    Button btnOk = null;
    Button btnCancel = null;

    List<HashMap<String, String>> lunchList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitControllerObject();
        setLunchList();

        setInterfaceActivity();
    }

    private void setInterfaceActivity() {
        if (lunchList.isEmpty()) {
            hideListViewAndButtonOk();
            showTextListEmpty();
        } else {
            setListViewItem();
        }
    }

    private void setListViewItem() {
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                lunchList,
                R.layout.activity_lunch_list_view,
                new String[]{KEY_PRESET},
                new int[]{android.R.id.text1}
        );

        setListAdapter(simpleAdapter);
    }

    private void showTextListEmpty() {
        TextView noListTextView = new TextView(this);
        noListTextView.setText(R.string.list_is_empty);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lunch_list_view_activity);
        linearLayout.addView(noListTextView);
    }

    private void InitControllerObject() {
        lunchListView = (ListView) findViewById(R.id.lunch_list_view);
        btnOk = (Button) findViewById(R.id.select_ok);
        btnCancel = (Button) findViewById(R.id.select_cancel);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private List<Preset> getLunchList() {
        LunchDbAdapter lunchDbAdapter = new LunchDbAdapter(this);
        lunchDbAdapter.open();

        List<Preset> presets = lunchDbAdapter.fetchAllPresets();

        lunchDbAdapter.close();
        return presets;
    }

    private void setLunchList() {
        List<Preset> presets = getLunchList();

        for (Preset preset : presets) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put(KEY_PRESET, preset.getName());

            lunchList.add(hashMap);
        }
    }

    private void hideListViewAndButtonOk() {
        lunchListView.setVisibility(View.INVISIBLE);
        btnOk.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        // TODO : event process for buttons
    }
}
