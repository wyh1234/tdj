package cn.com.taodaji.model.entity;

public class ComplaintDetail {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"items":{"img":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17032711474623ede678.jpg,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17032711474623ede678.jpg","extOrderId":"6386026172672647168","problem":"未收到货显示已送达","evaName":"宁","customerId":3787,"entityId":1,"type":1,"hotelName":"淘大集 宁159071519512","content":"迟到","evaTel":"15071519512"}}
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
         * items : {"img":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17032711474623ede678.jpg,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17032711474623ede678.jpg","extOrderId":"6386026172672647168","problem":"未收到货显示已送达","evaName":"宁","customerId":3787,"entityId":1,"type":1,"hotelName":"淘大集 宁159071519512","content":"迟到","evaTel":"15071519512"}
         */

        private ItemsBean items;

        public ItemsBean getItems() {
            return items;
        }

        public void setItems(ItemsBean items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * img : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17032711474623ede678.jpg,http://tsp-img.oss-cn-hangzhou.aliyuncs.com/17032711474623ede678.jpg
             * extOrderId : 6386026172672647168
             * problem : 未收到货显示已送达
             * evaName : 宁
             * customerId : 3787
             * entityId : 1
             * type : 1
             * hotelName : 淘大集 宁159071519512
             * content : 迟到
             * evaTel : 15071519512
             */

            private String img;
            private String extOrderId;
            private String problem;
            private String evaName;
            private int customerId;
            private int entityId;
            private int type;
            private String hotelName;
            private String content;
            private String evaTel;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getExtOrderId() {
                return extOrderId;
            }

            public void setExtOrderId(String extOrderId) {
                this.extOrderId = extOrderId;
            }

            public String getProblem() {
                return problem;
            }

            public void setProblem(String problem) {
                this.problem = problem;
            }

            public String getEvaName() {
                return evaName;
            }

            public void setEvaName(String evaName) {
                this.evaName = evaName;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getHotelName() {
                return hotelName;
            }

            public void setHotelName(String hotelName) {
                this.hotelName = hotelName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getEvaTel() {
                return evaTel;
            }

            public void setEvaTel(String evaTel) {
                this.evaTel = evaTel;
            }
        }
    }
}
