package com.tlcb.bdp.admin.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个是列表树形式显示的实体,
 * 这里的字段是在前台显示所有的,可修改
 * @author numberONe
 * date：2014-11-20
 */
public class TreeObject {
	private String resId;
    private String resNm;
    private String parentId;
	private String parentName;
	private String reskey;
	private String resurl;
	private Integer level;
	private String type;
	private String description;
	private String icon;
	private Integer ishide;
	private List<TreeObject> children = new ArrayList<TreeObject>();
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public List<TreeObject> getChildren() {
		return children;
	}
	public void setChildren(List<TreeObject> children) {
		this.children = children;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getIshide() {
		return ishide;
	}
	public void setIshide(Integer ishide) {
		this.ishide = ishide;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResNm() {
		return resNm;
	}
	public void setResNm(String resNm) {
		this.resNm = resNm;
	}
	public String getReskey() {
		return reskey;
	}
	public void setReskey(String reskey) {
		this.reskey = reskey;
	}
	public String getResurl() {
		return resurl;
	}
	public void setResurl(String resurl) {
		this.resurl = resurl;
	}
	
	
}
