package com.zed.hotsport.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zed.hotsport.R;
import com.zed.hotsport.bean.BlackBean;

import java.util.ArrayList;

public class BlackListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<BlackBean> blackBean;

    public BlackListAdapter(ArrayList<BlackBean> users, Activity activity) {
        super();
        this.blackBean = users;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return blackBean.size();
    }

    @Override
    public Object getItem(int position) {

        return blackBean.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.black_list, null);
            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_black_name);
            holder.tv_mark = (TextView) convertView.findViewById(R.id.tv_mark);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(blackBean.get(position).getName());
        if (!TextUtils.isEmpty(blackBean.get(position).getImsi())) {

            holder.tv_mark.setText(blackBean.get(position).getImsi());
        } else if (!TextUtils.isEmpty(blackBean.get(position).getImei())) {

            holder.tv_mark.setText(blackBean.get(position).getImei());
        } else if (!TextUtils.isEmpty(blackBean.get(position).getEsn())) {

            holder.tv_mark.setText(blackBean.get(position).getEsn());
        } else if (!TextUtils.isEmpty(blackBean.get(position).getMac())) {

            holder.tv_mark.setText(blackBean.get(position).getMac());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_mark;
    }
}

