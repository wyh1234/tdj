package cn.com.taodaji.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.com.taodaji.viewModel.adapter.PickupOrderDetailAdapter;

public class GoodsLevel2 {

    private String name;
    private int level;
    private int id;
    private String pName;
    private int pid;
    private boolean selected;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
