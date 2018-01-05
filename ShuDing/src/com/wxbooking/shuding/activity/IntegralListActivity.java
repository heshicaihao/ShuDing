package com.wxbooking.shuding.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.constants.MyConstants;
import com.wxbooking.shuding.fragment.IntegralFragment;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.utils.StringUtils;
import com.wxbooking.shuding.widget.PagerSlidingTabStrip;
import com.wxbooking.shuding.widget.ZoomOutPageTransformer;

/**
 * 积分列表（积分明细）
 * 
 * @author heshicaihao
 * 
 */
public class IntegralListActivity extends BaseActivity implements
		OnClickListener {

	private ImageView mBack;
	private TextView mSearchTV;
	private RelativeLayout mSearchRL;

	private List<String> mTitelIds = new ArrayList<String>();
	private List<String> TitleName = new ArrayList<String>();
	private List<IntegralFragment> mIntegralFragmentList;

	private ViewPager mContentPager;
	private mPagerAdapter mAdapter;
	private PagerSlidingTabStrip mTabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integral_list);
		initView();
		initData();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			AndroidUtils.finishActivity(this);
			break;
		case R.id.search_ll:
			gotoSearch(MyConstants.INTEGRAL_SEARCH_CODE + "");
			break;
		default:
			break;
		}
	}

	private void initView() {
		mBack = (ImageView) findViewById(R.id.back);
		mSearchRL = (RelativeLayout) findViewById(R.id.search_ll);
		mSearchTV = (TextView) findViewById(R.id.search_et);

		mSearchTV.setHint(this.getResources().getString(
				R.string.input_integral_search_init));
		mBack.setOnClickListener(this);
		mSearchRL.setOnClickListener(this);
	}

	private void setPageAdapter() {
		mContentPager = (ViewPager) this.findViewById(R.id.content_pager);
		mAdapter = new mPagerAdapter(getSupportFragmentManager());
		mContentPager.setAdapter(mAdapter);
		mContentPager.setOffscreenPageLimit(2);
		mContentPager.setPageTransformer(true, new ZoomOutPageTransformer());

		mTabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
		mTabs.setTextColorResource(R.color.light_gray_text);
		mTabs.setDividerColorResource(R.color.common_list_divider);
		// tabs.setUnderlineColorResource(R.color.common_list_divider);
		mTabs.setIndicatorColorResource(R.color.main_color);
		mTabs.setSelectedTextColorResource(R.color.main_color);
		// tabs.setIndicatorHeight(5);
		mTabs.setViewPager(mContentPager);
	}

	private void initData() {
		mIntegralFragmentList = new ArrayList<IntegralFragment>();
		JSONArray titelData = getTitelData();
		resolve(titelData);
	}

	private void resolve(JSONArray titel_list) {
		for (int i = 0; i < titel_list.length(); i++) {
			String titel_list_id = titel_list.optJSONObject(i).optString(
					"titel_list_id");
			String titel_list_name = titel_list.optJSONObject(i).optString(
					"titel_list_name");
			if (!StringUtils.isEmpty(titel_list_id)
					&& !StringUtils.isEmpty(titel_list_name)) {
				mTitelIds.add(titel_list_id);
				TitleName.add(titel_list_name);
				IntegralFragment fragment = new IntegralFragment(titel_list_id);
				mIntegralFragmentList.add(fragment);
			}
		}
		if (mIntegralFragmentList.size() > 0) {
			 setPageAdapter();
		}
	}

	/**
	 * 获取客户列表 信息
	 * 
	 * @return
	 */
	private JSONArray getTitelData() {
		JSONArray arr = new JSONArray();

		try {
			for (int i = 0; i < 6; i++) {
				JSONObject obj = new JSONObject();
				obj.put("titel_list_id", "" + i);
				obj.put("titel_list_name", (i + 1) + "月");
				arr.put(i, obj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return arr;

	}

	private class mPagerAdapter extends FragmentStatePagerAdapter {

//		private String Title[] = { "新 闻", "便民", "社 区", "美食", "娱乐", "电影", "房 产",
//				"汽车" };

		public mPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
//			int[] id = { 5, 18, 27, 37, 21, 36, 23, 24 }; // 新闻 = 5，便民 = 18 ，社区
															// = 27，美食 = 37 ，娱乐
															// = 21，电影 = 36，房产 =
															// 23，汽车 = 24
			return new IntegralFragment(mTitelIds.get(arg0) + "");
		}

		@Override
		public int getCount() {
			return TitleName.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TitleName.get(position);
		}

	}

}
