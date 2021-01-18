package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017-07-05.
 */

public class ProductFindName {

    /**
     * err : 0
     * data : {"items":[{"name":"粉条","number":1},{"name":"凉粉","number":1},{"name":"土豆","number":8},{"name":"土鸡蛋","number":4},{"name":"土鸡肉精膏","number":1},{"name":"土芹","number":1},{"name":"小土豆","number":2}]}
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
        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * name : 粉条
             * number : 1
             */

            private String name;
            private int number;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }
        }
    }
}
