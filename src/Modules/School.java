package Modules;

public class School {
    private int id;
    private String name = "";

    public void resetAll() {
        setId(0);
        setName("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
