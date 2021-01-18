package cn.com.taodaji.ui.activity.myself;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.DialogUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import java.math.BigDecimal;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ReceiveFee;
import cn.com.taodaji.ui.pay.BuyPackageActivity;
import tools.activity.SimpleToolbarActivity;

public class PickupFeeActivity extends SimpleToolbarActivity implements View.OnClickListener {

    private View mainView;
    private RelativeLayout rl;
    private GlideImageView logo;
    private TextView shopName,shopStatus,pickupFee;
    private Button buyPackage,removeBalance;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("淘钱包");
        right_icon_text.setText("明细");
        right_icon.setImageResource(R.mipmap.detail3x);
        right_onclick_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickupFeeActivity.this,PickupDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PublicCache.loginSupplier.getIsAutomaticRenewal()==1){
            shopStatus.setText("已开通自动续费,每次续费金额"+getIntent().getIntExtra("balance",0)+"元");
            Drawable drawable= getResources().getDrawable(R.mipmap.auto_true3x);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            shopStatus.setCompoundDrawables(drawable,null,null,null);
            shopStatus.setTextColor(getResources().getColor(R.color.blue_2898eb));
        }else {
            shopStatus.setText("未开通自动续费");
            Drawable drawable= getResources().getDrawable(R.mipmap.auto_false3x);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            shopStatus.setCompoundDrawables(drawable,null,null,null);
        }
    }

    protected void initMainView() {
        mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_pickup_fee);
        body_other.addView(mainView);

        rl = ViewUtils.findViewById(mainView,R.id.rl_shop);
        rl.setOnClickListener(this);
        logo = ViewUtils.findViewById(mainView,R.id.giv_shop_logo);
        shopName = ViewUtils.findViewById(mainView,R.id.tv_shop_name);
        shopStatus = ViewUtils.findViewById(mainView,R.id.tv_shop_status);
        if (PublicCache.loginSupplier.getIsAutomaticRenewal()==1){
            shopStatus.setText("已开通自动续费");
            Drawable drawable= getResources().getDrawable(R.mipmap.auto_true3x);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            shopStatus.setCompoundDrawables(drawable,null,null,null);
            shopStatus.setTextColor(getResources().getColor(R.color.blue_2898eb));
        }else {
            shopStatus.setText("未开通自动续费");
            Drawable drawable= getResources().getDrawable(R.mipmap.auto_false3x);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            shopStatus.setCompoundDrawables(drawable,null,null,null);
        }
        pickupFee = ViewUtils.findViewById(mainView,R.id.tv_rest_fee);
        pickupFee.setText(new BigDecimal(getIntent().getStringExtra("fee"))+"");

        buyPackage = ViewUtils.findViewById(mainView,R.id.btn_buy_pickup_package);
        buyPackage.setOnClickListener(this);
        removeBalance = ViewUtils.findViewById(mainView,R.id.btn_remove_balance);
        removeBalance.setOnClickListener(this);

        if (PublicCache.loginSupplier!=null){
            ImageLoaderUtils.loadImage(logo, PublicCache.loginSupplier.getStorePics());
            shopName.setText(PublicCache.loginSupplier.getStoreName());
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_shop:
                Intent intent1 = new Intent(PickupFeeActivity.this,AutomaticRenewalActivity.class);
                intent1.putExtra("fee",getIntent().getIntExtra("balance",0)+"");
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_buy_pickup_package:
                Intent intent2 = new Intent(PickupFeeActivity.this, BuyPackageActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btn_remove_balance:
                DialogUtils.getInstance(PickupFeeActivity.this).getSimpleDialog("操作成功后，转出的款项将直接进入供货款的可提现余额中。","确定要转出淘钱包余额吗？").setPositiveButton("确定转出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getRequestPresenter().receiveFee(PublicCache.loginSupplier.getStore(), new RequestCallback<ReceiveFee>() {
                            @Override
                            protected void onSuc(ReceiveFee body) {
                                dialogInterface.dismiss();
                                UIUtils.showToastSafe(body.getData().getMsg());
                                PickupFeeActivity.this.finish();
                            }

                            @Override
                            public void onFailed(int errCode, String msg) {
                                dialogInterface.dismiss();
                                UIUtils.showToastSafe(msg);
                            }
                        });
                    }
                },R.color.gray_6a).setNegativeButton("不转出了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                },R.color.red_dark).show();
                break;
            default:break;
        }
    }
}
