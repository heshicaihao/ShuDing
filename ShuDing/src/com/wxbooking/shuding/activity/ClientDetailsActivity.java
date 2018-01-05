package com.wxbooking.shuding.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.utils.AndroidUtils;

/**
 * 客户详情
 * 
 * @author heshicaihao
 * 
 */
public class ClientDetailsActivity extends BaseActivity implements
		OnClickListener {

	private TextView mTitle;
	private ImageView mBack;
	private Button mSettingInsider;
	private TextView mClientName;
	private TextView mSex;
	private TextView mClientType;
	private TextView mPhone;
	private TextView mConcernTime;
	private TextView mCheckNum;
	private TextView mIntegralNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_details);
		initView();
		initData();

	}

	private void initData() {
		JSONObject obj = getJSONArray();
		showClientInfo(obj);
		
	}

	private void showClientInfo(JSONObject obj) {
		String client_name = obj.optString("client_name");
		mClientName.setText(client_name);
		
		String sex = obj.optString("sex");
		mSex.setText(sex);
		
		String client_type = obj.optString("client_type");
		mClientType.setText(client_type);
		
		String phone = obj.optString("phone");
		mPhone.setText(phone);
		
		String concern_time = obj.optString("concern_time");
		mConcernTime.setText(concern_time);
		
		String check_num = obj.optString("check_num");
		mCheckNum.setText(check_num);
		
		String integral_num = obj.optString("integral_num");
		mIntegralNum.setText(integral_num);
		
	}

	private void initView() {
		mTitle = (TextView) findViewById(R.id.title);
		mBack = (ImageView) findViewById(R.id.back);
		mSettingInsider = (Button) findViewById(R.id.setting_insider);
		mClientName = (TextView) findViewById(R.id.client_name);
		mSex = (TextView) findViewById(R.id.sex);
		mClientType = (TextView) findViewById(R.id.client_type);
		mPhone = (TextView) findViewById(R.id.phone);
		mConcernTime = (TextView) findViewById(R.id.concern_time);
		mCheckNum = (TextView) findViewById(R.id.check_num);
		mIntegralNum = (TextView) findViewById(R.id.integral_num);

		mTitle.setText(R.string.client_details);
		mBack.setOnClickListener(this);
		mSettingInsider.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.setting_insider:
			gotoSettingInsiderActivity();
			break;

		case R.id.back:
			AndroidUtils.finishActivity(this);
			break;

		default:
			break;
		}

	}

	/**
	 * 跳转到ClientDetailsActivity
	 * 
	 * @param position
	 */
	private void gotoSettingInsiderActivity() {
		// ClientBean object = mData.get(position);
		// String mClient_id = object.getClient_id();
		String mClient_id = "9527";
		Intent intent = new Intent(this, SettingInsiderActivity.class);
		intent.putExtra("mClient_id", mClient_id);

		this.startActivity(intent);
		AndroidUtils.enterActvityAnim(this);
	}
	
	/**
	 * 获取订单列表 信息
	 * 
	 * @return
	 */
	private JSONObject getJSONArray() {
		// JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("client_id", "9527");
			obj.put("client_name", "张二蛋子");
			obj.put("sex", "男");
			obj.put("client_type", "银卡");
			obj.put("phone", "13512121212");
			obj.put("concern_time", "20160303");
			obj.put("check_num", "1次");
			obj.put("integral_num", "500");
			obj.put("image_url",
					"http://www.ddiiyy.com/public/images/4c/8d/e0/daad1be11d52d3ffee70f01b4b11fa157f35f6ba.png?1464080992#h");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj;
	}

}
