package tools.loadingDownFile;

import java.io.File;

public class FileLoadingBean {

    public FileLoadingBean(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }

    public FileLoadingBean(File file) {
        this.file = file;
    }

    /**
     * 文件大小
     */
    private long total;


    /**
     * 已下载大小
     */
    private long progress;


    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public long getProgress() {
        return progress;
    }

    public long getTotal() {
        return total;
    }


    public void setProgress(long progress) {
        this.progress = progress;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
