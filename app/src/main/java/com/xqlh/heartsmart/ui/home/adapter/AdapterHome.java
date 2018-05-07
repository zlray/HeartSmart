package com.xqlh.heartsmart.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityArticleBeautiful;
import com.xqlh.heartsmart.bean.EntityArticleNewest;
import com.xqlh.heartsmart.ui.home.model.IconTitleModel;
import com.xqlh.heartsmart.ui.home.ui.ArticleDetailActivity;
import com.xqlh.heartsmart.ui.home.ui.ArticleHomeActivity;
import com.xqlh.heartsmart.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class AdapterHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Uri> listBanner; //banner

    private List<IconTitleModel> listEight; //8个按钮

    private List<EntityArticleBeautiful.ResultBean> listBeautiful = new ArrayList<>(); //美文

    private List<EntityArticleNewest.ResultBean> listNewest = new ArrayList<>(); //最新

    private final int BUTTON_VIEW_TYPE = 0;//点击测试按钮

    private final int EIGHT_VIEW_TYPE = 1;//8个按钮

    private final int TEXT_VIEW_TYPE_ONE = 2;//第一个文字

    private final int BANNER_VIEW_TYPE = 3;//轮播图

    private final int TEXT_VIEW_TYPE_TWO = 4;//第二个文字

    private final int NEWEST_VIEW_TYPE = 5;//最新文章

    //最新
    public AdapterHome(Context context) {
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

    public void setBannerList(List<Uri> list) {
        this.listBanner = list;
    }

    public void setEightList(List<IconTitleModel> list) {
        this.listEight = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (viewType == BUTTON_VIEW_TYPE) {//

            view = getView(R.layout.item_layout_banner);
            view.setLayoutParams(lp);
            return new BannerHolder(view);

        } else if (viewType == EIGHT_VIEW_TYPE) {//8个按钮
            view = getView(R.layout.item_layout_eight);
            view.setLayoutParams(lp);
            return new EightHolder(view);
        } else if (viewType == TEXT_VIEW_TYPE_ONE) {// 第一文字
            view = getView(R.layout.item_layout_text);
            view.setLayoutParams(lp);
            return new TextHolder(view);
        } else if (viewType == BANNER_VIEW_TYPE) {//轮播
            view = getView(R.layout.item_layout_banner);
            view.setLayoutParams(lp);
            return new BannerHolder(view);
        } else if (viewType == TEXT_VIEW_TYPE_ONE) {// 第二文字
            view = getView(R.layout.item_layout_text);
            view.setLayoutParams(lp);
            return new TextHolder(view);
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
        if (holder instanceof ButtonHolder) {//按钮
            ButtonHolder buttonHolder = (ButtonHolder) holder;
            //设置banner的各种属性
            setButton(buttonHolder);
        } else if (holder instanceof EightHolder) { //8个按钮
            EightHolder eightHolder = (EightHolder) holder;
            //设置频道
            setEight(eightHolder);
        } else if (holder instanceof TextHolder) {//设备报告文字
            TextHolder textHolderOne = (TextHolder) holder;
            setTextOne(textHolderOne);
        } else if (holder instanceof ReportHolder) {//报告
            ReportHolder reportHolder = (ReportHolder) holder;
            setReport(reportHolder);
        } else if (holder instanceof BannerHolder) {//轮播图
            BannerHolder bannerHolder = (BannerHolder) holder;
            setBanner(bannerHolder);
        } else if (holder instanceof TextHolder) {//最热文章文字
            TextHolder textHolderTwo = (TextHolder) holder;
            setTextTwo(textHolderTwo);
        } else if (holder instanceof NewestHolder) {//正常布局
            NewestHolder newestHolder = (NewestHolder) holder;
            setNewest(newestHolder, position);
        }
    }

    private void setButton(ButtonHolder buttonHolder) {

    }

    private void setReport(ReportHolder reportHolder) {

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
                Intent intent = new Intent(context, ArticleHomeActivity.class);

                context.startActivity(intent);
            }
        });
    }

    private void setTextOne(TextHolder textHolder) {
        textHolder.tv.setText("设备报告");
        textHolder.rv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void setTextTwo(TextHolder textHolder) {
        textHolder.tv.setText("文章最热");
        textHolder.rv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //最新
    private void setNewest(NewestHolder newestHolder, final int position) {
        newestHolder.tv_article_title.setText(listNewest.get(position - 4).getTitle());
        newestHolder.tv_article_type.setText(listNewest.get(position - 4).getArticleTypeStr());
        newestHolder.tv_article_reading_times.setText(listNewest.get(position - 4).getShowTimes() + "人阅读");
        newestHolder.tv_article_date.setText(Constants.getYYD(listNewest.get(position - 4).getCreateTime()));

        Glide.with(context).load(listNewest.get(position - 4).getTitlePic()).into(newestHolder.iv_article_titlepic);
        newestHolder.ll_newest_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("id", listNewest.get(position - 4).getID().trim());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemViewType(int position) {

        /**
         *  区分item类型,返回不同的int类型的值,
         *  在onCreateViewHolder方法中用viewType来创建不同的ViewHolder
         */
        if (position == 0) {
            return BUTTON_VIEW_TYPE;
        } else if (position == 1) {
            return EIGHT_VIEW_TYPE;
        } else if (position == 2) {
            return TEXT_VIEW_TYPE_ONE;
        } else if (position == 3) {
            return BANNER_VIEW_TYPE;
        } else if (position == 4) {
            return TEXT_VIEW_TYPE_TWO;
        } else {
            return NEWEST_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return listNewest.size() + 4;
    }


    /**
     * 最新文章
     */
    public static class ButtonHolder extends RecyclerView.ViewHolder {

        public ButtonHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * 设备报告    设备报告
     */
    public static class TextHolder extends RecyclerView.ViewHolder {
        RelativeLayout rv_more;
        TextView tv;

        public TextHolder(View itemView) {
            super(itemView);
            rv_more = (RelativeLayout) itemView.findViewById(R.id.rv_more);
            tv = itemView.findViewById(R.id.tv);
        }
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
     * 设备报告
     */
    public static class ReportHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_beautiful;

        public ReportHolder(View itemView) {
            super(itemView);
            rv_beautiful = (RecyclerView) itemView.findViewById(R.id.rv_beautifu);
        }
    }

    /**
     * 最新的
     */
    public static class NewestHolder extends RecyclerView.ViewHolder {
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

        }
    }
}
