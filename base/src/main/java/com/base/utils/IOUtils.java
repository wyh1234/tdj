package com.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.util.List;

public class IOUtils {

    //  获取当前程序路径
    public static String getApplicationPath() {
        String path = UIUtils.getContext().getFilesDir().getAbsolutePath();
        Log.i("path", path);
        return path;
    }

    //  获取该程序的安装包路径
    public static String getPackageResourcePath() {
        return UIUtils.getContext().getPackageResourcePath();
    }


    //  获取程序默认数据库路径
    public static String getDatabasePath() {
        return UIUtils.getContext().getDatabasePath("com_tdj").getAbsolutePath();
    }

    /**
     * 判断SDCard是否存在 [当没有外挂SD卡时，内置ROM也被识别为存在sd卡]
     **/
    public static boolean isSdCardExist() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static File getFile(String path) {
        return new File(Environment.getExternalStorageDirectory(), path);
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 获取SD卡根目录路径
     *
     * @
     */
    public static String getSdCardPath() {
        if (isSdCardExist()) return Environment.getExternalStorageDirectory().getAbsolutePath();
        else return UIUtils.getContext().getCacheDir().getAbsolutePath();
    }

    /**
     * 获取默认相册路径
     *
     * @
     */
    public static String getPicturesPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    public static File createFile(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + "/" + fileName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }


    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.e("IOUtils", e.getMessage());
        }
    }

    /*
     * 检查文件是否存在
     *
     */
    public static boolean fileIsExists(String filenmae, String path) {
        File file = new File(path, filenmae);
        return fileIsExists(file);
    }

    public static boolean fileIsExists(File file) {
        return file.exists();
    }





    /*    说明:

            1.硬件上的 block size, 应该是"sector size"，linux的扇区大小是512byte
    2.有文件系统的分区的block size, 是"block size"，大小不一，可以用工具查看
    3.没有文件系统的分区的block size，也叫“block size”，大小指的是1024 byte
    4.Kernel buffer cache 的block size, 就是"block size"，大部分PC是1024
    5.磁盘分区的"cylinder size"，用fdisk 可以查看。
    我们这里的block size是第二种情况，一般SD卡都是fat32的文件系统，block size是4096.
    这样就可以知道手机的内部存储空间和sd卡存储空间的总大小和可用大小了。*/


    /**
     * 获取手机内部剩余存储空间
     *
     * @
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @
     */
    public static long getAvailableExternalMemorySize() {
        if (isSdCardExist()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return -1;//这里返回错误信息
        }
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @
     */
    public static long getTotalExternalMemorySize() {
        if (isSdCardExist()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return -1;//这里返回错误信息
        }
    }
}
