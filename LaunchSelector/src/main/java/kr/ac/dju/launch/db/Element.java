package kr.ac.dju.launch.db;

/**
 * Created by kjwon15 on 2014. 5. 4..
 */
public class Element {
    private long rowId;
    private long presetId;
    private String content;

    public Element(long rowId, long presetId, String content) {
        this.rowId = rowId;
        this.presetId = presetId;
        this.content = content;
    }

    public long getRowId() {
        return rowId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
