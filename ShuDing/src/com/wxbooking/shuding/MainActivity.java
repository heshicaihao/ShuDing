package com.wxbooking.shuding;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.common.AppManager;
import com.wxbooking.shuding.common.UpdateManager;
import com.wxbooking.shuding.frame.MainTab;
import com.wxbooking.shuding.listener.OnTabReselectListener;
import com.wxbooking.shuding.utils.FileUtil;
import com.wxbooking.shuding.widget.MyFragmentTabHost;
/**
 * 主页
 * 
 * @author heshicaihao
 */
public class MainActivity extends BaseActivity implements OnTabChangeListener,
		OnTouchListener {

	// public static final String Islogin = null;
	private MyFragmentTabHost mTabHost;
	private static Boolean mIsExit = false;
	public static boolean isForeground = false;
	public int CurrentTabNum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getDataIntent();
		initView();
		AppManager.getAppManager().addActivity(this);
		initLogin();
		// String json = AppJsonFileReader.getJson(getApplicationContext(),
		// "areaData.txt");
		// FileUtil.saveFile(json, MyConstants.AREA_DATA_DIR,
		// MyConstants.AREA_DATA, MyConstants.TXT);
		// JPushInterface.init(getApplicationContext());
		// registerMessageReceiver();
		
	}

	private void initLogin() {
		user.setUname("");
		user.setPassword("");
		user.setId("");
		user.setToken("");
		user.setIs_login(true);
		user.setOpen_id("");
		user.setUsername("");
		user.setIs_three_login(false);

		mUserController.saveUserInfo(user);

	}

	private void getDataIntent() {
		Intent intent = getIntent();
		CurrentTabNum = intent.getIntExtra("CurrentTabNum", 0);

	}

	@SuppressLint("NewApi")
	public void initView() {

		mTabHost = (MyFragmentTabHost) findViewById(R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		if (android.os.Build.VERSION.SDK_INT > 10) {
			mTabHost.getTabWidget().setShowDividers(0);
		}
		initTabs();
		mTabHost.setCurrentTab(CurrentTabNum);
		mTabHost.setOnTabChangedListener(this);
		onClickUpdate();

	}

	private void initTabs() {
		MainTab[] mTabs = MainTab.values();
		final int size = mTabs.length;
		for (int i = 0; i < size; i++) {
			MainTab mainTab = mTabs[i];
			TabSpec mTab = mTabHost.newTabSpec(getString(mainTab.getResName()));
			View mIndicator = LayoutInflater.from(getApplicationContext())
					.inflate(R.layout.view_home_tab_indicator, null);
			TextView mTitle = (TextView) mIndicator
					.findViewById(R.id.tab_title);
			ImageView mIcon = (ImageView) mIndicator
					.findViewById(R.id.tab_icon);
			Drawable mDrawable = this.getResources().getDrawable(
					mainTab.getResIcon());
			// mTitle.setCompoundDrawablesWithIntrinsicBounds(null, mDrawable,
			// null, null);
			mIcon.setImageDrawable(mDrawable);
			mTitle.setText(getString(mainTab.getResName()));
			mTab.setIndicator(mIndicator);
			mTab.setContent(new TabContentFactory() {

				@Override
				public View createTabContent(String tag) {
					return new View(MainActivity.this);
				}
			});
			mTabHost.addTab(mTab, mainTab.getClz(), null);

			mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		super.onTouchEvent(event);
		boolean mIsConsumed = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& v.equals(mTabHost.getCurrentTabView())) {
			Fragment mCurrentFragment = getCurrentFragment();
			if (mCurrentFragment != null
					&& mCurrentFragment instanceof OnTabReselectListener) {
				OnTabReselectListener listener = (OnTabReselectListener) mCurrentFragment;
				listener.onTabReselect();
				mIsConsumed = true;
			}
		}
		return mIsConsumed;
	}

	/**
	 * 监听返回--是否退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
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
			FileUtil.deleteOrderList();
			finish();
			System.exit(0);
		}
	}

	private Fragment getCurrentFragment() {
		return getSupportFragmentManager().findFragmentByTag(
				mTabHost.getCurrentTabTag());
	}

	@Override
	public void onTabChanged(String tabId) {
		final int size = mTabHost.getTabWidget().getTabCount();
		for (int i = 0; i < size; i++) {
			View v = mTabHost.getTabWidget().getChildAt(i);
			if (i == mTabHost.getCurrentTab()) {
				v.setSelected(true);
			} else {
				v.setSelected(false);
			}
		}
		supportInvalidateOptionsMenu();
	}

	private void onClickUpdate() {
		new UpdateManager(this, true);
	}


}
