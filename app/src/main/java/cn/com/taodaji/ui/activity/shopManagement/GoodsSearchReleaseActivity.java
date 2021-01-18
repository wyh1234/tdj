package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.viewModel.adapter.GoodsSearchRecyclerViewAdapter;
import cn.com.taodaji.model.entity.GoodsClassifySearchBean;

import com.base.retrofit.RequestCallback;

import cn.com.taodaji.model.presenter.RequestPresenter;
import tools.activity.DataBaseActivity;

import com.base.utils.JavaMethod;
import com.base.utils.ListDataSave;

/**
 * Created by Administrator on 2017-10-18.
 * 发布商品搜索Activity
 */

public class GoodsSearchReleaseActivity extends DataBaseActivity implements View.OnClickListener {
    private TextView mTvSearch;
    private String name;
    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private LinearLayout mLinearLayoutList;
    private LinearLayout mLinearLayoutNull;
    private TextView mTextViewCliear;
    private ImageView mImageViewBack;
    private Button mButtonSelect;
    private ImageView mImageViewClear;
    private GoodsSearchRecyclerViewAdapter adapter;
    private List<GoodsClassifySearchBean.DataBean> list = new ArrayList<>();
    ListDataSave listDataSave;

    @Override
    protected View getContentLayout() {
        return getLayoutView(R.layout.activity_goods_search);
    }

    public void initView() {
        super.initView();
        setStatusBarColor(R.color.blue_status);

        mTvSearch = (TextView) findViewById(R.id.goods_search_btn);
        mEditText = (EditText) findViewById(R.id.goods_search_edit_text);
        mRecyclerView = (RecyclerView) findViewById(R.id.goods_search_recycler_view);
        mLinearLayoutList = (LinearLayout) findViewById(R.id.goods_search_list_linearlayout);
        mLinearLayoutNull = (LinearLayout) findViewById(R.id.goods_search_null_linearlayout);
        mTextViewCliear = (TextView) findViewById(R.id.goods_search_clear_record_btn);
        mImageViewBack = (ImageView) findViewById(R.id.goods_search_back_image);
        mButtonSelect = (Button) findViewById(R.id.goods_search_select_btn);
        mImageViewClear = (ImageView) findViewById(R.id.goods_search_clear_image);
        mTvSearch.setOnClickListener(this);
        mEditText.setOnClickListener(this);
        mTextViewCliear.setOnClickListener(this);
        mImageViewBack.setOnClickListener(this);
        mButtonSelect.setOnClickListener(this);
        mImageViewClear.setOnClickListener(this);
    }

    public void initData() {
        //初始化RecyclerView
        initRecyclerView();
        //初始化历史记录数据
        initHistoryData();
        //输入文字监听
        initEditextLinstener();
    }

    private void initEditextLinstener() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEditText.getText().toString().length() >= 1) {
                    mImageViewClear.setVisibility(View.VISIBLE);
                } else {
                    mImageViewClear.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initHistoryData() {
        //获取上次点击的item存储数据，作为历史记录显示，在GoodsSearchRecyclerViewAdapter 中做存储
        listDataSave = new ListDataSave(this, "BEAN");
        List<String> listStr = listDataSave.getDataList("GOODS_SEARCH_BEAN");
        if (listStr != null && listStr.size() != 0) {
//            Gson gson = new Gson();
            for (int i = 0; i < listStr.size(); i++) {
//                GoodsClassifySearchBean.DataBean bean = gson.fromJson(listStr.get(i), GoodsClassifySearchBean.DataBean.class);
                GoodsClassifySearchBean.DataBean bean = JavaMethod.getJsonBean(listStr.get(i), GoodsClassifySearchBean.DataBean.class);
                list.add(bean);
                adapter.setList(list);
            }
        } else {
            mTextViewCliear.setVisibility(View.GONE);
        }
    }


    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        adapter = new GoodsSearchRecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);
    }


    private void getEditTextStr() {
        if (mEditText.getText() != null) {
            name = mEditText.getText().toString();
        } else {
            name = "";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_search_clear_image:
                mEditText.setText(null);
                break;
            case R.id.goods_search_btn:
                getEditTextStr();
                if (name.equals("")) {
                    mLinearLayoutList.setVisibility(View.GONE);
                    mLinearLayoutNull.setVisibility(View.VISIBLE);
                } else {
                    if (PublicCache.loginSupplier != null) {
                        RequestPresenter.getInstance().goods_classify_search(name,PublicCache.loginSupplier.getStore()+"", new RequestCallback<GoodsClassifySearchBean>() {
                            @Override
                            protected void onSuc(GoodsClassifySearchBean body) {
                                if (body.getData().size() != 0) {
                                    mLinearLayoutList.setVisibility(View.VISIBLE);
                                    mLinearLayoutNull.setVisibility(View.GONE);
                                    list.clear();
                                    list.addAll(body.getData());
                                    adapter.setList(list);
//                                    adapter.notifyDataSetChanged();
                                    mTextViewCliear.setVisibility(View.GONE);
                                } else {
                                    mLinearLayoutList.setVisibility(View.GONE);
                                    mLinearLayoutNull.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onFailed(int goodsClassifySearchBean, String msg) {
                            }
                        });
                    }

                }

                break;
            case R.id.goods_search_clear_record_btn:
                list.clear();
                adapter.setList(list);
//                adapter.notifyDataSetChanged();

                //清空储存的数据
                listDataSave.clearData();
                mTextViewCliear.setVisibility(View.GONE);
                break;
            case R.id.goods_search_back_image:
                finish();
                break;
            case R.id.goods_search_select_btn:
                Intent intent = new Intent(this, CommodityCategoryActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
