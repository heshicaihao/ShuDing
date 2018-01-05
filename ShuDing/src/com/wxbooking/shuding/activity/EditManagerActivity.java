package com.wxbooking.shuding.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.adapter.PopupHolder;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.widget.SwitchView;
import com.wxbooking.shuding.widget.SwitchView.OnStateChangedListener;

/**
 * 添加管理员,编辑管理员
 * 
 * @author heshicaihao
 * 
 */
public class EditManagerActivity extends BaseActivity implements
		OnClickListener, OnStateChangedListener {

	@SuppressWarnings("unused")
	private boolean mIsYes = true;

	private View mMenuView;
	private ImageView mBack;
	private TextView mTitle;

	private InputMethodManager mImm;

	private EditText mUserNameET;
	private EditText mUserPasswordET;
	private TextView mManagerTypeET;
	private EditText mRealNameET;
	private EditText mEMailET;
	private EditText mPhoneET;
	private SwitchView mSwitchView;
	private EditText mRemarksET;
	private MyPopupWindow mPopupWindow;
	private ListView mPopuListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_manager);

		initView();
		initData();
	}

	@Override
	public void toggleToOn() {
		mSwitchView.toggleSwitch(true);
		mIsYes = true;

	}

	@Override
	public void toggleToOff() {
		mSwitchView.toggleSwitch(false);
		mIsYes = false;

	}

	private void initData() {
		getDataIntent();
		setOpenKeyboard();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.manager_type_edit:
			mImm.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), 0);
			mPopupWindow = new MyPopupWindow(this, mListener);
			mPopupWindow.showAtLocation(
					EditManagerActivity.this.findViewById(R.id.t_view),
					Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			break;

		case R.id.back:
			AndroidUtils.finishActivity(this);
			break;

		default:
			break;
		}
	}

	private void initView() {

		mBack = (ImageView) findViewById(R.id.back);
		mTitle = (TextView) findViewById(R.id.title);

		mUserNameET = (EditText) findViewById(R.id.user_name_edit);
		mUserPasswordET = (EditText) findViewById(R.id.user_password_edit);
		mManagerTypeET = (TextView) findViewById(R.id.manager_type_edit);
		mRealNameET = (EditText) findViewById(R.id.real_name_edit);
		mEMailET = (EditText) findViewById(R.id.e_mail_edit);
		mPhoneET = (EditText) findViewById(R.id.phone_edit);
		mRemarksET = (EditText) findViewById(R.id.remarks_edit);
		mSwitchView = (SwitchView) findViewById(R.id.switchview);
		mSwitchView.setState(true);
		mSwitchView.setBtnColor(0xff2299ed);
		mSwitchView.setOnStateChangedListener(this);

		mTitle.setText(R.string.add_manager);
		mBack.setOnClickListener(this);
		mManagerTypeET.setOnClickListener(this);

	}

	// 为弹出窗口实现监听类
	private OnItemClickListener mListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			try {
				JSONArray mlist = getJSONArray();
				JSONObject obj = (JSONObject) mlist.get(position);
				String manager_name = obj.optString("manager_name");
				mManagerTypeET.setText(manager_name);
				mPopupWindow.dismiss();

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	};

	private void showMangerInfo(JSONObject manager) {

		try {
			String manager_name = manager.getString("manager_name");
			mUserNameET.setText(manager_name);

			String manager_password = manager.getString("manager_password");
			mUserPasswordET.setText(manager_password);

			String manager_type = manager.getString("manager_type");
			mManagerTypeET.setText(manager_type);

			String manager_real_name = manager.getString("manager_real_name");
			mRealNameET.setText(manager_real_name);

			String manager_e_mail = manager.getString("manager_e_mail");
			mEMailET.setText(manager_e_mail);

			String manager_phone = manager.getString("manager_phone");
			mPhoneET.setText(manager_phone);

			String enable = manager.getString("manager_account_enable");
			if ("true".equals(enable)) {
				mIsYes = true;
			} else if ("false".equals(enable)) {
				mIsYes = false;
			} else {
				mIsYes = false;
			}

			String remarks = manager.getString("remarks");
			mRemarksET.setText(remarks);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取订单列表 信息
	 * 
	 * @return
	 */
	private JSONObject getJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("manager_name", "张三丰");
			obj.put("manager_password", "123456");
			obj.put("manager_type", "超级管理员");
			obj.put("manager_real_name", "张二蛋子");
			obj.put("manager_e_mail", "123456@163.com");
			obj.put("manager_phone", "13588888888");
			obj.put("manager_account_enable", "false");
			obj.put("remarks", "此人帅的不一般");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;

	}

	/**
	 * 获取管理员分组 信息
	 * 
	 * @return
	 */
	private JSONArray getJSONArray() {
		JSONArray arr = new JSONArray();
		try {
			JSONObject obj00 = new JSONObject();
			obj00.put("id", "10000");
			obj00.put("manager_name", "超级管理员");
			arr.put(0, obj00);
			JSONObject obj01 = new JSONObject();
			obj01.put("id", "10000");
			obj01.put("manager_name", "高级管理员");
			arr.put(1, obj01);
			JSONObject obj02 = new JSONObject();
			obj02.put("id", "10000");
			obj02.put("manager_name", "普通管理员");
			arr.put(2, obj02);
			JSONObject obj03 = new JSONObject();
			obj03.put("id", "10000");
			obj03.put("manager_name", "客服");
			arr.put(3, obj03);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return arr;

	}

	/**
	 * 设置打开键盘
	 */
	private void setOpenKeyboard() {
		mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				mImm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 500);
	}

	/**
	 * 处理Intent数据
	 */
	private void getDataIntent() {
		Intent intent = getIntent();
		String data = intent.getStringExtra("data");
		try {
			JSONObject jsonobject = new JSONObject(data);
			String code = jsonobject.getString("code");
			JSONObject content = jsonobject.getJSONObject("content");
			if ("10001".equals(code)) {
				// initGoodsInfo();
			} else if ("10002".equals(code)) {
				String manager_id = content.getString("manager_id");
				initMangerInfo(manager_id);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initMangerInfo(String manager_id) {

		JSONObject manager_info = getJSONObject();
		showMangerInfo(manager_info);
	}

	/**
	 * 弹出层 选材质 选型号
	 * 
	 * @author heshicaihao
	 * 
	 */
	class MyPopupWindow extends PopupWindow {

		public Context context;

		public MyPopupWindow(Context context, OnItemClickListener mListener) {
			super(context);
			this.context = context;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mMenuView = inflater.inflate(
					R.layout.view_popup_window_edit_manager, null);
			initView(mMenuView, mListener);
		}

		/**
		 * 找ID
		 * 
		 * @param view
		 * @param itemsOnClick
		 */
		private void initView(View view, OnItemClickListener mListener) {
			mPopuListView = (ListView) view.findViewById(R.id.manager_listview);
			setPopupWindow();
			setMenuTouch();
			JSONArray mlist = getJSONArray();
			PopupListAdapter PopupAdapter = new PopupListAdapter(
					getBaseContext(), mlist);
			mPopuListView.setAdapter(PopupAdapter);
			mPopuListView.setOnItemClickListener(mListener);
		}

		/**
		 * 设置Touch PopupWindow
		 */
		private void setMenuTouch() {
			// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
			mMenuView.setOnTouchListener(new OnTouchListener() {

				public boolean onTouch(View v, MotionEvent event) {

					int height = mMenuView.findViewById(R.id.pop_layout)
							.getTop();
					int y = (int) event.getY();
					if (event.getAction() == MotionEvent.ACTION_UP) {
						if (y < height) {
							// mDataIntent = null;
							dismiss();
						}
					}
					return true;
				}
			});
		}

		/**
		 * 设置PopupWindow
		 */
		@SuppressWarnings("deprecation")
		private void setPopupWindow() {
			// 设置SelectPicPopupWindow的View
			this.setContentView(mMenuView);
			// 设置SelectPicPopupWindow弹出窗体的宽
			this.setWidth(LayoutParams.FILL_PARENT);
			// 设置SelectPicPopupWindow弹出窗体的高
			this.setHeight(LayoutParams.WRAP_CONTENT);
			// 设置SelectPicPopupWindow弹出窗体可点击
			this.setFocusable(true);
			// 设置SelectPicPopupWindow弹出窗体动画效果
			this.setAnimationStyle(R.style.AnimBottom);
			// 实例化一个ColorDrawable颜色为半透明
			ColorDrawable dw = new ColorDrawable(0xb0000000);
			// 设置SelectPicPopupWindow弹出窗体的背景
			this.setBackgroundDrawable(dw);
		}

	}

	class PopupListAdapter extends BaseAdapter {
		public String TAG = getClass().getName();

		@SuppressWarnings("unused")
		private Context mContext;
		private JSONArray mList;
		private LayoutInflater mInflater;
		private PopupHolder mHolder;

		public PopupListAdapter(Context mContext, JSONArray list) {
			this.mContext = mContext;
			this.mList = list;
			mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			int count = mList == null ? 0 : mList.length();
			return count;
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

				convertView = mInflater.inflate(R.layout.item_popup_list, null);
				mHolder = new PopupHolder();
				getItemView(convertView);
				convertView.setTag(mHolder);
			} else {
				mHolder = (PopupHolder) convertView.getTag();
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
			mHolder.manager_name = (TextView) convertView
					.findViewById(R.id.manager_name);

		}

		/**
		 * 给UI加载数据
		 * 
		 * @param position
		 * @param deleteListener
		 * @param editListener
		 */
		private void setData2UI(int position) {
			JSONObject object;
			try {
				object = (JSONObject) mList.get(position);
				String manager_name = object.optString("manager_name");
				mHolder.manager_name.setText(manager_name);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

}
