package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by zhaowenlong on 2019/3/16.
 */
public class ShopDetailBean {


    /**
     * err : 0
     * data : {"entityId":7848,"createTime":"2019-05-06 13:17","username":"15100000061","realname":"陈瑞","hotelName":"烧饼6","phoneNumber":"15100000061","provinceId":13,"cityId":193,"site":2,"siteName":"襄阳市","hotelAddress":"高新区春园路大吕沟桥汉江水云间一楼5","isActive":1,"userType":0,"markCode":"37f2f7106fbe11e98434df4b24969728_15100000061","flag":1,"remarks":"","role":0,"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1905061317184d682bed.jpg","verifyInfo":"审核通过!","bzlicenceUrl":"","identificationCard":"420682199403075047","identificationImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190506135018f33f51ea.jpg","authStatus":1,"refundMoney":0,"rechargeMoney":0,"money":0,"subUserId":-1,"hasVerfify":0,"verifyCode":"BRQC7848","isChargeFreight":1,"address":"湖北省老河口市薛集镇薛集村七组26号","gender":"女","frontageIdcardImageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190506135018f33f51ea.jpg","backIdcardImageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190506135019c12d4f76.jpg","birthday":762969600000,"expirationDate":"20210622","isAuth":1,"isInvoice":0,"parentId":7848,"imgCheckStatus":0,"imgRefuseInfo":"","licenceUrlCheckStatus":0,"licenceUrlRefuseInfo":"","alias":"","headUrl":"","commissionerName":"","commissionerTelephone":"","hotelType":"黄焖鸡米饭","leaderId":7848,"leader":"陈瑞","leaderPhone":"15100000061","subAcount":[{"isCreater":"N","employeesEntityId":294,"phone":"15100000061","flag":1,"enterpriseCode":"烧饼6","customerEntityId":7848,"nickName":"陈瑞","workName":"哈哈","role":0,"isLeader":"Y","markCode":"37f2f7106fbe11e98434df4b24969728_15100000061","parentCustomerEntityId":7848,"empFlag":0},{"isCreater":"Y","employeesEntityId":295,"phone":"15100000001","flag":1,"enterpriseCode":"烧饼1","customerEntityId":7802,"nickName":"黄油","workName":"哈哈","role":0,"isLeader":"N","markCode":"6367fb906ed111e9941baf93f3072cd8_15100000001","parentCustomerEntityId":7848,"empFlag":0},{"isCreater":"N","employeesEntityId":315,"phone":"15700002001","flag":2,"enterpriseCode":"烧饼6","customerEntityId":7860,"nickName":"小林","workName":"小林","role":2,"isLeader":"N","markCode":"37f2f7106fbe11e98434df4b24969728_15100000061","parentCustomerEntityId":7848,"empFlag":0}],"shipAddr":{"entity_type_id":2,"deliveredTimeEnd":"10：00","street":"高新区春园路大吕沟桥汉江水云间一楼5","lastname":"烧饼6","truckTime":"1","entity_id":5946,"hotelName":"烧饼6","name":"哈哈","gender":0,"created_at":1557119839000,"fence_gid":"","lat":-1,"website_id":2,"parent_id":7848,"truck_time":"1","lon":-1,"middlename":"哈哈","deliveredTime":"9：00","is_active":1,"country_id":"CN","is_default":1,"delivered_time_end":"10：00","firstname":"哈哈","attribute_set_id":0,"country":"中国","isActive":"1","updated_at":1557119839000,"address":"高新区春园路大吕沟桥汉江水云间一楼5","region_code":0,"city_code":0,"address_type":1,"telephone":"15100000061","delivered_time":"9：00","increment_id":"","county_code":-1},"hotelTypeList":[{"parentId":4,"level":3,"name":"黄焖鸡米饭","hotelTypeId":6}]}
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
         * entityId : 7848
         * createTime : 2019-05-06 13:17
         * username : 15100000061
         * realname : 陈瑞
         * hotelName : 烧饼6
         * phoneNumber : 15100000061
         * provinceId : 13
         * cityId : 193
         * site : 2
         * siteName : 襄阳市
         * hotelAddress : 高新区春园路大吕沟桥汉江水云间一楼5
         * isActive : 1
         * userType : 0
         * markCode : 37f2f7106fbe11e98434df4b24969728_15100000061
         * flag : 1
         * remarks :
         * role : 0
         * imageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1905061317184d682bed.jpg
         * verifyInfo : 审核通过!
         * bzlicenceUrl :
         * identificationCard : 420682199403075047
         * identificationImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190506135018f33f51ea.jpg
         * authStatus : 1
         * refundMoney : 0
         * rechargeMoney : 0
         * money : 0
         * subUserId : -1
         * hasVerfify : 0
         * verifyCode : BRQC7848
         * isChargeFreight : 1
         * address : 湖北省老河口市薛集镇薛集村七组26号
         * gender : 女
         * frontageIdcardImageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190506135018f33f51ea.jpg
         * backIdcardImageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/190506135019c12d4f76.jpg
         * birthday : 762969600000
         * expirationDate : 20210622
         * isAuth : 1
         * isInvoice : 0
         * parentId : 7848
         * imgCheckStatus : 0
         * imgRefuseInfo :
         * licenceUrlCheckStatus : 0
         * licenceUrlRefuseInfo :
         * alias :
         * headUrl :
         * commissionerName :
         * commissionerTelephone :
         * hotelType : 黄焖鸡米饭
         * leaderId : 7848
         * leader : 陈瑞
         * leaderPhone : 15100000061
         * subAcount : [{"isCreater":"N","employeesEntityId":294,"phone":"15100000061","flag":1,"enterpriseCode":"烧饼6","customerEntityId":7848,"nickName":"陈瑞","workName":"哈哈","role":0,"isLeader":"Y","markCode":"37f2f7106fbe11e98434df4b24969728_15100000061","parentCustomerEntityId":7848,"empFlag":0},{"isCreater":"Y","employeesEntityId":295,"phone":"15100000001","flag":1,"enterpriseCode":"烧饼1","customerEntityId":7802,"nickName":"黄油","workName":"哈哈","role":0,"isLeader":"N","markCode":"6367fb906ed111e9941baf93f3072cd8_15100000001","parentCustomerEntityId":7848,"empFlag":0},{"isCreater":"N","employeesEntityId":315,"phone":"15700002001","flag":2,"enterpriseCode":"烧饼6","customerEntityId":7860,"nickName":"小林","workName":"小林","role":2,"isLeader":"N","markCode":"37f2f7106fbe11e98434df4b24969728_15100000061","parentCustomerEntityId":7848,"empFlag":0}]
         * shipAddr : {"entity_type_id":2,"deliveredTimeEnd":"10：00","street":"高新区春园路大吕沟桥汉江水云间一楼5","lastname":"烧饼6","truckTime":"1","entity_id":5946,"hotelName":"烧饼6","name":"哈哈","gender":0,"created_at":1557119839000,"fence_gid":"","lat":-1,"website_id":2,"parent_id":7848,"truck_time":"1","lon":-1,"middlename":"哈哈","deliveredTime":"9：00","is_active":1,"country_id":"CN","is_default":1,"delivered_time_end":"10：00","firstname":"哈哈","attribute_set_id":0,"country":"中国","isActive":"1","updated_at":1557119839000,"address":"高新区春园路大吕沟桥汉江水云间一楼5","region_code":0,"city_code":0,"address_type":1,"telephone":"15100000061","delivered_time":"9：00","increment_id":"","county_code":-1}
         * hotelTypeList : [{"parentId":4,"level":3,"name":"黄焖鸡米饭","hotelTypeId":6}]
         */

