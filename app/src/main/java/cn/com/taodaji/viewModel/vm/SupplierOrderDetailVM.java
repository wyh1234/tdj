package cn.com.taodaji.viewModel.vm;

import com.base.viewModel.BaseVM;

import cn.com.taodaji.R;

public class SupplierOrderDetailVM extends BaseVM{

    @Override
    protected void dataBinding() {
        //收款
        putRelation(R.id.img_payment_shop_logo, "customerLogo");
        putRelation(R.id.txt_payment_shop_name, "customerName");
//        putRelation(R.id.txt_payment_price, "subtotalCost");
       // putRelation(R.id.txt_order_payment_price, "subtotalCost");

        putRelation(R.id.txt_payment_commission, "commission");//佣金

        putRelation(R.id.txt_receive_time, "expectDeliveredDate");//

        putRelation(R.id.txt_order_number, "extOrderId");
        putRelation(R.id.txt_pay_time, "paymentTime");
        putRelation(R.id.txt_cancel_time, "createTime");

        //售后
        putRelation(R.id.img_shop_logo, "customer_img");
        putRelation(R.id.txt_shop_name, "hotel_name");
//        putRelation(R.id.txt_after_total_price, "total_price");

        putRelation(R.id.txt_after_receive_time, "required_delivery_time");//


        putRelation(R.id.txt_back_count, "amount");
        putRelation(R.id.txt_back_unit_1, "avg_unit");

       // putRelation(R.id.txt_back_price, "total_price");
        putRelation(R.id.txt_back_commission, "commission");

        putRelation(R.id.txt_name, "name");
        putRelation(R.id.txt_nick_name, "nick_name");

        putRelation(R.id.txt_single_price, "price");
       // putRelation(R.id.txt_single_unit_1, "avg_unit");
        putRelation(R.id.txt_buy_count, "original_amount");
        putRelation(R.id.txt_buy_unit_2, "unit");
        putRelation(R.id.txt_buy_price, "original_total_price");

        putRelation(R.id.txt_back_order_number, "order_no");
        putRelation(R.id.txt_back_number, "after_sales_no");
        putRelation(R.id.txt_back_pay_time, "order_pay_time");
        putRelation(R.id.txt_ask_back_time, "create_time");
        putRelation(R.id.txt_back_finish_time, "complete_time");

        //提现

        putRelation(R.id.txt_getcash_total_price, "capital_withdrawal");
        putRelation(R.id.txt_get_cash_bank, "bank_name");
        putRelation(R.id.txt_account_bank, "bank_address");
        putRelation(R.id.txt_bank_number, "account_no");

        putRelation(R.id.txt_ask_get_cash_time, "create_time");
        putRelation(R.id.txt_accept_get_cash_time, "update_time");

    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.txt_go_order_detail);
    }
}
