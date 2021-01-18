/*
 ******************************* Copyright (c)*********************************\
 **
 **                 (c) Copyright 2015, 蒋朋, china, qd. sd
 **                          All Rights Reserved
 **
 **                           By()
 **
 **
 **-----------------------------------版本信息------------------------------------
 ** 版    本: V0.1
 **
 **------------------------------------------------------------------------------
 ********************************End of Head************************************\
 */

package com.tdj.zxinglibrary.decode;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;

/**
 * 获取图片的路径
 */

public class ImageUtil {

    @TargetApi(19)
    public static String getImageAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null) return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri)) return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
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


    public static int[] binaryzationBitmap(@NonNull int[] pix, int tmp) {
        int alpha = 0xFF << 24;
        for (int i = 0; i < pix.length; i++) {
            int grey = pix[i];
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
            pix[i] = alpha << 24 | red << 16 | green << 8 | blue;
            if (pix[i] == -1) {
                pix[i] = -1;
            } else {
                pix[i] = -16777216;
            }
        }
        return pix;
    }

    //将位图转换为字节
    public static byte[] bitmapTobyte(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //字节转换为位图
    public static Bitmap byteToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
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
    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int w = 450;
        int h = 800;
        double dd = 1.0 * height * width / (w * h);
        return (int) Math.round(Math.sqrt(dd));
    }
    //按比例缩小图片的质量
    public static Bitmap getcompressBitmap(Bitmap bitmap) {
        //缩小图片的质量
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        return byteToBitmap(baos.toByteArray());
    }
}
