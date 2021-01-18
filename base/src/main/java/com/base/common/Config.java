package com.base.common;

import com.base.utils.IOUtils;

/**
 * Created by yangkuo on 2018-06-13.
 */
public interface Config {
    // 更新包在sdk中的存储位置
    String APKSaveDir = IOUtils.getSdCardPath() + "/tdj";

    // 图片在sdk中的存储位置
    String imageSaveDir = APKSaveDir + "/image";

    // 图片在sdk中的存储位置
    String glideImageSaveDir = APKSaveDir + "/glideImage";
}
