package cn.com.taodaji.model.entity;

import java.util.List;

/**
 * Created by yangkuo on 2018/11/23.
 */
public class StoreLimit {

    /**
     * err : 0
     * data : {"currentList":{"sales_number":10,"created_at":1542426165000,"total_number":10,"store_id":1457,"type":1,"entity_id":2,"website_id":2},"historyList":[{"sales_number":10,"created_at":"2018-11-17 11:42:45","total_number":10,"store_id":1457,"type":1,"entity_id":2,"website_id":2}],"list":[{"sales_number":3,"category_name":"新鲜蔬菜","is_active":1,"check_status":1,"total_number":5,"category_id":10,"entity_id":6,"website_id":2},{"category_name":"水果","is_active":1,"check_status":1,"category_id":14,"entity_id":7,"website_id":2}],"total":2}
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
         * currentList : {"sales_number":10,"created_at":1542426165000,"total_number":10,"store_id":1457,"type":1,"entity_id":2,"website_id":2}
         * historyList : [{"sales_number":10,"created_at":"2018-11-17 11:42:45","total_number":10,"store_id":1457,"type":1,"entity_id":2,"website_id":2}]
         * list : [{"sales_number":3,"category_name":"新鲜蔬菜","is_active":1,"check_status":1,"total_number":5,"category_id":10,"entity_id":6,"website_id":2},{"category_name":"水果","is_active":1,"check_status":1,"category_id":14,"entity_id":7,"website_id":2}]
         * total : 2
         */

        private CurrentListBean currentList;
        private int total;
        private List<HistoryListBean> historyList;
        private List<ListBean> list;

        public CurrentListBean getCurrentList() {
            return currentList;
        }

        public void setCurrentList(CurrentListBean currentList) {
            this.currentList = currentList;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<HistoryListBean> getHistoryList() {
            return historyList;
        }

        public void setHistoryList(List<HistoryListBean> historyList) {
            this.historyList = historyList;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class CurrentListBean {
            /**
             * sales_number : 10
             * created_at : 1542426165000
             * total_number : 10
             * store_id : 1457
             * type : 1
             * entity_id : 2
             * website_id : 2
             */

            private int sales_number;
            private long created_at;
            private int total_number;
            private int store_id;
            private int type;
            private int entity_id;
            private int website_id;

            public int getSales_number() {
                return sales_number;
            }

            public void setSales_number(int sales_number) {
                this.sales_number = sales_number;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }

            public int getTotal_number() {
                return total_number;
            }

            public void setTotal_number(int total_number) {
                this.total_number = total_number;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(int entity_id) {
                this.entity_id = entity_id;
            }

            public int getWebsite_id() {
                return website_id;
            }

            public void setWebsite_id(int website_id) {
                this.website_id = website_id;
            }
        }

        public static class HistoryListBean {
            /**
             * sales_number : 10
             * created_at : 2018-11-17 11:42:45
             * total_number : 10
             * store_id : 1457
             * type : 1
             * entity_id : 2
             * website_id : 2
             */

            private int sales_number;
            private String created_at;
            private int total_number;
            private int store_id;
            private int type;
            private int entity_id;
            private int website_id;

            public int getSales_number() {
                return sales_number;
            }

            public void setSales_number(int sales_number) {
                this.sales_number = sales_number;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public int getTotal_number() {
                return total_number;
            }

            public void setTotal_number(int total_number) {
                this.total_number = total_number;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(int entity_id) {
                this.entity_id = entity_id;
            }

            public int getWebsite_id() {
                return website_id;
            }

            public void setWebsite_id(int website_id) {
                this.website_id = website_id;
            }
        }

        public static class ListBean {
            /**
             * sales_number : 3
             * category_name : 新鲜蔬菜
             * is_active : 1
             * check_status : 1
             * total_number : 5
             * category_id : 10
             * entity_id : 6
             * website_id : 2
             */

            private int sales_number;
            private String category_name;
            private int is_active;
            private int check_status;
            private int total_number;
            private int category_id;
            private int entity_id;
            private int website_id;

            public int getSales_number() {
                return sales_number;
            }

            public void setSales_number(int sales_number) {
                this.sales_number = sales_number;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public int getIs_active() {
                return is_active;
            }

            public void setIs_active(int is_active) {
                this.is_active = is_active;
            }

            public int getCheck_status() {
                return check_status;
            }

            public void setCheck_status(int check_status) {
                this.check_status = check_status;
            }

            public int getTotal_number() {
                return total_number;
            }

            public void setTotal_number(int total_number) {
                this.total_number = total_number;
            }

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public int getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(int entity_id) {
                this.entity_id = entity_id;
            }

            public int getWebsite_id() {
                return website_id;
            }

            public void setWebsite_id(int website_id) {
                this.website_id = website_id;
            }
        }
    }
}
