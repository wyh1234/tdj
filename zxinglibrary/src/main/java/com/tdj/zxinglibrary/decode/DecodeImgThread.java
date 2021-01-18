package com.tdj.zxinglibrary.decode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.Hashtable;
import java.util.Vector;

/**
 * <p>
 * 解析二维码图片
 * 解析是耗时操作，要放在子线程
 */

public class DecodeImgThread extends Thread {


    /*图片路径*/
    private String imgPath;
    /*回调*/
    private DecodeImgCallback callback;
    private Bitmap scanBitmap;


    public DecodeImgThread(String imgPath, DecodeImgCallback callback) {

        this.imgPath = imgPath;
        this.callback = callback;
    }

    @Override
    public void run() {
        super.run();

        if (TextUtils.isEmpty(imgPath) || callback == null) {
            return;
        }
//        parseRectFromFile(imgPath, 180);
        /**
         * 对图片进行裁剪，防止oom
         */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小

        scanBitmap = BitmapFactory.decodeFile(imgPath, options);


        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 1000);
        if (sampleSize <= 0) {
            sampleSize = 1;
        }
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(imgPath, options);

        MultiFormatReader multiFormatReader = new MultiFormatReader();
        // 解码的参数
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(2);
        // 可以解析的编码类型
        Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
        if (decodeFormats == null || decodeFormats.isEmpty()) {
            decodeFormats = new Vector<BarcodeFormat>();
            // 扫描的类型  一维码和二维码
            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        // 设置解析的字符编码格式为UTF8
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8");
        // 设置解析配置参数
        multiFormatReader.setHints(hints);
        // 开始对图像资源解码
        Result rawResult = null;
        try {
            rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new BitmapLuminanceSource(scanBitmap))));

            Log.i("解析结果", rawResult.getText());

        } catch (Exception e) {
            e.printStackTrace();
            //  Log.i("解析的图片结果","失败");
        }

        if (rawResult != null) {
            callback.onImageDecodeSuccess(rawResult);
        } else {
            callback.onImageDecodeFailed();
        }


    }


    /**
     * 对图片进行二维码解析，获取二维码区域(如果有)
     *
     * @param imagePath 资源路径
     * @return 解析结果
     */
    public static Rect parseRectFromFile(final String imagePath, int tmp) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, options);
            options.inJustDecodeBounds = false;

            int originalWidth = options.outWidth;
            int originalHeight = options.outHeight;
            int inSampleSize = 1;
            if (originalWidth > 1000 || originalHeight > 1000) {
                int halfWidth = originalWidth / 2;
                int halfHeight = originalHeight / 2;
                while ((halfWidth / inSampleSize >= 1000) && (halfHeight / inSampleSize >= 1000)) {
                    inSampleSize *= 2;
                }
            }

            options.inSampleSize = inSampleSize;
            Bitmap input = BitmapFactory.decodeFile(imagePath, options);


            int width = input.getWidth(); // 获取位图的宽
            int height = input.getHeight(); // 获取位图的高
            int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

            input.getPixels(pixels, 0, width, 0, 0, width, height);

            int[][] pix = new int[height][width];

            int alpha = 0xFF << 24;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int grey = pixels[width * i + j];
                    // 分离三原色
                    alpha = ((grey & 0xFF000000) >> 24);
                    int red = ((grey & 0x00FF0000) >> 16);
                    int green = ((grey & 0x0000FF00) >> 8);
                    int blue = (grey & 0x000000FF);
                    if (red > tmp) {
                        red = 255;
                    } else {
                        red = 0;
                    }
                    if (blue > tmp) {
                        blue = 255;
                    } else {
                        blue = 0;
                    }
                    if (green > tmp) {
                        green = 255;
                    } else {
                        green = 0;
                    }
                    pixels[width * i + j] = alpha << 24 | red << 16 | green << 8 | blue;
                    if (pixels[width * i + j] == -1) {
                        pixels[width * i + j] = -1;
                        pix[i][j] = -1;
                    } else {
                        pixels[width * i + j] = -16777216;
                        pix[i][j] = -16777216;
                    }
                }
            }
            pixels = null;

            //新建图片
//            Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            //设置图片数据
//            newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
//            Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460);


            return null;

        } catch (Exception e) {

        }

        return null;
    }


}
