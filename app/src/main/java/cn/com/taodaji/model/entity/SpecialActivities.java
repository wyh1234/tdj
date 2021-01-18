package cn.com.taodaji.model.entity;

import java.util.List;


/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class SpecialActivities {

    /**
     * total : 4
     * items : [{"entity_id":73,"create_time":"2018-05-17 09:13","update_time":"2018-05-17 09:13","site":5,"name":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180516152247276ea26b.png","inner_image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/18051615225217d77031.png","width":640,"height":250,"type":3,"storeId":0,"productId":0,"url":"","sort":8887,"url_type":0,"url_id":0,"is_delete":"N"},{"entity_id":70,"create_time":"2018-05-16 10:54","update_time":"2018-05-16 10:54","site":5,"name":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180516105405459a1731.png","inner_image":"","width":640,"height":250,"type":1,"storeId":0,"productId":13756,"url":"","sort":8888,"url_type":0,"url_id":0,"is_delete":"N"},{"entity_id":74,"create_time":"2018-05-16 15:53","update_time":"2018-05-16 15:53","site":5,"name":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180516154508b6608e48.png","inner_image":"","width":640,"height":250,"type":4,"storeId":null,"productId":null,"url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180516152247276ea26b.png","sort":8888,"url_type":0,"url_id":0,"is_delete":"N"},{"entity_id":72,"create_time":"2018-05-17 09:42","update_time":"2018-05-17 09:42","site":5,"name":"","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1805161509579fe445d0.png","inner_image":"","width":640,"height":250,"type":2,"storeId":719,"productId":null,"url":null,"sort":8889,"url_type":0,"url_id":0,"is_delete":"N"}]
     * pn : 1
     * ps : 10
     */

    private int total;
    private int pn;
    private int ps;
    private List<ItemsBean> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * entity_id : 73
         * create_time : 2018-05-17 09:13
         * update_time : 2018-05-17 09:13
         * site : 5
         * name :
         * image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/180516152247276ea26b.png
         * inner_image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/18051615225217d77031.png
         * width : 640
         * height : 250
         * type : 3
         * storeId : 0
         * productId : 0
         * url :
         * sort : 8887
         * url_type : 0
         * url_id : 0
         * is_delete : N
         */

        private int entity_id;
        private String create_time;
        private String update_time;
        private int site;
        private String name;
        private String image;
        private String inner_image;
        private int width;
        private int height;
        private int type;
        private int storeId;
        private int productId;
        private String url;
        private int sort;
        private int url_type;
        private int url_id;
        private String is_delete;

        public int getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
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

        public int getSite() {
            return site;
        }

        public void setSite(int site) {
            this.site = site;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getUrl_type() {
            return url_type;
        }

        public void setUrl_type(int url_type) {
            this.url_type = url_type;
        }

        public int getUrl_id() {
            return url_id;
        }

        public void setUrl_id(int url_id) {
            this.url_id = url_id;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }
    }
}


