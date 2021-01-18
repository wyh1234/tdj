package cn.com.taodaji.ui.activity.wallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import tools.activity.SimpleToolbarActivity;

import com.base.widget.DatePickDialog;
import com.base.utils.DateUtils;
import com.base.utils.ViewUtils;

/**
 * Created by Administrator on 2017-12-05.
 */

public class DetailFiltrateActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private TextView startTiemTv;
    private TextView endTimeTv;
    private String start_time;
    private String end_time;
    private Button BtnOk;
    private List<TextView> syptTvList = new ArrayList<>();
    private List<TextView> timeTvList = new ArrayList<>();
    private TextView tv_all, tv_yjz, tv_shtk, tv_tx, tv_djk, tv_time_today, tv_time_one_week, tv_time_one_month, tv_time_three_month, tv_time_six_month;
    private int type;
    private String orderFreezeStatus = "";

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("明细筛选");
    }

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutView(this, R.layout.activity_detail_filtrate);
        body_other.addView(mainView); //toolbar 添加view
        body_bottom.setVisibility(View.GONE);
        body_center.setVisibility(View.GONE);
        initBtnView(mainView);
        initLinstener();
        initTimeDate();

    }

    private void initTimeDate() {
        String nowDate = DateUtils.parseTime("yyyy-MM-dd");
        if (nowDate != null) {
            startTiemTv.setText(nowDate);
            endTimeTv.setText(nowDate);
            start_time = nowDate;
            end_time = nowDate;
        }
    }

    private void initLinstener() {
        startTiemTv.setOnClickListener(this);
        endTimeTv.setOnClickListener(this);
        BtnOk.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        tv_yjz.setOnClickListener(this);
        tv_shtk.setOnClickListener(this);
        tv_tx.setOnClickListener(this);
        tv_djk.setOnClickListener(this);
        tv_time_today.setOnClickListener(this);
        tv_time_one_week.setOnClickListener(this);
        tv_time_one_month.setOnClickListener(this);
        tv_time_three_month.setOnClickListener(this);
        tv_time_six_month.setOnClickListener(this);
    }

    private void initBtnView(View view) {
        startTiemTv = ViewUtils.findViewById(view, R.id.fltrate_start_time_text);
        endTimeTv = ViewUtils.findViewById(view, R.id.filtrate_end_time_text);
        BtnOk = ViewUtils.findViewById(view, R.id.filtrate_button_ok);
        tv_all = ViewUtils.findViewById(view, R.id.filtrate_type_text_all);
        tv_yjz = ViewUtils.findViewById(view, R.id.filtrate_type_text_yizhifu);
        tv_shtk = ViewUtils.findViewById(view, R.id.filtrate_type_text_shtk);
        tv_tx = ViewUtils.findViewById(view, R.id.filtrate_type_text_tx);
        tv_djk = ViewUtils.findViewById(view, R.id.filtrate_type_text_djk);
        tv_time_today = ViewUtils.findViewById(view, R.id.filtrate_type_tiem_today);
        tv_time_one_week = ViewUtils.findViewById(view, R.id.filtrate_type_tiem_one_week);
        tv_time_one_month = ViewUtils.findViewById(view, R.id.filtrate_type_tiem_one_month);
        tv_time_three_month = ViewUtils.findViewById(view, R.id.filtrate_type_tiem_three_month);
        tv_time_six_month = ViewUtils.findViewById(view, R.id.filtrate_type_tiem_six_month);
        syptTvList.add(tv_all);
        syptTvList.add(tv_yjz);
        syptTvList.add(tv_shtk);
        syptTvList.add(tv_tx);
        syptTvList.add(tv_djk);
        timeTvList.add(tv_time_today);
        timeTvList.add(tv_time_one_week);
        timeTvList.add(tv_time_one_month);
        timeTvList.add(tv_time_three_month);
        timeTvList.add(tv_time_six_month);

        setTextViewSelected(syptTvList, tv_all);
        setTextViewSelected(timeTvList, tv_time_today);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.filtrate_button_ok:
//                Intent intent = new Intent();
//                intent.setClass(this,SupplyMoneyFilterListActivity.class);
//                startActivity(intent);
                SupplyMoneyFilterListActivity.startActivity(this, type, start_time, end_time/*, orderFreezeStatus*/);
                break;
            case R.id.fltrate_start_time_text:
                new DatePickDialog(this, start_time, startTiemTv) {
                    @Override
                    public void setOnclick(DialogInterface dialog, int whichButton, String dateStr) {
                        start_time = dateStr;
                        if (end_time.compareTo(start_time) < 0) {
                            BtnOk.setEnabled(false);
                        } else BtnOk.setEnabled(true);
                    }
                };
                break;
            case R.id.filtrate_end_time_text:
                new DatePickDialog(this, end_time, endTimeTv) {
                    @Override
                    public void setOnclick(DialogInterface dialog, int whichButton, String dateStr) {
                        end_time = dateStr;
                        if (end_time.compareTo(start_time) < 0) {
                            BtnOk.setEnabled(false);
                        } else BtnOk.setEnabled(true);
                    }
                };
                break;
            case R.id.filtrate_type_text_all:
                orderFreezeStatus = "";
                type = 0;
                setTextViewSelected(syptTvList, tv_all);
                break;
            case R.id.filtrate_type_text_yizhifu:
                type = 1;//1、订单 2、提现 3、售后
                orderFreezeStatus = "";
                setTextViewSelected(syptTvList, tv_yjz);
                break;
            case R.id.filtrate_type_text_shtk:
                type = 3;
                orderFreezeStatus = "";
                setTextViewSelected(syptTvList, tv_shtk);
                break;
            case R.id.filtrate_type_text_tx:
                type = 2;
                orderFreezeStatus = "";
                setTextViewSelected(syptTvList, tv_tx);
                break;
            case R.id.filtrate_type_text_djk:
                orderFreezeStatus = "FREEZE";
                type = 1;
                setTextViewSelected(syptTvList, tv_djk);
                break;
            case R.id.filtrate_type_tiem_today:
                setTextViewSelected(timeTvList, tv_time_today);
                setTime(DateUtils.parseTime("yyyy-MM-dd"));
                break;
            case R.id.filtrate_type_tiem_one_week:
                setTextViewSelected(timeTvList, tv_time_one_week);
                setTime(DateUtils.getDay("yyyy-MM-dd", -7));
                break;
            case R.id.filtrate_type_tiem_one_month:
                setTextViewSelected(timeTvList, tv_time_one_month);
                setTime(DateUtils.getMonth("yyyy-MM-dd", -1));
                break;
            case R.id.filtrate_type_tiem_three_month:
                setTextViewSelected(timeTvList, tv_time_three_month);
                setTime(DateUtils.getMonth("yyyy-MM-dd", -3));
                break;
            case R.id.filtrate_type_tiem_six_month:
                setTextViewSelected(timeTvList, tv_time_six_month);
                setTime(DateUtils.getMonth("yyyy-MM-dd", -6));
                break;
        }
    }

    private void setTextViewSelected(List<TextView> list, TextView tv) {
        if (!tv.isSelected()) {
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelected(false);
                }
                tv.setSelected(true);
            }
        }
    }

    private void setTime(String start_time) {
        this.start_time = start_time;
        startTiemTv.setText(start_time);
        endTimeTv.setText(DateUtils.parseTime("yyyy-MM-dd"));
    }
}
