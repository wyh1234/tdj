package cn.com.taodaji.model.entity;

import java.util.List;

public class EvaluationDetail {

    /**
     * err : 0
     * msg : 处理成功
     * data : {"items":{"tagList":[{"entityId":1,"type":1,"problem":"未收到货显示已送达","createdAt":"2019-08-09 13:37:29","updatedAt":"2019-08-09 14:53:09"},{"entityId":2,"type":1,"problem":"配送超时","createdAt":"2019-08-09 14:51:40","updatedAt":"2019-08-09 14:53:19"}],"evaName":"张平","customerId":9,"anonymous":1,"entityId":1,"evaType":1,"hotelName":"金佳禾","evaTel":"\u202d15587727799\u202c","evaContent":"hhhhhh"}}
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
         * items : {"tagList":[{"entityId":1,"type":1,"problem":"未收到货显示已送达","createdAt":"2019-08-09 13:37:29","updatedAt":"2019-08-09 14:53:09"},{"entityId":2,"type":1,"problem":"配送超时","createdAt":"2019-08-09 14:51:40","updatedAt":"2019-08-09 14:53:19"}],"evaName":"张平","customerId":9,"anonymous":1,"entityId":1,"evaType":1,"hotelName":"金佳禾","evaTel":"\u202d15587727799\u202c","evaContent":"hhhhhh"}
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
             * tagList : [{"entityId":1,"type":1,"problem":"未收到货显示已送达","createdAt":"2019-08-09 13:37:29","updatedAt":"2019-08-09 14:53:09"},{"entityId":2,"type":1,"problem":"配送超时","createdAt":"2019-08-09 14:51:40","updatedAt":"2019-08-09 14:53:19"}]
             * evaName : 张平
             * customerId : 9
             * anonymous : 1
             * entityId : 1
             * evaType : 1
             * hotelName : 金佳禾
             * evaTel : ‭15587727799‬
             * evaContent : hhhhhh
             */

            private String evaName;
            private int customerId;
            private int anonymous;
            private int entityId;
            private int evaType;
            private String hotelName;
            private String evaTel;
            private String evaContent;
            private List<TagListBean> tagList;

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

            public int getAnonymous() {
                return anonymous;
            }

            public void setAnonymous(int anonymous) {
                this.anonymous = anonymous;
            }

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public int getEvaType() {
                return evaType;
            }

            public void setEvaType(int evaType) {
                this.evaType = evaType;
            }

            public String getHotelName() {
                return hotelName;
            }

            public void setHotelName(String hotelName) {
                this.hotelName = hotelName;
            }

            public String getEvaTel() {
                return evaTel;
            }

            public void setEvaTel(String evaTel) {
                this.evaTel = evaTel;
            }

            public String getEvaContent() {
                return evaContent;
            }

            public void setEvaContent(String evaContent) {
                this.evaContent = evaContent;
            }

            public List<TagListBean> getTagList() {
                return tagList;
            }

            public void setTagList(List<TagListBean> tagList) {
                this.tagList = tagList;
            }

            public static class TagListBean {
                /**
                 * entityId : 1
                 * type : 1
                 * problem : 未收到货显示已送达
                 * createdAt : 2019-08-09 13:37:29
                 * updatedAt : 2019-08-09 14:53:09
                 */

                private int entityId;
                private int type;
                private String problem;
                private String createdAt;
                private String updatedAt;

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

                public String getProblem() {
                    return problem;
                }

                public void setProblem(String problem) {
                    this.problem = problem;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getUpdatedAt() {
                    return updatedAt;
                }

                public void setUpdatedAt(String updatedAt) {
                    this.updatedAt = updatedAt;
                }
            }
        }
    }
}
