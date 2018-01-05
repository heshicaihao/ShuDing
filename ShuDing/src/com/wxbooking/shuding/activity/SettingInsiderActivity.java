package com.wxbooking.shuding.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.adapter.ManagerTypeAdapter;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.utils.StringUtils;

/**
 * 设置会员
 * 
 * @author heshicaihao
 * 
 */
public class SettingInsiderActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	private String mManagerTypeStr;

	private ImageView mBack;
	private TextView mTitle;
	private TextView mChoiceTitle;
	private Button mCancel;
	private Button mConfirm;
	private ListView mManagerTypeListView;
	private RelativeLayout mManagerTypeView;
	private TextView mManagerTypeEdit;

	private JSONArray mManagerTypeJSONArray;
	private ManagerTypeAdapter mAdapter;
	private InputMethodManager mImm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_insider);

		initView();
		initData();

	}

	private void initView() {
		mBack = (ImageView) findViewById(R.id.back);
		mTitle = (TextView) findViewById(R.id.title);
		mChoiceTitle = (TextView) findViewById(R.id.choice_title);
		mManagerTypeEdit = (TextView) findViewById(R.id.insider_type_edit);
		mCancel = (Button) findViewById(R.id.btn_cancel);
		mConfirm = (Button) findViewById(R.id.btn_confirm);
		mManagerTypeListView = (ListView) findViewById(R.id.manager_type_list);
		mManagerTypeView = (RelativeLayout) findViewById(R.id.choice_insider_view);

		mTitle.setText(R.string.setting_insider);
		mChoiceTitle.setText(R.string.select_the_insider_type);
		mBack.setOnClickListener(this);
		mManagerTypeEdit.setOnClickListener(this);
		mCancel.setOnClickListener(this);
		mConfirm.setOnClickListener(this);
		mManagerTypeListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.insider_type_edit:
			mImm.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), 0);
			mManagerTypeView.setVisibility(View.VISIBLE);
			break;

		case R.id.btn_cancel:
			mManagerTypeView.setVisibility(View.GONE);
			break;

		case R.id.btn_confirm:
			mManagerTypeView.setVisibility(View.GONE);
			if (!StringUtils.isEmpty(mManagerTypeStr)) {
				mManagerTypeEdit.setText(mManagerTypeStr);
			}
			break;

		case R.id.back:
			AndroidUtils.finishActivity(this);
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mManagerTypeStr = mManagerTypeJSONArray.optString(position);
		mAdapter.setSeclection(position);
		mAdapter.notifyDataSetInvalidated();
	}

	private void initData() {
		mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				mImm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, 500);
		mManagerTypeJSONArray = getJSONArray();

		mAdapter = new ManagerTypeAdapter(this, mManagerTypeJSONArray);
		mManagerTypeListView.setAdapter(mAdapter);
	}

	/**
	 * 获取管理员分组 信息
	 * 
	 * @return
	 */
	private JSONArray getJSONArray() {
		JSONArray arr = new JSONArray();
		try {
			arr.put(0, "白金卡");
			arr.put(1, "金卡");
			arr.put(2, "银卡");
			arr.put(3, "VIP");
			arr.put(4, "散客");
			arr.put(5, "屌丝");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return arr;

	}
}
