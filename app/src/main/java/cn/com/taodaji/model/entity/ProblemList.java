package cn.com.taodaji.model.entity;

import java.util.List;

public class ProblemList {

    /**
     * err : 0
     * data : {"list":[{"entityId":1,"problem":"未收到货显示已送达"},{"entityId":2,"problem":"配送超时"},{"entityId":3,"problem":"司机要额外收取配送费"},{"entityId":4,"problem":"不送到店门口"},{"entityId":5,"problem":"其他"},{"entityId":24,"problem":"商品积压"}],"evaName":"宁","evaTel":"15071519512"}
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
         * list : [{"entityId":1,"problem":"未收到货显示已送达"},{"entityId":2,"problem":"配送超时"},{"entityId":3,"problem":"司机要额外收取配送费"},{"entityId":4,"problem":"不送到店门口"},{"entityId":5,"problem":"其他"},{"entityId":24,"problem":"商品积压"}]
         * evaName : 宁
         * evaTel : 15071519512
         */

        private String evaName;
        private String evaTel;
        private List<ListBean> list;

        public String getEvaName() {
            return evaName;
        }

        public void setEvaName(String evaName) {
            this.evaName = evaName;
        }

        public String getEvaTel() {
            return evaTel;
        }

        public void setEvaTel(String evaTel) {
            this.evaTel = evaTel;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * entityId : 1
             * problem : 未收到货显示已送达
             */

            private int entityId;
            private String problem;

            public int getEntityId() {
                return entityId;
            }

            public void setEntityId(int entityId) {
                this.entityId = entityId;
            }

            public String getProblem() {
                return problem;
            }

            public void setProblem(String problem) {
                this.problem = problem;
            }
        }
    }
}
