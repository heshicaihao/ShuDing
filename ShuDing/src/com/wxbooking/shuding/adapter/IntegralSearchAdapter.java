package com.wxbooking.shuding.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.bean.IntegralBean;

public class IntegralSearchAdapter extends BaseAdapter {
	public String TAG = getClass().getName();

	@SuppressWarnings("unused")
	private Context mContext;
	@SuppressWarnings("unused")
	private Activity mActivity;
	private List<IntegralBean> mData;
	private LayoutInflater mInflater;
	private IntegralSearchHolder mHolder;

	public IntegralSearchAdapter(Activity mActivity, Context context) {
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
	public void setData(List<IntegralBean> mData) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.item_integral_list, null);
			mHolder = new IntegralSearchHolder();
			getItemView(convertView);
			convertView.setTag(mHolder);
		} else {
			mHolder = (IntegralSearchHolder) convertView.getTag();
		}
		setData2UI(position);

		return convertView;
	}

	/**
	 * 找到Item 的 控件
	 * 
	 * @param convertView
	 */
	private void getItemView(View convertView) {
		mHolder.reason = (TextView) convertView.findViewById(R.id.reason);
		mHolder.integral_num = (TextView) convertView
				.findViewById(R.id.integral_num);
		mHolder.name = (TextView) convertView.findViewById(R.id.name);
		mHolder.phone = (TextView) convertView.findViewById(R.id.phone);
		mHolder.time = (TextView) convertView.findViewById(R.id.time);

	}

	/**
	 * 给UI加载数据
	 * 
	 * @param position
	 * @param deleteListener
	 * @param editListener
	 */
	private void setData2UI(int position) {
		IntegralBean object = mData.get(position);

		String reason = object.getReason();
		mHolder.reason.setText(reason);

		String integral_num = object.getIntegral_num();
		mHolder.integral_num.setText(integral_num);

		String nick_name = object.getNick_name();
		String real_name = object.getReal_name();
		mHolder.name.setText(real_name+"("+nick_name+")");

		String phone = object.getPhone();
		mHolder.phone.setText(phone);

		String time = object.getTime();
		mHolder.time.setText(time);

	}

}
