package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by yangkuo on 2018-05-18.
 */

public class HomePageFuncationButton {

    /**
     * err : 0
     * data : [{"categoryId":10,"imageUrl":"","name":"新鲜蔬菜"},{"categoryId":11,"imageUrl":"","name":"禽肉蛋类"},{"categoryId":12,"imageUrl":"","name":"米面粮油"}]
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
         * categoryId : 10
         * imageUrl :
         * name : 新鲜蔬菜
         */

        private int categoryId;
        private String imageUrl;
        private String name;
        private int type;
        private String url;//信息发布的跳转url

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
