package tools.animation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.FeeTips;
import cn.com.taodaji.ui.activity.shopManagement.CommodityCategoryActivity;
import razerdp.basepopup.BasePopupWindow;

public class WelcomeEnterPopuWindow extends BasePopupWindow implements View.OnClickListener{
    private View popupView;
    private TextView tv2;
    private Button welcome_btn,welcome_cancel_btn;
    private FeeTips.DataBean.InfoBean infoBean;
    private Context context;
    public WelcomeEnterPopuWindow(Context context, FeeTips.DataBean.InfoBean infoBean) {
        super(context);
        this.context=context;
        this.infoBean=infoBean;
        welcome_btn = popupView.findViewById(R.id.welcome_btn);
        welcome_cancel_btn = popupView.findViewById(R.id.welcome_cancel_btn);
        welcome_cancel_btn.setOnClickListener(this);
        welcome_btn.setOnClickListener(this);
        tv2= popupView.findViewById(R.id.tv2);
        if (infoBean.getReminrType()==1){
            tv2.setText("您获得了一份"+30+"天"+(infoBean.getStoreType()==1?"旗舰店":"专卖店")+"体验服务");

        }else if (infoBean.getReminrType()==2){
            tv2.setText("您已三天未发布商品");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.welcome_btn:
                Intent  intent = new Intent(context, CommodityCategoryActivity.class);
                context.startActivity(intent);
                dismiss();
                break;
            case R.id.welcome_cancel_btn:
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
        popupView = createPopupById(R.layout.welcome_enter_popu_item);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
