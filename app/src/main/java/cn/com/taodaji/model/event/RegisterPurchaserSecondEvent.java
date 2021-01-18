package cn.com.taodaji.model.event;

import java.util.List;
import java.util.Map;

import cn.com.taodaji.model.entity.ShopTypeBean;

public class RegisterPurchaserSecondEvent {
    private Map<String, Object> values_map;
    private List<ShopTypeBean> selectedList;
    private int process;

    public RegisterPurchaserSecondEvent(Map<String, Object> values_map, List<ShopTypeBean> selectedList,int process) {
        this.values_map = values_map;
        this.selectedList = selectedList;
        this.process=process;
    }

    public Map<String, Object> getValues_map() {
        return values_map;
    }

    public void setValues_map(Map<String, Object> values_map) {
        this.values_map = values_map;
    }

    public List<ShopTypeBean> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<ShopTypeBean> selectedList) {
        this.selectedList = selectedList;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }
}
