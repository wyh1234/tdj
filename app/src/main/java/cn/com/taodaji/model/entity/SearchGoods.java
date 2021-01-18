package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class SearchGoods {

    /**
     * name : 白醋((保宁牌)
     * items : [{"entityId":356,"createTime":1467976514000,"name":"白醋((保宁牌)","commodityId":124,"store":5,"storeName":"老郑调料店","unit":"瓶","price":1.8,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/160708191514e90b5275.jpeg","stock":999997,"qrCodeId":"","categoryId":"26"}]
     */

    private String name;
    //  private List<ItemsBean> items;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

/*
    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }
*/


}
