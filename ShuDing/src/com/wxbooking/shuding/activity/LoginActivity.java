package com.wxbooking.shuding.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.utils.SharedpreferncesUtil;
import com.wxbooking.shuding.utils.StringUtils;

/**
 * 登录
 * 
 * @author heshicaihao
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	private static Boolean mIsExit = false;

	private String mPhoneStr;
	private String mPasswordStr;
	private boolean mIsThreeLogin;

	private RelativeLayout mFindPassword;
	private ImageView mAccountDelete;
	private ImageView mPassDelete;

	private EditText mAccount;
	private EditText mPassword;
	private Button mLogin;
	private View mView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mView = View.inflate(this, R.layout.activity_login, null);
		setContentView(mView);
		initView();
		initData();
	}

	/**
	 * 监听返回--是否退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// AndroidUtils.finishActivity(this);
			exitBy2Click();
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.find_password_rl:
			gotoOther(this, ResetPasswordActivity.class);
			break;
		case R.id.account_delete:
			mAccount.setText("");
			mAccountDelete.setVisibility(View.GONE);
			break;
		case R.id.password_delete:
			mPassword.setText("");
			mPassDelete.setVisibility(View.GONE);
			break;
		case R.id.login:
			if (AndroidUtils.isNoFastClick()) {
				getInfoInput();
				showmeidialog();
				login();
				SharedpreferncesUtil.setGuided(getApplicationContext());
			}
			break;
		case R.id.back:
			AndroidUtils.finishActivity(this);
			break;
		default:
			break;
		}
	}

	private void getInfoInput() {
		mPhoneStr = mAccount.getText().toString().trim();
		mPasswordStr = mPassword.getText().toString().trim();

	}

	private void login() {
		if (StringUtils.isEmpty(mPhoneStr)) {
			Toast.makeText(this, getString(R.string.please_input_phone_null),
					Toast.LENGTH_SHORT).show();
			dismissmeidialog();
			return;
		}
		if (!AndroidUtils.isPhoneNumberValid(mPhoneStr)) {
			Toast.makeText(this, getString(R.string.please_input_phone_again),
					Toast.LENGTH_SHORT).show();
			mAccount.setText("");
			mPhoneStr = null;
			dismissmeidialog();
			return;
		}
		if (StringUtils.isEmpty(mPasswordStr)) {
			Toast.makeText(this,
					getString(R.string.please_input_password_null),
					Toast.LENGTH_SHORT).show();
			dismissmeidialog();
			return;
		}
		// NetHelper.loginUser(mPhoneStr, mPasswordStr,
		// new AsyncHttpResponseHandler() {
		//
		// @Override
		// public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
		// LogUtils.logd(TAG, "loginUser +onSuccess");
		// resolveLoginUser(arg2);
		// }
		//
		// @Override
		// public void onFailure(int arg0, Header[] arg1, byte[] arg2,
		// Throwable arg3) {
		// Toast.makeText(LoginActivity.this,
		// getString(R.string.request_service_failure),
		// Toast.LENGTH_SHORT).show();
		// LogUtils.logd(TAG, "loginUser +onFailure");
		// dismissmeidialog();
		// }
		// });
	}

	@SuppressWarnings("unused")
	private void resolveLoginUser(byte[] responseBody) {
		// String json;
		// try {
		// json = new String(responseBody, "UTF-8");
		// JSONObject obj = new JSONObject(json);
		// String msg = obj.optString("msg");
		// String code = obj.optString("code");
		// JSONObject result = obj.optJSONObject("result");
		// if ("0".equals(code)) {
		// mIsThreeLogin = false;
		// savaLoginInfo(result);
		// } else {
		// Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT)
		// .show();
		// dismissmeidialog();
		// }
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }

	}

	private void initView() {

		mFindPassword = (RelativeLayout) findViewById(R.id.find_password_rl);
		mAccount = (EditText) findViewById(R.id.account);
		mPassword = (EditText) findViewById(R.id.password);
		mAccountDelete = (ImageView) findViewById(R.id.account_delete);
		mPassDelete = (ImageView) findViewById(R.id.password_delete);
		mLogin = (Button) findViewById(R.id.login);

		mAccountDelete.setVisibility(View.GONE);
		mPassDelete.setVisibility(View.GONE);

		setTextChangedListener();
		mFindPassword.setOnClickListener(this);
		mAccountDelete.setOnClickListener(this);
		mPassDelete.setOnClickListener(this);
		mLogin.setOnClickListener(this);

	}

	private void initData() {

	}

	/**
	 * 添加编辑框监听
	 */
	private void setTextChangedListener() {
		mAccount.addTextChangedListener(new TextWatcher() {

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
					mAccountDelete.setVisibility(View.VISIBLE);
				} else {
					mAccountDelete.setVisibility(View.GONE);
				}
			}
		});
		mPassword.addTextChangedListener(new TextWatcher() {

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
					mPassDelete.setVisibility(View.VISIBLE);
				} else {
					mPassDelete.setVisibility(View.GONE);
				}
			}
		});
	}

	/**
	 * 登录成功后，保存用户信息
	 * 
	 * @param result
	 */
	@SuppressWarnings("unused")
	private void savaLoginInfo(JSONObject result) {

		String token = result.optString("token");
		JSONObject accountinfo = result.optJSONObject("accountinfo");
		String id = accountinfo.optString("id");

		user.setUname(mPhoneStr);
		user.setPassword(mPasswordStr);
		user.setId(id);
		user.setToken(token);
		user.setIs_login(true);
		// user.setOpen_id(mOpenid);
		// user.setUsername(mUserName);
		user.setIs_three_login(mIsThreeLogin);

		mUserController.saveUserInfo(user);

		// getOrderInfo();
	}

	/**
	 * 双击退出函数
	 */
	private void exitBy2Click() {
		Timer tExit = null;
		if (mIsExit == false) {
			mIsExit = true; // 准备退出
			Toast.makeText(this, getString(R.string.tip_double_click_exit),
					Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					mIsExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			finish();
			System.exit(0);
		}
	}

}
