package cn.com.taodaji.viewModel.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.BaseViewHolder;
import com.base.glide.nineImageView.ImagesActivity;
import com.base.utils.JavaMethod;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ImageUpload;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.viewModel.vm.ADInfoViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.base.listener.UploadPicturesDoneListener;
import com.base.utils.ADInfo;
import com.base.utils.DensityUtil;
import com.base.utils.IOUtils;
import com.base.utils.BitmapUtil;

import tools.extend.TakePhotoUtils;

import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.takephoto.app.TakePhoto;
import com.base.takephoto.model.TImage;
import com.base.takephoto.model.TResult;

public class SimpleAddPicturesAdapter extends SingleRecyclerViewAdapter {


    private TakePhoto takePhoto;
    private boolean isCrop = true;
    private int maxSelect = 5;
    private boolean isUploadDone = true;//是否上传完毕
    private boolean isCallBack = false;//是否需要回调
    private int image_index = 1000;//图片id
    private boolean isShowAdd = true;//是否显示添加按扭
    private boolean isShowDelete = true;//是否显示删除按钮

    private SparseArrayCompat<TImage> list_timage = new SparseArrayCompat<>();
    //图片上传完成接口
    private UploadPicturesDoneListener uploadPicturesDoneListener;

    public void setTakePhoto(TakePhoto takePhoto) {
        this.takePhoto = takePhoto;
    }

    public void setCrop(boolean crop) {
        isCrop = crop;
    }

    public void setMaxSelect(int maxSelect) {
        this.maxSelect = maxSelect;
    }

    //设置 上传完成后是否需要自动回调接口
    public void setCallBack(boolean isCallBack) {
        this.isCallBack = isCallBack;
    }

    public boolean isUploadDone() {
        return isUploadDone;
    }

    public void setShowAdd(boolean showAdd) {
        isShowAdd = showAdd;
    }

    public void setShowDelete(boolean showDelete) {
        isShowDelete = showDelete;
    }

    public void setUploadPicturesDoneListener(UploadPicturesDoneListener uploadPicturesDoneListener) {
        this.uploadPicturesDoneListener = uploadPicturesDoneListener;
    }

    @Override
    public void initBaseVM() {
        putBaseVM(TYPE_CHILD, new ADInfoViewModel());
    }

    @Override
    public View onCreateHolderView(ViewGroup parent, int viewType) {
        View view1 = ViewUtils.getFragmentView(parent, R.layout.customer_added_pictures_item);

        int w = parent.getMeasuredWidth();

        int spac = parent.getPaddingEnd() + parent.getPaddingStart() + DensityUtil.dp2px(5) * 3;

        UIUtils.setViewSize(view1, (w - spac) / 4, (w - spac) / 4);
        return view1;
    }

    @Override
    public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
        super.onBindViewHolderCustomer(holder, position);
        if ("10000".compareTo(JavaMethod.getFieldValue(getListBean(position), "imageId")) == 0 || !isShowDelete) {
            holder.findViewById(R.id.delete_image).setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onItemClick(View view, int onclickType, int position, Object bean) {
        if (onclickType == 0) {
            if ("10000".compareTo(JavaMethod.getFieldValue(getListBean(position), "imageId")) == 0) {
                /**
                 *打开相册
                 * */
                String fileName = System.currentTimeMillis() + ".jpg";
                Uri imageUri = Uri.fromFile(IOUtils.getFile(fileName));
                TakePhotoUtils.getInstance().setCrop(isCrop).setImageUri(imageUri).openPhotoAlbum(takePhoto, maxSelect - position, true);

                //添加图片事件的传递
                if (itemClickListener != null)
                    itemClickListener.onItemClick(view, onclickType, position, bean);
            } else if (view.getId() == R.id.delete_image) {

                remove(position);

                //如果不存在添加项则添加添加按扭
                if (getAddIndex() == -1) addItemAdds();
            } else
//                ZoomImageActivity.startActivity(view.getContext(), JavaMethod.getFieldValue(bean, "imageObject"));
                ImagesActivity.startActivity(getRecyclerView(), R.id.image, "imageObject", position);
            return true;
        }
        return super.onItemClick(view, onclickType, position, bean);
    }

    public void takeSuccess(TResult result) {
        for (TImage tImage : result.getImages()) {
            image_index++;

            list_timage.put(image_index, tImage);

            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(image_index + "");
            adInfo.setImageObject(tImage.getCompressPath());

            int pos = getAddIndex();
            //如果存在添加的项
            if (pos > -1) {
                if (pos >= maxSelect - 1) {
                    remove(pos);
                    loadMore(adInfo);
                } else insert(adInfo, getAddIndex());

            } else loadMore(adInfo);

        }

        if (isUploadDone) upLoadImage(list_timage.keyAt(0));
    }

    private int getAddIndex() {
        List<ADInfo> list = getList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getImageId().compareTo("10000") == 0) return i;
        }

