package cn.com.taodaji.viewModel.vm;

import com.base.viewModel.BaseVM;

import cn.com.taodaji.R;


public class ReceiptAddressVM extends BaseVM {
    @Override
    protected void dataBinding() {
        putRelation(R.id.consignee, "consignee");
        putRelation(R.id.phoneNumber, "phoneNumber");
        putRelation(R.id.hotelName, "hotelName");
        putRelation(R.id.hotelName, "hotelName");
        putRelation(R.id.street, "street");
        putRelation(R.id.provinceName, "provinceName");
        putRelation(R.id.cityName, "cityName");
        putRelation(R.id.address_default, "isDefault");

    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.address_default);
        putViewOnClick(R.id.address_update);
        putViewOnClick(R.id.address_delete);
        putViewOnClick(R.id.address_detail);
    }
}
