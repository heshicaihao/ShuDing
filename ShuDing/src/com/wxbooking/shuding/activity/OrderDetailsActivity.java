package com.wxbooking.shuding.activity;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.utils.JSONUtil;
import com.wxbooking.shuding.utils.LogUtils;

/**
 * 订单详情
 * 
 * @author heshicaihao
 * 
 */
public class OrderDetailsActivity extends BaseActivity implements
		OnClickListener {

	private ImageView mBack;
	private TextView mTitle;
	private TextView mOrderId;
	private TextView mHotelName;
	private TextView mHotelType;
	private TextView mOrderStatus;
	private TextView mPayMode;
	private TextView mPayStatus;
	private TextView mIntoTime;
	private TextView mOutTime;
	private TextView mClientNum;
	private TextView mRoomNum;
	private TextView mTotalSum;
	private TextView mRemarks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_details);
		initView();
		initData();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			AndroidUtils.finishActivity(this);
			break;
		default:
			break;
		}
	}

	private void initView() {
		mTitle = (TextView) findViewById(R.id.title);
		mBack = (ImageView) findViewById(R.id.back);

		mOrderId = (TextView) findViewById(R.id.order_id);
		mHotelName = (TextView) findViewById(R.id.hotel_name);
		mHotelType = (TextView) findViewById(R.id.hotel_type);
		mOrderStatus = (TextView) findViewById(R.id.order_status);
		mPayMode = (TextView) findViewById(R.id.pay_mode);
		mPayStatus = (TextView) findViewById(R.id.pay_status);
		mIntoTime = (TextView) findViewById(R.id.into_time);
		mOutTime = (TextView) findViewById(R.id.out_time);
		mClientNum = (TextView) findViewById(R.id.client_num);
		mRoomNum = (TextView) findViewById(R.id.room_num);
		mTotalSum = (TextView) findViewById(R.id.total_sum);
		mRemarks = (TextView) findViewById(R.id.remarks);

		mTitle.setText(R.string.order_details);
		mBack.setOnClickListener(this);
	}

	private void initData() {
		getOrderInfo();
		JSONObject obj = getJSONArray();
		showOrderInfo(obj);

	}

	private void showOrderInfo(JSONObject obj) {
		try {
			String order_id = obj.getString("order_id");
			mOrderId.setText(order_id);
			
			String hotel_name = obj.getString("hotel_name");
			mHotelName.setText(hotel_name);
			
			String hotel_type = obj.getString("hotel_type");
			mHotelType.setText(hotel_type);
			
			String order_status = obj.getString("order_status");
			mOrderStatus.setText(order_status);
			
			String pay_mode = obj.getString("pay_mode");
			mPayMode.setText(pay_mode);
			
			String pay_status = obj.getString("pay_status");
			mPayStatus.setText(pay_status);
			
			String into_time = obj.getString("into_time");
			mIntoTime.setText(into_time);
			
			String out_time = obj.getString("out_time");
			mOutTime.setText(out_time);
			
			String client_num = obj.getString("client_num");
			mClientNum.setText(client_num);
			
			String room_num = obj.getString("room_num");
			mRoomNum.setText(room_num);
			
			String total_sum = obj.getString("total_sum");
			mTotalSum.setText(total_sum);
			
			String remarks = obj.getString("remarks");
			mRemarks.setText(remarks);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void getOrderInfo() {

//		NetHelper.getOrderDetail(mUser.getId(), mUser.getToken(), mOutTradeNo,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//						LogUtils.logd(TAG, "onSuccess");
//						resolveOrderDetail(arg2);
//						dismissmeidialog();
//					}
//
//					@Override
//					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
//							Throwable arg3) {
//						LogUtils.logd(TAG, "onFailure");
//						dismissmeidialog();
//					}
//				});

	}

	@SuppressWarnings("unused")
	private void resolveOrderDetail(byte[] responseBody) {
		try {
			String json = new String(responseBody, "UTF-8");
			LogUtils.logd(TAG, "json:" + json);
			JSONObject result = JSONUtil.resolveResult(responseBody);
			if (result != null) {
				// showOrderInfo(result);
				// setListAdapter(result);
				// showConsignee(result);
			} else {
				dismissmeidialog();
				JSONObject JSONObject = new JSONObject(json);
				String msg = JSONObject.optString("msg");
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				AndroidUtils.finishActivity(this);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// private void getDataIntent() {
	// Intent intent = getIntent();
	// mOutTradeNo = intent.getStringExtra("mOutTradeNo");
	// }

	/**
	 * 获取订单列表 信息
	 * 
	 * @return
	 */
	private JSONObject getJSONArray() {
		// JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("order_id", "160523001");
			obj.put("hotel_name", "深圳市舒订大酒店");
			obj.put("hotel_type", "至尊套房");
			obj.put("order_status", "已入住");
			obj.put("pay_mode", "支付宝");
			obj.put("pay_status", "已支付");
			obj.put("into_time", "05-31");
			obj.put("out_time", "06-01");
			obj.put("client_num", "2人");
			obj.put("room_num", "1间");
			obj.put("total_sum", "8888.88");
			obj.put("remarks", "我想窗户向南的");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;
	}

}
