package Modules;

public class TP_Details {

    private int id;
    private String date;
    private String time;
    private double payAmount;

    public void resetAll() {
        setId(0);
        setDate("");
        setTime("");
        setPayAmount(0);
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

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }
}
