package Modules;

public class AC_Attendance {
    private int id;
    private String date;
    private String time;

    public void resetAll() {
        setId(0);
        setDate("");
        setTime("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
