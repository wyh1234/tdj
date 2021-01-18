package cn.com.taodaji.model.entity;

import java.util.List;

public class PackingCashProgressBean {

    /**
     * err : 0
     * data : {"list":[{"handle_info":"退押金成功。 申请1个, 实退1个。 (备注：可以退钱了)","created_at":"2018-12-26 17:16:52","after_sale_id":18,"type":4,"entity_id":62,"handle_user":"超级管理员","website_id":2},{"handle_info":"汉江路站点， 配送中心收到退回包装物1个, 等到供应商认领。 (备注：很好啊)","created_at":"2018-12-26 17:16:04","after_sale_id":18,"type":3,"entity_id":61,"handle_user":"超级管理员","website_id":2},{"handle_info":"司机已在取货路上(毕文虎  15570420488)，48小时内未收到包装，默认为退押金订单取消，需重新申请。","created_at":"2018-12-26 17:15:50","after_sale_id":18,"type":2,"entity_id":60,"handle_user":"超级管理员","website_id":2},{"handle_info":"提出申请成功，准备好包装物，司机马上到。","created_at":"2018-12-26 17:05:02","after_sale_id":18,"type":1,"entity_id":59,"handle_user":"超级管理员","website_id":2}],"total":4}
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
         * list : [{"handle_info":"退押金成功。 申请1个, 实退1个。 (备注：可以退钱了)","created_at":"2018-12-26 17:16:52","after_sale_id":18,"type":4,"entity_id":62,"handle_user":"超级管理员","website_id":2},{"handle_info":"汉江路站点， 配送中心收到退回包装物1个, 等到供应商认领。 (备注：很好啊)","created_at":"2018-12-26 17:16:04","after_sale_id":18,"type":3,"entity_id":61,"handle_user":"超级管理员","website_id":2},{"handle_info":"司机已在取货路上(毕文虎  15570420488)，48小时内未收到包装，默认为退押金订单取消，需重新申请。","created_at":"2018-12-26 17:15:50","after_sale_id":18,"type":2,"entity_id":60,"handle_user":"超级管理员","website_id":2},{"handle_info":"提出申请成功，准备好包装物，司机马上到。","created_at":"2018-12-26 17:05:02","after_sale_id":18,"type":1,"entity_id":59,"handle_user":"超级管理员","website_id":2}]
         * total : 4
         */
        //退押状态  为1时可撤销
        private int status;
        private int total;
        private List<ListBean> list;

       /**  退押状态  为1时可撤销*/
        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * handle_info : 退押金成功。 申请1个, 实退1个。 (备注：可以退钱了)
             * created_at : 2018-12-26 17:16:52
             * after_sale_id : 18
             * type : 4
             * entity_id : 62
             * handle_user : 超级管理员
             * website_id : 2
             */

            private String handle_info;
            private String created_at;
            private long after_sale_id;
            private int type;
            private int entity_id;
            private String handle_user;
            private int website_id;
            private boolean check;

            public boolean isCheck() {
                return check;
            }

            public void setCheck(boolean check) {
                this.check = check;
            }

            public String getHandle_info() {
                return handle_info;
            }

            public void setHandle_info(String handle_info) {
                this.handle_info = handle_info;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public long getAfter_sale_id() {
                return after_sale_id;
            }

            public void setAfter_sale_id(long after_sale_id) {
                this.after_sale_id = after_sale_id;
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

            public String getHandle_user() {
                return handle_user;
            }

            public void setHandle_user(String handle_user) {
                this.handle_user = handle_user;
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
