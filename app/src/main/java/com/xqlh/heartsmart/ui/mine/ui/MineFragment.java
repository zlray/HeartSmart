package com.xqlh.heartsmart.ui.mine.ui;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xqlh.heartsmart.Event.EventUpdateUserInfor;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseLazyFragment;
import com.xqlh.heartsmart.bean.EntityUserInfor;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ImageLoaderUtil;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseLazyFragment {

    @BindView(R.id.mine_titleBar)
    TitleBar mine_titleBar;
    @BindView(R.id.mine_iv_head)
    ImageView mine_iv_head;
    @BindView(R.id.mine_tv_name)
    TextView mine_tv_name;
    @BindView(R.id.rv_setting)
    RelativeLayout rv_setting;
    @BindView(R.id.rv_appraisal)
    RelativeLayout rv_appraisal;

    @BindView(R.id.rv_head)
    RelativeLayout rv_head;

    @BindView(R.id.rv_collection)
    RelativeLayout rv_collection;

    @BindView(R.id.rv_report)
    RelativeLayout rv_report;



    private SharedPreferencesHelper sp_login_token;

    @Override
    protected int setContentView() {
        Log.i("lz", "我的fragment");
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initTtileBar();
        sp_login_token = new SharedPreferencesHelper(
                getActivity(), Constants.CHECKINFOR);
    }

    @OnClick({R.id.rv_setting})
    public void onClikc(View view) {
        switch (view.getId()) {
            case R.id.rv_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    @Override
    protected void lazyLoad() {
        getUserInfor();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void updatetCheck(EventUpdateUserInfor eventUpdateUserInfor) {
        switch (eventUpdateUserInfor.getMsg()) {
            case "updateUserInfor":
                getUserInfor();
                break;
        }
    }

    public void getUserInfor() {
        RetrofitHelper.getApiService()
                .getUserInfor(sp_login_token.getSharedPreference(Constants.LOGIN_TOKEN, "").toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUserInfor>() {
                    @Override
                    public void onSuccess(EntityUserInfor response) {
                        if (response.getCode() == 1) {
                            if (response.getMsg().equals("OK")) {
                                mine_tv_name.setText(response.getResult().getName());
                                ImageLoaderUtil.LoadImage(getActivity(), response.getResult().getHeadimgurl(), mine_iv_head);
                            }
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "用户名或者密码错误", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    public void initTtileBar() {
        mine_titleBar.setTitle("我的");
        mine_titleBar.setTitleColor(Color.WHITE);

    }

    @OnClick({R.id.rv_head, R.id.rv_appraisal, R.id.rv_report, R.id.rv_collection})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rv_head:
                startActivity(new Intent(getActivity(), MineActivity.class));
                break;
            case R.id.rv_appraisal:
                startActivity(new Intent(getActivity(), UndoneAppraisalActivity.class));
                break;
            case R.id.rv_report:
                startActivity(new Intent(getActivity(), UserReportCategoryActivity.class));
                break;
            case R.id.rv_collection:
                startActivity(new Intent(getActivity(),CollectActivity.class));
                break;
        }
    }


}
