package cn.com.taodaji.model.entity;

import java.util.List;

public class FindBusiness {

    /**
     * err : 0
     * data : {"items":[{"adsInfo":"广告位火热招商中。联系电话1300000000，小明子经理","category":"测试","enter_time":"2018-08-10 11:28:53","entity_id":9,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180809151947131c56bd.png","inner_image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808101128440901f0d8.png","site":19,"sort":87,"storeId":724,"storeName":"啦啦啦"},{"adsInfo":"广告位招租，联系人小宇1310000000","category":"冻货","enter_time":"2018-08-10 14:35:31","entity_id":10,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808101434090d9eb755.jpg","inner_image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808101435102434099c.png","site":19,"sort":87,"storeId":718,"storeName":"人怕出名猪怕壮"},{"adsInfo":"广告位招租中，联系人小明子，电话13023637106","category":"水果","enter_time":"2018-08-10 14:30:54","entity_id":2,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808091423400be79cb7.png","inner_image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180810143033973b6397.jpg","site":19,"sort":88,"storeId":728,"storeName":"小宇调料"}],"pn":1,"ps":20,"total":3}
     * msg : success
     */

    private int err;
    private DataBean data;
    private String msg="";

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
         * items : [{"adsInfo":"广告位火热招商中。联系电话1300000000，小明子经理","category":"测试","enter_time":"2018-08-10 11:28:53","entity_id":9,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180809151947131c56bd.png","inner_image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808101128440901f0d8.png","site":19,"sort":87,"storeId":724,"storeName":"啦啦啦"},{"adsInfo":"广告位招租，联系人小宇1310000000","category":"冻货","enter_time":"2018-08-10 14:35:31","entity_id":10,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808101434090d9eb755.jpg","inner_image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808101435102434099c.png","site":19,"sort":87,"storeId":718,"storeName":"人怕出名猪怕壮"},{"adsInfo":"广告位招租中，联系人小明子，电话13023637106","category":"水果","enter_time":"2018-08-10 14:30:54","entity_id":2,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808091423400be79cb7.png","inner_image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180810143033973b6397.jpg","site":19,"sort":88,"storeId":728,"storeName":"小宇调料"}]
         * pn : 1
         * ps : 20
         * total : 3
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
             * adsInfo : 广告位火热招商中。联系电话1300000000，小明子经理
             * category : 测试
             * enter_time : 2018-08-10 11:28:53
             * entity_id : 9
             * image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180809151947131c56bd.png
             * inner_image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1808101128440901f0d8.png
             * site : 19
             * sort : 87
             * storeId : 724
             * storeName : 啦啦啦
             */

            private String adsInfo="";
            private String category="";
            private String enter_time="";
            private int entity_id;
            private String image="";
            private String inner_image="";
            private String image_url="";
            private int site;
            private int sort;
            private int storeId;
            private String storeName="";
            private int type;

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getAdsInfo() {
                return adsInfo;
            }

            public void setAdsInfo(String adsInfo) {
                this.adsInfo = adsInfo;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getEnter_time() {
                return enter_time;
            }

            public void setEnter_time(String enter_time) {
                this.enter_time = enter_time;
            }

            public int getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(int entity_id) {
                this.entity_id = entity_id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getInner_image() {
                return inner_image;
            }

            public void setInner_image(String inner_image) {
                this.inner_image = inner_image;
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

            public int getStoreId() {
                return storeId;
            }

            public void setStoreId(int storeId) {
                this.storeId = storeId;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }
        }
    }
}
