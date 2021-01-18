package tools.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.glide.nineImageView.ImagesActivity;
import com.base.listener.UploadPicturesDoneListener;
import com.base.takephoto.app.TakePhotoFragment;
import com.base.takephoto.model.TImage;
import com.base.takephoto.model.TResult;
import com.base.utils.ADInfo;
import com.base.utils.BitmapUtil;
import com.base.utils.DensityUtil;
import com.base.utils.IOUtils;
import com.base.utils.UIUtils;
import com.base.utils.ViewUtils;
import com.base.viewModel.BaseViewHolder;
import com.base.viewModel.adapter.SingleRecyclerViewAdapter;
import com.base.viewModel.adapter.splite_line.DividerGridItemDecoration;

import java.util.List;

import cn.com.taodaji.R;
import cn.com.taodaji.common.PublicCache;
import cn.com.taodaji.model.entity.ImageUpload;
import cn.com.taodaji.model.presenter.RequestPresenter;
import cn.com.taodaji.ui.ppw.TakePhotoPopupWindow;
import cn.com.taodaji.viewModel.vm.ADInfoViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.extend.TakePhotoUtils;

public class AddedPicturesFragment extends TakePhotoFragment {

    private SingleRecyclerViewAdapter customerSimpleRecyclerViewAdapter;
    //图片上传完成接口
    private UploadPicturesDoneListener uploadPicturesDoneListener;
    private int RESULT_LOAD_IMAGE = 200;
    private boolean isUploadDone = true;//是否上传完毕
    private boolean isCallBack = false;//是否需要回调
    private int image_index = 1000;
    private int maxSelect = 5;
    private View mRootView;
    private SparseArrayCompat<TImage> list_timage = new SparseArrayCompat<>();
    private boolean isCrop = true;

    private String watermarkString = "";//添加水印
    private TakePhotoPopupWindow takePhotoPopupWindow;
    private boolean isCompress=true;
    private int type;

    public boolean isCompress() {
        return isCompress;
    }

    public void setCompress(boolean compress) {
        isCompress = compress;
    }

    public void setWatermarkString(String watermarkString) {
        this.watermarkString = watermarkString;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView(inflater, container, savedInstanceState);
        } else {
            // 防止重复加载，出现闪退
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    private View view;

    public void setBackgroundColor(@ColorRes int colorInt) {
        view.setBackgroundColor(UIUtils.getColor(colorInt));
    }

    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.customer_added_pictures_group, container, false);

