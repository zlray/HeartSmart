package com.xqlh.heartsmart.ui.guide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.ui.guide.adapter.GuidePageAdapter;
import com.xqlh.heartsmart.ui.welcome.WelcomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zl
 * @description 引导页面  GuideActivity
 * @date 2018-3-16   16th March,2018
 */

public class GuideActivity extends BaseActivity {
    @BindView(R.id.guide_vp)
    ViewPager guide_vp;

    @BindView(R.id.guide_ib_start)
    ImageButton guide_ib_start;

    @BindView(R.id.guide_ll_point)
    LinearLayout guide_ll_point;

    private int[] imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    private ViewGroup vg;//放置圆点

    //实例化原点View
    private ImageView iv_point;
    private ImageView[] ivPointArray;

    @Override
    public int setContent() {
        return R.layout.activity_guide;
    }

    @Override
    public boolean setFullScreen() {
        //返回true设置为全屏
        // return true means full screen
        return true;
    }

    @Override
    public void init() {
        //加载ViewPager
        initViewPager();

        //加载底部圆点
        initPoint();
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }


    /**
     * 加载底部圆点
     */
    private void initPoint() {
        //这里实例化LinearLayout
        vg = (ViewGroup) findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            iv_point = new ImageView(this);
            iv_point.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            iv_point.setPadding(30, 0, 30, 0);//left,top,right,bottom
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.drawable.full_holo);
            } else {
                iv_point.setBackgroundResource(R.drawable.empty_holo);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
        }
    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        //实例化图片资源
        imageIdArray = new int[]{R.drawable.guidepage1, R.drawable.guidepage1, R.drawable.guidepage1, R.drawable.guidepage1};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);

            //将ImageView加入到集合中
            viewList.add(imageView);
        }

        //View集合初始化好后，设置Adapter
        guide_vp.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        guide_vp.addOnPageChangeListener(new MyOnPageChangeListener());
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //循环设置当前页的标记图
            int length = imageIdArray.length;
            for (int i = 0; i < length; i++) {
                ivPointArray[position].setBackgroundResource(R.drawable.full_holo);
                if (position != i) {
                    ivPointArray[i].setBackgroundResource(R.drawable.empty_holo);
                }
            }

            //判断是否是最后一页，若是则显示按钮
            if (position == imageIdArray.length - 1) {
                guide_ib_start.setVisibility(View.VISIBLE);
            } else {
                guide_ib_start.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @OnClick({R.id.guide_ib_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_ib_start:
                startActivity(new Intent(GuideActivity.this, WelcomeActivity.class));
                break;
        }
    }
}
