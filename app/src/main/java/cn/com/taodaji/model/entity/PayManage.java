package cn.com.taodaji.model.entity;

import java.util.List;

public class PayManage {


    /**
     * err : 0
     * data : [{"entity_id":1,"type":1,"type_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/191129095221e0d9e50f.png","item_type":"ANNAL","item_name":"平台年费","item_content":"查看特权详细","item_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/19112909533905c94af8.png","status":"1","item_sort":1,"link_url":null,"link_method":null},{"entity_id":2,"type":1,"type_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/191129095221e0d9e50f.png","item_type":"ADVERTISEMENT","item_name":"广告推广管理","item_content":"增加商品曝光率","item_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/191129095631c3bd764c.png","status":"1","item_sort":5,"link_url":null,"link_method":null},{"entity_id":3,"type":1,"type_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/191129095221e0d9e50f.png","item_type":"SWEEPCODE","item_name":"扫码费","item_content":"提高效率","item_url":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/191129095815892c45b4.png","status":"1","item_sort":10,"link_url":null,"link_method":null}]
     * msg : success
     */

    private int err;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * entity_id : 1
         * type : 1
         * type_url : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/191129095221e0d9e50f.png
         * item_type : ANNAL
         * item_name : 平台年费
         * item_content : 查看特权详细
         * item_url : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/19112909533905c94af8.png
         * status : 1
         * item_sort : 1
         * link_url : null
         * link_method : null
         */
        private List<DataBeanList> list;
        private int type;
        private String type_url;
        private String item_name;
        private String item_content;
        private String item_url;
        private String item_type;

        public String getItem_type() {
            return item_type;
        }

        public void setItem_type(String item_type) {
            this.item_type = item_type;
        }

        public List<DataBeanList> getList() {
            return list;
        }

        public void setList(List<DataBeanList> list) {
            this.list = list;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_content() {
            return item_content;
        }

        public void setItem_content(String item_content) {
            this.item_content = item_content;
        }

        public String getItem_url() {
            return item_url;
        }

        public void setItem_url(String item_url) {
            this.item_url = item_url;
        }


        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getType_url() {
            return type_url;
        }

        public void setType_url(String type_url) {
            this.type_url = type_url;
        }

       public static class  DataBeanList{
            private int entity_id;
            private int type;
            private String type_url;
            private String item_type;
            private String item_name;
            private String item_content;
            private String item_url;
            private String status;
            private int item_sort;
            private Object link_url;
            private Object link_method;


            public int getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(int entity_id) {
                this.entity_id = entity_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getType_url() {
                return type_url;
            }

            public void setType_url(String type_url) {
                this.type_url = type_url;
            }

            public String getItem_type() {
                return item_type;
            }

            public void setItem_type(String item_type) {
                this.item_type = item_type;
            }

            public String getItem_name() {
                return item_name;
            }

            public void setItem_name(String item_name) {
                this.item_name = item_name;
            }

            public String getItem_content() {
                return item_content;
            }

            public void setItem_content(String item_content) {
                this.item_content = item_content;
            }

            public String getItem_url() {
                return item_url;
            }

            public void setItem_url(String item_url) {
                this.item_url = item_url;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getItem_sort() {
                return item_sort;
            }

            public void setItem_sort(int item_sort) {
                this.item_sort = item_sort;
            }

            public Object getLink_url() {
                return link_url;
            }

            public void setLink_url(Object link_url) {
                this.link_url = link_url;
            }

            public Object getLink_method() {
                return link_method;
            }

            public void setLink_method(Object link_method) {
                this.link_method = link_method;
            }
        }
    }
}
