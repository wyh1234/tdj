package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class MarketingManage implements Serializable {


    /**
     * err : 0
     * data : [{"entity_id":14,"site":2,"type":6,"name":"弹屏+顶部banner","remark":"弹屏加广告","bg_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1911271701218eff71c8.png","app_sort":4,"app_title":"弹屏加广告12","app_content":"弹屏加广告12","stage_type":2,"app_explain_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1911271701378b833ebf.png","delete_flag":0,"create_time":"2019-11-27 17:02","update_time":"2019-11-27 17:02","packageList":[{"entity_id":61,"advertisement_type_id":14,"type_detail":"2,3","stage":1,"days":1,"price":100,"gift_days":0,"remark":null,"delete_flag":0,"create_time":"2019-11-27 17:02","limit_days":100}],"exceptionList":[{"entity_id":2,"name":"顶部banner","type":2,"detail":"2","title":"","content":null,"url":null,"sort":2,"flag":0},{"entity_id":3,"name":"弹屏banner","type":3,"detail":"3","title":"","content":null,"url":null,"sort":3,"flag":0}]}]
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

    public static class DataBean implements Serializable{
        /**
         * entity_id : 14
         * site : 2
         * type : 6
         * name : 弹屏+顶部banner
         * remark : 弹屏加广告
         * bg_url : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1911271701218eff71c8.png
         * app_sort : 4
         * app_title : 弹屏加广告12
         * app_content : 弹屏加广告12
         * stage_type : 2
         * app_explain_url : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1911271701378b833ebf.png
         * delete_flag : 0
         * create_time : 2019-11-27 17:02
         * update_time : 2019-11-27 17:02
         * packageList : [{"entity_id":61,"advertisement_type_id":14,"type_detail":"2,3","stage":1,"days":1,"price":100,"gift_days":0,"remark":null,"delete_flag":0,"create_time":"2019-11-27 17:02","limit_days":100}]
         * exceptionList : [{"entity_id":2,"name":"顶部banner","type":2,"detail":"2","title":"","content":null,"url":null,"sort":2,"flag":0},{"entity_id":3,"name":"弹屏banner","type":3,"detail":"3","title":"","content":null,"url":null,"sort":3,"flag":0}]
         */

        private int entity_id;
        private int site;
        private int type;
        private String name;
        private String remark;
        private String bg_url;
        private int app_sort;
        private String app_title;
        private String app_content;
        private int stage_type;
        private String app_explain_url;
        private int delete_flag;
        private String create_time;
        private String update_time;
        private List<PackageListBean> packageList;
        private List<ExceptionListBean> exceptionList;

        public int getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
        }

        public int getSite() {
            return site;
        }

        public void setSite(int site) {
            this.site = site;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getBg_url() {
            return bg_url;
        }

        public void setBg_url(String bg_url) {
            this.bg_url = bg_url;
        }

        public int getApp_sort() {
            return app_sort;
        }

        public void setApp_sort(int app_sort) {
            this.app_sort = app_sort;
        }

        public String getApp_title() {
            return app_title;
        }

        public void setApp_title(String app_title) {
            this.app_title = app_title;
        }

        public String getApp_content() {
            return app_content;
        }

        public void setApp_content(String app_content) {
            this.app_content = app_content;
        }

        public int getStage_type() {
            return stage_type;
        }

        public void setStage_type(int stage_type) {
            this.stage_type = stage_type;
        }

        public String getApp_explain_url() {
            return app_explain_url;
        }

        public void setApp_explain_url(String app_explain_url) {
            this.app_explain_url = app_explain_url;
        }

        public int getDelete_flag() {
            return delete_flag;
        }

        public void setDelete_flag(int delete_flag) {
            this.delete_flag = delete_flag;
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

        public List<PackageListBean> getPackageList() {
            return packageList;
        }

        public void setPackageList(List<PackageListBean> packageList) {
            this.packageList = packageList;
        }

        public List<ExceptionListBean> getExceptionList() {
            return exceptionList;
        }

        public void setExceptionList(List<ExceptionListBean> exceptionList) {
            this.exceptionList = exceptionList;
        }

        public static class PackageListBean implements Serializable {
            /**
             * entity_id : 61
             * advertisement_type_id : 14
             * type_detail : 2,3
             * stage : 1
             * days : 1
             * price : 100
             * gift_days : 0
             * remark : null
             * delete_flag : 0
             * create_time : 2019-11-27 17:02
             * limit_days : 100
             */

            private int entity_id;
            private int advertisement_type_id;
            private String type_detail;
            private int stage;
            private int days;
            private BigDecimal price;
            private int gift_days;
            private Object remark;
            private int delete_flag;
            private String create_time;
            private int limit_days;
            private boolean flag;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public int getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(int entity_id) {
                this.entity_id = entity_id;
            }

            public int getAdvertisement_type_id() {
                return advertisement_type_id;
            }

            public void setAdvertisement_type_id(int advertisement_type_id) {
                this.advertisement_type_id = advertisement_type_id;
            }

            public String getType_detail() {
                return type_detail;
            }

            public void setType_detail(String type_detail) {
                this.type_detail = type_detail;
            }

            public int getStage() {
                return stage;
            }

            public void setStage(int stage) {
                this.stage = stage;
            }

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public BigDecimal getPrice() {
                return price;
            }

            public void setPrice(BigDecimal price) {
                this.price = price;
            }

            public int getGift_days() {
                return gift_days;
            }

            public void setGift_days(int gift_days) {
                this.gift_days = gift_days;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public int getDelete_flag() {
                return delete_flag;
            }

            public void setDelete_flag(int delete_flag) {
                this.delete_flag = delete_flag;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getLimit_days() {
                return limit_days;
            }

            public void setLimit_days(int limit_days) {
                this.limit_days = limit_days;
            }
        }

        public static class ExceptionListBean implements Serializable {
            /**
             * entity_id : 2
             * name : 顶部banner
             * type : 2
             * detail : 2
             * title :
             * content : null
             * url : null
             * sort : 2
             * flag : 0
             */

            private int entity_id;
            private String name;
            private int type;
            private String detail;
            private String title;
            private Object content;
            private Object url;
            private int sort;
            private int flag;

            public int getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(int entity_id) {
                this.entity_id = entity_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getContent() {
                return content;
            }

            public void setContent(Object content) {
                this.content = content;
            }

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }
        }
    }
}
