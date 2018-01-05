package com.wxbooking.shuding.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wxbooking.shuding.base.BaseBean;

public class ClientBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String client_id;
	private String image_url;
	private String client_fans;
	private String nick_name;
	private String sex;
	private String client_name;
	private String check_num;
	private String integral_num;

	public ClientBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getClient_fans() {
		return client_fans;
	}

	public void setClient_fans(String client_fans) {
		this.client_fans = client_fans;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getCheck_num() {
		return check_num;
	}

	public void setCheck_num(String check_num) {
		this.check_num = check_num;
	}

	public String getIntegral_num() {
		return integral_num;
	}

	public void setIntegral_num(String integral_num) {
		this.integral_num = integral_num;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public ClientBean(String client_id, String image_url, String client_fans,
			String nick_name, String sex, String client_name, String check_num,
			String integral_num) {
		super();
		this.client_id = client_id;
		this.image_url = image_url;
		this.client_fans = client_fans;
		this.nick_name = nick_name;
		this.sex = sex;
		this.client_name = client_name;
		this.check_num = check_num;
		this.integral_num = integral_num;
	}

	@Override
	public String toString() {
		return "ClientBean [client_id=" + client_id + ", image_url="
				+ image_url + ", client_fans=" + client_fans + ", nick_name="
				+ nick_name + ", sex=" + sex + ", client_name=" + client_name
				+ ", check_num=" + check_num + ", integral_num=" + integral_num
				+ "]";
	}

	/**
	 * 解析 获得管理员数据
	 * 
	 * @param arr
	 * @return
	 */
	public static List<ClientBean> getClientList(JSONArray arr) {
		List<ClientBean> clientList = new ArrayList<ClientBean>();

		for (int i = 0; i < arr.length(); i++) {
			ClientBean clientbean = new ClientBean();
			try {
				JSONObject object = (JSONObject) arr.get(i);
				String client_id = object.optString("client_id");
				clientbean.setClient_id(client_id);
				
				String image_url = object.optString("image_url");
				clientbean.setImage_url(image_url);

				String client_fans = object.optString("client_fans");
				clientbean.setClient_fans(client_fans);

				String nick_name = object.optString("nick_name");
				clientbean.setNick_name(nick_name);

				String sex = object.optString("sex");
				clientbean.setSex(sex);

				String client_name = object.optString("client_name");
				clientbean.setClient_name(client_name);

				String check_num = object.optString("check_num");
				clientbean.setCheck_num(check_num);

				String integral_num = object.optString("integral_num");
				clientbean.setIntegral_num(integral_num);

				clientList.add(clientbean);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return clientList;

	}

}
