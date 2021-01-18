package cn.com.taodaji.viewModel.vm;


import com.base.viewModel.BaseVM;

import cn.com.taodaji.R;

public class OrderListViewModel extends BaseVM {


    @Override
    protected void dataBinding() {
        putRelation(R.id.order_num, "lastName");
        putRelation(R.id.customerLogo, "customerLogo");
        putRelation(R.id.customerName, "lastName");
        putRelation(R.id.order_state, "statusCode");





        putRelation(R.id.order_time, "createTime");
        putRelation(R.id.count_image, "itemCount");
        putRelation(R.id.cart_price_sum, "actualTotalCost");
        putRelation(R.id.expectDeliveredDate, "expectDeliveredDate");
        putRelation(R.id.expectDeliveredDate_time, "expectDeliveredLatestTime");

    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.order_fold);
        putViewOnClick(R.id.order_oneMore);
        putViewOnClick(R.id.order_ok);
    }
}
