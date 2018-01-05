package com.wxbooking.shuding.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.activity.AboutMeActivity;
import com.wxbooking.shuding.activity.LoginActivity;
import com.wxbooking.shuding.activity.ManagerListActivity;
import com.wxbooking.shuding.activity.ResetPasswordActivity;
import com.wxbooking.shuding.base.BaseFragment;
import com.wxbooking.shuding.bean.UserBean;
import com.wxbooking.shuding.common.UpdateManager;
import com.wxbooking.shuding.common.UserController;
import com.wxbooking.shuding.net.MyURL;
import com.wxbooking.shuding.utils.SharedpreferncesUtil;

/**
 * 设置主页
 * 
 * @author heshicaihao
 */
public class TabSettingFragment extends BaseFragment implements OnClickListener {

	private TextView mTitle;
	private LinearLayout mManagerSettingLL;
	private LinearLayout mOnlineHelpLL;
	private LinearLayout mUpdateAppLL;
	private LinearLayout mOnlineFeedbackLL;
	private LinearLayout mAboutMeLL;
	private LinearLayout mResetPasswordLL;
	private Button mLogoutLL;

	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_tab_setting, null);
		}
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
		initView(mView);
		return mView;
	}

	public void initView(View view) {
		mTitle = (TextView) view.findViewById(R.id.title);
		mManagerSettingLL = (LinearLayout) view
				.findViewById(R.id.manager_setting_ll);
		mResetPasswordLL = (LinearLayout) view
				.findViewById(R.id.reset_password_ll);
		mOnlineHelpLL = (LinearLayout) view.findViewById(R.id.online_help_ll);
		mUpdateAppLL = (LinearLayout) view.findViewById(R.id.update_app_ll);
		mOnlineFeedbackLL = (LinearLayout) view
				.findViewById(R.id.online_feedback_ll);
		mAboutMeLL = (LinearLayout) view.findViewById(R.id.about_me_ll);

		mLogoutLL = (Button) view.findViewById(R.id.logout_ll);

		mTitle.setText(R.string.tab_setting);

		mManagerSettingLL.setOnClickListener(this);
		mResetPasswordLL.setOnClickListener(this);
		mOnlineHelpLL.setOnClickListener(this);
		mUpdateAppLL.setOnClickListener(this);
		mOnlineFeedbackLL.setOnClickListener(this);
		mAboutMeLL.setOnClickListener(this);
		mLogoutLL.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.manager_setting_ll:
			gotoOther(getApplication(), ManagerListActivity.class);
			break;
		case R.id.reset_password_ll:
			gotoOther(getApplication(), ResetPasswordActivity.class);
			break;
		case R.id.online_help_ll:
			startOtherWeb(getApplication(),
					this.getString(R.string.online_help), MyURL.HELP_URL);
			break;
		case R.id.update_app_ll:
			onClickUpdate();
			break;
		case R.id.online_feedback_ll:
			startOtherWeb(getApplication(),
					this.getString(R.string.online_feedback),
					MyURL.SUGGESTIONS_URL);
			break;
		case R.id.about_me_ll:
			gotoOther(getApplication(), AboutMeActivity.class);
			break;
		case R.id.logout_ll:
			gotoLogout();
			SharedpreferncesUtil.setUnGuided(getApplication());
			gotoOther(getApplication(), LoginActivity.class);
			getActivity().finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 检查更新
	 */
	private void onClickUpdate() {
		new UpdateManager(getApplication(), false);
	}

	/**
	 * 退出登录
	 */
	public void gotoLogout() {
		UserController mUserController = UserController
				.getInstance(getApplication());
		UserBean user = mUserController.getUserInfo();
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
		mUserController.saveUserInfo(user);
		// Toast.makeText(getApplication(),
		// getApplication().getString(R.string.logout_ok),
		// Toast.LENGTH_SHORT).show();
	}

}
