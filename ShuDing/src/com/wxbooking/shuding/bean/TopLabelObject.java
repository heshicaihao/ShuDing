package com.wxbooking.shuding.bean;

import com.wxbooking.shuding.base.BaseBean;

/**
 * 标签实体类
 */
public class TopLabelObject extends BaseBean{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
    public boolean isSelect;
    public String name;

    public TopLabelObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TopLabelObject() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}