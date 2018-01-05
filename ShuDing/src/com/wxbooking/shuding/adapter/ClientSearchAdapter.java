package com.wxbooking.shuding.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.activity.ClientDetailsActivity;
import com.wxbooking.shuding.bean.ClientBean;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.widget.CircleImageView;
import com.wxbooking.shuding.xutils.BitmapHelp;
import com.wxbooking.shuding.xutils.BitmapUtils;


public class ClientSearchAdapter extends BaseAdapter {
	public String TAG = getClass().getName();

	private Context mContext;
	private Activity mActivity;
	private List<ClientBean> mData;
	private LayoutInflater mInflater;
	private ClientSearchHolder mHolder;
	private BitmapUtils bitmapUtils;

	public ClientSearchAdapter(Activity mActivity, Context context) {
		this.mContext = context;
		this.mActivity = mActivity;
		mInflater = LayoutInflater.from(context);

		if (bitmapUtils == null) {
			bitmapUtils = BitmapHelp.getBitmapUtils(mContext);
		}
		bitmapUtils.configDefaultLoadingImage(R.drawable.blank_bg);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.blank_bg);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		bitmapUtils.configDiskCacheEnabled(false);
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
	public void setData(List<ClientBean> mData) {
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

			convertView = mInflater
					.inflate(R.layout.item_client_list, null);
			mHolder = new ClientSearchHolder();
			getItemView(convertView);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ClientSearchHolder) convertView.getTag();
		}
		setData2UI(position);

		mHolder.item_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				gotoClientDetailsActivity(position);
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
		mHolder.image_url = (CircleImageView) convertView
				.findViewById(R.id.integral_image);
		mHolder.client_fans = (TextView) convertView
				.findViewById(R.id.client_fans);
		mHolder.nick_name = (TextView) convertView
				.findViewById(R.id.nick_name);
		mHolder.client_name = (TextView) convertView
				.findViewById(R.id.client_name);
		mHolder.check_num = (TextView) convertView
				.findViewById(R.id.check_num);
		mHolder.integral_num = (TextView) convertView
				.findViewById(R.id.integral_num);
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
		ClientBean object = mData.get(position);
		String image_url = object.getImage_url();
		// mHolder.image_url.setImageResource(R.drawable.ic_launcher);
		bitmapUtils.display(mHolder.image_url, image_url);
		// bitmapUtils.display(mHolder.image_url, image_url,
		// new BitmapLoadCallBack<View>() {
		//
		// @Override
		// public void onLoadCompleted(View container, String uri,
		// Bitmap bitmap, BitmapDisplayConfig config,
		// BitmapLoadFrom from) {
		// // TODO Auto-generated method stub
		// if (bitmap == null) {
		//
		// } else {
		// mHolder.image_url.setImageBitmap(bitmap);
		// }
		//
		// }
		//
		// @Override
		// public void onLoadFailed(View container, String uri,
		// Drawable drawable) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		String client_fans = object.getClient_fans();
		mHolder.client_fans.setText(client_fans);

		String nick_name = object.getNick_name();
		String sex = object.getSex();
		mHolder.nick_name.setText(nick_name + "(" + sex + ")");

		String client_name = object.getClient_name();
		mHolder.client_name.setText(client_name);

		String check_num = object.getCheck_num();
		mHolder.check_num.setText(check_num);

		String integral_num = object.getIntegral_num();
		mHolder.integral_num.setText(integral_num);

	}

	/**
	 * 跳转到ClientDetailsActivity
	 * 
	 * @param position
	 */
	private void gotoClientDetailsActivity(int position) {
		ClientBean object = mData.get(position);
		String mClient_id = object.getClient_id();
		Intent intent = new Intent(mContext, ClientDetailsActivity.class);
		intent.putExtra("mClient_id", mClient_id);

		mActivity.startActivity(intent);
		AndroidUtils.enterActvityAnim(mActivity);
	}

}
