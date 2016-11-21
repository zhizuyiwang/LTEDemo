package com.zed.hotsport.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zed.hotsport.R;
import com.zed.hotsport.TCP.TCPAction;
import com.zed.hotsport.base.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DeviceStatusActivity extends AppCompatActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.activity_device_status)
    LinearLayout activityDeviceStatus;
    @Bind(R.id.tv_cpu_status)
    TextView tvCpuStatus;
    @Bind(R.id.tv_memory_status)
    TextView tvMemoryStatus;
    @Bind(R.id.tv_wendu_status)
    TextView tvWenduStatus;
    @Bind(R.id.tv_catch_status)
    TextView tvCatchStatus;
    @Bind(R.id.lt_cell_status)
    TextView ltCellStatus;
    @Bind(R.id.lt_pa_status)
    TextView ltPaStatus;
    @Bind(R.id.lt_pa_down)
    TextView ltPaDown;
    @Bind(R.id.lt_pa_power)
    TextView ltPaPower;
    @Bind(R.id.dx_cell_status)
    TextView dxCellStatus;
    @Bind(R.id.dx_pa_status)
    TextView dxPaStatus;
    @Bind(R.id.dx_pa_down)
    TextView dxPaDown;
    @Bind(R.id.dx_pa_power)
    TextView dxPaPower;
    @Bind(R.id.mobile_cell_status)
    TextView mobileCellStatus;
    @Bind(R.id.mobile_pa_status)
    TextView mobilePaStatus;
    @Bind(R.id.mobile_pa_down)
    TextView mobilePaDown;
    @Bind(R.id.mobile_pa_power)
    TextView mobilePaPower;
    @Bind(R.id.mobile1_cell_status)
    TextView mobile1CellStatus;
    @Bind(R.id.mobile1_pa_status)
    TextView mobile1PaStatus;
    @Bind(R.id.mobile1_pa_down)
    TextView mobile1PaDown;
    @Bind(R.id.mobile1_pa_power)
    TextView mobile1PaPower;
    private SharedPreferences sp_device_status;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(TCPAction.ACTTON_STATUS)){//状态上报广播
                initViewData();//更改控件数据
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_status);
        ButterKnife.bind(this);
        initToolBar();
        initData();
        initReceiver();
        initViewData();
    }

    /**
     * 注册广播
     */
    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TCPAction.ACTTON_STATUS);
        registerReceiver(receiver, filter);
    }

    /**
     * 初始化各个控件的数据
     */
    private void initViewData() {
        tvCpuStatus.setText(sp_device_status.getString(Constant.CPU_STATUS,""));
        tvMemoryStatus.setText(sp_device_status.getString(Constant.MEMORY_STATUS,""));
        tvWenduStatus.setText(sp_device_status.getString(Constant.WENDU,""));
        tvCatchStatus.setText(sp_device_status.getString(Constant.CATCH_STATUS,""));

        ltCellStatus.setText(sp_device_status.getString(Constant.LT_CELL_STATUS,""));
        ltPaStatus.setText(sp_device_status.getString(Constant.LT_PA_STATUS,""));
        ltPaDown.setText(sp_device_status.getString(Constant.LT_PA_DOWN,""));
        ltPaPower.setText(sp_device_status.getString(Constant.LT_PA_POWER,""));

        dxCellStatus.setText(sp_device_status.getString(Constant.DX_CELL_STATUS,""));
        dxPaStatus.setText(sp_device_status.getString(Constant.DX_PA_STATUS,""));
        dxPaDown.setText(sp_device_status.getString(Constant.DX_PA_DOWN,""));
        dxPaPower.setText(sp_device_status.getString(Constant.DX_PA_POWER,""));

        mobileCellStatus.setText(sp_device_status.getString(Constant.MOBILE_CELL_STATUS,""));
        mobilePaStatus.setText(sp_device_status.getString(Constant.MOBILE_PA_STATUS,""));
        mobilePaDown.setText(sp_device_status.getString(Constant.MOBILE_PA_DOWN,""));
        mobilePaPower.setText(sp_device_status.getString(Constant.MOBILE_PA_POWER,""));

        mobile1CellStatus.setText(sp_device_status.getString(Constant.MOBILE1_CELL_STATUS,""));
        mobile1PaStatus.setText(sp_device_status.getString(Constant.MOBILE1_PA_STATUS,""));
        mobile1PaDown.setText(sp_device_status.getString(Constant.MOBILE1_PA_DOWN,""));
        mobile1PaPower.setText(sp_device_status.getString(Constant.MOBILE1_PA_POWER,""));
    }

    /**
     * 初始化数据
     */
    private void initData() {
        sp_device_status = getSharedPreferences(Constant.DEVICE_STATUS,0);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolBar.setNavigationIcon(R.mipmap.l_arrow);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceStatusActivity.this.finish();
            }
        });
        toolBar.setTitle("设备状态");//设置主标题
        toolBar.setTitleTextColor(getResources().getColor(R.color.color_f5f5f5));
        toolBar.setTitleTextAppearance(this, R.style.ActionBarTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
