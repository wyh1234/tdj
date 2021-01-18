package cn.com.taodaji.model.entity;

import com.base.annotation.OnlyField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class GoodsCategoryList {

    /**
     * typeId : 10
     * categoryId : 10
     * categoryName : 蔬菜
     * children : [{"typeId":14,"categoryId":14,"categoryName":"叶菜类","children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170105011847fff40b96.jpg","status":1,"priceDiscount":1},{"typeId":15,"categoryId":15,"categoryName":"根茎类","children":[],"imageUrl":"","status":1,"priceDiscount":1},{"typeId":16,"categoryId":16,"categoryName":"茄果类","children":[],"imageUrl":"","status":1,"priceDiscount":1},{"typeId":17,"categoryId":17,"categoryName":"含豆类","children":[],"imageUrl":"","status":1,"priceDiscount":1},{"typeId":19,"categoryId":19,"categoryName":"豆制品","children":[],"imageUrl":"","status":1,"priceDiscount":1},{"typeId":36,"categoryId":51,"categoryName":"菌菇类","children":[],"imageUrl":"","status":1,"priceDiscount":1},{"typeId":37,"categoryId":52,"categoryName":"佐料类","children":[],"imageUrl":"","status":1,"priceDiscount":1},{"typeId":38,"categoryId":53,"categoryName":"腌泡类","children":[],"imageUrl":"","status":1,"priceDiscount":1}]
     * imageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17010501192095cd6394.png
     * status : 1
     * priceDiscount : 1
     */

    private int typeId;
    @OnlyField
    private int categoryId;
    private String categoryName;
    private String imageUrl;
    private int status;
    private float priceDiscount;
    private List<ChildrenBean> children;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(float priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean implements Serializable {
        /**
         * typeId : 14
         * categoryId : 14
         * categoryName : 叶菜类
         * children : []
         * imageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170105011847fff40b96.jpg
         * status : 1
         * priceDiscount : 1
         */

        private int typeId;
        @OnlyField
        private int categoryId;
        private String categoryName;
        private String imageUrl;
        private int status;
        private float priceDiscount;
        private boolean selected;
        private int isDrainageArea;
        private int isForceTemplate;

        public int getIsForceTemplate() {
            return isForceTemplate;
        }

        public void setIsForceTemplate(int isForceTemplate) {
            this.isForceTemplate = isForceTemplate;
        }

        private List<GoodsCategoryListNext> children;

        public int getIsDrainageArea() {
            return isDrainageArea;
        }

        public void setIsDrainageArea(int isDrainageArea) {
            this.isDrainageArea = isDrainageArea;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public float getPriceDiscount() {
            return priceDiscount;
        }

        public void setPriceDiscount(float priceDiscount) {
            this.priceDiscount = priceDiscount;
        }

        public List<GoodsCategoryListNext> getChildren() {
            return children;
        }

        public void setChildren(List<GoodsCategoryListNext> children) {
            this.children = children;
        }
    }
}

