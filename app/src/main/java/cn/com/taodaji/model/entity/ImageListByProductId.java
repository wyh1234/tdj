package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21 0021.
 */
public class ImageListByProductId {

    /**
     * err : 0
     * data : [{"create_time":"2017-03-16","items":"http://www.sina.com.cn/aa.jpg"},{"create_time":"2017-03-21","items":"http://www.sina.com.cn/aa.jpg,http://www.sina.com.cn/aa.jpg"},{"create_time":"2017-03-22","items":"http://www.sina.com.cn/aa.jpg"},{"create_time":"2017-03-23","items":"http://www.sina.com.cn/aa.jpg"}]
     * msg : success
     */

    private int err;
    private String msg;
    private List<DataBean> data;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * create_time : 2017-03-16
         * items : http://www.sina.com.cn/aa.jpg
         */

        private String create_time;
        private String items;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }
    }
}
