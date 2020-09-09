package Modules;

import java.util.Vector;

public class AC_TypeList {

    private int id;
    private String absent_count = "";
    private Vector<Integer> ids;
    private int[][] actl;

    public void resetAll() {
        setId(0);
        setAbsent_count("");
        setIds(null);
        setActl(null);
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

    public int[][] getActl() {
        return actl;
    }

    public void setActl(int[][] actl) {
        this.actl = actl;
    }
}