        private int entityId;
        private String createTime;
        private String username;
        private String realname;
        private String hotelName;
        private String phoneNumber;
        private int provinceId;
        private int cityId;
        private int site;
        private String siteName;
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
        private int refundMoney;
        private int rechargeMoney;
        private int money;
        private int subUserId;
        private int hasVerfify;
        private String verifyCode;
        private int isChargeFreight;
        private String address;
        private String gender;
        private String frontageIdcardImageUrl;
        private String backIdcardImageUrl;
        private long birthday;
        private String expirationDate;
        private int isAuth;
        private int isInvoice;
        private int parentId;
        private int imgCheckStatus;
        private String imgRefuseInfo;
        private int licenceUrlCheckStatus;
        private String licenceUrlRefuseInfo;
        private String alias;
        private String headUrl;
        private String commissionerName;
        private String commissionerTelephone;
        private String hotelType;
        private int leaderId;
        private String leader;
        private String leaderPhone;
        private ShipAddrBean shipAddr;
        private List<SubAcountBean> subAcount;
        private List<HotelTypeListBean> hotelTypeList;

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

        public int getRefundMoney() {
            return refundMoney;
        }

        public void setRefundMoney(int refundMoney) {
            this.refundMoney = refundMoney;
        }

        public int getRechargeMoney() {
            return rechargeMoney;
        }

