package cn.com.taodaji.model.presenter;

import android.support.test.runner.AndroidJUnit4;

import com.base.entity.ResultInfo;
import com.base.utils.JavaMethod;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by yangkuo on 2018-09-17.
 */
@RunWith(AndroidJUnit4.class)
public class RequestPresenterTest {

    @Test
    public void getSupplierOrderPayment() {
    }

    @Test
    public void getSupplierOrderAfterSale() {
    }

    @Test
    public void getSupplierOrderGetCash() {
    }

    @Test
    public void getSupplierOrderFormItemDetailList() {
    }


    @Test
    public void test() {

        String json = "{\"err\": \"2\", \"msg\": 21}";

        ResultInfo resultInfo = JavaMethod.getJsonBean(json, ResultInfo.class);


        if (resultInfo != null) {

        }


    }


}