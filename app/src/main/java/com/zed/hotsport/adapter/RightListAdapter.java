package com.zed.hotsport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zed.hotsport.R;
import com.zed.hotsport.bean.UserBean;
import com.zed.hotsport.utils.ConvertCodeUtility;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/10.
 */

public class RightListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<UserBean> data;

    public RightListAdapter(ArrayList<UserBean> userBeans, Context context){
        this.context = context;
        this.data = userBeans;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_layout,null);
            viewHolder.imei = (TextView) convertView.findViewById(R.id.tv2);
            viewHolder.operator = (TextView) convertView.findViewById(R.id.tv3);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv4);
            viewHolder.blackName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.TSM = (TextView) convertView.findViewById(R.id.tv_TSM);
            viewHolder.lac = (TextView) convertView.findViewById(R.id.tv_lac);
            viewHolder.power = (TextView) convertView.findViewById(R.id.tv_power);
            viewHolder.place = (TextView) convertView.findViewById(R.id.tv_place);
            viewHolder.phoneType = (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imei.setText(data.get(position).getImei());
        viewHolder.operator.setText(data.get(position).getOprerator());
        viewHolder.blackName.setText(data.get(position).getBlackName());

        String time = ConvertCodeUtility.getStrByDate(data.get(position).getTimer()).substring(11,19);
        viewHolder.time.setText(time);

        viewHolder.TSM.setText(data.get(position).getTSM());
        viewHolder.lac.setText(data.get(position).getLac());
        viewHolder.power.setText(data.get(position).getPower());
        viewHolder.place.setText(data.get(position).getPlace());
        viewHolder.phoneType.setText(data.get(position).getPhoneType());
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
        TextView imei;
        TextView operator;
        TextView time;
        TextView blackName;
        TextView TSM;
        TextView lac;
        TextView power;
        TextView place;
        TextView phoneType;
    }
    public ArrayList<UserBean> getData() {
        return data;
    }

    public void setData(ArrayList<UserBean> data) {
        this.data = data;
    }


}
