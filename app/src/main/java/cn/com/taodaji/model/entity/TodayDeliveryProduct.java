package cn.com.taodaji.model.entity;

import java.util.List;

public class TodayDeliveryProduct {

    /**
     * err : 0
     * data : {"orderProductToBeStoreds":[{"nickName":"商品名称","productId":-1,"productName":"商品名称"},{"nickName":"测试白菜","productId":30557,"productName":"测试商品01"},{"nickName":"","productId":69435,"productName":"包菜"}],"total":3}
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
         * orderProductToBeStoreds : [{"nickName":"商品名称","productId":-1,"productName":"商品名称"},{"nickName":"测试白菜","productId":30557,"productName":"测试商品01"},{"nickName":"","productId":69435,"productName":"包菜"}]
         * total : 3
         */

        private int total;
        private List<OrderProductToBeStoredsBean> orderProductToBeStoreds;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<OrderProductToBeStoredsBean> getOrderProductToBeStoreds() {
            return orderProductToBeStoreds;
        }

        public void setOrderProductToBeStoreds(List<OrderProductToBeStoredsBean> orderProductToBeStoreds) {
            this.orderProductToBeStoreds = orderProductToBeStoreds;
        }

        public static class OrderProductToBeStoredsBean {
            /**
             * nickName : 商品名称
             * productId : -1
             * productName : 商品名称
             */

            private String nickName;
            private int productId;
            private String productName;
            private String num;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }
        }
    }
}
