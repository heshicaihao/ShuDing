package com.wxbooking.shuding.adapter;

import org.json.JSONArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxbooking.shuding.R;

/**
 * 管理员分组 列表 List 的适配器
 * 
 * @author heshicaihao
 */
public class ManagerTypeAdapter extends BaseAdapter {

	private JSONArray mData;

	@SuppressWarnings("unused")
	private Context mContext;
	private LayoutInflater mInflater;
	private int clickTemp = -1;// 选中的位置

	// private ACache mCache;

	// mCache.getAsString(MyConstants.MINWIDTH)
	public ManagerTypeAdapter(Context context, JSONArray jsonArray) {
		// mCache = ACache.get(context);
		this.mData = jsonArray;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		notifyDataSetChanged();
		// clickTemp =Integer.parseInt(mCache
		// .getAsString(MyConstants.SELECTED_ADDRESS));
	}

	public void setSeclection(int position) {
		clickTemp = position;
	}

	@Override
	public int getCount() {
		return mData.length();
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
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_manager_type_list,
					null);
			holder = new ViewHolder();
			holder.manager_type_nameTv = (TextView) convertView
					.findViewById(R.id.manager_type_name);
			holder.checkBox = (ImageView) convertView
					.findViewById(R.id.checkbox);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String manager_type_name = mData.optString(position);
		holder.manager_type_nameTv.setText(manager_type_name);

		if (clickTemp == position) {
			holder.checkBox.setImageResource(R.drawable.checkbox_pressed);
			// mCache.put(MyConstants.SELECTED_ADDRESS, position+"");
		} else {
			holder.checkBox.setImageResource(R.drawable.checkbox_normal);
		}

		return convertView;

	}

	public static class ViewHolder {
		public TextView manager_type_nameTv;
		public ImageView checkBox;
	}

}
