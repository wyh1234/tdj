package cn.com.taodaji.ui.activity.myself;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.glide.GlideImageView;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.IntegralItem;
import cn.com.taodaji.model.entity.ShareInfoBean;
import cn.com.taodaji.model.entity.ShareRecord;
import cn.com.taodaji.model.presenter.ModelRequest;
import cn.com.taodaji.ui.activity.integral.ShopVipActivity;
import cn.com.taodaji.ui.activity.integral.WebViewActivity;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.viewModel.adapter.ShareRecordAdapter;
import tools.activity.SimpleToolbarActivity;
import tools.share.ShareUtils;

public class ShareActivity extends SimpleToolbarActivity implements View.OnClickListener {
   private GlideImageView img_share;
    private String imageURL="";
    private String shareURL="";
    private RecyclerView recyclerView;
    private LinearLayout layout;
    private List<IntegralItem.DataBean> recordList = new ArrayList<>();
    private  ShareRecordAdapter adapter;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("邀请有礼");

    }

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.activity_myself_share);
        TextView ok= ViewUtils.findViewById(view, R.id.ok);
        setViewBackColor(ok);
        ok.setOnClickListener(this);

        TextView rewardRule = ViewUtils.findViewById(view,R.id.tv_share_reward_record);
        rewardRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ShareActivity.this, WebViewActivity.class);
                intent1.putExtra("url",PublicCache.getROOT_URL().get(2)+"tdj-user/user/integral/shareRulePage");
                startActivity(intent1);
            }
        });

        layout = ViewUtils.findViewById(view,R.id.toolbar);

        img_share= ViewUtils.findViewById(view, R.id.img_share);
        TextView text_invite_code= ViewUtils.findViewById(view, R.id.text_invite_code);
        recyclerView = ViewUtils.findViewById(view,R.id.rv_share_record);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if ( PublicCache.loginPurchase!=null){
            initRecordList();
        }

         adapter = new ShareRecordAdapter(recordList,this);
        recyclerView.setAdapter(adapter);

        if (PublicCache.loginPurchase != null) {
            text_invite_code.setVisibility(View.VISIBLE);
            text_invite_code.setText("我的邀请码："+PublicCache.loginPurchase.getVerifyCode());
            right_textView.setVisibility(View.VISIBLE);
            right_textView.setText("分享列表");
            right_textView.setBackgroundResource(R.drawable.r_round_rect_solid_transparent);
//            right_icon.setImageResource(R.mipmap.icon_white_person);
//            shareURL=shareURL+PublicCache.loginPurchase.getVerifyCode();
//            imageURL=imageURL+PublicCache.loginPurchase.getVerifyCode();
        }else {
            text_invite_code.setVisibility(View.GONE);
            right_textView.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
//            shareURL=shareURL+"0";
//            imageURL=imageURL+"0";
        }


        body_other.addView(view);

        right_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareShopListActivity.startActivity(getBaseActivity());
            }
        });

    }

    private void initRecordList(){
        Map<String,Object> map=new HashMap<>();
        map.put("userId", PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        map.put("type",1);
        getIntegralRequestPresenter().integral_queryInviteList(map, new RequestCallback<IntegralItem>(this) {
            @Override
            public void onSuc(IntegralItem body) {
                if (!ListUtils.isEmpty(body.getData())){
                    recordList.addAll(body.getData());

                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                UIUtils.showToastSafe(msg);

            }
        });

    }

    @Override
    protected void initData() {
        getShareData();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ShareActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            UIUtils.showToastSafesShort("请允许淘大集读取存储卡");
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 110);
        } else shareWeb();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 110) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    shareWeb();
                } else {
                    //提示没有权限，安装不了咯
                    UIUtils.showToastSafesShort("分享失败");
                }
            }
        }
    }


    private void shareWeb() {
        new ShareUtils(this).shareWeb(shareURL, "淘大集-专业酒店食材供应链平台", "淘大集食材覆盖：新鲜蔬菜、禽肉蛋类、米面粮油、调料、水果等。食材相对市场价低20%~50%，更省钱省心省力。专业配送和服务团队。");
    }
    private void getShareData(){
        onStartLoading();
        String  code="0";
        if (PublicCache.loginPurchase != null&&!TextUtils.isEmpty(PublicCache.loginPurchase.getVerifyCode())) {
            code=PublicCache.loginPurchase.getVerifyCode();
        }
        addRequest(ModelRequest.getInstance(1).getShareInfoData(code, PublicCache.site_login), new RequestCallback<ShareInfoBean>(getBaseActivity()) {
            @Override
            protected void onSuc(ShareInfoBean body) {
                if (body.getErr()==0&&body.getData()!=null) {
                     shareURL=body.getData().getSharePaht();
                     imageURL=body.getData().getPath();
                     img_share.loadImage(imageURL);
                }
            }
        });

    }

}
