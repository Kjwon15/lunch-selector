package kr.ac.dju.launch;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import kr.ac.dju.launch.db.LunchDbAdapter;
import kr.ac.dju.launch.db.Preset;

/**
 * Created by hayan on 14. 5. 11.
 */
public class PresetListActivity extends ListActivity implements View.OnClickListener {

    private Button btnOk;
    private Button btnCancel;

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
        btnOk = (Button) findViewById(R.id.select_ok);
        btnCancel = (Button) findViewById(R.id.select_cancel);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO : event process for buttons
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
