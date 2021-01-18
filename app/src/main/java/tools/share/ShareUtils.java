package tools.share;

import android.graphics.Bitmap;
import android.util.Log;

import com.base.activity.BaseActivity;
import com.base.utils.UIUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Hashtable;

import cn.com.taodaji.R;

/**
 * Created by yangkuo on 2018/11/23.
 */
public class ShareUtils {

    private BaseActivity mContext;

    public ShareUtils(BaseActivity mContext) {
        this.mContext = mContext;
    }

    public void shareWeb(String url, String title, String description) {
        shareWeb(R.mipmap.share_logo, url, title, description);
    }

    /**
     * @param obj         图标可以为int  或String
     * @param url         链接地址
     * @param title       标题
     * @param description 说明
     */
    public void shareWeb(Object obj, String url, String title, String description) {
        UMImage thumb;
        if (obj instanceof Integer) {
            thumb = new UMImage(mContext, (int) obj);
        } else {
            thumb = new UMImage(mContext, obj.toString());
        }


        UMWeb web = new UMWeb(url);
        web.setThumb(thumb);
        web.setDescription(description);
        web.setTitle(title);

        new ShareAction(mContext).withMedia(web).setDisplayList(/*SHARE_MEDIA.SINA, */SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener).open();
    }

    public ShareUtils setUmShareListener(UMShareListener umShareListener) {
        this.umShareListener = umShareListener;
        return this;
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            UIUtils.showToastSafe("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());

                UIUtils.showToastSafe(t.getMessage());
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            UIUtils.showToastSafe("分享取消");
        }
    };

    /**
     * 生成二维码
     *
     * @param text 需要生成二维码的文字、网址等
     * @param size 需要生成二维码的大小（）
     * @return bitmap
     */
    public static Bitmap createQRCode(String text, int size) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, size, size, hints);
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * size + x] = 0xff000000;
                    } else {
                        pixels[y * size + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
