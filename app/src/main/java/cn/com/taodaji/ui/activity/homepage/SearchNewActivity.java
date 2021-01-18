package cn.com.taodaji.ui.activity.homepage;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.ui.activity.market.ShopDetailInformationSeachActivity;
import cn.com.taodaji.viewModel.adapter.SimpleSearchHostAdapter;
import cn.com.taodaji.model.entity.Searchhost;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.ui.ppw.PopupResultInterface;
import cn.com.taodaji.ui.ppw.SearchToolBarPopupWindow;

import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.SpacesItemDecoration;
import com.base.utils.ADInfo;
import com.base.utils.BitmapUtil;
import com.base.utils.JavaMethod;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

public class SearchNewActivity extends SearchBaseActivity implements View.OnClickListener {
    private SearchToolBarPopupWindow stp;
    private SimpleSearchHostAdapter firstView_adapter1;
    private SimpleSearchHostAdapter firstView_adapter2;
    private LinearLayout first_view;//搜索默认  第一个页面
    private LinearLayout second_view;//搜索第二个页面
    private Searchhost body;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        super.titleSetting(toolbar);
        Drawable drawable1 = BitmapUtil.getDrawable(R.mipmap.sort_gray);
        // 这一步必须要做,否则不会显示.
        drawable1.setBounds(0, 0, UIUtils.dip2px(15), UIUtils.dip2px(10));
        if (getIntent().getStringExtra("id")!=null){
            right_textView.setText("搜本店");
            search_edit.setHint("请输入要搜索的商品名称");

//            search_heard.setPadding(0, 0, 0, 0);
        }else {
            setSearchText("商品");
            search_heard.setText(getSearchText());
            search_heard.setCompoundDrawables(null, null, drawable1, null);
            search_heard.setPadding(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0);
        }
        if (getIntent().getIntExtra("isCanteen",0)==1){
            search_edit.setHint("请输入要搜索的商品名称");
        }
        search_heard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getIntExtra("isCanteen",0)==1){
                    return;
                }
                if (stp == null) {
                    stp = new SearchToolBarPopupWindow(getBaseActivity(), view.getWidth());
                    stp.setResultInterface(new PopupResultInterface() {
                        @Override
                        public void setResult(Object object) {
                            setSearchText(object.toString());
                            search_heard.setText(getSearchText());
                            String ss = search_edit.getText().toString();
                            if (ss.length() == 0) return;
                            getSearchResult(ss,getIntent().getIntExtra("isCanteen",0));
                        }
                    });
                }
                if (!stp.isShowing()) stp.showAsDropDown(view);
            }
        });
        right_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = search_edit.getText().toString().trim();
                onClicked(str);
            }
        });
    }


    private void onClicked(String str) {
        Intent intent = null;
        if (getIntent().getStringExtra("id")!=null){
//            ShopDetailInformationSeachActivity.startActivity(Integer.parseInt(getIntent().getStringExtra("id")));
            intent = new Intent(this, ShopDetailInformationSeachActivity.class);
            intent.putExtra("data", str);
            intent.putExtra("id", getIntent().getStringExtra("id"));
            intent.putExtra("searchhost", getIntent().getSerializableExtra("searchhost"));
            startActivity(intent);
         /*   intent = new Intent(this, ShopDetailInformationSeachActivity.class);
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("data", str);
                startActivity(intent);

            }*/
        }else {
            if (getSearchText().equals("商品")) {
                saveHistorical(str);



                intent = new Intent(this, SearchGoodsActivity.class);
                    intent.putExtra("isCanteen", getIntent().getIntExtra("isCanteen",0));
            } else {
                intent = new Intent(this, SearchShopActivity.class);
            }
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra("data", str);
                startActivity(intent);
            }
        }

    }


    @Override
    protected void initMainView() {
        View view = ViewUtils.getLayoutViewMatch(this, R.layout.activity_search_main);
        body_scroll.addView(view);

        simpleSearchResultAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    Object obj = JavaMethod.getValueFromBean(bean, "name");
                    if (obj != null) {
                        onClicked(obj.toString());
                    }
                    return true;
                }
                return false;
            }
        });

        firstView_init(view);
        secondView_init(view);
    }


    @Override
    protected void initData() {
        List<ADInfo> ll = getHistorical();
        firstView_adapter1.notifyDataSetChanged(ll);
        getHostsearchData();
    }

    private void getHostsearchData() {
        if (getIntent().getStringExtra("id")!=null){
            List<ADInfo> list = new ArrayList<>();

            if ((Searchhost) getIntent().getSerializableExtra("searchhost")!=null){
                body= (Searchhost) (Searchhost) getIntent().getSerializableExtra("searchhost");

                for (Searchhost.DataBean db : body.getData()) {
                    ADInfo adInfo = new ADInfo();
                    adInfo.setImageName(db.getName());
                    adInfo.setImageContent("" + db.getHit_number());
                    list.add(adInfo);
                }

                firstView_adapter2.notifyDataSetChanged(list);
            }


        }else {
            getRequestPresenter().getSearchhost(new RequestCallback<Searchhost>() {
                @Override
                public void onSuc(Searchhost body) {
                    List<ADInfo> list = new ArrayList<>();
                    for (Searchhost.DataBean db : body.getData()) {
                        ADInfo adInfo = new ADInfo();
                        adInfo.setImageName(db.getName());
                        adInfo.setImageContent("" + db.getHit_number());
                        list.add(adInfo);
                    }
                    firstView_adapter2.notifyDataSetChanged(list);
                }

                @Override
                public void onFailed(int searchhost, String msg) {
                }
            });
        }

    }


    private void firstView_init(View main_view) {
        first_view = ViewUtils.findViewById(main_view, R.id.first_view);
        LinearLayout historical_search_ll = ViewUtils.findViewById(main_view, R.id.historical_search_ll);
        LinearLayout historical_search = ViewUtils.findViewById(main_view, R.id.historical_search);
        LinearLayout host_search = ViewUtils.findViewById(main_view, R.id.host_search);

        TextView tv_host_search = ViewUtils.findViewById(main_view, R.id.tv_host_search);
        if (getIntent().getStringExtra("id")!=null){
            historical_search_ll.setVisibility(View.GONE);
            historical_search.setVisibility(View.GONE);
            tv_host_search.setText("本店热搜");
        }

        TextView search_clear = ViewUtils.findViewById(main_view, R.id.search_clear);
        search_clear.setOnClickListener(this);

        RecyclerView firstView_recyclerView1 = getLayoutView(R.layout.t_recyclerview);

        RecyclerView firstView_recyclerView2 = getLayoutView(R.layout.t_recyclerview);

        historical_search.addView(firstView_recyclerView1);
        host_search.addView(firstView_recyclerView2);

        //设置  RecyclerView.LayoutManager
        firstView_recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL));
        firstView_recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL));
        //设置adapter
        firstView_adapter1 = new SimpleSearchHostAdapter();
        firstView_adapter1.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    onFirstViewItemClick((ADInfo) bean);
                    return true;
                }
                return false;
            }
        });
        firstView_recyclerView1.setAdapter(firstView_adapter1);

        firstView_adapter2 = new SimpleSearchHostAdapter();
        firstView_adapter2.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    onFirstViewItemClick((ADInfo) bean);
                    return true;
                }
                return false;
            }
        });

        firstView_recyclerView2.setAdapter(firstView_adapter2);

        //设置项目间隔
        SpacesItemDecoration sp = new SpacesItemDecoration(15);
        firstView_recyclerView1.addItemDecoration(sp);
        firstView_recyclerView2.addItemDecoration(sp);

    }

    private void secondView_init(View main_view) {
        second_view = ViewUtils.findViewById(main_view, R.id.second_view);
        RecyclerView secondView_recyclerView = getLayoutView(R.layout.t_recyclerview);
        second_view.addView(secondView_recyclerView);
        //设置  RecyclerView.LayoutManager
        secondView_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        secondView_recyclerView.setAdapter(simpleSearchResultAdapter);

        //设置项目间隔
        SpacesItemDecoration sp = new SpacesItemDecoration(15);
        secondView_recyclerView.addItemDecoration(sp);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (stp != null && stp.isShowing()) {
                stp.dismiss();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void onFirstViewItemClick(ADInfo bean) {
        // search_edit.removeTextChangedListener(this);
        if (bean == null) return;
        search_edit.setText(bean.getImageName());
        search_edit.setSelection(bean.getImageName().length());
        //  search_edit.addTextChangedListener(this);
        saveHistorical(bean.getImageName());
        onClicked(bean.getImageName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*  清空历史记录*/
            case R.id.search_clear:
                SharedPreferences.Editor clear_prefrs = HIStorical_Prefrs.edit();
                clear_prefrs.clear();
                clear_prefrs.apply();
                firstView_adapter1.clear();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (getIntent().getStringExtra("id")==null){
            String ss = editable.toString();
            if (ss.length() == 0) {
                List<ADInfo> ll = getHistorical();
                firstView_adapter1.setList(ll);
                getHostsearchData();
                first_view.setVisibility(View.VISIBLE);
                second_view.setVisibility(View.GONE);
                simpleSearchResultAdapter.clear();
            } else if (ss.length() >= 2) {
                first_view.setVisibility(View.GONE);
                second_view.setVisibility(View.VISIBLE);
                getSearchResult(ss,getIntent().getIntExtra("isCanteen",0));
            }
        }
    }
}
