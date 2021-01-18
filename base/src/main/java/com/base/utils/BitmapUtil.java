package com.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.reflect.Array.getInt;

/**
 * 当一张图片非常非常大时，若想放大图片查看细节，肯定就要加载原图。但是图片又非常大，全部加载进来肯定就直接OOM了，
 * BitmapRegionDecoder提供了一个方法，能够加载一部分图片。
 * public Bitmap decodeRegion(Rect rect, BitmapFactory.Options options)
 * 传入的矩形rect就是我们要加载图片的区域。
 * <p>
 * <p>
 * 获取当前主题背景颜色
 * SkinManager.getInstance().getResourceManager().getColor("future_bg")
 */
public class BitmapUtil {


    //从绝对路经获取图片
    public static Bitmap getBitmap(String filepath) {
        return BitmapFactory.decodeFile(filepath);
    }


    //从assets中获取图片
    public static Bitmap getAssetsBitmap(String filename) {
        return UIUtils.getAssetsImage("images/" + filename);
    }


    //将位图转换为字节
    public static byte[] bitmapTobyte(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /*将位图转换为字节 needRecycle 是否释放*/
    public static byte[] bitmapTobyte(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //字节转换为位图
    public static Bitmap byteToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    //将位图转换为输入流
    public static InputStream bitmapToInputstream(Bitmap bm) {
        return new ByteArrayInputStream(bitmapTobyte(bm));
    }

    //将File转换为Bitmap
    public static Bitmap UriToBitmap(Uri filePath) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(UIUtils.getContext().getContentResolver().openInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    //获取位图大小
    public static long getBitmapsize(Bitmap bitmap) {
        if (bitmap == null) return 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int w = 450;
        int h = 800;
        double dd = 1.0 * height * width / (w * h);
        return (int) Math.round(Math.sqrt(dd));
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath,boolean... compress) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //  inJustDecodeBounds设置为true，可以不把图片读到内存中,但依然可以计算出图片的大小
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize计算图片的缩放值
        options.inSampleSize = calculateInSampleSize(options);
        // Decode head_portrait_bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //尺寸 初步压缩
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap == null) return null;
        //质量压缩
        if (compress.length>0 ) {
            return null;
        }
        return getcompressBitmap(bitmap);
    }

    //按比例缩小图片的质量
    public static Bitmap getcompressBitmap(Bitmap bitmap) {
        //缩小图片的质量
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        return byteToBitmap(baos.toByteArray());
    }

    //处理从照相机返回的图片旋转的问题
    public static Bitmap resizedBitmap(String path) {
        int angle = readPictureDegree(path);
        Bitmap bitmap = getBitmap(path);
        if (bitmap == null) return null;
        return rotaingImageView(angle, bitmap);
    }

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    //获取图片角度
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    public static void setAntialias(ImageView imageView) {
        // 设置的抗锯齿
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        drawable.setAntiAlias(true);
    }

    /**
     * 设置镜像图像
     *
     * @param mContext
     * @param imageId
     */
    public static ImageView createReflectedImages(Context mContext, int imageId) {

        Bitmap originalImage = BitmapFactory.decodeResource(mContext.getResources(), imageId);

        final int reflectionGap = 4;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);

        canvas.drawBitmap(originalImage, 0, 0, null);

        Paint deafaultPaint = new Paint();
        // canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
        deafaultPaint.setTextAlign(Paint.Align.CENTER);
        // if (!TextUtils.isEmpty(imagetext)) canvas.drawText(imagetext, width / 2, height + reflectionGap, deafaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(bitmapWithReflection);

        return imageView;
    }

    /**
     * 转换图片成圆形
     *
     * @dRes 传入Bitmap对象
     */
    public static Drawable toRoundDrawable(@DrawableRes int dRes) {
        return new BitmapDrawable(UIUtils.getResources(), toRoundBitmap(dRes));
    }

