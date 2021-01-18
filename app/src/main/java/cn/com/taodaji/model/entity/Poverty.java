package cn.com.taodaji.model.entity;

import java.util.List;

public class Poverty {

    /**
     * err : 0
     * data : {"items":[{"commentary":"hdhdh","createTime":"2019-08-15 17:08:13","entityId":4,"flag":1,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815170810483febf5.jpg","site":2,"sort":87,"title":"hah","type":0,"url":""},{"commentary":"爱心满人间","createTime":"2019-08-15 17:07:05","entityId":3,"flag":1,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815170616d07fe75c.png","site":2,"sort":88,"title":"淘大集","type":1,"url":"www.baidu.com"}],"pn":0,"ps":0,"total":2}
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
         * items : [{"commentary":"hdhdh","createTime":"2019-08-15 17:08:13","entityId":4,"flag":1,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815170810483febf5.jpg","site":2,"sort":87,"title":"hah","type":0,"url":""},{"commentary":"爱心满人间","createTime":"2019-08-15 17:07:05","entityId":3,"flag":1,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815170616d07fe75c.png","site":2,"sort":88,"title":"淘大集","type":1,"url":"www.baidu.com"}]
         * pn : 0
         * ps : 0
         * total : 2
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
             * commentary : hdhdh
             * createTime : 2019-08-15 17:08:13
             * entityId : 4
             * flag : 1
             * image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815170810483febf5.jpg
             * site : 2
             * sort : 87
             * title : hah
             * type : 0
             * url :
             */

            private String commentary;
            private String createTime;
            private int entityId;
            private int flag;
            private String image;
            private int site;
            private int sort;
            private String title;
            private int type;
            private String url;

            public String getCommentary() {
                return commentary;
            }

            public void setCommentary(String commentary) {
                this.commentary = commentary;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getSite() {
                return site;
            }

            public void setSite(int site) {
                this.site = site;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

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
        }
    }
}
