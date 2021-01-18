package cn.com.taodaji.model.entity;

import java.io.Serializable;

/**
 * Created by yangkuo on 2018-09-19.
 */
public class FindProductCommission implements Serializable {


    /**
     * entity_id : 13
     * commodity_id	int	三级分类id
     * retail_level1	String	零售规格一
     * retail_level2	String	零售规格二
     * wholesale_level1	String	批发规格一
     * wholesale_level2	String	批发规格二
     * retail_commission	String	零售提成，单位%，例：2%
     * wholesale_commission	String	批发提成，单位%，例：1%
     * create_time : 2018-08-31 17:09
     * update_time : 2018-08-31 17:09
     */


        private int categoryId;
        private int categoryParentId;
        private String createdAt;
        private String entityId;
        private String retailCommission;
        private String retailLevel1;
        private String retailLevel2;
        private String updatedAt;
        private String wholesaleCommission;
        private String wholesaleLevel1;
        private String wholesaleLevel2;

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getCategoryParentId() {
            return categoryParentId;
        }

        public void setCategoryParentId(int categoryParentId) {
            this.categoryParentId = categoryParentId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getRetailCommission() {
            return retailCommission;
        }

        public void setRetailCommission(String retailCommission) {
            this.retailCommission = retailCommission;
        }

        public String getRetailLevel1() {
            return retailLevel1;
        }

        public void setRetailLevel1(String retailLevel1) {
            this.retailLevel1 = retailLevel1;
        }

        public String getRetailLevel2() {
            return retailLevel2;
        }

        public void setRetailLevel2(String retailLevel2) {
            this.retailLevel2 = retailLevel2;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getWholesaleCommission() {
            return wholesaleCommission;
        }

        public void setWholesaleCommission(String wholesaleCommission) {
            this.wholesaleCommission = wholesaleCommission;
        }

        public String getWholesaleLevel1() {
            return wholesaleLevel1;
        }

        public void setWholesaleLevel1(String wholesaleLevel1) {
            this.wholesaleLevel1 = wholesaleLevel1;
        }

        public String getWholesaleLevel2() {
            return wholesaleLevel2;
        }

        public void setWholesaleLevel2(String wholesaleLevel2) {
            this.wholesaleLevel2 = wholesaleLevel2;
        }

}
