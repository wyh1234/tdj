package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

import com.base.annotation.OnlyField;
import com.orm.dsl.Ignore;

public class RefundDetail {

    /**
     * entity_id : 23
     * create_time : 2017-03-07 17:28
     * province : 13
     * city : 193
     * after_sales_no : 1488879029353
     * store_id : 4
     * supplier_name : 淘大集
     * supplier_tel : 18656004515
     * store_name : 张记冻货店
     * customer_id : 42
     * customer_name : 斩风3
     * hotel_name : 测试酒店3
     * customer_tel : 18727120758
     * customer_address : 襄阳盛大酒3店
     * order_id : 545
     * order_item_id : 1913
     * sku : 32434343223
     * unit : 斤
     * name : 菠菜
     * nick_name : 四季青
     * price : 43
     * total_price : 430
     * amount : 10
     * problem_description : 菠菜质量稀烂
     * certificate_photos : https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png;https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png
     * apply_type : 1
     * status : 2
     * out_trade_no : f79e5520ed0f11e6807e4b081815219e
     * product_img : http://llwer.pi.com
     * order_no : 32423434243
     * "original_price": null,
     * "original_amount": null,
     * "original_total_price": null,
     * create_order_time : 2017-03-10 15:51
     * required_delivery_time : 2017-03-07 17:30
     * order_pay_time : 2017-03-07 17:30
     * customer_img : null
     * store_img : null
     * items : [{"entity_id":64,"create_time":"2017-03-10 15:51","after_sales_application_id":23,"handle_info":"售后同意退款，等待售后工作人员退款到采购商","handle_operator":"系统人员","remarks":null,"type":5}]
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
    @OnlyField
    private int order_item_id;
    private String sku;
    private String unit;
    private String name;
    private String nick_name;
    private BigDecimal price;
    private BigDecimal total_price;
    private BigDecimal amount;
    private BigDecimal original_price;
    private int original_amount;
    private BigDecimal original_total_price;
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
    private Object customer_img;
    private Object store_img;
    private List<ItemsBean> items;
    private int isUseCoupon;
    private String receive_hotel_name;

    private String avg_unit;
    private BigDecimal avg_price = BigDecimal.ZERO;
    private BigDecimal discount_avg_price = BigDecimal.ZERO;
    private BigDecimal level_2_value = BigDecimal.ZERO;
    private String level_2_unit;
    private BigDecimal level_3_value = BigDecimal.ZERO;
    private String level_3_unit;
    private int level_type;
    private String pro_remark;


    private int problem_type;
    private String qr_code_id;
    private BigDecimal discount_total_price;

    private String  remark;
    private Integer problem_info;


    private String packageName;  //包装名称
    private BigDecimal foregift = BigDecimal.ZERO; //押金费用(单个)
    //订单列表押金使用
    @Ignore
    private BigDecimal orderForegift = BigDecimal.ZERO; //押金总金额
    @Ignore
    private int isForegift;// 是否收取押金 0-不收，1-收
    @Ignore
    private int packageStatus = -1;//押金状态  -1 订购，0 - 未退, 1-已退完
    @Ignore
    private int packageNum;  //押金订购数量
    @Ignore
    private BigDecimal packageFee = BigDecimal.ZERO;//订购或未退押金金额
    private String shipping_line_code;

    public String getShipping_line_code() {
        return shipping_line_code;
    }

    public void setShipping_line_code(String shipping_line_code) {
        this.shipping_line_code = shipping_line_code;
    }

    public Integer getProblem_info() {
        return problem_info;
    }

    public void setProblem_info(Integer problem_info) {
        this.problem_info = problem_info;
    }

    public String getPro_remark() {
        return pro_remark;
    }

    public void setPro_remark(String pro_remark) {
        this.pro_remark = pro_remark;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public BigDecimal getForegift() {
        return foregift;
    }

    public void setForegift(BigDecimal foregift) {
        this.foregift = foregift;
    }

    public BigDecimal getOrderForegift() {
        return orderForegift;
    }

    public void setOrderForegift(BigDecimal orderForegift) {
        this.orderForegift = orderForegift;
    }

    public int getIsForegift() {
        return isForegift;
    }

    public void setIsForegift(int isForegift) {
        this.isForegift = isForegift;
    }

    public int getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(int packageStatus) {
        this.packageStatus = packageStatus;
    }

    public int getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(int packageNum) {
        this.packageNum = packageNum;
    }

    public BigDecimal getPackageFee() {
        return packageFee;
    }

    public void setPackageFee(BigDecimal packageFee) {
        this.packageFee = packageFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAvg_unit() {
        return avg_unit;
    }

    public void setAvg_unit(String avg_unit) {
        this.avg_unit = avg_unit;
    }

    public BigDecimal getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(BigDecimal avg_price) {
        this.avg_price = avg_price;
    }

    public BigDecimal getDiscount_avg_price() {
        return discount_avg_price;
    }

    public void setDiscount_avg_price(BigDecimal discount_avg_price) {
        this.discount_avg_price = discount_avg_price;
    }

    public BigDecimal getLevel_2_value() {
        return level_2_value;
    }

    public void setLevel_2_value(BigDecimal level_2_value) {
        this.level_2_value = level_2_value;
    }

    public String getLevel_2_unit() {
        return level_2_unit;
    }

    public void setLevel_2_unit(String level_2_unit) {
        this.level_2_unit = level_2_unit;
    }

    public BigDecimal getLevel_3_value() {
        return level_3_value;
    }

    public void setLevel_3_value(BigDecimal level_3_value) {
        this.level_3_value = level_3_value;
    }

    public String getLevel_3_unit() {
        return level_3_unit;
    }

    public void setLevel_3_unit(String level_3_unit) {
        this.level_3_unit = level_3_unit;
    }

    public int getLevel_type() {
        return level_type;
    }

    public void setLevel_type(int level_type) {
        this.level_type = level_type;
    }

    public int getProblem_type() {
        return problem_type;
    }

    public void setProblem_type(int problem_type) {
        this.problem_type = problem_type;
    }

    public String getQr_code_id() {
        return qr_code_id;
    }

    public void setQr_code_id(String qr_code_id) {
        this.qr_code_id = qr_code_id;
    }

    public BigDecimal getDiscount_total_price() {
        return discount_total_price;
    }

    public void setDiscount_total_price(BigDecimal discount_total_price) {
        this.discount_total_price = discount_total_price;
    }

    public String getReceive_hotel_name() {
        return receive_hotel_name;
    }

    public void setReceive_hotel_name(String receive_hotel_name) {
        this.receive_hotel_name = receive_hotel_name;
    }

    public int getIsUseCoupon() {
        return isUseCoupon;
    }

    public void setIsUseCoupon(int isUseCoupon) {
        this.isUseCoupon = isUseCoupon;
    }

    public BigDecimal getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(BigDecimal original_price) {
        this.original_price = original_price;
    }

    public int getOriginal_amount() {
        return original_amount;
    }

    public void setOriginal_amount(int original_amount) {
        this.original_amount = original_amount;
    }

    public BigDecimal getOriginal_total_price() {
        return original_total_price;
    }

    public void setOriginal_total_price(BigDecimal original_total_price) {
        this.original_total_price = original_total_price;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public Object getCustomer_img() {
        return customer_img;
    }

    public void setCustomer_img(Object customer_img) {
        this.customer_img = customer_img;
    }

    public Object getStore_img() {
        return store_img;
    }

    public void setStore_img(Object store_img) {
        this.store_img = store_img;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * entity_id : 64
         * create_time : 2017-03-10 15:51
         * after_sales_application_id : 23
         * handle_info : 售后同意退款，等待售后工作人员退款到采购商
         * handle_operator : 系统人员
         * remarks : null
         * type : 5
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
        private boolean selected;







        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
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
    }
}

