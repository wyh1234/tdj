package cn.com.taodaji.model.entity;

/**
 * Created by zhaowenlong on 2019/3/16.
 */
public class ChangeHotelBean {

    /**
     * err : 0
     * data : {"entityId":1,"createTime":"2017-03-21 11:20","username":"18571161290","realname":"又又","hotelName":"MOMO酒店","phoneNumber":"18571161290","site":2,"siteName":"襄阳","provinceId":13,"cityId":193,"hotelAddress":"樊城区人民路72号","isActive":1,"userType":0,"markCode":"8fa55eb0095111e7a85ecb189ce58509_18571161280","flag":2,"remarks":"","role":1,"imageUrl":"","verifyInfo":"审核通过!","bzlicenceUrl":"","identificationCard":"","identificationImage":"","authStatus":1,"money":0,"subUserId":5,"loginUserId":5,"orderNumber":{"seller_print_goods":0,"wait_buyer_confirm_goods":0,"wait_seller_send_goods":2,"wait_buyer_pay":0,"trade_success":4,"wait_seller_confirm_goods":0}}
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
         * entityId : 1
         * createTime : 2017-03-21 11:20
         * username : 18571161290
         * realname : 又又
         * hotelName : MOMO酒店
         * phoneNumber : 18571161290
         * site : 2
         * siteName : 襄阳
         * provinceId : 13
         * cityId : 193
         * hotelAddress : 樊城区人民路72号
         * isActive : 1
         * userType : 0
         * markCode : 8fa55eb0095111e7a85ecb189ce58509_18571161280
         * flag : 2
         * remarks :
         * role : 1
         * imageUrl :
         * verifyInfo : 审核通过!
         * bzlicenceUrl :
         * identificationCard :
         * identificationImage :
         * authStatus : 1
         * money : 0
         * subUserId : 5
         * loginUserId : 5
         * orderNumber : {"seller_print_goods":0,"wait_buyer_confirm_goods":0,"wait_seller_send_goods":2,"wait_buyer_pay":0,"trade_success":4,"wait_seller_confirm_goods":0}
         */

        private int entityId;
        private String createTime;
        private String username;
        private String realname;
        private String hotelName;
        private String phoneNumber;
        private int site;
        private String siteName;
        private int provinceId;
        private int cityId;
        private String hotelAddress;
        private int isActive;
        private int userType;
        private String markCode;
        private int flag;
        private String remarks;
        private int role;
        private String imageUrl;
        private String verifyInfo;
        private String bzlicenceUrl;
        private String identificationCard;
        private String identificationImage;
        private int authStatus;
        private int money;
        private int subUserId;
        private int loginUserId;
        private OrderNumberBean orderNumber;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public int getSite() {
            return site;
        }

        public void setSite(int site) {
            this.site = site;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getHotelAddress() {
            return hotelAddress;
        }

        public void setHotelAddress(String hotelAddress) {
            this.hotelAddress = hotelAddress;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getMarkCode() {
            return markCode;
        }

        public void setMarkCode(String markCode) {
            this.markCode = markCode;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getVerifyInfo() {
            return verifyInfo;
        }

        public void setVerifyInfo(String verifyInfo) {
            this.verifyInfo = verifyInfo;
        }

        public String getBzlicenceUrl() {
            return bzlicenceUrl;
        }

        public void setBzlicenceUrl(String bzlicenceUrl) {
            this.bzlicenceUrl = bzlicenceUrl;
        }

        public String getIdentificationCard() {
            return identificationCard;
        }

        public void setIdentificationCard(String identificationCard) {
            this.identificationCard = identificationCard;
        }

        public String getIdentificationImage() {
            return identificationImage;
        }

        public void setIdentificationImage(String identificationImage) {
            this.identificationImage = identificationImage;
        }

        public int getAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(int authStatus) {
            this.authStatus = authStatus;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getSubUserId() {
            return subUserId;
        }

        public void setSubUserId(int subUserId) {
            this.subUserId = subUserId;
        }

        public int getLoginUserId() {
            return loginUserId;
        }

        public void setLoginUserId(int loginUserId) {
            this.loginUserId = loginUserId;
        }

        public OrderNumberBean getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(OrderNumberBean orderNumber) {
            this.orderNumber = orderNumber;
        }

        public static class OrderNumberBean {
            /**
             * seller_print_goods : 0
             * wait_buyer_confirm_goods : 0
             * wait_seller_send_goods : 2
             * wait_buyer_pay : 0
             * trade_success : 4
             * wait_seller_confirm_goods : 0
             */

            private int seller_print_goods;
            private int wait_buyer_confirm_goods;
            private int wait_seller_send_goods;
            private int wait_buyer_pay;
            private int trade_success;
            private int wait_seller_confirm_goods;

            public int getSeller_print_goods() {
                return seller_print_goods;
            }

            public void setSeller_print_goods(int seller_print_goods) {
                this.seller_print_goods = seller_print_goods;
            }

            public int getWait_buyer_confirm_goods() {
                return wait_buyer_confirm_goods;
            }

            public void setWait_buyer_confirm_goods(int wait_buyer_confirm_goods) {
                this.wait_buyer_confirm_goods = wait_buyer_confirm_goods;
            }

            public int getWait_seller_send_goods() {
                return wait_seller_send_goods;
            }

            public void setWait_seller_send_goods(int wait_seller_send_goods) {
                this.wait_seller_send_goods = wait_seller_send_goods;
            }

            public int getWait_buyer_pay() {
                return wait_buyer_pay;
            }

            public void setWait_buyer_pay(int wait_buyer_pay) {
                this.wait_buyer_pay = wait_buyer_pay;
            }

            public int getTrade_success() {
                return trade_success;
            }

            public void setTrade_success(int trade_success) {
                this.trade_success = trade_success;
            }

            public int getWait_seller_confirm_goods() {
                return wait_seller_confirm_goods;
            }

            public void setWait_seller_confirm_goods(int wait_seller_confirm_goods) {
                this.wait_seller_confirm_goods = wait_seller_confirm_goods;
            }
        }
    }
}
