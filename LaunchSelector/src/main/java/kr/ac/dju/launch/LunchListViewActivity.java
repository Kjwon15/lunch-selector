package kr.ac.dju.launch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kr.ac.dju.launch.db.Preset;

/**
 * Created by hayan on 14. 5. 11.
 */
public class LunchListViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
