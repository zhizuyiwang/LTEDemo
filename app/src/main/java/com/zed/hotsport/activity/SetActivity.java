package com.zed.hotsport.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zed.hotsport.R;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    @Bind(R.id.et_lt_NCC)
    EditText etLtNCC;
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
    @Bind(R.id.cb_dx)
    CheckBox cbDx;
    @Bind(R.id.tv_dx_operator)
    TextView tvDxOperator;
    @Bind(R.id.et_dx_operator)
    AppCompatSpinner etDxOperator;
    @Bind(R.id.tv_dx_frequency)
    TextView tvDxFrequency;
    @Bind(R.id.et_tx_frequency)
    EditText etTxFrequency;
    @Bind(R.id.tv_tx_scramble)
    TextView tvTxScramble;
    @Bind(R.id.et_tx_scramble)
    EditText etTxScramble;
    @Bind(R.id.tv_tx_descend)
    TextView tvTxDescend;
    @Bind(R.id.et_tx_descend)
    AppCompatSpinner etTxDescend;
    @Bind(R.id.tv_tx_tac)
    TextView tvTxTac;
    @Bind(R.id.et_tx_tac)
    EditText etTxTac;
    @Bind(R.id.tv_tx_tac_period)
    TextView tvTxTacPeriod;
    @Bind(R.id.et_tx_tac_period)
    EditText etTxTacPeriod;
    @Bind(R.id.tv_tx_CDX)
    TextView tvTxCDX;
    @Bind(R.id.et_tx_CDX)
    AppCompatSpinner etTxCDX;
    @Bind(R.id.tv_tx_band)
    TextView tvTxBand;
    @Bind(R.id.et_tx_band)
    EditText etTxBand;
    @Bind(R.id.tv_tx_frequency1)
    TextView tvTxFrequency1;
    @Bind(R.id.et_tx_frequency1)
    EditText etTxFrequency1;
    @Bind(R.id.tv_tx_dianping)
    TextView tvTxDianping;
    @Bind(R.id.et_tx_dianping)
    EditText etTxDianping;
    @Bind(R.id.tx_container)
    LinearLayout txContainer;
    @Bind(R.id.btnSendCommand)
    Button btnSendCommand;
    @Bind(R.id.activity_black)
    LinearLayout activityBlack;
    private boolean flag = false;//用来显示高级配置的标志

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        initToolBar();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {


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
                        txContainer.setVisibility(View.GONE);

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
                        txContainer.setVisibility(View.VISIBLE);
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendCommand:

                break;
        }
    }
}
