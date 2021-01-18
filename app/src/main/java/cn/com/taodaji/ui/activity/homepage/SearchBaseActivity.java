package cn.com.taodaji.ui.activity.homepage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.SimpleSearchResultAdapter;
import cn.com.taodaji.model.entity.ProductFindName;
import cn.com.taodaji.model.entity.StoreFindName;
import com.base.retrofit.RequestCallback;
import retrofit2.Call;
import tools.activity.SimpleToolbarActivity;
import com.base.widget.my_edittext.UserNameEditText;
import com.base.utils.ADInfo;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public abstract class SearchBaseActivity extends SimpleToolbarActivity implements TextWatcher {

    public UserNameEditText search_edit;
    public TextView right_textView, search_heard;
    public SimpleSearchResultAdapter simpleSearchResultAdapter;
    private String searchText;//当前搜索是搜商品，还是店铺


    public static int his_position = 0;//历史记录的位置

    private static final String HistoricalSearch_Name = "HistoricalSearch_Name";//历史搜索保存文件名
    public SharedPreferences HIStorical_Prefrs;

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    @Override
    protected Toolbar initToolbar() {
        View view = ViewUtils.getFragmentView(title, R.layout.toolbar_search_edit);
        title.addView(view);
        Toolbar toolbar = ViewUtils.findViewById(view, R.id.toolbar);
        search_edit = ViewUtils.findViewById(toolbar, R.id.search_edit);
        right_textView = ViewUtils.findViewById(view, R.id.right_text);
        search_heard = ViewUtils.findViewById(view, R.id.search_heard);
        return toolbar;
    }

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor(R.color.orange_yellow_ff7d01);
        setToolBarColor(R.color.white);
        toolbar.setNavigationIcon(R.mipmap.left_arrow_gary);//设置导航栏图标
        toolbar.setContentInsetsRelative(0,0);
        search_edit.addTextChangedListener(this);

        right_textView.setText("搜索");
        right_textView.setTextColor(UIUtils.getColor(R.color.gray_66));
        right_textView.setTextSize(22);
        simpleSearchResultAdapter = new SimpleSearchResultAdapter();
        HIStorical_Prefrs = getSharedPreferences(HistoricalSearch_Name, Context.MODE_PRIVATE);
    }


    private String searchName = "";//搜索的名字
    private Call<ProductFindName> productFindNameCall;
    private Call<StoreFindName> storeFindNameCall;


    //获取搜索列表数据
    public void getSearchResult(String str,int isCanteen) {
        if (TextUtils.isEmpty(str)) return;
        if (getSearchText().equals("商品")) {
            getGoodsResult(str,isCanteen);
        } else getShopResult(str);
    }


    private void getGoodsResult(String name,int isCanteen) {
        //如果本次请求包含上次请求，或者上次请求包含本次请求，则取消上次请求
        if (name.contains(searchName) || searchName.contains(name)) {
            if (productFindNameCall != null) productFindNameCall.cancel();
        }
        searchName = name;
        if (searchName.length() == 0) return;
        productFindNameCall = getRequestPresenter().getProductFindName(searchName,isCanteen);
        productFindNameCall.enqueue(new RequestCallback<ProductFindName>() {
            @Override
            public void onSuc(ProductFindName body) {
                simpleSearchResultAdapter.setItemViewType(0);
                simpleSearchResultAdapter.notifyDataSetChanged(body.getData().getItems());
            }

            @Override
            public void onFailed(int productFindName, String msg) {

            }
        });
    }

    private void getShopResult(String name) {
        //如果本次请求包含上次请求，或者上次请求包含本次请求，则取消上次请求
        if (name.contains(searchName) || searchName.contains(name)) {
            if (storeFindNameCall != null) storeFindNameCall.cancel();
        }
        searchName = name;
        if (searchName.length() == 0) return;
        storeFindNameCall = getRequestPresenter().getStoreFindName(searchName);
        storeFindNameCall.enqueue(new RequestCallback<StoreFindName>() {
            @Override
            public void onSuc(StoreFindName body) {
                simpleSearchResultAdapter.setItemViewType(1);
                simpleSearchResultAdapter.notifyDataSetChanged(body.getData().getItems());
            }

            @Override
            public void onFailed(int storeFindName, String msg) {

            }
        });
    }

    //获取本地历史记录  同时大于8时清除最后的元素
    public List<ADInfo> getHistorical() {
        return sort_histor_data(HIStorical_Prefrs.getAll());
    }

    //保存历史记录到本地
    public void saveHistorical(String str) {
        if (TextUtils.isEmpty(str)) return;
        SharedPreferences.Editor save_prefrs = HIStorical_Prefrs.edit();
        save_prefrs.putInt(str, ++his_position);
        save_prefrs.apply();
    }

    private List<ADInfo> sort_histor_data(Map<String, ?> map) {
        SparseArrayCompat<String> lis = new SparseArrayCompat<>();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            lis.put(Integer.valueOf(entry.getValue().toString()), entry.getKey());
        }
        if (lis.size() == 0) his_position = 0;
        else his_position = lis.keyAt(lis.size() - 1);
        int count = lis.size();
        List<ADInfo> ll = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (i < 8) {
                ADInfo adInfo = new ADInfo();
                adInfo.setImageName(lis.valueAt(count - i - 1));
                ll.add(adInfo);
            } else {
                SharedPreferences.Editor clear_prefrs = HIStorical_Prefrs.edit();
                clear_prefrs.remove(lis.valueAt(count - i - 1));
                clear_prefrs.apply();
            }

        }
        return ll;
    }

}
