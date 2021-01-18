package cn.com.taodaji.model.entity;

import java.util.List;

public class IntegralItem {


    /**
     * status : 200
     * data : [{"limit":0,"offset":0,"id":72,"type":1,"userId":122,"createTime":"2019-08-02 13:10:04","integral":20,"isExpired":0,"nickName":null,"mobile":"18671081687","typeName":"分享"}]
     * totalCount : null
     * error : null
     * message : null
     * errorCode : null
     */

    private int err;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * limit : 0
         * offset : 0
         * id : 72
         * type : 1
         * userId : 122
         * createTime : 2019-08-02 13:10:04
         * integral : 20
         * isExpired : 0
         * nickName : null
         * mobile : 18671081687
         * typeName : 分享
         */

        private int limit;
        private int offset;
        private int id;
        private int type;
        private int userId;
        private String createTime;
        private String integral;
        private String integralStr;
        private int isExpired;
        private String nickName;
        private String mobile;
        private String typeName;
        private String extOrderId;

        public String getExtOrderId() {
            return extOrderId;
        }

        public void setExtOrderId(String extOrderId) {
            this.extOrderId = extOrderId;
        }

        public String getIntegralStr() {
            return integralStr;
        }

        public void setIntegralStr(String integralStr) {
            this.integralStr = integralStr;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public int getIsExpired() {
            return isExpired;
        }

        public void setIsExpired(int isExpired) {
            this.isExpired = isExpired;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
