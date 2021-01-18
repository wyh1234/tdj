package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.TodayDeliverScreenEvent;
import cn.com.taodaji.model.event.TodayOrderTimeEvent;
import cn.com.taodaji.ui.fragment.TodayDeliverGoodsOrderFragment;
import cn.com.taodaji.viewModel.adapter.ManageActivityPagerAdapter;
import tools.activity.SimpleToolbarActivity;
import tools.fragment.DataBaseFragment;
import tools.gprint.PrintUtils;

public class TodayDeliverGoodsOrderManageActivity extends SimpleToolbarActivity {
    private TabLayout tabLayout;
    private ManageActivityPagerAdapter mAdapter;
    private PrintUtils printUtils;
    private TextView timeTv,houseName,screen, truckText, productText, areaText, tips;
    private  String truck="车次：全部车",product="商品：全部",area="区域：全部区";
    private RelativeLayout loading;
    private TodayDeliverScreenEvent event;

    public TodayDeliverScreenEvent getEvent() {
        return event;
    }

    public void setEvent(TodayDeliverScreenEvent event) {
        this.event = event;
    }

    @Override
    public void loading(String... message) {
        super.loading(message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loading.setVisibility(View.GONE);
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
        simple_title.setText("今日待入库订单");
        right_textView.setText("设置打印机");
        right_textView.setTextSize(12);
        right_icon.setImageResource(R.mipmap.ic_print_white);
        right_textView.setTextColor(UIUtils.getColor(R.color.white));
    }

    @Override
    protected void initMainView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        View mainView = getLayoutView(R.layout.activity_today_deliver_goods_order_manage);
        body_other.addView(mainView);

        tabLayout = ViewUtils.findViewById(mainView, R.id.tabLayout);
        ViewPager mViewPager = ViewUtils.findViewById(mainView, R.id.tabLayout_viewpager);

        houseName = ViewUtils.findViewById(mainView,R.id.tv_delivery_warehouse_name);
        houseName.setText(getIntent().getStringExtra("stationName"));
        timeTv = ViewUtils.findViewById(mainView, R.id.today_deliver_goods_order_time_text);
        tips = ViewUtils.findViewById(mainView,R.id.tips);
        Spanned spanned = Html.fromHtml("打印小票中，"+"<font color='red'>"+"不可对手机做一切操作，以免影响打印状态更改"+"</font>"+"如果缺纸，请补充打印纸张，打印结束后，此消息框自动消失。");
        tips.setText(spanned);

        truckText = ViewUtils.findViewById(mainView,R.id.tv_truck);
        productText = ViewUtils.findViewById(mainView,R.id.tv_product);
        areaText = ViewUtils.findViewById(mainView,R.id.tv_area);
        loading = ViewUtils.findViewById(mainView,R.id.loading);
        loading.setOnClickListener(null);
        truckText.setText(truck);
        productText.setText(product);
        areaText.setText(area);

        screen = ViewUtils.findViewById(mainView,R.id.tv_screening);
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TodayDeliverGoodsOrderManageActivity.this,TodayDeliverScreeningActivity.class);
                intent.putExtra("rwId",getIntent().getIntExtra("rwId",-1));
                intent.putExtra("stationId",getIntent().getIntExtra("stationId",-1));
                intent.putExtra("storeId", PublicCache.loginSupplier.getStore());
                intent.putExtra("todayDeliverScreenEvent", event!=null?event:new TodayDeliverScreenEvent());
                startActivity(intent);
            }
        });

        List<DataBaseFragment> fragments = initFragments();
        mAdapter = new ManageActivityPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mAdapter.setFragments(fragments);

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(UIUtils.getColorStateList(R.color.s_blue_2898eb));
        tabLayout.setSelectedTabIndicatorColor(UIUtils.getColor(R.color.blue_2898eb));
        tabLayout.setSelectedTabIndicatorHeight(5);

    }
    @Override
    protected void initData() {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (printUtils != null) {
            printUtils.recycler();
        }
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    private List<DataBaseFragment> initFragments() {
        printUtils = new PrintUtils(this, right_textView);
        int storeId= getIntentData();
        List<DataBaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < WAIT_ORDER_MANAGEMENT_LIST.size(); i++) {
            TodayDeliverGoodsOrderFragment todayOrder = new TodayDeliverGoodsOrderFragment();
            todayOrder.setTitle(WAIT_ORDER_MANAGEMENT_LIST.get(i));
            todayOrder.setPrintStatus(i-1);
            todayOrder.setRwId(getIntent().getIntExtra("rwId",0));
            todayOrder.setStationId(getIntent().getIntExtra("stationId",0));
            todayOrder.setStoreId(PublicCache.loginSupplier.getStore());
            todayOrder.setmContext(TodayDeliverGoodsOrderManageActivity.this);
            todayOrder.setPrintUtils(printUtils);
            todayOrder.setLoading(loading);
            fragments.add(todayOrder);
        }
        return fragments;
    }

    @Subscribe
    public void onEvent(TodayOrderTimeEvent event) {
        if (event.getSendTime().equals("")){
            timeTv.setText("今日暂无订单");
        }else {
            timeTv.setText(event.getSendTime()+getIntent().getStringExtra("carTime"));
        }
    }

    @Subscribe
    public void onEvent(TodayDeliverScreenEvent event) {
        switch (event.getTime()){
            case "1":
                truck ="车次：普通车";
                break;
            case "2":
                truck="车次：早班车";
                break;
            default: truck="车次：全部车";
            break;
        }
        product="商品："+event.getProduct();
        area="区域："+event.getArea();
        truckText.setText(truck);
        productText.setText(product);
        areaText.setText(area);
        setEvent(event);
    }

    private int getIntentData() {
        int storeId;
        Intent intent = getIntent();
        int number1 = intent.getIntExtra("orderId", 0);
        int number2 = intent.getIntExtra("orderId1", 0);
        if (number1 != 0) {
            storeId = number1;
        } else if (number2 != 0) {
            storeId = number2;
        } else {
            storeId = 0;
        }
        return storeId;
    }

}
