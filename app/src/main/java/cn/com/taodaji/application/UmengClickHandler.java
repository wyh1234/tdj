package cn.com.taodaji.application;

import android.content.Context;

import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cn.com.taodaji.model.event.NotificationCountEvent;
import cn.com.taodaji.model.event.NotificationStartActivityEvent;
import cn.com.taodaji.model.event.OrderEvent;
import cn.com.taodaji.ui.activity.myself.NewsListActivity;
import cn.com.taodaji.ui.activity.orderPlace.AfterSalesListActivity;
import cn.com.taodaji.ui.activity.orderPlace.OrderListActivity;
import cn.com.taodaji.ui.activity.purchaseBill.PurchaseMoneyListActivity;
import cn.com.taodaji.ui.activity.wallet.SupplyMoneyListActivity;
import cn.com.taodaji.ui.activity.wallet.SupplyMoneyNewListActivity;

/**
 * Created by Administrator on 2018/1/20.
 */

public class UmengClickHandler extends UmengNotificationClickHandler {
    @Override
    public void handleMessage(Context context, UMessage uMessage) {
        super.handleMessage(context, uMessage);
        if (!uMessage.clickOrDismiss) return;
        Map<String, String> map = uMessage.extra;
        if (map == null) return;
        EventBus.getDefault().post(new NotificationCountEvent());//发送通知数量变化
        String value = map.get("utype");

        switch (value) {
            case "101":
                EventBus.getDefault().postSticky(new OrderEvent(0));
//                ActivityManage.setTopActivity(OrderListActivity.class);
                /**{@link cn.com.taodaji.ui.activity.homepage.ManageActivity#onNotificationEvent(NotificationStartActivityEvent)}  接收*/
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(OrderListActivity.class));
                break;
            case "102":
                EventBus.getDefault().postSticky(new OrderEvent(0));
//                ActivityManage.setTopActivity(OrderListActivity.class);
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(OrderListActivity.class));
                break;
            case "103":
                EventBus.getDefault().postSticky(new OrderEvent(0));
//                ActivityManage.setTopActivity(OrderListActivity.class);
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(OrderListActivity.class));
                break;
            case "104":
//                ActivityManage.setTopActivity(PurchaseMoneyListActivity.class);
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(PurchaseMoneyListActivity.class));
                break;
            case "105":
                EventBus.getDefault().postSticky(new OrderEvent(0));
//                ActivityManage.setTopActivity(OrderListActivity.class);
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(OrderListActivity.class));
                break;
            case "106":
//                ActivityManage.setTopActivity(SupplyMoneyListActivity.class);
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(SupplyMoneyNewListActivity.class));
                break;
            case "107":
//                ActivityManage.setTopActivity(SupplyMoneyListActivity.class);
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(SupplyMoneyNewListActivity.class));
                break;
            case "108":
//                ActivityManage.setTopActivity(NewsListActivity.class);
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(NewsListActivity.class));
                break;
            case "109":
//                ActivityManage.setTopActivity(NewsListActivity.class);
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(NewsListActivity.class));
                break;
            case "110":
//                ActivityManage.setTopActivity(NewsListActivity.class);
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(NewsListActivity.class));
            case "111"://供货商售后
            case "112"://采购商售后
                EventBus.getDefault().postSticky(new NotificationStartActivityEvent(AfterSalesListActivity.class));

                break;
        }

    }
}
