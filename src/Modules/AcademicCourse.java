package Modules;

public class AcademicCourse {

    private int id;
    private String exam_year = "";

    public void resetAll() {
        setId(0);
        setExam_year("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
    }
}
