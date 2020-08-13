package Modules;

import java.util.HashMap;
import java.util.Vector;

public class TeacherHasSubject {
    private int id;
    private HashMap<Integer, String> subjectList = new HashMap<>();

    public void resetAll() {
        setId(0);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, String> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(HashMap<Integer, String> subjectList) {
        this.subjectList = subjectList;
    }
}
