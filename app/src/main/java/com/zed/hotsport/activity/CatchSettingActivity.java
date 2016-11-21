package com.zed.hotsport.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zed.hotsport.R;
import com.zed.hotsport.TCP.TCPOutHelper;
import com.zed.hotsport.base.BaseApplication;
import com.zed.hotsport.base.Constant;
import com.zed.hotsport.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CatchSettingActivity extends AppCompatActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.activity_catch_setting)
    LinearLayout activityCatchSetting;
    @Bind(R.id.all_tv_catch)
    TextView allTvCatch;
    @Bind(R.id.all_cb_catch)
    CheckBox allCbCatch;
    @Bind(R.id.dx_tv_catch)
    TextView dxTvCatch;
    @Bind(R.id.dx_cb_catch)
    CheckBox dxCbCatch;
    @Bind(R.id.lt_tv_catch)
    TextView ltTvCatch;
    @Bind(R.id.lt_cb_catch)
    CheckBox ltCbCatch;
    @Bind(R.id.mobile_tv_catch)
    TextView mobileTvCatch;
    @Bind(R.id.mobile_cb_catch)
    CheckBox mobileCbCatch;
    @Bind(R.id.mobile1_tv_catch)
    TextView mobile1TvCatch;
    @Bind(R.id.mobile1_cb_catch)
    CheckBox mobile1CbCatch;
    private SharedPreferences sp_catch_setting;
    private boolean allCB;//侦码是否选中
    private boolean ltCB;//联通上报是否选中
    private boolean dxCB;//电信上报是否选中
    private boolean ydCB;//移动上报是否选中
    private boolean yd1CB;//移动1上报是否选中


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch_setting);
        ButterKnife.bind(this);
        initToolBar();
        initData();
        intViewData();
    }

    /**
     * 初始化控件数据
     */
    private void intViewData() {
        allCB = sp_catch_setting.getBoolean(Constant.ALLCATCH, true);
        ltCB = sp_catch_setting.getBoolean(Constant.LT_CATCH, true);
        dxCB = sp_catch_setting.getBoolean(Constant.DX_CATCH, true);
        ydCB = sp_catch_setting.getBoolean(Constant.YD_CATCH, true);
        yd1CB = sp_catch_setting.getBoolean(Constant.YD1_CATCH, true);
        if (!allCB) {
            allCbCatch.setChecked(false);
            allTvCatch.setText("总侦码开关已关闭");
            allTvCatch.setTextColor(Color.RED);
        } else {
            allCbCatch.setChecked(true);
            allTvCatch.setText("总侦码开关已打开");
            allTvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
        }
        if (!ltCB) {
            ltCbCatch.setChecked(false);
            ltTvCatch.setText("联通上报开关已关闭");
            ltTvCatch.setTextColor(Color.RED);
        } else {
            ltCbCatch.setChecked(true);
            ltTvCatch.setText("联通上报开关已打开");
            ltTvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
        }
        if (!dxCB) {
            dxCbCatch.setChecked(false);
            dxTvCatch.setText("电信上报开关已关闭");
            dxTvCatch.setTextColor(Color.RED);
        } else {
            dxCbCatch.setChecked(true);
            dxTvCatch.setText("电信上报开关已打开");
            dxTvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
        }
        if (!ydCB) {
            mobileCbCatch.setChecked(false);
            mobileTvCatch.setText("移动上报开关已关闭");
            mobileTvCatch.setTextColor(Color.RED);
        } else {
            mobileCbCatch.setChecked(true);
            mobileTvCatch.setText("移动上报开关已打开");
            mobileTvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
        }

        if (!yd1CB) {
            mobile1CbCatch.setChecked(false);
            mobile1TvCatch.setText("移动1上报开关已关闭");
            mobile1TvCatch.setTextColor(Color.RED);

        } else {
            mobile1CbCatch.setChecked(true);
            mobile1TvCatch.setText("移动1上报开关已打开");
            mobile1TvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
        }
        allCbCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseApplication.connect) {
                    SystemClock.sleep(200);
                    if (allCB) {
                        allCbCatch.setChecked(false);
                        allTvCatch.setText("总侦码开关已关闭");
                        allTvCatch.setTextColor(Color.RED);
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetAllCatch(BaseApplication.CurrentEid, (byte) 0);
                    } else {
                        allCbCatch.setChecked(true);
                        allTvCatch.setText("总侦码开关已打开");
                        allTvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetAllCatch(BaseApplication.CurrentEid, (byte) 1);
                    }
                    allCB = !allCB;
                    sp_catch_setting.edit().putBoolean(Constant.ALLCATCH, allCB).commit();
                } else {
                    ToastUtils.showToast(CatchSettingActivity.this, "设备未连接，设置失败!", 0);
                }
            }
        });
        ltCbCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseApplication.connect) {
                    SystemClock.sleep(200);
                    if (ltCB) {
                        ltCbCatch.setChecked(false);
                        ltTvCatch.setText("联通上报开关已关闭");
                        ltTvCatch.setTextColor(Color.RED);
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetPA(BaseApplication.CurrentEid,(byte)0,(byte)0);
                    } else {
                        ltCbCatch.setChecked(true);
                        ltTvCatch.setText("联通上报开关已打开");
                        ltTvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetPA(BaseApplication.CurrentEid,(byte)0,(byte)1);
                    }
                    ltCB = !ltCB;
                    sp_catch_setting.edit().putBoolean(Constant.LT_CATCH, ltCB).commit();
                } else {
                    ToastUtils.showToast(CatchSettingActivity.this, "设备未连接，设置失败!", 0);
                }
            }
        });
        dxCbCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseApplication.connect) {
                    SystemClock.sleep(200);
                    if (dxCB) {
                        dxCbCatch.setChecked(false);
                        dxTvCatch.setText("电信上报开关已关闭");
                        dxTvCatch.setTextColor(Color.RED);
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetPA(BaseApplication.CurrentEid,(byte)1,(byte)0);
                    } else {
                        dxCbCatch.setChecked(true);
                        dxTvCatch.setText("电信上报开关已打开");
                        dxTvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetPA(BaseApplication.CurrentEid,(byte)1,(byte)1);
                    }
                    dxCB = !dxCB;
                    sp_catch_setting.edit().putBoolean(Constant.DX_CATCH, dxCB).commit();
                } else {
                    ToastUtils.showToast(CatchSettingActivity.this, "设备未连接，设置失败!", 0);
                }

            }
        });

        mobileCbCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseApplication.connect) {
                    SystemClock.sleep(200);
                    if (ydCB) {
                        mobileCbCatch.setChecked(false);
                        mobileTvCatch.setText("移动上报开关已关闭");
                        mobileTvCatch.setTextColor(Color.RED);
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetPA(BaseApplication.CurrentEid,(byte)2,(byte)0);
                    } else {
                        mobileCbCatch.setChecked(true);
                        mobileTvCatch.setText("移动上报开关已打开");
                        mobileTvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetPA(BaseApplication.CurrentEid,(byte)2,(byte)1);
                    }
                    ydCB = !ydCB;
                    sp_catch_setting.edit().putBoolean(Constant.YD_CATCH, ydCB).commit();
                } else {
                    ToastUtils.showToast(CatchSettingActivity.this, "设备未连接，设置失败!", 0);
                }

            }
        });
        mobile1CbCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseApplication.connect) {
                    SystemClock.sleep(200);
                    if (yd1CB) {
                        mobile1CbCatch.setChecked(false);
                        mobile1TvCatch.setText("移动1上报开关已关闭");
                        mobile1TvCatch.setTextColor(Color.RED);
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetPA(BaseApplication.CurrentEid,(byte)3,(byte)0);
                    } else {
                        mobile1CbCatch.setChecked(true);
                        mobile1TvCatch.setText("移动1上报开关已打开");
                        mobile1TvCatch.setTextColor(getResources().getColor(R.color.color_0176da));
                        TCPOutHelper.getInstance(CatchSettingActivity.this).msgSetPA(BaseApplication.CurrentEid,(byte)3,(byte)1);
                    }
                    yd1CB = !yd1CB;
                    sp_catch_setting.edit().putBoolean(Constant.YD1_CATCH, yd1CB).commit();
                } else {
                    ToastUtils.showToast(CatchSettingActivity.this, "设备未连接，设置失败!", 0);
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        sp_catch_setting = getSharedPreferences(Constant.CATCH_SETTING, 0);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolBar.setNavigationIcon(R.mipmap.l_arrow);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatchSettingActivity.this.finish();
            }
        });
        toolBar.setTitle("开关侦码设置");//设置主标题
        toolBar.setTitleTextColor(getResources().getColor(R.color.color_f5f5f5));
        toolBar.setTitleTextAppearance(this, R.style.ActionBarTitle);
    }
}