    public static Drawable toRoundDrawable(@NonNull Bitmap source) {
        return new BitmapDrawable(UIUtils.getResources(), toRoundBitmap(source));
    }

    public static Bitmap toRoundBitmap(@DrawableRes int dRes) {
        return toRoundBitmap(getBitmap(dRes));
    }


    /**
     * 转换图片成圆形
     *
     * @source 传入Bitmap对象
     */
    public static Bitmap toRoundBitmap(@NonNull Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap square = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap circle = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(circle);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(square, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return circle;
    }


    /**
     * 转换为圆角距形
     *
     * @param source 传入Bitmap对象
     * @param radius 圆角半径 px
     * @return
     */

    public static Bitmap toRoundRectBitmap(@NonNull Bitmap source, int radius) {

        Bitmap result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }


    public static Bitmap toRoundRectBitmap(@DrawableRes int dRes, int radius) {
        return toRoundRectBitmap(getBitmap(dRes), radius);
    }

    public static Drawable toRoundRectDrawable(@DrawableRes int dRes, int radius) {
        return new BitmapDrawable(UIUtils.getResources(), toRoundRectBitmap(dRes, radius));
    }

    public static Drawable toRoundRectDrawable(@NonNull Bitmap source, int radius) {
        return new BitmapDrawable(UIUtils.getResources(), toRoundRectBitmap(source, radius));
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        return AppCompatResources.getDrawable(UIUtils.getContext(), resId);
    }

    /**
     * 获取drawable
     */
    public static ColorDrawable getColorDrawable(@ColorRes int resId) {
        return new ColorDrawable(UIUtils.getColor(resId));
    }

    public static int getDrawableId(@NonNull String fileName) {
//        String path = "img" + i;
        return UIUtils.getResources().getIdentifier(fileName, "drawable", UIUtils.getPackageName());
    }

    public static Drawable getDrawable(Bitmap bitmap) {
        return new BitmapDrawable(UIUtils.getResources(), bitmap);
    }

    public static Bitmap getBitmap(@DrawableRes int resId) {
        return BitmapFactory.decodeResource(UIUtils.getResources(), resId);
    }


    public static Bitmap getBitmap(@NonNull ImageView imageView) {
        imageView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        imageView.layout(0, 0, imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
        imageView.buildDrawingCache();
        imageView.setDrawingCacheEnabled(true);

        Bitmap obmp = Bitmap.createBitmap(imageView.getDrawingCache());  //获取到Bitmap的图片
        imageView.setDrawingCacheEnabled(false);
        return obmp;
    }

    public static Bitmap getBitmap(Drawable drawable) {
        try {
            Bitmap bitmap;
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else if (drawable instanceof NinePatchDrawable) {
                Object obj = JavaMethod.getFieldValue(drawable, "mNinePatchState");
                NinePatch dfs = JavaMethod.getFieldValue(obj, "mNinePatch");
                if (dfs != null) {
                    NinePatch ninePatch = (NinePatch) dfs;

 /*                   byte[] chunk = ninePatch.getBitmap().getNinePatchChunk();
                    if (NinePatch.isNinePatchChunk(chunk)) {
                        int l1 = chunk[32];
                        int l2 = chunk[36];

                        int r1 = chunk[40] + drawable.getIntrinsicWidth();
                        int r2 = chunk[44] + drawable.getIntrinsicWidth();

                        int t1 = chunk[48];
                        int t2 = chunk[52];

                        int b1 = chunk[56] + drawable.getIntrinsicHeight();
                        int b2 = chunk[60] + drawable.getIntrinsicHeight();

                        //中间固定区域
                        Rect fixed_mid = new Rect(l2, t2, r1, b1);

                    }*/
                    return ninePatch.getBitmap();
                } else
                    bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void readPaddingFromChunk(byte[] chunk, Rect paddingRect) {
        paddingRect.left = getInt(chunk, 12);
        paddingRect.right = getInt(chunk, 16);
        paddingRect.top = getInt(chunk, 20);
        paddingRect.bottom = getInt(chunk, 24);
    }


    /**
     * 给图片添加水印
     */
    public static Bitmap drawTextToBitmap(Bitmap src, String logo, @ColorInt int color) {
        if (src == null) return null;
        //原图宽高
        int w = src.getWidth();
        int h = src.getHeight();

        //创建一个和原图宽高一样的bitmap
//        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = src.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);

        //新建画笔，默认style为实心
        Paint paint = new Paint();
        //设置颜色，颜色可用Color.parseColor("#6b99b9")代替
        paint.setColor(color);
        //设置透明度
        paint.setAlpha(99);
        paint.setStyle(Paint.Style.FILL);
        //抗锯齿
        paint.setAntiAlias(true);
        //画笔粗细大小
        paint.setTextSize(65);
        //保存当前画布状态
//        canvas.save();
//        画布旋转-30度
//        canvas.rotate(-30);
        //获取要添加文字的宽度
        float textWidth = paint.measureText(logo);

        //绘制文字
        canvas.drawText(logo, (w - textWidth) / 2, h / 2, paint);


//        int index = 0;
//        //行循环，从高度为0开始，向下每隔80dp开始绘制文字
//        for (int positionY = -DensityUtil.dp2px(30); positionY <= height; positionY += DensityUtil.dp2px(80)) {
//            //设置每行文字开始绘制的位置,0.58是根据角度算出tan30°,后面的(index++ % 2) * textWidth是为了展示效果交错绘制
//            float fromX = -0.58f * height + (index++ % 2) * textWidth;
//            //列循环，从每行的开始位置开始，向右每隔2倍宽度的距离开始绘制（文字间距1倍宽度）
//            for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
//                //绘制文字
//                canvas.drawText(logo, positionX, positionY, paint);
//            }
//        }
        // 保存状态
//        canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
        //恢复画布状态
//        canvas.restore();

        return bitmap;
    }


    /**
     * 添加logo水印
     *
     * @param src  原图片
     * @param logo logo
     * @return 水印图片
     */
    public static Bitmap createWaterMaskImage(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        //原图宽高
        int w = src.getWidth();
        int h = src.getHeight();
        //logo宽高
        int ww = logo.getWidth();
        int wh = logo.getHeight();
        //创建一个和原图宽高一样的bitmap
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        //创建
        Canvas canvas = new Canvas(newBitmap);
        //绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //新建矩阵
        Matrix matrix = new Matrix();
        //对矩阵作缩放处理
        matrix.postScale(0.1f, 0.1f);
        //对矩阵作位置偏移，移动到底部中间的位置
        matrix.postTranslate(0.5f * w - 0.05f * ww, h - 0.1f * wh - 3);
        //将logo绘制到画布上并做矩阵变换
        canvas.drawBitmap(logo, matrix, null);
        // 保存状态
        canvas.save();// 保存
        // 恢复状态
        canvas.restore();
        return newBitmap;
    }

    /**
     * 对图片进行二值化处理
     *
     * @param imagePath 资源路径
     * @return 解析结果
     */
    public static Bitmap binaryzationBitmap(final String imagePath, int tmp) {
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
            return binaryzationBitmap(input, tmp);
        } catch (Exception e) {

        }

        return null;
    }

    public static Bitmap binaryzationBitmap(@NonNull Bitmap input, int tmp) {
        int width = input.getWidth(); // 获取位图的宽
        int height = input.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        input.getPixels(pixels, 0, width, 0, 0, width, height);


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
                } else {
                    pixels[width * i + j] = -16777216;
                }
            }
        }

        //新建图片
        Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        //设置图片数据
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, width, height);

        return resizeBmp;
    }

    public static byte[] binaryzationBitmap(byte[] input, int tmp) {
        Bitmap bitmap = byteToBitmap(input);
        if (bitmap != null) {
            Bitmap res = binaryzationBitmap(bitmap, tmp);
            if (res != null) return bitmapTobyte(res);
        }
        return null;
    }

}
