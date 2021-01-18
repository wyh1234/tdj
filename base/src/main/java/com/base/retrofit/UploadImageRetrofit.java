package com.base.retrofit;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static okhttp3.MultipartBody.FORM;


public class UploadImageRetrofit {


    public static MultipartBody.Part getMultipartBody_part(String fileName, byte[] content) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(FORM, content);
        return MultipartBody.Part.createFormData("image", fileName, requestFile);
    }

    public static MultipartBody.Part getMultipartBody_part(File file) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile = RequestBody.create(FORM, file);
//         RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        return MultipartBody.Part.createFormData("image", file.getName(), requestFile);
    }


}
