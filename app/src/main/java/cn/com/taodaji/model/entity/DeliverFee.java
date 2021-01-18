package cn.com.taodaji.model.entity;

import java.util.List;

public class DeliverFee {


    /**
     * err : 0
     * data : {"defaultTime":{"startTime":"10:00","time":"10:00--12:00","isDefault":"1","timeEntityId":1,"endTime":"12:00"},"deliverInfo":{"streetNumber":"12-12-12","deliverAddress":"襄阳樊城区中原街道中原路左岸春天2","isCreateAddress":0},"deliverScope":600,"isUpdateAddress":1,"pickupInfo":{"masterName":"李团长","shortPickupPoint":"","masterPhone":"13712345678","lon":30,"pickupPoint":"取货点","lat":110,"pickupAddress":"襄阳市樊城区七里河街道取货点地址","communityId":1},"serviceFee":[{"serviceName":"客户自提","deliveryType":1},{"serviceName":"送货上门","deliveryType":2}],"tips":{"info3":"当日同一用户的订单，同一时间送达","info1":"收取1元服务费给予团长。","info2":"收取5元服务费给予团长。"}}
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
         * defaultTime : {"startTime":"10:00","time":"10:00--12:00","isDefault":"1","timeEntityId":1,"endTime":"12:00"}
         * deliverInfo : {"streetNumber":"12-12-12","deliverAddress":"襄阳樊城区中原街道中原路左岸春天2","isCreateAddress":0}
         * deliverScope : 600
         * isUpdateAddress : 1
         * pickupInfo : {"masterName":"李团长","shortPickupPoint":"","masterPhone":"13712345678","lon":30,"pickupPoint":"取货点","lat":110,"pickupAddress":"襄阳市樊城区七里河街道取货点地址","communityId":1}
         * serviceFee : [{"serviceName":"客户自提","deliveryType":1},{"serviceName":"送货上门","deliveryType":2}]
         * tips : {"info3":"当日同一用户的订单，同一时间送达","info1":"收取1元服务费给予团长。","info2":"收取5元服务费给予团长。"}
         */

        private DefaultTimeBean defaultTime;
        private DeliverInfoBean deliverInfo;
        private int deliverScope;
        private int isUpdateAddress;
        private PickupInfoBean pickupInfo;
        private TipsBean tips;
        private List<ServiceFeeBean> serviceFee;

        public DefaultTimeBean getDefaultTime() {
            return defaultTime;
        }

        public void setDefaultTime(DefaultTimeBean defaultTime) {
            this.defaultTime = defaultTime;
        }

        public DeliverInfoBean getDeliverInfo() {
            return deliverInfo;
        }

        public void setDeliverInfo(DeliverInfoBean deliverInfo) {
            this.deliverInfo = deliverInfo;
        }

        public int getDeliverScope() {
            return deliverScope;
        }

        public void setDeliverScope(int deliverScope) {
            this.deliverScope = deliverScope;
        }

        public int getIsUpdateAddress() {
            return isUpdateAddress;
        }

        public void setIsUpdateAddress(int isUpdateAddress) {
            this.isUpdateAddress = isUpdateAddress;
        }

        public PickupInfoBean getPickupInfo() {
            return pickupInfo;
        }

        public void setPickupInfo(PickupInfoBean pickupInfo) {
            this.pickupInfo = pickupInfo;
        }

        public TipsBean getTips() {
            return tips;
        }

        public void setTips(TipsBean tips) {
            this.tips = tips;
        }

        public List<ServiceFeeBean> getServiceFee() {
            return serviceFee;
        }

        public void setServiceFee(List<ServiceFeeBean> serviceFee) {
            this.serviceFee = serviceFee;
        }

        public static class DefaultTimeBean {
            /**
             * startTime : 10:00
             * time : 10:00--12:00
             * isDefault : 1
             * timeEntityId : 1
             * endTime : 12:00
             */

            private String startTime;
            private String time;
            private String isDefault;
            private int timeEntityId;
            private String endTime;

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(String isDefault) {
                this.isDefault = isDefault;
            }

            public int getTimeEntityId() {
                return timeEntityId;
            }

            public void setTimeEntityId(int timeEntityId) {
                this.timeEntityId = timeEntityId;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }
        }

        public static class DeliverInfoBean {
            /**
             * streetNumber : 12-12-12
             * deliverAddress : 襄阳樊城区中原街道中原路左岸春天2
             * isCreateAddress : 0
             */

            private String streetNumber;
            private String deliverAddress;
            private int isCreateAddress;
            private String customerName;
            private String customerTel;

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerTel() {
                return customerTel;
            }

            public void setCustomerTel(String customerTel) {
                this.customerTel = customerTel;
            }

            public String getStreetNumber() {
                return streetNumber;
            }

            public void setStreetNumber(String streetNumber) {
                this.streetNumber = streetNumber;
            }

            public String getDeliverAddress() {
                return deliverAddress;
            }

            public void setDeliverAddress(String deliverAddress) {
                this.deliverAddress = deliverAddress;
            }

            public int getIsCreateAddress() {
                return isCreateAddress;
            }

            public void setIsCreateAddress(int isCreateAddress) {
                this.isCreateAddress = isCreateAddress;
            }
        }

        public static class PickupInfoBean {
            /**
             * masterName : 李团长
             * shortPickupPoint :
             * masterPhone : 13712345678
             * lon : 30
             * pickupPoint : 取货点
             * lat : 110
             * pickupAddress : 襄阳市樊城区七里河街道取货点地址
             * communityId : 1
             */

            private String masterName;
            private String shortPickupPoint;
            private String masterPhone;
            private double lon;
            private String pickupPoint;
            private double lat;
            private String pickupAddress;
            private int communityId;

            public String getMasterName() {
                return masterName;
            }

            public void setMasterName(String masterName) {
                this.masterName = masterName;
            }

            public String getShortPickupPoint() {
                return shortPickupPoint;
            }

            public void setShortPickupPoint(String shortPickupPoint) {
                this.shortPickupPoint = shortPickupPoint;
            }

            public String getMasterPhone() {
                return masterPhone;
            }

            public void setMasterPhone(String masterPhone) {
                this.masterPhone = masterPhone;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public String getPickupPoint() {
                return pickupPoint;
            }

            public void setPickupPoint(String pickupPoint) {
                this.pickupPoint = pickupPoint;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public String getPickupAddress() {
                return pickupAddress;
            }

            public void setPickupAddress(String pickupAddress) {
                this.pickupAddress = pickupAddress;
            }

            public int getCommunityId() {
                return communityId;
            }

            public void setCommunityId(int communityId) {
                this.communityId = communityId;
            }
        }

        public static class TipsBean {
            /**
             * info3 : 当日同一用户的订单，同一时间送达
             * info1 : 收取1元服务费给予团长。
             * info2 : 收取5元服务费给予团长。
             */

            private String info3;
            private String info1;
            private String info2;

            public String getInfo3() {
                return info3;
            }

            public void setInfo3(String info3) {
                this.info3 = info3;
            }

            public String getInfo1() {
                return info1;
            }

            public void setInfo1(String info1) {
                this.info1 = info1;
            }

            public String getInfo2() {
                return info2;
            }

            public void setInfo2(String info2) {
                this.info2 = info2;
            }
        }

        public static class ServiceFeeBean {
            /**
             * serviceName : 客户自提
             * deliveryType : 1
             */

            private String serviceName;
            private int deliveryType;

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public int getDeliveryType() {
                return deliveryType;
            }

            public void setDeliveryType(int deliveryType) {
                this.deliveryType = deliveryType;
            }
        }
    }
}
