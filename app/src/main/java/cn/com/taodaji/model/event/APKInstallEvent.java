package cn.com.taodaji.model.event;

import java.io.File;

/**
 * 下载完成，可以安装事件
 */
public class APKInstallEvent {
    private File file;

    private String version_info;

    public APKInstallEvent(File file, String version_info) {
        this.file = file;
        this.version_info = version_info;
    }

    public String getVersion_info() {
        return version_info;
    }

    public void setVersion_info(String version_info) {
        this.version_info = version_info;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
