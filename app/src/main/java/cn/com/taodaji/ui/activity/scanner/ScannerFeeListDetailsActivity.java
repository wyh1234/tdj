package cn.com.taodaji.ui.activity.scanner;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ScannerFeeListDetail;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class ScannerFeeListDetailsActivity extends DataBaseActivity {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_order_no)
    TextView tv_order_no;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_num)
    TextView tv_num;

    @BindView(R.id.goods_count)
    TextView goods_count;
    @BindView(R.id.goods_unit2)
    TextView goods_unit2;
    @BindView(R.id.cart_price)
    TextView cart_price;

    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_ck)
    TextView tv_ck;
    @BindView(R.id.tv_sm_name)
    TextView tv_sm_name;
    @BindView(R.id.tv_sm_phone)
    TextView tv_sm_phone;

    @OnClick({R.id.btn_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.scannaer_fee_list_details_layout);
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
        tv_title.setText("扫码详情");
        getData();
    }

    public void getData(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("entityId",getIntent().getIntExtra("entityId",0));
        getRequestPresenter().scannerFeeDrtail(map, new RequestCallback<ScannerFeeListDetail>() {
            @Override
            protected void onSuc(ScannerFeeListDetail body) {
                ShowLoadingDialog.close();
                if (body.getData().getPrice().compareTo(BigDecimal.ZERO)>0){
                    tv_money.setText("-"+body.getData().getPrice()+"");
                }else {
                    tv_money.setText(body.getData().getPrice()+"");
                }
                if (body.getData().getType()==4){
                    tv_type.setText("扫码入库严重超时(元)");
                }else if (body.getData().getType()==1){
                    tv_type.setText("扫码入库正常免收费(元)");
                }else if (body.getData().getType()==2){
                    tv_type.setText("扫码入库人工正常收费(元)");
                }else if (body.getData().getType()==3){
                    tv_type.setText("扫码入库延时(元)");
                }else if (body.getData().getType()==5){
                    tv_type.setText("扫码入库严重超时额外扣费(元)");
                }else if (body.getData().getType()==6){
                    tv_type.setText("扫码入库免收费(元)");
                }else if (body.getData().getType()==7){
                    tv_type.setText("扫码入库取消收费(元)");
                }
                tv_date.setText("扫码时间:"+getTimes(new Date(body.getData().getCreateTime())));
                tv_ck.setText("仓库:"+body.getData().getScannerHouse());
                tv_sm_name.setText("扫码司机:"+body.getData().getDriverName());
                tv_sm_phone.setText("手机号:"+body.getData().getDriverPhone());
                ImageLoaderUtils.loadImage(iv,body.getData().getOrderInfo().getProductImage());
                tv_name.setText(body.getData().getOrderInfo().getProductName());
                if (!UIUtils.isNullOrZeroLenght(body.getData().getOrderInfo().getNickName())){
                    tv_nickname.setText("("+body.getData().getOrderInfo().getNickName()+")");
                }
                tv_num.setText("X"+body.getData().getOrderInfo().getQty());
                goods_unit2.setText(body.getData().getOrderInfo().getAvgUnit());
                cart_price.setText(body.getData().getOrderInfo().getTotalPrice()+"");

                if (body.getData().getOrderInfo().getLevelType()==3){
                    tv_content.setText("￥"+body.getData().getOrderInfo().getPrice()+"元/"+body.getData().getOrderInfo().getUnit()+"("+body.getData().getOrderInfo().getLevel2Value() + ""+ body.getData().getOrderInfo().getLevel2Unit()+"*"+body.getData().getOrderInfo().getLevel3Value()+  body.getData().getOrderInfo().getLevel3Unit()+ ""+")");
                } else if (body.getData().getOrderInfo().getLevelType()==1){
                    tv_content.setText("￥"+body.getData().getOrderInfo().getPrice()+"元/"+body.getData().getOrderInfo().getUnit());


                }else {
                    tv_content.setText("￥" + body.getData().getOrderInfo().getPrice() + "元/" + body.getData().getOrderInfo().getUnit() + "(" + body.getData().getOrderInfo().getLevel2Value() + body.getData().getOrderInfo().getLevel2Unit() + ")");
                }
                goods_count.setText(body.getData().getOrderInfo().getQty()+"");
                tv_order_no.setText("商品编号："+body.getData().getOrderInfo().getSku());

            }

            @Override
            public void onFailed(int errCode, String msg) {
                ShowLoadingDialog.close();
            }
        });



    }

    public static String getTimes(Date date) {//
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
