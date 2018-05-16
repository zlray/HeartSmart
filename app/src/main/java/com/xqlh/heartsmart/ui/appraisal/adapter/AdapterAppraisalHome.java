package com.xqlh.heartsmart.ui.appraisal.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityAppraisalRecommend;
import com.xqlh.heartsmart.ui.appraisal.ui.AppraisalCategoryActivity;
import com.xqlh.heartsmart.ui.appraisal.ui.AppraisalIntroduceActivity;
import com.xqlh.heartsmart.ui.home.adapter.AdapterEightButton;
import com.xqlh.heartsmart.ui.home.model.IconTitleModel;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class AdapterAppraisalHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Uri> listBanner; //banner

    private List<IconTitleModel> listEight; //8个按钮



    private List<EntityAppraisalRecommend.ResultBean> listHot = new ArrayList<>(); //美文

    private List<EntityAppraisalRecommend.ResultBean> listRecommend = new ArrayList<>(); //最新

    private final int BANNER_VIEW_TYPE = 0;//轮播图

    private final int EIGHT_VIEW_TYPE = 1;//8个按钮

    private final int BEAUTIFUL_VIEW_TYPE = 2;//美文

    private final int NEWEST_VIEW_TYPE = 3;//最新文章

    String[] CHANNELS;
    List<String> mDataList;

    //最新
    public AdapterAppraisalHome(Context context) {
        this.context = context;
        CHANNELS = new String[]{"专业测评", "趣味测评"};
        mDataList = Arrays.asList(CHANNELS);
    }

    public void setRecommendList(List<EntityAppraisalRecommend.ResultBean> list) {
        this.listRecommend = list;
    }

    public void addRecommendList(List<EntityAppraisalRecommend.ResultBean> list) {
        //增加数据
        int position = listRecommend.size();
        listRecommend.addAll(position, list);
        notifyItemInserted(position);
    }

    public void clearRecommend() {
        listRecommend.clear();
    }

