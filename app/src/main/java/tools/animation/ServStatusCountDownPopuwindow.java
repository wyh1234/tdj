package tools.animation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FeeTips;
import cn.com.taodaji.ui.activity.homepage.ManageActivity;
import cn.com.taodaji.ui.activity.myself.MyEquitiesActivity;
import cn.com.taodaji.ui.activity.myself.MySelfSupYearMoney;
import cn.com.taodaji.ui.activity.myself.YearMoneyRenewActivity;
import cn.com.taodaji.ui.activity.shopManagement.CommodityCategoryActivity;
import razerdp.basepopup.BasePopupWindow;

public class ServStatusCountDownPopuwindow extends BasePopupWindow implements View.OnClickListener{
    private View popupView;
    private TextView tv2,tv_num,tv3;
    private Button btn,btn1;
    private FeeTips.DataBean.InfoBean infoBean;
    private Context context;
    private ApplyUsePopuwindow applyUsePopuwindow;
    public ServStatusCountDownPopuwindow(Context context, FeeTips.DataBean.InfoBean infoBean) {
        super(context);
        this.context=context;
        this.infoBean=infoBean;
        btn = popupView.findViewById(R.id.btn);
        btn1 = popupView.findViewById(R.id.btn1);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        tv2= popupView.findViewById(R.id.tv2);
        tv3= popupView.findViewById(R.id.tv3);
        tv_num=popupView.findViewById(R.id.tv_num);
        tv3.setText("您的店铺"+(infoBean.getStoreType()==1?"旗舰店":"专卖店")+"特权将于"+infoBean.getEndTime()+"到期");
        tv_num.setText(infoBean.getLessDay()+"");
        tv2.setText((infoBean.getStoreType()==1?"旗舰店":"专卖店")+"特权还剩"+infoBean.getLessDay()+"天");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                Intent intent=null;
                if (infoBean.getIsSelected()==1){
                    intent =new Intent(getContext(), MySelfSupYearMoney.class);
                    intent.putExtra("storeId", PublicCache.loginSupplier.getStore()+"");
                }else if (infoBean.getIsSelected()==0){
                    intent=new Intent(getContext(), YearMoneyRenewActivity.class);
                    intent.putExtra("type",infoBean.getStoreType()==1?"QJ":"ZM");
                }

                context.startActivity(intent);
                dismiss();
                break;
            case R.id.btn1:
                if (applyUsePopuwindow!=null){
                    if (applyUsePopuwindow.isShowing()){
                        return;
                    }
                }
                applyUsePopuwindow = new ApplyUsePopuwindow(context);
                applyUsePopuwindow.setPopupWindowFullScreen(true);//铺满
                applyUsePopuwindow.showPopupWindow();
                dismiss();
                break;

        }

    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.year_money_popu_item);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
