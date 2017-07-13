package com.chinaxiaopu.xiaopuMobi.dto;


import lombok.Data;

/**
 * 应用在：
 *  社团创建
 * 
 * @author Wang
 *
 */
public @Data class GroupCreateDto extends ContextDto {

    private static final long serialVersionUID = 1L;

    private Integer groupId;//创建时不需要，修改时需要
	private Integer schoolId;
    private String schoolName;
    private String name;//社团名称
    private String slogan;//社团口号
    private String description;//社团简介
    private String categoryId;//类型Id
    private String categoryName;//类型名
    private String logoImgUrl;//社团LOGO
    private String posterImg;//社团LOGO,暂未使用
    private String[] contentImgs;//社团图片
    private String further;//社团是前台创建的{"isCreate":"1"}

}
