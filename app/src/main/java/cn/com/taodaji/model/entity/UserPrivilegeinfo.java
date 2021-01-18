package cn.com.taodaji.model.entity;

import java.util.List;

public class UserPrivilegeinfo {


    /**
     * err : 0
     * data : {"id":11,"userCode":"15271967861","siteId":2,"nickName":"胡杰","realName":null,"password":null,"token":null,"mobile":"15271967861","headUrl":null,"age":null,"sex":null,"idCardNo":null,"level":0,"type":null,"status":1,"deviceToken":null,"shareCode":null,"lastSignTime":"2019-08-15 13:36:32","signCount":1,"updateTime":"2019-08-15 13:36:36","createTime":null,"invitationCode":"SDFIGF11","shipAddress":[{"id":219062,"userId":"11","province":null,"city":null,"area":null,"detailAddress":"汉江创投产业园","status":0,"isDefault":0,"lon":"111.644015","lat":"32.392676","gid":"0f9af4f1-44a4-4bc0-8384-870474e2fa87","recevingPersion":"胡杰","recevingMobile":"15271967861","updateTime":null,"createTime":null},{"id":219263,"userId":"11","province":null,"city":null,"area":null,"detailAddress":"襄城区隆中大道西诸葛书院","status":1,"isDefault":0,"lon":"112.038092","lat":"32.009068","gid":"e23dd553-b5d2-40d4-9de4-855a9224fb77","recevingPersion":"陈晓改","recevingMobile":"13972255990","updateTime":null,"createTime":null},{"id":222784,"userId":"11","province":null,"city":null,"area":null,"detailAddress":null,"status":0,"isDefault":0,"lon":null,"lat":null,"gid":null,"recevingPersion":null,"recevingMobile":null,"updateTime":null,"createTime":null},{"id":251838,"userId":"11","province":null,"city":"2","area":null,"detailAddress":"汉江创投产业园测试新增11","status":1,"isDefault":1,"lon":"111.644015","lat":"32.392676","gid":"0f9af4f1-44a4-4bc0-8384-870474e2fa87","recevingPersion":"胡杰1","recevingMobile":"15271967861","updateTime":"2019-08-15 09:58:16","createTime":"2019-08-15 09:58:16"}],"integral":120,"money":0,"firstOrderStatus":null,"privileges":[{"id":1,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":1,"title":"积分特权","linkType":null,"linkUrl":null,"status":null},{"id":2,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":5,"title":"购物特权","linkType":null,"linkUrl":null,"status":null},{"id":4,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":15,"title":"免费退换货","linkType":null,"linkUrl":null,"status":null},{"id":5,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":20,"title":"专属客服","linkType":null,"linkUrl":null,"status":null},{"id":6,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":25,"title":"课程培训","linkType":null,"linkUrl":null,"status":null},{"id":7,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":30,"title":"行业协会","linkType":null,"linkUrl":null,"status":null},{"id":8,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":35,"title":"代缴协会会费","linkType":null,"linkUrl":null,"status":null}],"approachs":null,"myWaiePayment":null}
     * error : null
     * msg : Success
     * errorCode : null
     */