        RecyclerView recyclerView = ViewUtils.findViewById(view, R.id.added_image_group);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(DensityUtil.dp2px(5)));

        customerSimpleRecyclerViewAdapter = new SingleRecyclerViewAdapter() {
            @Override
            public void initBaseVM() {
                putBaseVM(TYPE_CHILD, new ADInfoViewModel());
                putBaseVM(100, new ADInfoViewModel());
            }

            @Override
            public int concludeItemViewType(Object obj) {
                if (obj == null) return TYPE_CHILD;
                if (obj.getClass() == ADInfo.class) {
                    ADInfo adInfo = (ADInfo) obj;
                    if (adInfo.getImageId().equals("10000")) return 100;
                }
                return super.concludeItemViewType(obj);
            }

            @Override
            public View onCreateHolderView(ViewGroup parent, int viewType) {
                View view1 = ViewUtils.getFragmentView(parent, R.layout.customer_added_pictures_item);

                int w = parent.getMeasuredWidth();

                int spac = parent.getPaddingEnd() + parent.getPaddingStart() + DensityUtil.dp2px(6) * 3;


                UIUtils.setViewSize(view1, (w - spac) / 4 - 1, (w - spac) / 4);
                return view1;
            }

            @Override
            public void onBindViewHolderCustomer(BaseViewHolder holder, int position) {
                super.onBindViewHolderCustomer(holder, position);
//                String imageId = String.valueOf(JavaMethod.getFieldValue(getListBean(position), "imageId"));
//                if (imageId.equals("10000")) {

                if (getItemViewType(position) == 100) {
                    holder.findViewById(R.id.delete_image).setVisibility(View.GONE);
                } else holder.findViewById(R.id.delete_image).setVisibility(View.VISIBLE);
            }

            @Override
            public boolean onItemClick(View view, int onclickType, int position, Object itemBean) {
                if (onclickType == 0) {
                    ADInfo bean = (ADInfo) itemBean;
                    if (bean == null) return true;

                    if (bean.getImageId().equals("10000")) {
                        /**
                         *打开相册
                         */
                        String fileName = System.currentTimeMillis() + ".jpg";
                        Uri imageUri = Uri.fromFile(IOUtils.getFile(fileName));
                       // TakePhotoUtils.getInstance().setCrop(isCrop).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), maxSelect - position, true);


                        if (takePhotoPopupWindow == null) {
                            takePhotoPopupWindow = new TakePhotoPopupWindow(view) {
                                @Override
                                public void goCamera() {
                                    if (type==1){
                                        TakePhotoUtils.getInstance().setCrop(isCrop).setCorpOwnTool(true).setCorpSize(false,400,400).setImageUri(imageUri).openCamera(getTakePhoto());

                                    }else if (type==2){
                                        TakePhotoUtils.getInstance().setCrop(false).setImageUri(imageUri).openCamera(getTakePhoto());

                                    }else {
                                        TakePhotoUtils.getInstance().setCrop(isCrop).setImageUri(imageUri).openCamera(getTakePhoto());

                                    }

                                }

                                @Override
                                public void goAlbum() {
                                    if (type==1){
                                        TakePhotoUtils.getInstance().setCrop(isCrop).setCorpOwnTool(true).setCorpSize(false,400,400).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), maxSelect - position, true);


                                    }else if (type==2){
                                        TakePhotoUtils.getInstance().setCrop(false).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), maxSelect - position, true);

                                    }else {
                                        TakePhotoUtils.getInstance().setCrop(isCrop).setImageUri(imageUri).openPhotoAlbum(getTakePhoto(), maxSelect - position, true);

                                    }
                                }
                            };
                            takePhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                        } else {
                            if (!takePhotoPopupWindow.isShowing()) {
                                takePhotoPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0 );
                            }

                        }
                    } else if (view.getId() == R.id.delete_image) {
                        list_timage.remove(Integer.valueOf(bean.getImageId()));
                        customerSimpleRecyclerViewAdapter.remove(position);


                        loadAdd();

                    } else
                        ImagesActivity.startActivity(recyclerView, R.id.image, "imageObject", position);
