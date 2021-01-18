package cn.com.taodaji.model.entity;

import com.base.annotation.OnlyField;

import java.util.List;

/**
 * Created by Administrator on 2017-10-14.
 */

public class AfterDetails {

    /**
     * err : 0
     * data : {"entity_id":1,"create_time":"2017-04-01 11:08","province":13,"city":193,"after_sales_no":"1491016090971","store_id":31,"supplier_name":"刘珊","supplier_tel":"13635809635","store_name":"刘记店铺","customer_id":10,"customer_name":"李俊","hotel_name":"襄阳市襄城区政府","customer_tel":"18727120758","customer_address":"章程规定","order_id":20,"order_item_id":20,"sku":"xtb_1491015526728","unit":"斤","name":"菠菜","nick_name":"","price":0.01,"discount_price":0.01,"total_price":0.01,"amount":1,"problem_description":"名字咯lol米诺","certificate_photos":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1704011108088e49fadf.jpg   ","apply_type":2,"status":6,"out_trade_no":"a4d359c0168711e793014d4245d08505","product_img":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170401105814b7616faf.jpg   ","order_no":"1000000003","create_order_time":"2017-09-13 17:45","required_delivery_time":"2017-04-01 11:08","order_pay_time":"2017-04-01 11:02","customer_img":"","store_img":"","original_price":0.01,"original_amount":1,"original_total_price":0.01,"discount_total_price":0.01,"qr_code_id":"1491016090971","problem_type":0,"isUseCoupon":0,"items":[{"entity_id":3,"create_time":"2017-04-01 11:11","after_sales_application_id":1,"handle_info":"售后成功","handle_operator":"系统人员","remarks":"售后成功","type":6,"customerName":"李俊","customerTel":"18727120758","supplierName":"刘珊","supplierTel":"13635809635"},{"entity_id":2,"create_time":"2017-04-01 11:09","after_sales_application_id":1,"handle_info":"售后同意换货，卖家请带新鲜的商品到配送中心","handle_operator":"系统人员","remarks":"售后同意换货，卖家请带新鲜的商品到配送中心","type":2,"customerName":"李俊","customerTel":"18727120758","supplierName":"刘珊","supplierTel":"13635809635"},{"entity_id":1,"create_time":"2017-04-01 11:08","after_sales_application_id":1,"handle_info":"提出申请成功,等待平台售后处理","handle_operator":"系统人员","remarks":"采购商提出售后申请，等待客服处理","type":1,"customerName":"李俊","customerTel":"18727120758","supplierName":"刘珊","supplierTel":"13635809635"}]}
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
         * entity_id : 1
         * create_time : 2017-04-01 11:08
         * province : 13
         * city : 193
         * after_sales_no : 1491016090971
         * store_id : 31
         * supplier_name : 刘珊
         * supplier_tel : 13635809635
         * store_name : 刘记店铺
         * customer_id : 10
         * customer_name : 李俊
         * hotel_name : 襄阳市襄城区政府
         * customer_tel : 18727120758
         * customer_address : 章程规定
         * order_id : 20
         * order_item_id : 20
         * sku : xtb_1491015526728
         * unit : 斤
         * name : 菠菜
         * nick_name :
         * price : 0.01
         * discount_price : 0.01
         * total_price : 0.01
         * amount : 1
         * problem_description : 名字咯lol米诺
         * certificate_photos : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1704011108088e49fadf.jpg
         * apply_type : 2
         * status : 6
         * out_trade_no : a4d359c0168711e793014d4245d08505
         * product_img : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/170401105814b7616faf.jpg
         * order_no : 1000000003
         * create_order_time : 2017-09-13 17:45
         * required_delivery_time : 2017-04-01 11:08
         * order_pay_time : 2017-04-01 11:02
         * customer_img :
         * store_img :
         * original_price : 0.01
         * original_amount : 1
         * original_total_price : 0.01
         * discount_total_price : 0.01
         * qr_code_id : 1491016090971
         * problem_type : 0
         * isUseCoupon : 0
         * items : [{"entity_id":3,"create_time":"2017-04-01 11:11","after_sales_application_id":1,"handle_info":"售后成功","handle_operator":"系统人员","remarks":"售后成功","type":6,"customerName":"李俊","customerTel":"18727120758","supplierName":"刘珊","supplierTel":"13635809635"},{"entity_id":2,"create_time":"2017-04-01 11:09","after_sales_application_id":1,"handle_info":"售后同意换货，卖家请带新鲜的商品到配送中心","handle_operator":"系统人员","remarks":"售后同意换货，卖家请带新鲜的商品到配送中心","type":2,"customerName":"李俊","customerTel":"18727120758","supplierName":"刘珊","supplierTel":"13635809635"},{"entity_id":1,"create_time":"2017-04-01 11:08","after_sales_application_id":1,"handle_info":"提出申请成功,等待平台售后处理","handle_operator":"系统人员","remarks":"采购商提出售后申请，等待客服处理","type":1,"customerName":"李俊","customerTel":"18727120758","supplierName":"刘珊","supplierTel":"13635809635"}]
         */

        private int entity_id;
        private String create_time;
        private int province;
        private int city;
        private String after_sales_no;
        private int store_id;
        private String supplier_name;
        private String supplier_tel;
        private String store_name;
        private int customer_id;
        private String customer_name;
        private String hotel_name;
        private String customer_tel;
        private String customer_address;
        private int order_id;
        private int order_item_id;
        private String sku;
        private String unit;
        private String name;
        private String nick_name;
        private double price;
        private double discount_price;
        private double total_price;
        private String amount;

        private String problem_description;
        private String certificate_photos;
        private int apply_type;
        private int status;
        private String out_trade_no;
        private String product_img;
        private String order_no;
        private String create_order_time;
        private String required_delivery_time;
        private String order_pay_time;
        private String customer_img;
        private String store_img;

