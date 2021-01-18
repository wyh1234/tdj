package cn.com.taodaji.ui.activity.scanner;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.UIUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.RefreshPickupFee;
import cn.com.taodaji.model.entity.StoreDiyFee;
import cn.com.taodaji.model.presenter.RequestPresenter2;
import cn.com.taodaji.ui.activity.homepage.SpecialActivitiesActivity;
import cn.com.taodaji.ui.activity.homepage.SpecialOfferActivity;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.ui.activity.myself.PickupFeeActivity;
import cn.com.taodaji.ui.activity.myself.PickupServiceActivity;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class ScannerFeeActivity extends DataBaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_right)
    TextView tv_right;
    private Drawable drawable;;
    @BindView(R.id.rl_wallet)
    RelativeLayout rl_wallet;
    @BindView(R.id.tv_star)
    TextView tv_star;
    @BindView(R.id.tv_end)
    TextView tv_end;
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.tv_scannaer_fee)
    TextView tv_scannaer_fee;
    @BindView(R.id.tv_self_scannaer)
    TextView tv_self_scannaer;
    @BindView(R.id.view_self_scannaer)
    View view_self_scannaer;
    @BindView(R.id.view_scannaer_fee)
    View view_scannaer_fee;
    @BindView(R.id.rl_scannaer_fee)
    RelativeLayout rl_scannaer_fee;
    @BindView(R.id.rl_self_scannaer)
    RelativeLayout rl_self_scannaer;
    @BindView(R.id.rl_bottom)
    RelativeLayout rl_bottom;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_fee)
    TextView tv_fee;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_day)
    TextView tv_day;
    private TimePickerView pvTime;
    private boolean flag;
    private String star_date,end_date,fee;
    private int scannerTyp=1;
    @OnClick({R.id.rl_self_scannaer,R.id.rl_scannaer_fee,R.id.tv_star,R.id.tv_end,R.id.btn_back,R.id.rl_wallet,R.id.btn,R.id.rl_bottom,R.id.tv_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.rl_self_scannaer:
                scannerTyp=2;
                tv_self_scannaer.setTextColor(UIUtils.getColor(this,R.color.blue_2898eb));
                tv_scannaer_fee.setTextColor(UIUtils.getColor(this,R.color.black_65));
                view_scannaer_fee.setVisibility(View.GONE);
                view_self_scannaer.setVisibility(View.VISIBLE);
                storeDiyFee();
                break;
            case R.id.rl_scannaer_fee:
                scannerTyp=1;
                tv_self_scannaer.setTextColor(UIUtils.getColor(this,R.color.black_65));
                tv_scannaer_fee.setTextColor(UIUtils.getColor(this,R.color.blue_2898eb));
                view_scannaer_fee.setVisibility(View.VISIBLE);
                view_self_scannaer.setVisibility(View.GONE);
                storeDiyFee();
                break;
            case R.id.tv_star:
                flag=true;
                getDate();
                break;
            case R.id.tv_end:
                flag=false;
                getDate();

                break;
            case R.id.rl_wallet:

                Intent intent_rl_wallet = new Intent(this, PickupFeeActivity.class);
                intent_rl_wallet.putExtra("fee",fee);
                startActivity(intent_rl_wallet);
                break;
            case R.id.btn:
                storeDiyFee();
                break;
             case R.id.rl_bottom:
//                 ClickCheckedUtil.onClickChecked(1000)
                 Intent intent=new Intent(this,ScannerFeeListActivity.class);
                 intent.putExtra("scannerType",scannerTyp+"");
                 intent.putExtra("star_date",star_date);
                 intent.putExtra("end_date",end_date);
                 startActivity(intent);
                break;
            case R.id.tv_right:
                Intent intent_tv_right=new Intent(this, SpecialActivitiesActivity.class);
                intent_tv_right.putExtra("url","http://siji.51taodj.com:8060/test-driver/explain/scanner.do?storeId="+PublicCache.loginSupplier.getStore()+"&siteId="+PublicCache.loginSupplier.getSite());
                intent_tv_right.putExtra("name","常见问题");
                startActivity(intent_tv_right);
                break;
        }
    }

    private void storeDiyFee() {
        LogUtils.e(star_date);
        LogUtils.e(end_date);
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();

        map.put("storeId", PublicCache.loginSupplier.getStore());
        map.put("scannerType", scannerTyp);
        map.put("startTime", star_date);
        map.put("endTime", end_date);
        getRequestPresenter().storeDiyFee(map, new RequestCallback<StoreDiyFee>() {
            @Override
            protected void onSuc(StoreDiyFee body) {
                ShowLoadingDialog.close();
                tv_money.setText("总扫码费用："+body.getData().getCountMonry()+"元");
                tv_count.setText("总扫码数："+body.getData().getRowSize()+"单");
                tv_date.setText(tv_star.getText()+"至"+tv_end.getText());
                if (body.getData().getFreeDays()<=0){
                    tv_day.setVisibility(View.GONE);
                }else {
                    tv_day.setVisibility(View.VISIBLE);
                    tv_day.setText("您的店铺免收扫码费用，还剩"+body.getData().getFreeText());
                }

            }

            @Override
            public void onFailed(int errCode, String msg) {
                super.onFailed(errCode, msg);
                ShowLoadingDialog.close();

            }
        });

    }

    public void getDate(){
        //昨天的11点到今天的凌晨三点
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (flag){
                    star_date=getTimes(date);
                    tv_star.setText(setTime(date));

                }else {
                    end_date=getTimes(date);
                    tv_end.setText(setTime(date));
                }

            }
        }) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月","日", "时", "分", "")
                .isCenterLabel(true)
                .setContentTextSize(16)
                .setLineSpacingMultiplier(1.8f)
                .setDividerColor(Color.DKGRAY)
                .setDate(Calendar.getInstance())
                .setDecorView(null)
                .build();
        pvTime.show();
    }

    public static String getTimes(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    public static String getTime(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);
    }
    public static String setTime(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.scannaer_fee_layout);
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
        tv_title.setText("扫码费用");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("常见问题");
        drawable = getResources().getDrawable(R.mipmap.cjwt);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_right.setCompoundDrawables(drawable, null, null, null);

        end_date=getTime(new Date(System.currentTimeMillis()))+"03:00:00";//接口上传开始时间
        star_date=getTime(new Date(System.currentTimeMillis()-60*24*60*1000))+"23:00:00";//接口上传结束时间
        tv_end.setText(getTime(new Date(System.currentTimeMillis()))+"03:00");
        tv_star.setText(getTime(new Date(System.currentTimeMillis()-60*24*60*1000))+"23:00");
        storeDiyFee();
        refreshPickupFee();
    }

    public void refreshPickupFee(){
        RequestPresenter2.getInstance().refreshPickupFee(PublicCache.loginSupplier.getStore(), new RequestCallback<RefreshPickupFee>() {
            @Override
            protected void onSuc(RefreshPickupFee body) {
                fee=body.getData().getReceive_money().toString();
                tv_fee.setText("剩余"+body.getData().getReceive_money()+"元");
            }

            @Override
            public void onFailed(int errCode, String msg) {
                UIUtils.showToastSafe(msg);
            }
        });
    }
}