        return -1;
    }


    public void upLoadImage(int image_index) {
        isUploadDone = false;//将要上传图片，所没有上传完毕
        TImage tImage = list_timage.get(image_index);
        //图片压缩
        Bitmap bitmap = BitmapUtil.getSmallBitmap(tImage.getCompressPath());
        if (bitmap != null) {
            byte[] imagebyte = BitmapUtil.bitmapTobyte(bitmap, true);
            RequestPresenter.getInstance().upload(UIUtils.getFileNames(tImage.getCompressPath()), imagebyte, new Callback<ImageUpload>() {
                @Override
                public void onResponse(Call<ImageUpload> call, Response<ImageUpload> response) {
                    if (response.body() == null) {
                        UIUtils.showToastSafesShort("图片太大了");
                        removeImage(image_index);
                        if (list_timage.size() > 0) upLoadImage(list_timage.keyAt(0));
                        return;
                    }

                    if (response.body().getErr() != 0) {
                        removeImage(image_index);
                        UIUtils.showToastSafesShort("图片上传失败");
                        if (list_timage.size() > 0) upLoadImage(list_timage.keyAt(0));
                    } else {
                        ImageUpload body = response.body();
                        List<ADInfo> adInfoList = getList();
                        for (ADInfo adInfo : adInfoList) {
                            if (Integer.valueOf(adInfo.getImageId()) == image_index) {
                                adInfo.setImageUrl(body.getData());
                                break;
                            }
                        }

                        list_timage.removeAt(0);

                        if (list_timage.size() > 0) upLoadImage(list_timage.keyAt(0));
                        else {
                            isUploadDone = true;
                            if (isCallBack) {
                                uploadPicturesDoneListener.uploadPicturesIsDone(getImageStr());
                            }
                        }

                    }

                }

                @Override
                public void onFailure(Call<ImageUpload> call, Throwable t) {
                    removeImage(image_index);
                    UIUtils.showToastSafesShort("图片上传失败");
                    if (list_timage.size() > 0) upLoadImage(list_timage.keyAt(0));
                }
            });
        }
    }

    public void removeImage(int image_index) {
        list_timage.remove(image_index);
        for (int i = 0; i < getItemCount(); i++) {
            ADInfo adInfo = (ADInfo) getListBean(i);
            if (Integer.valueOf(adInfo.getImageId()) == image_index) {
                remove(i);
                break;
            }
        }
        if (getAddIndex() == -1) addItemAdds();
        //最后一张图片上传失败时，回调
        if (list_timage.size() == 0) {
            isUploadDone = true;
            if (isCallBack) {
                uploadPicturesDoneListener.uploadPicturesIsDone(getImageStr());
            }
        }
    }


    public void setImageStrs(String imageStrs) {
        clear();
        //如果为空则显示添加项
        if (TextUtils.isEmpty(imageStrs)) {
            addItemAdds();
            return;
        }

        String[] imageurl = imageStrs.split(",");
        for (int i = 0; i < imageurl.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(i + "");
            adInfo.setImageUrl(imageurl[i]);
            adInfo.setImageObject(imageurl[i]);
            insert(adInfo, i);
        }
        //如果显示的数量小于可选择的最大数量，则显示添加
        if (imageurl.length < maxSelect) addItemAdds();
    }

    private void addItemAdds() {
        //如果isShowAdd=false不添加
        if (!isShowAdd) return;
        ADInfo adInfo = new ADInfo();
        adInfo.setImageId("10000");
        if (PublicCache.loginSupplier != null) adInfo.setImageObject(R.mipmap.ic_added_image_sup);
        else adInfo.setImageObject(R.mipmap.ic_added_image_pur);
        loadMore(adInfo);
    }


    public String getImageStr() {
        List<ADInfo> list = getList();

        String gallery = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getImageId().compareTo("10000") != 0)
                gallery += list.get(i).getImageUrl() + ",";
        }
        if (gallery.length() > 0) gallery = gallery.substring(0, gallery.length() - 1);
        return gallery;
    }


}
