package cn.com.taodaji.model.entity;

import java.util.List;


public class HomepageGridDatas {

    private List<ListBean> list;
    private int isShowIndex;

    public int getIsShowIndex() {
        return isShowIndex;
    }

    public void setIsShowIndex(int isShowIndex) {
        this.isShowIndex = isShowIndex;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * categoryId : 12
         * name : 调味品
         * products : {"items":[{"entityId":364,"createTime":1486349767000,"name":"黑胡椒","commodityId":100,"store":13,"storeName":"窝窝蔬菜","unit":"千克","price":12,"specialPrice":0,"recommendImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061056056c1f05da.jpg","stock":2000,"qrCodeId":"","categoryId":"25","description":"他咯哦","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"调味品-椒类","categories":"25","nickName":"小黑胡椒","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061056056c1f05da.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061055408c92ff1e.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061054492468ff70.jpg"],"phone":"18571161280","realName":"格子","verifyInfo":"","sku":"xtb_1486349767730","credentialsImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170206105527cb28e3c1.jpg","memberPrice":14.399999999999999},{"entityId":365,"createTime":1486349839000,"name":"黑胡椒","commodityId":100,"store":13,"storeName":"窝窝蔬菜","unit":"千克","price":12,"specialPrice":0,"recommendImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061057152f1923de.jpg","stock":274,"qrCodeId":"","categoryId":"25","description":"他咯哦","saleNum":0,"authStatus":2,"bannerImage":"","catalogCategoryName":"调味品-椒类","categories":"25","nickName":"ABC","status":1,"gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061057152f1923de.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061056056c1f05da.jpg","http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1702061055408c92ff1e.jpg"],"phone":"18571161280","realName":"格子","verifyInfo":"","sku":"xtb_1486349839297","credentialsImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170206105705dddde114.jpg","memberPrice":14.399999999999999}]}
         */

        private int categoryId;
        private String name;
        private String recommendImage;
        private ProductsBean products;

        public String getRecommendImage() {
            return recommendImage;
        }

        public void setRecommendImage(String recommendImage) {
            this.recommendImage = recommendImage;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ProductsBean getProducts() {
            return products;
        }

        public void setProducts(ProductsBean products) {
            this.products = products;
        }

        public static class ProductsBean {
            private List<GoodsInformation> items;

            public List<GoodsInformation> getItems() {
                return items;
            }

            public void setItems(List<GoodsInformation> items) {
                this.items = items;
            }


        }
    }
}


