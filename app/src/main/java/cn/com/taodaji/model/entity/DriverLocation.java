package cn.com.taodaji.model.entity;

public class DriverLocation {

    /**
     * err : 0
     * data : {"customerLat":32.004739,"customerLng":112.116845,"driverLat":30.59389892578125,"driverLng":114.3374563259549,"driverStatus":6,"deliveryTime":"9:00"}
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
         * customerLat : 32.004739
         * customerLng : 112.116845
         * driverLat : 30.59389892578125
         * driverLng : 114.3374563259549
         * driverStatus : 6
         * deliveryTime : 9:00
         */

        private double customerLat;
        private double customerLng;
        private double driverLat;
        private double driverLng;
        private int driverStatus;
        private String deliveryTime;

        public double getCustomerLat() {
            return customerLat;
        }

        public void setCustomerLat(double customerLat) {
            this.customerLat = customerLat;
        }

        public double getCustomerLng() {
            return customerLng;
        }

        public void setCustomerLng(double customerLng) {
            this.customerLng = customerLng;
        }

        public double getDriverLat() {
            return driverLat;
        }

        public void setDriverLat(double driverLat) {
            this.driverLat = driverLat;
        }

        public double getDriverLng() {
            return driverLng;
        }

        public void setDriverLng(double driverLng) {
            this.driverLng = driverLng;
        }

        public int getDriverStatus() {
            return driverStatus;
        }

        public void setDriverStatus(int driverStatus) {
            this.driverStatus = driverStatus;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }
    }
}
