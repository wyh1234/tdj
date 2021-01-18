package cn.com.taodaji.model.entity;

import java.util.List;

public class PageByCSIdList {

    /**
     * err : 0
     * data : {"total":3,"items":[{"entity_id":23,"create_time":"2017-03-07 17:28","province":13,"city":193,"after_sales_no":"1488879029353","store_id":4,"supplier_name":"淘大集","supplier_tel":"18656004515","store_name":"张记冻货店","customer_id":42,"customer_name":"斩风3","hotel_name":"测试酒店3","customer_tel":"18727120758","customer_address":"襄阳盛大酒3店","order_id":545,"order_item_id":1913,"sku":"32434343223","unit":"斤","name":"菠菜","nick_name":"四季青","price":43,"total_price":430,"amount":10,"problem_description":"菠菜质量稀烂","certificate_photos":"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png","apply_type":1,"status":2,"out_trade_no":"f79e5520ed0f11e6807e4b081815219e","product_img":"http://llwer.pi.com","order_no":"32423434243","create_order_time":"2017-03-10 15:51","required_delivery_time":"2017-03-07 17:30","order_pay_time":"2017-03-07 17:30","customer_img":null,"store_img":null,"items":[{"entity_id":64,"create_time":"2017-03-10 15:51","after_sales_application_id":23,"handle_info":"售后同意退款，等待售后工作人员退款到采购商","handle_operator":"系统人员","remarks":null,"type":5}]},{"entity_id":30,"create_time":"2017-03-08 10:36","province":13,"city":193,"after_sales_no":"1488940665662","store_id":2,"supplier_name":"淘大集","supplier_tel":"18656004515","store_name":"张记冻货店","customer_id":42,"customer_name":"斩风3","hotel_name":"测试酒店3","customer_tel":"18727120758","customer_address":"襄阳盛大酒3店","order_id":545,"order_item_id":1913,"sku":"32434343223","unit":"斤","name":"菠菜","nick_name":"四季青","price":43,"total_price":430,"amount":10,"problem_description":"问题描述","certificate_photos":"http://www.sina.com.cn","apply_type":1,"status":6,"out_trade_no":"f79e5520ed0f11e6807e4b081815219e","product_img":"http://llwer.pi.com","order_no":"32423434243","create_order_time":"2017-03-10 15:53","required_delivery_time":"2017-03-08 10:36","order_pay_time":"2017-03-02 10:38","customer_img":null,"store_img":null,"items":[{"entity_id":62,"create_time":"2017-03-10 14:43","after_sales_application_id":30,"handle_info":"售后成功","handle_operator":"系统人员","remarks":"售后成功","type":6},{"entity_id":63,"create_time":"2017-03-10 14:43","after_sales_application_id":30,"handle_info":"售后成功","handle_operator":"系统人员","remarks":"售后成功","type":6}]},{"entity_id":31,"create_time":"2017-03-08 10:38","province":13,"city":193,"after_sales_no":"1488940719287","store_id":4,"supplier_name":"淘大集","supplier_tel":"18656004515","store_name":"张记冻货店","customer_id":42,"customer_name":"斩风3","hotel_name":"测试酒店3","customer_tel":"18727120758","customer_address":"襄阳盛大酒3店","order_id":545,"order_item_id":1913,"sku":"32434343223","unit":"斤","name":"菠菜","nick_name":"四季青","price":43,"total_price":430,"amount":10,"problem_description":"问题描述","certificate_photos":"http://www.sina.com.cn","apply_type":1,"status":6,"out_trade_no":"f79e5520ed0f11e6807e4b081815219e","product_img":"http://llwer.pi.com","order_no":"32423434243","create_order_time":"2017-03-10 14:27","required_delivery_time":"2017-03-08 10:38","order_pay_time":"2017-02-07 16:32","customer_img":null,"store_img":null,"items":[{"entity_id":61,"create_time":"2017-03-10 14:26","after_sales_application_id":31,"handle_info":"售后成功","handle_operator":"系统人员","remarks":"售后成功","type":6}]}],"pn":1,"ps":3}
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
         * total : 3
         * items : [{"entity_id":23,"create_time":"2017-03-07 17:28","province":13,"city":193,"after_sales_no":"1488879029353","store_id":4,"supplier_name":"淘大集","supplier_tel":"18656004515","store_name":"张记冻货店","customer_id":42,"customer_name":"斩风3","hotel_name":"测试酒店3","customer_tel":"18727120758","customer_address":"襄阳盛大酒3店","order_id":545,"order_item_id":1913,"sku":"32434343223","unit":"斤","name":"菠菜","nick_name":"四季青","price":43,"total_price":430,"amount":10,"problem_description":"菠菜质量稀烂","certificate_photos":"https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png","apply_type":1,"status":2,"out_trade_no":"f79e5520ed0f11e6807e4b081815219e","product_img":"http://llwer.pi.com","order_no":"32423434243","create_order_time":"2017-03-10 15:51","required_delivery_time":"2017-03-07 17:30","order_pay_time":"2017-03-07 17:30","customer_img":null,"store_img":null,"items":[{"entity_id":64,"create_time":"2017-03-10 15:51","after_sales_application_id":23,"handle_info":"售后同意退款，等待售后工作人员退款到采购商","handle_operator":"系统人员","remarks":null,"type":5}]},{"entity_id":30,"create_time":"2017-03-08 10:36","province":13,"city":193,"after_sales_no":"1488940665662","store_id":2,"supplier_name":"淘大集","supplier_tel":"18656004515","store_name":"张记冻货店","customer_id":42,"customer_name":"斩风3","hotel_name":"测试酒店3","customer_tel":"18727120758","customer_address":"襄阳盛大酒3店","order_id":545,"order_item_id":1913,"sku":"32434343223","unit":"斤","name":"菠菜","nick_name":"四季青","price":43,"total_price":430,"amount":10,"problem_description":"问题描述","certificate_photos":"http://www.sina.com.cn","apply_type":1,"status":6,"out_trade_no":"f79e5520ed0f11e6807e4b081815219e","product_img":"http://llwer.pi.com","order_no":"32423434243","create_order_time":"2017-03-10 15:53","required_delivery_time":"2017-03-08 10:36","order_pay_time":"2017-03-02 10:38","customer_img":null,"store_img":null,"items":[{"entity_id":62,"create_time":"2017-03-10 14:43","after_sales_application_id":30,"handle_info":"售后成功","handle_operator":"系统人员","remarks":"售后成功","type":6},{"entity_id":63,"create_time":"2017-03-10 14:43","after_sales_application_id":30,"handle_info":"售后成功","handle_operator":"系统人员","remarks":"售后成功","type":6}]},{"entity_id":31,"create_time":"2017-03-08 10:38","province":13,"city":193,"after_sales_no":"1488940719287","store_id":4,"supplier_name":"淘大集","supplier_tel":"18656004515","store_name":"张记冻货店","customer_id":42,"customer_name":"斩风3","hotel_name":"测试酒店3","customer_tel":"18727120758","customer_address":"襄阳盛大酒3店","order_id":545,"order_item_id":1913,"sku":"32434343223","unit":"斤","name":"菠菜","nick_name":"四季青","price":43,"total_price":430,"amount":10,"problem_description":"问题描述","certificate_photos":"http://www.sina.com.cn","apply_type":1,"status":6,"out_trade_no":"f79e5520ed0f11e6807e4b081815219e","product_img":"http://llwer.pi.com","order_no":"32423434243","create_order_time":"2017-03-10 14:27","required_delivery_time":"2017-03-08 10:38","order_pay_time":"2017-02-07 16:32","customer_img":null,"store_img":null,"items":[{"entity_id":61,"create_time":"2017-03-10 14:26","after_sales_application_id":31,"handle_info":"售后成功","handle_operator":"系统人员","remarks":"售后成功","type":6}]}]
         * pn : 1
         * ps : 3
         */

        private int total;
        private int pn;
        private int ps;
        private List<RefundDetail> items;

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

        public List<RefundDetail> getItems() {
            return items;
        }

        public void setItems(List<RefundDetail> items) {
            this.items = items;
        }


    }
}
