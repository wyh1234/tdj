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
import cn.com.taodaji.ui.activity.myself.MySelfSupYearMoney;
import cn.com.taodaji.ui.activity.myself.YearMoneyRenewActivity;
import razerdp.basepopup.BasePopupWindow;

public class ExpiredPopuWindow extends BasePopupWindow implements View.OnClickListener {
    private View popupView;
    private Button btn, btn1;
    private Context context;
    private ApplyUsePopuwindow applyUsePopuwindow;
    private FeeTips.DataBean.InfoBean infoBean;

    public ExpiredPopuWindow(Context context, FeeTips.DataBean.InfoBean infoBean) {
        super(context);
        this.context = context;
        this.infoBean=infoBean;
        btn = popupView.findViewById(R.id.btn);
        btn1 = popupView.findViewById(R.id.btn1);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
                if (applyUsePopuwindow != null) {
                    if (applyUsePopuwindow.isShowing()) {
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
        popupView = createPopupById(R.layout.year_money_expired_item);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}