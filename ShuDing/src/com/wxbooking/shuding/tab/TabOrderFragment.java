package com.wxbooking.shuding.tab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.activity.OrderListActivity;
import com.wxbooking.shuding.base.BaseFragment;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.widget.HistogramView;

/**
 * 订单主页 （首页）
 * 
 * @author heshicaihao
 */
public class TabOrderFragment extends BaseFragment implements OnClickListener {

	private ImageView mBack;
	private TextView mTitle;
	
	private View mView;
	private LinearLayout mLinearLayout;
	private HistogramView green;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_tab_order, null);
		}
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
		initView(mView);
		initData();
		return mView;
	}

	public void initView(View view) {
		mBack = (ImageView) view.findViewById(R.id.back);
		mTitle = (TextView) view.findViewById(R.id.title);
		mLinearLayout = (LinearLayout) view.findViewById(R.id.go_order_list);
		green = (HistogramView) view.findViewById(R.id.histogram_view);
		green.start(2);

		mTitle.setText(R.string.tab_order);
		mBack.setVisibility(View.GONE);
		mLinearLayout.setOnClickListener(this);

	}

	private void initData() {
		JSONArray data = getJSONArrayPic();
		green.setData(data);
	}
	

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.go_order_list:
			gotoOrderList();
			break;

		default:
			break;
		}

	}

	/**
	 * 跳转到 OrderPayActivity
	 * 
	 * @param position
	 */
	private void gotoOrderList() {
		Intent intent = new Intent(getApplication(), OrderListActivity.class);
		// intent.putExtra("mTotalStr", mTotalStr);
		// intent.putExtra("mGoodsNameStr", "");
		// intent.putExtra("mGoodsInfoStr", "");
		// intent.putExtra("mOutTradeNo", mOutTradeNo);
		this.startActivity(intent);
		AndroidUtils.enterActvityAnim(getActivity());
	}

	/**
	 * 获取首页图表 数据
	 * 
	 * @return
	 */
	private JSONArray getJSONArrayPic() {
//		long now = new Date().getTime();
//		long now = System.currentTimeMillis();
//		long now = Calendar.getInstance().getTimeInMillis();
		long now = 1469100679;
		long t = 86400;
		JSONArray arr = new JSONArray();
		try {
			JSONObject obj00 = new JSONObject();
			obj00.put("time", now-6*t);
			obj00.put("data", "88");
			JSONObject obj01 = new JSONObject();
			obj01.put("time", now-5*t);
			obj01.put("data", "56");
			JSONObject obj02 = new JSONObject();
			obj02.put("time", now-4*t);
			obj02.put("data", "40");
			JSONObject obj03 = new JSONObject();
			obj03.put("time", now-3*t);
			obj03.put("data", "20");
			JSONObject obj04 = new JSONObject();
			obj04.put("time", now-2*t);
			obj04.put("data", "21");
			JSONObject obj05 = new JSONObject();
			obj05.put("time", now-1*t);
			obj05.put("data", "70");
			JSONObject obj06 = new JSONObject();
			obj06.put("time", now);
			obj06.put("data", "94");
			
			arr.put(0, obj00);
			arr.put(1, obj01);
			arr.put(2, obj02);
			arr.put(3, obj03);
			arr.put(4, obj04);
			arr.put(5, obj05);
			arr.put(6, obj06);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return arr;

	}


}
