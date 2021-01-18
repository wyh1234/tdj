package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017-07-24.
 */

public class PunishScoreRecord {

    /**
     * err : 0
     * data : {"list":[{"punish_type":"关闭店铺","id":23,"remark":"","store_id":11,"create_datetime":"2017-06-08 09:27","type":4,"modify_datetime":1498870766000,"resume_datetime":1498870766000,"punishScore":0.3},{"punish_type":"歇业一周","id":15,"remark":"","store_id":11,"create_datetime":"2017-04-24 10:08","type":2,"modify_datetime":1496730711000,"resume_datetime":1496730711000,"punishScore":0.1}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * punish_type : 关闭店铺
             * id : 23
             * remark :
             * store_id : 11
             * create_datetime : 2017-06-08 09:27
             * type : 4
             * modify_datetime : 1498870766000
             * resume_datetime : 1498870766000
             * punishScore : 0.3
             */

            private String punish_type;
            private int id;
            private String remark;
            private int store_id;
            private String create_datetime;
            private int type;
            private long modify_datetime;
            private long resume_datetime;
            private BigDecimal punishScore;

            public String getPunish_type() {
                return punish_type;
            }

            public void setPunish_type(String punish_type) {
                this.punish_type = punish_type;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getCreate_datetime() {
                return create_datetime;
            }

            public void setCreate_datetime(String create_datetime) {
                this.create_datetime = create_datetime;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public long getModify_datetime() {
                return modify_datetime;
            }

            public void setModify_datetime(long modify_datetime) {
                this.modify_datetime = modify_datetime;
            }

            public long getResume_datetime() {
                return resume_datetime;
            }

            public void setResume_datetime(long resume_datetime) {
                this.resume_datetime = resume_datetime;
            }

            public BigDecimal getPunishScore() {
                return punishScore;
            }

            public void setPunishScore(BigDecimal punishScore) {
                this.punishScore = punishScore;
            }
        }
    }
}
