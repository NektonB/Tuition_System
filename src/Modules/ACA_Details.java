package Modules;

import java.util.Vector;

public class ACA_Details {

    private int id;
    private String status;
    private Vector<Integer> ids;

    public void resetAll() {
        setId(0);
        setStatus("");
        setIds(null);
    }

    public Vector<Integer> getIds() {
        return ids;
    }

    public void setIds(Vector<Integer> ids) {
        this.ids = ids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
