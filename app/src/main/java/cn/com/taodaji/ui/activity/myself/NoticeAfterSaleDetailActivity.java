package cn.com.taodaji.ui.activity.myself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.RefundDetail;
import cn.com.taodaji.model.entity.WaitNoticeResultBean;
import cn.com.taodaji.model.event.NoticeCountEvent;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.viewModel.adapter.SimpleAfterSalesAdapter;
import cn.com.taodaji.viewModel.vm.NoticeDetailVM;
import tools.activity.SimpleToolbarActivity;

public class NoticeAfterSaleDetailActivity extends SimpleToolbarActivity {
    private View mainView;
    private RecyclerView cart_item;
    private BaseViewHolder viewHolder;
    private SimpleAfterSalesAdapter simpleAfterSalesAdapter;
    private List<RefundDetail> items=new ArrayList<>();

    private WaitNoticeResultBean.DataBean.ItemsBean itemsBean;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();

        simple_title.setText("详情");
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_notice_after_sale_detail);
        body_scroll.addView(mainView);
        mainLayout.setBackgroundColor(UIUtils.getColor(R.color.bg_color));



        cart_item = ViewUtils.findViewById(mainView, R.id.cart_item);
        cart_item.setLayoutManager(new LinearLayoutManager(this));
        cart_item.addItemDecoration(new DividerItemDecoration(UIUtils.dip2px(3), R.color.white));

        viewHolder = new BaseViewHolder(mainView, new NoticeDetailVM(), null);

        itemsBean=(WaitNoticeResultBean.DataBean.ItemsBean)getIntent().getSerializableExtra("data");
        if (itemsBean != null) {

            int originatorRole = itemsBean.getOriginatorRole();
            String start_role= PublicCache.getRoleType().getValueOfKey(originatorRole);
            itemsBean.setStartNameAndRole(itemsBean.getCustomerName()+"("+start_role+")");


            viewHolder.setValues(itemsBean);

            //1.订单/付款成功
            //2.订单/付款超时订单关闭
            //3.提现/提现成功
            //4.售后申请/售后申请成功
            //5.售后完成
           /* if (itemsBean.getMessageType()==1) {
                viewHolder.setText(R.id.text_after_sale_title,"付款时间：");
            }else  if (itemsBean.getMessageType()==2) {
                viewHolder.setText(R.id.text_after_sale_title,"关闭时间：");
            }*/

        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (itemsBean == null) {
            return;
        }
        //updataUnReadNotice();
        onStartLoading();
        getRequestPresenter().after_details_salesNo(itemsBean.getAfterSalesApplicationNo(), new ResultInfoCallback<RefundDetail>(this) {
            @Override
            public void onSuccess(RefundDetail body) {
                if (body != null) {
                    items.clear();
                    items.add(body);
                    simpleAfterSalesAdapter = new SimpleAfterSalesAdapter();
                    simpleAfterSalesAdapter.setGoDetail(false);
                    simpleAfterSalesAdapter.setList(items);
                    cart_item.setAdapter(simpleAfterSalesAdapter);

                    //  1-退款    2- 换货   3-退款退货  4-补货
                    if (body.getApply_type() == 1) {
                        viewHolder.setText(R.id.text_return_amount_title,"申请退货：");
                        viewHolder.setText(R.id.text_return_money_title,"退款金额：");
                        viewHolder.setText(R.id.text_after_sale_title,"申请退款时间：");
                    }else{
                        viewHolder.setText(R.id.text_return_amount_title,"申请换货：");
                        viewHolder.setText(R.id.text_return_money_title,"换货金额：");
                        viewHolder.setText(R.id.text_after_sale_title,"申请售后时间：");
                    }



                    viewHolder.setText(R.id.text_business_number,body.getQr_code_id());
                    viewHolder.setText(R.id.text_return_amount,body.getAmount()+"");
                    viewHolder.setText(R.id.text_return_unit,body.getAvg_unit());

                    viewHolder.setText(R.id.text_return_money,body.getTotal_price()+"");
                    String dis_single_price= body.getDiscount_avg_price()+"元/"+body.getAvg_unit();
                    viewHolder.setText(R.id.text_single_price,dis_single_price);
                    if (body.getIsUseCoupon()>0) {
                        viewHolder.setVisibility(R.id.text_less_reason,View.VISIBLE);
                    }else{
                        viewHolder.setVisibility(R.id.text_less_reason,View.GONE);
                    }

               /*     text_return_amount
                            text_return_unit
                    text_return_amount_title text_return_money_title  text_single_price  text_less_reason*/
                }

            }

            @Override
            public void onFailed(int refundDetailResultInfo, String msg) {
//                loadingDimss();
                UIUtils.showToastSafe(msg);
            }
        });
    }

   /* private void updataUnReadNotice(){
        addRequest(ModelRequest.getInstance(1).updateNoticeUnReadCount(itemsBean.getEntityId(), PublicCache.site_login), new RequestCallback<WaitNoticeResultBean>() {
            @Override
            protected void onSuc(WaitNoticeResultBean body) {
                if (body.getErr()==0) {
                    EventBus.getDefault().postSticky(new NoticeCountEvent());
                }
            }
        });

    }*/
}
