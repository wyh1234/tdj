package cn.com.taodaji.ui.activity.shopManagement;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.common.Config;
import com.base.glide.ImageLoaderUtils;
import com.base.retrofit.RequestCallback;
import com.base.takephoto.model.TResult;
import com.base.utils.BitmapUtil;
import com.base.utils.IOUtils;
import com.base.utils.UIUtils;
import com.base.utils.UserNameUtill;
import com.base.utils.ViewUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ImageUpload;
import cn.com.taodaji.model.entity.Register;
import cn.com.taodaji.model.entity.ShopTypeBean;
import cn.com.taodaji.model.entity.TimeHourBean;
import cn.com.taodaji.model.event.ShopTypeSelectListEvent;
import cn.com.taodaji.ui.activity.login.CityActivity;
import cn.com.taodaji.ui.activity.login.RegisterPurchaserShopTypeActivity;
import cn.com.taodaji.ui.activity.login.ShopAddressActivity;
import cn.com.taodaji.ui.ppw.TakePhotoPopupWindow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.TakePhotosActivity;
import tools.extend.TakePhotoUtils;

public class CreateStoreActivity extends TakePhotosActivity implements View.OnClickListener {
    private ImageView img_shop_picture;
    private Button register_OK;
    private LinearLayout line_get_time;
    private View mainView;

    private String image_url = null;
    private boolean isCallback = false;
    private Bitmap bitmap;

    private TextView text_shop_get_time, text_shop_type, text_shop_address;

    private OptionsPickerView pvCustomOptions;

    private ArrayList<TimeHourBean> timeList = new ArrayList<>();

    private ArrayList<ArrayList<TimeHourBean>> options2Items = new ArrayList<>();

    private List<ShopTypeBean> selectedList;

    private EditText edit_shop_name, edit_shop_invite, leaderName,leaderPhone,houseNumber;

    private Map<String, Object> values_map;

    private String start_time = "";
    private String end_time = "";
    private String fenceId="";
    private int cityId=0;

    private int start_select=7;
    private int end_select=0;

