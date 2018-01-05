package com.wxbooking.shuding.base;

import com.wxbooking.shuding.activity.OtherWebActivity;
import com.wxbooking.shuding.common.AppContext;
import com.wxbooking.shuding.dialog.CustomProgressDialog;
import com.wxbooking.shuding.utils.AndroidUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {
	public String TAG = getClass().getName();

	protected LayoutInflater mInflater;
	protected ProgressDialog mProgress;
	protected AppContext mContext;
	protected int mCurrentPage = 1;
	protected int mOffset = 0; // 第N条数据
	protected boolean mIsPage = true; // 是否还有下一页
	protected int mTotal_page;// 总页数
	public CustomProgressDialog dialog;

	public AppContext getApplication() {
		return (AppContext) getActivity().getApplication();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog = new CustomProgressDialog(getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.mInflater = inflater;
		View view = super.onCreateView(inflater, container, savedInstanceState);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	protected int getLayoutId() {
		return 0;
	}

	protected View inflateView(int resId) {
		return this.mInflater.inflate(resId, null);
	}

	public boolean onBackPressed() {
		return false;
	}

	/**
	 * 显示进度条
	 */
	protected void showProgressDialog() {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (mProgress == null) {
					mProgress = new ProgressDialog(getActivity());
					mProgress.setMessage("正在加载,请稍后...");
					mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					mProgress.setCancelable(true);
				}
				mProgress.show();
			}
		});
	}

	/**
	 * 显示进度条
	 */
	protected void showProgressDialog(final String message) {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (mProgress == null) {
					mProgress = new ProgressDialog(getActivity());
					mProgress.setMessage(message);
					mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					mProgress.setCancelable(true);
				}
				mProgress.show();
			}
		});
	}

	/**
	 * 得到一个进度条
	 * 
	 * @param msg
	 * @return
	 */
	public ProgressDialog getProgressDialog(String msg) {
		mProgress = new ProgressDialog(getActivity());
		// progressDialog.setIndeterminate(true);
		mProgress.setMessage(msg);
		mProgress.setCancelable(true);
		return mProgress;
	}

	/**
	 * 隐藏进度条
	 */
	protected void dismissProgressDialog() {
		getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (mProgress != null) {
					mProgress.dismiss();
				}
			}
		});
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
		AndroidUtils.enterActvityAnim(getActivity());
	}

//	private void initView(View view) {
//	};

	public void showmeidialog() {

		dialog.show();
	}

	public void dismissmeidialog() {
		dialog.dismiss();
	}

}