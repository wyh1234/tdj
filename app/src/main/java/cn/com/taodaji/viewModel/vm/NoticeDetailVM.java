package cn.com.taodaji.viewModel.vm;

import com.base.viewModel.BaseVM;

import cn.com.taodaji.R;

public class NoticeDetailVM extends BaseVM {
    @Override
    protected void dataBinding() {
        putRelation("messageTitle", R.id.text_reason);
        putRelation("capitalAmount",R.id.text_money);
        putRelation("startNameAndRole", R.id.text_start_person);//nameAndRole
        putRelation("payNameAndRole", R.id.text_pay_person);
        putRelation("originatorName", R.id.text_shop_name);
        putRelation("createOrderTime", R.id.text_order_time);
        putRelation("afterSalesApplicationCreateTime", R.id.text_after_sale_time);

        putRelation("handleCreateTime", R.id.text_apply_time);
        putRelation("handleWithdrawalTime", R.id.text_handle_time);

    }
}
