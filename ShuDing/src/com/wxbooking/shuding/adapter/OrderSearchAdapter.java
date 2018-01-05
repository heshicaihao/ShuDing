package com.wxbooking.shuding.adapter;

import java.util.List;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.activity.OrderDetailsActivity;
import com.wxbooking.shuding.bean.OrderBean;
import com.wxbooking.shuding.utils.AndroidUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderSearchAdapter extends BaseAdapter {
	public String TAG = getClass().getName();

	private Context mContext;
	private Activity mActivity;
	private List<OrderBean> mData;
	private LayoutInflater mInflater;
	private OrderHolder mHolder;


	public OrderSearchAdapter(Activity mActivity, Context context) {
		this.mContext = context;
		this.mActivity = mActivity;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		int cout = 0;
		if (mData == null) {
			cout = 0;
		} else {
			cout = mData.size();
		}
		return cout;
	}

	/**
	 * 设置数据
	 * 
	 * @param mData
	 */
	public void setData(List<OrderBean> mData) {
		this.mData = mData;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView,
			ViewGroup parent) {
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.item_order_list, null);
			mHolder = new OrderHolder();
			getItemView(convertView);
			convertView.setTag(mHolder);
		} else {
			mHolder = (OrderHolder) convertView.getTag();
		}
		setData2UI(position);
		
		mHolder.item_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				gotoOrderDetailsActivity(position);
			}

		});
		

		return convertView;
	}

	/**
	 * 找到Item 的 控件
	 * 
	 * @param convertView
	 */
	private void getItemView(View convertView) {
		mHolder.order_status = (TextView) convertView
				.findViewById(R.id.order_status);
		mHolder.time = (TextView) convertView.findViewById(R.id.time);
		mHolder.name = (TextView) convertView.findViewById(R.id.client_name);
		mHolder.hotel_name = (TextView) convertView
				.findViewById(R.id.hotel_name);
		mHolder.hotel_price = (TextView) convertView
				.findViewById(R.id.hotel_price);
		mHolder.hotel_type = (TextView) convertView
				.findViewById(R.id.hotel_type);
		mHolder.pay_status = (TextView) convertView
				.findViewById(R.id.pay_status);
		mHolder.into_time = (TextView) convertView
				.findViewById(R.id.into_time);
		mHolder.out_time = (TextView) convertView
				.findViewById(R.id.out_time);
		mHolder.order_explain = (TextView) convertView
				.findViewById(R.id.order_explain);
		mHolder.item_view = (LinearLayout) convertView
				.findViewById(R.id.item_view);

	}

	/**
	 * 给UI加载数据
	 * 
	 * @param position
	 * @param deleteListener
	 * @param editListener
	 */
	private void setData2UI(int position) {
		OrderBean object = mData.get(position);
		// String createtimeStr = object.getCreatetime();
		// long createtime = Long.parseLong(createtimeStr);
		// String time = StringUtils.longToDate(createtime);
		// mHolder.time.setText(time);

		String order_status = object.getOrder_status();
		mHolder.order_status.setText(order_status);

		String time = object.getTime();
		mHolder.time.setText(time);

		String name = object.getName();
		mHolder.name.setText(name);

		String hotel_name = object.getHotel_name();
		mHolder.hotel_name.setText(hotel_name);

		String hotel_price = object.getHotel_price();
		mHolder.hotel_price.setText("¥ "+hotel_price);

		String hotel_type = object.getHotel_type();
		mHolder.hotel_type.setText(hotel_type);

		String pay_status = object.getPay_status();
		mHolder.pay_status.setText(pay_status);

		String into_time = object.getInto_time();
		mHolder.into_time.setText(into_time);

		String out_time = object.getOut_time();
		mHolder.out_time.setText(out_time);

		String order_explain = object.getOrder_explain();
		mHolder.order_explain.setText(order_explain);

	}

	/**
	 * 跳转到 OrderDetailsActivity
	 * 
	 * @param position
	 */
	private void gotoOrderDetailsActivity(int position) {
		OrderBean object = mData.get(position);
		String mOutTradeNo = object.getOrder_id();
		Intent intent = new Intent(mContext, OrderDetailsActivity.class);
		intent.putExtra("mOutTradeNo", mOutTradeNo);

		mActivity.startActivity(intent);
		AndroidUtils.enterActvityAnim(mActivity);
	}

}
