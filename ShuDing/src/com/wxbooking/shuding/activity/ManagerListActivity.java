package com.wxbooking.shuding.activity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.adapter.ManagerHolder;
import com.wxbooking.shuding.base.BaseActivity;
import com.wxbooking.shuding.bean.ManagerBean;
import com.wxbooking.shuding.dialog.CustomDialog;
import com.wxbooking.shuding.utils.AndroidUtils;
import com.wxbooking.shuding.utils.LogUtils;

/**
 * 管理员列表（管理员设置）
 * 
 * @author heshicaihao
 * 
 */
public class ManagerListActivity extends BaseActivity implements
		OnClickListener {

	private TextView mTitle;
	private TextView mSave;
	private ImageView mBack;
	private ListView mListView;
	private ManagerAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_list);

		initView();
		initData();
	}

	private void initData() {
		JSONArray arr = getJSONArray();
		List<ManagerBean> list = ManagerBean.getManagerList(arr);
		mAdapter = new ManagerAdapter(this, this, list);
		mListView.setAdapter(mAdapter);
	}

	private void initView() {
		mTitle = (TextView) findViewById(R.id.title);
		mSave = (TextView) findViewById(R.id.save);
		mBack = (ImageView) findViewById(R.id.back);
		mListView = (ListView) findViewById(R.id.listview);

		mTitle.setText(R.string.setting_manager);
		mSave.setVisibility(View.VISIBLE);
		mSave.setText(R.string.add_manager);

		mBack.setOnClickListener(this);
		mSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.back:
			AndroidUtils.finishActivity(this);
			break;
		case R.id.save:
			String data = getData("10001", "");
			Intent intent = new Intent(this, EditManagerActivity.class);
			intent.putExtra("data", data);
			this.startActivity(intent);
			AndroidUtils.enterActvityAnim(this);
			break;

		default:
			break;
		}
	}

	public static class ManagerAdapter extends BaseAdapter {
		private String TAG = "CartAdapter";
		private List<ManagerBean> mData;

		private Context mContext;
		private Activity mActivity;
		private LayoutInflater mInflater;
		private ManagerHolder mHolder;

		public ManagerAdapter(Activity mActivity, Context context,
				List<ManagerBean> mData) {
			this.mData = mData;
			this.mContext = context;
			this.mActivity = mActivity;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			EditOnClickListener editListener = null;
			DeleteOnClickListener deleteListener = null;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_manager_list,
						null);
				mHolder = new ManagerHolder();
				getItemView(convertView);

				convertView.setTag(mHolder);
				editListener = new EditOnClickListener(position);
				deleteListener = new DeleteOnClickListener(position);
				mHolder.edit_btn_Tv.setOnClickListener(editListener);
				mHolder.delete_btn_Tv.setOnClickListener(deleteListener);

			} else {
				mHolder = (ManagerHolder) convertView.getTag();
			}
			setData2UI(position);

			return convertView;

		}

		/**
		 * 给UI加载数据
		 * 
		 * @param position
		 * @param deleteListener
		 * @param editListener
		 */
		private void setData2UI(int position) {
			ManagerBean works = mData.get(position);

			String manager_name = works.getManager_name();
			mHolder.manager_name_Iv.setText(manager_name);

			String account_enable = works.getAccount_enable();
			mHolder.account_enable_Iv.setText(account_enable);

			String phone = works.getPhone();
			mHolder.phone_Tv.setText(phone);

			String manager_type = works.getManager_type();
			mHolder.manager_type_Tv.setText(manager_type);

		}

		/**
		 * 找到Item 的 控件
		 * 
		 * @param convertView
		 */
		private void getItemView(View convertView) {
			mHolder.manager_name_Iv = (TextView) convertView
					.findViewById(R.id.manager_name);

			mHolder.account_enable_Iv = (TextView) convertView
					.findViewById(R.id.account_enable);

			mHolder.phone_Tv = (TextView) convertView.findViewById(R.id.phone);

			mHolder.manager_type_Tv = (TextView) convertView
					.findViewById(R.id.manager_type);

			mHolder.edit_btn_Tv = (TextView) convertView
					.findViewById(R.id.edit_btn);

			mHolder.delete_btn_Tv = (TextView) convertView
					.findViewById(R.id.delete_btn);

		}

		/**
		 * 删除对话框
		 * 
		 * @param position
		 */
		private void showAlertDialog(final int position) {
			CustomDialog.Builder builder = new CustomDialog.Builder(mActivity);
			builder.setMessage(mContext.getString(R.string.delete_works));
			builder.setPositiveButton(mContext.getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							mData.remove(position);
							notifyDataSetChanged();
							dialog.dismiss();

						}
					});

			builder.setNegativeButton(mContext.getString(R.string.cancel),
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							LogUtils.logd(TAG, "position:");
							dialog.dismiss();
						}
					});
			builder.create().show();

		}

		private class DeleteOnClickListener implements OnClickListener {
			int mPosition;

			public DeleteOnClickListener(int inPosition) {
				mPosition = inPosition;
			}

			@Override
			public void onClick(View v) {
				showAlertDialog(mPosition);
			}

		}

		private class EditOnClickListener implements OnClickListener {
			int mPosition;

			public EditOnClickListener(int inPosition) {
				mPosition = inPosition;
			}

			@Override
			public void onClick(View v) {
				ManagerBean managerbean = mData.get(mPosition);
				String data = getData("10002", managerbean.getManager_id());
				// LogUtils.logd(TAG, data);
				Intent intent = new Intent(mContext, EditManagerActivity.class);
				intent.putExtra("data", data);
				mActivity.startActivity(intent);
				AndroidUtils.enterActvityAnim(mActivity);
			}

			/**
			 * 拼Json 给编辑管理员 信息
			 * 
			 * @param code
			 * @param manager_id
			 * @return data
			 */
			private String getData(String code, String manager_id) {
				JSONObject object = new JSONObject();
				try {
					object.put("code", code);
					JSONObject contentJson = new JSONObject();
					contentJson.put("manager_id", manager_id);
					object.put("content", contentJson);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				String data = object.toString();
				return data;
			}
		}
	}

	/**
	 * 获取订单列表 信息
	 * 
	 * @return
	 */
	private JSONArray getJSONArray() {
		JSONArray arr = new JSONArray();
		JSONObject obj = new JSONObject();
		try {
			obj.put("manager_id", "1000011");
			obj.put("manager_name", "张三丰");
			obj.put("account_enable", "已启用");
			obj.put("phone", "13588888888");
			obj.put("manager_type", "超级管理员");
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

	/**
	 * 拼Json 给编辑管理员 信息
	 * 
	 * @param code
	 * @param manager_id
	 * @return data
	 */
	private String getData(String code, String manager_id) {
		JSONObject object = new JSONObject();
		try {
			object.put("code", code);
			JSONObject contentJson = new JSONObject();
			contentJson.put("manager_id", manager_id);
			object.put("content", contentJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String data = object.toString();
		return data;
	}

}
