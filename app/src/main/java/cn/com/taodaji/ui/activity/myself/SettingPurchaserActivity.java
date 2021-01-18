package cn.com.taodaji.ui.activity.myself;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.common.Config;
import com.base.glide.GlideImageView;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.takephoto.model.TResult;
import com.base.utils.BitmapUtil;
import com.base.utils.CacheUtils;
import com.base.utils.IOUtils;
import com.base.utils.JavaMethod;
import com.base.utils.SystemUtils;
import com.base.utils.ThreadManager;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.bumptech.glide.Glide;
import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.LoginMethod;
import cn.com.taodaji.model.entity.ImageUpload;
import cn.com.taodaji.model.entity.ImageUploadOk;
import cn.com.taodaji.model.entity.MyselftUpdateP;
import cn.com.taodaji.model.entity.UpdateCustomerBean;
import cn.com.taodaji.model.event.LoginOutEvent;
import cn.com.taodaji.model.event.PersonInfoChangeEvent;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.model.sqlBean.LoginPurchaseBean;
import cn.com.taodaji.model.sqlBean.LoginSupplierBean;
import cn.com.taodaji.ui.activity.login.PhoneReBindFirstActivity;
import cn.com.taodaji.ui.activity.login.UpdatePassWordActivity;
import cn.com.taodaji.ui.activity.ocr.RealNameAuthenticationActivity;
import cn.com.taodaji.ui.activity.ocr.RealNameAuthenticationShowActivity;
import cn.com.taodaji.ui.activity.ocr.RealNamePurchaserAuthenticationActivity;
import cn.com.taodaji.ui.ppw.TakePhotoPopupWindow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.TakePhotosActivity;
import tools.extend.PhoneUtils;
import tools.extend.TakePhotoUtils;


public class SettingPurchaserActivity extends TakePhotosActivity implements View.OnClickListener {
    private Button login_out;
    private TextView txt_service_phone;
    private TextView clean;

    private GlideImageView img_portrait;
    private TakePhotoPopupWindow takePhotoPopupWindow;
    private Bitmap bitmap;
    private String headUrl = null;
    private boolean isCallback = true;
    private String imageDescription;
    private Map<String, Object> map;
    private String imageParamsKey;

    private TextView text_real_name, text_nickname, text_phone;
    private ImageView img_real_tag;

    private String cardno = "0";//是否实名认证

    private LinearLayout ll_phone;

