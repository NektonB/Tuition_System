package Modules;

public class ACC_Type {

    private int id;
    private String type = "";

    public void resetAll() {
        setId(0);
        setType("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
