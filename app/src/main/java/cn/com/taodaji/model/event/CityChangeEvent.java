package cn.com.taodaji.model.event;

/**
 * Created by yangkuo on 2018-09-28.
 */
public class CityChangeEvent {
    private int id;
    private String name;
    private boolean f;

    public CityChangeEvent(int id, String name,boolean f) {
        this.id = id;
        this.name = name;
        this.f=f;
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
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
