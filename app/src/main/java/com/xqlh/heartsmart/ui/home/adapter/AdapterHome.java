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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityArticleNewest;
import com.xqlh.heartsmart.bean.EntityUserReport;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterUserReportSimple;
import com.xqlh.heartsmart.ui.equipment.ui.EquipmentReportCategoryActivity;
import com.xqlh.heartsmart.ui.home.model.IconTitleModel;
import com.xqlh.heartsmart.ui.home.ui.ArticleDetailActivity;
import com.xqlh.heartsmart.ui.home.ui.ArticleHomeActivity;
import com.xqlh.heartsmart.ui.mine.ui.AppraisalUserReportActivity;
import com.xqlh.heartsmart.ui.mine.ui.MusicRelaxActivity;
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
    private List<Uri> listBanner = new ArrayList<>(); //banner

    private List<IconTitleModel> listEight = new ArrayList<>(); //8个按钮

    private List<EntityArticleNewest.ResultBean> listNewest = new ArrayList<>(); //最新

    private List<EntityUserReport.ResultBean> listReport = new ArrayList<>(); //报告


    private final int BANNER_VIEW_TYPE = 0;//轮播图

    private final int EIGHT_VIEW_TYPE = 1;//8个按钮

    private final int TEXT_VIEW_TYPE_ONE = 2;//第一个文字

    private final int REPORT_VIEW_TYPE = 3;//报告

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

    public void setReportList(List<EntityUserReport.ResultBean> list) {
        this.listReport = list;
        Log.i("lz", listReport.size() + "setReportList");
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
        if (viewType == BANNER_VIEW_TYPE) {//
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
        } else if (viewType == REPORT_VIEW_TYPE) { //报告
            view = getView(R.layout.item_layout_report);
            view.setLayoutParams(lp);
            return new ReportHolder(view);
        } else if (viewType == TEXT_VIEW_TYPE_TWO) {// 第二文字
            view = getView(R.layout.item_layout_text);
            view.setLayoutParams(lp);
            return new TextHolder2(view);
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
        if (holder instanceof BannerHolder) {//轮播图
            BannerHolder bannerHolder = (BannerHolder) holder;
            setBanner(bannerHolder);
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
        } else if (holder instanceof TextHolder2) {//最热文章文字
            TextHolder2 textHolderTwo = (TextHolder2) holder;
            setTextTwo(textHolderTwo);
        } else if (holder instanceof NewestHolder) {//正常布局
            NewestHolder newestHolder = (NewestHolder) holder;
            setNewest(newestHolder, position);
        }
    }

    //设备报告
    private void setReport(ReportHolder reportHolder) {
        AdapterUserReportSimple adapterUserReportSimple;

        reportHolder.rv_report.setLayoutManager(new LinearLayoutManager(context));

        adapterUserReportSimple = new AdapterUserReportSimple(R.layout.item_layout_report_simple, listReport);

        reportHolder.rv_report.setAdapter(adapterUserReportSimple);

        adapterUserReportSimple.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(context, AppraisalUserReportActivity.class);
                intent.putExtra("reportId", listReport.get(position).getID());
                context.startActivity(intent);

            }
        });
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
                Intent intent = null;
                if (listEight.get(position).getTitle().equals("心理文章")) {
                    intent = new Intent(context, ArticleHomeActivity.class);
                } else if (listEight.get(position).getTitle().equals("音乐放松")) {
                    intent = new Intent(context, MusicRelaxActivity.class);
                } else {
                    intent = new Intent(context, EquipmentReportCategoryActivity.class);
                    intent.putExtra("id", listEight.get(position).getArticleTypeID());
                    intent.putExtra("title", listEight.get(position).getTitle());
                }
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

    private void setTextTwo(TextHolder2 textHolder) {
        textHolder.tv.setText("文章最热");
        textHolder.rv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //最新
    private void setNewest(NewestHolder newestHolder, final int position) {
        newestHolder.tv_article_title.setText(listNewest.get(position - 3).getTitle());
        newestHolder.tv_article_type.setText(listNewest.get(position - 3).getArticleTypeStr());
        newestHolder.tv_article_introduction.setText(listNewest.get(position - 3).getIntroduction());
        newestHolder.tv_article_reading_times.setText(listNewest.get(position - 3).getShowTimes() + "人阅读");
        newestHolder.tv_article_date.setText(Constants.getYYD(listNewest.get(position - 3).getCreateTime()));

        Glide.with(context).load(listNewest.get(position - 3).getTitlePic()).into(newestHolder.iv_article_titlepic);
        newestHolder.ll_newest_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("id", listNewest.get(position - 3).getID().trim());
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
            return BANNER_VIEW_TYPE; //banner
        } else if (position == 1) {
            return EIGHT_VIEW_TYPE;//8个
        } else if (position == 2) {
            return TEXT_VIEW_TYPE_ONE;//
        } else if (position == 3) {
            return REPORT_VIEW_TYPE;//报告
        } else if (position == 4) {
            return TEXT_VIEW_TYPE_TWO;//文字
        } else {
            return NEWEST_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return listNewest.size() + 3;//
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

    public static class TextHolder2 extends RecyclerView.ViewHolder {
        RelativeLayout rv_more;
        TextView tv;

        public TextHolder2(View itemView) {
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
        RecyclerView rv_report;

        public ReportHolder(View itemView) {
            super(itemView);
            rv_report = (RecyclerView) itemView.findViewById(R.id.rv_report);
        }
    }

    /**
     * 最新的
     */
    public static class NewestHolder extends RecyclerView.ViewHolder {
        TextView tv_article_title;//标题
        TextView tv_article_introduction;
        TextView tv_article_type; //类型
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
            tv_article_introduction = itemView.findViewById(R.id.tv_article_introduction);
            iv_article_titlepic = (ImageView) itemView.findViewById(R.id.iv_article_titlepic);
            ll_newest_content = (LinearLayout) itemView.findViewById(R.id.ll_newest_content);

        }
    }
}
