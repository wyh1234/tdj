package cn.com.taodaji.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangkuo on 2018-05-04.
 */

public class FindByIsActive implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * id : 1
         * name : 北京市
         */

        private int id;
        private String name;
        private String initials;//小程序的id

        private String phone1;
        private String phone2;


        private String addition1 = "";
        private String addition2 = "";
        private String addition3 = "";
        private String addition4 = "";
        private String announcementContent="";//公告开关0关闭；1打开
        private int announcementSwitch=0;

        private String adcode;
        private double lat,lon;

        private int h5Open; //h5页面开关，0-关闭，1-开启
        private String h5Url;
        private String h5Img;
        private String customerPurchaseTimeHint;
        private String customerPurchaseTimeControl;

        public String getCustomerPurchaseTimeHint() {
            return customerPurchaseTimeHint;
        }

        public void setCustomerPurchaseTimeHint(String customerPurchaseTimeHint) {
            this.customerPurchaseTimeHint = customerPurchaseTimeHint;
        }

        public String getCustomerPurchaseTimeControl() {
            return customerPurchaseTimeControl;
        }

        public void setCustomerPurchaseTimeControl(String customerPurchaseTimeControl) {
            this.customerPurchaseTimeControl = customerPurchaseTimeControl;
        }

        public int getH5Open() {
            return h5Open;
        }

        public void setH5Open(int h5Open) {
            this.h5Open = h5Open;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public String getH5Img() {
            return h5Img;
        }

        public void setH5Img(String h5Img) {
            this.h5Img = h5Img;
        }

        public String getAnnouncementContent() {
            return announcementContent;
        }

        public void setAnnouncementContent(String announcementContent) {
            this.announcementContent = announcementContent;
        }

        public int getAnnouncementSwitch() {
            return announcementSwitch;
        }

        public void setAnnouncementSwitch(int announcementSwitch) {
            this.announcementSwitch = announcementSwitch;
        }

        public String getAddition4() {
            return addition4;
        }

        public void setAddition4(String addition4) {
            this.addition4 = addition4;
        }

        public String getAddition1() {
            return addition1;
        }

        public void setAddition1(String addition1) {
            this.addition1 = addition1;
        }

        public String getAddition2() {
            return addition2;
        }

        public void setAddition2(String addition2) {
            this.addition2 = addition2;
        }

        public String getAddition3() {
            return addition3;
        }

        public void setAddition3(String addition3) {
            this.addition3 = addition3;
        }

        public String getPhone1() {
            return phone1;
        }

        public void setPhone1(String phone1) {
            this.phone1 = phone1;
        }

        public String getPhone2() {
            return phone2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }

        public String getInitials() {
            return initials;
        }

        public void setInitials(String initials) {
            this.initials = initials;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }
}

