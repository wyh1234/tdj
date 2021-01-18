/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.com.taodaji.ui.activity.ocr;

import java.io.File;

import com.base.utils.IOUtils;

public class FileUtil {

    private static File front_file;
    private static File back_file;

    public static File getFront_file(boolean isNew) {
        if (isNew || front_file == null)
            front_file = IOUtils.getFile(System.currentTimeMillis() + ".jpg");
        return front_file;
    }

    public static File getBack_file(boolean isNew) {
        if (isNew || back_file == null)
            back_file = IOUtils.getFile(System.currentTimeMillis() + ".jpg");
        return back_file;
    }


}
