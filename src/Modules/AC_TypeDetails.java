package Modules;

import java.util.Vector;

public class AC_TypeDetails {

    private int id;
    private Vector<Integer> ids;
    private int[][] actd;

    public void resetAll() {
        setId(0);
        setIds(null);
        setActd(null);
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

    public int[][] getActd() {
        return actd;
    }

    public void setActd(int[][] actd) {
        this.actd = actd;
    }
}
