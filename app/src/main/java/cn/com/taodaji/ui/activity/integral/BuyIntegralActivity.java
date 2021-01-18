package cn.com.taodaji.ui.activity.integral;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.BaseIntegral;
import cn.com.taodaji.model.entity.BuyIntegral;
import cn.com.taodaji.model.entity.CartQuantity;
import cn.com.taodaji.model.entity.IntegralOrder;
import cn.com.taodaji.model.entity.QueryIntergral;
import cn.com.taodaji.ui.activity.integral.adapter.BaseRecyclerViewAdapter;
import cn.com.taodaji.ui.activity.integral.adapter.BuyIntegralAdapter;
import cn.com.taodaji.ui.activity.integral.adapter.IntegralShopCartAdapter;
import cn.com.taodaji.ui.activity.integral.popuwindow.CheckSuccessPopupWindow;
import cn.com.taodaji.ui.activity.integral.popuwindow.MyTextChangedListener;
import cn.com.taodaji.ui.activity.integral.tools.ListUtils;
import cn.com.taodaji.ui.activity.integral.tools.ShowLoadingDialog;
import cn.com.taodaji.viewModel.adapter.SimpleMyselfFunctionAdapter;
import io.reactivex.functions.Consumer;
import tools.activity.DataBaseActivity;
import tools.statusbar.Eyes;

