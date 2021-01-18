package cn.com.taodaji.ui.activity.packingCash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.base.entity.ResultInfo;
import com.base.listener.UploadPicturesDoneListener;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.greenrobot.eventbus.Subscribe;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.OrderDetail;
import cn.com.taodaji.model.entity.PackingReturnMaxBean;
import cn.com.taodaji.model.event.PackingCashCancleEvent;
import cn.com.taodaji.model.event.PackingCashReturnCarbeanOrOrderBeanEvent;
import cn.com.taodaji.model.event.PackingCashReturnEvent;
import cn.com.taodaji.model.event.PackingCashReturnSweepCodeEvent;
import cn.com.taodaji.model.sqlBean.CartGoodsBean;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.vm.OrderPlaceGoodsDetailVM;
import cn.com.taodaji.viewModel.vm.PackingCashCarGoodsBeanVM;
import cn.com.taodaji.viewModel.vm.PackingCashOrderPlaceGoodsVM;
import tools.activity.SimpleToolbarActivity;
import tools.fragment.AddedPicturesFragment;

public class PackingCashReturnActivity extends SimpleToolbarActivity implements UploadPicturesDoneListener, View.OnClickListener{
    private CartGoodsBean cartGoodsBean;
    private OrderDetail.ItemsBean orderBean;
    private Button btn;

    private AddedPicturesFragment addedPicturesFragment;
    private TextView  tv_supply_name;
    private EditText edit_return_count;
    private TextView txt_return_money;

