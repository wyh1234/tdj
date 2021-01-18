package cn.com.taodaji.model.event;

/**
 * Created by Administrator on 2017/2/28 0028.
 */
public class OrderPrintSettingEvent {
    private String name;
    private String adress;

    public OrderPrintSettingEvent(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
