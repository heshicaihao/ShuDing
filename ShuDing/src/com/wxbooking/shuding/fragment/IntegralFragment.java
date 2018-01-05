package com.wxbooking.shuding.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wxbooking.shuding.R;
import com.wxbooking.shuding.adapter.IntegralSearchAdapter;
import com.wxbooking.shuding.base.BaseFragment;
import com.wxbooking.shuding.bean.IntegralBean;
import com.wxbooking.shuding.listener.OnRefreshListener;
import com.wxbooking.shuding.net.NetHelper;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.utils.LogUtils;
import com.wxbooking.shuding.widget.RefreshListView;

@SuppressLint("ValidFragment")
public class IntegralFragment extends BaseFragment implements OnRefreshListener {

	private View mView;
	@SuppressWarnings("unused")
	private String mChannelID;

	private RefreshListView mListView;
	private IntegralSearchAdapter mAdapter;
	// getJSONArray()
	private List<IntegralBean> clientList = new ArrayList<IntegralBean>();
	private int mPage = 1;
	private String pager_total = "1";
	private int mPageCout = 5;

	public IntegralFragment() {

	}

	public IntegralFragment(String channelId) {
		this.mChannelID = channelId;
		LogUtils.log(TAG, "ID :" + channelId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.integral_fragment, null, false);
		initView(mView);
		initData();

		return mView;
	}

	@Override
	public void onDownPullRefresh() {
		// TODO Auto-generated method stub
		mPage = 1;
		initData(true);
	}

	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		if (mPage > Integer.parseInt(pager_total)) {
			mListView.hideFooterView();
			if (AndroidUtils.isNoFastClick()) {
				Toast.makeText(getApplication(),
						this.getString(R.string.no_more), Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			initData(false);
		}
	}

	private void initView(View mView) {
		// TODO Auto-generated method stub
		mListView = (RefreshListView) mView.findViewById(R.id.listview);

		mAdapter = new IntegralSearchAdapter(getActivity(), getApplication());
		mListView.setAdapter(mAdapter);

		mListView.setOnRefreshListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		if (AndroidUtils.isNetworkAvailable(getActivity())) {
			// showmeidialog();
			initData(true);
		} else {
			Toast.makeText(getApplication(), this.getString(R.string.no_net),
					Toast.LENGTH_SHORT).show();
		}
	}

	private void initData(boolean flag) {

		// if (mUser.isIs_login()) {
		getOrderInfo(flag);
		// }
	}

	private void getOrderInfo(final boolean flag) {
		NetHelper.getOrderList("", "", "all", mPage, mPageCout,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						LogUtils.logd(TAG, "getOrderList+onSuccess");
						resolveOrderList(flag, arg2);
						dismissmeidialog();
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						LogUtils.logd(TAG, "getOrderList+onFailure");
						dismissmeidialog();
					}
				});
	}

	private void resolveOrderList(boolean flag, byte[] arg2) {
		JSONArray arr = getIntegralSearchJSONArray();
		List<IntegralBean> list = IntegralBean.getIntegralList(arr);
		if (list != null && list.size() > 0) {
			if (flag) {
				clientList.clear();
			}
			mPage++;

			clientList.addAll(list);
			mAdapter.setData(clientList);
			mAdapter.notifyDataSetChanged();
			mListView.hideHeaderView();
			mListView.hideFooterView();
		} else {
			mPage = 0;
		}
	}

	/**
	 * 获取客户列表 信息
	 * 
	 * @return
	 */
	private JSONArray getIntegralSearchJSONArray() {
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("integral_id", "00001");
			obj.put("reason", "每日签到");
			obj.put("integral_num", "+10");
			obj.put("nick_name", "卖刀的人");
			obj.put("real_name", "张二蛋子");
			obj.put("phone", "13544442222");
			obj.put("time", "20160606");
			arr.put(0, obj);
			arr.put(1, obj);
			arr.put(2, obj);
			arr.put(3, obj);
			arr.put(4, obj);
			arr.put(5, obj);
			arr.put(6, obj);
			arr.put(7, obj);
			arr.put(8, obj);
			arr.put(9, obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return arr;

	}

}
