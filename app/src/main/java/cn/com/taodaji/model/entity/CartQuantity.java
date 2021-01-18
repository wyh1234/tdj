package cn.com.taodaji.model.entity;

public class CartQuantity {
    private int err;
    private String msg;
    private SignInData data;

    public SignInData getData() {
        return data;
    }

    public void setData(SignInData data) {
        this.data = data;
    }

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

    public static  class  SignInData{
        private int integral;
        private double money;
        private int totalIntegral;
        private int signCount;

        public int getSignCount() {
            return signCount;
        }

        public void setSignCount(int signCount) {
            this.signCount = signCount;
        }

        public int getTotalIntegral() {
            return totalIntegral;
        }

        public void setTotalIntegral(int totalIntegral) {
            this.totalIntegral = totalIntegral;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }
    }

}
