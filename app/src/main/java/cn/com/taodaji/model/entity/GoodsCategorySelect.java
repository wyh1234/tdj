package cn.com.taodaji.model.entity;

import java.util.List;

public class GoodsCategorySelect {

    /**
     * err : 0
     * data : {"list":[{"categoryId":10,"children":[{"categoryId":15,"children":[{"categoryId":10005115,"level":0,"name":"白菜苗","parentId":15,"parentName":"叶菜类","position":0,"priceDiscount":0,"retailCommission":0,"status":0,"typeId":0,"wholesaleCommission":0}],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E5%8F%B6%E8%8F%9C%E7%B1%BB.png","level":3,"name":"叶菜类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":9,"typeId":89,"wholesaleCommission":0},{"categoryId":16,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E6%A0%B9%E8%8C%8E%E7%B1%BB.png","level":3,"name":"根茎类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":90,"wholesaleCommission":0},{"categoryId":17,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E8%8C%84%E6%9E%9C%E7%B1%BB.png","level":3,"name":"茄果类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":91,"wholesaleCommission":0},{"categoryId":18,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E5%90%AB%E8%B1%86%E7%B1%BB.png","level":3,"name":"含豆类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":92,"wholesaleCommission":0},{"categoryId":19,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E8%8F%8C%E8%8F%87%E7%B1%BB.png","level":3,"name":"菌菇类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":93,"wholesaleCommission":0},{"categoryId":20,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E8%91%B1%E5%A7%9C%E8%92%9C.png","level":3,"name":"葱姜蒜","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":94,"wholesaleCommission":0},{"categoryId":30,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E8%B1%86%E5%88%B6%E5%93%81.png","level":3,"name":"豆制品","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":104,"wholesaleCommission":0}],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1806091121060e862ffa.png","level":2,"name":"新鲜蔬菜","parentId":9,"position":0,"priceDiscount":1.3,"recommendImage":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1806091121175ec53703.jpg","retailCommission":0,"status":1,"typeId":84,"wholesaleCommission":0}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * categoryId : 10
             * children : [{"categoryId":15,"children":[{"categoryId":10005115,"level":0,"name":"白菜苗","parentId":15,"parentName":"叶菜类","position":0,"priceDiscount":0,"retailCommission":0,"status":0,"typeId":0,"wholesaleCommission":0}],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E5%8F%B6%E8%8F%9C%E7%B1%BB.png","level":3,"name":"叶菜类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":9,"typeId":89,"wholesaleCommission":0},{"categoryId":16,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E6%A0%B9%E8%8C%8E%E7%B1%BB.png","level":3,"name":"根茎类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":90,"wholesaleCommission":0},{"categoryId":17,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E8%8C%84%E6%9E%9C%E7%B1%BB.png","level":3,"name":"茄果类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":91,"wholesaleCommission":0},{"categoryId":18,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E5%90%AB%E8%B1%86%E7%B1%BB.png","level":3,"name":"含豆类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":92,"wholesaleCommission":0},{"categoryId":19,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E8%8F%8C%E8%8F%87%E7%B1%BB.png","level":3,"name":"菌菇类","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":93,"wholesaleCommission":0},{"categoryId":20,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E8%91%B1%E5%A7%9C%E8%92%9C.png","level":3,"name":"葱姜蒜","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":94,"wholesaleCommission":0},{"categoryId":30,"children":[],"imageUrl":"http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E8%B1%86%E5%88%B6%E5%93%81.png","level":3,"name":"豆制品","parentId":10,"position":0,"priceDiscount":1.3,"retailCommission":0,"status":1,"typeId":104,"wholesaleCommission":0}]
             * imageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1806091121060e862ffa.png
             * level : 2
             * name : 新鲜蔬菜
             * parentId : 9
             * position : 0
             * priceDiscount : 1.3
             * recommendImage : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/1806091121175ec53703.jpg
             * retailCommission : 0
             * status : 1
             * typeId : 84
             * wholesaleCommission : 0
             */

