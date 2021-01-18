package cn.com.taodaji.model.entity;

import java.util.List;

public class OrderPayMethod {

    private List<PaymentsBean> payments;

    public List<PaymentsBean> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentsBean> payments) {
        this.payments = payments;
    }

    public static class PaymentsBean {
        /**
         * code : cash_ondelivery
         * method : 货到付款
         */

        private String code;
        private String method;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }
}