        public void setRechargeMoney(int rechargeMoney) {
            this.rechargeMoney = rechargeMoney;
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

        public int getHasVerfify() {
            return hasVerfify;
        }

        public void setHasVerfify(int hasVerfify) {
            this.hasVerfify = hasVerfify;
        }

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public int getIsChargeFreight() {
            return isChargeFreight;
        }

        public void setIsChargeFreight(int isChargeFreight) {
            this.isChargeFreight = isChargeFreight;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getFrontageIdcardImageUrl() {
            return frontageIdcardImageUrl;
        }

        public void setFrontageIdcardImageUrl(String frontageIdcardImageUrl) {
            this.frontageIdcardImageUrl = frontageIdcardImageUrl;
        }

        public String getBackIdcardImageUrl() {
            return backIdcardImageUrl;
        }

        public void setBackIdcardImageUrl(String backIdcardImageUrl) {
            this.backIdcardImageUrl = backIdcardImageUrl;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        public int getIsAuth() {
            return isAuth;
        }

        public void setIsAuth(int isAuth) {
            this.isAuth = isAuth;
        }

        public int getIsInvoice() {
            return isInvoice;
        }

        public void setIsInvoice(int isInvoice) {
            this.isInvoice = isInvoice;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getImgCheckStatus() {
            return imgCheckStatus;
        }

        public void setImgCheckStatus(int imgCheckStatus) {
            this.imgCheckStatus = imgCheckStatus;
        }

        public String getImgRefuseInfo() {
            return imgRefuseInfo;
        }

        public void setImgRefuseInfo(String imgRefuseInfo) {
            this.imgRefuseInfo = imgRefuseInfo;
        }

        public int getLicenceUrlCheckStatus() {
            return licenceUrlCheckStatus;
        }

        public void setLicenceUrlCheckStatus(int licenceUrlCheckStatus) {
            this.licenceUrlCheckStatus = licenceUrlCheckStatus;
        }

        public String getLicenceUrlRefuseInfo() {
            return licenceUrlRefuseInfo;
        }

        public void setLicenceUrlRefuseInfo(String licenceUrlRefuseInfo) {
            this.licenceUrlRefuseInfo = licenceUrlRefuseInfo;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getCommissionerName() {
            return commissionerName;
        }

        public void setCommissionerName(String commissionerName) {
            this.commissionerName = commissionerName;
        }

        public String getCommissionerTelephone() {
            return commissionerTelephone;
        }

        public void setCommissionerTelephone(String commissionerTelephone) {
            this.commissionerTelephone = commissionerTelephone;
        }

        public String getHotelType() {
            return hotelType;
        }

        public void setHotelType(String hotelType) {
            this.hotelType = hotelType;
        }

        public int getLeaderId() {
            return leaderId;
        }

        public void setLeaderId(int leaderId) {
            this.leaderId = leaderId;
        }

        public String getLeader() {
            return leader;
        }

        public void setLeader(String leader) {
            this.leader = leader;
        }

        public String getLeaderPhone() {
            return leaderPhone;
        }

        public void setLeaderPhone(String leaderPhone) {
            this.leaderPhone = leaderPhone;
        }

        public ShipAddrBean getShipAddr() {
            return shipAddr;
        }

        public void setShipAddr(ShipAddrBean shipAddr) {
            this.shipAddr = shipAddr;
        }

        public List<SubAcountBean> getSubAcount() {
            return subAcount;
        }

        public void setSubAcount(List<SubAcountBean> subAcount) {
            this.subAcount = subAcount;
        }

        public List<HotelTypeListBean> getHotelTypeList() {
            return hotelTypeList;
        }

        public void setHotelTypeList(List<HotelTypeListBean> hotelTypeList) {
            this.hotelTypeList = hotelTypeList;
        }

        public static class ShipAddrBean {
            /**
             * entity_type_id : 2
             * deliveredTimeEnd : 10：00
             * street : 高新区春园路大吕沟桥汉江水云间一楼5
             * lastname : 烧饼6
             * truckTime : 1
             * entity_id : 5946
             * hotelName : 烧饼6
             * name : 哈哈
             * gender : 0
             * created_at : 1557119839000
             * fence_gid :
             * lat : -1
             * website_id : 2
             * parent_id : 7848
             * truck_time : 1
             * lon : -1
             * middlename : 哈哈
             * deliveredTime : 9：00
             * is_active : 1
             * country_id : CN
             * is_default : 1
             * delivered_time_end : 10：00
             * firstname : 哈哈
             * attribute_set_id : 0
             * country : 中国
             * isActive : 1
             * updated_at : 1557119839000
             * address : 高新区春园路大吕沟桥汉江水云间一楼5
             * region_code : 0
             * city_code : 0
             * address_type : 1
             * telephone : 15100000061
             * delivered_time : 9：00
             * increment_id :
             * county_code : -1
             */

            private int entity_type_id;
            private String deliveredTimeEnd;
            private String street;
            private String lastname;
            private String truckTime;
            private int entity_id;
            private String hotelName;
            private String name;
            private int gender;
            private long created_at;
            private String fence_gid;
            private double lat;
            private int website_id;
            private int parent_id;
            private String truck_time;
            private double lon;
            private String middlename;
            private String deliveredTime;
            private int is_active;
            private String country_id;
            private int is_default;
            private String delivered_time_end;
            private String firstname;
            private int attribute_set_id;
            private String country;
            private String isActive;
            private long updated_at;
            private String address;
            private int region_code;
            private int city_code;
            private int address_type;
            private String telephone;
            private String delivered_time;
            private String increment_id;
            private String street_number;
            private int county_code;

            public int getEntity_type_id() {
                return entity_type_id;
            }

            public void setEntity_type_id(int entity_type_id) {
                this.entity_type_id = entity_type_id;
            }

            public String getDeliveredTimeEnd() {
                return deliveredTimeEnd;
            }

            public void setDeliveredTimeEnd(String deliveredTimeEnd) {
                this.deliveredTimeEnd = deliveredTimeEnd;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getLastname() {
                return lastname;
            }

            public void setLastname(String lastname) {
                this.lastname = lastname;
            }

            public String getTruckTime() {
                return truckTime;
            }

            public void setTruckTime(String truckTime) {
                this.truckTime = truckTime;
            }

            public int getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(int entity_id) {
                this.entity_id = entity_id;
            }

            public String getHotelName() {
                return hotelName;
            }

            public void setHotelName(String hotelName) {
                this.hotelName = hotelName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }

            public String getFence_gid() {
                return fence_gid;
            }

            public void setFence_gid(String fence_gid) {
                this.fence_gid = fence_gid;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public int getWebsite_id() {
                return website_id;
            }

            public void setWebsite_id(int website_id) {
                this.website_id = website_id;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public String getTruck_time() {
                return truck_time;
            }

            public void setTruck_time(String truck_time) {
                this.truck_time = truck_time;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public String getMiddlename() {
                return middlename;
            }

            public void setMiddlename(String middlename) {
                this.middlename = middlename;
            }

            public String getDeliveredTime() {
                return deliveredTime;
            }

            public void setDeliveredTime(String deliveredTime) {
                this.deliveredTime = deliveredTime;
            }

            public int getIs_active() {
                return is_active;
            }

            public void setIs_active(int is_active) {
                this.is_active = is_active;
            }

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }

            public String getDelivered_time_end() {
                return delivered_time_end;
            }

            public void setDelivered_time_end(String delivered_time_end) {
                this.delivered_time_end = delivered_time_end;
            }

            public String getFirstname() {
                return firstname;
            }

            public void setFirstname(String firstname) {
                this.firstname = firstname;
            }

            public int getAttribute_set_id() {
                return attribute_set_id;
            }

            public void setAttribute_set_id(int attribute_set_id) {
                this.attribute_set_id = attribute_set_id;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getIsActive() {
                return isActive;
            }

            public void setIsActive(String isActive) {
                this.isActive = isActive;
            }

            public long getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(long updated_at) {
                this.updated_at = updated_at;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getRegion_code() {
                return region_code;
            }

            public void setRegion_code(int region_code) {
                this.region_code = region_code;
            }

            public int getCity_code() {
                return city_code;
            }

            public void setCity_code(int city_code) {
                this.city_code = city_code;
            }

            public int getAddress_type() {
                return address_type;
            }

            public void setAddress_type(int address_type) {
                this.address_type = address_type;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getDelivered_time() {
                return delivered_time;
            }

            public void setDelivered_time(String delivered_time) {
                this.delivered_time = delivered_time;
            }

            public String getIncrement_id() {
                return increment_id;
            }

            public void setIncrement_id(String increment_id) {
                this.increment_id = increment_id;
            }

            public int getCounty_code() {
                return county_code;
            }

            public void setCounty_code(int county_code) {
                this.county_code = county_code;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }
        }

        public static class SubAcountBean {
            /**
             * isCreater : N
             * employeesEntityId : 294
             * phone : 15100000061
             * flag : 1
             * enterpriseCode : 烧饼6
             * customerEntityId : 7848
             * nickName : 陈瑞
             * workName : 哈哈
             * role : 0
             * isLeader : Y
             * markCode : 37f2f7106fbe11e98434df4b24969728_15100000061
             * parentCustomerEntityId : 7848
             * empFlag : 0
             */

            private String isCreater;
            private int employeesEntityId;
            private String phone;
            private int flag;
            private String enterpriseCode;
            private int customerEntityId;
            private String nickName;
            private String workName;
            private int role;
            private String isLeader;
            private String markCode;
            private int parentCustomerEntityId;
            private int empFlag;

            public String getIsCreater() {
                return isCreater;
            }

            public void setIsCreater(String isCreater) {
                this.isCreater = isCreater;
            }

            public int getEmployeesEntityId() {
                return employeesEntityId;
            }

            public void setEmployeesEntityId(int employeesEntityId) {
                this.employeesEntityId = employeesEntityId;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public String getEnterpriseCode() {
                return enterpriseCode;
            }

            public void setEnterpriseCode(String enterpriseCode) {
                this.enterpriseCode = enterpriseCode;
            }

            public int getCustomerEntityId() {
                return customerEntityId;
            }

            public void setCustomerEntityId(int customerEntityId) {
                this.customerEntityId = customerEntityId;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getWorkName() {
                return workName;
            }

            public void setWorkName(String workName) {
                this.workName = workName;
            }

            public int getRole() {
                return role;
            }

            public void setRole(int role) {
                this.role = role;
            }

            public String getIsLeader() {
                return isLeader;
            }

            public void setIsLeader(String isLeader) {
                this.isLeader = isLeader;
            }

            public String getMarkCode() {
                return markCode;
            }

            public void setMarkCode(String markCode) {
                this.markCode = markCode;
            }

            public int getParentCustomerEntityId() {
                return parentCustomerEntityId;
            }

            public void setParentCustomerEntityId(int parentCustomerEntityId) {
                this.parentCustomerEntityId = parentCustomerEntityId;
            }

            public int getEmpFlag() {
                return empFlag;
            }

            public void setEmpFlag(int empFlag) {
                this.empFlag = empFlag;
            }
        }

        public static class HotelTypeListBean {
            /**
             * parentId : 4
             * level : 3
             * name : 黄焖鸡米饭
             * hotelTypeId : 6
             */

            private int parentId;
            private int level;
            private String name;
            private int hotelTypeId;

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getHotelTypeId() {
                return hotelTypeId;
            }

            public void setHotelTypeId(int hotelTypeId) {
                this.hotelTypeId = hotelTypeId;
            }
        }
    }
}
