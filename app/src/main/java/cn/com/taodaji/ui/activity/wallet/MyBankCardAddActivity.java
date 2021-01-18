package cn.com.taodaji.ui.activity.wallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.BankAccount;

import com.base.utils.DialogUtils;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.entity.ResultInfo;
import com.base.retrofit.RequestCallback;
import cn.com.taodaji.ui.ppw.RecyclerViewPopupWindow;
import tools.activity.SimpleToolbarActivity;
import com.base.utils.ADInfo;
import com.base.utils.BandCardEditText;
import com.base.utils.CustomerData;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;


public class MyBankCardAddActivity extends SimpleToolbarActivity implements View.OnClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("添加银行卡");
    }


    private BandCardEditText et_bankCard_no;
    private EditText et_bankCard_name2;
    private int bankType = 4;//默认为中国银行
    private boolean isCardNo = false;
    private TextView bankCard_bind;
    private View et_bankCard_name1;
    private TextView bankCard_name,tips;
    private ImageView imageView;
    private CustomerData<Integer, String, String> bank = PublicCache.getBank();
    private final String[] info = {"可以通过银行卡客服电话来查询：银行卡背面会有该银行的客服电话，拨打银行卡的客服，然后转人工服务就能查询，需要验证个人信息。"};
    private final int[] tipsIndex ={0};
    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_myself_my_bank_card_add);
        body_other.addView(mainView);
        bankCard_bind = ViewUtils.findViewById(mainView, R.id.bankCard_bind);
        bankCard_bind.setOnClickListener(this);
        setViewBackColor(bankCard_bind);
        TextView name = ViewUtils.findViewById(mainView, R.id.name);

        if (PublicCache.loginSupplier != null)
            name.setText(PublicCache.loginSupplier.getRealname());
        else if (PublicCache.loginPurchase != null)
            name.setText(PublicCache.loginPurchase.getRealname());

        et_bankCard_name1 = ViewUtils.findViewById(mainView, R.id.et_bankCard_name1);
        tips = ViewUtils.findViewById(mainView,R.id.tv_add_card_tips);
        tips.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tips.getPaint().setAntiAlias(true);//抗锯齿
        imageView = ViewUtils.findViewById(mainView, R.id.imageView);
        imageView.setImageResource(bank.getKeyOfId(bankType));
        bankCard_name = ViewUtils.findViewById(mainView, R.id.bankCard_name);
        bankCard_name.setText(bank.getValueOfId(bankType));
        et_bankCard_name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerViewPopupWindow rvp = new RecyclerViewPopupWindow(MyBankCardAddActivity.this, v.getWidth());
                rvp.setOnCustomerItemClickListener(new OnItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int onclickType, int position, Object itemBean) {
                        if (onclickType == 0) {
                            ADInfo bean = (ADInfo) itemBean;
                            bankType = bean.getImageGoodsType();
                            imageView.setImageResource((int) bean.getImageObject());
                            bankCard_name.setText(bean.getImageName());
                            return true;
                        }
                        return false;
                    }
                });
                rvp.showAsDropDown(et_bankCard_name1);
            }
        });
        et_bankCard_name2 = ViewUtils.findViewById(mainView, R.id.et_bankCard_name2);
        et_bankCard_no = ViewUtils.findViewById(mainView, R.id.et_bankCard_no);
        et_bankCard_no.setBankCardListener(new BandCardEditText.BankCardListener() {
            @Override
            public void success(String name) {
                isCardNo = true;
            }

            @Override
            public void failure(String str) {
                isCardNo = false;
                if (str.length() == 16 || str.length() == 19) {
                    et_bankCard_no.setError("卡号输入不正确");
                }
            }
        });

        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.getInstance(MyBankCardAddActivity.this).getTipsWithButtonDialog("查询开户支行说明",info,tipsIndex).show();
            }
        });
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (et_bankCard_name2.getText().length() == 0) {
            UIUtils.showToastSafesShort("请输入开户支行");
            return;
        }
        if (et_bankCard_no.getText().length() == 0) {
            UIUtils.showToastSafesShort("请输入银行卡号");
            return;
        }
        if (!isCardNo) {
            UIUtils.showToastSafesShort("银行卡号输入不正确");
            return;
        }


        /**
         * 添加银行账户
         *
         * 供应商
         * 参数名	类型	必须(1是/0否)	说明
         * provinceId	int	1	省ID
         * cityId	int	1	城市ID
         * storeId	int	1	店铺ID|
         * storeName	String	1	店铺名称
         * marketId	int	1	市场ID
         * bankName	String	1	银行名字
         * accountNo	String	1	银行账号
         * ownerName	String	1	银行卡真实户名
         * bankAddress	String	1	银行卡开户行地址
         * bankType	int	1	银行类型，1、工行 2、建行 3、农行 4、中行
         *
         *采购商
         *  :provinceId	int	1	省ID
         :cityId	int	1	城市ID
         :customerId	int	1	采购商ID
         :hotelName	int	1	酒店名称
         :bankType	int	1	类型1、表示支付宝 2、表示银行卡
         :isDefault	int	1	是否默认
         :bankName	int	1	“支付宝”或者 “XXXX银行”
         :accountNo	int	1	可以是 支付宝账、银行账号卡号
         :ownerName	int	1	持卡姓名 或者 支付宝姓名
         :bankAddress	int	1	银行地址
         */

        Map<String, Object> map = new HashMap<>();
        loading();
        if (PublicCache.loginSupplier != null) {
            map.put("provinceId", PublicCache.loginSupplier.getProvinceId());
            map.put("cityId", PublicCache.loginSupplier.getCityId());
            map.put("storeId", PublicCache.loginSupplier.getStore());
            map.put("storeName", PublicCache.loginSupplier.getStoreName());
            map.put("marketId", PublicCache.loginSupplier.getMarketId());
            map.put("bankName", bankCard_name.getText().toString());
            map.put("accountNo", et_bankCard_no.getText().toString().replaceAll(" ", ""));
            map.put("ownerName", PublicCache.loginSupplier.getRealname());
            map.put("bankAddress", et_bankCard_name2.getText().toString());
            //  map.put("bankType", bankType);

            getRequestPresenter().bankAccount(map, new RequestCallback<BankAccount>() {
                @Override
                public void onSuc(BankAccount body) {
                    loadingDimss();
                    finish();
                }

                @Override
                public void onFailed(int bankAccount, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });


            //发送网络请求
        } else if (PublicCache.loginPurchase != null) {

            map.put("provinceId", PublicCache.loginPurchase.getProvinceId());
            map.put("cityId", PublicCache.loginPurchase.getCityId());
            map.put("customerId", PublicCache.loginPurchase.getEntityId());
            map.put("hotelName", PublicCache.loginPurchase.getHotelName());
            map.put("isDefault", 1);
            map.put("bankName", bankCard_name.getText().toString());
            map.put("accountNo", et_bankCard_no.getText().toString().replaceAll(" ", ""));
            map.put("ownerName", PublicCache.loginPurchase.getRealname());
            map.put("bankAddress", et_bankCard_name2.getText().toString());
            map.put("bankType", 2);

            getRequestPresenter().customerFinance_bankAccount(map, new RequestCallback<ResultInfo>() {
                @Override
                public void onSuc(ResultInfo body) {
                    loadingDimss();
                    finish();
                }

                @Override
                public void onFailed(int resultInfo, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort(msg);
                }
            });
        }

    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyBankCardAddActivity.class);
        context.startActivity(intent);
    }
}
