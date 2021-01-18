package cn.com.taodaji.model.entity;

public class FlagshipStroeInfo {
    private String name;
    private String cont;

    public FlagshipStroeInfo(String name, String cont) {
        this.name = name;
        this.cont = cont;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }
}
