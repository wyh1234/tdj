package cn.com.taodaji.ui.activity.shopManagement;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.base.common.Config;
import com.base.glide.ImageLoaderUtils;
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
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;
import cn.com.taodaji.ui.ppw.TakePhotoPopupWindow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.SimpleToolbarActivity;
import tools.activity.TakePhotosActivity;
import tools.extend.TakePhotoUtils;

public class StoreImageActivity extends TakePhotosActivity implements View.OnClickListener {

    private View mainView;
    private ImageView takePhoto,photoBackground;
    private Button save;
    private TextView textView,shopTitle;

    private Map<String, Object> map = new HashMap<>();
    private String imageParamsKey = "imageUrl";

    private Bitmap bitmap;
    private String image_url = null;
    private boolean isCallback = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("门店形象照");
    }

    @Override
    public void initMainView() {
        mainView = getLayoutView(R.layout.activity_store_image);
        body_other.addView(mainView);

        takePhoto = ViewUtils.findViewById(mainView, R.id.iv_take_photo_icon);
        save = ViewUtils.findViewById(mainView, R.id.btn_save);
        photoBackground = ViewUtils.findViewById(mainView,R.id.rl_image_bg);
        textView = ViewUtils.findViewById(mainView,R.id.tv_take_photo_text);
        shopTitle = ViewUtils.findViewById(mainView,R.id.tv_shop_title);
        shopTitle.setText(PublicCache.loginPurchase.getHotelName());
        photoBackground.setOnClickListener(this);
        save.setOnClickListener(this);
        if (!getIntent().getStringExtra("url").equals("")){
            switch (getIntent().getIntExtra("imgStatus",-1)){
                case 0:
                    Glide.with(this).load(getIntent().getStringExtra("url")).into(photoBackground);
                    takePhoto.setBackgroundResource(R.mipmap.icon_gray_wait);
                    textView.setText("正在审核中");
                    break;
                case 1:
                    Glide.with(this).load(getIntent().getStringExtra("url")).into(photoBackground);
                    textView.setText("更换门头");
                    break;
                case 2:
                    Glide.with(this).load(getIntent().getStringExtra("url")).into(photoBackground);
                    textView.setText("审核失败，重新拍摄");
                    break;
                    default:break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_image_bg:
                TakePhotoPopupWindow photoPopupWindow = new TakePhotoPopupWindow(takePhoto) {
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
                break;
            case R.id.btn_save:
                returnResult();
                loading("正在上传");
                break;
            default:
                break;
        }
    }


    //上传图片信息
    private void returnResult() {
        if (TextUtils.isEmpty(image_url)) return;
        map.put("entityId",PublicCache.loginPurchase.getEntityId());
        map.put(imageParamsKey, image_url);//imagePa  类型参数
        String update = "update";
        getRequestPresenter().customer_update(update, map, new RequestCallback<ImageUploadOk>() {
            @Override
            public void onSuc(ImageUploadOk ok) {
                loadingDimss();
                LoginPurchaseBean loginPurchaseBean = SugarRecord.first(LoginPurchaseBean.class);
                loginPurchaseBean.setImageUrl(image_url);
                PublicCache.loginPurchase.setImageUrl(image_url);
                loginPurchaseBean.update();
                UIUtils.showToastSafesShort("上传成功");
                takePhoto.setBackgroundResource(R.mipmap.icon_gray_wait);
                textView.setText("正在审核中");
                finish();
            }

            @Override
            public void onFailed(int imageUploadOk, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort("上传失败");
            }
        });
    }


    @Override
    public void takeSuccess(TResult result) {
        String path = result.getImage().getCompressPath();
        if (TextUtils.isEmpty(path)) return;
        String imageName = UIUtils.getFileNames(path);
        bitmap = BitmapUtil.getSmallBitmap(path);
        if (bitmap == null) return;
        image_url = null;

        //ImageLoaderUtils.loadImage(takePhoto,  path);
        getRequestPresenter().upload(imageName, BitmapUtil.bitmapTobyte(bitmap, true), new Callback<ImageUpload>() {
            @Override
            public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                if (response.body() != null) {
                    image_url = response.body().getData();
                    Glide.with(getApplicationContext()).load(image_url).into(photoBackground);
                } else {
                    UIUtils.showToastSafesShort(response.message());
                }

            }

            @Override
            public void onFailure(Call<ImageUpload> call, Throwable t) {
                UIUtils.showToastSafesShort("图片上传失败，请检查网络");
                loadingDimss();
            }
        });
    }


    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }
}
