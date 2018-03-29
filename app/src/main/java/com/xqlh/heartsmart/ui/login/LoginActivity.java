package com.xqlh.heartsmart.ui.login;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.vondear.rxtools.RxAnimationTool;
import com.vondear.rxtools.RxKeyboardTool;
import com.xqlh.heartsmart.MainActivity;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.api.bean.EntityLogin;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.logo)
    ImageView mLogo;

    @BindView(R.id.et_account)
    EditText et_account;

    @BindView(R.id.iv_clean_account)
    ImageView iv_clean_account;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.iv_clean_password)
    ImageView iv_clean_password;


    @BindView(R.id.bt_login)
    Button bt_login;


    @BindView(R.id.bt_forget_password)
    TextView bt_forget_password;

    @BindView(R.id.content)
    LinearLayout mContent;

    @BindView(R.id.scrollView)
    ScrollView mScrollView;


    @BindView(R.id.root)
    RelativeLayout mRoot;

    @BindView(R.id.iv_show_password)
    ImageView iv_show_password;


    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例
    private int height = 0;


    @Override
    public int setContent() {
        return R.layout.activity_login;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        initView();
        initEvent();

    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    private void initView() {
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
    }

    private void initEvent() {
        et_account.addTextChangedListener(textWatcher);
        et_password.addTextChangedListener(textWatcher);
        /**
         * 禁止键盘弹起的时候可以滚动
         */
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mScrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
                    int dist = mContent.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        RxAnimationTool.zoomIn(mLogo, 0.6f, dist);
                    }

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    if ((mContent.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", mContent.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        RxAnimationTool.zoomOut(mLogo, 0.6f);
                    }
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
            String userAcoount = et_account.getText().toString().trim();
            String userPassword = et_password.getText().toString().trim();
            if (userAcoount.length() > 0 && userPassword.length() > 0) {
                bt_login.setEnabled(true);
                bt_login.setBackgroundResource(R.drawable.login_bt_bg);
            } else {
                bt_login.setEnabled(false);
                bt_login.setBackgroundResource(R.drawable.login_bt_bg_default);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String userAcoount = et_account.getText().toString().trim();
            String userPassword = et_password.getText().toString().trim();
            if (!TextUtils.isEmpty(userAcoount) && iv_clean_account.getVisibility() == View.GONE) {
                //显示清除按钮
                iv_clean_account.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(userAcoount)) {
                iv_clean_account.setVisibility(View.GONE);
                bt_login.setEnabled(false);
            }
            if (!TextUtils.isEmpty(userPassword) && iv_clean_password.getVisibility() == View.GONE) {
                iv_clean_password.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(userPassword)) {
                iv_clean_password.setVisibility(View.GONE);
                bt_login.setEnabled(false);
            }
        }
    };


    @OnClick({R.id.iv_clean_account, R.id.iv_clean_password, R.id.iv_show_password, R.id.bt_login, R.id.bt_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clean_account:
                et_account.setText("");
                break;
            case R.id.iv_clean_password:
                et_password.setText("");
                break;
            case R.id.iv_show_password:
                if (et_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_show_password.setImageResource(R.drawable.pass_visuable);
                } else {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_show_password.setImageResource(R.drawable.pass_gone);
                }
                String pwd = et_password.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    et_password.setSelection(pwd.length());
                break;
            case R.id.bt_login:
                RxKeyboardTool.hideSoftInput(LoginActivity.this);
                //
                login();

                break;
            case R.id.bt_forget_password:
                startActivity(new Intent(LoginActivity.this, RetrievePasswordActivity.class));
                break;
        }
    }

    public void login() {
        // 18701662581
        RetrofitHelper.getApiService()
                .Login("xiaomeinv", "123456")
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityLogin>bindToLifecycle())
                .compose(ProgressUtils.<EntityLogin>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityLogin>() {
                    @Override
                    public void onSuccess(EntityLogin response) {
                        if (response.getCode() == 1) {
                            if (response.getMsg().equals("OK")) {
                                Toasty.success(Utils.getContext(), "成功接收到Token,可存到本地", Toast.LENGTH_SHORT, true).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        }
                    }
                });

    }

}
