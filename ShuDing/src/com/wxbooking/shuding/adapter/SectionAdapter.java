package com.wxbooking.shuding.adapter;

import java.util.List;

import com.wxbooking.shuding.R;
import com.wxbooking.shuding.bean.TopLabelObject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 管理员分组 列表 List 的适配器
 * 
 * @author heshicaihao
 */
public class SectionAdapter extends BaseAdapter {

    private final Context context;
    private final List<TopLabelObject> itemList;
    private final int selectid;

    public SectionAdapter(Context context, List<TopLabelObject> item, int selectid) {
        this.context = context;
        this.itemList = item;
        this.selectid = selectid;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataList data = null;
        if (convertView == null) {
            data = new DataList();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.view_secect_category_item, null);
            data.mText = (TextView) convertView.findViewById(R.id.client_name);
            data.mImage = (ImageView) convertView
                    .findViewById(R.id.checkimg);
            convertView.setTag(data);
        } else {
            data = (DataList) convertView.getTag();
        }
        data.mText.setText(itemList.get(position).name);
        if (selectid == position) {
            data.mImage.setVisibility(View.VISIBLE);
            convertView.setBackgroundResource(R.color.tab_view_press);
            data.mText.setTextColor(context.getResources()
                    .getColor(R.color.main_color));
        } else {
            data.mImage.setVisibility(View.INVISIBLE);
            convertView.setBackgroundResource(R.color.white);
            data.mText.setTextColor(Color.BLACK);
        }
        return convertView;
    }

    public void freshdata() {
        this.notifyDataSetChanged();
    }

    private class DataList {
        public TextView mText;
        public ImageView mImage;
    }
}