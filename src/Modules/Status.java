package Modules;

public class Status {

    private int id = 0;
    private String status = "";

    public void resetAll() {
        setId(0);
        setStatus("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
