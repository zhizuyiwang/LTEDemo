package com.zed.hotsport.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zed.hotsport.R;
import com.zed.hotsport.TCP.TCPAction;
import com.zed.hotsport.TCP.TCPService;
import com.zed.hotsport.adapter.LeftListAdapter;
import com.zed.hotsport.adapter.RightListAdapter;
import com.zed.hotsport.bean.UserBean;
import com.zed.hotsport.utils.ConvertCodeUtility;
import com.zed.hotsport.utils.OpenDialogUtils;
import com.zed.hotsport.utils.ToastUtils;
import com.zed.hotsport.utils.UtilTools;
import com.zed.hotsport.view.SyncHorizontalScrollView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.tv1_click)
    TextView tv1Click;
    @Bind(R.id.tv2_click)
    TextView tv2Click;
    @Bind(R.id.tv3_click)
    TextView tv3Click;
    @Bind(R.id.tv_timer)
    TextView tvTimer;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_TMS)
    TextView tvTMS;
    @Bind(R.id.tv_lac)
    TextView tvLac;
    @Bind(R.id.tv_power)
    TextView tvPower;
    @Bind(R.id.tv_place)
    TextView tvPlace;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.myScrollView)
    SyncHorizontalScrollView myScrollView;
    @Bind(R.id.listView1)
    ListView listView1;
    @Bind(R.id.container1)
    LinearLayout container1;
    @Bind(R.id.listView2)
    ListView listView2;
    @Bind(R.id.container2)
    LinearLayout container2;
    @Bind(R.id.container_scrollView)
    SyncHorizontalScrollView containerScrollView;

    private LeftListAdapter leftListAdapter;
    private RightListAdapter rightListAdapter;
    private LayoutInflater inflater;
    private String[] items;
    private ArrayList<UserBean> users = new ArrayList<>();

    private Intent tcpService;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(TCPAction.ACTION_CATCH)) {//上报广播
                /*UserBean user = intent.getParcelableExtra("USER");
                users.add(0,user);
                leftListAdapter.notifyDataSetChanged();
                rightListAdapter.notifyDataSetChanged();
                UtilTools.setListViewHeightBasedOnChildren(listView1);
                UtilTools.setListViewHeightBasedOnChildren(listView2);*/
                ToastUtils.showToast(MainActivity.this,"上报数据",0);
            }
            if(action.equals(TCPAction.ACTTON_CESHI)){
                ToastUtils.showToast(MainActivity.this,"链路打通，得到目标Socket",0);
            }
            if(action.equals(TCPAction.ACTTON_START)){
                ToastUtils.showToast(MainActivity.this,"通讯初始化成功",0);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //设置两个水平控件的联动
        myScrollView.setScrollView(containerScrollView);
        containerScrollView.setScrollView(myScrollView);

        initToolBar();
        initData();
        initReceiver();
        //开始TCP通讯
        startTCPService();
        initListener();

    }

    /**
     * 注册长按监听
     */
    private void initListener() {
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showItemDialog(position);
                return true;
            }
        });

        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showItemDialog(position);
                return true;
            }
        });

    }

    private void showItemDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){//添加至黑名单


                }else if(which == 1){//添加至白名单

                }
            }
        });
        builder.create().show();
    }

    private void startTCPService() {
        Log.e("TAG","开启服务");
        tcpService = new Intent(this, TCPService.class);
        startService(tcpService);
    }

    private void stopTCPService() {
        Log.e("TAG","销毁服务");
        stopService(tcpService);
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TCPAction.ACTION_CATCH);
        filter.addAction(TCPAction.ACTTON_CESHI);
        filter.addAction(TCPAction.ACTTON_START);
        registerReceiver(receiver, filter);
    }

    private void initData() {
        inflater = getLayoutInflater();
        items = new String[]{"添加至黑名单","添加至白名单"};
        for (int i = 0;i < 20;i++){
            addUser();
        }
        leftListAdapter = new LeftListAdapter(users,MainActivity.this);
        rightListAdapter = new RightListAdapter(users,MainActivity.this);
        listView1.setAdapter(leftListAdapter);
        UtilTools.setListViewHeightBasedOnChildren(listView1);
        listView2.setAdapter(rightListAdapter);
        UtilTools.setListViewHeightBasedOnChildren(listView2);
    }

    private void initToolBar() {
        toolBar.setNavigationIcon(R.mipmap.l_arrow);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
        toolBar.setTitle("4G热点侦码");//设置主标题
        toolBar.setTitleTextColor(getResources().getColor(R.color.color_f5f5f5));
        toolBar.setTitleTextAppearance(this, R.style.ActionBarTitle);

        toolBar.inflateMenu(R.menu.base_toolbar_menu);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuId = item.getItemId();
                switch (menuId) {
                    case R.id.action_add:

                        addUser();
                        Intent intent = new Intent(TCPAction.ACTION_CATCH);
                        MainActivity.this.sendBroadcast(intent);
                        break;
                    case R.id.action_setting:
                        Intent setActivity = new Intent(MainActivity.this, SetActivity.class);
                        startActivity(setActivity);
                        break;
                    case R.id.action_black:
                        Intent blackActivity = new Intent(MainActivity.this, BlackActivity.class);
                        startActivity(blackActivity);

                        break;
                    case R.id.action_white:
                        Intent whiteActivity = new Intent(MainActivity.this, WhiteActivity.class);
                        startActivity(whiteActivity);

                        break;
                }
                return true;
            }
        });
    }

    /**
     * 添加上报的信息（假数据）
     */
    private void addUser() {
        UserBean userBean = new UserBean();
        userBean.setImsi("12345678945612");
        userBean.setImei("12345667895465");
        userBean.setBlackName("张三");
        userBean.setLac("123");
        userBean.setOprerator("中国移动");
        userBean.setPhoneType("苹果8plus");
        userBean.setPlace("火星内核");
        userBean.setPower("45db");
        userBean.setTSM("455");
        userBean.setTimer(new Date(System.currentTimeMillis()));
        users.add(0,userBean);
    }

    @OnClick({R.id.tv1_click, R.id.tv2_click, R.id.tv3_click, R.id.tv_timer, R.id.tv_name, R.id.tv_TMS, R.id.tv_lac, R.id.tv_power, R.id.tv_place, R.id.tv_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1_click:
                break;
            case R.id.tv2_click:
                break;
            case R.id.tv3_click:
                break;
            case R.id.tv_timer:
                break;
            case R.id.tv_name:
                break;
            case R.id.tv_TMS:
                break;
            case R.id.tv_lac:
                break;
            case R.id.tv_power:
                break;
            case R.id.tv_place:
                break;
            case R.id.tv_type:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    /**
     * 退出界面的提示
     */
    private void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("是否确定退出？");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();

            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        stopTCPService();
    }
}
