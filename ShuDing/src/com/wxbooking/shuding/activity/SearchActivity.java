package com.wxbooking.shuding.activity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.adapter.ClientSearchAdapter;
import com.wxbooking.shuding.adapter.IntegralSearchAdapter;
import com.wxbooking.shuding.adapter.OrderSearchAdapter;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.bean.ClientBean;
import com.wxbooking.shuding.bean.IntegralBean;
import com.wxbooking.shuding.bean.OrderBean;
import com.wxbooking.shuding.constants.MyConstants;
import com.wxbooking.shuding.utils.AndroidUtils;

/**
 * 搜索 展示列表
 * 
 * @author heshicaihao
 * 
 */
public class SearchActivity extends BaseActivity implements OnClickListener,
		OnEditorActionListener {
	private int mSearchCode = 0;
	
	private TextView mCancelTV;
	private ImageView mDeleteIV;
	private EditText mSearchET;
	private ListView mListView;
	
	private InputMethodManager mImm;
	private OrderSearchAdapter mOrderSearchAdapter;
	private ClientSearchAdapter mClientSearchAdapter;
	private IntegralSearchAdapter mIntegralSearchAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initView();
		initData();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.delete_iv:
			mSearchET.setText("");
			mDeleteIV.setVisibility(View.GONE);
			break;

		case R.id.cancel:
			mImm.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), 0);
			AndroidUtils.finishActivity(this);
			break;
		case R.id.search_edittext:
			List<OrderBean> list = null;
			mOrderSearchAdapter.setData(list);
			mOrderSearchAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_SEARCH) {
			// 先隐藏键盘
			((InputMethodManager) mSearchET.getContext().getSystemService(
					Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
					SearchActivity.this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			showData(mSearchCode);
			return true;
		}
		return false;
	}

	/**
	 * 展示数据
	 */
	private void showData(int search_code) {
		switch (search_code) {
		case MyConstants.ORDER_SEARCH_CODE:
			setOrderSearchAdapter();
			break;
			
		case MyConstants.CLIENT_SEARCH_CODE:
			setClientSearchAdapter();
			break;
			
		case MyConstants.INTEGRAL_SEARCH_CODE:
			setIntegralSearchAdapter();
			break;

		default:
			break;
		}

	}

	/**
	 * 设置订单搜索 适配器
	 */
	private void setOrderSearchAdapter() {
		mOrderSearchAdapter = new OrderSearchAdapter(SearchActivity.this,
				getApplication());
		mListView.setAdapter(mOrderSearchAdapter);
		JSONArray arr = getOrderSearchJSONArray();
		List<OrderBean> list = OrderBean.getOrderList(arr);
		mOrderSearchAdapter.setData(list);
		mOrderSearchAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置客户搜索 适配器
	 */
	private void setClientSearchAdapter() {
		mClientSearchAdapter = new ClientSearchAdapter(SearchActivity.this,
				getApplication());
		mListView.setAdapter(mClientSearchAdapter);
		JSONArray arr = getClientSearchJSONArray();
		List<ClientBean> list = ClientBean.getClientList(arr);
		mClientSearchAdapter.setData(list);
		mClientSearchAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置积分搜索 适配器
	 */
	private void setIntegralSearchAdapter() {
		mIntegralSearchAdapter = new IntegralSearchAdapter(SearchActivity.this,
				getApplication());
		mListView.setAdapter(mIntegralSearchAdapter);
		JSONArray arr = getIntegralSearchJSONArray();
		List<IntegralBean> list = IntegralBean.getIntegralList(arr);
		mIntegralSearchAdapter.setData(list);
		mIntegralSearchAdapter.notifyDataSetChanged();
	}
	
	/**
	 * 处理Intent数据
	 */
	private void getDataIntent() {
		Intent intent = getIntent();
		String search_codeStr = intent.getStringExtra(MyConstants.SEARCH_TYPE);
		mSearchCode = Integer.parseInt(search_codeStr);
		switch (mSearchCode) {
		case MyConstants.ORDER_SEARCH_CODE:
			mSearchET.setHint(this.getResources().getString(
					R.string.input_order_search_init));
			break;
		case MyConstants.CLIENT_SEARCH_CODE:
			mSearchET.setHint(this.getResources().getString(
					R.string.input_client_search_init));
			break;
		case MyConstants.INTEGRAL_SEARCH_CODE:
			mSearchET.setHint(this.getResources().getString(
					R.string.input_integral_search_init));
			break;
		default:
			break;
		}

	}

	private void initView() {
		mCancelTV = (TextView) findViewById(R.id.cancel);
		mDeleteIV = (ImageView) findViewById(R.id.delete_iv);
		mSearchET = (EditText) findViewById(R.id.search_edittext);
		mListView = (ListView) findViewById(R.id.listview);

		setTextChangedListener();
		mSearchET.setOnEditorActionListener(this);
		// mSearchET.setOnClickListener(this);
		mCancelTV.setOnClickListener(this);
		mDeleteIV.setOnClickListener(this);

	}

	private void initData() {
		getDataIntent();
		setOpenKeyboard();
	}

	/**
	 * 初始化软盘 并打开软盘
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
	 * 添加编辑框监听
	 */
	private void setTextChangedListener() {
		mSearchET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (arg0.toString().length() > 0) {
					mDeleteIV.setVisibility(View.VISIBLE);
				} else {
					mDeleteIV.setVisibility(View.GONE);
				}
			}
		});
	}

	/**
	 * 获取订单列表 信息
	 * 
	 * @return
	 */
	private JSONArray getOrderSearchJSONArray() {
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

	/**
	 * 获取客户列表 信息
	 * 
	 * @return
	 */
	private JSONArray getClientSearchJSONArray() {
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

	/**
	 * 获取客户列表 信息
	 * 
	 * @return
	 */
	private JSONArray getIntegralSearchJSONArray() {
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("integral_id", "00001");
			obj.put("reason", "每日签到");
			obj.put("integral_num", "+10");
			obj.put("nick_name", "卖刀的人");
			obj.put("real_name", "张二蛋子");
			obj.put("phone", "13544442222");
			obj.put("time", "20160606");
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

}
