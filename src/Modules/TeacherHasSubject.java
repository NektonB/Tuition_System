package Modules;

import java.util.Vector;

public class TeacherHasSubject {
    private int id;
    private Vector<Subject> subjectList = new Vector<>();

    public void resetAll() {
        setId(0);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(Vector<Subject> subjectList) {
        this.subjectList = subjectList;
    }
}
