package cn.com.taodaji.ui.activity.myself;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import tools.extend.TakePhotoUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ImageUpload;
import cn.com.taodaji.model.entity.ImageUploadOk;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;

import com.base.common.Config;
import com.base.retrofit.RequestCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.TakePhotosActivity;

import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.BitmapUtil;
import com.base.takephoto.model.TResult;
import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;

import com.base.utils.IOUtils;
import com.base.utils.SerializableMap;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class ImageUploadActivity extends TakePhotosActivity implements View.OnClickListener {

    private String title;
    private boolean isCut = false;
    private String imageDescription;
    private Map<String, Object> map;
    private String imageParamsKey;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setToolBarColor();
        setStatusBarColor();
    }


    LinearLayout photo_album;
    LinearLayout camera;
    Button cancel;
    private Bitmap bitmap;
    GlideImageView head_portrait;
    private String image_url = null;
    private boolean isCallback = false;

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.activity_upload_image);
        body_scroll.addView(view);

        photo_album = ViewUtils.findViewById(view, R.id.photo_album);
        photo_album.setOnClickListener(this);
        camera = ViewUtils.findViewById(view, R.id.camera);
        camera.setOnClickListener(this);
        cancel = ViewUtils.findViewById(view, R.id.cancel);
        cancel.setOnClickListener(this);


        head_portrait = ViewUtils.findViewById(view, R.id.head_portrait);
        UIUtils.setViewSize(head_portrait, UIUtils.getScreenWidthPixels(), UIUtils.getScreenWidthPixels());

    }

    @Override
    protected void initData() {
        Intent data = getIntent();
        title = data.getStringExtra("title");
        simple_title.setText(title);
        isCut = data.getBooleanExtra("isCut", false);
        imageDescription = data.getStringExtra("imageDescription");
        imageParamsKey = data.getStringExtra("imageParamsKey");
        Bundle bundle = data.getExtras();
        if (bundle == null) return;
        SerializableMap sermap = (SerializableMap) bundle.get("map");
        if (sermap != null) {
            map = sermap.getMap();
        } else {
            map = new HashMap<>();
            if (PublicCache.loginPurchase != null)
                map.put("entityId", PublicCache.loginPurchase.getEntityId());
            else if (PublicCache.loginSupplier != null)
                map.put("entityId", PublicCache.loginSupplier.getEntityId());
        }
        String imageUrl = data.getStringExtra("imageUrl");

        ImageLoaderUtils.loadImage(head_portrait,  imageUrl);
    }


    @Override
    public void takeSuccess(TResult result) {
        String path = result.getImage().getCompressPath();
        if (TextUtils.isEmpty(path)) return;
        String imageName = UIUtils.getFileNames(path);
        bitmap = BitmapUtil.getSmallBitmap(path);
        if (bitmap == null) return;
        image_url = null;

        ImageLoaderUtils.loadImage(head_portrait,  path);
        getRequestPresenter().upload(imageName, BitmapUtil.bitmapTobyte(bitmap, true), new Callback<ImageUpload>() {
            @Override
            public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                if (response.body() != null) {
                    image_url = response.body().getData();
                    if (isCallback) returnResult();
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

    @Override
    public void onClick(View view) {
        String fileName = System.currentTimeMillis() + ".jpg";
        Uri imageUri = Uri.fromFile(IOUtils.createFile(Config.imageSaveDir, fileName));

        switch (view.getId()) {
            /**
             *打开相册
             * */
            case R.id.photo_album:
                TakePhotoUtils.getInstance().setCrop(isCut).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 1, true);
                break;
            /**
             *打开相机
             * */
            case R.id.camera:
                TakePhotoUtils.getInstance().setCrop(isCut).setImageUri(imageUri).openCamera(getTakePhoto());
                break;
            case R.id.cancel:
                if (bitmap == null) {
                    UIUtils.showToastSafesShort("请选择一张图片");
                    return;
                }

                if (image_url == null) {
                    isCallback = true;
                    loading("图片正在上传...");
                } else returnResult();
                break;

        }

    }

    //上传图片信息
    private void returnResult() {
        if (TextUtils.isEmpty(image_url)) return;
        map.put(imageParamsKey, image_url);
        String update = null;
        switch (PublicCache.login_mode) {
            case PURCHASER:
                switch (imageDescription) {
                    case CARD_ID_UPLOAD:
                        //实名验证和其他的不同,其他的是一样的
                        update = "toAuth";
                        break;
                    default:
                        update = "update";
                        break;

                }
                getRequestPresenter().customer_update(update, map, new RequestCallback<ImageUploadOk>() {
                    @Override
                    public void onSuc(ImageUploadOk ok) {
                        UIUtils.showToastSafesShort("上传成功");
                        ok.setData(image_url);
                        ok.setMsg(imageDescription);
                        LoginPurchaseBean loginPurchaseBean = SugarRecord.first(LoginPurchaseBean.class);
                        if (loginPurchaseBean != null) {
                            switch (imageDescription) {
                                case HEAD_PORTRAIT_UPLOAD:
                                    loginPurchaseBean.setImageUrl(image_url);
                                    PublicCache.loginPurchase.setImageUrl(image_url);
                                    break;
                                case CARD_ID_UPLOAD:
                                    loginPurchaseBean.setIdentificationImage(image_url);
                                    PublicCache.loginPurchase.setIdentificationImage(image_url);
                                    break;
                                case LICENCE_UPLOAD:
                                    loginPurchaseBean.setBzlicenceUrl(image_url);
                                    PublicCache.loginPurchase.setBzlicenceUrl(image_url);
                                    break;
                            }
                            loginPurchaseBean.update();
                        }
                        EventBus.getDefault().post(ok);
                        finish();
                    }

                    @Override
                    public void onFailed(int imageUploadOk, String msg) {

                    }
                });


                break;

            case SUPPLIER:
                switch (imageDescription) {
                    case CARD_ID_UPLOAD:
                        //实名验证和其他的不同,其他的是一样的
                        update = "toAuth";
                        break;
                    default:
                        update = "update";
                        break;

                }
                getRequestPresenter().supplier_update(update, map, new RequestCallback<ImageUploadOk>() {
                    @Override
                    public void onSuc(ImageUploadOk ok) {
                        UIUtils.showToastSafesShort("上传成功");
                        ok.setData(image_url);
                        ok.setMsg(imageDescription);
                        LoginSupplierBean loginSupplierBean = SugarRecord.first(LoginSupplierBean.class);
                        if (loginSupplierBean != null) {
                            switch (imageDescription) {
                                case HEAD_PORTRAIT_UPLOAD:
                                    loginSupplierBean.setStorePics(image_url);
                                    PublicCache.loginSupplier.setStorePics(image_url);
                                    break;
                                case CARD_ID_UPLOAD:
                                    loginSupplierBean.setIdcardPics(image_url);
                                    PublicCache.loginSupplier.setIdcardPics(image_url);
                                    break;
                                case FOOD_QUALITY_UPLOAD:
                                    loginSupplierBean.setFoodQualityPics(image_url);
                                    PublicCache.loginSupplier.setFoodQualityPics(image_url);
                                    break;
                                case LICENCE_UPLOAD:
                                    loginSupplierBean.setLicencePics(image_url);
                                    PublicCache.loginSupplier.setLicencePics(image_url);
                                    break;
                            }
                            loginSupplierBean.update();
                        }
                        EventBus.getDefault().post(ok);
                        finish();
                    }

                    @Override
                    public void onFailed(int imageUploadOk, String msg) {

                    }
                });
                break;
        }

    }

}
