package cn.com.taodaji.ui.activity.employeeManagement;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.common.Config;
import com.base.glide.ImageLoaderUtils;
import com.base.glide.nineImageView.ImagesActivity;
import com.base.retrofit.RequestCallback;
import com.base.takephoto.model.TResult;
import com.base.utils.BitmapUtil;
import com.base.utils.IOUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.bumptech.glide.Glide;
import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ImageUpload;
import cn.com.taodaji.model.entity.ImageUploadOk;
import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.ui.ppw.TakePhotoPopupWindow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.SimpleToolbarActivity;
import tools.activity.TakePhotosActivity;
import tools.extend.TakePhotoUtils;

public class LicenseActivity extends TakePhotosActivity {

    private View mainView, secondView;
    private Button submit, certi;
    private TextView tips1, tips2, shopName,enterpriseName;
    private ImageView icon,enterpriseIcon,storeIcon;
    private LinearLayout shopTitle;
    private RelativeLayout reviewIcon;
    private int status = -1;//营业执照状态 0-待审核，1-审核成功，2-审核失败
    private boolean isCertification;//false 未认证  true 已认证
    private Bitmap bitmap;
    public String image_url = null;
    private Map<String, Object> map = new HashMap<>();
    private String imageParamsKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("营业执照认证");
    }

    @Override
    public void initMainView() {
        mainView = getLayoutView(R.layout.activity_license);
        body_other.addView(mainView);
        secondView = getLayoutView(R.layout.layout_license_certification);

        enterpriseName = ViewUtils.findViewById(mainView, R.id.tv_company_name);
        enterpriseIcon = ViewUtils.findViewById(mainView,R.id.iv_enterprise_license);

        shopTitle = ViewUtils.findViewById(secondView, R.id.ll_certification_store_name);
        shopName = ViewUtils.findViewById(secondView, R.id.tv_certification_store_name);
        tips1 = ViewUtils.findViewById(secondView, R.id.tv_tips1);
        tips2 = ViewUtils.findViewById(secondView, R.id.tv_tips2);
        icon = ViewUtils.findViewById(secondView, R.id.iv_certification_store_icon);
        certi = ViewUtils.findViewById(secondView, R.id.btn_cert_license);
        storeIcon = ViewUtils.findViewById(secondView,R.id.iv_store_icon);
        reviewIcon = ViewUtils.findViewById(secondView,R.id.rl_review_success);

        submit = ViewUtils.findViewById(mainView, R.id.btn_submit);
        status = getIntent().getIntExtra("status", -1);
        if (!getIntent().getStringExtra("licenseUrl").trim().equals("")) {
            body_other.removeView(mainView);
            body_other.addView(secondView);
            CurrentView(status);
        }else {
            enterpriseName.setText(PublicCache.loginPurchase.getHotelName());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading("正在提交");
                returnResult();
            }
        });

        storeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagesActivity.startActivity(storeIcon,PublicCache.loginPurchase.getBzlicenceUrl());
            }
        });

        certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status) {
                    case 2:
                        body_other.removeView(secondView);
                        body_other.addView(mainView);
                        enterpriseName.setText(PublicCache.loginPurchase.getHotelName());
                        break;
                    case 1:
                        finish();
                    case 0:
                        finish();
                    default:
                        break;
                }
            }
        });
        enterpriseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePhotoPopupWindow photoPopupWindow = new TakePhotoPopupWindow(icon) {
                    String fileName = System.currentTimeMillis() + ".jpg";
                    Uri imageUri = Uri.fromFile(IOUtils.createFile(Config.imageSaveDir, fileName));

                    @Override
                    public void goCamera() {
                        TakePhotoUtils.getInstance().setCrop(false).setImageUri(imageUri).openCamera(getTakePhoto());
                    }

                    @Override
                    public void goAlbum() {
                        TakePhotoUtils.getInstance().setCrop(false).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 1, true);
                    }
                };
                photoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            }
        });
    }


    public void CurrentView(int status) {
        switch (status) {
            case 2:
                certi.setText("重新认证");
                shopTitle.setVisibility(View.GONE);
                reviewIcon.setVisibility(View.GONE);
                icon.setImageResource(R.mipmap.review_not_pass);
                tips1.setText("企业名称："+PublicCache.loginPurchase.getHotelName());
                tips1.setTextColor(getResources().getColor(R.color.black));
                tips2.setText("失败原因："+getIntent().getStringExtra("info"));
                tips2.setTextColor(getResources().getColor(R.color.red_dark));
                break;
            case 0:
                certi.setText("好的");
                reviewIcon.setVisibility(View.GONE);
                shopTitle.setVisibility(View.GONE);
                icon.setImageResource(R.mipmap.reviewing);
                tips1.setText("企业名称："+PublicCache.loginPurchase.getHotelName());
                tips1.setTextColor(getResources().getColor(R.color.black));
                tips2.setText("您的信息正在审核中，请您耐心等待");
                tips2.setTextColor(getResources().getColor(R.color.black));
                break;
            case 1:
                certi.setText("恭喜您，您的证件已经通过审核");
                shopTitle.setVisibility(View.VISIBLE);
                shopName.setText("企业名称："+PublicCache.loginPurchase.getHotelName());
                icon.setVisibility(View.GONE);
                ImageLoaderUtils.loadImage(storeIcon,getIntent().getStringExtra("licenseUrl"),false);
                tips1.setVisibility(View.INVISIBLE);
                tips2.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }


    //上传图片信息
    private void returnResult() {
        if (TextUtils.isEmpty(image_url)) return;
        map.put("entityId",PublicCache.loginPurchase.getEntityId());
        map.put("bzlicenceUrl", image_url);
        String update = "update";
        getRequestPresenter().customer_update(update, map, new RequestCallback<ImageUploadOk>() {
            @Override
            public void onSuc(ImageUploadOk ok) {
                loadingDimss();
                UIUtils.showToastSafesShort("提交成功");
                LoginPurchaseBean loginPurchaseBean = SugarRecord.first(LoginPurchaseBean.class);
                if (loginPurchaseBean != null) {
                    loginPurchaseBean.setBzlicenceUrl(image_url);
                    PublicCache.loginPurchase.setBzlicenceUrl(image_url);
                }
                loginPurchaseBean.update();
                finish();
            }

            @Override
            public void onFailed(int imageUploadOk, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        String path = result.getImage().getOriginalPath();
        if (TextUtils.isEmpty(path)) return;
        loading("正在上传");
        String imageName = UIUtils.getFileNames(path);
        bitmap = BitmapUtil.getSmallBitmap(path);
        if (bitmap == null) return;
        image_url = null;
        getRequestPresenter().upload(imageName, BitmapUtil.bitmapTobyte(bitmap, true), new Callback<ImageUpload>() {
            @Override
            public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                if (response.body() != null) {
                    loadingDimss();
                    image_url = response.body().getData();
                    ImageLoaderUtils.loadImage(enterpriseIcon, path,false);
                } else {
                    loadingDimss();
                    UIUtils.showToastSafesShort(response.message());
                }
            }

            @Override
            public void onFailure(Call<ImageUpload> call, Throwable t) {
                loadingDimss();
                UIUtils.showToastSafesShort("图片上传失败，请检查网络");
            }
        });
    }
}