public class BuyIntegralActivity extends DataBaseActivity implements BaseRecyclerViewAdapter.OnItemClickListener,MyTextChangedListener.ViewListener {
    @BindView(R.id.intergral_toolbar)
    Toolbar intergral_toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.integral_list)
    RecyclerView integral_list;
    @BindView(R.id.tv_txl)
    ImageView tv_txl;
    @BindView(R.id.ed_phone)
    EditText ed_phone;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.tv_err)
    TextView tv_err;

    @BindView(R.id.btn)
    Button btn;
    private List<QueryIntergral.QueryIntergralData> list = new ArrayList<>();
    private BuyIntegralAdapter buyIntegralAdapter;
    private RxPermissions rxPermissions;
    private String  guid;
    private boolean b;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @OnClick({R.id.tv_txl,R.id.btn_back,R.id.btn,R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_txl:
                rxPermissions.request(Manifest.permission.READ_CONTACTS).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(intent, 0);

                        }
                    }
                });



                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn:
                if (ListUtils.isNullOrZeroLenght(getGuid())){
                    UIUtils.showToastSafe("请选择充值积分套餐");
                    return;
                }
                if (ListUtils.isNullOrZeroLenght(ed_phone.getText().toString())){
                    UIUtils.showToastSafe("请输入正确的充值账户");
                    return;
                }
                if (ed_phone.getText().toString().length()!=11){
                    UIUtils.showToastSafe("账户输入有误");
                    return;
                }
                if (!ListUtils.isTel(ed_phone.getText().toString())){
                    UIUtils.showToastSafe("账户输入有误");
                    return;
                }
                if (!b){
                    UIUtils.showToastSafe("该手机号非平台注册的账号");
                    return;
                }
                buyIntegral();

                break;
            case R.id.tv_right:
                Intent intent1 = new Intent(this, WebViewActivity.class);
                intent1.putExtra("url",PublicCache.getROOT_URL().get(2)+"tdj-user/user/integral/buyRulePage");
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.buy_itegral_layout);
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        Eyes.setStatusBarColor(this, UIUtils.getColor(this, R.color.white));
        intergral_toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        tv_title.setText("购买积分");
        tv_title.setTextColor(getResources().getColor(R.color.gray_66));
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(R.mipmap.left_arrow_gary);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(getResources().getColor(R.color.gray_66));
        tv_right.setText("规则");
        rxPermissions = new RxPermissions(this);
        integral_list.setLayoutManager(new GridLayoutManager(this, 3));
        buyIntegralAdapter = new BuyIntegralAdapter(this, list);
        buyIntegralAdapter.setOnItemClickListener(this);
        integral_list.setAdapter(buyIntegralAdapter);


        MyTextChangedListener myTextChangedListener = new MyTextChangedListener(this);
        ed_phone.addTextChangedListener(myTextChangedListener);
        myTextChangedListener.setOnItemClickListener(this);
        ed_phone.setText(PublicCache.loginPurchase.getPhoneNumber());

    }

    @Override
    protected void initData() {
        super.initData();
        query_intergral_list();
    }

    public void buyIntegral(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("userId",  PublicCache.loginPurchase.getEmpRole()==0?PublicCache.loginPurchase.getEntityId():PublicCache.loginPurchase.getLoginUserId());
        map.put("orderType", "0");
        map.put("shippingFee", "");
        map.put("orderAddressId", "");
        map.put("remark", ed_phone.getText().toString().replace(" ",""));
        map.put("distributionType", "1");
        map.put("goodsId", getGuid());

        getIntegralRequestPresenter().buyIntegral(map, new RequestCallback<IntegralOrder>(this) {
            @Override
            public void onSuc(IntegralOrder body) {
                ShowLoadingDialog.close();
                Intent intent = new Intent(BuyIntegralActivity.this, IntegralShopPayActivity.class);
                intent.putExtra("BuyIntegral","BuyIntegral");
                intent.putExtra("IntegralOrder",body.getData());
                intent.putExtra("IntegralOrder",body.getData());
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();

            }
        });
    }
    public void query_intergral_list(){
        ShowLoadingDialog.showLoadingDialog(this);
        Map<String,Object> map=new HashMap<>();
        map.put("packageType", "1");
        getIntegralRequestPresenter().query_intergral_list(map, new RequestCallback<QueryIntergral>(this) {
            @Override
            public void onSuc(QueryIntergral body) {
                ShowLoadingDialog.close();
                list.addAll(body.getData());
                buyIntegralAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {
                ShowLoadingDialog.close();

            }
        });
    }

    public void checkUser(){
        Map<String,Object> map=new HashMap<>();
        map.put("userCode", ed_phone.getText().toString().replace(" ",""));
        getIntegralRequestPresenter().checkUser(map, new RequestCallback<BaseIntegral>(this) {
            @Override
            public void onSuc(BaseIntegral body) {
                if (body.getData()==0){
                    b=false;
                    tv_err.setText(body.getMsg());
                }else {
                    b=true;
                }

            }

            @Override
            public void onFailed(int goodsCategoryList_resu, String msg) {

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 0:
                if (data == null) {
                    return;
                }

                //处理返回的data,获取选择的联系人信息**
                Uri uri = data.getData();
                String[] contacts = this.getPhoneContacts(uri);
                LogUtils.i(contacts[0]);
                LogUtils.i(contacts[1]);
                ed_phone.setText(contacts[1]);
                ed_phone.setSelection(ed_phone.getText().toString().length());
                checkUser();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String[] getPhoneContacts(Uri uri) {
        String[] contact = new String[2];
        // **//得到ContentResolver对象**
        ContentResolver cr = getContentResolver();
        // **//取得电话本中开始一项的光标**
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            // **//取得联系人姓名**
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0] = cursor.getString(nameFieldColumnIndex);
            // **//取得电话号码**
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if (phone != null) {
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        } else {
            return null;
        }
        return contact;
    }

    @Override
    public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {
        for (int i=0;i<list.size();i++){
            list.get(i).setB(false);
        }
        list.get(position).setB(true);
        setGuid(list.get(position).getGuid());
        buyIntegralAdapter.notifyDataSetChanged();
    }

    @Override
    public void viewshow() {
        checkUser();
        iv_right.setImageResource(R.mipmap.duigou_bg);
    }

    @Override
    public void viewhide() {
        iv_right.setImageResource(R.mipmap.error_bg);
    }


}
