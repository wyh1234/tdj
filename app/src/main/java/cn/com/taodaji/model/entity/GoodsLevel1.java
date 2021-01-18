package cn.com.taodaji.model.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.viewModel.adapter.PickupOrderDetailAdapter;

public class GoodsLevel1 {

    private String name;
    private int level;
    private int id;

    private List<GoodsLevel2> level2List = new ArrayList<>();

    public List<GoodsLevel2> getLevel2List() {
        return level2List;
    }

    public void setLevel2List(List<GoodsLevel2> level2List) {
        this.level2List = level2List;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
