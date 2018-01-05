package com.wxbooking.shuding.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wxbooking.shuding.base.BaseBean;

public class ManagerBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String manager_id;
	private String manager_name;
	private String account_enable;
	private String phone;
	private String manager_type;

	public ManagerBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}

	public String getAccount_enable() {
		return account_enable;
	}

	public void setAccount_enable(String account_enable) {
		this.account_enable = account_enable;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getManager_type() {
		return manager_type;
	}

	public void setManager_type(String manager_type) {
		this.manager_type = manager_type;
	}

	public String getManager_id() {
		return manager_id;
	}

	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}

	public ManagerBean(String manager_id, String manager_name,
			String account_enable, String phone, String manager_type) {
		super();
		this.manager_id = manager_id;
		this.manager_name = manager_name;
		this.account_enable = account_enable;
		this.phone = phone;
		this.manager_type = manager_type;
		
	}
	
	@Override
	public String toString() {
		return "ManagerBean [manager_id=" + manager_id + ", manager_name="
				+ manager_name + ", account_enable=" + account_enable
				+ ", phone=" + phone + ", manager_type=" + manager_type + "]";
	}

	/**
	 * 解析 获得管理员数据
	 * 
	 * @param arr
	 * @return
	 */
	public static List<ManagerBean> getManagerList(JSONArray arr) {
		List<ManagerBean> managerList = new ArrayList<ManagerBean>();

		for (int i = 0; i < arr.length(); i++) {
			ManagerBean managerbean = new ManagerBean();
			try {
				JSONObject object = (JSONObject) arr.get(i);
				String manager_id = object.optString("manager_id");
				managerbean.setManager_id(manager_id);
				
				String manager_name = object.optString("manager_name");
				managerbean.setManager_name(manager_name);

				String account_enable = object.optString("account_enable");
				managerbean.setAccount_enable(account_enable);

				String phone = object.optString("phone");
				managerbean.setPhone(phone);

				String manager_type = object.optString("manager_type");
				managerbean.setManager_type(manager_type);

				managerList.add(managerbean);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return managerList;

	}

}
