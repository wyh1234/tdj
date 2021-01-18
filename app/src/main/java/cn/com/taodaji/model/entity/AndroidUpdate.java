package cn.com.taodaji.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/25 0025.
 */
public class AndroidUpdate {

    /**
     * err : 0
     * data : {"entity_id":1,"create_time":"2017-03-25 11:00","download_url":"https://tdj-cui.oss-cn-qingdao.aliyuncs.com/android/tdj201906051422.apk","versioin":"3.3.0","title":"安卓更新通知","content":"一、系统功能优化。","is_update":1}
     * msg : success
     */

    private int err;
    private DataBean data;
    private String msg;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean implements Serializable {
        /**
         * entity_id : 1
         * create_time : 2017-03-25 11:00
         * download_url : https://tdj-cui.oss-cn-qingdao.aliyuncs.com/android/tdj201906051422.apk
         * versioin : 3.3.0
         * title : 安卓更新通知
         * content : 一、系统功能优化。
         * is_update : 1
         */

        private int entity_id;
        private String create_time;
        private String download_url;
        private String versioin;
        private String title;
        private String content;
        private int is_update;

        public int getEntity_id() {
            return entity_id;
        }

        public void setEntity_id(int entity_id) {
            this.entity_id = entity_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getVersioin() {
            return versioin;
        }

        public void setVersioin(String versioin) {
            this.versioin = versioin;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIs_update() {
            return is_update;
        }

        public void setIs_update(int is_update) {
            this.is_update = is_update;
        }
    }
}
