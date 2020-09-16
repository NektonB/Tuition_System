package Modules;

public class TeacherPayment {

    private int id;
    private String year;
    private String month;

    public void resetAll() {
        setId(0);
        setYear("");
        setMonth("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
