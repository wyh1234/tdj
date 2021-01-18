package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/20.
 */

public class GetNews {


        /**
         * total : 3
         * items : [{"entityId":2,"createTime":"2018-01-20 11:35","updateTime":"2018-01-20 11:35","status":1,"type":0,"userId":1,"title":"提现成功，请注意查收","content":"你于2018-01-20 11:34申请的提现100元，已于2018-01-20 11:35处理完成，请在您提供的提现账户中注意查收。","isRead":0},{"entityId":3,"createTime":"2018-01-20 11:35","updateTime":"2018-01-20 11:35","status":1,"type":0,"userId":1,"title":"提现成功，请注意查收","content":"你于2018-01-20 11:35申请的提现100元，已于2018-01-20 11:35处理完成，请在您提供的提现账户中注意查收。","isRead":0},{"entityId":4,"createTime":"2018-01-20 11:37","updateTime":"2018-01-20 11:37","status":1,"type":0,"userId":1,"title":"提现成功，请注意查收","content":"你于2018-01-20 11:37申请的提现50元，已于2018-01-20 11:37处理完成，请在您提供的提现账户中注意查收。","isRead":0}]
         * pn : 1
         * ps : 10
         */

        private int total;
        private int pn;
        private int ps;
        private List<ItemsBean> items;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getPs() {
            return ps;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable {
            /**
             * entityId : 2
             * createTime : 2018-01-20 11:35
             * updateTime : 2018-01-20 11:35
             * status : 1
             * type : 0
             * userId : 1
             * title : 提现成功，请注意查收
             * content : 你于2018-01-20 11:34申请的提现100元，已于2018-01-20 11:35处理完成，请在您提供的提现账户中注意查收。
             * isRead : 0
             */

            private int entityId;
            private String createTime;
            private String updateTime;
            private int status;
            private int type;
            private int userId;
            private String title;
            private String content;
            private int isRead;

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

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }
        }
    }

