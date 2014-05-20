package kr.ac.dju.launch;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import kr.ac.dju.launch.db.LunchDbAdapter;
import kr.ac.dju.launch.db.Preset;

/**
 * Created by hayan on 14. 5. 11.
 */
public class PresetListActivity extends ListActivity implements View.OnClickListener {

    private Button btnAdd;

    private LunchDbAdapter dbAdapter;
    private PresetAdapter presetAdapter;
    private List<Preset> presetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_list);

        dbAdapter = new LunchDbAdapter(this);
        setListViewItem();
        InitControllerObject();

    }

    private void setListViewItem() {
        presetList = dbAdapter.fetchAllPresets();
        presetAdapter = new PresetAdapter(this, presetList);

        setListAdapter(presetAdapter);
    }

    private void invalidateListViewItem() {
        presetList.clear();
        for (Preset preset : dbAdapter.fetchAllPresets()) {
            presetList.add(preset);
        }
        presetAdapter.notifyDataSetChanged();
    }

    private void InitControllerObject() {
        btnAdd = (Button) findViewById(R.id.btn_add_preset);

        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        v.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnAdd.getId()) {
            startAddPresetActivity();
        }
    }

    private void startAddPresetActivity() {
        Intent intent = new Intent(this, EditPresetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    private class PresetAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<Preset> list;

        public PresetAdapter(Context context, List<Preset> list) {
            super();
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            Preset preset = list.get(position);
            view.setText(preset.getName());
            return view;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
    }
}
