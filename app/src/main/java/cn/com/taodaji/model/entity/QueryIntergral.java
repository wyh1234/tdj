package cn.com.taodaji.model.entity;

import java.util.List;

public class QueryIntergral {
    private int err;
    private String msg;
    private List<QueryIntergralData> data;

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

    public List<QueryIntergralData> getData() {
        return data;
    }

    public void setData(List<QueryIntergralData> data) {
        this.data = data;
    }

    public static class QueryIntergralData{
        private String name;
        private String creatorId;
        private String price;
        private String gotAmount;
        private String guid;
        private boolean b;

        public boolean isB() {
            return b;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGotAmount() {
            return gotAmount;
        }

        public void setGotAmount(String gotAmount) {
            this.gotAmount = gotAmount;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }
    }
}
