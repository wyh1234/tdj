package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15 0015.
 */
public class OrderShipmethod {

    private List<ShippingMethodsBean> shippingMethods;

    public List<ShippingMethodsBean> getShippingMethods() {
        return shippingMethods;
    }

    public void setShippingMethods(List<ShippingMethodsBean> shippingMethods) {
        this.shippingMethods = shippingMethods;
    }

    public static class ShippingMethodsBean {
        /**
         * code : system_default
         * freeCost : 0
         * price : 0
         * title : 快递免邮
         */

        private String code;
        private int freeCost;
        private float price;
        private String title;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getFreeCost() {
            return freeCost;
        }

        public void setFreeCost(int freeCost) {
            this.freeCost = freeCost;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