    @Override
    public void titleSetting(Toolbar toolbar){
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("新建门店");
        right_icon.setVisibility(View.VISIBLE);
        right_icon.setImageResource(R.mipmap.shop_address_pull);
        right_onclick_line.setOnClickListener(this);
        right_onclick_line.setVisibility(View.INVISIBLE);
        right_textView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initMainView(){
        mainView = getLayoutView(R.layout.activity_create_store);
        body_other.addView(mainView);

        register_OK = ViewUtils.findViewById(this, R.id.register_OK);
        register_OK.setOnClickListener(this);


        text_shop_get_time = ViewUtils.findViewById(this, R.id.text_shop_get_time);
        text_shop_type = ViewUtils.findViewById(this, R.id.text_shop_type);
        text_shop_address = ViewUtils.findViewById(this, R.id.text_shop_address);

        edit_shop_name = ViewUtils.findViewById(this, R.id.edit_shop_name);
        edit_shop_invite = ViewUtils.findViewById(this, R.id.edit_shop_invite);

        img_shop_picture = ViewUtils.findViewById(this, R.id.img_shop_picture);
        img_shop_picture.setOnClickListener(this);

        line_get_time = ViewUtils.findViewById(this, R.id.line_get_time);
        line_get_time.setOnClickListener(this);

        leaderName = ViewUtils.findViewById(this,R.id.edit_leader_name);
        leaderPhone = ViewUtils.findViewById(this,R.id.edit_leader_phone);

        houseNumber = ViewUtils.findViewById(this,R.id.edit_house_number);

        ViewUtils.findViewById(this, R.id.line_shop_type).setOnClickListener(this);
        ViewUtils.findViewById(this, R.id.line_address).setOnClickListener(this);

        getOptionData();
        initCustomOptionPicker();
    }



    @Override
    public void takeSuccess(TResult result) {
        String path = result.getImage().getCompressPath();
        if (TextUtils.isEmpty(path)) return;
        String imageName = UIUtils.getFileNames(path);
        bitmap = BitmapUtil.getSmallBitmap(path);
        if (bitmap == null) return;
        image_url = null;

        ImageLoaderUtils.loadImage(img_shop_picture, path);


        getRequestPresenter().imageUpload(imageName, BitmapUtil.bitmapTobyte(bitmap, true), new Callback<ImageUpload>() {
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


    //上传图片信息
    private void returnResult() {
        //if (TextUtils.isEmpty(image_url)) return;

        /* 获取页面的值*/
        values_map = new HashMap<>();

        values_map.put("hotelName", edit_shop_name.getText().toString());  //0-注册，1-门店创建
        values_map.put("hotelAddress", text_shop_address.getText().toString());  //0-注册，1-门店创建
        values_map.put("realname", leaderName.getText().toString().trim());
        values_map.put("account", leaderPhone.getText().toString().trim());
        values_map.put("phoneNumber",leaderPhone.getText().toString().trim());
        values_map.put("resource", 1);  //0-注册，1-门店创建
        //values_map.put("operator", );// operator; //操作人,resource为1时 operator有值
        String hotelTypeId="";
        if (selectedList!= null) {
            for (int i = 0; i <selectedList.size() ; i++) {
                if (i == selectedList.size()-1) {
                    hotelTypeId=hotelTypeId+selectedList.get(i).getHotelTypeId();
                }else {
                    hotelTypeId=hotelTypeId+selectedList.get(i).getHotelTypeId()+",";
                }

            }
        }

        values_map.put("hotelTypeId",hotelTypeId);  //门店类型id

        if (image_url!=null) {
            values_map.put("imageUrl", image_url);  //门店形象照
        }

        values_map.put("deliveredTime", start_time);  //门店到货开始时间
        values_map.put("deliveredTimeEnd", end_time);  //门店到货结束时间
        values_map.put("fenceGid", fenceId);  //商圈id
        values_map.put("parentId",PublicCache.loginPurchase.getLoginUserId());
        values_map.put("operator",PublicCache.loginPurchase.getLoginUserId());
        values_map.put("streetNumber",houseNumber.getText().toString().trim());

        if (!TextUtils.isEmpty(edit_shop_invite.getText().toString().trim())) {
            values_map.put("verifyCode", edit_shop_invite.getText().toString().trim());  //邀请码
        }

        values_map.put("isG", -1);  //门店形象照

        loading("正在创建...");
        getRequestPresenter().customer_registerData(values_map, cityId, new RequestCallback<Register>(this) {
            @Override
            public void onSuc(Register body) {
               loadingDimss();
               finish();
            }

            @Override
            public void onFailed(int register, String msg) {
                loadingDimss();
                UIUtils.showToastSafesShort(msg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_shop_type: {
                RegisterPurchaserShopTypeActivity.startActivity(this,selectedList);
                break;
            }  case R.id.line_address: {
                Intent intent1 = new Intent(this, ShopAddressActivity.class);
                startActivityForResult(intent1,2);
                break;
            }
            case R.id.line_get_time: {
                if (pvCustomOptions != null) {
                    pvCustomOptions.show(); //弹出自定义条件选择器
                }
                break;
            }
            case R.id.img_shop_picture: {
                TakePhotoPopupWindow photoPopupWindow = new TakePhotoPopupWindow(img_shop_picture) {
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
                photoPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                break;
            }
            case R.id.register_OK: {//text_shop_get_time, text_shop_type, text_shop_address;  edit_shop_name
                if (TextUtils.isEmpty(edit_shop_name.getText().toString())) {
                    UIUtils.showToastSafesShort("门店名称不可为空");
                    return;
                }
                if (TextUtils.isEmpty(leaderName.getText().toString())) {
                    UIUtils.showToastSafesShort("负责人姓名不可为空");
                    return;
                }
                if (!UserNameUtill.isPhoneNO(leaderPhone.getText().toString())) {
                    UIUtils.showToastSafesShort("手机号码格式错误");
                    return;
                }
                if (TextUtils.isEmpty(text_shop_address.getText().toString())) {
                    UIUtils.showToastSafesShort("门店地址不可为空");
                    return;
                }
                if (TextUtils.isEmpty(text_shop_get_time.getText().toString())) {
                    UIUtils.showToastSafesShort("请选择到货时间");
                    return;
                }
//                if (bitmap == null) {
//                    UIUtils.showToastSafesShort("请选择一张图片");
//                    return;
//                }
//                if (image_url == null) {
//                    isCallback = true;
//                    loading("图片正在上传...");
//                } else {
                    returnResult();
//                }


                break;
            }
            case R.id.img_back: {
                finish();
                break;
            }
            case R.id.right_onclick_line: {
                Intent intent = new Intent(CreateStoreActivity.this, CityActivity.class);
                intent.putExtra("data", PublicCache.findByIsActive);
                startActivityForResult(intent,3);
                break;
            }
        }
    }


    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */


        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = timeList.get(options1).getPickerViewText() + "——" + options2Items.get(options1).get(option2).getPickerViewText();
                text_shop_get_time.setText(tx);

                start_time = timeList.get(options1).getPickerViewText();
                end_time = options2Items.get(options1).get(option2).getPickerViewText();
            }
        })
                .setDividerColor(UIUtils.getColor(R.color.transparent)).setLayoutRes(R.layout.part_register_purchaser_second_time_wheel, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView text_cancel = (TextView) v.findViewById(R.id.text_cancel);
                        TextView text_ok = (TextView) v.findViewById(R.id.text_ok);

                        ImageView ivCancel = (ImageView) v.findViewById(R.id.img_cancel);

                        text_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });

                        text_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                }).setSelectOptions(start_select, end_select).isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isDialog(true)
                .setOutSideCancelable(false)
                .build();

        // pvCustomOptions.setPicker(timeList,timeList);//添加数据
        pvCustomOptions.setPicker(timeList, options2Items);
        // pvCustomOptions.setSelectOptions(6,6);
        //  pvCustomOptions.setSelectOptions(3,4);
        text_shop_get_time.setText(timeList.get(start_select).getPickerViewText() + "——" + options2Items.get(start_select).get(end_select).getPickerViewText());

        start_time = timeList.get(start_select).getPickerViewText();
        end_time = options2Items.get(start_select).get(end_select).getPickerViewText();

        Dialog mDialog = pvCustomOptions.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvCustomOptions.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }

    }

