package kr.ac.dju.launch.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kjwon15 on 2014. 5. 4..
 */
public class Preset {
    private long rowId = -1;
    private String name;
    private List<Element> elementList;

    public Preset() {
        this.elementList = new ArrayList<Element>();
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

    public void setName(String name) {
        this.name = name;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public void setElementList(List<Element> list) {
        this.elementList = list;
    }
}