    private int applyNum;
    private BigDecimal applyFee;//押金费用
    private int applyMaxNum;
    private BigDecimal foregift=BigDecimal.ZERO;//押金单价


    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        simple_title.setText("退押金");
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }


    @Override
    protected void initData() {
        getPackingCashReturn();
    }

    private void getPackingCashReturn(){
        loading();
        Map<String, Object> map = new HashMap<>();
        map.put("site", PublicCache.site_login);
        if (cartGoodsBean != null) {
            map.put("packOrderId", cartGoodsBean.getPackOrderId());
        } else if (orderBean != null) {
            map.put("packOrderId", orderBean.getPackOrderId());
        }
        addRequest(ModelRequest.getInstance().getCashMaxCount(map), new RequestCallback<ResultInfo<PackingReturnMaxBean>>() {
            @Override
            protected void onSuc(ResultInfo<PackingReturnMaxBean> body) {
                loadingDimss();
                if (body.getData()!= null) {
                    applyMaxNum = body.getData().getApplyMaxNum();
                    foregift =body.getData().getForegift();
                    edit_return_count.setHint("最多可退"+applyMaxNum+"个");
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                loadingDimss();
                UIUtils.showToastSafe(msg);
            }

        });
    }

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_packing_cash_return);
        body_scroll.addView(mainView);

        btn=mainView.findViewById(R.id.btn_submit_return_packing_cash);
        btn.setOnClickListener(this);
        tv_supply_name=mainView.findViewById(R.id.tv_supply_name);

        edit_return_count=mainView.findViewById(R.id.edit_return_count);
        txt_return_money=mainView.findViewById(R.id.txt_return_money);

        View goods_information = ViewUtils.findViewById(mainView, R.id.pack_cash_create_layout);


        if (cartGoodsBean != null) {
            //列表
            BaseViewHolder holder = new BaseViewHolder(goods_information, new PackingCashCarGoodsBeanVM(), null);
            holder.setValues(cartGoodsBean);

            if (PublicCache.loginPurchase != null){
                tv_supply_name.setText(cartGoodsBean.getStoreName());
            }


        } else if (orderBean != null) {
            //详情
            BaseViewHolder holder = new BaseViewHolder(goods_information, new PackingCashOrderPlaceGoodsVM(), null);
            holder.setValues(orderBean);

            if (PublicCache.loginPurchase != null){
                tv_supply_name.setText(orderBean.getStoreName());
            }
        }

        edit_return_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (TextUtils.isEmpty(s.toString())) {
                        applyNum=0;
                        txt_return_money.setText("0元");
                    }else{
                        applyNum = Integer.valueOf(s.toString());
                        if (applyNum>0) {
                            applyFee=foregift.multiply(BigDecimal.valueOf(applyNum));
                            txt_return_money.setText(applyFee.stripTrailingZeros().toPlainString()+"元");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        addedPicturesFragment = (AddedPicturesFragment) getSupportFragmentManager().findFragmentById(R.id.addedPicturesFragment);
        //addedPicturesFragment.setBackgroundColor(R.color.white);
        addedPicturesFragment.setMaxSelect(3);
    }

    @Override
    public void onClick(View v) {
        if (PublicCache.loginPurchase==null)return ;
        if (PublicCache.loginPurchase.getEmpRole()==2||PublicCache.loginPurchase.getEmpRole()==5) {
            UIUtils.showToastSafesShort(PublicCache.getRoleType().getValueOfKey(PublicCache.loginPurchase.getEmpRole())+"没有该操作权限");
            return ;
        }
        switch(v.getId()){
            case R.id.btn_submit_return_packing_cash :{
                if (applyNum<=0) {
                    UIUtils.showToastSafesShort("请输入需要退的数量");
                    return;
                }
                if (applyNum>applyMaxNum) {
                    UIUtils.showToastSafesShort("最多可退"+applyMaxNum+"个");
                    return;
                }



                if (!addedPicturesFragment.isUploadDone()) {
                    addedPicturesFragment.setCallBack(true);
                } else uploadPicturesIsDone(null);

            break;
            }
            default:
            break;
        }
    }

    @Override
    public void uploadPicturesIsDone(Object obj) {
        if (isDestroyed()) return;
        if (TextUtils.isEmpty(addedPicturesFragment.getImageStr())) {
            UIUtils.showToastSafesShort("请选择押金物的图片");
            return;
        }
        loading();
        btn.setEnabled(false);

        Map<String, Object> map = new HashMap<>();
        map.put("site", PublicCache.site_login);
        map.put("customerName", PublicCache.loginPurchase.getHotelName());
        map.put("applyNum", applyNum);//申请数量
        map.put("applyFee", applyFee); //押金费用
        map.put("packageImg", addedPicturesFragment.getImageStr()); //包装物图片,逗号分隔

        if (cartGoodsBean != null) {
            map.put("packOrderId", cartGoodsBean.getPackOrderId());
        } else if (orderBean != null) {
            map.put("packOrderId", orderBean.getPackOrderId());
        }

        addRequest(ModelRequest.getInstance().applyPackingCash(map), new RequestCallback<ResultInfo>() {
            @Override
            protected void onSuc(ResultInfo body) {

                if (body .getErr()==0) {
                    UIUtils.showToastSafe("提交成功");
                    if (cartGoodsBean!=null ) {
                        EventBus.getDefault().post(new PackingCashReturnEvent(cartGoodsBean.getAfterSaleId()));
                    }
                    if (applyNum==applyMaxNum) {
                        EventBus.getDefault().post(new PackingCashReturnSweepCodeEvent());
                    }
                    finish();
                }else{
                    UIUtils.showToastSafe(body.getMsg());
                }

                loadingDimss();
            }

            @Override
            public void onFailed(int errCode, String msg) {
                btn.setEnabled(true);
                UIUtils.showToastSafe(msg);
                loadingDimss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //列表入口
    @Subscribe(sticky = true)
    public void onEvent(PackingCashReturnCarbeanOrOrderBeanEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        cartGoodsBean = event.getCartGoodsBean();
        orderBean= event.getOrderBean();
    }


    public static void startActivity(Context context, CartGoodsBean unit) {
        if (unit == null) return;
        unit.setCurrentStatus(unit.getPackageStatus());//一定是0未退状态
        unit.setCurrentFee(unit.getPackageFee());
        unit.setCurrentNum(unit.getPackageNum());
        EventBus.getDefault().postSticky(new PackingCashReturnCarbeanOrOrderBeanEvent(unit,null));
        context.startActivity(new Intent(context, PackingCashReturnActivity.class));
    }

    public static void startActivity(Context context, OrderDetail.ItemsBean unit) {
        if (unit == null) return;
        unit.setCurrentStatus(unit.getPackageStatus());//一定是0未退状态
        unit.setCurrentFee(unit.getPackageFee());
        unit.setCurrentNum(unit.getPackageNum());
        EventBus.getDefault().postSticky(new PackingCashReturnCarbeanOrOrderBeanEvent(null,unit));
        context.startActivity(new Intent(context, PackingCashReturnActivity.class));
    }
}
