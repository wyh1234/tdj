package cn.com.taodaji.ui.activity.myself;


import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.FooddemandSave;

import java.util.HashMap;
import java.util.Map;

import com.base.listener.UploadPicturesDoneListener;
import com.base.retrofit.RequestCallback;

import cn.com.taodaji.ui.activity.shopManagement.CommodityCategoryActivity;
import cn.com.taodaji.ui.ppw.SimpleButtonPopupWindow;
import tools.activity.MenuToolbarActivity;
import tools.activity.SimpleToolbarActivity;
import tools.activity.TakePhotosActivity;
import tools.fragment.AddedPicturesFragment;

import com.base.utils.UIDataProcessing;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class GoodsNeedActivity extends TakePhotosActivity implements UploadPicturesDoneListener,View.OnClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("新品需求");
    }

    private View mainView;
    private Button goods_need_ok;
    private EditText title,type,info;
    private SimpleButtonPopupWindow simpleButtonPopupWindow;
    private AddedPicturesFragment addedPicturesFragment;
    private int pid,id;
    private TextView category;
    private LinearLayout select;

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_myself_goods_need);
        body_scroll.addView(mainView);
        goods_need_ok = ViewUtils.findViewById(mainView, R.id.goods_need_ok);
        goods_need_ok.setOnClickListener(this);
        title = ViewUtils.findViewById(mainView, R.id.et_item_title);
        type = ViewUtils.findViewById(mainView, R.id.et_item_type);
        info = ViewUtils.findViewById(mainView,R.id.et_item_content);
        category = ViewUtils.findViewById(mainView, R.id.tv_item_category);
        select = ViewUtils.findViewById(mainView, R.id.ll_select_category);
        select.setOnClickListener(this);

        addedPicturesFragment = (AddedPicturesFragment) getSupportFragmentManager().findFragmentById(R.id.addedPicturesFragment);
        addedPicturesFragment.setMaxSelect(3);
        setViewBackColor(goods_need_ok);
    }

    @Override
    protected void initData() {
        if (getIntent().getStringExtra("name")!=null){
            title.setText(getIntent().getStringExtra("name"));
        }

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_select_category:
                Intent intent = new Intent(GoodsNeedActivity.this, CommodityCategoryActivity.class);
                intent.putExtra("GoodsNeed",1);
                startActivityForResult(intent,100);
                break;
            case R.id.goods_need_ok:
                if (pid==0||id==0|| title.getText().toString().trim().length() == 0) {
                    UIUtils.showToastSafesShort("商品名称和类别不可为空");
                    return;
                }

                Map<String, Object> map = new HashMap<>();

                map.put("site", PublicCache.site_name);
                map.put("parentCategoryId", pid);
                map.put("categoryId", id);
                map.put("title",title.getText().toString().trim());
                map.put("nickName",type.getText().toString().trim());
                map.put("content",info.getText().toString().trim());
                if (PublicCache.login_mode.equals(PURCHASER)) {
                    map.put("source", "0");
                    map.put("sourceEntityId", PublicCache.loginPurchase.getLoginUserId());
                } else {
                    map.put("source", "1");
                    map.put("sourceEntityId", PublicCache.loginSupplier.getEntityId());
                }
                map.put("img_url",addedPicturesFragment.getImageStr());
                loading("正在提交...");
                getRequestPresenter().fooddemand_save(map, new RequestCallback<FooddemandSave>() {
                    @Override
                    public void onSuc(FooddemandSave body) {
                        loadingDimss();
                        //弹出选择窗口
                        simpleButtonPopupWindow = new SimpleButtonPopupWindow(0, mainView);
                        simpleButtonPopupWindow.setMessage("我们已经收到您的新品需求，感谢您的帮助和支持。")
                                .isClose(false)
                                .setCloseHide(true)
                                .isTransparent(true)
                                .setButton_left_text("逛市场")
                                .setButton_right_text("去挑菜")
                                .setButton_left_backgroundResource(R.drawable.r_round_rect_solid_orange_ff7d01)
                                .setButtonOnclickInterface(new SimpleButtonPopupWindow.ButtonOnclickInterface() {
                                    @Override
                                    public void buttonLeftOnclick() {
                                        MenuToolbarActivity.goToPage(1);
                                        simpleButtonPopupWindow.dismiss();
                                        finish();
                                    }

                                    @Override
                                    public void buttonRightOnclick(int position, View showCountView) {
                                        MenuToolbarActivity.goToPage(2);
                                        simpleButtonPopupWindow.dismiss();
                                        finish();
                                    }
                                });
                        simpleButtonPopupWindow.showAtLocation(mainView, Gravity.CENTER, 0, 0);
                    }

                    @Override
                    public void onFailed(int fooddemandSave, String msg) {
                        loadingDimss();
                        UIUtils.showToastSafe(msg);
                    }
                });
                break;
                default:break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (simpleButtonPopupWindow != null && simpleButtonPopupWindow.isShowing())
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void uploadPicturesIsDone(Object obj) {
        if (isDestroyed()) return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==100){
            if(resultCode==RESULT_OK){
                pid = data.getIntExtra("parentCategoryId",0);
                id = data.getIntExtra("categoryId",0);
                category.setText(data.getStringExtra("categoryName"));
            }
        }
    }
}
