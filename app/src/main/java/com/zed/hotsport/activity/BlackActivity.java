package com.zed.hotsport.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zed.hotsport.R;
import com.zed.hotsport.adapter.BlackListAdapter;
import com.zed.hotsport.bean.BlackBean;
import com.zed.hotsport.utils.ToastUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BlackActivity extends AppCompatActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.activity_black)
    LinearLayout activityBlack;
    @Bind(R.id.list_View)
    ListView black_listView;
    private ArrayList<BlackBean> blackList;
    private BlackListAdapter blackListAdapter;
    private AlertDialog dialog;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black);
        ButterKnife.bind(this);
        initToolBar();
        initData();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        black_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //查看详情

            }
        });

        black_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //有编辑和删除功能
                showItemDialog(position);
                return true;
            }
        });
    }

    private void showItemDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BlackActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){//编辑


                }else if(which == 1){//删除

                }
            }
        });
        builder.create().show();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库中查询数据
        items = new String[]{"编辑","删除"};
        blackList = new ArrayList<>();
        for(int i = 0;i < 7;i++){
           // blackList.add(new BlackBean())

        }
        if(blackList.size()>0){
            blackListAdapter = new BlackListAdapter(blackList,this);
            black_listView.setAdapter(blackListAdapter);
        }
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolBar.setNavigationIcon(R.mipmap.l_arrow);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlackActivity.this.finish();
            }
        });
        toolBar.setTitle("黑名单管理");//设置主标题
        toolBar.setTitleTextColor(getResources().getColor(R.color.color_f5f5f5));
        toolBar.setTitleTextAppearance(this, R.style.ActionBarTitle);

        toolBar.inflateMenu(R.menu.base_black_menu);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuId = item.getItemId();
                switch (menuId) {
                    case R.id.action_add:
                        // 展示添加黑名单的对话框
                        showAddBlackDialog();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 添加黑名单对话框
     */
    private void showAddBlackDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        View view = getLayoutInflater()
                .inflate(R.layout.add_black_dialog, null);
        dialog = adb.create();
        final EditText black_name = (EditText) view
                .findViewById(R.id.blacklist_add_name);
        final EditText black_imsi = (EditText) view
                .findViewById(R.id.blacklist_add_imsi);
        final EditText black_imei = (EditText) view
                .findViewById(R.id.blacklist_add_imei);
        final EditText black_esn = (EditText) view
                .findViewById(R.id.blacklist_add_esn);
        final EditText black_mac = (EditText) view
                .findViewById(R.id.blacklist_add_mac);
        TextView btnOk = (TextView) view.findViewById(R.id.btn_ok);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mac = " ";
                String name = black_name.getText().toString().trim();
                String imsi = black_imsi.getText().toString().trim();
                String imei = black_imei.getText().toString().trim();
                String esn = black_esn.getText().toString().trim();
                Pattern pattern = Pattern
                        .compile("[0-9A-Fa-f]{8}");
                Matcher matcher1 = pattern.matcher(esn);
                if (!TextUtils.isEmpty(black_esn.getText().toString().trim())) {
                    esn = black_esn.getText().toString().trim().toUpperCase();
                }
                String mac1 = black_mac.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showToast(BlackActivity.this,"黑名单名不能为空",Toast.LENGTH_SHORT);
                    return;

                }
                if (TextUtils.isEmpty(imsi) && TextUtils.isEmpty(imei)
                        && TextUtils.isEmpty(esn) && TextUtils.isEmpty(mac1)) {

                    ToastUtils.showToast(BlackActivity.this,"IMSI/IMEI/ESN/MAC请至少填写一个",Toast.LENGTH_SHORT);
                    return;
                }

                if ((imsi.length() == 15)
                        || (imei.length() == 15)
                        || ((esn.length() == 8) && matcher1.matches())
                        || (mac1.length() == 12)) {

                    if (mac1.length() == 12) {
                        mac = mac1.substring(0, 2).toUpperCase() + ":"
                                + mac1.substring(2, 4).toUpperCase() + ":"
                                + mac1.substring(4, 6).toUpperCase() + ":"
                                + mac1.substring(6, 8).toUpperCase() + ":"
                                + mac1.substring(8, 10).toUpperCase() + ":"
                                + mac1.substring(10, 12).toUpperCase();
                        Pattern p = Pattern
                                .compile("([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}");
                        Matcher matcher = p.matcher(mac);
                        if (!matcher.matches()) {
                            ToastUtils.showToast(BlackActivity.this,"您输入的mac包含非十六进制字符！",Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                    if (imei.length() != 0 && imei.length()==15) {
                        if (imei.charAt(14) != '0') {
                            black_imei.setText(imei.substring(0, 14) + "0");

                            imei = black_imei.getText().toString().trim();
                        }
                    }
                    if ((imsi.length() != 0 && imsi.length()!=15)){
                        ToastUtils.showToast(BlackActivity.this, "IMSI/IMEI格式为15位数字 ,ESN格式为8位十六进制字符串,mac格式为12位十六进制字符串！（1到9和a到f的组合）", Toast.LENGTH_LONG);
                        return;
                    }
                    if ((imei.length() != 0 && imei.length()!=15)){
                        ToastUtils.showToast(BlackActivity.this, "IMSI/IMEI格式为15位数字 ,ESN格式为8位十六进制字符串,mac格式为12位十六进制字符串！（1到9和a到f的组合）", Toast.LENGTH_LONG);
                        return;
                    }
                    if ((esn.length() != 0 && esn.length()!=8)){
                        ToastUtils.showToast(BlackActivity.this, "IMSI/IMEI格式为15位数字 ,ESN格式为8位十六进制字符串,mac格式为12位十六进制字符串！（1到9和a到f的组合）", Toast.LENGTH_LONG);
                        return;
                    }

                    //注意，在这里插入到数据库中


                } else {
                    ToastUtils.showToast(BlackActivity.this, "IMSI/IMEI格式为15位数字 ,ESN格式为8位十六进制字符串,mac格式为12位十六进制字符串！（1到9和a到f的组合）", Toast.LENGTH_LONG);
                    return;
                }

                // 刷新页面，在页面上显示新的黑名单
                // 将黑名单添加至集合，刷新listView
                BlackBean blackUser = new BlackBean(name, imsi, imei, esn, mac,0);//0为黑名单

                blackList.add(blackUser); // 将黑名单加至集合
                if (blackListAdapter == null) {
                    // new 出来的adapter 会让listView 显示第一条数据
                    blackListAdapter = new BlackListAdapter(blackList,
                            BlackActivity.this);
                    black_listView.setAdapter(blackListAdapter);
                } else {
                    blackListAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setView(view); //设置view
        dialog.show();
    }

}
