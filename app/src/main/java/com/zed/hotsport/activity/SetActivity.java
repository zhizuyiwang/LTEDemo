package com.zed.hotsport.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zed.hotsport.R;
import com.zed.hotsport.TCP.TCPAction;
import com.zed.hotsport.TCP.TCPOutHelper;
import com.zed.hotsport.base.BaseApplication;
import com.zed.hotsport.base.Constant;
import com.zed.hotsport.bean.CellBean;
import com.zed.hotsport.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.cbSelectAll)
    CheckBox cbSelectAll;
    @Bind(R.id.cb_mobile)
    CheckBox cbMobile;
    @Bind(R.id.tv_mobile_operator)
    TextView tvMobileOperator;
    @Bind(R.id.et_mobile_operator_spinner)
    AppCompatSpinner etMobileOperatorSpinner;
    @Bind(R.id.tv_mobile_frequency)
    TextView tvMobileFrequency;
    @Bind(R.id.et_mobile_frequency)
    EditText etMobileFrequency;
    @Bind(R.id.tv_mobile_scramble)
    TextView tvMobileScramble;
    @Bind(R.id.et_mobile_scramble)
    EditText etMobileScramble;
    @Bind(R.id.tv_mobile_descend)
    TextView tvMobileDescend;
    @Bind(R.id.et_mobile_descend)
    AppCompatSpinner etMobileDescend;
    @Bind(R.id.tv_mobile_tac)
    TextView tvMobileTac;
    @Bind(R.id.et_mobile_tac)
    EditText etMobileTac;
    @Bind(R.id.tv_mobile_tac_period)
    TextView tvMobileTacPeriod;
    @Bind(R.id.et_mobile_tac_period)
    EditText etMobileTacPeriod;
    @Bind(R.id.tv_mobile_CDX)
    TextView tvMobileCDX;
    @Bind(R.id.et_mobile_CDX)
    AppCompatSpinner etMobileCDX;
    @Bind(R.id.tv_mobile_band)
    TextView tvMobileBand;
    @Bind(R.id.et_mobile_band)
    EditText etMobileBand;
    @Bind(R.id.tv_mobile_frequency1)
    TextView tvMobileFrequency1;
    @Bind(R.id.et_mobile_frequency1)
    EditText etMobileFrequency1;
    @Bind(R.id.tv_mobile_dianping)
    TextView tvMobileDianping;
    @Bind(R.id.et_mobile_dianping)
    EditText etMobileDianping;
    @Bind(R.id.mobile_container)
    LinearLayout mobileContainer;
    //移动1
    @Bind(R.id.cb_mobile1)
    CheckBox cbMobile1;
    @Bind(R.id.tv_mobile1_operator)
    TextView tvMobile1Operator;
    @Bind(R.id.et_mobile1_operator)
    AppCompatSpinner etMobile1Operator;
    @Bind(R.id.tv_mobile1_frequency)
    TextView tvMobile1Frequency;
    @Bind(R.id.et_mobile1_frequency)
    EditText etMobile1Frequency;
    @Bind(R.id.tv_mobile1_scramble)
    TextView tvMobile1Scramble;
    @Bind(R.id.et_mobile1_scramble)
    EditText etMobile1Scramble;
    @Bind(R.id.tv_mobile1_descend)
    TextView tvMobile1Descend;
    @Bind(R.id.et_mobile1_descend)
    AppCompatSpinner etMobile1Descend;
    @Bind(R.id.tv_mobile1_tac)
    TextView tvMobile1Tac;
    @Bind(R.id.et_mobile1_tac)
    EditText etMobile1Tac;
    @Bind(R.id.tv_mobile1_tac_period)
    TextView tvMobile1TacPeriod;
    @Bind(R.id.et_mobile1_tac_period)
    EditText etMobile1TacPeriod;
    @Bind(R.id.tv_mobile1_CDX)
    TextView tvMobile1CDX;
    @Bind(R.id.et_mobile1_CDX)
    AppCompatSpinner etMobile1CDX;
    @Bind(R.id.tv_mobile1_band)
    TextView tvMobile1Band;
    @Bind(R.id.et_mobile1_band)
    EditText etMobile1Band;
    @Bind(R.id.tv_mobile1_frequency1)
    TextView tvMobile1Frequency1;
    @Bind(R.id.et_mobile1_frequency1)
    EditText etMobile1Frequency1;
    @Bind(R.id.tv_mobile1_dianping)
    TextView tvMobile1Dianping;
    @Bind(R.id.et_mobile1_dianping)
    EditText etMobile1Dianping;
    @Bind(R.id.mobile1_container)
    LinearLayout mobile1Container;
    //联通
    @Bind(R.id.cb_lt)
    CheckBox cbLt;
    @Bind(R.id.tv_lt_operator)
    TextView tvLtOperator;
    @Bind(R.id.et_lt_operator)
    AppCompatSpinner etLtOperator;
    @Bind(R.id.tv_lt_frequency)
    TextView tvLtFrequency;
    @Bind(R.id.et_lt_frequency)
    EditText etLtFrequency;
    @Bind(R.id.tv_lt_scramble)
    TextView tvLtScramble;
    @Bind(R.id.et_lt_scramble)
    EditText etLtScramble;
    @Bind(R.id.tv_lt_descend)
    TextView tvLtDescend;
    @Bind(R.id.et_lt_descend)
    AppCompatSpinner etLtDescend;
    @Bind(R.id.tv_lt_tac)
    TextView tvLtTac;
    @Bind(R.id.et_lt_tac)
    EditText etLtTac;
    @Bind(R.id.tv_lt_tac_period)
    TextView tvLtTacPeriod;
    @Bind(R.id.et_lt_tac_period)
    EditText etLtTacPeriod;
    @Bind(R.id.tv_lt_CDX)
    TextView tvLtCDX;
    @Bind(R.id.et_lt_CDX)
    AppCompatSpinner etLtCDX;
    @Bind(R.id.tv_lt_band)
    TextView tvLtBand;
    @Bind(R.id.et_lt_band)
    EditText etLtBand;
    @Bind(R.id.tv_lt_frequency1)
    TextView tvLtFrequency1;
    @Bind(R.id.et_lt_frequency1)
    EditText etLtFrequency1;
    @Bind(R.id.tv_lt_dianping)
    TextView tvLtDianping;
    @Bind(R.id.et_lt_dianping)
    EditText etLtDianping;
    @Bind(R.id.lt_container)
    LinearLayout ltContainer;
    //电信
    @Bind(R.id.cb_dx)
    CheckBox cbDx;
    @Bind(R.id.tv_dx_operator)
    TextView tvDxOperator;
    @Bind(R.id.et_dx_operator)
    AppCompatSpinner etDxOperator;
    @Bind(R.id.tv_dx_frequency)
    TextView tvDxFrequency;
    @Bind(R.id.et_dx_frequency)
    EditText etDxFrequency;
    @Bind(R.id.tv_dx_scramble)
    TextView tvDxScramble;
    @Bind(R.id.et_dx_scramble)
    EditText etDxScramble;
    @Bind(R.id.tv_dx_descend)
    TextView tvDxDescend;
    @Bind(R.id.et_dx_descend)
    AppCompatSpinner etDxDescend;
    @Bind(R.id.tv_dx_tac)
    TextView tvDxTac;
    @Bind(R.id.et_dx_tac)
    EditText etDxTac;
    @Bind(R.id.tv_dx_tac_period)
    TextView tvDxTacPeriod;
    @Bind(R.id.et_dx_tac_period)
    EditText etDxTacPeriod;
    @Bind(R.id.tv_dx_CDX)
    TextView tvDxCDX;
    @Bind(R.id.et_dx_CDX)
    AppCompatSpinner etDxCDX;
    @Bind(R.id.tv_dx_band)
    TextView tvDxBand;
    @Bind(R.id.et_dx_band)
    EditText etDxBand;
    @Bind(R.id.tv_dx_frequency1)
    TextView tvDxFrequency1;
    @Bind(R.id.et_dx_frequency1)
    EditText etDxFrequency1;
    @Bind(R.id.tv_dx_dianping)
    TextView tvDxDianping;
    @Bind(R.id.et_dx_dianping)
    EditText etDxDianping;
    @Bind(R.id.dx_container)
    LinearLayout DxContainer;
    @Bind(R.id.btnSendCommand)
    Button btnSendCommand;
    @Bind(R.id.activity_black)
    LinearLayout activityBlack;
    private boolean flag = false;//用来显示高级配置的标志
    private SharedPreferences sp_setting;//保存参数的sp

    //中国联通
    private String lt_frequency;//频点
    private String lt_scramble;//扰码
    private int lt_descend;//功率衰减下标，也是衰减值
    private String lt_tac;//TAC
    private String lt_tac_period;//TAC变更周期
    private int lt_CDX;//重定向下标
    private String lt_band;//频段
    private String lt_frequency1;//频点
    private String lt_dianping;//最小接入电平
    //中国电信
    private String dx_frequency;//频点
    private String dx_scramble;//扰码
    private int dx_descend;//功率衰减下标，也是衰减值
    private String dx_tac;//TAC
    private String dx_tac_period;//TAC变更周期
    private int dx_CDX;//重定向下标
    private String dx_band;//频段
    private String dx_frequency1;//频点
    private String dx_dianping;//最小接入电平
    //中国移动
    private String yd_frequency;//频点
    private String yd_scramble;//扰码
    private int yd_descend;//功率衰减下标,也是衰减值
    private String yd_tac;//TAC
    private String yd_tac_period;//TAC变更周期
    private int yd_CDX;//重定向下标
    private String yd_band;//频段
    private String yd_frequency1;//频点
    private String yd_dianping;//最小接入电平
    //中国移动1
    private String yd1_frequency;//频点
    private String yd1_scramble;//扰码
    private int yd1_descend;//功率衰减下标，也是衰减值
    private String yd1_tac;//TAC
    private String yd1_tac_period;//TAC变更周期
    private int yd1_CDX;//重定向下标
    private String yd1_band;//频段
    private String yd1_frequency1;//频点
    private String yd1_dianping;//最小接入电平
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(TCPAction.ACTION_PARA)) {
                initView();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        initToolBar();
        initReceiver();
        initData();
        initView();
        initListener();
    }

    /**
     * 监听Spinner的条目点击事件
     */
    private void initListener() {
        etLtDescend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //当选中Spinner的某一天数据时.会执行
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lt_descend = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etDxDescend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dx_descend = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etMobileDescend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yd_descend = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etMobile1Descend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yd1_descend = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TCPAction.ACTION_PARA);
        registerReceiver(receiver, filter);
    }

    /**
     * 初始化控件数据
     */
    private void initView() {
        cbSelectAll.setChecked(sp_setting.getBoolean(Constant.SELECTALL_CB, true));
        cbLt.setChecked(sp_setting.getBoolean(Constant.LT_SETTING_CB, true));
        cbDx.setChecked(sp_setting.getBoolean(Constant.DX_SETTING_CB, true));
        cbMobile.setChecked(sp_setting.getBoolean(Constant.YD_SETTING_CB, true));
        cbMobile1.setChecked(sp_setting.getBoolean(Constant.YD1_SETTING_CB, true));
        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbLt.setChecked(isChecked);
                cbDx.setChecked(isChecked);
                cbMobile.setChecked(isChecked);
                cbMobile1.setChecked(isChecked);
            }
        });
        //联通的配置参数
        etLtFrequency.setText(sp_setting.getString(Constant.LT_FREQUENCY, 0 + ""));
        etLtScramble.setText(sp_setting.getString(Constant.LT_SCRAMBLE, 0 + ""));
        etLtDescend.setSelection(sp_setting.getInt(Constant.LT_DESCEND, 0));
        etLtTac.setText(sp_setting.getString(Constant.LT_TAC, 0 + ""));
        etLtTacPeriod.setText(sp_setting.getString(Constant.LT_TAC_PERIOD, 0 + ""));
        etLtCDX.setSelection(sp_setting.getInt(Constant.LT_CDX, 0));
        etLtBand.setText(sp_setting.getString(Constant.LT_BAND, 0 + ""));
        etLtFrequency1.setText(sp_setting.getString(Constant.LT_FREQUENCY1, 0 + ""));
        etLtDianping.setText(sp_setting.getString(Constant.LT_DIANPING, 0 + ""));

        //电信的配置参数
        etDxFrequency.setText(sp_setting.getString(Constant.DX_FREQUENCY, 0 + ""));
        etDxScramble.setText(sp_setting.getString(Constant.DX_SCRAMBLE, 0 + ""));
        etDxDescend.setSelection(sp_setting.getInt(Constant.DX_DESCEND, 0));
        etDxTac.setText(sp_setting.getString(Constant.DX_TAC, 0 + ""));
        etDxTacPeriod.setText(sp_setting.getString(Constant.DX_TAC_PERIOD, 0 + ""));
        etDxCDX.setSelection(sp_setting.getInt(Constant.DX_CDX, 0));
        etDxBand.setText(sp_setting.getString(Constant.DX_BAND, 0 + ""));
        etDxFrequency1.setText(sp_setting.getString(Constant.DX_FREQUENCY1, 0 + ""));
        etDxDianping.setText(sp_setting.getString(Constant.DX_DIANPING, 0 + ""));

        //移动的配置参数
        etMobileFrequency.setText(sp_setting.getString(Constant.MOBILE_FREQUENCY, 0 + ""));
        etMobileScramble.setText(sp_setting.getString(Constant.MOBILE_SCRAMBLE, 0 + ""));
        etMobileDescend.setSelection(sp_setting.getInt(Constant.MOBILE_DESCEND, 0));
        etMobileTac.setText(sp_setting.getString(Constant.MOBILE_TAC, 0 + ""));
        etMobileTacPeriod.setText(sp_setting.getString(Constant.MOBILE_TAC_PERIOD, 0 + ""));
        etMobileCDX.setSelection(sp_setting.getInt(Constant.MOBILE_CDX, 0));
        etMobileBand.setText(sp_setting.getString(Constant.MOBILE_BAND, 0 + ""));
        etMobileFrequency1.setText(sp_setting.getString(Constant.MOBILE_FREQUENCY1, 0 + ""));
        etMobileDianping.setText(sp_setting.getString(Constant.MOBILE_DIANPING, 0 + ""));

        //移动1的配置参数
        etMobile1Frequency.setText(sp_setting.getString(Constant.MOBILE1_FREQUENCY, 0 + ""));
        etMobile1Scramble.setText(sp_setting.getString(Constant.MOBILE1_SCRAMBLE, 0 + ""));
        etMobile1Descend.setSelection(sp_setting.getInt(Constant.MOBILE1_DESCEND, 0));
        etMobile1Tac.setText(sp_setting.getString(Constant.MOBILE1_TAC, 0 + ""));
        etMobile1TacPeriod.setText(sp_setting.getString(Constant.MOBILE1_TAC_PERIOD, 0 + ""));
        etMobile1CDX.setSelection(sp_setting.getInt(Constant.MOBILE1_CDX, 0));
        etMobile1Band.setText(sp_setting.getString(Constant.MOBILE1_BAND, 0 + ""));
        etMobile1Frequency1.setText(sp_setting.getString(Constant.MOBILE1_FREQUENCY1, 0 + ""));
        etMobile1Dianping.setText(sp_setting.getString(Constant.MOBILE1_DIANPING, 0 + ""));
    }

    /**
     * 初始化数据
     */
    private void initData() {
        sp_setting = getSharedPreferences(Constant.DEVICE_PARA, 0);
        //查询设备参数
        TCPOutHelper.getInstance(SetActivity.this).msgGetCellPara(BaseApplication.CurrentEid);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolBar.setNavigationIcon(R.mipmap.l_arrow);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
        toolBar.setTitle("设置界面");//设置主标题
        toolBar.setTitleTextColor(getResources().getColor(R.color.color_f5f5f5));
        toolBar.setTitleTextAppearance(this, R.style.ActionBarTitle);

        toolBar.inflateMenu(R.menu.base_setting_menu);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuId = item.getItemId();
                switch (menuId) {
                    case R.id.action_normal_setting://基础设置
                        flag = false;
                        mobileContainer.setVisibility(View.GONE);
                        mobile1Container.setVisibility(View.GONE);
                        ltContainer.setVisibility(View.GONE);
                        DxContainer.setVisibility(View.GONE);
                       /* LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(30, 60, 30, 0);
                        btnSendCommand.setLayoutParams(lp);*/

                        break;
                    case R.id.action_setting://高级设置
                        flag = true;
                        mobileContainer.setVisibility(View.VISIBLE);
                        mobile1Container.setVisibility(View.VISIBLE);
                        ltContainer.setVisibility(View.VISIBLE);
                        DxContainer.setVisibility(View.VISIBLE);
                        break;
                    case R.id.action_get_para:
                        TCPOutHelper.getInstance(SetActivity.this).msgGetCellPara(BaseApplication.CurrentEid);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    /**
     * 退出界面的提示
     */
    private void showExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
        builder.setMessage("是否确定退出？");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SetActivity.this.finish();
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

    @OnClick(R.id.btnSendCommand)
    public void onClick(View v) {

        Log.e("TAG", "下发配置");
        getInfo();
        if (BaseApplication.connect) {
            saveSetting();
            if (cbLt.isChecked()) {
                Log.e("TAG", "下发联通配置");
                CellBean ltCell = new CellBean();
                ltCell.setFrequency(lt_frequency);
                ltCell.setScramble(lt_scramble);
                ltCell.setPower_down(lt_descend );
                ltCell.setTac(lt_tac);
                ltCell.setLac_period(lt_tac_period);
                ltCell.setBand(lt_band);
                ltCell.setFrequency1(lt_frequency1);
                ltCell.setDianPing(lt_dianping);
                //下发联通配置
                TCPOutHelper.getInstance(SetActivity.this).msgSetCellPara(BaseApplication.CurrentEid, ltCell, 0, 1);
            }
            SystemClock.sleep(100);
            if (cbDx.isChecked()) {
                Log.e("TAG", "下发电信配置");
                CellBean dxCell = new CellBean();
                dxCell.setFrequency(dx_frequency);
                dxCell.setScramble(dx_scramble);
                dxCell.setPower_down(dx_descend);
                dxCell.setTac(dx_tac);
                dxCell.setLac_period(dx_tac_period);
                dxCell.setBand(dx_band);
                dxCell.setFrequency1(dx_frequency1);
                dxCell.setDianPing(dx_dianping);
                //下发电信配置
                TCPOutHelper.getInstance(SetActivity.this).msgSetCellPara(BaseApplication.CurrentEid, dxCell, 1, 11);
            }
            SystemClock.sleep(100);
            if (cbMobile.isChecked()) {
                Log.e("TAG", "下发移动配置");
                CellBean ydCell = new CellBean();
                ydCell.setFrequency(yd_frequency);
                ydCell.setScramble(yd_scramble);
                ydCell.setPower_down(yd_descend);

                ydCell.setTac(yd_tac);
                ydCell.setLac_period(yd_tac_period);
                ydCell.setBand(yd_band);
                ydCell.setFrequency1(yd_frequency1);
                ydCell.setDianPing(yd_dianping);
                //下发移动配置
                TCPOutHelper.getInstance(SetActivity.this).msgSetCellPara(BaseApplication.CurrentEid, ydCell, 2, 0);
            }
            SystemClock.sleep(100);
            if (cbMobile1.isChecked()) {
                Log.e("TAG", "下发移动1通配置");
                CellBean yd1Cell = new CellBean();
                yd1Cell.setFrequency(yd1_frequency);
                yd1Cell.setScramble(yd1_scramble);
                yd1Cell.setPower_down(yd1_descend);
                yd1Cell.setTac(yd1_tac);
                yd1Cell.setLac_period(yd1_tac_period);
                yd1Cell.setBand(yd1_band);
                yd1Cell.setFrequency1(yd1_frequency1);
                yd1Cell.setDianPing(yd1_dianping);
                //下发移动1配置
                TCPOutHelper.getInstance(SetActivity.this).msgSetCellPara(BaseApplication.CurrentEid, yd1Cell, 3, 0);
            }
        } else {
            ToastUtils.showToast(SetActivity.this, "设备未连接，下发参数失败", 0);
        }

    }

    /**
     * 设备若连接，并且点击了下发命令按钮，则保存设置,这里只保存选择状态，因为其他信息会在进入配置界面时，根据查询的参数值回显参数值
     */
    private void saveSetting() {
        sp_setting.edit().putBoolean(Constant.SELECTALL_CB,cbSelectAll.isChecked()).commit();
        sp_setting.edit().putBoolean(Constant.LT_SETTING_CB,cbLt.isChecked()).commit();
        sp_setting.edit().putBoolean(Constant.DX_SETTING_CB,cbDx.isChecked()).commit();
        sp_setting.edit().putBoolean(Constant.YD_SETTING_CB,cbMobile.isChecked()).commit();
        sp_setting.edit().putBoolean(Constant.YD1_SETTING_CB,cbMobile1.isChecked()).commit();
    }

    /**
     * 得到要配置的参数
     */
    private void getInfo() {
        //联通
        lt_frequency = etLtFrequency.getText().toString().trim();
        lt_scramble = etLtScramble.getText().toString().trim();
        lt_tac = etLtTac.getText().toString().trim();
        lt_tac_period = etLtTacPeriod.getText().toString().trim();
        lt_band = etLtBand.getText().toString().trim();
        lt_frequency1 = etLtFrequency1.getText().toString().trim();
        lt_dianping = etLtDianping.getText().toString().trim();
        //电信
        dx_frequency = etDxFrequency.getText().toString().trim();
        dx_scramble = etDxScramble.getText().toString().trim();
        dx_tac = etDxTac.getText().toString().trim();
        dx_tac_period = etDxTacPeriod.getText().toString().trim();
        dx_band = etDxBand.getText().toString().trim();
        dx_frequency1 = etDxFrequency1.getText().toString().trim();
        dx_dianping = etDxDianping.getText().toString().trim();
        //移动
        yd_frequency = etMobileFrequency.getText().toString().trim();
        yd_scramble = etMobileScramble.getText().toString().trim();
        yd_tac = etMobileTac.getText().toString().trim();
        yd_tac_period = etMobileTacPeriod.getText().toString().trim();
        yd_band = etMobileBand.getText().toString().trim();
        yd_frequency1 = etMobileFrequency1.getText().toString().trim();
        yd_dianping = etMobileDianping.getText().toString().trim();
        //移动1
        yd1_frequency = etMobile1Frequency.getText().toString().trim();
        yd1_scramble = etMobile1Scramble.getText().toString().trim();
        yd1_tac = etMobile1Tac.getText().toString().trim();
        yd1_tac_period = etMobile1TacPeriod.getText().toString().trim();
        yd1_band = etMobile1Band.getText().toString().trim();
        yd1_frequency1 = etMobile1Frequency1.getText().toString().trim();
        yd1_dianping = etMobile1Dianping.getText().toString().trim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
