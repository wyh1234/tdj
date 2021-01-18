package cn.com.taodaji.ui.activity.myself;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.ItemClickListener;
import cn.com.taodaji.model.entity.ProblemItem;
import cn.com.taodaji.model.entity.TodayDeliverScreenEvent;
import cn.com.taodaji.model.entity.TodayDeliveryArea;
import cn.com.taodaji.model.entity.TodayDeliveryProduct;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.viewModel.adapter.TodayOrderAdapter;

public class TodayDeliverScreeningActivity extends AppCompatActivity implements View.OnClickListener {

    private Button confirm,cancel;
    private TodayOrderAdapter productAdapter,areaAdapter;
    private RadioButton earlyBus,normalBus,allBus;
    private RecyclerView productRecycler,areaRecycler;
    private List<ProblemItem> productList = new ArrayList<>();
    private List<ProblemItem> areaList = new ArrayList<>();
    private int rwId,storeId,stationId,productId=-1,areaId=-1;
    private String time ="",product="全部",area="全部区",productNickeName;//1 普通车；2 早班车；
    private TextView tv_all,tv_b,tv_c,tv_ps_time,tv_jr;
    private TodayDeliverScreenEvent todayDeliverScreenEvent;
    private int isc=-1;
    private String ps_time="1";

    public String getPs_time() {
        return ps_time;
    }

    public void setPs_time(String ps_time) {
        this.ps_time = ps_time;
    }

    public int getIsc() {
        return isc;
    }

