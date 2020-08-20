package Modules;

public class Stream {

    private int id;
    private String stream = "";

    public void resetAll() {
        setId(0);
        setStream("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}
