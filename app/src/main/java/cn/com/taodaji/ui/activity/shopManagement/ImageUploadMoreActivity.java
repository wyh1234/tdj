package cn.com.taodaji.ui.activity.shopManagement;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.base.listener.UploadPicturesDoneListener;
import com.base.retrofit.RequestCallback;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.QualificationUpload;
import tools.activity.SimpleToolbarActivity;
import tools.fragment.AddedPicturesFragment;

public class ImageUploadMoreActivity extends SimpleToolbarActivity implements UploadPicturesDoneListener, View.OnClickListener {
    Button ok;

    @Override
    protected void titleSetting(Toolbar toolbar) {
        setStatusBarColor();
        setToolBarColor();
        simple_title.setText("上传最新资质");
    }

    private AddedPicturesFragment addedPicturesFragment;
    private int productId;
    private String str_url;

    @Override
    protected void initMainView() {
        View mainView = getLayoutView(R.layout.activity_upload_qualifications);
        body_other.addView(mainView);
        ok = ViewUtils.findViewById(mainView, R.id.ok);
        ok.setOnClickListener(this);

        addedPicturesFragment = (AddedPicturesFragment) getSupportFragmentManager().findFragmentById(R.id.addedPicturesFragment);
        addedPicturesFragment.setMaxSelect(30);
        addedPicturesFragment.setCrop(false);
        addedPicturesFragment.setCompress(false);
        if (PublicCache.loginSupplier!=null)
        addedPicturesFragment.setWatermarkString(PublicCache.loginSupplier.getStoreName());
    }

    private String image_str;

    @Override
    protected void initData() {
        productId = getIntent().getIntExtra("productId", -1);
        image_str = getIntent().getStringExtra("data");
        addedPicturesFragment.setImageStrs(image_str);
    }

    @Override
    public void uploadPicturesIsDone(Object obj) {
        str_url = addedPicturesFragment.getImageStr();

        if (productId == -1) {
            Intent intent = new Intent();
            intent.putExtra("data", str_url);
            setResult(RESULT_OK, intent);
            finish();
        } else
            getRequestPresenter().qualification_upload(productId, str_url, new RequestCallback<QualificationUpload>() {
                @Override
                public void onSuc(QualificationUpload body) {
                    loadingDimss();
                    UIUtils.showToastSafesShort("资质上传成功");
                    Intent intent = new Intent();
                    intent.putExtra("data", str_url);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                @Override
                public void onFailed(int qualificationUpload, String msg) {
                    loadingDimss();
                    UIUtils.showToastSafesShort("资质上传失败  " + msg);
                }
            });

    }

    public void onClick(View view) {
        loading("努力上传中....");
        if (addedPicturesFragment.isUploadDone()) {
            uploadPicturesIsDone(null);
        } else addedPicturesFragment.setCallBack(true);
    }
}
