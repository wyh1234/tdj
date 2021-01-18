package cn.com.taodaji.ui.activity.myself;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerItemDecoration;
import com.base.widget.my_edittext.UserNameEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ShopTypeBean;
import cn.com.taodaji.model.event.ShopTypeSearchListEvent;
import cn.com.taodaji.model.event.ShopTypeSelectEvent;
import cn.com.taodaji.viewModel.adapter.SearchShopTypeListAdapter;
import tools.activity.SimpleToolbarActivity;


public class ShopTypeSearchActivity extends SimpleToolbarActivity implements TextWatcher {
    public UserNameEditText search_edit;
    public TextView right_textView, search_heard;
    public SearchShopTypeListAdapter adapter;
    private String searchText;//当前搜索是搜商品，还是店铺
    private String searchName = "";//搜索的名字

    private View mainView;
    private RecyclerView search_recyclerView;//搜索结果的列表

    private List<ShopTypeBean> searchList;
    @Override
    protected Toolbar initToolbar() {
        View view = ViewUtils.getFragmentView(title, R.layout.toolbar_search_edit);
        title.addView(view);
        Toolbar toolbar = ViewUtils.findViewById(view, R.id.toolbar);
        search_edit = ViewUtils.findViewById(toolbar, R.id.search_edit);
        search_edit.setHint("输关键字速搜门店类型");
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
        adapter = new SearchShopTypeListAdapter();
        //  HIStorical_Prefrs = getSharedPreferences(HistoricalSearch_Name, Context.MODE_PRIVATE);



        right_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    @Override
    protected void initMainView() {
        mainView = getLayoutView(R.layout.activity_shop_type_search);
        body_other.addView(mainView);

        search_recyclerView = ViewUtils.findViewById(mainView, R.id.search_recyclerView);
        //搜索结果显示的view\
        search_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search_recyclerView.addItemDecoration(new DividerItemDecoration(1, R.color.gray_db));
        search_recyclerView.setAdapter(adapter);


        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    ShopTypeBean item = (ShopTypeBean) bean;
                    if (!TextUtils.isEmpty(item.getName())) {
//                        search_edit.removeTextChangedListener(ShopTypeSearchActivity.this);
//                        search_edit.setText(item.getName());
//                        search_edit.setSelection(item.getName().length());
//                        search_edit.addTextChangedListener(ShopTypeSearchActivity.this);

                        EventBus.getDefault().postSticky(new ShopTypeSelectEvent(item));

                        finish();
//                       Intent intent = new Intent(ShopTypeSearchActivity.this, RegisterPurchaserShopTypeActivity.class);
//                       startActivity(intent);
                    }
                    return true;
                }
                return false;
            }
        });




        // adapter.setList(searchList);


    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String ss = s.toString();


        if (TextUtils.isEmpty(ss)) {
            adapter.clearAll();
        }else {
            search(ss);
        }
    }

    private void search(String str){
        if (searchList==null) {
            return;
        }

        List<ShopTypeBean> tempList=new ArrayList<>();
        for (int i = 0; i <searchList.size() ; i++) {
            ShopTypeBean bean1=searchList.get(i);
            if (bean1.isSingle()) {
                if (bean1.getName().contains(str)) {
                    tempList.add(bean1);
                }
            }else {
                List<ShopTypeBean> list2=bean1.getChildren();
                for (int j = 0; j < list2.size(); j++) {
                    ShopTypeBean bean2=list2.get(j);
                    if (bean2.isSingle()) {
                        if (bean2.getName().contains(str)) {
                            tempList.add(bean2);
                        }
                    }else {
                        List<ShopTypeBean> list3=bean2.getChildren();
                        for (int k = 0; k <list3.size() ; k++) {
                            ShopTypeBean bean3=list3.get(k);
                            if (bean3.getName().contains(str)) {
                                tempList.add(bean3);
                            }
                        }
                    }
                }

            }

        }

        adapter.setList(tempList);
    }



    @Subscribe(sticky = true)
    public void onEvent(ShopTypeSearchListEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        searchList= event.getResultList();

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
}
