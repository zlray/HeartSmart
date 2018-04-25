package com.xqlh.heartsmart.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityArticleBeautiful;
import com.xqlh.heartsmart.bean.EntityArticleNewest;
import com.xqlh.heartsmart.ui.home.model.IconTitleModel;
import com.xqlh.heartsmart.ui.home.ui.ArticleDetailActivity;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.Utils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2018/4/23.
 */

public class AdapterArticleHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Uri> listBanner; //banner

    private List<IconTitleModel> listEight; //8个按钮

    private List<EntityArticleBeautiful.ResultBean> listBeautiful = new ArrayList<>(); //美文

    private List<EntityArticleNewest.ResultBean> listNewest = new ArrayList<>(); //最新

    private final int BANNER_VIEW_TYPE = 0;//轮播图

    private final int EIGHT_VIEW_TYPE = 1;//8个按钮

    private final int BEAUTIFUL_VIEW_TYPE = 2;//美文

    private final int NEWEST_VIEW_TYPE = 3;//最新文章

    //最新
    public AdapterArticleHome(Context context) {
        this.context = context;
    }

    public void setNewestList(List<EntityArticleNewest.ResultBean> list) {
        this.listNewest = list;
        Log.i("lz", listNewest.size() + "setNewestList");
    }

    public void addNewestList(List<EntityArticleNewest.ResultBean> list) {
        //增加数据
        int position = listNewest.size();
        listNewest.addAll(position, list);
        notifyItemInserted(position);
    }

    public void clearNewestList() {
        listNewest.clear();
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

    public void setBeautifulList(List<EntityArticleBeautiful.ResultBean> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.listBeautiful = list;
        Log.i("lz", listBeautiful.size() + "setBeautifulList");
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
        } else if (viewType == BEAUTIFUL_VIEW_TYPE) {// 美文
            view = getView(R.layout.item_layout_beautiful);
            view.setLayoutParams(lp);
            return new BeautifulHolder(view);
        } else {//正常
            view = getView(R.layout.item_rv_newest);
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

    private void setEight(EightHolder eightHolder) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
        //设置LayoutManager
        eightHolder.rv_eight.setLayoutManager(gridLayoutManager);
        AdapterEightButton adapterEightButton;
        adapterEightButton = new AdapterEightButton(R.layout.item_icon_title_eight_button, listEight);

        eightHolder.rv_eight.setAdapter(adapterEightButton);

        adapterEightButton.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toasty.warning(Utils.getContext(), listEight.get(position).getTitle(), Toast.LENGTH_LONG, true).show();
            }
        });
    }

    private void setBeautiful(BeautifulHolder beautifulHolder) {
        Log.i("lz", "setBeautiful美文");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        beautifulHolder.rv_beautiful.setLayoutManager(linearLayoutManager);

        AdapterBeautifulArticle adapterBeautifulArticle;
        adapterBeautifulArticle = new AdapterBeautifulArticle(R.layout.item_rv_beautiful, context, listBeautiful);

        beautifulHolder.rv_beautiful.setAdapter(adapterBeautifulArticle);

        adapterBeautifulArticle.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("id", listBeautiful.get(position).getID());
                context.startActivity(intent);
            }
        });
    }

    //最新
    private void setNewest(NewestHolder newestHolder, final int position) {
        Log.i("lz", "setNewest最新的");
        newestHolder.tv_article_title.setText(listNewest.get(position - 3).getTitle());
        newestHolder.tv_article_type.setText(listNewest.get(position - 3).getArticleTypeStr());
        newestHolder.tv_article_reading_times.setText(listNewest.get(position - 3).getShowTimes() + "人阅读");
        newestHolder.tv_article_date.setText(Constants.getYYD(listNewest.get(position - 3).getCreateTime()));

        Glide.with(context).load(listNewest.get(position - 3).getTitlePic()).into(newestHolder.iv_article_titlepic);
        newestHolder.ll_newest_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("id", listNewest.get(position - 3).getID());
                context.startActivity(intent);
            }
        });

//        AdapterNewestArticle adapterNewestArticle;
//        adapterNewestArticle = new AdapterNewestArticle(R.layout.item_rv_newest, context, listNewest);
//
//        newestHolder.rv_newest.setAdapter(adapterNewestArticle);
//
//        adapterNewestArticle.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(context, ArticleDetailActivity.class);
//                intent.putExtra("id", listNewest.get(position).getID());
//                context.startActivity(intent);
//            }
//        });

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
        return listNewest.size() + 3;
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
        RecyclerView rv_eight;

        public EightHolder(View itemView) {
            super(itemView);
            rv_eight = (RecyclerView) itemView.findViewById(R.id.rv_eight);

        }
    }

    /**
     * 美文
     */
    public static class BeautifulHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_beautiful;

        public BeautifulHolder(View itemView) {
            super(itemView);
            rv_beautiful = (RecyclerView) itemView.findViewById(R.id.rv_beautifu);
        }
    }

    /**
     * 最新的
     */
    public static class NewestHolder extends RecyclerView.ViewHolder {
        //        RecyclerView rv_newest;
        TextView tv_article_title;
        TextView tv_article_type;
        TextView tv_article_reading_times;
        TextView tv_article_date;
        ImageView iv_article_titlepic;
        LinearLayout ll_newest_content;

        public NewestHolder(View itemView) {
            super(itemView);
            tv_article_title = (TextView) itemView.findViewById(R.id.tv_article_title);
            tv_article_type = (TextView) itemView.findViewById(R.id.tv_article_type);
            tv_article_reading_times = (TextView) itemView.findViewById(R.id.tv_article_reading_times);
            tv_article_date = (TextView) itemView.findViewById(R.id.tv_article_date);

            iv_article_titlepic = (ImageView) itemView.findViewById(R.id.iv_article_titlepic);
            ll_newest_content = (LinearLayout) itemView.findViewById(R.id.ll_newest_content);

//            rv_newest = (RecyclerView) itemView.findViewById(R.id.rv_newest);
        }
    }
}
