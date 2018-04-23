package com.xqlh.heartsmart.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.RvListener;
import com.xqlh.heartsmart.bean.EntityArticleNewest;
import com.xqlh.heartsmart.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/17.
 */

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.MineViewHolder> {
    private Context mContext;
    private List<EntityArticleNewest.ResultBean> marticleList;
    private RvListener listener;

    public AdapterArticle(Context mContext, List<EntityArticleNewest.ResultBean> articleList, RvListener listener) {
        this.mContext = mContext;
        this.marticleList = articleList;
        this.listener = listener;
    }

    @Override
    public MineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //定义一个视图
        View childView = null;

        //布局加载器
        LayoutInflater inflater = LayoutInflater.from(mContext);

        //找到子视图
        childView = inflater.inflate(R.layout.item_newest_article, parent, false);

        //实例化mViewHolder
        AdapterArticle.MineViewHolder mViewHolder = new AdapterArticle.MineViewHolder(childView, listener);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MineViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Glide.with(mContext).load(marticleList.get(position).getTitlePic()).into(holder.article_titlepic);

        holder.article_title.setText(marticleList.get(position).getTitle());

        holder.article_introduction.setText(marticleList.get(position).getIntroduction());

        holder.article_type.setText(marticleList.get(position).getArticleTypeStr());

        holder.article_reading_times.setText(marticleList.get(position).getShowTimes() + "人阅读");

        holder.article_date.setText(Constants.getYYD(marticleList.get(position).getCreateTime()));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (marticleList != null) {
            return marticleList.size();
        }
        return 0;
    }

    class MineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_article_titlepic)
        ImageView article_titlepic;

        @BindView(R.id.tv_article_title)
        TextView article_title;

        @BindView(R.id.tv_article_introduction)
        TextView article_introduction;

        @BindView(R.id.tv_article_type)
        TextView article_type;

        @BindView(R.id.tv_article_reading_times)
        TextView article_reading_times;

        @BindView(R.id.tv_article_date)
        TextView article_date;

        protected RvListener mListener;

        public MineViewHolder(View itemView, RvListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v.getId(), getAdapterPosition());
                }
            });
        }
    }

}
