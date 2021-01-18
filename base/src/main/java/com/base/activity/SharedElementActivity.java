package com.base.activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by yangkuo on 2018-07-18.
 */


/**
 * 1.平移，跟我们的overridePendingTransition效果是一样的，从第二个和第三个参数就可以看出
 * ActivityOptionsCompat.makeCustomAnimation(Context context, int enterResId, int exitResId)
 * <p>
 * 2.将一个控件平滑的放大过渡到第二个activity，一般用于相册的具体照片的查看
 * ActivityOptionsCompat.makeScaleUpAnimation(View source,int startX, int startY, int startWidth, int startHeight)
 * <p>
 * 3.通过放大一个图片，最后过度到一个新的activity
 * ActivityOptionsCompat.makeThumbnailScaleUpAnimation(View source,Bitmap thumbnail, int startX, int startY)
 * <p>
 * 4.平滑的将一个控件平移的过渡到第二个activity
 * ActivityOptionsCompat.makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName)
 * <p>
 * 5. 平滑的将多个控件平移的过渡到第二个activity
 * ActivityOptionsCompat.makeSceneTransitionAnimation(Activity activity,Pair<View, String>… sharedElements)
 */
public class SharedElementActivity extends AppCompatActivity {
    //2.将一个控件平滑的放大过渡到第二个activity，一般用于相册的具体照片的查看
    public void startActivity(@NonNull Intent intent, @NonNull View sharedElementView) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(sharedElementView, sharedElementView.getWidth() / 2, sharedElementView.getHeight() / 2, 0, 0);
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }

    //3.通过放大一个图片，最后过度到一个新的activity
    public void startActivity(@NonNull Intent intent, @NonNull View sharedElementView, Bitmap thumbnail) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(sharedElementView, thumbnail, sharedElementView.getWidth() / 2, sharedElementView.getHeight() / 2);
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }

    //4.平滑的将一个控件平移的过渡到第二个activity
    public void startActivity(@NonNull Intent intent, @NonNull View sharedElementView, @NonNull String sharedElementName) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElementView, sharedElementName);
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }

    //5. 平滑的将多个控件平移的过渡到第二个activity
    public void startActivity(@NonNull Intent intent, Pair<View, String>... sharedElements) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedElements);
        ActivityCompat.startActivity(this, intent, compat.toBundle());
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
