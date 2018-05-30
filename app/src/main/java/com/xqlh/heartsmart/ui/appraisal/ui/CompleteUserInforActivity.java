package com.xqlh.heartsmart.ui.appraisal.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vondear.rxtools.view.dialog.RxDialogWheelYearMonthDay;
import com.xqlh.heartsmart.Event.EventUpdateUserInfor;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityUpdateUserInfor;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CompleteUserInforActivity extends BaseActivity {

    @BindView(R.id.update_titleBar)
    TitleBar update_titleBar;

    @BindView(R.id.et_name)
    EditText et_name;

    @BindView(R.id.tv_gender)
    TextView tv_gender;

    @BindView(R.id.tv_birth)
    TextView tv_birth;

    @BindView(R.id.bt_update)
    Button bt_update;

    private String[] sexArry = new String[]{"女", "男"};// 性别选择
    private RxDialogWheelYearMonthDay mRxDialogWheelYearMonthDay;//年月日

    SharedPreferencesHelper sp;
    private int gender;

    @Override
    public int setContent() {
        return R.layout.activity_complete_user_infor;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Log.i(TAG, "init: ............................");
        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);
        initTtileBar();
        et_name.addTextChangedListener(textWatcher);
        tv_gender.addTextChangedListener(textWatcher);
        tv_birth.addTextChangedListener(textWatcher);
    }

    public void initTtileBar() {
        update_titleBar.setTitle("完善个人信息");
        update_titleBar.setTitleColor(Color.WHITE);
        update_titleBar.setLeftImageResource(R.drawable.return_button);
        update_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.tv_gender, R.id.tv_birth, R.id.bt_update})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gender:
                showSexChooseDialog();
                break;
            case R.id.tv_birth:
                if (mRxDialogWheelYearMonthDay == null) {
                    initWheelYearMonthDayDialog();
                }
                mRxDialogWheelYearMonthDay.show();
                break;
            case R.id.bt_update:
                if ("男".equals(tv_gender.getText().toString())) {
                    gender = 1;
                } else {
                    gender = 2;
                }
                String token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();
                String name = et_name.getText().toString().trim();
                String birth = tv_birth.getText().toString().trim();
                Log.i(TAG, "update: " + token
                        + "--" + name + "--" + gender + "--" + birth);

                update(sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString(),
                        et_name.getText().toString().trim(),
                        gender,
                        tv_birth.getText().toString().trim());

                break;
        }
    }

    public void update(String toke, String name, int gender, String birthdate) {
        RetrofitHelper.getApiService()
                .updateUserInfor(toke, "", name, "", gender, birthdate, "", 0, "", "", 1, "")
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityUpdateUserInfor>bindToLifecycle())
                .compose(ProgressUtils.<EntityUpdateUserInfor>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUpdateUserInfor>() {
                    @Override
                    public void onSuccess(EntityUpdateUserInfor response) {
                        if (response.getCode() == 1) {
                            if (response.getMsg().equals("OK")) {
                                //发送eventbus
                                EventBus.getDefault().post(new EventUpdateUserInfor("updateUserInfor"));
                                finish();
                                Intent intent = new Intent(CompleteUserInforActivity.this, AppraisalActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            //
            String name = et_name.getText().toString().trim();
            String gender = tv_gender.getText().toString().trim();
            String birth = tv_birth.getText().toString().trim();
            //用户名框的操作
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(birth)) {
                bt_update.setEnabled(true);
                bt_update.setBackground(getDrawable(R.drawable.login_bt_bg));
            } else {
                bt_update.setEnabled(false);
                bt_update.setBackground(getDrawable(R.drawable.login_bt_bg_default));
            }
        }
    };

    private void showSexChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                tv_gender.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }

    private void initWheelYearMonthDayDialog() {
        // ------------------------------------------------------------------选择日期开始
        mRxDialogWheelYearMonthDay = new RxDialogWheelYearMonthDay(this, 1800, 2019);
        mRxDialogWheelYearMonthDay.getSureView().setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (mRxDialogWheelYearMonthDay.getCheckBoxDay().isChecked()) {
                            tv_birth.setText(
                                    mRxDialogWheelYearMonthDay.getSelectorYear() + "年"
                                            + mRxDialogWheelYearMonthDay.getSelectorMonth() + "月"
                                            + mRxDialogWheelYearMonthDay.getSelectorDay() + "日");
                        } else {
                            tv_birth.setText(
                                    mRxDialogWheelYearMonthDay.getSelectorYear() + "年"
                                            + mRxDialogWheelYearMonthDay.getSelectorMonth() + "月");
                        }
                        mRxDialogWheelYearMonthDay.cancel();
                    }
                });
        mRxDialogWheelYearMonthDay.getCancleView().setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        mRxDialogWheelYearMonthDay.cancel();
                    }
                });
        // ------------------------------------------------------------------选择日期结束
    }

}
