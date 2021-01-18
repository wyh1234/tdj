package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.util.List;

public class ShopTypeBean implements Serializable {
    private boolean check;


    /**
     * children : []
     * hotelTypeId : 2
     * level : 1
     * name : 快餐小吃
     * parentId : 1
     */

    private int hotelTypeId;
    private int level;
    private String name;
    private int parentId;
    private List<ShopTypeBean> children;

    private boolean add;
    private boolean single;

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getHotelTypeId() {
        return hotelTypeId;
    }

    public void setHotelTypeId(int hotelTypeId) {
        this.hotelTypeId = hotelTypeId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<ShopTypeBean> getChildren() {
        return children;
    }

    public void setChildren(List<ShopTypeBean> children) {
        this.children = children;
    }
}
