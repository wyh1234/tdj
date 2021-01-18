package cn.com.taodaji.model.event;

import java.util.List;

import cn.com.taodaji.model.entity.ShopTypeBean;

public class ShopTypeSelectListEvent {

    private List<ShopTypeBean> resultList;

    int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public ShopTypeSelectListEvent(List<ShopTypeBean> resultList) {
        this.resultList = resultList;
    }

    public ShopTypeSelectListEvent(List<ShopTypeBean> resultList, int flag) {
        this.resultList = resultList;
        this.flag = flag;
    }

    public List<ShopTypeBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ShopTypeBean> resultList) {
        this.resultList = resultList;
    }
}
