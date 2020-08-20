package Modules;

public class Exam {

    private int id;
    private String exam = "";

    public void resetAll(){
        setId(0);
        setExam("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }
}
