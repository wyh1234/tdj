package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.utils.JavaMethod;
import com.base.utils.SerializableMap;
import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.AddressSave;
import cn.com.taodaji.model.entity.AddressUpdate;
import cn.com.taodaji.model.entity.GoodsReceiptAddressBean;
import tools.activity.SimpleToolbarActivity;

public class GoodsReceiptAddressAddActivity extends SimpleToolbarActivity implements View.OnClickListener {


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
       // toolbar.setNavigationIcon(R.mipmap.left_arrow_gary);//设置导航栏图标
        simple_title.setText("修改收货人");
        //simple_title.setTextColor(UIUtils.getColor(R.color.white));
        right_textView.setText("保存");
      //  right_textView.setTextColor(UIUtils.getColor(R.color.white));
        right_textView.setBackgroundResource(R.drawable.r_round_rect_solid_transparent);
        title_right.setOnClickListener(this);
        title_split_line.setVisibility(View.VISIBLE);

    }

    private View mainView;

    private GoodsReceiptAddressBean itemsBean;
    private boolean isUpdate;
    private TextView place;
    private  Boolean flg1;//如果是提交訂單或者我的名片中跳转进来 则为true，表示地址不可编辑
    private boolean flg2;
    private TextView mEditText;
   // private TextView mEditTextXiangQing;
    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_goods_receipt_address_add);
        body_scroll.addView(mainView);
        mEditText = ViewUtils.findViewById(mainView,R.id.activity_myself_goods_receipt_address_add_editext);
       // mEditTextXiangQing = ViewUtils.findViewById(mainView,R.id.myself_goods_receipt_address_add_editext_xiangqing);
        place = ViewUtils.findViewById(mainView, R.id.place);
        place.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        itemsBean = (GoodsReceiptAddressBean) getIntent().getSerializableExtra("data");
        flg1 =  getIntent().getBooleanExtra("flg",false);
        flg2 = getIntent().getBooleanExtra("flg1",false);
        if (itemsBean != null) {
            UIDataProcessing.setChildControlsValue(mainView, itemsBean);
            isUpdate = true;
        } else {
            isUpdate = false;
        }
        place.setText(itemsBean.getProvinceName()+itemsBean.getCityName()+itemsBean.getStreet());
        if (flg1 || flg2){
            place.setEnabled(false);
//            mEditTextXiangQing.setFocusable(false);
//            mEditTextXiangQing.setFocusableInTouchMode(false);
        }else{
            place.setEnabled(true);
//            mEditTextXiangQing.setFocusable(true);
//            mEditTextXiangQing.setFocusableInTouchMode(true);
        }

    }

    private SerializableMap mymap;

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.place:
//                intent = new Intent(this, RegionSelectionActivity.class);
//                startActivityForResult(intent, 100);
                break;
            case R.id.title_right:
                final Map<String, Object> map = new HashMap<>();
                UIDataProcessing.getChildControlsValue(mainView, map);
                for (Object key : map.keySet()) {
                    Object v = map.get(key);
                    if (key.equals("phoneNumber")) {
                        if (v == null) {
                            UIUtils.showToastSafe("姓名、手机、单位、详细地址等不可为空");
                            return;
                        }
                        if (!UserNameUtill.isPhoneNO(v.toString())) {
                            UIUtils.showToastSafe("手机号码格式不正确");
                            return;
                        }

                    }
                    if (v == null || v.toString().length() == 0) {
                        if (key.equals("consignee") || key.equals("phoneNumber") || key.equals("hotelName") || key.equals("street")) {
                            UIUtils.showToastSafe("姓名、手机、单位、详细地址等不可为空");
                        }
                        return;
                    }
                }
                loading();
                if (isUpdate) {
                    //比较选出不同的参数
                    Map<String, Object> oldmap = JavaMethod.transBean2Map(itemsBean);
                    Map<String, Object> updateMap = JavaMethod.getDiffrentMap(map, oldmap);
                    updateMap.put("addressId", itemsBean.getAddressId());
                    updateMap.put("isDefault", itemsBean.getIsDefault());
                    mymap = new SerializableMap(updateMap);
                    //mymap.setMap(updateMap);
                    getRequestPresenter().updateAddress(updateMap, new RequestCallback<AddressUpdate>() {
                        @Override
                        public void onSuc(AddressUpdate body) {
                            loadingDimss();
                            setResult(RESULT_OK, new Intent().putExtra("data", mymap));
                            finish();
                        }

                        @Override
                        public void onFailed(int addressUpdate, String msg) {
                            loadingDimss();
                        }
                    });
                } else {
                    map.put("isDefault", "1");
                    mymap = new SerializableMap(map);
                    //mymap.setMap(map);
                    getRequestPresenter().addAddress(map, new RequestCallback<AddressSave>() {
                        @Override
                        public void onSuc(AddressSave body) {
                            loadingDimss();
                            setResult(RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFailed(int addressSave, String msg) {
                            loadingDimss();
                        }
                    });
                }
                break;
        }

    }

}
