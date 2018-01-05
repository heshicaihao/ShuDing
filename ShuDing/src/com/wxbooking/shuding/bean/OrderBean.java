package com.wxbooking.shuding.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wxbooking.shuding.base.BaseBean;


public class OrderBean  extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String order_id;
	private String order_status;
	private String time;
	private String name;
	private String hotel_name;
	private String hotel_price;
	private String hotel_type;
	private String pay_status;
	private String into_time;
	private String out_time;
	private String order_explain;
	
	public OrderBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHotel_name() {
		return hotel_name;
	}

	public void setHotel_name(String hotel_name) {
		this.hotel_name = hotel_name;
	}

	public String getHotel_price() {
		return hotel_price;
	}

	public void setHotel_price(String hotel_price) {
		this.hotel_price = hotel_price;
	}

	public String getHotel_type() {
		return hotel_type;
	}

	public void setHotel_type(String hotel_type) {
		this.hotel_type = hotel_type;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getInto_time() {
		return into_time;
	}

	public void setInto_time(String into_time) {
		this.into_time = into_time;
	}

	public String getOut_time() {
		return out_time;
	}

	public void setOut_time(String out_time) {
		this.out_time = out_time;
	}

	public String getOrder_explain() {
		return order_explain;
	}

	public void setOrder_explain(String order_explain) {
		this.order_explain = order_explain;
	}

	@Override
	public String toString() {
		return "OrderBean [order_id=" + order_id + ", order_status="
				+ order_status + ", time=" + time + ", name=" + name
				+ ", hotel_name=" + hotel_name + ", hotel_price=" + hotel_price
				+ ", hotel_type=" + hotel_type + ", pay_status=" + pay_status
				+ ", into_time=" + into_time + ", out_time=" + out_time
				+ ", order_explain=" + order_explain + "]";
	}

	public OrderBean(String order_id, String order_status, String time,
			String name, String hotel_name, String hotel_price,
			String hotel_type, String pay_status, String into_time,
			String out_time, String order_explain) {
		super();
		this.order_id = order_id;
		this.order_status = order_status;
		this.time = time;
		this.name = name;
		this.hotel_name = hotel_name;
		this.hotel_price = hotel_price;
		this.hotel_type = hotel_type;
		this.pay_status = pay_status;
		this.into_time = into_time;
		this.out_time = out_time;
		this.order_explain = order_explain;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hotel_name == null) ? 0 : hotel_name.hashCode());
		result = prime * result
				+ ((hotel_price == null) ? 0 : hotel_price.hashCode());
		result = prime * result
				+ ((hotel_type == null) ? 0 : hotel_type.hashCode());
		result = prime * result
				+ ((into_time == null) ? 0 : into_time.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((order_explain == null) ? 0 : order_explain.hashCode());
		result = prime * result
				+ ((order_id == null) ? 0 : order_id.hashCode());
		result = prime * result
				+ ((order_status == null) ? 0 : order_status.hashCode());
		result = prime * result
				+ ((out_time == null) ? 0 : out_time.hashCode());
		result = prime * result
				+ ((pay_status == null) ? 0 : pay_status.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderBean other = (OrderBean) obj;
		if (hotel_name == null) {
			if (other.hotel_name != null)
				return false;
		} else if (!hotel_name.equals(other.hotel_name))
			return false;
		if (hotel_price == null) {
			if (other.hotel_price != null)
				return false;
		} else if (!hotel_price.equals(other.hotel_price))
			return false;
		if (hotel_type == null) {
			if (other.hotel_type != null)
				return false;
		} else if (!hotel_type.equals(other.hotel_type))
			return false;
		if (into_time == null) {
			if (other.into_time != null)
				return false;
		} else if (!into_time.equals(other.into_time))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (order_explain == null) {
			if (other.order_explain != null)
				return false;
		} else if (!order_explain.equals(other.order_explain))
			return false;
		if (order_id == null) {
			if (other.order_id != null)
				return false;
		} else if (!order_id.equals(other.order_id))
			return false;
		if (order_status == null) {
			if (other.order_status != null)
				return false;
		} else if (!order_status.equals(other.order_status))
			return false;
		if (out_time == null) {
			if (other.out_time != null)
				return false;
		} else if (!out_time.equals(other.out_time))
			return false;
		if (pay_status == null) {
			if (other.pay_status != null)
				return false;
		} else if (!pay_status.equals(other.pay_status))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	
	/**
	 * 解析 获得订单数据
	 * 
	 * @param arr
	 * @return
	 */
	public static List<OrderBean> getOrderList(JSONArray arr) {
		List<OrderBean> orderList = new ArrayList<OrderBean>();

		for (int i = 0; i < arr.length(); i++) {
			OrderBean orderbean = new OrderBean();
			try {
				JSONObject object = (JSONObject) arr.get(i);
				String order_id = object.optString("order_id");
				orderbean.setOrder_id(order_id);

				String order_status = object.optString("order_status");
				orderbean.setOrder_status(order_status);

				String time = object.optString("time");
				orderbean.setTime(time);

				String name = object.optString("name");
				orderbean.setName(name);

				String hotel_name = object.optString("hotel_name");
				orderbean.setHotel_name(hotel_name);

				String hotel_price = object.optString("hotel_price");
				orderbean.setHotel_price(hotel_price);

				String hotel_type = object.optString("hotel_type");
				orderbean.setHotel_type(hotel_type);

				String pay_status = object.optString("pay_status");
				orderbean.setPay_status(pay_status);

				String into_time = object.optString("into_time");
				orderbean.setInto_time(into_time);

				String out_time = object.optString("out_time");
				orderbean.setOut_time(out_time);
				
				String order_explain = object.optString("order_explain");
				orderbean.setOrder_explain(order_explain);
				
				orderList.add(orderbean);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return orderList;

	}
	
	
	
	
}