//    public void refresh(List<ItemBean> newList) {
//        //刷新数据
//        mList.removeAll(mList);
//        mList.addAll(newList);
//        notifyDataSetChanged();
//    }


    public void setBannerList(List<Uri> list) {
        this.listBanner = list;
    }

    public void setEightList(List<IconTitleModel> list) {
        this.listEight = list;
    }


    public void setHotList(List<EntityAppraisalRecommend.ResultBean> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.listHot = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (viewType == BANNER_VIEW_TYPE) {//如果viewType是轮播图就去创建轮播图的viewHolder
            view = getView(R.layout.item_layout_banner);
            view.setLayoutParams(lp);
            return new BannerHolder(view);
        } else if (viewType == EIGHT_VIEW_TYPE) {// 8个按钮
            view = getView(R.layout.item_layout_eight);
            view.setLayoutParams(lp);
            return new EightHolder(view);
        } else if (viewType == BEAUTIFUL_VIEW_TYPE) {// hot
            view = getView(R.layout.item_layout_hot);
            view.setLayoutParams(lp);
            return new BeautifulHolder(view);
        } else {//正常
            view = getView(R.layout.item_rv_recommend);
            view.setLayoutParams(lp);
            return new NewestHolder(view);
        }
    }

    /**
     * 用来引入布局的方法
     */
    private View getView(int view) {
        View view1 = View.inflate(context, view, null);
        return view1;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //判断不同的ViewHolder做不同的处理
        if (holder instanceof BannerHolder) {//轮播图
            BannerHolder bannerHolder = (BannerHolder) holder;
            //设置banner的各种属性
            setBanner(bannerHolder);
        } else if (holder instanceof EightHolder) {//频道
            EightHolder eightHolder = (EightHolder) holder;
            //设置频道
            setEight(eightHolder);
        } else if (holder instanceof BeautifulHolder) {//美文
            BeautifulHolder beautifulHolder = (BeautifulHolder) holder;
            setBeautiful(beautifulHolder);
        } else if (holder instanceof NewestHolder) {//正常布局
            NewestHolder newestHolder = (NewestHolder) holder;
            setNewest(newestHolder, position);
        }
    }

    private void setBanner(BannerHolder bannerHolder) {
        bannerHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .setImages(listBanner)
                .setBannerAnimation(Transformer.Default)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

        }
    }

    private void setEight(final EightHolder eightHolder) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
        //设置LayoutManager
        eightHolder.rv_eight.setLayoutManager(gridLayoutManager);
        AdapterEightButton adapterEightButton;
        adapterEightButton = new AdapterEightButton(R.layout.item_icon_title_eight_button, listEight);

        eightHolder.rv_eight.setAdapter(adapterEightButton);

        adapterEightButton.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, AppraisalCategoryActivity.class);
                intent.putExtra("PsychtestTypeID", listEight.get(position).getArticleTypeID());
                intent.putExtra("title", listEight.get(position).getTitle());
                context.startActivity(intent);
            }
        });

    }


    private void setBeautiful(BeautifulHolder beautifulHolder) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        beautifulHolder.rv_hot.setLayoutManager(linearLayoutManager);

        AdapterHotApprisal adapterHotApprisal;
        adapterHotApprisal = new AdapterHotApprisal(R.layout.item_rv_hot, context, listHot);

        beautifulHolder.rv_hot.setAdapter(adapterHotApprisal);

        adapterHotApprisal.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, AppraisalIntroduceActivity.class);
                intent.putExtra("PsyID", listHot.get(position).getID().trim());
                context.startActivity(intent);
            }
        });
    }

    //最新
    private void setNewest(NewestHolder newestHolder, final int position) {
        newestHolder.tv_appraisal_title.setText(listRecommend.get(position - 3).getPsyName());
        newestHolder.tv_appraisal_introduction.setText(listRecommend.get(position - 3).getTitle());
        newestHolder.tv_appraisal_test_number.setText(listRecommend.get(position - 3).getTestMan() + "人测试");
        newestHolder.tv_appraisal_price.setText("￥" + 0.0);//价格

        Glide.with(context).load(listRecommend.get(position - 3).getPsyPic()).into(newestHolder.iv_appraisal);

        newestHolder.ll_appraisal_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AppraisalIntroduceActivity.class);
                intent.putExtra("PsyID", listRecommend.get(position - 3).getID().trim());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemViewType(int position) {
        /*
        区分item类型,返回不同的int类型的值
        在onCreateViewHolder方法中用viewType来创建不同的ViewHolder
         */
        if (position == 0) {//第0个位置是轮播图
            return BANNER_VIEW_TYPE;
        } else if (position == 1) {//第一个位置8个按钮
            return EIGHT_VIEW_TYPE;
        } else if (position == 2) {//第2个位置
            Log.i("lz", "第二个位置");
            return BEAUTIFUL_VIEW_TYPE;
        } else {//其他位置返回正常的布局
            Log.i("lz", "第三个位置");
            return NEWEST_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return listRecommend.size() + 3;
    }


    /**
     * 轮播图的ViewHolder
     */
    public static class BannerHolder extends RecyclerView.ViewHolder {
        Banner banner;

        public BannerHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);

        }
    }

    /**
     * 8个按钮
     */
    public static class EightHolder extends RecyclerView.ViewHolder {
        MagicIndicator magic_indicator;
        ViewPager viewPager;
        RecyclerView rv_eight;

        public EightHolder(View itemView) {
            super(itemView);
//            magic_indicator = itemView.findViewById(R.id.magic_indicator);
//            viewPager = itemView.findViewById(R.id.view_pager);
            rv_eight = (RecyclerView) itemView.findViewById(R.id.rv_eight);
        }
    }

    /**
     * 最热
     */
    public static class BeautifulHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_hot;

        public BeautifulHolder(View itemView) {
            super(itemView);
            rv_hot = (RecyclerView) itemView.findViewById(R.id.rv_hot);
        }
    }

    /**
     * 推荐
     */
    public static class NewestHolder extends RecyclerView.ViewHolder {
        //        RecyclerView rv_newest;
        TextView tv_appraisal_title;
        TextView tv_appraisal_introduction;
        TextView tv_appraisal_test_number;
        TextView tv_appraisal_price;
        ImageView iv_appraisal;
        LinearLayout ll_appraisal_content;

        public NewestHolder(View itemView) {
            super(itemView);
            tv_appraisal_title = (TextView) itemView.findViewById(R.id.tv_appraisal_title);
            tv_appraisal_introduction = (TextView) itemView.findViewById(R.id.tv_appraisal_introduction);
            tv_appraisal_test_number = (TextView) itemView.findViewById(R.id.tv_appraisal_test_number);
            tv_appraisal_price = (TextView) itemView.findViewById(R.id.tv_appraisal_price);

            iv_appraisal = (ImageView) itemView.findViewById(R.id.iv_appraisal);
            ll_appraisal_content = itemView.findViewById(R.id.ll_appraisal_content);

//            rv_newest = (RecyclerView) itemView.findViewById(R.id.rv_newest);
        }
    }
}
