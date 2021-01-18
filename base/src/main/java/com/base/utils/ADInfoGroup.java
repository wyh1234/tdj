package com.base.utils;

import com.base.annotation.OnlyField;

import java.util.List;

public class ADInfoGroup {

    @OnlyField
    private String imageId;//图片id或商品id
    private String imageUrl;//图片地址
    @OnlyField
    private String imageName;//图片名称
    private byte[] imageByte;//图片字节流
    private String imageContent;//图片说明文字或描述
    private int imageGoodsType;//商品所属类型
    private String imageLinkHttpUrl;//图片链接地址
    private int imageResource = 0;//本地图片
    private int goodsCount = 0;
    private boolean selected;  //是否选中
    private Object imageObject;
    private List<ADInfo> listAdinfo;

    public List<ADInfo> getListAdinfo() {
        return listAdinfo;
    }

    public void setListAdinfo(List<ADInfo> listAdinfo) {
        this.listAdinfo = listAdinfo;
    }


    public Object getImageObject() {
        return imageObject;
    }

    public void setImageObject(Object imageObject) {
        this.imageObject = imageObject;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }


    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageid) {
        this.imageId = imageid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public int getImageGoodsType() {
        return imageGoodsType;
    }

    public void setImageGoodsType(int imageGoodsType) {
        this.imageGoodsType = imageGoodsType;
    }

    public String getImageLinkHttpUrl() {
        return imageLinkHttpUrl;
    }

    public void setImageLinkHttpUrl(String HttpUrl) {
        imageLinkHttpUrl = HttpUrl;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

}
