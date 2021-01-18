package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.util.List;

public class PunishmentMessage {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"total":1,"items":[{"entityId":12,"createTime":"2019-07-25 11:05:57","showTime":"2019-07-25 00:00:00","content":"黄牌警告","receiverTel":"17771003521","receiverName":"测试司机","receiverType":1,"all":0,"status":1,"punishType":1,"noticeType":1,"readed":0,"deleted":0,"remark":null}]}
     */

    private int err;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total : 1
         * items : [{"entityId":12,"createTime":"2019-07-25 11:05:57","showTime":"2019-07-25 00:00:00","content":"黄牌警告","receiverTel":"17771003521","receiverName":"测试司机","receiverType":1,"all":0,"status":1,"punishType":1,"noticeType":1,"readed":0,"deleted":0,"remark":null}]
         */

        private int total;
        private List<ItemsBean> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable {
            /**
             * entityId : 12
             * createTime : 2019-07-25 11:05:57
             * showTime : 2019-07-25 00:00:00
             * content : 黄牌警告
             * receiverTel : 17771003521
             * receiverName : 测试司机
             * receiverType : 1
             * all : 0
             * status : 1
             * punishType : 1
             * noticeType : 1
             * readed : 0
             * deleted : 0
             * remark : null
             */

            private int entityId;
            private String createTime;
            private String showTime;
            private String content;
            private String receiverTel;
            private String receiverName;
            private int receiverType;
            private int all;
            private int status;
            private int punishType;
            private int noticeType;
            private int readed;
            private int deleted;
            private Object remark;

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

            public String getShowTime() {
                return showTime;
            }

            public void setShowTime(String showTime) {
                this.showTime = showTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getReceiverTel() {
                return receiverTel;
            }

            public void setReceiverTel(String receiverTel) {
                this.receiverTel = receiverTel;
            }

            public String getReceiverName() {
                return receiverName;
            }

            public void setReceiverName(String receiverName) {
                this.receiverName = receiverName;
            }

            public int getReceiverType() {
                return receiverType;
            }

            public void setReceiverType(int receiverType) {
                this.receiverType = receiverType;
            }

            public int getAll() {
                return all;
            }

            public void setAll(int all) {
                this.all = all;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getPunishType() {
                return punishType;
            }

            public void setPunishType(int punishType) {
                this.punishType = punishType;
            }

            public int getNoticeType() {
                return noticeType;
            }

            public void setNoticeType(int noticeType) {
                this.noticeType = noticeType;
            }

            public int getReaded() {
                return readed;
            }

            public void setReaded(int readed) {
                this.readed = readed;
            }

            public int getDeleted() {
                return deleted;
            }

            public void setDeleted(int deleted) {
                this.deleted = deleted;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }
        }
    }
}
