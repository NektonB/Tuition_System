package Modules;

import java.util.Vector;

public class AC_TypeList {

    private int id;
    private String absent_count = "";
    private Vector<Integer> ids;

    public void resetAll() {
        setId(0);
        setAbsent_count("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbsent_count() {
        return absent_count;
    }

    public void setAbsent_count(String absent_count) {
        this.absent_count = absent_count;
    }

    public Vector<Integer> getIds() {
        return ids;
    }

    public void setIds(Vector<Integer> ids) {
        this.ids = ids;
    }
}