//                        ZoomImageActivity.startActivity(view.getContext(), bean.getImageObject());

                    return true;
                } else return super.onItemClick(view, onclickType, position, itemBean);
            }
        };

        recyclerView.setAdapter(customerSimpleRecyclerViewAdapter);

        loadAdd();
        return view;
    }


    private void loadAdd() {
        if (getAddIndex() == -1) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId("10000");
            if (PublicCache.loginSupplier != null)
                adInfo.setImageObject(R.mipmap.ic_added_image_sup);
            else adInfo.setImageObject(R.mipmap.ic_added_image_pur);
            customerSimpleRecyclerViewAdapter.loadMore(adInfo);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int getAddIndex() {
        List<ADInfo> list = getList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getImageId().compareTo("10000") == 0) return i;
        }

        return -1;
    }


    public void setList(List<ADInfo> adInfos) {

        if (customerSimpleRecyclerViewAdapter.getRealCount() > 0) {
            customerSimpleRecyclerViewAdapter.clear();
        }

        customerSimpleRecyclerViewAdapter.notifyDataSetChanged(adInfos);

        if (adInfos.size() < maxSelect) {
            loadAdd();
        }
    }

    public void setImageStrs(String imageStrs) {

        if (TextUtils.isEmpty(imageStrs)) return;
        String[] imageurl = imageStrs.split(",");

        if (imageurl.length < maxSelect) {
            loadAdd();
        }

        for (int i = 0; i < imageurl.length; i++) {
            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(i + "");
            adInfo.setImageUrl(imageurl[i]);
            adInfo.setImageObject(imageurl[i]);
            customerSimpleRecyclerViewAdapter.insert(adInfo, customerSimpleRecyclerViewAdapter.getLastPosition());
        }

    }

    public void setMaxSelect(int maxSelect) {
        this.maxSelect = maxSelect;
    }


    public void setCrop(boolean crop) {
        isCrop = crop;
    }

    public List<ADInfo> getList() {
        //     List<ADInfo> list = new ArrayList<>();
        //   List  ll=customerSimpleRecyclerViewAdapter.getList();
        return customerSimpleRecyclerViewAdapter.getList();
    }

    public String getImageStr() {
        List<ADInfo> list = getList();
        if (list.size() == 1) return "";
        String gallery = "";
        for (int i = 0; i <= list.size() - 1; i++) {
            if (list.get(i).getImageUrl() != null) {
                gallery += list.get(i).getImageUrl() + ",";
            }
        }
        if (gallery.length() > 0) gallery = gallery.substring(0, gallery.length() - 1);
        return gallery;
    }


    //当前是否已经上传完毕
    public boolean isUploadDone() {
        return isUploadDone;
    }

    //设置 上传完成后是否需要自动回调接口
    public void setCallBack(boolean isCallBack) {
        this.isCallBack = isCallBack;
    }


    @Override
    public void takeSuccess(TResult result) {

        for (TImage tImage : result.getImages()) {
            image_index++;

            list_timage.put(image_index, tImage);


            ADInfo adInfo = new ADInfo();
            adInfo.setImageId(image_index + "");
            if (isCompress) {
                adInfo.setImageObject(tImage.getCompressPath());
            }else {
                adInfo.setImageObject(tImage.getOriginalPath());
            }


            if (customerSimpleRecyclerViewAdapter.getRealCount() == maxSelect) {
                customerSimpleRecyclerViewAdapter.remove(maxSelect - 1);
                customerSimpleRecyclerViewAdapter.loadMore(adInfo);
            } else
                customerSimpleRecyclerViewAdapter.insert(adInfo, customerSimpleRecyclerViewAdapter.getLastPosition());
        }


        if (isUploadDone) upLoadImage(list_timage.keyAt(0));
    }

    public void removeImage(int image_index) {
        list_timage.remove(image_index);
        for (int i = 0; i < customerSimpleRecyclerViewAdapter.getItemCount(); i++) {
            ADInfo adInfo = (ADInfo) customerSimpleRecyclerViewAdapter.getListBean(i);
            if (Integer.valueOf(adInfo.getImageId()) == image_index) {
                customerSimpleRecyclerViewAdapter.remove(i);
                break;
            }
        }
        loadAdd();
        //最后一张图片上传失败时，回调
        if (list_timage.size() == 0) {
            isUploadDone = true;
            if (isCallBack) {
                uploadPicturesDoneListener.uploadPicturesIsDone(getImageStr());
            }
        }
    }

    public void upLoadImage(int image_index) {
        isUploadDone = false;//将要上传图片，所没有上传完毕
        TImage tImage = list_timage.get(image_index);
        //图片压缩
        Bitmap bitmap;
        String fileName;
        if (isCompress) {
             bitmap = BitmapUtil.getSmallBitmap(tImage.getCompressPath());
             fileName=tImage.getCompressPath();
        }else {
             bitmap = BitmapUtil.getSmallBitmap(tImage.getOriginalPath());
             fileName=tImage.getOriginalPath();
        }

        if (bitmap != null) {
            if (!TextUtils.isEmpty(watermarkString)) {
                bitmap = BitmapUtil.drawTextToBitmap(bitmap, watermarkString, UIUtils.getColor(R.color.red_f0));
            }
            byte[] imagebyte = BitmapUtil.bitmapTobyte(bitmap, true);
            RequestPresenter.getInstance().upload(UIUtils.getFileNames(fileName), imagebyte, new Callback<ImageUpload>() {
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
                        UIUtils.showToastSafesShort("图片上传失败" + response.body().getMsg());
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
                    UIUtils.showToastSafesShort("图片上传失败" + t.getMessage());
                    if (list_timage.size() > 0) upLoadImage(list_timage.keyAt(0));
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UploadPicturesDoneListener) {
            uploadPicturesDoneListener = (UploadPicturesDoneListener) context;
        } else {
            Fragment fragment = getParentFragment();
            if (fragment == null)
                throw new IllegalStateException(context.getClass() + " 类，必须实现 UploadPicturesDoneListener 接口");
            else uploadPicturesDoneListener = (UploadPicturesDoneListener) fragment;

        }

    }
}
