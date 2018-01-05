package com.wxbooking.shuding.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wxbooking.shuding.MainActivity;
import com.wxbooking.shuding.R;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.utils.AnimationUtil;
import com.wxbooking.shuding.utils.SharedpreferncesUtil;

/**
 * 启动页
 * 
 * @author heshicaihao
 */
public class StartActivity extends BaseActivity implements OnClickListener {

	private TextView mStart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);
		DelayedJumpNext();
		initUserInfo();
		initView();
	}

	private void initView() {
		mStart= (TextView)findViewById(R.id.start_text);
		
		mStart.setOnClickListener(this);
	}

	/**
	 * 延时跳转下一页
	 */
	private void DelayedJumpNext() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					public void run() {
						if (SharedpreferncesUtil
								.getGuided(getApplicationContext())) {
							gotoMainActivity();
							StartActivity.this.finish();
						} else {
							gotoLoginActivity();
							StartActivity.this.finish();
						}
					}
				});
			}
		}, 2000);
	}

	private void gotoMainActivity() {
//		int CurrentTabNum = 0;
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//		intent.putExtra("CurrentTabNum", CurrentTabNum);
		startActivity(intent);
		StartActivity.this.finish();
	}

	private void gotoLoginActivity() {
		Intent intent = new Intent(StartActivity.this, LoginActivity.class);
		StartActivity.this.startActivity(intent);
		StartActivity.this.finish();
	}
	
	@Override
	protected void onResume() {
//		JPushInterface.onResume(this);
		super.onResume();

	}
	
	@Override
	protected void onPause() {
//		JPushInterface.onPause(this);
		super.onPause();
		
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.start_text:
			if (SharedpreferncesUtil.getGuided(getApplicationContext())) {
				gotoMainActivity();
				AnimationUtil.getIntence().cancelAlphaAnimation();
				StartActivity.this.finish();
			} else {
				gotoLoginActivity();
				AnimationUtil.getIntence().cancelAlphaAnimation();
				StartActivity.this.finish();

			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 初始化 用户信息
	 */
	private void initUserInfo() {
		user.setUname("");
		user.setPassword("");
		user.setId("");
		user.setToken("");
		user.setIs_login(false);
		user.setTemp_id("");
		user.setTemp_token("");
		user.setIs_temp_login(false);
		user.setOpen_id("");
		user.setType("");
		user.setIs_three_login(false);
		user.setIs_order_null(true);
		mUserController.saveUserInfo(user);
	}


}
