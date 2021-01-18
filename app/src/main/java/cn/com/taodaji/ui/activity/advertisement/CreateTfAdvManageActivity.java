package cn.com.taodaji.ui.activity.advertisement;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.AdvOrderDetailParam;
import cn.com.taodaji.model.entity.AvdOrder;
import cn.com.taodaji.model.entity.CreateTfAdvManage;
import cn.com.taodaji.model.entity.MarketingManage;
import cn.com.taodaji.model.entity.PayManage;
import cn.com.taodaji.model.entity.SelGoods;
import cn.com.taodaji.ui.activity.advertisement.adapter.CreateTfAdvManageAdapter;
import cn.com.taodaji.ui.activity.advertisement.popuwindow.AdvSelPopuWindow;
import cn.com.taodaji.ui.activity.advertisement.popuwindow.DelAdvPopuWindow;
import cn.com.taodaji.ui.activity.advertisement.popuwindow.RemarkPopuWindow;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.penalty.adapter.PunishListAdapter;
import me.leolin.shortcutbadger.ShortcutBadger;
import tools.activity.DataBaseActivity;
import tools.animation.ScrollLinearLayoutManager;
import tools.extend.MyRecyclerViews;
import tools.statusbar.Eyes;

public class CreateTfAdvManageActivity extends DataBaseActivity implements
        CreateTfAdvManageAdapter.OnItemClickListener, RemarkPopuWindow.RemarkPopuWindowListener,AdvSelPopuWindow.AdvSelPopuWindowListener
      ,DelAdvPopuWindow.DelAdvPopuWindowListener{
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.recyclerview)
    MyRecyclerViews recyclerView;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.post_ok)
    Button post_ok;
    @BindView(R.id.tv_wz)
    TextView tv_wz;
    @BindView(R.id.iv_sel)
    ImageView iv_sel;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    private CreateTfAdvManageAdapter createTfAdvManageAdapter;
    private ArrayList<CreateTfAdvManage> data = new ArrayList<>();
    private TimePickerView pvTime;
    private RemarkPopuWindow remarkPopuWindow;
    private DelAdvPopuWindow delAdvPopuWindow;
    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @OnClick({R.id.btn_back,R.id.tv_right,R.id.rl_title,R.id.post_ok,R.id.iv_sel,R.id.tv_agreement})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.rl_title:
                break;
            case R.id.post_ok:
                if (!iv_sel.isSelected()){
                    UIUtils.showToastSafe("请勾选并接受淘大集广告服务协议");
                    return;
                }
                List<Map<String,Object>> advOrderDetailParams = new ArrayList<>();
                for (int i=0;i<data.size();i++){
                    StringBuffer stringBuffer=new StringBuffer();
                    Map<String,Object> map=new HashMap<>();
                    map.put("putTime",data.get(i).getTime());
                    map.put("userRemark",UIUtils.isNullOrZeroLenght(data.get(i).getRemark())?"":data.get(i).getRemark());
                    if (ListUtils.isEmpty(data.get(i).getGoods())){
                        UIUtils.showToastSafe("请选择广告计划"+(i+1)+"商品");
                        return;
                    }
                    if (getIntent().getStringExtra("stage_type").equals("2")){
                        if (data.get(i).getDay()==null){
                            UIUtils.showToastSafe("请输入广告计划"+(i+1)+"投放天数");
                            return;
                        }
                        if (Integer.parseInt(data.get(i).getDay())>data.get(0).getLimit_days()){
                            UIUtils.showToastSafe("投放天数最多不超过"+data.get(0).getLimit_days()+"天");
                            return;
                        }
                        if (Integer.parseInt(data.get(i).getDay())==0){
                            UIUtils.showToastSafe("投放天数不能为0天");
                            return;
                        }
                        map.put("days",data.get(i).getDay());
                        map.put("advertisementPackageId",getIntent().getIntExtra("advertisementPackageId",0));
                    }else {
                        if (data.get(i).getAdvPackageId()==0){
                            UIUtils.showToastSafe("请选择广告计划"+(i+1)+"套餐");
                            return;
                        }
                        map.put("advertisementPackageId",data.get(i).getAdvPackageId());
                    }
                    for (int j=0;j<data.get(i).getGoods().size();j++){
                        stringBuffer.append(data.get(i).getGoods().get(j).getEntityId());
                        stringBuffer.append(",");
                    }
                    map.put("productIds",stringBuffer.substring(0,stringBuffer.toString().length()-1));
                    advOrderDetailParams.add(map);
                }

                getData(new Gson().toJson(advOrderDetailParams));
                break;
            case R.id.iv_sel:
                if (iv_sel.isSelected()){
                    iv_sel.setSelected(false);
                }else {
                    iv_sel.setSelected(true);
                }
                break;
            case R.id.tv_agreement:
                Intent intent=new Intent(this, WebViewActivity.class);
                intent.putExtra("url","https://www.taodaji.com.cn/h5/fine/service.html");
                intent.putExtra("title","广告服务协议");
                startActivity(intent);
                break;
        }
    }

    public void getData(String orderDetail){
        ShowLoadingDialog.showLoadingDialog(this);

        Map<String,Object> map=new HashMap<>();
        map.put("storeId",PublicCache.loginSupplier.getStore());
        map.put("advertisementTypeId",getIntent().getIntExtra("entity_id",0));
        map.put("orderDetail",orderDetail);
        LogUtils.e(map);
        getRequestPresenter().addMyAdvertisement(map, new RequestCallback<AvdOrder>() {
            @Override
            protected void onSuc(AvdOrder body) {
                ShowLoadingDialog.close();
                Intent intent=new Intent(CreateTfAdvManageActivity.this,AdvOrderCommitActivity.class);
                intent.putExtra("list",data);
                intent.putExtra("avdOrder",body);

                startActivity(intent);

            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.create_tfadv_manage_layout);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.blue_2898eb));
        tv_title.setTextColor(getResources().getColor(R.color.white));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_white);
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.blue_2898eb
        ));
        tv_title.setText("创建投放计划");
        tv_right.setVisibility(View.GONE);
        tv_right.setText("历史投放");
        Drawable drawable = getResources().getDrawable(R.mipmap.toufang);// 找到资源图片
        // 这一步必须要做，否则不会显示。
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置图片宽高
        tv_right.setCompoundDrawables(drawable, null, null, null);// 设置到控件中

        tv_right.setCompoundDrawablePadding(10);
        CreateData();
        ScrollLinearLayoutManager layoutManager = new ScrollLinearLayoutManager(this, 1);
        layoutManager.setScrollEnable(false);
        recyclerView.setLayoutManager(layoutManager);
        createTfAdvManageAdapter = new CreateTfAdvManageAdapter(this, data);
        createTfAdvManageAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(createTfAdvManageAdapter);
        tv_wz.setText(getIntent().getStringExtra("name"));
    }

    public void CreateData(){
        CreateTfAdvManage createTfAdvManage = new CreateTfAdvManage();
        createTfAdvManage.setB(getIntent().getStringExtra("stage_type").equals("2")?true:false);

        if (getIntent().getStringExtra("stage_type").equals("2")){
            createTfAdvManage.setAdvPackagePice(new BigDecimal(getIntent().getStringExtra("advPackagePice")));
            createTfAdvManage.setLimit_days(getIntent().getIntExtra("limit_days",0));
        }
        createTfAdvManage.setTime(getCurrDay());
        data.add(createTfAdvManage);
    }

    @Override
    public void onItemClick(View view, int position) {
        setPos(position);
        switch (view.getId()) {
            case R.id.rl_time:
                pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        // data.get(position).

                        data.get(getPos()).setTime(getTimes(date));
                        createTfAdvManageAdapter.notifyDataSetChanged();

                    }
                }) //年月日时分秒 的显示与否，不设置则默认全部显示
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("年", "月", "日", "", "", "")
                        .isCenterLabel(true)
                        .setContentTextSize(16)
                        .setLineSpacingMultiplier(1.8f)
                        .setDividerColor(Color.DKGRAY)
                        .setDate(Calendar.getInstance())
                        .setRangDate(Calendar.getInstance(), null)
                        .setDecorView(null)
                        .build();
                pvTime.show();
                break;
            case R.id.rl_title2:
                Intent intent = new Intent(this, SelGoodsActivity.class);
                intent.putExtra("selGoods","selGoods");
                startActivity(intent);
                break;
            case R.id.rl_title1:
                Intent intent_adv_package_Sel = new Intent(this, AdvPackageSelActivity.class);
                intent_adv_package_Sel.putExtra("DataBean",(MarketingManage.DataBean)getIntent().getSerializableExtra("DataBean"));
                startActivity(intent_adv_package_Sel);
                break;
            case R.id.rl_title3:
            if (remarkPopuWindow!=null){
                if (remarkPopuWindow.isShowing()){
                    return;
                }
                remarkPopuWindow.showPopupWindow();
            }else {

                remarkPopuWindow = new RemarkPopuWindow(this);
                remarkPopuWindow.setDismissWhenTouchOutside(false);
                remarkPopuWindow.setInterceptTouchEvent(false);
                remarkPopuWindow.setPopupWindowFullScreen(true);//铺满
                remarkPopuWindow.setRemarkPopuWindowListener(this);
                remarkPopuWindow.showPopupWindow();
            }

            break;
            case R.id.tv_right:
                if (position==0){
                    CreateData();
                    createTfAdvManageAdapter.notifyDataSetChanged();
                }else {

                    if (delAdvPopuWindow!=null){
                        if (delAdvPopuWindow.isShowing()){
                            return;
                        }
                        delAdvPopuWindow.showPopupWindow();
                    }else {

                        delAdvPopuWindow = new DelAdvPopuWindow(this,"广告计划"+(position+1));
                        delAdvPopuWindow.setDismissWhenTouchOutside(false);
                        delAdvPopuWindow.setInterceptTouchEvent(false);
                        delAdvPopuWindow.setPopupWindowFullScreen(true);//铺满
                        delAdvPopuWindow.setDelAdvPopuWindowListener(this);
                        delAdvPopuWindow.showPopupWindow();
                    }



                }
                LogUtils.e(data.size());


                break;

        }


    }

    public static String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
    public static String getCurrDay() {//可根据需要自行截取数据显示
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onCancel() {
        if (delAdvPopuWindow!=null){
            delAdvPopuWindow.dismiss();
        }
        if (remarkPopuWindow!=null){
            remarkPopuWindow.dismiss();
        }
    }

    @Override
    public void onOk(String remark) {
        data.get(getPos()).setRemark(remark);
        createTfAdvManageAdapter.notifyDataSetChanged();
        remarkPopuWindow.dismiss();

    }

    @Override
    public void onOk() {
        data.remove(getPos());
        createTfAdvManageAdapter.notifyDataSetChanged();
        delAdvPopuWindow.dismiss();
    }




    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onDestroy();
    }



    @Subscribe
    public void onEvent(Object event) {
        if (event instanceof MarketingManage.DataBean.PackageListBean){
            if (((MarketingManage.DataBean.PackageListBean) event).getGift_days()>0){
              String days="(加送"+((MarketingManage.DataBean.PackageListBean) event).getGift_days()+"天)";
                data.get(getPos()).setJsDay(days);
            }else {
                data.get(getPos()).setJsDay("");
            }
            data.get(getPos()).setAdvPackagePice(((MarketingManage.DataBean.PackageListBean) event).getPrice());
            data.get(getPos()).setAdvPackageName(((MarketingManage.DataBean.PackageListBean) event).getStage()+"期"+((MarketingManage.DataBean.PackageListBean) event).getDays()+"天");
            data.get(getPos()).setAdvPackageId(((MarketingManage.DataBean.PackageListBean) event).getEntity_id());
        }else if (event instanceof SelGoods.DataBean ){
            data.get(getPos()).setGoods(((SelGoods.DataBean) event).getItems());
        }
        createTfAdvManageAdapter.notifyDataSetChanged();

    }
}