    private MyselftUpdateP.DataBean bean;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("设置");

    }


    @Override
    protected void initMainView() {

        View mainView = getLayoutView(R.layout.activity_myself_purchaser_setting);
        body_scroll.addView(mainView);

        LinearLayout ll_news = ViewUtils.findViewById(mainView, R.id.ll_news);
        ll_news.setOnClickListener(this);

        LinearLayout ll_nickname = ViewUtils.findViewById(mainView, R.id.ll_nickname);
        ll_nickname.setOnClickListener(this);

        img_portrait = ViewUtils.findViewById(mainView, R.id.img_portrait);
        img_portrait.setOnClickListener(this);

        img_real_tag = ViewUtils.findViewById(mainView, R.id.img_real_tag);
        text_real_name = ViewUtils.findViewById(mainView, R.id.text_real_name);
        text_nickname = ViewUtils.findViewById(mainView, R.id.text_nickname);
        text_phone = ViewUtils.findViewById(mainView, R.id.text_phone);


        LinearLayout ll_open_ticket = ViewUtils.findViewById(mainView, R.id.ll_open_ticket);
        ll_open_ticket.setOnClickListener(this);
//        if (PublicCache.loginSupplier != null) {
        ll_open_ticket.setVisibility(View.GONE);
//        }

        LinearLayout ll_change_psw = ViewUtils.findViewById(mainView, R.id.ll_change_psw);
        ll_change_psw.setOnClickListener(this);

        LinearLayout ll_new_product = ViewUtils.findViewById(mainView, R.id.ll_new_product);
        ll_new_product.setOnClickListener(this);

        LinearLayout ll_mean_post = ViewUtils.findViewById(mainView, R.id.ll_mean_post);
        ll_mean_post.setOnClickListener(this);

        LinearLayout ll_service_phone = ViewUtils.findViewById(mainView, R.id.ll_service_phone);
        ll_service_phone.setOnClickListener(this);

        LinearLayout ll_real_name = ViewUtils.findViewById(mainView, R.id.ll_real_name);
        ll_real_name.setOnClickListener(this);

        txt_service_phone = ViewUtils.findViewById(mainView, R.id.txt_service_phone);
        txt_service_phone.setText(PhoneUtils.getPhoneService());

        LinearLayout ll_clean = ViewUtils.findViewById(mainView, R.id.ll_clean);
        ll_clean.setOnClickListener(this);
        clean = ViewUtils.findViewById(mainView, R.id.clean);
        try {
            clean.setText("当前缓存" + CacheUtils.getTotalCacheSize(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        View bottomView = ViewUtils.getLayoutView(this, R.layout.part_bottom_setting_login_out);

        body_bottom.addView(bottomView);
        body_bottom.setVisibility(View.VISIBLE);
        body_bottom.setBackgroundResource(R.color.white);

        login_out = ViewUtils.findViewById(bottomView, R.id.login_out);
        if (LoginMethod.notLoginChecked()) body_bottom.setVisibility(View.GONE);
        login_out.setOnClickListener(this);
        setViewBackColor(login_out);

        LinearLayout ll_about_us = ViewUtils.findViewById(mainView, R.id.ll_about_us);
        ll_about_us.setOnClickListener(this);

         ll_phone = ViewUtils.findViewById(mainView, R.id.ll_phone);
         ll_phone.setOnClickListener(this);

        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);

        imageDescription = HEAD_PORTRAIT_UPLOAD;

        map = new HashMap<>();

        imageParamsKey = "headUrl";

        if (PublicCache.loginPurchase != null)
        map.put("entityId", PublicCache.loginPurchase.getLoginUserId());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initData() {
        if (PublicCache.loginPurchase != null) {
            int en;
            en = PublicCache.loginPurchase.getLoginUserId();
            onStartLoading();//flag //0-刷新门店信息 1-获取当前登陆用户信息
            getRequestPresenter().customer_refreshInfo(en,1,PublicCache.loginPurchase.getLoginUserId(), new RequestCallback<MyselftUpdateP>(getBaseActivity()) {
                @Override
                public void onSuc(MyselftUpdateP event) {
                    bean=event.getData();
                    if (bean != null) {
                        initViewDate();
                        initPersonInfo();
                    }

                }

                @Override
                public void onFailed(int myselftUpdateP, String msg) {

                }
            });
        }
    }

    private void initViewDate() {
            if (bean.getHeadUrl() != null) {
                headUrl = bean.getHeadUrl();
                if (!TextUtils.isEmpty(headUrl)) {
                    ImageLoaderUtils.loadImage(img_portrait, headUrl, false);
                }
            }
           /* if (bean.getFlag() != 1) {
                //子账号
                //ll_phone.setVisibility(View.GONE);
                ll_phone.setOnClickListener(null);
            } else {
                //主账号
                // ll_phone.setVisibility(View.VISIBLE);
                ll_phone.setOnClickListener(this);
            }*/
    }

    private void initPersonInfo(){
        if (bean != null) {
            cardno = bean.getIsAuth();
            if (cardno.equals("0")) {
                img_real_tag.setImageResource(R.mipmap.icon_gray_unreal);
            } else {
                img_real_tag.setImageResource(R.mipmap.icon_orange_real);
            }
            text_real_name.setText(bean.getRealname());
            text_nickname.setText(bean.getAlias());
            text_phone.setText(bean.getPhoneNumber());
        }



    }
    //选择提现银行卡入口
    @Subscribe(sticky = true)
    public void onEvent(LoginOutEvent event) {
        //ben==true,点击事件变为 设置默认银行卡
        EventBus.getDefault().removeStickyEvent(event);
        body_bottom.setVisibility(View.GONE);
    }
    //实名认证完成后通知
    @Subscribe(sticky = true)
    public void onEvent(PersonInfoChangeEvent event) {
        //ben==true,点击事件变为 设置默认银行卡
        EventBus.getDefault().removeStickyEvent(event);
        bean=event.getBean();
        if (bean!=null)
            initPersonInfo();
    }
    @Override
    public void takeSuccess(TResult result) {
        String path = result.getImage().getCompressPath();
        if (TextUtils.isEmpty(path)) return;
        String imageName = UIUtils.getFileNames(path);
        bitmap = BitmapUtil.getSmallBitmap(path);
        if (bitmap == null) return;
        headUrl = null;

        ImageLoaderUtils.loadImage(img_portrait, path,false);
        getRequestPresenter().upload(imageName, BitmapUtil.bitmapTobyte(bitmap, true), new Callback<ImageUpload>() {
            @Override
            public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                if (response.body() != null) {
                    headUrl = response.body().getData();
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
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.img_portrait:
                String fileName = System.currentTimeMillis() + ".jpg";
                Uri imageUri = Uri.fromFile(IOUtils.createFile(Config.imageSaveDir, fileName));
                if (takePhotoPopupWindow == null) {
                    takePhotoPopupWindow = new TakePhotoPopupWindow(view) {
                        @Override
                        public void goCamera() {
                            TakePhotoUtils.getInstance().setCrop(true).setImageUri(imageUri).openCamera(getTakePhoto());
                        }

                        @Override
                        public void goAlbum() {
                            TakePhotoUtils.getInstance().setCrop(true).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 1, true);
                        }
                    };
                    takePhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                } else {
                    if (!takePhotoPopupWindow.isShowing()) {
                        takePhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0 );
                    }

                }

                break;
            case R.id.ll_real_name:
                if (cardno.equals("0")) {
                    intent = new Intent(this, RealNamePurchaserAuthenticationActivity.class);
                    intent.putExtra("bean",bean);
                    startActivity(intent);
                } else {
                    intent =new Intent(this, IdentityManageActivity.class);
                    intent.putExtra("bean",bean);
                    startActivity(intent);
                }
                break;

            case R.id.ll_phone:
                //手机换绑
                Intent intent1 = new Intent(getBaseActivity(), PhoneReBindFirstActivity.class);
                intent1.putExtra("bean",bean);
                startActivity(intent1);
                break;

            case R.id.ll_news:
                intent = new Intent(getBaseActivity(), SelfNewsActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_change_psw:
                if (LoginMethod.notLoginChecked()) {
                    LoginMethod.getInstance(this).toChooseLoginActivity();
                    return;
                }
                intent = new Intent(getBaseActivity(), UpdatePassWordActivity.class);
                intent.putExtra("bean",bean);
                startActivity(intent);
                break;
            case R.id.ll_open_ticket:
//                intent = new Intent(getBaseActivity(), TicketStatusActivity.class);
//                startActivity(intent);
                break;
            case R.id.ll_new_product:
                if (LoginMethod.notLoginChecked()) {
                    LoginMethod.getInstance(this).toChooseLoginActivity();
                    return;
                }
                intent = new Intent(getBaseActivity(), GoodsNeedActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_mean_post:
                if (LoginMethod.notLoginChecked()) {
                    LoginMethod.getInstance(this).toChooseLoginActivity();
                    return;
                }
                intent = new Intent(getBaseActivity(), MeaningPostActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_service_phone:
                SystemUtils.callPhone(getBaseActivity(), PhoneUtils.getPhoneService());
                break;
            case R.id.ll_clean:
                clean.setText("当前缓存" + "0k");
                ThreadManager.getShortPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        CacheUtils.clearAllCache(getApplicationContext());
                        Glide.get(getApplicationContext()).clearDiskCache();
                    }
                });
                UIUtils.showToastSafesShort("缓存已清理");
                break;

            case R.id.ll_about_us:
                intent = new Intent(getBaseActivity(), AboutMyselfActivity.class);
                startActivity(intent);
                break;

            case R.id.login_out:
                //TODO:点击退出设置界面，返回到我的中心出现退出模式
                UIUtils.showToastSafesShort("账号已退出");
                PublicCache.login_mode = PURCHASER;
                LoginMethod.loginOut();
                this.finish();

                break;
            case R.id.ll_nickname:
                initDialog();//tttt
                break;
        }

    }

    private void initDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(getBaseActivity(), R.layout.dialog_real_name_purchaser_edit, null);


        EditText edit_content=view.findViewById(R.id.edit_content);
        TextView text_left=view.findViewById(R.id.text_left);
        TextView text_right=view.findViewById(R.id.text_right);

        TextView text_title=view.findViewById(R.id.text_title);
        text_title.setText("修改昵称");
        TextView text_name=view.findViewById(R.id.text_name);
        text_name.setText("昵称");

        builder.setView(view);
        builder.setCancelable(false);
//        TextView title = (TextView) view.findViewById(R.id.title);//设置标题
//        Button btn_comfirm = (Button) view.findViewById(R.id.real_name_authentication_dialog_button);//确定按钮
        final AlertDialog dialog = builder.create();
//                取消或确定按钮监听事件处理
        text_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        text_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick=edit_content.getText().toString().trim();

                if (!TextUtils.isEmpty(nick)) {
                    dialog.dismiss();
                    text_nickname.setText(nick);
                    Map<String ,Object> map=new HashMap<>();
                    map.put("alias",nick);
                    if (bean.getRole() == 0){//主账号
                        map.put("entityId", PublicCache.loginPurchase.getEntityId());
                     }else{
                        map.put("entityId", PublicCache.loginPurchase.getSubUserId());
                    }

                    RequestPresenter.getInstance().updateCustomer(map, new RequestCallback<UpdateCustomerBean>() {
                        @Override
                        protected void onSuc(UpdateCustomerBean body) {
                            UIUtils.showToastSafesShort("昵称修改成功");
                        }
                    });
                }else {
                    UIUtils.showToastSafesShort("请填写昵称");
                }

            }
        });
        dialog.show();
    }

    //上传图片信息
    private void returnResult() {
        if (TextUtils.isEmpty(headUrl)) return;
        map.put(imageParamsKey, headUrl);
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
                        ok.setData(headUrl);
                        ok.setMsg(imageDescription);
                        LoginPurchaseBean loginPurchaseBean = SugarRecord.first(LoginPurchaseBean.class);
                        if (loginPurchaseBean != null) {
                            switch (imageDescription) {
                                case HEAD_PORTRAIT_UPLOAD:
                                    loginPurchaseBean.setHeadUrl(headUrl);
                                    PublicCache.loginPurchase.setHeadUrl(headUrl);
                                    break;

                            }
                            loginPurchaseBean.update();
                        }
                        EventBus.getDefault().post(ok);
                    }

                    @Override
                    public void onFailed(int imageUploadOk, String msg) {

                    }
                });


                break;


        }

    }


}