    private void getOptionData() {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */

        ArrayList<TimeHourBean> testList = new ArrayList<>();
        for (int i = 5; i <= 12; i++) {
            if (i > 5) {
                testList.add(new TimeHourBean(i, i + ":00"));
            }
            if (i < 12) {

                if (i > 5) {
                    timeList.add(new TimeHourBean(i, i + ":00"));
                    testList.add(new TimeHourBean(i, i + ":30"));
                }

                if (i < 11) {
                    timeList.add(new TimeHourBean(i, i + ":30"));
                }
            }
        }

        for (int i = 0; i < timeList.size(); i++) {
            if (testList.size() > 0) {
                testList.remove(0);
            }
            ArrayList<TimeHourBean> indexList = new ArrayList<>();
            indexList.addAll(testList);
            options2Items.add(indexList);
        }


        /*--------数据源添加完毕---------*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(ShopTypeSelectListEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        if (event .getFlag()!=0) {
            return;
        }
        selectedList = event.getResultList();
        String str = "";
        for (int i = 0; i < selectedList.size(); i++) {

            if (i == selectedList.size() - 1 ) {
                str = str + selectedList.get(i).getName() ;
            } else {
                str = str + selectedList.get(i).getName()+ "、";
            }
        }
        text_shop_type.setText(str);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (data != null&&data.getExtras().get("result")!=null) {
                String result = data.getExtras().getString("result","");//得到新Activity 关闭后返回的数据
                text_shop_address.setText(result);
                fenceId = data.getStringExtra("fenceGid");
                cityId = data.getIntExtra("id",0);
            }
        }
    }
}
