package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.GoodsCategoryList;
import cn.com.taodaji.ui.activity.myself.GoodsNeedActivity;
import cn.com.taodaji.ui.ppw.GoodsNameCustomerPopupwindow;
import cn.com.taodaji.model.entity.GoodsCategoryListNext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.activity.SimpleToolbarActivity;
import tools.extend.FluidLayout;
import tools.extend.SerializableMap;

import com.base.listener.UploadPicturesDoneListener;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class CommodityCategoryNextActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private View mainView;
    private FluidLayout fluidLayout;
    private boolean isCallback = false;
    private int parentCategoryId;
    private String parentCategoryName;
    private GoodsCategoryList.ChildrenBean data;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_shop_management_commodity_category_next);
        body_scroll.addView(mainView);
        body_scroll.setBackgroundResource(R.color.gray_f2);
        fluidLayout = ViewUtils.findViewById(this, R.id.fluidLayout);
        TextView help_message = ViewUtils.findViewById(this, R.id.help_message);
        help_message.setText(UIUtils.getString(R.string.commodity_category_help));
        SpannableStringBuilder builder = new SpannableStringBuilder(help_message.getText().toString());
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(UIUtils.getColor(R.color.blue_2898eb));
        builder.setSpan(blueSpan, 44, 52, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        help_message.setText(builder);
        help_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommodityCategoryNextActivity.this, GoodsNeedActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        isCallback = intent.getBooleanExtra("isCallback", false);
        parentCategoryId = intent.getIntExtra("parentCategoryId", 0);
        parentCategoryName = intent.getStringExtra("parentCategoryName");
        data = (GoodsCategoryList.ChildrenBean) intent.getSerializableExtra("data");
        if (data != null) {
            simple_title.setText(data.getCategoryName());
            genTag(data.getChildren());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==100){
            if (requestCode==101){
                initData();
            }
        }
    }

    private void genTag(List<GoodsCategoryListNext> list) {
        fluidLayout.removeAllViews();
        fluidLayout.setGravity(Gravity.TOP);
        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(this);
            tv.setOnClickListener(this);
            tv.setTextSize(18);
            tv.setBackgroundResource(R.drawable.s_round_rect_solid_gray_storke_blue_2898eb);
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(UIUtils.dip2px(5), UIUtils.dip2px(5), UIUtils.dip2px(5), UIUtils.dip2px(5));
            tv.setPadding(UIUtils.dip2px(15), UIUtils.dip2px(7), UIUtils.dip2px(15), UIUtils.dip2px(8));
            tv.setText(list.get(i).getName());
            tv.setTag(list.get(i).getName() + "_" + list.get(i).getEntityId());
            fluidLayout.addView(tv, params);
        }
    }

    @Override
    public void onClick(View view) {
        if (PublicCache.loginSupplier == null) return;
        String name;
        Intent intent;
        if ("自定义".equals(name = view.getTag().toString())) {
            Map<String, Object> map = new HashMap<>();
            map.put("categoryId", data.getCategoryId());
            map.put("parentCategoryId", parentCategoryId);
            map.put("parentCategoryName", parentCategoryName + "_" + data.getCategoryName());
            map.put("entityId", PublicCache.loginSupplier.getEntityId());
            Intent intent1 = new Intent(CommodityCategoryNextActivity.this,GoodsNameCustomerActivity.class);
            final SerializableMap myMap=new SerializableMap();
            myMap.setMap(map);//将map数据添加到封装的myMap中
            Bundle bundle=new Bundle();
            bundle.putSerializable("map", myMap);
            intent1.putExtras(bundle);
            startActivityForResult(intent1,100);
        } else {
            String[] str = name.split("_");
            intent = new Intent();
            intent.putExtra("typeId", data.getTypeId());
            intent.putExtra("name", str[0]);
            intent.putExtra("commodityId", Integer.valueOf(str[1]));
            intent.putExtra("categoryId", data.getCategoryId());
            intent.putExtra("goodsEditState", 0);//发布商品
            intent.putExtra("parentCategoryId", parentCategoryId);

            if (isCallback) {
                setResult(RESULT_OK, intent);
                finish();
            } else {
                if (getIntent().getIntExtra("isDrainageArea", 0)==0){
                    intent.putExtra("isForceTemplate", getIntent().getIntExtra("isForceTemplate", 0));
                    intent.setClass(this, ReleaseCommodityGoodsCreateActivity.class);
                }else {
                    intent.setClass(this, ReleaseGoodsActivity.class);
                }

                startActivity(intent);
            }
        }
    }
}
