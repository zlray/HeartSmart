package com.xqlh.heartsmart.ui.appraisal.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.ui.home.adapter.AdapterEightButton;
import com.xqlh.heartsmart.ui.home.model.IconTitleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 */

public class AdapterAppraisalVP extends PagerAdapter {
    private List<String> mDataList;
    private List<IconTitleModel> eightList1 = new ArrayList<>();

    private List<IconTitleModel> eightList2 = new ArrayList<>();


    public AdapterAppraisalVP(List<String> dataList) {
        mDataList = dataList;
    }

    public void initData() {

        eightList1.add(new IconTitleModel(R.drawable.health, "学习素质", "1e6dbe0ea7c14b8c9098335f0eae3150"));
        eightList1.add(new IconTitleModel(R.drawable.workplace, "职业指导", "79fc5ca675ac4ecca6948bbfd5544ad0"));
        eightList1.add(new IconTitleModel(R.drawable.knowledge, "人际交往", "7b826408f2fe4b3d846f0818e28592f3"));
        eightList1.add(new IconTitleModel(R.drawable.popular_science, "个性人格", "9e3af7070e22404fa87afb5ba1418d02"));
        eightList1.add(new IconTitleModel(R.drawable.sex_psychology, "婚恋家庭", "a193fd29ca8a41f3b7f5d32bc572dc57"));
        eightList1.add(new IconTitleModel(R.drawable.beautiful_article, "智力能力", "cbfa55abae374d2587633b84e45a44e5"));
        eightList1.add(new IconTitleModel(R.drawable.marriage, "心理健康", "fedcfb5f986c439cacc84a4f93907e5b"));
        eightList1.add(new IconTitleModel(R.drawable.health, "情绪测评", "d5984b6874a440f2875f1e43e419e927"));

        //8个按钮
        eightList2.clear();

        eightList2.add(new IconTitleModel(R.drawable.health, "财运", "571a0559d9444e86ad7e1fdb411c2ba8"));
        eightList2.add(new IconTitleModel(R.drawable.workplace, "智商", "5e4ec7ee4153457da4f960747e56a754"));
        eightList2.add(new IconTitleModel(R.drawable.knowledge, "社交", "667eec7961974cffa86d402283b41de9"));
        eightList2.add(new IconTitleModel(R.drawable.popular_science, "性格", "a13fe0241748437b8334a5cf1815c151"));
        eightList2.add(new IconTitleModel(R.drawable.sex_psychology, "爱情", "a32f134aef05451c8c37c6de5c2d3339"));
        eightList2.add(new IconTitleModel(R.drawable.beautiful_article, "星座", "ac6dd0df66a9413085cc5fa7c5486d01"));
        eightList2.add(new IconTitleModel(R.drawable.marriage, "人物匹配", "b14d7cf41b39465896426eaee606b91a"));
        eightList2.add(new IconTitleModel(R.drawable.health, "事业", "cbfa55abae374d2587633b84e45a44ea"));
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        RecyclerView recyclerView = new RecyclerView(container.getContext());

        AdapterEightButton  adapterEightButton = new AdapterEightButton(R.layout.item_icon_title_eight_button,eightList1);

        recyclerView.setAdapter(adapterEightButton);

        container.addView(recyclerView);
        return recyclerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position);
    }
}
