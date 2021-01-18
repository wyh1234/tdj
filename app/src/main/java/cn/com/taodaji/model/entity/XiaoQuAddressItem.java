package cn.com.taodaji.model.entity;

import com.amap.api.services.core.LatLonPoint;

import java.math.BigDecimal;
import java.util.List;

public class XiaoQuAddressItem {

    /**
     * err : 0
     * data : {"dataList":{"items":[{"address":"测试街道78号","area":"","beginTime":"","city":"","communityName":"测试2号","communityShortName":"222","createTime":"2020-02-12 17:29:44.0","distance":5.05,"endTime":"","id":2,"lat":"32.076749","lon":"112.131906","province":"","site":"2","status":"1","street":"","updateTime":""},{"address":"测试街道77号","area":"","beginTime":"","city":"","communityName":"测试","communityShortName":"111","createTime":"2020-02-12 16:22:13.0","distance":379.09,"endTime":"","id":1,"lat":"32.111","lon":"108.111","province":"","site":"2","status":"1","street":"","updateTime":""}],"pn":1,"ps":20,"total":2}}
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

    public static class DataBean {
        /**
         * dataList : {"items":[{"address":"测试街道78号","area":"","beginTime":"","city":"","communityName":"测试2号","communityShortName":"222","createTime":"2020-02-12 17:29:44.0","distance":5.05,"endTime":"","id":2,"lat":"32.076749","lon":"112.131906","province":"","site":"2","status":"1","street":"","updateTime":""},{"address":"测试街道77号","area":"","beginTime":"","city":"","communityName":"测试","communityShortName":"111","createTime":"2020-02-12 16:22:13.0","distance":379.09,"endTime":"","id":1,"lat":"32.111","lon":"108.111","province":"","site":"2","status":"1","street":"","updateTime":""}],"pn":1,"ps":20,"total":2}
         */

        private DataBean.DataListBean dataList;

        public DataBean.DataListBean getDataList() {
            return dataList;
        }

        public void setDataList(DataBean.DataListBean dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * items : [{"address":"测试街道78号","area":"","beginTime":"","city":"","communityName":"测试2号","communityShortName":"222","createTime":"2020-02-12 17:29:44.0","distance":5.05,"endTime":"","id":2,"lat":"32.076749","lon":"112.131906","province":"","site":"2","status":"1","street":"","updateTime":""},{"address":"测试街道77号","area":"","beginTime":"","city":"","communityName":"测试","communityShortName":"111","createTime":"2020-02-12 16:22:13.0","distance":379.09,"endTime":"","id":1,"lat":"32.111","lon":"108.111","province":"","site":"2","status":"1","street":"","updateTime":""}]
             * pn : 1
             * ps : 20
             * total : 2
             */

            private int pn;
            private int ps;
            private int total;
            private List<DataBean.DataListBean.ItemsBean> items;

            public int getPn() {
                return pn;
            }

            public void setPn(int pn) {
                this.pn = pn;
            }

            public int getPs() {
                return ps;
            }

            public void setPs(int ps) {
                this.ps = ps;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<DataBean.DataListBean.ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<DataBean.DataListBean.ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                /**
                 * address : 测试街道78号
                 * area :
                 * beginTime :
                 * city :
                 * communityName : 测试2号
                 * communityShortName : 222
                 * createTime : 2020-02-12 17:29:44.0
                 * distance : 5.05
                 * endTime :
                 * id : 2
                 * lat : 32.076749
                 * lon : 112.131906
                 * province :
                 * site : 2
                 * status : 1
                 * street :
                 * updateTime :
                 */
                private String fenceGid;
                private String address;
                private String area;
                private String beginTime;
                private String city;
                private String communityName;
                private String masterPhone;
                private String communityShortName;
                private String createTime;
                private BigDecimal distance;
                private String endTime;
                private int id;
                private String lat;
                private String lon;
                private String province;
                private String site;
                private String status;
                private String street;
                private String updateTime;

                public String getMasterPhone() {
                    return masterPhone;
                }

                public void setMasterPhone(String masterPhone) {
                    this.masterPhone = masterPhone;
                }

                public String getFenceGid() {
                    return fenceGid;
                }

                public void setFenceGid(String fenceGid) {
                    this.fenceGid = fenceGid;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public String getBeginTime() {
                    return beginTime;
                }

                public void setBeginTime(String beginTime) {
                    this.beginTime = beginTime;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getCommunityName() {
                    return communityName;
                }

                public void setCommunityName(String communityName) {
                    this.communityName = communityName;
                }

                public String getCommunityShortName() {
                    return communityShortName;
                }

                public void setCommunityShortName(String communityShortName) {
                    this.communityShortName = communityShortName;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public BigDecimal getDistance() {
                    return distance;
                }

                public void setDistance(BigDecimal distance) {
                    this.distance = distance;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLon() {
                    return lon;
                }

                public void setLon(String lon) {
                    this.lon = lon;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getSite() {
                    return site;
                }

                public void setSite(String site) {
                    this.site = site;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getStreet() {
                    return street;
                }

                public void setStreet(String street) {
                    this.street = street;
                }

                public String getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(String updateTime) {
                    this.updateTime = updateTime;
                }
            }
        }
    }
}
