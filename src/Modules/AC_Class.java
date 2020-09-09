package Modules;

import java.util.Vector;

public class AC_Class {

    private int id;
    private Vector<Integer> ids;
    private String[][] acc;

    public void resetAll() {
        setId(0);
        setIds(null);
        setAcc(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector<Integer> getIds() {
        return ids;
    }

    public void setIds(Vector<Integer> ids) {
        this.ids = ids;
    }

    public String[][] getAcc() {
        return acc;
    }

    public void setAcc(String[][] acc) {
        this.acc = acc;
    }
}
