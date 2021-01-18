package cn.com.taodaji.model.entity;

import java.util.List;

public class HomeStore {

    /**
     * err : 0
     * data : {"items":[{"createdAt":"2020-04-07 17:14:52","entityId":1,"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/18032210090297489264.jpg","info":"11","order":1,"type":1,"websiteId":3},{"createdAt":"2020-04-07 17:15:15","entityId":2,"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1707150918192c8422ef.jpg","info":"12","order":2,"type":3,"websiteId":3},{"createdAt":"2020-04-09 16:11:23","entityId":3,"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/200409161120dad5ddf6.png","info":"69651","order":3,"type":1,"websiteId":3},{"createdAt":"2020-04-09 16:34:25","entityId":4,"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/200409163416b87f1d13.jpg","info":"2802","order":4,"type":2,"websiteId":3}],"total":4}
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
         * items : [{"createdAt":"2020-04-07 17:14:52","entityId":1,"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/18032210090297489264.jpg","info":"11","order":1,"type":1,"websiteId":3},{"createdAt":"2020-04-07 17:15:15","entityId":2,"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1707150918192c8422ef.jpg","info":"12","order":2,"type":3,"websiteId":3},{"createdAt":"2020-04-09 16:11:23","entityId":3,"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/200409161120dad5ddf6.png","info":"69651","order":3,"type":1,"websiteId":3},{"createdAt":"2020-04-09 16:34:25","entityId":4,"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/200409163416b87f1d13.jpg","info":"2802","order":4,"type":2,"websiteId":3}]
         * total : 4
         */

        private int total;
        private List<ItemsBean> items;

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
             * createdAt : 2020-04-07 17:14:52
             * entityId : 1
             * imageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/18032210090297489264.jpg
             * info : 11
             * order : 1
             * type : 1
             * websiteId : 3
             */

            private String createdAt;
            private int entityId;
            private String imageUrl;
            private String info;
            private int order;
            private int type;
            private int websiteId;

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getWebsiteId() {
                return websiteId;
            }

            public void setWebsiteId(int websiteId) {
                this.websiteId = websiteId;
            }
        }
    }
}
