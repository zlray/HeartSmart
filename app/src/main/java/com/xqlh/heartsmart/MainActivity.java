package com.xqlh.heartsmart;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.ui.fragment.AppraisalFragment;
import com.xqlh.heartsmart.ui.fragment.EquipmentFragment;
import com.xqlh.heartsmart.ui.fragment.HomeFragment;
import com.xqlh.heartsmart.ui.fragment.MineFragment;
import com.xqlh.heartsmart.ui.fragment.ProductFragment;
import com.xqlh.heartsmart.utils.StatusBarUtil;
import com.xqlh.heartsmart.widget.BottomBar;
import com.xqlh.heartsmart.widget.BottomBarTab;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity {


    private static final String TAG = "MainActivity";

    @BindView(R.id.main_container)
    FrameLayout main_container;
    @BindView(R.id.main_bottombar)
    BottomBar main_bottombar;

    private SupportFragment[] mFragments = new SupportFragment[5];

    @Override
    public int setContent() {
        return R.layout.activity_main;
    }

    @Override
    public boolean setFullScreen() {
        return true;
    }

    @Override
    public void init() {


    }

    @Override
    public void bindView(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0, null);
        if (savedInstanceState == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = AppraisalFragment.newInstance();
            mFragments[2] = ProductFragment.newInstance();
            mFragments[3] = EquipmentFragment.newInstance();
            mFragments[4] = MineFragment.newInstance();

            getSupportDelegate().loadMultipleRootFragment(R.id.main_container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4]);
        } else {
            mFragments[0] = findFragment(HomeFragment.class);
            mFragments[1] = findFragment(AppraisalFragment.class);
            mFragments[2] = findFragment(ProductFragment.class);
            mFragments[3] = findFragment(EquipmentFragment.class);
            mFragments[4] = findFragment(MineFragment.class);
        }

        main_bottombar.addItem(new BottomBarTab(this, R.drawable.ic_news, "首页"))
                .addItem(new BottomBarTab(this, R.drawable.ic_news, "测评"))
                .addItem(new BottomBarTab(this, R.drawable.ic_news, "产品"))
                .addItem(new BottomBarTab(this, R.drawable.ic_news, "设备"))
                .addItem(new BottomBarTab(this, R.drawable.ic_news, "我的"));

        main_bottombar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                getSupportDelegate().showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }


}
