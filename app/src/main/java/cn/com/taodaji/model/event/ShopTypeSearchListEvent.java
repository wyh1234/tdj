package cn.com.taodaji.model.event;

import java.util.List;

import cn.com.taodaji.model.entity.ShopTypeBean;

public class ShopTypeSearchListEvent {
    private List<ShopTypeBean> resultList;

    public List<ShopTypeBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ShopTypeBean> resultList) {
        this.resultList = resultList;
    }

    public ShopTypeSearchListEvent(List<ShopTypeBean> resultList) {
        this.resultList = resultList;
    }
}
