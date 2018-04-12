package com.xqlh.heartsmart.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseLazyFragment;
import com.xqlh.heartsmart.ui.bean.EntityUserInfor;
import com.xqlh.heartsmart.ui.mine.MineActivity;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ImageLoaderUtil;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.utils.Utils;
import com.xqlh.heartsmart.widget.TitleBar;

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

    private SharedPreferencesHelper sp_login_token;

    @Override
    protected int setContentView() {
        Log.i("lz", "我的fragment");
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        initTtileBar();
        sp_login_token = new SharedPreferencesHelper(
                getActivity(), Constants.CHECKINFOR);
    }

    @Override
    protected void lazyLoad() {
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
                                Log.i("lz", "昵称" + response.getResult().getName());
                                Log.i("lz", "性别" + response.getResult().getSex());
                                Log.i("lz", "出生日期" + response.getResult().getBirthDate());
                            }
                        } else {
                            Toasty.warning(Utils.getContext(), "用户名或者密码错误", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    public void initTtileBar() {
        mine_titleBar.setTitle("我的");
        mine_titleBar.setTitleColor(Color.WHITE);

    }

    @OnClick({R.id.mine_iv_head})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.mine_iv_head:
                startActivity(new Intent(getActivity(), MineActivity.class));
                break;
        }
    }


}
