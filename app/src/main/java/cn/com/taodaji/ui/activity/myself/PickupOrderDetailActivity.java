package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.base.retrofit.RequestCallback;
import com.base.swipetoloadlayout.SwipeToLoadLayout;
import com.base.swipetoloadlayout.listener.OnLoadMoreListener;
import com.base.utils.UIUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.PickUpDetail;
import cn.com.taodaji.model.entity.PickUpGoods;
import cn.com.taodaji.model.entity.PickupOrderDetail;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.viewModel.adapter.PickupOrderDetailAdapter;
import tools.activity.SimpleToolbarActivity;

import static java.lang.Double.parseDouble;

public class PickupOrderDetailActivity extends SimpleToolbarActivity {

    private SwipeToLoadLayout swipe;
    private int pageNo =1;
    private RecyclerView recyclerView;
    private PickupOrderDetailAdapter adapter;
    private ArrayList<MultiItemEntity> goodsList = new ArrayList<>();
    private TextView pickupFee,payFee,goods,title;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("接货订单明细");
    }

    protected void initMainView() {
        View view = getLayoutView(R.layout.activity_pickup_order_detail);
        body_other.addView(view);

        pickupFee = view.findViewById(R.id.tv_pickup_fee);
        payFee = view.findViewById(R.id.tv_pay_fee);
        goods = view.findViewById(R.id.tv_goods_count);
        title = view.findViewById(R.id.tv_title_text);

        payFee.setText("(收："+getIntent().getStringExtra("payMoney")+")");
        pickupFee.setText("接货金额："+getIntent().getStringExtra("orderTotalFee")+"元");
        title.setText(getIntent().getStringExtra("address"));

        recyclerView = view.findViewById(R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PickupOrderDetailAdapter(goodsList,PickupOrderDetailActivity.this);
        recyclerView.setAdapter(adapter);

        swipe = view.findViewById(R.id.swipeToLoadLayout);
        swipe.setLoadingMore(true);
        swipe.setRefreshEnabled(false);
        swipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                initData(pageNo);
                pageNo +=1;
            }
        });
    }


    protected void initData(int pn) {
        String fee = getIntent().getStringExtra("feePercent");
        String date = getIntent().getStringExtra("expectDeliveredDate");
        int type = getIntent().getIntExtra("receiveType",0);
        int id = getIntent().getIntExtra("receiveStationId",0);
        RequestPresenter2.getInstance().findReceiveStationFeeDetail(PublicCache.loginSupplier.getStore(), parseDouble(fee), date, type, id, pn, 10, new RequestCallback<PickupOrderDetail>() {
            @Override
            protected void onSuc(PickupOrderDetail body) {
                if (swipe.isLoadingMore())swipe.setLoadingMore(false);
                goods.setText("共"+body.getData().getProductCount()+"件商品");
                Log.i("zhao", "onSuc: "+pn);
                if (body.getData().getPageBean().getRecordList()!=null){
                    for (PickupOrderDetail.DataBean.PageBeanBean.RecordListBean bean : body.getData().getPageBean().getRecordList()){
                        PickUpDetail list = new PickUpDetail();
                        list.setUrl(bean.getPictureUrl());
                        list.setName(bean.getProductName());
                        list.setShortName(bean.getNickName());
                        list.setOrderCount(bean.getItemCount());
                        list.setTotal(bean.getProductItemPrice());
                        list.setPickupFee(bean.getProductItemPrice());
                        list.setReceiveFee(bean.getProductPayMoney());
                        for (PickupOrderDetail.DataBean.PageBeanBean.RecordListBean.OrderItemsBean itemsBean : bean.getOrderItems()){
                            PickUpGoods item = new PickUpGoods();
                            item.setShopName(itemsBean.getHotelName()+"("+itemsBean.getRegionCollNo()+itemsBean.getRegion_no()+"区"+itemsBean.getLineCode()+"-"+itemsBean.getCustomerLineCode()+")");
                            item.setOrderNo(itemsBean.getQrCodeId());
                            switch (itemsBean.getLevelType()){
                                case 1:
                                    item.setPriceUnit("¥"+itemsBean.getLeve1Value()+"元/"+itemsBean.getLevel1Unit());
                                    break;
                                case 2:
                                    item.setPriceUnit("¥"+itemsBean.getLevel2Value()+"元/"+itemsBean.getLeve2Unit()+"("+itemsBean.getLeve1Value()+itemsBean.getLevel1Unit()+")");
                                    break;
                                case 3:
                                    item.setPriceUnit("¥"+itemsBean.getLevel3Value()+"元/"+itemsBean.getLevel3Unit()+"("+itemsBean.getLevel2Value()+itemsBean.getLeve2Unit()+"*"+itemsBean.getLeve1Value()+itemsBean.getLevel1Unit()+")");
                                    break;
                                    default:break;
                            }
                            item.setPickupFee(itemsBean.getRowTotal());
                            item.setGoodsCount(itemsBean.getAmount());
                            list.addSubItem(item);
                        }
                        goodsList.add(list);
                    }
                }else {
                    UIUtils.showToastSafe("暂无数据");
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                if (swipe.isLoadingMore())swipe.setLoadingMore(false);
                UIUtils.showToastSafe(msg);
            }
        });
    }
}
