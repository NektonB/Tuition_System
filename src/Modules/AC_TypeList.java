package Modules;

public class AC_TypeList {

    private int id;
    private String absent_count = "";

    public void resetAll(){
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
}
