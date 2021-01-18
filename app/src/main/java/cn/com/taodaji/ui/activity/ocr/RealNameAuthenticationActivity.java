package cn.com.taodaji.ui.activity.ocr;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ImageUpload;

import com.base.retrofit.RequestCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.SimpleToolbarActivity;
import tools.extend.PhoneUtils;

import com.base.entity.ResultInfo;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.utils.BitmapUtil;
import com.base.utils.ClickCheckedUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

/**
 * Created by Administrator on 2017-12-23.
 */

public class RealNameAuthenticationActivity extends SimpleToolbarActivity implements View.OnClickListener {
    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 1;
    private View mainView;
    private GlideImageView mImagePortrait, mImageBack, mImageExplain;

    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    private static final int REQUEST_CODE_CAMERA = 102;
    private AlertDialog.Builder alertDialog;
    private TextView mTvIdNumber, mTvPeriod;
    private Button mBtnOK;
    private String idNumber, name, address, birthday, expirationDate, imageFrontPath, imageBackPath, gender;

    private boolean isLoadDone_front = false;//身份证正面是否上传完毕
    private boolean isLoadDone_back = false;//身份证后面是否上传完毕
//    private CompressImageUtil imageUtil;

    private TextView true_name, phone_tel;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("实名认证");
    }

    @Override
    protected void initMainView() {
        alertDialog = new AlertDialog.Builder(this);
        mainView = getLayoutView(R.layout.activity_real_name_authentication);
        body_scroll.addView(mainView);

        true_name = ViewUtils.findViewById(mainView, R.id.true_name);
        phone_tel = ViewUtils.findViewById(mainView, R.id.phone_tel);

        mImagePortrait = ViewUtils.findViewById(mainView, R.id.real_name_authentication_portrait_img);
        mImageBack = ViewUtils.findViewById(mainView, R.id.real_name_authentication_back_img);
        mTvIdNumber = ViewUtils.findViewById(mainView, R.id.real_name_authentication_id_tv);
        mTvPeriod = ViewUtils.findViewById(mainView, R.id.real_name_authentication_period_tv);
        mImageExplain = ViewUtils.findViewById(mainView, R.id.real_name_authentication_explain_img);
        mBtnOK = ViewUtils.findViewById(mainView, R.id.real_name_authentication_ok_btn);
        setViewBackColor(mBtnOK);
        mImagePortrait.setOnClickListener(this);
        mImageBack.setOnClickListener(this);
        mImageExplain.setOnClickListener(this);
        mBtnOK.setOnClickListener(this);


        if (PublicCache.loginPurchase != null) {

//            PublicCache.loginPurchase.setRealname("谭亚明");
            true_name.setText(PublicCache.loginPurchase.getRealname());
            phone_tel.setText(PublicCache.loginPurchase.getPhoneNumber());
            mImageExplain.setVisibility(View.GONE);
        } else if (PublicCache.loginSupplier != null) {
//            PublicCache.loginSupplier.setRealname("肖明珍");
            true_name.setText(PublicCache.loginSupplier.getRealname());
            phone_tel.setText(PublicCache.loginSupplier.getPhoneNumber());
        }

        initAccessTokenWithAkSk();
        checkGalleryPermission();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.real_name_authentication_portrait_img:
                if (checkTokenStatus()) {
                    return;
                }
                if (checkGalleryPermission()) {
                    mTvIdNumber.setText("");
                    mImagePortrait.setImageResource(R.mipmap.portrait);
                    Intent intent = new Intent(this, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getFront_file(true).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
                break;
            case R.id.real_name_authentication_back_img:
                if (checkTokenStatus()) {
                    return;
                }
                if (checkGalleryPermission()) {
                    mTvPeriod.setText("");
                    mImageBack.setImageResource(R.mipmap.national_emblem);
                    Intent intent = new Intent(this, CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getBack_file(true).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }

                break;
            case R.id.real_name_authentication_explain_img:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = View.inflate(getBaseActivity(), R.layout.realname_authentication_aler_dialog_layout, null);
               TextView txt_service_phone=view.findViewById(R.id.txt_service_phone);
                txt_service_phone.setText(PhoneUtils.getPhoneService());
                builder.setView(view);
                builder.setCancelable(true);
                TextView title = (TextView) view.findViewById(R.id.title);//设置标题
                Button btn_comfirm = (Button) view.findViewById(R.id.real_name_authentication_dialog_button);//确定按钮
                final AlertDialog dialog = builder.create();
//                取消或确定按钮监听事件处理
                btn_comfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                break;
            case R.id.real_name_authentication_ok_btn:
                if (TextUtils.isEmpty(mTvIdNumber.getText().toString())) {
                    UIUtils.showToastSafesShort("身份证正面没有拍摄");
                } else if (TextUtils.isEmpty(mTvPeriod.getText().toString())) {
                    UIUtils.showToastSafesShort("身份证反面没有拍摄");
                } else {
                    loading();
                    //如果正面未传则传正面
                    if (!isLoadDone_front) {
                        imageLoad(imageFrontPath, "front");
                    }
                    //如果正面未传则传正面
                    if (!isLoadDone_back) {
                        imageLoad(imageBackPath, "back");
                    }
                }
                break;
        }
    }

    private void sucess() {
        Intent intent1 = new Intent(this, RealNameAuthenticationShowActivity.class);
        startActivity(intent1);
        finish();
    }

    private void returnResult() {
        Map<String, Object> map = new HashMap<>();
        map.put("gender", gender);
        map.put("isAuth", 1);
        map.put("birthday", birthday);
        map.put("address", address);
        map.put("expirationDate", expirationDate);
        map.put("frontageIdcardImageUrl", imageFrontPath);
        map.put("backIdcardImageUrl", imageBackPath);
        if (PublicCache.loginPurchase != null) {
            map.put("entityId", PublicCache.loginPurchase.getEntityId());
            map.put("identificationCard", idNumber);
            getRequestPresenter().customer_reversion(map, new RequestCallback<ResultInfo>() {
                @Override
                protected void onSuc(ResultInfo body) {
                    loadingDimss();
                    PublicCache.loginPurchase.setIdentificationCard(idNumber);
                    PublicCache.loginPurchase.setBackIdcardImageUrl(imageBackPath);
                    PublicCache.loginPurchase.setFrontageIdcardImageUrl(imageFrontPath);
                    PublicCache.loginPurchase.setIsAuth("1");
                    PublicCache.loginPurchase.update();
                    sucess();
                }

                @Override
                public void onFailed(int resultInfo, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafe(msg);
                }
            });
        } else if (PublicCache.loginSupplier != null) {

            map.put("idcardNumber", idNumber);
            map.put("entityId", PublicCache.loginSupplier.getEntityId());
            getRequestPresenter().supplier_reversion(map, new RequestCallback<ResultInfo>() {
                @Override
                protected void onSuc(ResultInfo body) {
                    loadingDimss();
                    PublicCache.loginSupplier.setIdcardNumber(idNumber);
                    PublicCache.loginSupplier.setBackIdcardImageUrl(imageBackPath);
                    PublicCache.loginSupplier.setFrontageIdcardImageUrl(imageFrontPath);
                    PublicCache.loginSupplier.setIsAuth("1");
                    PublicCache.loginSupplier.update();
                    sucess();
                }

                @Override
                public void onFailed(int resultInfo, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafe(msg);
                }
            });
        }


    }

    private boolean checkTokenStatus() {
        if (OCR.getInstance().hasGotToken()) {
            UIUtils.showToastSafe("实名认证初始化失败");
        }
        return OCR.getInstance().hasGotToken();
    }

    int count = 0;

    private void initAccessTokenWithAkSk() {
        if (OCR.getInstance().hasGotToken()) {
            loading();
            count++;
            OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
                @Override
                public void onResult(AccessToken accessToken) {
                    loadingDimss();
                }

                @Override
                public void onError(OCRError ocrError) {
//                    loadingDimss();
                    UIUtils.runInMainThread(() -> {
                        if (ClickCheckedUtil.onClickChecked(2000)) {
                            UIUtils.showToastSafesShort(ocrError.getMessage());
                        }
                        if (count == 5) finish();
                        else initAccessTokenWithAkSk();
                    });

                }
            }, this, "jYB4IoqeYbFZovylB0Etxzai", "wi2e2VFEn5HYYquHEpGbzUmEVUwgoQWq");
        }
    }

    private boolean checkGalleryPermission() {
        int ret = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ret != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            return false;
        }
        return true;
    }

    private void recIDCard(final String idCardSide, final String filePath) {
        loading();
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                loadingDimss();
                if (result != null) {
                    //获取到身份证信息，view赋值
                    if (result.getIdCardSide().equals("front")) {
                        if (!TextUtils.isEmpty(result.getIdNumber().getWords()) && result.getIdNumber().getWords().length() == 18) {

                            if (true_name.getText().toString().equals(result.getName().getWords())) {
                                mTvIdNumber.setText(result.getIdNumber().getWords());
                                idNumber = result.getIdNumber().getWords();
                                name = result.getName().getWords();
                                gender = result.getGender().getWords();
                                address = result.getAddress().getWords();
                                birthday = result.getBirthday().getWords();
                                isLoadDone_front = false;
                                imageFrontPath = filePath;
                                // imageLoad(filePath, result.getIdCardSide());

                                ImageLoaderUtils.loadImage(mImagePortrait,  filePath);

                            } else {
                                UIUtils.showToastSafe("请验证本人身份证");
                            }

                        } else UIUtils.showToastSafe("身份证人像页未识别");

                    } else {
                        if (!TextUtils.isEmpty(result.getExpiryDate().getWords())) {
                            isLoadDone_back = false;
                            imageBackPath = filePath;
                            //  imageLoad(filePath, result.getIdCardSide());
                            ImageLoaderUtils.loadImage(mImageBack,  filePath);
                            expirationDate = result.getExpiryDate().getWords();
                            mTvPeriod.setText("有效期：" + changageDateStyle(result.getSignDate().getWords()) + "-" + changageDateStyle(result.getExpiryDate().getWords()));

                        } else UIUtils.showToastSafe("身份证国徽页未识别");
                    }
                }
            }

            @Override
            public void onError(OCRError error) {
                loadingDimss();
                if (idCardSide.equals("front")) {
                    mImagePortrait.setImageResource(R.mipmap.portrait);
                    UIUtils.showToastSafe("身份证人像页未识别");

                } else {
                    mImageBack.setImageResource(R.mipmap.national_emblem);
                    UIUtils.showToastSafe("身份证国徽页未识别");
                }
            }
        });
    }

    private String changageDateStyle(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        if (date.equals("长期")) {
            return date;
        } else if (date.length() < 7) {
            return "";
        }

        StringBuilder sb = new StringBuilder(date);
        sb.insert(4, ".");
        date = sb.toString();
        StringBuffer sb1 = new StringBuffer(date);
        sb1.insert(7, ".");
        date = sb1.toString();
        return date;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        String filePath = FileUtil.getFront_file(false).getAbsolutePath();
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        String filePath = FileUtil.getBack_file(false).getAbsolutePath();
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }
    }

    private void imageLoad(String filePath, final String contentType) {
        String imageName = UIUtils.getFileNames(filePath);
        Bitmap bitmap = BitmapUtil.getSmallBitmap(filePath);
        if (bitmap == null) return;
        getRequestPresenter().upload(imageName, BitmapUtil.bitmapTobyte(bitmap, true), new Callback<ImageUpload>() {
            @Override
            public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                if (response.body() != null) {
                    if (contentType.equals("front")) {
                        imageFrontPath = response.body().getData();
                        isLoadDone_front = true;
                    } else {
                        imageBackPath = response.body().getData();
                        isLoadDone_back = true;
                    }
                    //如果正反面都上传完毕则提交
                    if (isLoadDone_front && isLoadDone_back) {
                        returnResult();
                    }
                }

            }

            @Override
            public void onFailure(Call<ImageUpload> call, Throwable t) {
                loadingDimss();
                UIUtils.showToastSafesShort("图片上传失败，请重新提交");
            }
        });
    }
}
