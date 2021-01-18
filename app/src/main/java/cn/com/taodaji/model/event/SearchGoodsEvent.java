package cn.com.taodaji.model.event;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.model.entity.GoodsInformation;

public class SearchGoodsEvent {
    private List<GoodsInformation> list=new ArrayList<>();

    public List<GoodsInformation> getList() {
        return list;
    }

    public void setList(List<GoodsInformation> list) {
        this.list.addAll(list);
    }
}
