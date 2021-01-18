package cn.com.taodaji.ui.ppw;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.R;
import cn.com.taodaji.model.event.PickupEvent;
import razerdp.basepopup.BasePopupWindow;

public class SwitchPickupServicePopupWindow extends BasePopupWindow {
    private View popupView;
    private TextView name,status,good,time,time2,time3,rate;
    private Button confrim;
    public SwitchPickupServicePopupWindow(Context context,String titles,String rates,String goods,String times,String times2,String times3,int type,int position) {
        super(context);
        name =popupView.findViewById(R.id.tv_pickup_name);
        name.setText(titles);
        good = popupView.findViewById(R.id.tv_pickup_goods);
        good.setText("接收商品："+goods);
        time = popupView.findViewById(R.id.tv_pickup_time);
        time.setText(times);
        time2 = popupView.findViewById(R.id.tv_pickup_time_2);
        time3 = popupView.findViewById(R.id.tv_pickup_time_3);
        confrim = popupView.findViewById(R.id.btn_message_confirm);
        status = popupView.findViewById(R.id.tv_item_status);
        rate = popupView.findViewById(R.id.tv_pickup_rate);
        rate.setText("接货费：按接货金额的"+rates+"缴纳");
        switch (type){
            case 0:
                status.setText("当前状态：已关闭");
                confrim.setText("重新申请开通");
                time2.setText("关闭时间"+times2);
                break;
            case 1:
                status.setText("当前状态：已开通");
                confrim.setText("确定申请关闭");
                time2.setText("开通时间："+times2);
                break;
            case 2:
                status.setText("当前状态：等待开通");
                confrim.setText("取消申请");
                time2.setText("申请时间："+times2);
                break;
            case 4:
                status.setText("当前状态：申请开通被驳回");
                confrim.setText("重新申请开通");
                time2.setText("申请时间："+times2);
                time3.setText("驳回时间："+times3);
                break;
            case 6:
                status.setText("当前状态：等待关闭");
                confrim.setText("取消关闭");
                time2.setText("申请关闭时间"+times2);
                break;
            case 7:
                status.setText("当前状态：申请关闭被驳回");
                confrim.setText("重新申请关闭");
                time2.setText("申请时间："+times2);
                time3.setText("驳回时间："+times3);
                break;
            case 9:
                status.setText("当前状态：未开通");
                confrim.setText("确定申请开通");
                break;
                default:break;
        }
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type==0||type==9||type==4){
                    EventBus.getDefault().post(new PickupEvent(position, 3, 0));
                    SwitchPickupServicePopupWindow.this.dismiss();
                }
                if (type==1||type==7){
                    EventBus.getDefault().post(new PickupEvent(position, 5, 1));
                    SwitchPickupServicePopupWindow.this.dismiss();
                }
                if (type==2){
                    EventBus.getDefault().post(new PickupEvent(position, 8, 0));
                    SwitchPickupServicePopupWindow.this.dismiss();
                }
                if (type==6){
                    EventBus.getDefault().post(new PickupEvent(position, 1, 1));
                    SwitchPickupServicePopupWindow.this.dismiss();
                }
            }
        });
    }


    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView().findViewById(R.id.btn_close_ppw);
    }

    @Override
    public View onCreatePopupView() {
        popupView = createPopupById(R.layout.ppw_switch_pickup);
        return popupView;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }
}
