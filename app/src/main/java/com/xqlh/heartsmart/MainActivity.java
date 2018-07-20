package com.xqlh.heartsmart;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vondear.rxtools.RxPermissionsTool;
import com.xqlh.heartsmart.adapter.FragmentVpAdapter;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.ui.appraisal.ui.AppraisalFragment;
import com.xqlh.heartsmart.ui.equipment.ui.EquipmentFragment;
import com.xqlh.heartsmart.ui.home.ui.HomeFragment;
import com.xqlh.heartsmart.ui.mine.ui.MineFragment;
import com.xqlh.heartsmart.ui.product.ui.ProductFragment;
import com.xqlh.heartsmart.utils.ContextUtils;

import java.util.ArrayList;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    private static final String TAG = "MainActivity";
    //viewPager
    @BindView(R.id.vp_main)
    ViewPager mViewPager;

    @BindView(R.id.rg_main)
    RadioGroup mRadioGroup;

    //首页
    @BindView(R.id.rb_home)
    RadioButton rb_home;
    //测评
    @BindView(R.id.rb_appraisal)
    RadioButton rb_appraisal;
    //产品
    @BindView(R.id.rb_product)
    RadioButton rb_product;
    //设备
    @BindView(R.id.rb_equipment)
    RadioButton rb_equipment;
    //我的
    @BindView(R.id.rb_mine)
    RadioButton rb_mine;

    //fragment集合
    private ArrayList<Fragment> mFragments;

    //fragment的适配器  ViewpagerAdapter
    private FragmentVpAdapter mPagerAdapter;

    //首页
    HomeFragment homeFragment;

    //测评
    AppraisalFragment appraisalFragment;

    //产品
    ProductFragment productFragment;

    //我的碎片 MineFragment
    EquipmentFragment equipmentFragment;

    MineFragment mineFragment;

    //是否进入双击退出计时状态
    boolean is_exit = false;
    //第一次点击退出的时间戳
    long l_firstClickTime;
    //第二次点击退出的时间戳
    long l_secondClickTime;


    /**
     * 绑定控件
     */

    @Override
    public int setContent() {
        return R.layout.activity_main;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        //统一处理多个权限
        RxPermissionsTool.
                with(MainActivity.this).
                addPermission(Manifest.permission.ACCESS_FINE_LOCATION).
                addPermission(Manifest.permission.ACCESS_COARSE_LOCATION).
                addPermission(Manifest.permission.READ_EXTERNAL_STORAGE).
                addPermission(Manifest.permission.CAMERA).
                addPermission(Manifest.permission.CALL_PHONE).
                addPermission(Manifest.permission.READ_PHONE_STATE).
                addPermission(Manifest.permission.BLUETOOTH).
                initPermission();
/**
 * 加载单选组，使用单选组不必给单选组内的每个RadioButton 设置监听
 */
        /**
         * 给单选按钮设置监听
         */
        mRadioGroup.setOnCheckedChangeListener(this);
        /**
         * 实例化fragment
         */
        homeFragment = new HomeFragment();
        appraisalFragment = new AppraisalFragment();
        productFragment = new ProductFragment();
        equipmentFragment = new EquipmentFragment();
        mineFragment = new MineFragment();

        //实例化fragmentlist
        mFragments = new ArrayList<>();

        //fragment集合中添加fragment
        mFragments.add(homeFragment);
        mFragments.add(appraisalFragment);
        mFragments.add(productFragment);
        mFragments.add(equipmentFragment);
        mFragments.add(mineFragment);

        //适配器
        mPagerAdapter = new FragmentVpAdapter(getSupportFragmentManager(), mFragments);

        //viewPager设置适配器
        mViewPager.setAdapter(mPagerAdapter);

        //当前为第一个页面
//        mViewPager.setCurrentItem(0);


        mRadioGroup.check(R.id.rb_home);

        //ViewPager的页面改变监听
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击退出
        doubleColickExit(keyCode, event);

        return true;
    }

    /**
     * @description 双击退出函数
     */
    private void doubleColickExit(int keyCode, KeyEvent event) {
        //当用户第一次点击返回钮时
            if (keyCode == KeyEvent.KEYCODE_BACK && is_exit == false) {
                is_exit = true;//设置记录标志为true
                l_firstClickTime = System.currentTimeMillis();//获得第一次点击的时间戳
                //显示再次点击退出提示
                Toasty.custom(ContextUtils.getContext(), "双击退出", getResources().getDrawable(R.drawable.beat), Toast.LENGTH_SHORT, false).show();
            }
        //用户第二次点击返回钮
        else if (keyCode == KeyEvent.KEYCODE_BACK && is_exit == true) {
            l_secondClickTime = System.currentTimeMillis();//记录下第二次点击退出的时间
            //时间差在两秒之内，退出程序
            if (l_secondClickTime - l_firstClickTime < 2000) {
                finish();
            } else {
                is_exit = false;//重置记录退出时间标志
                doubleColickExit(keyCode, event);//超出2000毫秒时，重新开始本函数逻辑
            }
        }

    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            //获取当前页面用于改变对应RadioButton的状态
            int current = mViewPager.getCurrentItem();

            switch (current) {
                case 0:
                    mRadioGroup.check(R.id.rb_home);
                    break;
                case 1:
                    mRadioGroup.check(R.id.rb_appraisal);
                    break;
                case 2:
                    mRadioGroup.check(R.id.rb_product);
                    break;
                case 3:
                    mRadioGroup.check(R.id.rb_equipment);
                    break;
                case 4:
                    mRadioGroup.check(R.id.rb_mine);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {


        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rb_appraisal:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.rb_product:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.rb_equipment:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.rb_mine:
                mViewPager.setCurrentItem(4);
                break;
        }
    }


}