    public void setIsc(int isc) {
        this.isc = isc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_deliver_screening);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.RIGHT;//设置对话框置顶显示
        lp.alpha = 1.0f;
        win.setAttributes(lp);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        initView();
        initData();
    }
    private void setIscView(int isc){
        setIsc(isc);
        if (isc==-1){
            tv_all.setBackgroundResource(R.drawable.bg_selected_problem);
            tv_b.setBackgroundResource(R.drawable.bg_unselect_problem);
            tv_c.setBackgroundResource(R.drawable.bg_unselect_problem);
            tv_all.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e01));
            tv_b.setTextColor(getResources().getColor(R.color.black));
            tv_c.setTextColor(getResources().getColor(R.color.black));

        }else if (isc==0){
            tv_all.setBackgroundResource(R.drawable.bg_unselect_problem);
            tv_b.setBackgroundResource(R.drawable.bg_selected_problem);
            tv_c.setBackgroundResource(R.drawable.bg_unselect_problem);

            tv_all.setTextColor(getResources().getColor(R.color.black));
            tv_b.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e01));
            tv_c.setTextColor(getResources().getColor(R.color.black));
        }else {
            tv_all.setBackgroundResource(R.drawable.bg_unselect_problem);
            tv_b.setBackgroundResource(R.drawable.bg_unselect_problem);
            tv_c.setBackgroundResource(R.drawable.bg_selected_problem);
            tv_all.setTextColor(getResources().getColor(R.color.black));
            tv_b.setTextColor(getResources().getColor(R.color.black));
            tv_c.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e01));
        }

    }

    public void  initView(){
        confirm = (Button) findViewById(R.id.btn_confirm);
        cancel = (Button) findViewById(R.id.btn_cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

        productRecycler = (RecyclerView) findViewById(R.id.rv_product_name);
        areaRecycler = (RecyclerView) findViewById(R.id.rv_product_area);

        tv_all=(TextView) findViewById(R.id.tv_all);
        tv_b=(TextView) findViewById(R.id.tv_b);
        tv_c=(TextView) findViewById(R.id.tv_c);
        tv_ps_time=(TextView) findViewById(R.id.tv_ps_time);
        tv_ps_time.setOnClickListener(this);
        tv_jr=(TextView) findViewById(R.id.tv_jr);
        tv_jr.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_c.setOnClickListener(this);
        tv_b.setOnClickListener(this);

        allBus = (RadioButton) findViewById(R.id.rb_all_bus);
        earlyBus = (RadioButton) findViewById(R.id.rb_early_bus);
        normalBus = (RadioButton) findViewById(R.id.rb_normal_bus);
        todayDeliverScreenEvent= (TodayDeliverScreenEvent) getIntent().getSerializableExtra("todayDeliverScreenEvent");
        if (ListUtils.isNullOrZeroLenght(todayDeliverScreenEvent.getTime())){
            time="";
            allBus.setChecked(true);
        }else if (todayDeliverScreenEvent.getTime().equals("1")){
            time="1";
            normalBus.setChecked(true);
        }else {
            time="2";
            earlyBus.setChecked(true);
        }
        setIscView(todayDeliverScreenEvent.getIsc());
        setDate(todayDeliverScreenEvent.getPs_time());

        if (todayDeliverScreenEvent!=null){
            if (!ListUtils.isNullOrZeroLenght(todayDeliverScreenEvent.getArea())){
               area= todayDeliverScreenEvent.getArea();
            }
            if (todayDeliverScreenEvent.getAreaId()!=0){
                areaId=todayDeliverScreenEvent.getAreaId();
            }
            if (todayDeliverScreenEvent.getProductId()!=0){
                productId=todayDeliverScreenEvent.getProductId();
            }
            if (!ListUtils.isNullOrZeroLenght(todayDeliverScreenEvent.getProduct())){
                product= todayDeliverScreenEvent.getProduct();
            }
            if (!ListUtils.isNullOrZeroLenght(todayDeliverScreenEvent.getProductNickName())){
                productNickeName= todayDeliverScreenEvent.getProductNickName();
            }
        }



        productRecycler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        productRecycler.setNestedScrollingEnabled(false);
        productAdapter = new TodayOrderAdapter(productList,this);
        productAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                productAdapter.setSelected(position);
                productId = productList.get(position).getId();
                productNickeName = productList.get(position).getNickname();
                product = productList.get(position).getText()+"("+productList.get(position).getNickname()+")";
            }
        });
        productRecycler.setAdapter(productAdapter);

        areaRecycler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        areaRecycler.setNestedScrollingEnabled(false);
        areaAdapter = new TodayOrderAdapter(areaList,this);
        areaAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                areaAdapter.setSelected(position);
                areaId = areaList.get(position).getId();
                area = areaList.get(position).getText();
            }
        });
        areaRecycler.setAdapter(areaAdapter);

        allBus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    time="";
                    initData();
                }
            }
        });

        normalBus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    time = "1";
                    initData();
                }
            }
        });

        earlyBus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    time = "2";
                    initData();
                }
            }
        });

        rwId = getIntent().getIntExtra("rwId",0);
        stationId = getIntent().getIntExtra("stationId",0);
        storeId = getIntent().getIntExtra("storeId",0);
    }

    public void initData(){
        RequestPresenter.getInstance().getProductNameList(storeId, stationId, rwId, time, new RequestCallback<TodayDeliveryProduct>() {
            @Override
            protected void onSuc(TodayDeliveryProduct body) {
                if (body.getData().getOrderProductToBeStoreds().size()!=0){
                    productList.clear();
                    for(TodayDeliveryProduct.DataBean.OrderProductToBeStoredsBean bean:body.getData().getOrderProductToBeStoreds()){
                        ProblemItem item = new ProblemItem();
                        item.setId(bean.getProductId());
                        item.setText(bean.getProductName());
                        item.setNickname(bean.getNickName());
                        item.setNum(bean.getNum());
                        productList.add(item);
                    }
                    LogUtils.e(time);
                    LogUtils.e(todayDeliverScreenEvent.getTime());
                        if (todayDeliverScreenEvent.getTime()!=null){

                            if (todayDeliverScreenEvent.getTime().equals(time)) {
                                productAdapter.setSelected(todayDeliverScreenEvent.getProductPosition());
                            }else {
                                productAdapter.setSelected(0);
                            }

                         }else {
                            productAdapter.setSelected(0);
                    }

                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
        RequestPresenter.getInstance().getProductAreaList(storeId, stationId, rwId, time, new RequestCallback<TodayDeliveryArea>() {
            @Override
            protected void onSuc(TodayDeliveryArea body) {
                if (body.getData().getOrderRegionToBeStoreds().size()!=0){
                    areaList.clear();
                    for (TodayDeliveryArea.DataBean.OrderRegionToBeStoredsBean bean : body.getData().getOrderRegionToBeStoreds()){
                        ProblemItem item = new ProblemItem();
                        item.setId(bean.getRegionCollectionId());
                        item.setText(bean.getRegionCollNo()+"区");
                        areaList.add(item);
                    }
                    LogUtils.e(todayDeliverScreenEvent.getTime());
                    LogUtils.e(time);
                    if (todayDeliverScreenEvent.getTime()!=null){
                        if (todayDeliverScreenEvent.getTime().equals(time)) {
                            areaAdapter.setSelected(todayDeliverScreenEvent.getAreaPosition());
                        }else {
                            areaAdapter.setSelected(0);
                        }
                    }else {
                        areaAdapter.setSelected(0);
                    }

                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirm:
                TodayDeliverScreenEvent event = new TodayDeliverScreenEvent();
                event.setArea(area);
                event.setAreaId(areaId);
                event.setProduct(product);
                event.setProductId(productId);
                event.setTime(time);
                event.setProductNickName(productNickeName);
                event.setAreaPosition(areaAdapter.getSelected());
                event.setProductPosition(productAdapter.getSelected());
                event.setPs_time(getPs_time());
                event.setIsc(getIsc());
                EventBus.getDefault().post(event);
                LogUtils.e(event);
                finish();
                break;
            case R.id.btn_cancel:
                finish();
                break;

            case R.id.tv_all:

                setIscView(-1);
                break;
            case R.id.tv_b:

                setIscView(0);
                break;
            case R.id.tv_c:

                setIscView(1);
                break;
            case R.id.tv_ps_time:
                setDate("2");
                break;
            case R.id.tv_jr:
                setDate("1");
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }

    public void setDate(String ps_time){
        setPs_time(ps_time);
        if (ps_time.equals("1")){
            tv_jr.setBackgroundResource(R.drawable.bg_selected_problem);
            tv_ps_time.setBackgroundResource(R.drawable.bg_unselect_problem);
            tv_jr.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e01));
            tv_ps_time.setTextColor(getResources().getColor(R.color.black));

        }else if (ps_time.equals("2")){
            tv_ps_time.setBackgroundResource(R.drawable.bg_selected_problem);
            tv_jr.setBackgroundResource(R.drawable.bg_unselect_problem);
            tv_ps_time.setTextColor(getResources().getColor(R.color.orange_yellow_ff7e01));
            tv_jr.setTextColor(getResources().getColor(R.color.black));
        }
    }

}
