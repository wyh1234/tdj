package com.base.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
public class FileSaveUtil {

    public static void saveFile(Context context, String dir, String fileName, Bitmap bitmap) {
        if (bitmap == null) return;
        try {

            File file = IOUtils.createFile(dir, fileName);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            UIUtils.showToastSafesShort(file.getAbsolutePath());
        } catch (Exception e) {
            UIUtils.showToastSafesShort("保存失败");
            e.printStackTrace();
        }
    }
}
