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
 * 积分规则
 * 
 * @author heshicaihao
 * 
 */
public class IntegralRuleActivity extends BaseActivity implements
		OnClickListener {

	private TextView mTitle;
	private ImageView mBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integral_rule);
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
		
		mTitle.setText(R.string.integral_rule);
		mBack.setOnClickListener(this);
	}

	private void initData() {
		getOrderInfo();
	}

	private void getOrderInfo() {

		// NetHelper.getOrderDetail(mUser.getId(), mUser.getToken(),
		// mOutTradeNo,
		// new AsyncHttpResponseHandler() {
		//
		// @Override
		// public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
		// LogUtils.logd(TAG, "onSuccess");
		// resolveOrderDetail(arg2);
		// dismissmeidialog();
		// }
		//
		// @Override
		// public void onFailure(int arg0, Header[] arg1, byte[] arg2,
		// Throwable arg3) {
		// LogUtils.logd(TAG, "onFailure");
		// dismissmeidialog();
		// }
		// });

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

}
