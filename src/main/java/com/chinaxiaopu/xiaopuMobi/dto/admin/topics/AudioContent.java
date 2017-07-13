package com.chinaxiaopu.xiaopuMobi.dto.admin.topics;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ycy on 2016/12/6.
 */
public class AudioContent {

    private String audioTime;

    private String slogan;

    private String[] urls;

    private String desc;

    private Integer imageWidth;

    private Integer imageHeight;

    private String[] bannerImgs;

    private String[] posterImgs;
    @Setter @Getter
    private String[] coverImg;

    public String getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(String audioTime) {
        this.audioTime = audioTime;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String[] getBannerImgs() {
        return bannerImgs;
    }

    public void setBannerImgs(String[] bannerImgs) {
        this.bannerImgs = bannerImgs;
    }

    public String[] getPosterImgs() {
        return posterImgs;
    }

    public void setPosterImgs(String[] posterImgs) {
        this.posterImgs = posterImgs;
    }
}
