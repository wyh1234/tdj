package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017-07-05.
 */

public class StoreFindName {

    /**
     * err : 0
     * data : {"items":[{"id":102,"name":"陈氏蔬菜批发"},{"id":97,"name":"汤氏蔬菜"},{"id":89,"name":"天天鲜特价蔬菜商行"},{"id":81,"name":"聂氏蔬菜酒店专供"},{"id":80,"name":"惠鲜蔬菜"},{"id":68,"name":"潘氏蔬菜店"},{"id":57,"name":"襄阳荣信诚樊三娃蔬菜"},{"id":54,"name":"晋氏蔬菜"},{"id":16,"name":"杨氏蔬菜"}],"pn":1,"ps":20,"total":9}
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
        /**
         * items : [{"id":102,"name":"陈氏蔬菜批发"},{"id":97,"name":"汤氏蔬菜"},{"id":89,"name":"天天鲜特价蔬菜商行"},{"id":81,"name":"聂氏蔬菜酒店专供"},{"id":80,"name":"惠鲜蔬菜"},{"id":68,"name":"潘氏蔬菜店"},{"id":57,"name":"襄阳荣信诚樊三娃蔬菜"},{"id":54,"name":"晋氏蔬菜"},{"id":16,"name":"杨氏蔬菜"}]
         * pn : 1
         * ps : 20
         * total : 9
         */

        private int pn;
        private int ps;
        private int total;
        private List<ItemsBean> items;

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 102
             * name : 陈氏蔬菜批发
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
