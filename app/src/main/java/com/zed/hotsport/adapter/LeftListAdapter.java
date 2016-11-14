package com.zed.hotsport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zed.hotsport.R;
import com.zed.hotsport.bean.UserBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/10.
 */

public class LeftListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<UserBean> data;

    public LeftListAdapter(ArrayList<UserBean> userBeans, Context context){
        this.context = context;
        this.data = userBeans;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_layout1,null);
            viewHolder.imsi = (TextView) convertView.findViewById(R.id.tv1);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imsi.setText(data.get(position).getImei());
        return convertView;

    }
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder{
        TextView imsi;
    }

    public ArrayList<UserBean> getData() {
        return data;
    }

    public void setData(ArrayList<UserBean> data) {
        this.data = data;
    }
}
