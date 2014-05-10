package kr.ac.dju.launch.db;

import java.util.List;

/**
 * Created by kjwon15 on 2014. 5. 4..
 */
public class Preset {
    private long rowId;
    private String name;
    private List<Element> elementList;

    public Preset(long rowId, String name, List<Element> elements) {
        this.rowId = rowId;
        this.name = name;
        this.elementList = elements;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getName() {
        return name;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public void setName(String name) {
        this.name = name;
    }
}
