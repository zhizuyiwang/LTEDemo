package com.zed.hotsport.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zed.hotsport.R;
import com.zed.hotsport.TCP.TCPAction;
import com.zed.hotsport.TCP.TCPOutHelper;
import com.zed.hotsport.base.BaseApplication;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VersionQueryActivity extends AppCompatActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.activity_version_query)
    LinearLayout activityVersionQuery;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.btn1)
    Button btn1;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(TCPAction.ACTTON_VERSION)) {
                String version = intent.getStringExtra("VERSION");
                tvVersion.setText(version);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_query);
        ButterKnife.bind(this);
        initToolBar();
        initReceiver();
        //查询版本号
        TCPOutHelper.getInstance(this).msgGetVersion(BaseApplication.CurrentEid);
    }

    /**
     * 初始化广播
     */
    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TCPAction.ACTTON_VERSION);
        registerReceiver(receiver, filter);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolBar.setNavigationIcon(R.mipmap.l_arrow);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VersionQueryActivity.this.finish();
            }
        });
        toolBar.setTitle("版本查询");//设置主标题
        toolBar.setTitleTextColor(getResources().getColor(R.color.color_f5f5f5));
        toolBar.setTitleTextAppearance(this, R.style.ActionBarTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @OnClick(R.id.btn1)
    public void onClick() {
        //查询版本号
        TCPOutHelper.getInstance(this).msgGetVersion(BaseApplication.CurrentEid);
    }
}
