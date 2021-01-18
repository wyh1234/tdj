package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class StoreRecommend {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * bannerImgUrl : 实力商家图片
         * mainCommodity : 主营商品
         * marketNo : 市场编号
         * name : 店铺名
         * store : 11
         * logoImageUrl : 店铺头像
         * marketName : 市场名称
         */

        private String bannerImgUrl;
        private String mainCommodity;
        private String marketNo;
        private String name;
        private int store;
        private String logoImageUrl;
        private String marketName;

        public String getBannerImgUrl() {
            return bannerImgUrl;
        }

        public void setBannerImgUrl(String bannerImgUrl) {
            this.bannerImgUrl = bannerImgUrl;
        }

        public String getMainCommodity() {
            return mainCommodity;
        }

        public void setMainCommodity(String mainCommodity) {
            this.mainCommodity = mainCommodity;
        }

        public String getMarketNo() {
            return marketNo;
        }

        public void setMarketNo(String marketNo) {
            this.marketNo = marketNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStore() {
            return store;
        }

        public void setStore(int store) {
            this.store = store;
        }

        public String getLogoImageUrl() {
            return logoImageUrl;
        }

        public void setLogoImageUrl(String logoImageUrl) {
            this.logoImageUrl = logoImageUrl;
        }

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }
    }
}

