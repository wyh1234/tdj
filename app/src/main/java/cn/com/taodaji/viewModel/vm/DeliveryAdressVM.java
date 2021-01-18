package cn.com.taodaji.viewModel.vm;

import com.base.viewModel.BaseVM;

import cn.com.taodaji.R;

public class DeliveryAdressVM extends BaseVM {

    @Override
    protected void dataBinding() {
        putRelation(R.id.consignee, "name");
        putRelation(R.id.phoneNumber, "telephone");
        putRelation(R.id.hotelName, "hotelName");
        putRelation(R.id.street, "address");
    }


    @Override
    protected void addOnclick() {

    }
}
