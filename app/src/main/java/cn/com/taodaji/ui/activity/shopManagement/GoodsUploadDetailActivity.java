package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.base.viewModel.adapter.OnItemClickListener;
import com.base.common.Config;
import com.base.utils.BitmapUtil;
import com.base.utils.IOUtils;

import cn.com.taodaji.model.entity.GoodsInformation;
import tools.extend.TakePhotoUtils;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.takephoto.model.TImage;
import com.base.takephoto.model.TResult;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.viewModel.adapter.GoodsUploadDetailAdapter;
import cn.com.taodaji.model.entity.FindProductDetail;
import cn.com.taodaji.model.entity.ImageUpload;

import com.base.retrofit.RequestCallback;
import com.base.retrofit.ResultInfoCallback;

import org.greenrobot.eventbus.EventBus;

import cn.com.taodaji.model.presenter.RequestPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.TakePhotosActivity;

/**
 * Created by yangkuo on 2018-03-06.
 */

public class GoodsUploadDetailActivity extends TakePhotosActivity implements View.OnClickListener, OnItemClickListener {
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setTitle("上传商品详情");
        setStatusBarColor(R.color.blue_2898eb);
        setToolBarColor(R.color.blue_2898eb);
    }

    private TextView goods_name, goods_nickName;
    private TextView textView_21, textView_22;
    private int product_id;
    private String goodsName, goodsNickName;

    private GoodsUploadDetailAdapter goodsUploadDetailAdapter;

    private int entity_id = -10000;

    private int upLoadPosition = -1;

    private AlertDialog alertDialog;
    private EditText et_input;
    private int isUpdate = -1;//是否更新AlertDialog,,更新的位置


    private boolean isEdit;//是否从编辑过来

    @Override
    protected void initMainView() {
        View mainView = ViewUtils.getLayoutViewMatch(this, R.layout.activity_goods_upload_detail);
        body_other.addView(mainView);
        loading();
        goods_name = ViewUtils.findViewById(mainView, R.id.goods_name);
        goods_name.setText(goodsName);

        goods_nickName = ViewUtils.findViewById(mainView, R.id.goods_nickName);
        goods_nickName.setText(goodsNickName);
        textView_21 = ViewUtils.findViewById(mainView, R.id.textView_21);
        textView_22 = ViewUtils.findViewById(mainView, R.id.textView_22);
        ViewUtils.findViewById(mainView, R.id.tv_text_add).setOnClickListener(this);
        ViewUtils.findViewById(mainView, R.id.tv_image_add).setOnClickListener(this);

        final RecyclerView recyclerView = ViewUtils.findViewById(mainView, R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goodsUploadDetailAdapter = new GoodsUploadDetailAdapter();
        goodsUploadDetailAdapter.setItemClickListener(this);
        recyclerView.setAdapter(goodsUploadDetailAdapter);


        View bottomView = ViewUtils.getLayoutView(this, R.layout.item_goods_upload_detail_bottom);
        body_bottom.addView(bottomView);
        body_bottom.setVisibility(View.VISIBLE);
        body_bottom.setBackgroundResource(R.color.gray_f5);
        ViewUtils.findViewById(bottomView, R.id.goods_create_ok).setOnClickListener(this);

        //文字弹窗
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = ViewUtils.getLayoutView(this, R.layout.popup_window_simple_intput);
        et_input = ViewUtils.findViewById(view, R.id.et_input);
        builder.setView(view);
        alertDialog = builder.create();
        ViewUtils.findViewById(view, R.id.no).setOnClickListener(this);
        ViewUtils.findViewById(view, R.id.yes).setOnClickListener(this);

        // if (alertDialog.getWindow() != null) alertDialog.getWindow().setDimAmount(0);//设置昏暗度为0
    }

    @Override
    protected void initData() {

        product_id = getIntent().getIntExtra("product_id", -1);
        goodsName = getIntent().getStringExtra("goodsName");
        goodsNickName = getIntent().getStringExtra("goodsNickName");
        isEdit = getIntent().getBooleanExtra("isUpdate", false);

        if (product_id == -1) return;
        if (TextUtils.isEmpty(goodsNickName)) {
            textView_21.setVisibility(View.GONE);
            textView_22.setVisibility(View.GONE);
        } else goods_nickName.setText(goodsNickName);
        goods_name.setText(goodsName);
        getData();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        upLoadImage(result.getImage());
    }


    @Override
    public void onClick(View v) {
        if (product_id == -1) return;
        switch (v.getId()) {
            case R.id.yes:
                if (alertDialog != null) alertDialog.dismiss();
                String sst = et_input.getText().toString();
                if (sst.length() == 0) {
                    UIUtils.showToastSafesShort("请输入描述");
                    return;
                }

                if (goodsUploadDetailAdapter != null) {
                    FindProductDetail.DataBean dataBean = new FindProductDetail.DataBean();
                    dataBean.setType(2);
                    dataBean.setDescription(et_input.getText().toString());
                    dataBean.setProduct_id(product_id);
                    dataBean.setEntity_id(entity_id++);
                    if (isUpdate != -1) {
                        goodsUploadDetailAdapter.update(isUpdate, dataBean);
                        isUpdate = -1;
                    } else {
                        goodsUploadDetailAdapter.loadMore(dataBean);
                        goodsUploadDetailAdapter.setBottomVisible();
                    }

                }
                break;
            case R.id.no:
                if (alertDialog != null) alertDialog.dismiss();
                break;
            case R.id.tv_image_add:
                String fileName = System.currentTimeMillis() + ".jpg";
                Uri imageUri = Uri.fromFile(IOUtils.createFile(Config.imageSaveDir, fileName));
                TakePhotoUtils.getInstance().setCrop(false).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 1, true);
                break;
            case R.id.tv_text_add:
                if (alertDialog != null && !alertDialog.isShowing()) {
                    et_input.setText("");
                    alertDialog.show();
                }
                break;
            case R.id.goods_create_ok:
                goodsUploadDetailAdapter.dataDispose();
                List<FindProductDetail.DataBean> list = goodsUploadDetailAdapter.getList();
                if (!list.isEmpty()) {
                    String str = "";
                    for (FindProductDetail.DataBean dataBean : list) {
                        str += dataBean.getDescription() + "#";
                    }
                    str = str.substring(0, str.length() - 1);
                    loading();
                    getRequestPresenter().product_saveDetail(product_id, str, new ResultInfoCallback() {
                        @Override
                        public void onSuccess(Object body) {
                            loadingDimss();
                            if (isEdit) {
                                UIUtils.showToastSafesShort("商品详情编辑成功");
                            } else {
                                UIUtils.showToastSafesShort("商品详情发布成功");
                                startActivity(new Intent(GoodsUploadDetailActivity.this, ReleaseGoodsHelpActivity.class));
                            }
                            EventBus.getDefault().post(new GoodsInformation());
                            finish();
                        }

                        @Override
                        public void onFailed(int o, String msg) {
                            UIUtils.showToastSafesShort(msg);
                            loadingDimss();
                        }
                    });
                }
                break;
        }
    }

    private void getData() {
        if (product_id == -1) return;
        loading();
        getRequestPresenter().product_findProductDetail(product_id, new RequestCallback<FindProductDetail>() {
            @Override
            protected void onSuc(FindProductDetail body) {
                loadingDimss();
                goodsUploadDetailAdapter.setList(body.getData());
            }

            @Override
            public void onFailed(int findProductDetail, String msg) {
                loadingDimss();
            }
        });
    }

    public void upLoadImage(TImage tImage) {
        loading();
        //图片压缩
        Bitmap bitmap = BitmapUtil.getSmallBitmap(tImage.getCompressPath());
        if (bitmap != null) {
            byte[] imagebyte = BitmapUtil.bitmapTobyte(bitmap, true);
            RequestPresenter.getInstance().upload(UIUtils.getFileNames(tImage.getCompressPath()), imagebyte, new Callback<ImageUpload>() {
                @Override
                public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                    loadingDimss();
                    if (response.code() == 200) {
                        if (response.body().getErr() == 0) {
                            FindProductDetail.DataBean dataBean = new FindProductDetail.DataBean();
                            dataBean.setType(1);
                            dataBean.setDescription(response.body().getData());
                            dataBean.setProduct_id(product_id);
                            dataBean.setEntity_id(entity_id++);
                            if (upLoadPosition != -1) {
                                goodsUploadDetailAdapter.update(upLoadPosition, dataBean);
                                upLoadPosition = -1;
                            } else {
                                goodsUploadDetailAdapter.loadMore(dataBean);
                                goodsUploadDetailAdapter.setBottomVisible();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<ImageUpload> call, Throwable t) {
                    loadingDimss();
                    UIUtils.showToastSafesShort("图片上传失败");
                }
            });
        }
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            switch (view.getId()) {
                case R.id.et_description:
                    isUpdate = position;
                    if (alertDialog != null && !alertDialog.isShowing()) {
                        FindProductDetail.DataBean dataBean = (FindProductDetail.DataBean) bean;
                        et_input.setText(dataBean.getDescription());
                        et_input.setSelection(dataBean.getDescription().length());
                        alertDialog.show();
                    }
                    break;
                case R.id.tv_delete:
                    goodsUploadDetailAdapter.remove(position);
                    break;
                case R.id.tv_update:
                    upLoadPosition = position;
                    String fileName = System.currentTimeMillis() + ".jpg";
                    Uri imageUri = Uri.fromFile(IOUtils.getFile(fileName));
                    TakePhotoUtils.getInstance().setCrop(false).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), 1, true);
                    break;
            }
            return true;
        }
        return false;
    }


}
