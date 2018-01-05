package com.wxbooking.shuding.frame;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.tab.TabOrderFragment;
import com.wxbooking.shuding.tab.TabSettingFragment;
import com.wxbooking.shuding.tab.TabClientFragment;

public enum MainTab {

	ORDER(0, R.string.tab_order, R.drawable.tab_order_btn,
			TabOrderFragment.class),

	CLIENT(2, R.string.tab_client, R.drawable.tab_client_btn,
			TabClientFragment.class),

	SETTING(3, R.string.tab_setting, R.drawable.tab_setting_btn,
			TabSettingFragment.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}

}
