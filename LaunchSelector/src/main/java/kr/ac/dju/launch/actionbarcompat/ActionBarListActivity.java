package kr.ac.dju.launch.actionbarcompat;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by kjwon15 on 2014. 5. 21..
 */
public abstract class ActionBarListActivity extends ActionBarActivity {
    private ListView listView;
    private View emptyView;

    protected ListView getListView() {
        if (listView == null) {
            listView = (ListView) findViewById(android.R.id.list);
            emptyView = (View) findViewById(android.R.id.empty);
            listView.setEmptyView(emptyView);
        }
        return listView;
    }

    protected void setListAdapter(ListAdapter adapter) {
        getListView().setAdapter(adapter);
    }

    protected ListAdapter getListAdapter() {
        ListAdapter adapter = getListView().getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter)adapter).getWrappedAdapter();
        }
        return adapter;
    }

    protected void onListItemClick(ListView lv, View v, int position, long id) {
        getListView().getOnItemClickListener().onItemClick(lv, v, position, id);
    }

    protected void onListItemLongClick(ListView lv, View v, int position, long id) {
        getListView().getOnItemLongClickListener().onItemLongClick(lv, v, position, id);
    }
}
