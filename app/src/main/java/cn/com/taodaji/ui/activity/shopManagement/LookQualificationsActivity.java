package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.GroupQualificationsLookAdapter;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ImageListByProductId;

import com.base.glide.nineImageView.ImagesActivity;
import com.base.retrofit.RequestCallback;

import tools.activity.SimpleToolbarActivity;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.adapter.OnItemClickListener;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;

import com.base.utils.ADInfo;
import com.base.utils.ADInfoGroup;
import com.base.utils.DateUtils;
import com.base.utils.ViewUtils;

public class LookQualificationsActivity extends SimpleToolbarActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    Button ok;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("检验报告");
    }

    private GroupQualificationsLookAdapter adapter;

    private int productId;
    private boolean isHideOk = false;

    @Override
    protected void initMainView() {
        View view = getLayoutView(R.layout.activity_look_qualifications);
        body_other.addView(view);
        recyclerView = ViewUtils.findViewById(view, R.id.recyclerView);
        ok = ViewUtils.findViewById(view, R.id.ok);
        ok.setOnClickListener(this);

        if (PublicCache.loginPurchase != null) ok.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(5, R.color.white));
        adapter = new GroupQualificationsLookAdapter();
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object bean) {
                if (onclickType == 0) {
                    int itemType = adapter.concludeItemViewType(bean);
                    if (itemType == SingleRecyclerViewAdapter.TYPE_CHILD) {
                        if (view instanceof ImageView) {
//                            ZoomImageActivity.startActivity(view, JavaMethod.getFieldValue(bean, "imageObject"), Pair.create(view, UIUtils.getString(R.string.transitional_image)));
//                            ZoomImageActivity.startActivity((ImageView) view, );
//                            Intent intent = new Intent(LookQualificationsActivity.this, ZoomImageActivity.class);
//                            intent.putExtra("url", String.valueOf(JavaMethod.getFieldValue(bean, "imageObject")));
//                            startActivity(intent, view);

                            ImagesActivity.startActivity(recyclerView, position, R.id.image, "imageObject");
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        productId = getIntent().getIntExtra("productId", -1);
        isHideOk = getIntent().getBooleanExtra("hide", false);
        if (isHideOk) ok.setVisibility(View.GONE);
        getRequestPresenter().getImageListByProductId(productId, new RequestCallback<ImageListByProductId>() {
            @Override
            public void onSuc(ImageListByProductId body) {
                List<ADInfoGroup> group_list = new ArrayList<>();
                for (ImageListByProductId.DataBean dataBean : body.getData()) {
                    ADInfoGroup ad = new ADInfoGroup();
                    ad.setImageName(dataBean.getCreate_time());
                    String[] str = dataBean.getItems().split(",");
                    List<ADInfo> child_list = new ArrayList<>();
                    for (String s : str) {
                        ADInfo in = new ADInfo();
                        in.setImageObject(s);
                        child_list.add(in);
                    }
                    ad.setListAdinfo(child_list);
                    group_list.add(ad);
                }
                adapter.setGroupList(group_list);
            }

            @Override
            public void onFailed(int imageListByProductId, String msg) {

            }
        });
    }


    public void onClick(View view) {
        Intent intent = new Intent(this, ImageUploadMoreActivity.class);
        intent.putExtra("productId", productId);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String urls = data.getStringExtra("data");
            if (TextUtils.isEmpty(urls)) return;

            List<ADInfoGroup> group_list = adapter.getGroupList();
            String time;

            String[] str = urls.split(",");
            List<ADInfo> child_list = new ArrayList<>();
            for (String s : str) {
                ADInfo in = new ADInfo();
                in.setImageObject(s);
                child_list.add(in);
            }
            if (group_list.size() > 0 && (time = group_list.get(0).getImageName()).compareTo(DateUtils.dateLongToString(0, "yyyy-MM-dd")) == 0) {
                group_list.get(0).getListAdinfo().addAll(child_list);
            } else {
                ADInfoGroup ad = new ADInfoGroup();
                ad.setImageName(DateUtils.dateLongToString(0, "yyyy-MM-dd"));
                ad.setListAdinfo(child_list);
                group_list.add(0, ad);
            }
            adapter.setGroupList(group_list);
        }
    }
}
