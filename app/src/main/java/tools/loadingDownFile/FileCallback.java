package tools.loadingDownFile;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.base.utils.IOUtils;
import com.base.utils.ThreadManager;

public abstract class FileCallback implements Callback<ResponseBody> {

    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public FileCallback(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }


    /**
     * 请求成功后保存文件
     */
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        ThreadManager.getDownloadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    saveFile(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 通过IO流写入文件
     */
    public File saveFile(Response<ResponseBody> response) throws Exception {
        InputStream in = null;
        FileOutputStream out = null;
        byte[] buf = new byte[2048];
        int len;


        try {

            long fileSize = response.body().contentLength();
            long fileSizeDownloaded = 0;

            in = response.body().byteStream();
            File file = IOUtils.createFile(destFileDir, destFileName);
            out = new FileOutputStream(file);
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
                fileSizeDownloaded += len;

                // 通过EventBus发布进度信息
                EventBus.getDefault().post(new FileLoadingBean(fileSize, fileSizeDownloaded));
            }

            // 回调成功的接口
            EventBus.getDefault().post(new FileLoadingBean(file));
            return file;
        } finally {
            in.close();
            out.close();
        }
    }

}
