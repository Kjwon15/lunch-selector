package kr.ac.dju.launch;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kr.ac.dju.launch.db.LunchDbAdapter;
import kr.ac.dju.launch.db.Preset;

/**
 * Created by hayan on 14. 5. 11.
 */
public class PresetListActivity extends ListActivity implements OnClickListener, OnItemClickListener, OnItemLongClickListener {

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

    @Override
    protected void onResume() {
        super.onResume();
        invalidateListViewItem();
    }

    private void setListViewItem() {
        presetList = dbAdapter.fetchAllPresets();
        presetAdapter = new PresetAdapter(this, presetList);

        setListAdapter(presetAdapter);
        ListView listView = getListView();
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Preset selectedPreset = (Preset) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, RouletteActivity.class);
        long rowid = selectedPreset.getRowId();
        intent.putExtra(C.EXTRA_PRESET_ROWID, rowid);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String[] items = new String[] {
                getString(R.string.action_modify),
                getString(R.string.action_delete),
        };
        final Preset preset = (Preset) parent.getAdapter().getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(getApplicationContext(), EditPresetActivity.class);
                    intent.putExtra(C.EXTRA_PRESET_ROWID, preset.getRowId());
                    startActivity(intent);
                } else if (which == 1) {
                    dbAdapter.deletePreset(preset);
                }
            }
        });
        builder.setTitle(preset.getName());
        builder.create().show();
        return false;
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
