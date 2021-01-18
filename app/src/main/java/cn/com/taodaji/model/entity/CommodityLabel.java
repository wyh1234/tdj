package cn.com.taodaji.model.entity;

import java.util.List;

public class CommodityLabel {

    /**
     * err : 0
     * data : {"list":[{"datas":[],"propertyId":163,"propertyZhName":"产地","required":1},{"datas":[{"dataId":165,"dataValue":"1CM以下"},{"dataId":166,"dataValue":"2-3CM"}],"propertyId":164,"propertyZhName":"长度","required":1},{"datas":[{"dataId":284,"dataValue":"红色"},{"dataId":285,"dataValue":"绿色"}],"propertyId":165,"propertyZhName":"颜色","required":0},{"datas":[{"dataId":167,"dataValue":"1-2CM以上"},{"dataId":168,"dataValue":"3-4CM以下"},{"dataId":169,"dataValue":"5-6CM以下"}],"propertyId":166,"propertyZhName":"重量","required":0},{"datas":[{"dataId":170,"dataValue":"1-2CM以上"},{"dataId":171,"dataValue":"3-4CM以下"}],"propertyId":167,"propertyZhName":"形状","required":0},{"datas":[],"propertyId":168,"propertyZhName":"包装","required":0},{"datas":[{"dataId":172,"dataValue":"粗"},{"dataId":173,"dataValue":"细"},{"dataId":174,"dataValue":"无"}],"propertyId":169,"propertyZhName":"根蒂","required":1}]}
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
             * datas : []
             * propertyId : 163
             * propertyZhName : 产地
             * required : 1
             */

            private int propertyId;
            private String propertyZhName;
            private int required;
            private String content;
            private List<Property> datas;
            private boolean f;

            public boolean isF() {
                return f;
            }

            public void setF(boolean f) {
                this.f = f;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getPropertyId() {
                return propertyId;
            }

            public void setPropertyId(int propertyId) {
                this.propertyId = propertyId;
            }

            public String getPropertyZhName() {
                return propertyZhName;
            }

            public void setPropertyZhName(String propertyZhName) {
                this.propertyZhName = propertyZhName;
            }

            public int getRequired() {
                return required;
            }

            public void setRequired(int required) {
                this.required = required;
            }

            public List<Property> getDatas() {
                return datas;
            }

            public void setDatas(List<Property> datas) {
                this.datas = datas;
            }
        }
    }
}
