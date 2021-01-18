package cn.com.taodaji.model.entity;

import com.base.annotation.OnlyField;

import java.util.List;

/**
 * Created by yangkuo on 2018-03-06.
 */

public class FindProductDetail {

    /**
     * err : 0
     * data : [{"entity_id":41,"product_id":1,"description":"http://wwww.baidu.com.jpg","status":1,"type":1,"create_time":"2018-02-01 16:24","update_time":null},{"entity_id":42,"product_id":1,"description":"新信息向你","status":1,"type":2,"create_time":"2018-02-01 16:24","update_time":null}]
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
         * entity_id : 41
         * product_id : 1
         * description : http://wwww.baidu.com.jpg
         * status : 1
         * type : 1
         * create_time : 2018-02-01 16:24
         * update_time : null
         */

        @OnlyField
        private int entity_id;
        private int product_id;
        private String description = "";
        private int status;
        private int type;
        private String create_time;
        private String update_time;

        public int getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
