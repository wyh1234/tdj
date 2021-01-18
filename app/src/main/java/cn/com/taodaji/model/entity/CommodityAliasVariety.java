package cn.com.taodaji.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class CommodityAliasVariety {

    /**
     * err : 0
     * data : {"aliasList":[{"aliasName":"火火1","aliasEntityId":3},{"aliasName":"火哥","aliasEntityId":5}],"varietyList":[{"varietyName":"山东火葱","varietyEntityId":16},{"varietyName":"河南火葱","varietyEntityId":17},{"varietyName":"葱哥","varietyEntityId":19}]}
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
        private List<AliasListBean> aliasList;
        private List<VarietyListBean> varietyList;



        public List<AliasListBean> getAliasList() {
            return aliasList;
        }

        public void setAliasList(List<AliasListBean> aliasList) {
            this.aliasList = aliasList;
        }

        public List<VarietyListBean> getVarietyList() {
            return varietyList;
        }

        public void setVarietyList(List<VarietyListBean> varietyList) {
            this.varietyList = varietyList;
        }

        public static class AliasListBean {
            /**
             * aliasName : 火火1
             * aliasEntityId : 3
             */

            private String aliasName;
            private int aliasEntityId;

            public String getAliasName() {
                return aliasName;
            }

            public void setAliasName(String aliasName) {
                this.aliasName = aliasName;
            }

            public int getAliasEntityId() {
                return aliasEntityId;
            }

            public void setAliasEntityId(int aliasEntityId) {
                this.aliasEntityId = aliasEntityId;
            }
        }

        public static class VarietyListBean {
            /**
             * varietyName : 山东火葱
             * varietyEntityId : 16
             */

            private String varietyName;
            private int varietyEntityId;
            private SpecInfo specInfo;

            public SpecInfo getSpecInfo() {
                return specInfo;
            }

            public void setSpecInfo(SpecInfo specInfo) {
                this.specInfo = specInfo;
            }

            public String getVarietyName() {
                return varietyName;
            }

            public void setVarietyName(String varietyName) {
                this.varietyName = varietyName;
            }

            public int getVarietyEntityId() {
                return varietyEntityId;
            }

            public void setVarietyEntityId(int varietyEntityId) {
                this.varietyEntityId = varietyEntityId;
            }
            public static class SpecInfo{

                /**
                 * entityId : 8
                 * templateId : 77
                 * level1Unit : 箱
                 * level2Value : 11
                 * level2Unit : 斤
                 * level3Value : 0
                 * level3Unit :
                 * levelType : 2
                 * specName : 规格名称
                 * bImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/201009151909e2c1c2b3.jpg
                 * cImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/20100915191717011343.jpg
                 */

                private int entityId;
                private int templateId;
                private String level1Unit;
                private BigDecimal level2Value;
                private String level2Unit;
                private BigDecimal level3Value;
                private String level3Unit;
                private int levelType;
                private String specName;
                private String bImage;
                private String cImage;
                private String dImage;

                private int stock;
                private BigDecimal price = BigDecimal.ZERO;

                public String getdImage() {
                    return dImage;
                }

                public void setdImage(String dImage) {
                    this.dImage = dImage;
                }

                public String getbImage() {
                    return bImage;
                }

                public void setbImage(String bImage) {
                    this.bImage = bImage;
                }

                public String getcImage() {
                    return cImage;
                }

                public void setcImage(String cImage) {
                    this.cImage = cImage;
                }

                public int getStock() {
                    return stock;
                }

                public void setStock(int stock) {
                    this.stock = stock;
                }

                public BigDecimal getPrice() {
                    return price;
                }

                public void setPrice(BigDecimal price) {
                    this.price = price;
                }

                public int getEntityId() {
                    return entityId;
                }

                public void setEntityId(int entityId) {
                    this.entityId = entityId;
                }

                public int getTemplateId() {
                    return templateId;
                }

                public void setTemplateId(int templateId) {
                    this.templateId = templateId;
                }

                public String getLevel1Unit() {
                    return level1Unit;
                }

                public void setLevel1Unit(String level1Unit) {
                    this.level1Unit = level1Unit;
                }

                public BigDecimal getLevel2Value() {
                    return level2Value;
                }

                public void setLevel2Value(BigDecimal level2Value) {
                    this.level2Value = level2Value;
                }

                public String getLevel2Unit() {
                    return level2Unit;
                }

                public void setLevel2Unit(String level2Unit) {
                    this.level2Unit = level2Unit;
                }

                public BigDecimal getLevel3Value() {
                    return level3Value;
                }

                public void setLevel3Value(BigDecimal level3Value) {
                    this.level3Value = level3Value;
                }

                public String getLevel3Unit() {
                    return level3Unit;
                }

                public void setLevel3Unit(String level3Unit) {
                    this.level3Unit = level3Unit;
                }

                public int getLevelType() {
                    return levelType;
                }

                public void setLevelType(int levelType) {
                    this.levelType = levelType;
                }

                public String getSpecName() {
                    return specName;
                }

                public void setSpecName(String specName) {
                    this.specName = specName;
                }

            }
        }


    }
}
