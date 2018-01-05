package com.wxbooking.shuding.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wxbooking.shuding.R;
import com.wxbooking.shuding.adapter.ClientSearchHolder;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.bean.ClientBean;
import com.wxbooking.shuding.constants.MyConstants;
import com.wxbooking.shuding.listener.OnRefreshListener;
import com.wxbooking.shuding.net.NetHelper;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.utils.LogUtils;
import com.wxbooking.shuding.widget.CircleImageView;
import com.wxbooking.shuding.widget.RefreshListView;
import com.wxbooking.shuding.xutils.BitmapHelp;
import com.wxbooking.shuding.xutils.BitmapUtils;

/**
 * 客户列表
 * 
 * @author heshicaihao
 * 
 */
public class ClientListActivity extends BaseActivity implements
		OnClickListener, OnRefreshListener {

	private ImageView mBack;
	private RelativeLayout mSearchRL;
	private  TextView mSearchTV;
	private RefreshListView mListView;
	private ClientAdapter mAdapter;
	private List<ClientBean> clientList = new ArrayList<ClientBean>();
	private int mPage = 1;
	private String pager_total = "1";
	private int mPageCout = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_list);
		initView();
		initData();

	}

	private void initData() {

		if (AndroidUtils.isNetworkAvailable(ClientListActivity.this)) {
			showmeidialog();
			initData(true);
		} else {
			Toast.makeText(getApplication(), this.getString(R.string.no_net),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void initData(boolean flag) {

		// if (mUser.isIs_login()) {
		getOrderInfo(flag);
		// }
	}

	private void getOrderInfo(final boolean flag) {
		NetHelper.getOrderList("", "", "all", mPage, mPageCout,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						LogUtils.logd(TAG, "getOrderList+onSuccess");
						resolveOrderList(flag, arg2);
						dismissmeidialog();
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						LogUtils.logd(TAG, "getOrderList+onFailure");
						dismissmeidialog();
					}
				});
	}

	private void resolveOrderList(boolean flag, byte[] arg2) {
		JSONArray arr = getJSONArray();
		List<ClientBean> list = ClientBean.getClientList(arr);
		if (list != null && list.size() > 0) {
			if (flag) {
				clientList.clear();
			}
			mPage++;

			clientList.addAll(list);
			mAdapter.setData(clientList);
			mAdapter.notifyDataSetChanged();
			mListView.hideHeaderView();
			mListView.hideFooterView();
		} else {
			mPage = 0;
		}
	}

	@Override
	public void onDownPullRefresh() {
		// TODO Auto-generated method stub
		mPage = 1;
		initData(true);
	}

	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		if (mPage > Integer.parseInt(pager_total)) {
			mListView.hideFooterView();
			if (AndroidUtils.isNoFastClick()) {
				Toast.makeText(getApplication(),
						this.getString(R.string.no_more), Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			initData(false);
		}
	}

	private void initView() {
		mBack = (ImageView) findViewById(R.id.back);
		mSearchRL = (RelativeLayout) findViewById(R.id.search_ll);
		mSearchTV = (TextView) findViewById(R.id.search_et);
		mListView = (RefreshListView) findViewById(R.id.listview);

		mSearchTV.setHint(this.getResources().getString(R.string.input_client_search_init));
		mBack.setOnClickListener(this);
		mSearchRL.setOnClickListener(this);

		mAdapter = new ClientAdapter(this, getApplication());
		mListView.setAdapter(mAdapter);

		mListView.setOnRefreshListener(this);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.back:
			AndroidUtils.finishActivity(this);
			break;
		case R.id.search_ll:
			gotoSearch(MyConstants.CLIENT_SEARCH_CODE+"");
			break;

		default:
			break;
		}
	}

	/**
	 * 获取客户列表 信息
	 * 
	 * @return
	 */
	private JSONArray getJSONArray() {
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("client_id", "9527");
			obj.put("image_url",
					"http://www.ddiiyy.com/upload/8/e7/e7f7/20151123/165215_7880.png");
			obj.put("client_fans", "微信粉丝");
			obj.put("nick_name", "卖刀的人");
			obj.put("sex", "男");
			obj.put("client_name", "张二蛋子");
			obj.put("check_num", "1次");
			obj.put("integral_num", "500");
			arr.put(0, obj);
			arr.put(1, obj);
			arr.put(2, obj);
			arr.put(3, obj);
			arr.put(4, obj);
			arr.put(5, obj);
			arr.put(6, obj);
			arr.put(7, obj);
			arr.put(8, obj);
			arr.put(9, obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return arr;

	}

	public class ClientAdapter extends BaseAdapter {
		public String TAG = getClass().getName();

		private Context mContext;
		private Activity mActivity;
		private List<ClientBean> mData;
		private LayoutInflater mInflater;
		private ClientSearchHolder mHolder;
		private BitmapUtils bitmapUtils;

		public ClientAdapter(Activity mActivity, Context context) {
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

}
