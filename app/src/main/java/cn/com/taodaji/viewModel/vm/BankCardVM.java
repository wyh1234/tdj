package cn.com.taodaji.viewModel.vm;

import android.support.annotation.NonNull;
import android.view.View;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;

import com.base.viewModel.BaseVM;
import com.base.utils.CustomerData;

public class BankCardVM extends BaseVM {
    @Override
    protected void dataBinding() {
        putRelation(R.id.imageView, "bankType");
        putRelation(R.id.bankCard_name, "bankName");
        putRelation(R.id.bankCard_no, "accountNo");
    }

    @Override
    protected void addOnclick() {
        putViewOnClick(R.id.item_view);
    }


    private CustomerData<Integer, String, String> bank = PublicCache.getBank();


    @Override
    public void setValues(@NonNull View view, Object value) {
        if (view == null) return;
        if (R.id.imageView == view.getId()) {
            int bankType = (int) value;
            value = bank.getKeyOfId(bankType);
        }
        super.setValues(view, value);
    }

}
