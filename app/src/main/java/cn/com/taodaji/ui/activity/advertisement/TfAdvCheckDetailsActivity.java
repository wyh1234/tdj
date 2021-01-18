package cn.com.taodaji.ui.activity.advertisement;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.TfAdvertisement;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class TfAdvCheckDetailsActivity extends DataBaseActivity {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_reason)
    TextView tv_reason;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_remark)
    TextView tv_remark;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.tv_sq_time)
    TextView tv_sq_time;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.tv_star_time)
    TextView tv_star_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_p_name)
    TextView tv_p_name;
    @BindView(R.id.tv_title_alias)
    TextView tv_title_alias;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_sel_adv)
    TextView tv_sel_adv;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_adv_tj)
    TextView tv_adv_tj;
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.tfad_check_details_layout);
    }
    @OnClick({R.id.btn_back,R.id.rl_title2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.rl_title2://选择商品
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        Map<String,Object> map=new HashMap<>();
        map.put("entityId",getIntent().getStringExtra("entityId"));
        LogUtils.e(map);
        getRequestPresenter().myAdvertisementList(map, new RequestCallback<TfAdvertisement>() {
            @Override
            protected void onSuc(TfAdvertisement body) {

                if (body.getData().getItems().get(0).getStatus()==5){
                    tv_status.setText("已暂停");
                    tv_status.setBackgroundResource(R.drawable.check_tv_sel);
                    tv_status.setTextColor(Color.parseColor("#FD0006"));
                    tv_status.setSelected(true);
                    tv_reason.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);
                    if (body.getData().getItems().get(0).getSuspendTime().length()>0){
                        tv_end_time.setText("暂停时间:"+body.getData().getItems().get(0).getSuspendTime().substring(0,10));
                    }


                }else if (body.getData().getItems().get(0).getStatus()==4){
                    tv_status.setBackgroundResource(R.drawable.check_tv_one_sel);
                    tv_status.setTextColor(Color.parseColor("#0BB35C"));
                    tv_status.setSelected(false);
                    tv_status.setText("已完成");
                    tv_reason.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);
                    if (body.getData().getItems().get(0).getEndTime().length()>0){
                        tv_end_time.setText("结束时间:"+body.getData().getItems().get(0).getEndTime().substring(0,10));
                    }

                }else if (body.getData().getItems().get(0).getStatus()==3){
                    tv_status.setBackgroundResource(R.drawable.check_tv_one_sel);
                    tv_status.setTextColor(Color.parseColor("#67C23A"));
                    tv_status.setSelected(false);
                    tv_status.setText("投放中");
                    tv_reason.setVisibility(View.GONE);
                    rl3.setVisibility(View.VISIBLE);
                    if (body.getData().getItems().get(0).getEndTime().length()>0){
                        tv_end_time.setText("结束时间:"+body.getData().getItems().get(0).getEndTime().substring(0,10));
                    }
                }else if (body.getData().getItems().get(0).getStatus()==2){
                    tv_status.setBackgroundResource(R.drawable.check_tv_one_sel);
                    tv_status.setTextColor(Color.parseColor("#3B9BFF"));
                    tv_status.setSelected(true);
                    tv_status.setText("排期中");
                    tv_reason.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                }else if (body.getData().getItems().get(0).getStatus()==1){
                    tv_status.setBackgroundResource(R.drawable.check_tv_sel);
                    tv_status.setTextColor(Color.parseColor("#FE8833"));
                    tv_status.setSelected(false);
                    tv_status.setText("待审核");
                    tv_reason.setVisibility(View.GONE);
                    rl3.setVisibility(View.GONE);
                }else if (body.getData().getItems().get(0).getStatus()==6){
                    tv_status.setText("审核未通过");
                    tv_status.setBackgroundResource(R.drawable.check_tv_sel);
                    tv_status.setTextColor(Color.parseColor("#EB2831"));
                    tv_status.setSelected(true);
                    tv_reason.setVisibility(View.VISIBLE);
                    rl3.setVisibility(View.GONE);
                    tv_reason.setText(body.getData().getItems().get(0).getRefuseRemark());
                }

                tv_sq_time.setText("申请时间:"+body.getData().getItems().get(0).getCreateTime().substring(0,10));
                tv_id.setText("计划ID:"+body.getData().getItems().get(0).getAdvertisementPlanCode());

                tv_id.setText("计划ID:"+body.getData().getItems().get(0).getAdvertisementPlanCode());
                if (body.getData().getItems().get(0).getStartTime().length()>0){
                    tv_star_time.setText("开始时间:"+body.getData().getItems().get(0).getStartTime().substring(0,10));
                }

                tv_name.setText(body.getData().getItems().get(0).getAdvertisementTypeName());
                tv_time.setText(body.getData().getItems().get(0).getPutTime());
                ImageLoaderUtils.loadImage(iv,body.getData().getItems().get(0).getProductImage());
                tv_p_name.setText(body.getData().getItems().get(0).getProductName());
                tv_title_alias.setText("("+body.getData().getItems().get(0).getProductNickName()+")");
                String string;
                if (body.getData().getItems().get(0).getStageType()==1){
                    string=body.getData().getItems().get(0).getStage()+"期"+body.getData().getItems().get(0).getPackageDays()+"天";
                    tv_adv_tj.setVisibility(View.GONE);
                    tv_money.setText(body.getData().getItems().get(0).getPrice()+"元");
                    if(body.getData().getItems().get(0).getGiftDays()>0){
                        String str=string+"<font color=\"#FD0404\">"+"(加送"+body.getData().getItems().get(0).getGiftDays()+"天)"+"</font>";
                        tv_sel_adv.setText(Html.fromHtml(str));
                    }else {
                        tv_sel_adv.setText(string);
                    }
                }else {
                    LogUtils.e(body.getData().getItems().get(0).getLimitDays());
                    string=body.getData().getItems().get(0).getDays()+"天";
                    tv_adv_tj.setVisibility(View.VISIBLE);
                    tv_adv_tj.setText("*投放天数最多不超过"+body.getData().getItems().get(0).getLimitDays()+"天");
                    tv_money.setText(body.getData().getItems().get(0).getPrice().multiply(new BigDecimal(body.getData().getItems().get(0).getDays()))+"元");
                    tv_sel_adv.setText(string);
                }


                tv_remark.setText(body.getData().getItems().get(0).getUserRemark());


                if ((body.getData().getItems().get(0).getProductMaxPrice()).compareTo(new BigDecimal(-1)) == 0 && body.getData().getItems().get(0).getSpecs().size() == 1) {
                    TfAdvertisement.DataBean.ItemsBean.Specs gsf = body.getData().getItems().get(0).getSpecs().get(0);
                    if (gsf == null) return;
                    if (gsf.getLevelType() == 1) {
                        tv_content.setText(body.getData().getItems().get(0).getProductMinPrice() + "元/" + body.getData().getItems().get(0).getProductUnit());
                    } else {
                        if (gsf.getLevelType() == 3) {
                            tv_content.setText(body.getData().getItems().get(0).getProductMinPrice() + "元/" + body.getData().getItems().get(0).getProductUnit() + "(" + gsf.getLevel2Value() + "" + gsf.getLevel2Unit() + "*" + gsf.getLevel3Value() + gsf.getLevel3Unit() + "" + ")");
                        } else {
                            tv_content.setText(body.getData().getItems().get(0).getProductMinPrice() + "元/" + body.getData().getItems().get(0).getProductUnit() + "(" + gsf.getLevel2Value() + "" + gsf.getLevel2Unit() + ")");
                        }
                    }
                } else {
                    tv_content.setText(body.getData().getItems().get(0).getProductMinPrice() + "元/" + body.getData().getItems().get(0).getProductUnit());
                }
            }

            @Override
            public void onFailed(int errCode, String msg) {
            }
        });
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
        tv_title.setText("计划详情");

}
}
