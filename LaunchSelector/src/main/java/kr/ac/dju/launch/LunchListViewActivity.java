package kr.ac.dju.launch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by hayan on 14. 5. 11.
 */
public class LunchListViewActivity extends Activity {
    ListView lunchListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lunchListView = (ListView) findViewById(R.id.lunch_list_view);

    }
}
