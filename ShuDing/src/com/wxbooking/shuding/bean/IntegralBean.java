package com.wxbooking.shuding.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wxbooking.shuding.base.BaseBean;

public class IntegralBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String integral_id;
	private String reason;
	private String integral_num;
	private String nick_name;
	private String real_name;
	private String phone;
	private String time;

	public IntegralBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getIntegral_id() {
		return integral_id;
	}

	public void setIntegral_id(String integral_id) {
		this.integral_id = integral_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIntegral_num() {
		return integral_num;
	}

	public void setIntegral_num(String integral_num) {
		this.integral_num = integral_num;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "IntegralBean [integral_id=" + integral_id + ", reason="
				+ reason + ", integral_num=" + integral_num + ", nick_name="
				+ nick_name + ", real_name=" + real_name + ", phone=" + phone
				+ ", time=" + time + "]";
	}

	public IntegralBean(String integral_id, String reason, String integral_num,
			String nick_name, String real_name, String phone, String time) {
		super();
		this.integral_id = integral_id;
		this.reason = reason;
		this.integral_num = integral_num;
		this.nick_name = nick_name;
		this.real_name = real_name;
		this.phone = phone;
		this.time = time;
	}
	
	/**
	 * 解析 获得管理员数据
	 * 
	 * @param arr
	 * @return
	 */
	public static List<IntegralBean> getIntegralList(JSONArray arr) {
		List<IntegralBean> integralList = new ArrayList<IntegralBean>();

		for (int i = 0; i < arr.length(); i++) {
			IntegralBean integralbean = new IntegralBean();
			try {
				JSONObject object = (JSONObject) arr.get(i);
				
				String integral_id = object.optString("integral_id");
				integralbean.setIntegral_id(integral_id);
				
				String reason = object.optString("reason");
				integralbean.setReason(reason);
				
				String integral_num = object.optString("integral_num");
				integralbean.setIntegral_num(integral_num);

				String nick_name = object.optString("nick_name");
				integralbean.setNick_name(nick_name);
				
				String real_name = object.optString("real_name");
				integralbean.setReal_name(real_name);

				String phone = object.optString("phone");
				integralbean.setPhone(phone);

				String time = object.optString("time");
				integralbean.setTime(time);
				
				integralList.add(integralbean);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return integralList;

	}


}
