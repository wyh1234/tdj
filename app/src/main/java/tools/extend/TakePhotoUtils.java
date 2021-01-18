package tools.extend;

import android.net.Uri;

import com.base.takephoto.app.TakePhoto;
import com.base.takephoto.compress.CompressConfig;
import com.base.takephoto.model.CropOptions;
import com.base.takephoto.model.LubanOptions;
import com.base.takephoto.model.TakePhotoOptions;
import com.base.utils.UIUtils;

public class TakePhotoUtils {

    private int maxSizeKb = 100;//图片最大大小kb
    private boolean isOwnCompressTool = true;//是否用自带压缩工具,第三方的工具有问题
    private boolean isCrop = false;//是否裁切
    private boolean isCorpOwnTool = false;//是否使用自带裁切工具   自带的工具在剪切大图片时会使出问题时

    private boolean isSize = false;//是尺寸还是比例
    private int wCrop = 500;//裁切宽
    private int hCrop = 500;//裁切高

    private int wCompress = 450;//压缩的宽
    private int hCompress = 800;//压缩的高
//
//    private int wCompress = UIUtils.getScreenWidthPixels();//压缩的宽
//    private int hCompress = UIUtils.getScreenHeightPixels();//压缩的高

    private Uri imageUri;//拍照或裁切保存的路经

    public TakePhotoUtils setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
        return takePhotoUtils;
    }

    //是否显示压缩进度
    private boolean showProgressBar = true;
    //是否保存原图
    private boolean enableRawFile = true;

    //设置最大大小kb
    public TakePhotoUtils setMaxSizeKb(int maxSizeKb) {
        this.maxSizeKb = maxSizeKb;
        return takePhotoUtils;
    }

    //设压缩工具，第三方还是自带，true默认自带
    public TakePhotoUtils setOwnCompressTool(boolean ownCompressTool) {
        isOwnCompressTool = ownCompressTool;
        return takePhotoUtils;
    }

    //设置是否裁切
    public TakePhotoUtils setCrop(boolean crop) {
        isCrop = crop;
        return takePhotoUtils;
    }

    //设裁剪工具，第三方还是自带，默认 true自带
    public TakePhotoUtils setCorpOwnTool(boolean corpOwnTool) {
        isCorpOwnTool = corpOwnTool;
        return takePhotoUtils;
    }

    //设置裁剪尺寸 默认400*400
    public TakePhotoUtils setCorpSize(boolean isSize, int w, int h) {
        this.isSize = isSize;
        this.wCrop = w;
        this.hCrop = h;
        return takePhotoUtils;
    }

    //是否压缩进度条
    public TakePhotoUtils setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
        return takePhotoUtils;
    }

    public TakePhotoUtils setEnableRawFile(boolean enableRawFile) {
        this.enableRawFile = enableRawFile;
        return takePhotoUtils;
    }


    private static TakePhotoUtils takePhotoUtils = null;

    public static TakePhotoUtils getInstance() {
        if (takePhotoUtils == null) {
            synchronized (TakePhotoUtils.class) {
                takePhotoUtils = new TakePhotoUtils();
            }
        }
        return takePhotoUtils;
    }


    //拍照
    public void openCamera(TakePhoto takePhoto) {
        configCompress(takePhoto);
        if (isCrop) takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
        else takePhoto.onPickFromCapture(imageUri);
    }

    //从相册选择完成后是否裁切, maxSelectCount 最多选择几张，如果选择多张则自动调用takephoto的相册
    public void openPhotoAlbum(TakePhoto takePhoto, int maxSelectCount, boolean isUseOwnAlbum) {
        if (maxSelectCount < 1) {
//            UIUtils.showToastSafesShort("已选择的图片达到上限");
            return;
        }
        configCompress(takePhoto);
        configTakePhotoOption(takePhoto, isUseOwnAlbum);

        if (isCrop) {
            if (maxSelectCount > 1)
                takePhoto.onPickMultipleWithCrop(maxSelectCount, getCropOptions());
            else takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
        } else {
            if (maxSelectCount > 1) takePhoto.onPickMultiple(maxSelectCount);
            else takePhoto.onPickFromGallery();
        }


    }


    //是否使用takephoto自带相册
    private void configTakePhotoOption(TakePhoto takePhoto, boolean isUse) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        //是否使用自带相册
        builder.setWithOwnGallery(isUse);
        //纠正拍照的照片旋转角度
        builder.setCorrectImage(true);

        takePhoto.setTakePhotoOptions(builder.create());
    }

    //是否压缩
    //  默认压缩,不保存原图
    private void configCompress(TakePhoto takePhoto) {
        //不使用压缩
        //takePhoto.onEnableCompress(null, false);

        //最大大小
        int maxSize = maxSizeKb * 1024;

        CompressConfig config;

        //是否使用自带压缩工具
        if (isOwnCompressTool) {
            config = new CompressConfig.Builder().setMaxSize(maxSize).setMaxPixel(wCompress >= hCompress ? wCompress : hCompress).enableReserveRaw(enableRawFile).create();
        } else {
            LubanOptions option = new LubanOptions.Builder().setMaxHeight(hCompress).setMaxWidth(wCompress).setMaxSize(maxSize).create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }

        takePhoto.onEnableCompress(config, showProgressBar);
    }

    //是否裁切 默认使用自带裁切工具
    private CropOptions getCropOptions() {
        if (!isCrop) return null;
        CropOptions.Builder builder = new CropOptions.Builder();
        if (isSize) builder.setOutputX(wCrop).setOutputY(hCrop);
        else builder.setAspectX(wCrop).setAspectY(hCrop);
        builder.setWithOwnCrop(isCorpOwnTool);
        return builder.create();
    }
}
