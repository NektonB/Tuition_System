package Modules;

import java.util.Vector;

public class AC_TypeDetails {

    private int id;
    private Vector<Integer> ids;

    public void resetAll(){
        setId(0);
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
}