            private int categoryId;
            private String imageUrl;
            private int level;
            private String name;
            private int parentId;
            private int position;
            private double priceDiscount;
            private String recommendImage;
            private int retailCommission;
            private int status;
            private int typeId;
            private int wholesaleCommission;
            private List<ChildrenBeanX> children;

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public int getPosition() {
                return position;
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public double getPriceDiscount() {
                return priceDiscount;
            }

            public void setPriceDiscount(double priceDiscount) {
                this.priceDiscount = priceDiscount;
            }

            public String getRecommendImage() {
                return recommendImage;
            }

            public void setRecommendImage(String recommendImage) {
                this.recommendImage = recommendImage;
            }

            public int getRetailCommission() {
                return retailCommission;
            }

            public void setRetailCommission(int retailCommission) {
                this.retailCommission = retailCommission;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public int getWholesaleCommission() {
                return wholesaleCommission;
            }

            public void setWholesaleCommission(int wholesaleCommission) {
                this.wholesaleCommission = wholesaleCommission;
            }

            public List<ChildrenBeanX> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBeanX> children) {
                this.children = children;
            }

            public static class ChildrenBeanX {
                /**
                 * categoryId : 15
                 * children : [{"categoryId":10005115,"level":0,"name":"白菜苗","parentId":15,"parentName":"叶菜类","position":0,"priceDiscount":0,"retailCommission":0,"status":0,"typeId":0,"wholesaleCommission":0}]
                 * imageUrl : http://tsp-img.oss-cn-hangzhou.aliyuncs.com/%E5%8F%B6%E8%8F%9C%E7%B1%BB.png
                 * level : 3
                 * name : 叶菜类
                 * parentId : 10
                 * position : 0
                 * priceDiscount : 1.3
                 * retailCommission : 0
                 * status : 9
                 * typeId : 89
                 * wholesaleCommission : 0
                 */

                private int categoryId;
                private String imageUrl;
                private int level;
                private String name;
                private int parentId;
                private int position;
                private double priceDiscount;
                private int retailCommission;
                private int status;
                private int typeId;
                private int wholesaleCommission;
                private List<ChildrenBean> children;

                public int getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(int categoryId) {
                    this.categoryId = categoryId;
                }

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getParentId() {
                    return parentId;
                }

                public void setParentId(int parentId) {
                    this.parentId = parentId;
                }

                public int getPosition() {
                    return position;
                }

                public void setPosition(int position) {
                    this.position = position;
                }

                public double getPriceDiscount() {
                    return priceDiscount;
                }

                public void setPriceDiscount(double priceDiscount) {
                    this.priceDiscount = priceDiscount;
                }

                public int getRetailCommission() {
                    return retailCommission;
                }

                public void setRetailCommission(int retailCommission) {
                    this.retailCommission = retailCommission;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getTypeId() {
                    return typeId;
                }

                public void setTypeId(int typeId) {
                    this.typeId = typeId;
                }

                public int getWholesaleCommission() {
                    return wholesaleCommission;
                }

                public void setWholesaleCommission(int wholesaleCommission) {
                    this.wholesaleCommission = wholesaleCommission;
                }

                public List<ChildrenBean> getChildren() {
                    return children;
                }

                public void setChildren(List<ChildrenBean> children) {
                    this.children = children;
                }

                public static class ChildrenBean {
                    /**
                     * categoryId : 10005115
                     * level : 0
                     * name : 白菜苗
                     * parentId : 15
                     * parentName : 叶菜类
                     * position : 0
                     * priceDiscount : 0
                     * retailCommission : 0
                     * status : 0
                     * typeId : 0
                     * wholesaleCommission : 0
                     */

                    private int categoryId;
                    private int level;
                    private String name;
                    private int parentId;
                    private String parentName;
                    private int position;
                    private int priceDiscount;
                    private int retailCommission;
                    private int status;
                    private int typeId;
                    private int wholesaleCommission;

                    public int getCategoryId() {
                        return categoryId;
                    }

                    public void setCategoryId(int categoryId) {
                        this.categoryId = categoryId;
                    }

                    public int getLevel() {
                        return level;
                    }

                    public void setLevel(int level) {
                        this.level = level;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public int getParentId() {
                        return parentId;
                    }

                    public void setParentId(int parentId) {
                        this.parentId = parentId;
                    }

                    public String getParentName() {
                        return parentName;
                    }

                    public void setParentName(String parentName) {
                        this.parentName = parentName;
                    }

                    public int getPosition() {
                        return position;
                    }

                    public void setPosition(int position) {
                        this.position = position;
                    }

                    public int getPriceDiscount() {
                        return priceDiscount;
                    }

                    public void setPriceDiscount(int priceDiscount) {
                        this.priceDiscount = priceDiscount;
                    }

                    public int getRetailCommission() {
                        return retailCommission;
                    }

                    public void setRetailCommission(int retailCommission) {
                        this.retailCommission = retailCommission;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public int getTypeId() {
                        return typeId;
                    }

                    public void setTypeId(int typeId) {
                        this.typeId = typeId;
                    }

                    public int getWholesaleCommission() {
                        return wholesaleCommission;
                    }

                    public void setWholesaleCommission(int wholesaleCommission) {
                        this.wholesaleCommission = wholesaleCommission;
                    }
                }
            }
        }
    }
}
