package com.wxbooking.shuding.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.activity.IntegralListActivity;
import com.wxbooking.shuding.activity.IntegralRuleActivity;
import com.wxbooking.shuding.activity.ClientListActivity;
import com.wxbooking.shuding.base.BaseFragment;
import com.wxbooking.shuding.bean.PieItemBean;
import com.wxbooking.shuding.widget.PieChartView;
/**
 * 客户主页
 * 
 * @author heshicaihao
 */
public class TabClientFragment extends BaseFragment implements OnClickListener {

	private ImageView mBack;
	private TextView mTitle;

	private View mView;
	private PieChartView pieChartView, pieChartView02, pieChartView03;
	private LinearLayout mUserManageLL;
	private LinearLayout mIntegralDetailLL;
	private LinearLayout mIntegralRuleLL;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_tab_client, null);
		}
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}

		initView(mView);
		initData();

		return mView;
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.client_manage_ll:
			gotoOther(getApplication(), ClientListActivity.class);
			break;
			
		case R.id.integral_detail_ll:
			gotoOther(getApplication(), IntegralListActivity.class);
			break;
			
		case R.id.integral_rule_ll:
			gotoOther(getApplication(), IntegralRuleActivity.class);
			break;

		default:
			break;
		}
	}

	public void initView(View view) {
		mBack = (ImageView) view.findViewById(R.id.back);
		mTitle = (TextView) view.findViewById(R.id.title);

		pieChartView = (PieChartView) view.findViewById(R.id.pie_chart);
		pieChartView02 = (PieChartView) view.findViewById(R.id.pie_chart02);
		pieChartView03 = (PieChartView) view.findViewById(R.id.pie_chart03);
		
		mUserManageLL = (LinearLayout) view.findViewById(R.id.client_manage_ll);
		mIntegralDetailLL = (LinearLayout) view.findViewById(R.id.integral_detail_ll);
		mIntegralRuleLL = (LinearLayout) view.findViewById(R.id.integral_rule_ll);

		mTitle.setText(R.string.tab_client);
		mBack.setVisibility(View.GONE);
		mUserManageLL.setOnClickListener(this);
		mIntegralDetailLL.setOnClickListener(this);
		mIntegralRuleLL.setOnClickListener(this);
	}

	private void initData() {
		PieItemBean[] items = new PieItemBean[] { new PieItemBean("娱乐", 200),
				new PieItemBean("旅行", 100), new PieItemBean("学习", 120),
				new PieItemBean("人际关系", 140), new PieItemBean("交通", 100),
				new PieItemBean("餐饮", 50) };
		pieChartView.setPieItems(items);
		pieChartView02.setPieItems(items);
		pieChartView03.setPieItems(items);

	}



}
