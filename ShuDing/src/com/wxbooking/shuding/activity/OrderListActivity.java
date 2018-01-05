package com.wxbooking.shuding.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wxbooking.shuding.R;
import com.wxbooking.shuding.adapter.OrderHolder;
import com.wxbooking.shuding.adapter.SectionAdapter;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.bean.OrderBean;
import com.wxbooking.shuding.bean.TopLabelObject;
import com.wxbooking.shuding.constants.MyConstants;
import com.wxbooking.shuding.listener.OnRefreshListener;
import com.wxbooking.shuding.net.NetHelper;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.utils.LogUtils;
import com.wxbooking.shuding.widget.RefreshListView;

/**
 * 订单列表
 * 
 * @author heshicaihao
 * 
 */
@SuppressLint("InlinedApi")
public class OrderListActivity extends BaseActivity implements OnClickListener,
		OnRefreshListener {

	private ImageView mBack;
	private TextView mSearchTV;
	private RelativeLayout mSearchRL;

	private LinearLayout vTextLeft_container;
	private LinearLayout vTextMiddle_container;
	private LinearLayout vTextRight_container;
	private LinearLayout vTextRight_container02;

	private ImageView leftMark;
	private ImageView middleMark;
	private ImageView rightMark;
	private ImageView rightMark02;
	private TextView leftText;
	private TextView middleText;
	private TextView rightText;
	private TextView rightText02;

	private final int selectPos[] = new int[] { 0, 0, 0, 0 };
	private int secindex = 0;
	private PopupWindow mPopupWindow;
	private SectionAdapter mPopAdapter;
	private ListView view_list;
	private RefreshListView mListView;

	private static List<TopLabelObject> mSports = new ArrayList<TopLabelObject>();
	private final List<TopLabelObject> mSexs = new ArrayList<TopLabelObject>();
	private final List<TopLabelObject> mLevels = new ArrayList<TopLabelObject>();
	private final List<TopLabelObject> mLevels02 = new ArrayList<TopLabelObject>();
	private List<OrderBean> orderList = new ArrayList<OrderBean>();
	private TabOrderAdapter mAdapter;
	private int mPage = 1;
	private String pager_total = "1";
	private int mPageCout = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_list);

		initView();
		initData();
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.back:
			AndroidUtils.finishActivity(this);
			break;
		case R.id.search_ll:
			gotoSearch(MyConstants.ORDER_SEARCH_CODE + "");
			break;
		case R.id.sectionleft_container:
			secindex = 0;
			selectSecCheck(secindex);
			showSectionPop(0, secindex);
			break;
		case R.id.sectionmiddle_container:
			secindex = 1;
			selectSecCheck(secindex);
			showSectionPop(0, secindex);
			break;
		case R.id.sectionright_container:
			secindex = 2;
			selectSecCheck(secindex);
			showSectionPop(0, secindex);
			break;
		case R.id.sectionright_container02:
			secindex = 3;
			selectSecCheck(secindex);
			showSectionPop(0, secindex);
			break;

		default:
			break;
		}
	}

	@Override
	public void onDownPullRefresh() {
		mPage = 1;
		initData(true);
	}

	@Override
	public void onLoadingMore() {
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
		List<OrderBean> list = OrderBean.getOrderList(arr);
		if (list != null && list.size() > 0) {
			if (flag) {
				orderList.clear();
			}
			mPage++;

			orderList.addAll(list);
			mAdapter.setData(orderList);
			mAdapter.notifyDataSetChanged();
			mListView.hideHeaderView();
			mListView.hideFooterView();
		} else {
			mPage = 0;
		}
	}

	private void initData() {
		if (AndroidUtils.isNetworkAvailable(OrderListActivity.this)) {
			showmeidialog();
			initData(true);
		} else {
			Toast.makeText(getApplication(), this.getString(R.string.no_net),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void initView() {
		mBack = (ImageView) findViewById(R.id.back);
		mSearchRL = (RelativeLayout) findViewById(R.id.search_ll);
		mSearchTV = (TextView) findViewById(R.id.search_et);
		mListView = (RefreshListView) findViewById(R.id.listview);

		mSearchTV.setHint(this.getResources().getString(
				R.string.input_order_search_init));
		mBack.setOnClickListener(this);
		mSearchRL.setOnClickListener(this);

		// TODO Auto-generated method stub
		leftMark = (ImageView) findViewById(R.id.mark1);
		middleMark = (ImageView) findViewById(R.id.mark2);
		rightMark = (ImageView) findViewById(R.id.mark3);
		rightMark02 = (ImageView) findViewById(R.id.mark4);

		leftText = (TextView) findViewById(R.id.sectionleft);
		middleText = (TextView) findViewById(R.id.sectionmiddle);
		rightText = (TextView) findViewById(R.id.sectionright);
		rightText02 = (TextView) findViewById(R.id.sectionright02);
		leftText.setText("酒店");
		middleText.setText("订单状态");
		rightText.setText("支付状态");
		rightText02.setText("支付方式");
		vTextLeft_container = (LinearLayout) findViewById(R.id.sectionleft_container);
		vTextMiddle_container = (LinearLayout) findViewById(R.id.sectionmiddle_container);
		vTextRight_container = (LinearLayout) findViewById(R.id.sectionright_container);
		vTextRight_container02 = (LinearLayout) findViewById(R.id.sectionright_container02);

		vTextLeft_container.setOnClickListener(this);
		vTextMiddle_container.setOnClickListener(this);
		vTextRight_container.setOnClickListener(this);
		vTextRight_container02.setOnClickListener(this);

		mAdapter = new TabOrderAdapter(this, getApplication());
		mListView.setAdapter(mAdapter);

		mListView.setOnRefreshListener(this);
	}

	protected void selectSecCheck(int index) {
		leftText.setTextColor(0xff696969);
		middleText.setTextColor(0xff696969);
		rightText.setTextColor(0xff696969);
		rightText02.setTextColor(0xff696969);

		leftMark.setImageResource(R.drawable.pull_down_press);
		middleMark.setImageResource(R.drawable.pull_down_press);
		rightMark.setImageResource(R.drawable.pull_down_press);
		rightMark02.setImageResource(R.drawable.pull_down_press);
		switch (index) {
		case 0:
			leftText.setTextColor(this.getResources().getColor(
					R.color.main_color));
			leftMark.setImageResource(R.drawable.pull_up_press);
			break;
		case 1:
			middleText.setTextColor(this.getResources().getColor(
					R.color.main_color));
			middleMark.setImageResource(R.drawable.pull_up_press);
			break;
		case 2:
			rightText.setTextColor(this.getResources().getColor(
					R.color.main_color));
			rightMark.setImageResource(R.drawable.pull_up_press);
			break;
		case 3:
			rightText02.setTextColor(this.getResources().getColor(
					R.color.main_color));
			rightMark02.setImageResource(R.drawable.pull_up_press);
			break;
		}
	}

	@SuppressLint("InlinedApi")
	private void showSectionPop(int width, final int secindex) {
		View layout = LayoutInflater.from(this).inflate(
				R.layout.view_popup_category, null);
		view_list = (ListView) layout.findViewById(R.id.view_list);
		RelativeLayout blankarea = (RelativeLayout) layout
				.findViewById(R.id.blankarea);
		blankarea.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mPopupWindow.dismiss();
			}
		});
		view_list.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("InlinedApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectPos[secindex] = position;
				mPopAdapter.notifyDataSetChanged();
				switch (secindex) {
				case 0:
					mPopAdapter = new SectionAdapter(OrderListActivity.this,
							mSports, position);
					view_list.setAdapter(mPopAdapter);
					if (position == 0) {
						leftText.setText("全部");
					} else {
						leftText.setText(mSports.get(position).getName());
					}
					// sport_id = mSports.get(position).getId();
					// // 加载网络数据
					// refreshData(true);
					break;
				case 1:
					mPopAdapter = new SectionAdapter(OrderListActivity.this,
							mSexs, position);
					view_list.setAdapter(mPopAdapter);
					if (position == 0) {
						middleText.setText("不限");
					} else {
						middleText.setText(mSexs.get(position).getName());
					}
					// sex = mSexs.get(position).getId();
					// // 加载网络数据
					// refreshData(true);
					break;
				case 2:
					mPopAdapter = new SectionAdapter(OrderListActivity.this,
							mLevels, position);
					view_list.setAdapter(mPopAdapter);
					if (position == 0) {
						rightText.setText("不限");
					} else {
						rightText.setText(mLevels.get(position).getName());
					}
					// 加载网络数据
					// level = mLevels.get(position).getId();
					// refreshData(true);
					break;
				case 3:
					mPopAdapter = new SectionAdapter(OrderListActivity.this,
							mLevels02, position);
					view_list.setAdapter(mPopAdapter);
					if (position == 0) {
						rightText02.setText("不限");
					} else {
						rightText02.setText(mLevels02.get(position).getName());
					}
					// 加载网络数据
					// level = mLevels.get(position).getId();
					// refreshData(true);
					break;
				}
				mPopupWindow.dismiss();
			}
		});
		mPopupWindow = new PopupWindow(layout, width,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setWindowLayoutMode(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		ColorDrawable dw = new ColorDrawable(R.color.white);
		mPopupWindow.setBackgroundDrawable(dw);
		mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
		mPopupWindow.setOutsideTouchable(true);

		if (secindex == 0) {
			mPopAdapter = new SectionAdapter(this, mSports, selectPos[secindex]);
		} else if (secindex == 1) {
			mPopAdapter = new SectionAdapter(this, mSexs, selectPos[secindex]);
		} else if (secindex == 2) {
			mPopAdapter = new SectionAdapter(this, mLevels, selectPos[secindex]);
		} else if (secindex == 3) {
			mPopAdapter = new SectionAdapter(this, mLevels02,
					selectPos[secindex]);
		}
		view_list.setAdapter(mPopAdapter);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				selectSecCheck(4);
			}
		});
		mPopupWindow.showAsDropDown(leftText, 0, 0);
		mPopupWindow.update();
	}

	/**
	 * 获取订单列表 信息
	 * 
	 * @return
	 */
	private JSONArray getJSONArray() {
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("order_id", "160523001");
			obj.put("order_status", "已入住");
			obj.put("time", "2016/5/23 12:35");
			obj.put("name", "张明");
			obj.put("hotel_name", "深圳市舒订大酒店");
			obj.put("hotel_price", "8888.88");
			obj.put("hotel_type", "至尊套房");
			obj.put("pay_status", "已支付");
			obj.put("into_time", "05-31");
			obj.put("out_time", "06-01");
			obj.put("order_explain", "1晚/1间");
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

	public class TabOrderAdapter extends BaseAdapter {
		public String TAG = getClass().getName();

		private Context mContext;
		private Activity mActivity;
		private List<OrderBean> mData;
		private LayoutInflater mInflater;
		private OrderHolder mHolder;

		public TabOrderAdapter(Activity mActivity, Context context) {
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
			mHolder.name = (TextView) convertView
					.findViewById(R.id.client_name);
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
			mHolder.hotel_price.setText("¥ " + hotel_price);

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
			String mOrder_id = object.getOrder_id();
			Intent intent = new Intent(mContext, OrderDetailsActivity.class);
			intent.putExtra("mOrder_id", mOrder_id);

			mActivity.startActivity(intent);
			AndroidUtils.enterActvityAnim(mActivity);
		}

	}

}
