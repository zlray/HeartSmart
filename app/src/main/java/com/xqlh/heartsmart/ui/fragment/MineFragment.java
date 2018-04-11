package com.xqlh.heartsmart.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseLazyFragment;
import com.xqlh.heartsmart.ui.mine.MineActivity;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseLazyFragment {

    @BindView(R.id.mine_titleBar)
    TitleBar mine_titleBar;
    @BindView(R.id.mine_iv_head)
    ImageView mine_iv_head;

    @Override
    protected int setContentView() {
        Log.i("lz", "我的fragment");
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {
        initTtileBar();
    }

    @Override
    protected void lazyLoad() {

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