    private int err;
    private DataBean data;
    private Object error;
    private String msg;
    private Object errorCode;

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

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public static class DataBean {
        /**
         * id : 11
         * userCode : 15271967861
         * siteId : 2
         * nickName : 胡杰
         * realName : null
         * password : null
         * token : null
         * mobile : 15271967861
         * headUrl : null
         * age : null
         * sex : null
         * idCardNo : null
         * level : 0
         * type : null
         * status : 1
         * deviceToken : null
         * shareCode : null
         * lastSignTime : 2019-08-15 13:36:32
         * signCount : 1
         * updateTime : 2019-08-15 13:36:36
         * createTime : null
         * invitationCode : SDFIGF11
         * shipAddress : [{"id":219062,"userId":"11","province":null,"city":null,"area":null,"detailAddress":"汉江创投产业园","status":0,"isDefault":0,"lon":"111.644015","lat":"32.392676","gid":"0f9af4f1-44a4-4bc0-8384-870474e2fa87","recevingPersion":"胡杰","recevingMobile":"15271967861","updateTime":null,"createTime":null},{"id":219263,"userId":"11","province":null,"city":null,"area":null,"detailAddress":"襄城区隆中大道西诸葛书院","status":1,"isDefault":0,"lon":"112.038092","lat":"32.009068","gid":"e23dd553-b5d2-40d4-9de4-855a9224fb77","recevingPersion":"陈晓改","recevingMobile":"13972255990","updateTime":null,"createTime":null},{"id":222784,"userId":"11","province":null,"city":null,"area":null,"detailAddress":null,"status":0,"isDefault":0,"lon":null,"lat":null,"gid":null,"recevingPersion":null,"recevingMobile":null,"updateTime":null,"createTime":null},{"id":251838,"userId":"11","province":null,"city":"2","area":null,"detailAddress":"汉江创投产业园测试新增11","status":1,"isDefault":1,"lon":"111.644015","lat":"32.392676","gid":"0f9af4f1-44a4-4bc0-8384-870474e2fa87","recevingPersion":"胡杰1","recevingMobile":"15271967861","updateTime":"2019-08-15 09:58:16","createTime":"2019-08-15 09:58:16"}]
         * integral : 120
         * money : 0.0
         * firstOrderStatus : null
         * privileges : [{"id":1,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":1,"title":"积分特权","linkType":null,"linkUrl":null,"status":null},{"id":2,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":5,"title":"购物特权","linkType":null,"linkUrl":null,"status":null},{"id":4,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":15,"title":"免费退换货","linkType":null,"linkUrl":null,"status":null},{"id":5,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":20,"title":"专属客服","linkType":null,"linkUrl":null,"status":null},{"id":6,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":25,"title":"课程培训","linkType":null,"linkUrl":null,"status":null},{"id":7,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":30,"title":"行业协会","linkType":null,"linkUrl":null,"status":null},{"id":8,"menuPic":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png","sort":35,"title":"代缴协会会费","linkType":null,"linkUrl":null,"status":null}]
         * approachs : null
         * myWaiePayment : null
         */

        private int id;
        private String userCode;
        private int siteId;
        private String nickName;
        private Object realName;
        private Object password;
        private Object token;
        private String mobile;
        private Object headUrl;
        private Object age;
        private Object sex;
        private Object idCardNo;
        private int level;
        private Object type;
        private int status;
        private Object deviceToken;
        private Object shareCode;
        private String lastSignTime;
        private int signCount;
        private String updateTime;
        private Object createTime;
        private String invitationCode;
        private int integral;
        private double money;
        private Object firstOrderStatus;
        private Object approachs;
        private Object myWaiePayment;
        private List<ShipAddressBean> shipAddress;
        private List<PrivilegesBean> privileges;
        private String expireTime;
        private String upIntegral;

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public String getUpIntegral() {
            return upIntegral;
        }

        public void setUpIntegral(String upIntegral) {
            this.upIntegral = upIntegral;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public int getSiteId() {
            return siteId;
        }

        public void setSiteId(int siteId) {
            this.siteId = siteId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
            this.realName = realName;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(Object headUrl) {
            this.headUrl = headUrl;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(Object idCardNo) {
            this.idCardNo = idCardNo;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(Object deviceToken) {
            this.deviceToken = deviceToken;
        }

        public Object getShareCode() {
            return shareCode;
        }

        public void setShareCode(Object shareCode) {
            this.shareCode = shareCode;
        }

        public String getLastSignTime() {
            return lastSignTime;
        }

        public void setLastSignTime(String lastSignTime) {
            this.lastSignTime = lastSignTime;
        }

        public int getSignCount() {
            return signCount;
        }

        public void setSignCount(int signCount) {
            this.signCount = signCount;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(String invitationCode) {
            this.invitationCode = invitationCode;
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

        public Object getFirstOrderStatus() {
            return firstOrderStatus;
        }

        public void setFirstOrderStatus(Object firstOrderStatus) {
            this.firstOrderStatus = firstOrderStatus;
        }

        public Object getApproachs() {
            return approachs;
        }

        public void setApproachs(Object approachs) {
            this.approachs = approachs;
        }

        public Object getMyWaiePayment() {
            return myWaiePayment;
        }

        public void setMyWaiePayment(Object myWaiePayment) {
            this.myWaiePayment = myWaiePayment;
        }

        public List<ShipAddressBean> getShipAddress() {
            return shipAddress;
        }

        public void setShipAddress(List<ShipAddressBean> shipAddress) {
            this.shipAddress = shipAddress;
        }

        public List<PrivilegesBean> getPrivileges() {
            return privileges;
        }

        public void setPrivileges(List<PrivilegesBean> privileges) {
            this.privileges = privileges;
        }

        public static class ShipAddressBean {
            /**
             * id : 219062
             * userId : 11
             * province : null
             * city : null
             * area : null
             * detailAddress : 汉江创投产业园
             * status : 0
             * isDefault : 0
             * lon : 111.644015
             * lat : 32.392676
             * gid : 0f9af4f1-44a4-4bc0-8384-870474e2fa87
             * recevingPersion : 胡杰
             * recevingMobile : 15271967861
             * updateTime : null
             * createTime : null
             */

            private int id;
            private String userId;
            private Object province;
            private Object city;
            private Object area;
            private String detailAddress;
            private int status;
            private int isDefault;
            private String lon;
            private String lat;
            private String gid;
            private String recevingPersion;
            private String recevingMobile;
            private Object updateTime;
            private Object createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public Object getProvince() {
                return province;
            }

            public void setProvince(Object province) {
                this.province = province;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }

            public Object getArea() {
                return area;
            }

            public void setArea(Object area) {
                this.area = area;
            }

            public String getDetailAddress() {
                return detailAddress;
            }

            public void setDetailAddress(String detailAddress) {
                this.detailAddress = detailAddress;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(int isDefault) {
                this.isDefault = isDefault;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getRecevingPersion() {
                return recevingPersion;
            }

            public void setRecevingPersion(String recevingPersion) {
                this.recevingPersion = recevingPersion;
            }

            public String getRecevingMobile() {
                return recevingMobile;
            }

            public void setRecevingMobile(String recevingMobile) {
                this.recevingMobile = recevingMobile;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }
        }

        public static class PrivilegesBean {
            /**
             * id : 1
             * menuPic : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190815122111db4a3126.png
             * sort : 1
             * title : 积分特权
             * linkType : null
             * linkUrl : null
             * status : null
             */

            private int id;
            private String menuPic;
            private int sort;
            private String title;
            private Object linkType;
            private Object linkUrl;
            private Object status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMenuPic() {
                return menuPic;
            }

            public void setMenuPic(String menuPic) {
                this.menuPic = menuPic;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getLinkType() {
                return linkType;
            }

            public void setLinkType(Object linkType) {
                this.linkType = linkType;
            }

            public Object getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(Object linkUrl) {
                this.linkUrl = linkUrl;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }
        }
    }
}
