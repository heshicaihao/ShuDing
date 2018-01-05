package com.wxbooking.shuding.base;

import java.util.List;

import com.wxbooking.shuding.activity.OtherWebActivity;
import com.wxbooking.shuding.activity.SearchActivity;
import com.wxbooking.shuding.bean.UserBean;
import com.wxbooking.shuding.common.AppManager;
import com.wxbooking.shuding.common.UserController;
import com.wxbooking.shuding.constants.MyConstants;
import com.wxbooking.shuding.dialog.CustomProgressDialog;
import com.wxbooking.shuding.dialog.FrameProgressDialog;
import com.wxbooking.shuding.utils.AndroidUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


public class BaseActivity extends FragmentActivity {
	public String TAG = getClass().getName();

	public static int LOAD_SUCCESS = 1;
	public static int LOAD_ERROR = -1;

	public static int FIRST_LOAD = 1;
	public static int LOAD_MORE_DATA = 2;
	public static int REFRESH_DATA = 3;
	public UserController mUserController;
	public UserBean user;
	public CustomProgressDialog dialog;
	public FrameProgressDialog frameDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		mUserController = UserController.getInstance(this);
		user = new UserBean();
		dialog = new CustomProgressDialog(this);
		frameDialog = new FrameProgressDialog(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		AndroidUtils.exitActvityAnim(this);
	}


	public void startActivity(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		context.startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		FragmentManager fm = getSupportFragmentManager();
		int index = requestCode >> 16;
		if (index != 0) {
			index--;
			if (fm.getFragments() == null || index < 0
					|| index >= fm.getFragments().size()) {
				return;
			}
			Fragment frag = fm.getFragments().get(index);
			if (frag == null) {
			} else {
				handleResult(frag, requestCode, resultCode, data);
			}
			return;
		}

	}

	/**
	 * 递归调用，对所有子Fragement生效
	 * 
	 * @param frag
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	private void handleResult(Fragment frag, int requestCode, int resultCode,
			Intent data) {
		frag.onActivityResult(requestCode & 0xffff, resultCode, data);
		List<Fragment> frags = frag.getChildFragmentManager().getFragments();
		if (frags != null) {
			for (Fragment f : frags) {
				if (f != null)
					handleResult(f, requestCode, resultCode, data);
			}
		}
	}
	
	public void showFrameDialog() {
		frameDialog.show();
	}

	public void dismissFrameDialog() {
		frameDialog.dismiss();
	}
	
	public void showmeidialog() {

		dialog.show();
	}

	public void dismissmeidialog() {
		dialog.dismiss();
	}
	
	/**
	 * 打开H5界面
	 * 
	 * @param context
	 */
	public void startOtherWeb(Context context, String title, String url) {
		Intent intent = new Intent(context, OtherWebActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	/**
	 * 跳转到其他界面
	 */
	public void gotoOther(Context context, Class<?> activity) {
		Intent intent = new Intent(context, activity);
		startActivity(intent);
		AndroidUtils.enterActvityAnim(this);
	}
	
	/**
	 * 跳转到 SearchActivity
	 * 
	 */
	public void gotoSearch(String search_type) {
		Intent intent = new Intent(this, SearchActivity.class);
		intent.putExtra(MyConstants.SEARCH_TYPE, search_type);
		this.startActivity(intent);
		AndroidUtils.enterActvityAnim(this);
	}

	

}
