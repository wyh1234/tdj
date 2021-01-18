package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-10-19.
 */

public class GoodsClassifySearchBean  {

    /**
     * err : 0
     * data : [{"entityId":3,"createTime":"2017-03-27 12:26","name":"大白菜","pname":"DBC","unit":null,"sortIndex":1,"categoryId":15,"parentCategoryId":10,"authorId":null,"authorType":null,"parentCategoryName":"新鲜蔬菜","description":null,"priceDiscount":1.3,"typeId":89,"categoryName":"叶菜类"}]
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
         * entityId : 3
         * createTime : 2017-03-27 12:26
         * name : 大白菜
         * pname : DBC
         * unit : null
         * sortIndex : 1
         * categoryId : 15
         * parentCategoryId : 10
         * authorId : null
         * authorType : null
         * parentCategoryName : 新鲜蔬菜
         * description : null
         * priceDiscount : 1.3
         * typeId : 89
         * categoryName : 叶菜类
         */

        private int entityId;
        private String createTime;
        private String name;
        private String pname;
        private Object unit;
        private int sortIndex;
        private int categoryId;
        private int parentCategoryId;
        private Object authorId;
        private Object authorType;
        private String parentCategoryName;
        private Object description;
        private double priceDiscount;
        private int typeId;
        private String categoryName;
        private int  isDrainageArea;
        private int isForceTemplate;

        public int getIsForceTemplate() {
            return isForceTemplate;
        }

        public void setIsForceTemplate(int isForceTemplate) {
            this.isForceTemplate = isForceTemplate;
        }

        public int getIsDrainageArea() {
            return isDrainageArea;
        }

        public void setIsDrainageArea(int isDrainageArea) {
            this.isDrainageArea = isDrainageArea;
        }

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public int getSortIndex() {
            return sortIndex;
        }

        public void setSortIndex(int sortIndex) {
            this.sortIndex = sortIndex;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getParentCategoryId() {
            return parentCategoryId;
        }

        public void setParentCategoryId(int parentCategoryId) {
            this.parentCategoryId = parentCategoryId;
        }

        public Object getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Object authorId) {
            this.authorId = authorId;
        }

        public Object getAuthorType() {
            return authorType;
        }

        public void setAuthorType(Object authorType) {
            this.authorType = authorType;
        }

        public String getParentCategoryName() {
            return parentCategoryName;
        }

        public void setParentCategoryName(String parentCategoryName) {
            this.parentCategoryName = parentCategoryName;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public double getPriceDiscount() {
            return priceDiscount;
        }

        public void setPriceDiscount(double priceDiscount) {
            this.priceDiscount = priceDiscount;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