        private double original_price;

        private int original_amount;
        private double original_total_price;
        private int isUseCoupon;
        private List<ItemsBean> items;



        private int problem_type;
        private String qr_code_id;
        private double discount_total_price;


        public int getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public String getAfter_sales_no() {
            return after_sales_no;
        }

        public void setAfter_sales_no(String after_sales_no) {
            this.after_sales_no = after_sales_no;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getSupplier_name() {
            return supplier_name;
        }

        public void setSupplier_name(String supplier_name) {
            this.supplier_name = supplier_name;
        }

        public String getSupplier_tel() {
            return supplier_tel;
        }

        public void setSupplier_tel(String supplier_tel) {
            this.supplier_tel = supplier_tel;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getHotel_name() {
            return hotel_name;
        }

        public void setHotel_name(String hotel_name) {
            this.hotel_name = hotel_name;
        }

        public String getCustomer_tel() {
            return customer_tel;
        }

        public void setCustomer_tel(String customer_tel) {
            this.customer_tel = customer_tel;
        }

        public String getCustomer_address() {
            return customer_address;
        }

        public void setCustomer_address(String customer_address) {
            this.customer_address = customer_address;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getOrder_item_id() {
            return order_item_id;
        }

        public void setOrder_item_id(int order_item_id) {
            this.order_item_id = order_item_id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getDiscount_price() {
            return discount_price;
        }

        public void setDiscount_price(double discount_price) {
            this.discount_price = discount_price;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getProblem_description() {
            return problem_description;
        }

        public void setProblem_description(String problem_description) {
            this.problem_description = problem_description;
        }

        public String getCertificate_photos() {
            return certificate_photos;
        }

        public void setCertificate_photos(String certificate_photos) {
            this.certificate_photos = certificate_photos;
        }

        public int getApply_type() {
            return apply_type;
        }

        public void setApply_type(int apply_type) {
            this.apply_type = apply_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getProduct_img() {
            return product_img;
        }

        public void setProduct_img(String product_img) {
            this.product_img = product_img;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getCreate_order_time() {
            return create_order_time;
        }

        public void setCreate_order_time(String create_order_time) {
            this.create_order_time = create_order_time;
        }

        public String getRequired_delivery_time() {
            return required_delivery_time;
        }

        public void setRequired_delivery_time(String required_delivery_time) {
            this.required_delivery_time = required_delivery_time;
        }

        public String getOrder_pay_time() {
            return order_pay_time;
        }

        public void setOrder_pay_time(String order_pay_time) {
            this.order_pay_time = order_pay_time;
        }

        public String getCustomer_img() {
            return customer_img;
        }

        public void setCustomer_img(String customer_img) {
            this.customer_img = customer_img;
        }

        public String getStore_img() {
            return store_img;
        }

        public void setStore_img(String store_img) {
            this.store_img = store_img;
        }

        public double getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(double original_price) {
            this.original_price = original_price;
        }

        public int getOriginal_amount() {
            return original_amount;
        }

        public void setOriginal_amount(int original_amount) {
            this.original_amount = original_amount;
        }

        public double getOriginal_total_price() {
            return original_total_price;
        }

        public void setOriginal_total_price(double original_total_price) {
            this.original_total_price = original_total_price;
        }

        public double getDiscount_total_price() {
            return discount_total_price;
        }

        public void setDiscount_total_price(double discount_total_price) {
            this.discount_total_price = discount_total_price;
        }

        public String getQr_code_id() {
            return qr_code_id;
        }

        public void setQr_code_id(String qr_code_id) {
            this.qr_code_id = qr_code_id;
        }

        public int getProblem_type() {
            return problem_type;
        }

        public void setProblem_type(int problem_type) {
            this.problem_type = problem_type;
        }

        public int getIsUseCoupon() {
            return isUseCoupon;
        }

        public void setIsUseCoupon(int isUseCoupon) {
            this.isUseCoupon = isUseCoupon;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * entity_id : 3
             * create_time : 2017-04-01 11:11
             * after_sales_application_id : 1
             * handle_info : 售后成功
             * handle_operator : 系统人员
             * remarks : 售后成功
             * type : 6
             * customerName : 李俊
             * customerTel : 18727120758
             * supplierName : 刘珊
             * supplierTel : 13635809635
             */

            private int entity_id;
            private String create_time;
            private int after_sales_application_id;
            private String handle_info;
            private String handle_operator;
            private String remarks;
            private int type;
            private String customerName;
            private String customerTel;
            private String supplierName;
            private String supplierTel;

            public int getEntity_id() {
                return entity_id;
            }

            public void setEntity_id(int entity_id) {
                this.entity_id = entity_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getAfter_sales_application_id() {
                return after_sales_application_id;
            }

            public void setAfter_sales_application_id(int after_sales_application_id) {
                this.after_sales_application_id = after_sales_application_id;
            }

            public String getHandle_info() {
                return handle_info;
            }

            public void setHandle_info(String handle_info) {
                this.handle_info = handle_info;
            }

            public String getHandle_operator() {
                return handle_operator;
            }

            public void setHandle_operator(String handle_operator) {
                this.handle_operator = handle_operator;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerTel() {
                return customerTel;
            }

            public void setCustomerTel(String customerTel) {
                this.customerTel = customerTel;
            }

            public String getSupplierName() {
                return supplierName;
            }

            public void setSupplierName(String supplierName) {
                this.supplierName = supplierName;
            }

            public String getSupplierTel() {
                return supplierTel;
            }

            public void setSupplierTel(String supplierTel) {
                this.supplierTel = supplierTel;
            }
        }
    }
}
