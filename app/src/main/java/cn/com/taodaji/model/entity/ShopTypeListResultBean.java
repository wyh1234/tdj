package cn.com.taodaji.model.entity;

import java.util.List;

public class ShopTypeListResultBean {

    /**
     * err : 0
     * data : {"list":[{"children":[{"children":[{"hotelTypeId":6,"level":3,"name":"黄焖鸡米饭","parentId":4},{"hotelTypeId":7,"level":3,"name":"盖浇饭","parentId":4},{"hotelTypeId":8,"level":3,"name":"猪脚饭","parentId":4},{"hotelTypeId":9,"level":3,"name":"烤肉饭","parentId":4}],"hotelTypeId":4,"level":2,"name":"米饭","parentId":2},{"children":[{"hotelTypeId":10,"level":3,"name":"牛肉面","parentId":5},{"hotelTypeId":11,"level":3,"name":"兰州拉面","parentId":5}],"hotelTypeId":5,"level":2,"name":"面馆","parentId":2}],"hotelTypeId":2,"level":1,"name":"快餐小吃","parentId":1},{"children":[{"hotelTypeId":12,"level":2,"name":"满汉全席","parentId":3},{"hotelTypeId":13,"level":2,"name":"八仙过海","parentId":3},{"children":[{"hotelTypeId":15,"level":3,"name":"川湘菜","parentId":14},{"hotelTypeId":16,"level":3,"name":"粤菜","parentId":14},{"hotelTypeId":19,"level":3,"name":"湖北菜","parentId":14}],"hotelTypeId":14,"level":2,"name":"地方菜","parentId":3}],"hotelTypeId":3,"level":1,"name":"正餐","parentId":1},{"hotelTypeId":17,"level":1,"name":"KTV","parentId":1},{"hotelTypeId":18,"level":1,"name":"土特产","parentId":1}]}
     * msg : success
     */

    private int err;
    private DataBean data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<ShopTypeBean> list;

        public List<ShopTypeBean> getList() {
            return list;
        }

        public void setList(List<ShopTypeBean> list) {
            this.list = list;
        }


    }
}
