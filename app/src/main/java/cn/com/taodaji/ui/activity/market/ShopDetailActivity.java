package cn.com.taodaji.ui.activity.market;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.glide.nineImageView.ImagesActivity;
import com.base.swipetoloadlayout.listener.OnGetDataListener;
import com.base.utils.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ShopDetail;
import tools.activity.SimpleToolbarActivity;

public class ShopDetailActivity extends SimpleToolbarActivity implements OnGetDataListener,View.OnClickListener {
    private GlideImageView shop_logo;
    private ImageView iv_add_attention;
    private TextView tv_enshrine_count,shop_evaluate_value,shop_name;
    private TextView text_2,text_3,text_4,btn;
    private LinearLayout ll3,ll4;
    private  static   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getData(int pn) {

    }

    @Override
    protected void titleSetting(Toolbar toolbar) {

        ShopDetail body= (ShopDetail) getIntent().getSerializableExtra("body");

       View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.shop_detail_layout);
        body_other.addView(mainView);
        tv_enshrine_count= ViewUtils.findViewById(mainView, R.id.tv_enshrine_count);
        tv_enshrine_count.setText(body.getFavoriteCount()+"");
        shop_evaluate_value= ViewUtils.findViewById(mainView, R.id.shop_evaluate_value);
        shop_evaluate_value.setText(body.getStoreScore()+"");
        iv_add_attention= ViewUtils.findViewById(mainView, R.id.iv_add_attention);
       iv_add_attention.setSelected(body.isFavor());
        shop_logo= ViewUtils.findViewById(mainView, R.id.shop_logo);
        shop_logo.loadImage(body.getLogoImageUrl());
        shop_name= ViewUtils.findViewById(mainView, R.id.shop_name);
        text_2= ViewUtils.findViewById(mainView, R.id.text_2);
        text_3= ViewUtils.findViewById(mainView, R.id.text_3);
        text_4= ViewUtils.findViewById(mainView, R.id.text_4);
        ll3= ViewUtils.findViewById(mainView, R.id.ll3);//经营许可证
        ll4= ViewUtils.findViewById(mainView, R.id.ll4);
        btn= ViewUtils.findViewById(mainView, R.id.btn);
        shop_name.setText(body.getName());
        if (body.getIdcardNumber() == null || "".equals(body.getIdcardNumber()) || body.getIdcardImageUrl() == null || "".equals(body.getIdcardImageUrl() )){
            text_2.setText("未实名");
        }else {
            text_2.setText("已实名");
            text_2.setTextColor(getResources().getColor(R.color.alipay));
        }
        text_4.setText(body.getMainCommodity());
        text_3.setText( format.format(new Date(body.getCreateTime())));
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagesActivity.startActivity(view, body.getFoodQualiynoImageUrl());
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (body.getFoodQualiynoImageUrl() == null || "".equals(body.getFoodQualiynoImageUrl().toString())){
                    return;
                }
                ImagesActivity.startActivity(view, body.getFoodQualiynoImageUrl());
            }
        });
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (body.getLicenceDocurl() == null || "".equals(body.getLicenceDocurl().toString())){
                    return;
                }
                ImagesActivity.startActivity(view, body.getLicenceDocurl());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }

    @Override
    protected void initMainView() {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.orange_yellow_ff7d01);
        simple_title.setText("店铺详情");
    }
}
