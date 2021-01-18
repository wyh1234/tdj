package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017-05-15.
 */
public class FindByActivitiesID {


    /**
     * err : 0
     * data : {"total":2,"items":[{"entityId":608,"name":"油麦菜","commodityId":32,"commodityName":"油麦菜","nickName":"广油麦菜","credentialsImage":"","categoryId":15,"categoryName":"叶菜类","updateTime":1493691938000,"createTime":1492672637000,"authStatus":2,"verifyInfo":"","gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17042015151521bd4367.jpeg"],"stock":9999,"unit":"斤","price":2.5,"saleNum":10,"totalSellNum":20,"totalSellAmount":36,"isRecommend":0,"memberPrice":3.25,"status":1,"description":"绿色蔬菜","store":16,"storeName":"杨氏蔬菜","sku":"xtb_1492672637129","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17042015151521bd4367.jpeg","smallImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17042015151521bd4367.jpeg","thumbnail":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17042015151521bd4367.jpeg","parentCategoryName":"新鲜蔬菜-叶菜类","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"parentCategoryId":10},{"entityId":609,"name":"油麦菜","commodityId":32,"commodityName":"油麦菜","nickName":"本地油麦菜","credentialsImage":"","categoryId":15,"categoryName":"叶菜类","updateTime":1493856420000,"createTime":1492672751000,"authStatus":2,"verifyInfo":"","gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420151812c9700527.jpeg"],"stock":999,"unit":"斤","price":1.8,"saleNum":20,"totalSellNum":20,"totalSellAmount":30,"isRecommend":0,"memberPrice":2.34,"status":1,"description":"鲜嫩碧绿","store":16,"storeName":"杨氏蔬菜","sku":"xtb_1492672751419","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420151812c9700527.jpeg","smallImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420151812c9700527.jpeg","thumbnail":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420151812c9700527.jpeg","parentCategoryName":"新鲜蔬菜-叶菜类","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"parentCategoryId":10}],"pn":1,"ps":10,"innerImage":"http://tdj-res.oss-cn-hangzhou.aliyuncs.com/banner/banner3.jpg"}
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
         * total : 2
         * items : [{"entityId":608,"name":"油麦菜","commodityId":32,"commodityName":"油麦菜","nickName":"广油麦菜","credentialsImage":"","categoryId":15,"categoryName":"叶菜类","updateTime":1493691938000,"createTime":1492672637000,"authStatus":2,"verifyInfo":"","gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17042015151521bd4367.jpeg"],"stock":9999,"unit":"斤","price":2.5,"saleNum":10,"totalSellNum":20,"totalSellAmount":36,"isRecommend":0,"memberPrice":3.25,"status":1,"description":"绿色蔬菜","store":16,"storeName":"杨氏蔬菜","sku":"xtb_1492672637129","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17042015151521bd4367.jpeg","smallImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17042015151521bd4367.jpeg","thumbnail":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17042015151521bd4367.jpeg","parentCategoryName":"新鲜蔬菜-叶菜类","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"parentCategoryId":10},{"entityId":609,"name":"油麦菜","commodityId":32,"commodityName":"油麦菜","nickName":"本地油麦菜","credentialsImage":"","categoryId":15,"categoryName":"叶菜类","updateTime":1493856420000,"createTime":1492672751000,"authStatus":2,"verifyInfo":"","gallery":["http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420151812c9700527.jpeg"],"stock":999,"unit":"斤","price":1.8,"saleNum":20,"totalSellNum":20,"totalSellAmount":30,"isRecommend":0,"memberPrice":2.34,"status":1,"description":"鲜嫩碧绿","store":16,"storeName":"杨氏蔬菜","sku":"xtb_1492672751419","image":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420151812c9700527.jpeg","smallImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420151812c9700527.jpeg","thumbnail":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170420151812c9700527.jpeg","parentCategoryName":"新鲜蔬菜-叶菜类","productType":0,"specialId":0,"allowPurchase":0,"alreadyPurchase":0,"parentCategoryId":10}]
         * pn : 1
         * ps : 10
         * innerImage : http://tdj-res.oss-cn-hangzhou.aliyuncs.com/banner/banner3.jpg
         */

        private int total;
        private int pn;
        private int ps;
        private String innerImage = "";
        private List<GoodsInformation> items;

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

        public String getInnerImage() {
            return innerImage;
        }

        public void setInnerImage(String innerImage) {
            this.innerImage = innerImage;
        }

        public List<GoodsInformation> getItems() {
            return items;
        }

        public void setItems(List<GoodsInformation> items) {
            this.items = items;
        }


    }
}
