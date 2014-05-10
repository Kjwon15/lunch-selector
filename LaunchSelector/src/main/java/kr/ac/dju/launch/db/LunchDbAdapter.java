package kr.ac.dju.launch.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kjwon15 on 2014. 5. 4..
 */
public class LunchDbAdapter {

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "lunch_preset.db";

    private final static String TABLE_PRESETS = "presets";
    private final static String TABLE_ELEMENTS = "presets";
    private final static String KEY_ID = "rowid";
    private final static String KEY_NAME = "name";
    private final static String KEY_PRESET_ID = "preset_id";
    private final static String KEY_CONTENT = "content";

    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;


    public LunchDbAdapter(Context context) {
        this.context = context;
        open();
    }

    private void open() {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createPreset(Preset preset) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, preset.getName());
        long id = db.insert(TABLE_PRESETS, null, values);
        preset.setRowId(id);
        for (Element element : preset.getElementList()) {
            element.setPresetId(id);
        }
        return id;
    }

    public long createElement(Element element) {
        ContentValues values = new ContentValues();
        values.put(KEY_PRESET_ID, element.getPresetId());
        values.put(KEY_CONTENT, element.getContent());
        long id = db.insert(TABLE_ELEMENTS, null, values);
        element.setRowId(id);
        return id;
    }

    public boolean deletePreset(Preset preset) {
        long rowid = preset.getRowId();
        db.delete(TABLE_ELEMENTS, KEY_PRESET_ID + "=?",
                new String[]{String.valueOf(rowid)}
        );

        int result = db.delete(TABLE_PRESETS, KEY_ID + "=?",
                new String[]{String.valueOf(rowid)}
        );

        return result > 0;
    }

    public boolean deleteElement(Element element) {
        long rowid = element.getRowId();
        int result = db.delete(TABLE_ELEMENTS, KEY_ID + "=?",
                new String[]{String.valueOf(rowid)}
        );

        return result > 0;
    }

    public boolean updatePreset(Preset preset) {
        long rowid = preset.getRowId();

        //FIXME: delete unlinked Element with this Preset.
        for (Element element : preset.getElementList()) {
            element.setPresetId(rowid);
            boolean succeed = updateElement(element);
            if (! succeed) {
                createElement(element);
            }
        }

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, preset.getName());
        long result = db.update(TABLE_PRESETS, values, KEY_ID + "=?",
                new String[]{String.valueOf(rowid)}
        );

        return result > 0;
    }

    public boolean updateElement(Element element) {
        long rowid = element.getRowId();

        ContentValues values = new ContentValues();
        values.put(KEY_PRESET_ID, element.getPresetId());
        values.put(KEY_CONTENT, element.getContent());
        long result = db.update(TABLE_ELEMENTS, values, KEY_ID + "=?",
                new String[]{String.valueOf(rowid)}
        );

        return result > 0;
    }

    public List<Preset> fetchAllPresets() {
        List<Preset> list = new ArrayList<Preset>();
        Cursor cursor = db.query(TABLE_PRESETS, new String[]{KEY_ID, KEY_NAME},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            long rowid = cursor.getLong(cursor.getColumnIndex(KEY_ID));
            String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            List<Element> elements = fetchAllElements(rowid);
            Preset preset = new Preset(rowid, name, elements);
            list.add(preset);
        }

        return list;
    }

    public List<Element> fetchAllElements(long rowId) {
        List<Element> list = new ArrayList<Element>();
        Cursor cursor = db.query(TABLE_ELEMENTS, new String[]{KEY_ID, KEY_PRESET_ID, KEY_CONTENT},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            long rowid = cursor.getLong(cursor.getColumnIndex(KEY_ID));
            long presetId = cursor.getLong(cursor.getColumnIndex(KEY_PRESET_ID));
            String content = cursor.getString(cursor.getColumnIndex(KEY_CONTENT));
            Element element = new Element(rowid, presetId, content);

            list.add(element);
        }

        return list;
    }


    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query;
            query = MessageFormat.format("create table {0} (" +
                    "{1} varchar not null" +
                    ");",
                    TABLE_PRESETS,
                    KEY_NAME
            );
            db.execSQL(query);

            query = MessageFormat.format("create table {0}(" +
                    "{1} integer not null," +
                    "{2} varchar not null," +
                    "foreign key ({1}) references {3} ({4})" +
                    ");",
                    TABLE_ELEMENTS,
                    KEY_PRESET_ID, KEY_CONTENT,
                    TABLE_PRESETS, KEY_ID
            );

            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
