package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by yangkuo on 2019/1/28.
 */
public class SpecialMerchants {

    /**
     * list : [{"site":0,"adsInfo":"","createTime":"2019-01-28","sort":88,"adStatus":0,"putStartTime":"2019-01-28","adsName":"今天是小年","width":720,"entityId":15,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190128094058271b94e2.jpg","type":5,"putCity":"2,3","isDelete":"N","flag":0,"height":1280,"putEndTime":"2019-02-02","putSet":0},{"site":2,"adsInfo":"39010","createTime":"2019-01-24","sort":88,"adStatus":0,"putStartTime":"2019-01-28","updateTime":1548482382000,"adsName":"点点滴滴","width":720,"entityId":3,"image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190124110319bab4b0d9.jpg","type":1,"isDelete":"N","flag":0,"height":1280,"putEndTime":"2019-02-01","putSet":0}]
     * pn : 1
     * ps : 20
     * total : 2
     */

    private int pn;
    private int ps;
    private int total;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * site : 0
         * adsInfo :
         * createTime : 2019-01-28
         * sort : 88
         * adStatus : 0
         * putStartTime : 2019-01-28
         * adsName : 今天是小年
         * width : 720
         * entityId : 15
         * image : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190128094058271b94e2.jpg
         * type : 5
         * putCity : 2,3
         * isDelete : N
         * flag : 0
         * height : 1280
         * putEndTime : 2019-02-02
         * putSet : 0
         * updateTime : 1548482382000
         */

        private int site;
        private String adsInfo;
        private String createTime;
        private int sort;
        private int adStatus;
        private String putStartTime;
        private String adsName;
        private int width;
        private int entityId;
        private String image;
        private int type;
        private String putCity;
        private String isDelete;
        private int flag;
        private int height;
        private String putEndTime;
        private int putSet;
        private long updateTime;

        public int getSite() {
            return site;
        }

        public void setSite(int site) {
            this.site = site;
        }

        public String getAdsInfo() {
            return adsInfo;
        }

        public void setAdsInfo(String adsInfo) {
            this.adsInfo = adsInfo;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getAdStatus() {
            return adStatus;
        }

        public void setAdStatus(int adStatus) {
            this.adStatus = adStatus;
        }

        public String getPutStartTime() {
            return putStartTime;
        }

        public void setPutStartTime(String putStartTime) {
            this.putStartTime = putStartTime;
        }

        public String getAdsName() {
            return adsName;
        }

        public void setAdsName(String adsName) {
            this.adsName = adsName;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPutCity() {
            return putCity;
        }

        public void setPutCity(String putCity) {
            this.putCity = putCity;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getPutEndTime() {
            return putEndTime;
        }

        public void setPutEndTime(String putEndTime) {
            this.putEndTime = putEndTime;
        }

        public int getPutSet() {
            return putSet;
        }

        public void setPutSet(int putSet) {
            this.putSet = putSet;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}

