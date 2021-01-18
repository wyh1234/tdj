package cn.com.taodaji.ui.activity.penalty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.base.glide.ImageLoaderUtils;
import com.base.utils.BitmapUtil;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;


import cn.com.taodaji.R;
import cn.com.taodaji.model.entity.ImageUpload;
import cn.com.taodaji.ui.activity.penalty.adapter.AppealUploadAdapter;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.activity.SimpleToolbarActivity;
import tools.animation.ScrollLinearLayoutManager;
import tools.extend.GifSizeFilter;
import tools.extend.MyGlideEngine;

public class AppealActivity extends SimpleToolbarActivity implements AppealUploadAdapter.OnItemClickListener {
    private View mainView;
    private AppealUploadAdapter appealUploadAdapter;
    private RecyclerView phoneview_list;
    private RxPermissions rxPermissions;
    public static final int REQUEST_CODE_CHOOSE_GRIDE = 23;
    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("申诉");
    }

    @Override
    protected void initMainView() {
        rxPermissions = new RxPermissions(this);
        mainView = getLayoutView(R.layout.appeal_layout);
        body_other.addView(mainView);
        phoneview_list= ViewUtils.findViewById(mainView,R.id.phoneview_list);
        ScrollLinearLayoutManager layout3 = new ScrollLinearLayoutManager(this, 3);//服务
        layout3.setScrollEnable(false);
        phoneview_list.setLayoutManager(layout3);
        appealUploadAdapter = new AppealUploadAdapter(this);
        phoneview_list.setAdapter(appealUploadAdapter);
        appealUploadAdapter.setOnItemClickListener(this);
    }

    @Override
    public void OnItemViewClick(View view, int position) {
        if (appealUploadAdapter.getItemCount() == position + 1&&appealUploadAdapter.getmURL().size()!=3) {
            rxPermissions.request(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        //从相册中选择图片 此处使用知乎开源库Matisse
                        Matisse.from(AppealActivity.this)
                                .choose(MimeType.ofImage())
                                .theme(R.style.Matisse_Dracula)
                                .countable(true)//true:选中后显示数字;false:选中后显示对号
                                .maxSelectable(3)
                                .capture(true)
                                .captureStrategy(new CaptureStrategy(true, "cn.com.taodaji.provider" +
                                        "")) //是否拍照功能，并设置拍照后图片的保存路径; FILE_PATH = 你项目的包名.fileprovider,必须配置不然会抛异常
                                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .originalEnable(true)
                                .maxOriginalSize(10)
                                .thumbnailScale(0.85f)
                                .imageEngine(new MyGlideEngine())
                                .forResult(REQUEST_CODE_CHOOSE_GRIDE);

                    }
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE_GRIDE && resultCode == RESULT_OK) {//storage/emulated/0/Pictures/JPEG_20181011_155709.jpg

            Log.e("OnActivityResult ", String.valueOf(Matisse.obtainPathResult(data).get(0)));
            for (int i=0;i<Matisse.obtainPathResult(data).size();i++){
                String path = Matisse.obtainPathResult(data).get(i);
                loading("正在上传");
                String imageName = UIUtils.getFileNames(path);
                getRequestPresenter().upload(imageName, BitmapUtil.bitmapTobyte(BitmapUtil.getSmallBitmap(path), true), new Callback<ImageUpload>() {
                    @Override
                    public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                        if (response.body() != null) {
                            loadingDimss();
                            appealUploadAdapter.setData(response.body().getData());

                        } else {
                            loadingDimss();
                            UIUtils.showToastSafesShort(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageUpload> call, Throwable t) {
                        loadingDimss();
                        UIUtils.showToastSafesShort("图片上传失败，请检查网络");
                    }
                });

            }
        }
    }
}
