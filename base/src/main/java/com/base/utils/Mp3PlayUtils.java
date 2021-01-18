package com.base.utils;

import android.media.MediaPlayer;
import android.util.Log;

import com.base.common.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Mp3PlayUtils implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer = null;
    private static Mp3PlayUtils mp3PlayUtils = null;

    private static String httpUrl = "";//网络路经
    private static String urlPath = "";//播放的路经

    private Mp3PlayUtils() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            //播放监听
            mediaPlayer.setOnCompletionListener(this);
            //准备监听
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
        }
    }

    public static Mp3PlayUtils getInstance() {
        if (mp3PlayUtils == null) mp3PlayUtils = new Mp3PlayUtils();
        return mp3PlayUtils;
    }

    //播放完成
    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.reset();
    }

    //准备完成
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    //播放出错
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public void start(String url) {
        //停止播放
        stop();
        httpUrl = url;
        //停止正在执行的播放线程
        ThreadManager.getShortPool().removeRunning(run);

        String fileName = UIUtils.getFileNames(httpUrl);
        String sDCard = Config.imageSaveDir;//文件存储路径
        File file = new File(sDCard, fileName);
        //如果本地存在则播放
        if (file.exists()) {
            urlPath = sDCard + "/" + fileName;
            //执行播放线程
            ThreadManager.getShortPool().execute(run);
        }
        //否则下载
        else {
            downMp3();
        }

    }

    //下载音频文件并保存在本地
    private void downMp3() {
        //关闭正在下载的线程
        ThreadManager.getDownloadPool().removeRunning(downRun);
        //执行新的下载线程
        ThreadManager.getDownloadPool().execute(downRun);
    }

    //播放子线程
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            try {
                mediaPlayer.setDataSource(urlPath);
                mediaPlayer.prepare();
            } catch (IOException | IllegalStateException e) {
                //出现异常时关闭该线程
                ThreadManager.getShortPool().removeRunning(this);
            }
        }
    };

    //下载音频文件并保存在本地
    private Runnable downRun = () -> {
        OutputStream output = null;
        InputStream in = null;
        String fileName = UIUtils.getFileNames(httpUrl);
        String sDCard = Config.imageSaveDir;//文件存储路径
        String pathName = sDCard + "/" + fileName;
        try {
            File file = new File(pathName);
            if (file.exists()) {
                Log.e(getClass().getSimpleName(), fileName + "  文件已存在无需下载");
            } else {
                Log.e(getClass().getSimpleName(), fileName + "  正在连接");
                URL url = new URL(httpUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                in = conn.getInputStream();
                Log.e(getClass().getSimpleName(), fileName + "  正在下载");
                file.createNewFile();////新建文件
                output = new FileOutputStream(file);
                //读取大文件
                byte[] buf = new byte[2048];
                int len;
                while ((len = in.read(buf)) != -1) {
                    output.write(buf, 0, len);
                }

                Log.e(getClass().getSimpleName(), fileName + "  下载完成");
                //播放Mp3
                start(httpUrl);
            }

        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), fileName + "  下载失败");
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (output != null) output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };

}
