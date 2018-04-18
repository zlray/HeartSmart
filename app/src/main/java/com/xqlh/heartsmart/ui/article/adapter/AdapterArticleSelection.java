package com.xqlh.heartsmart.ui.article.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.RvListener;
import com.xqlh.heartsmart.ui.bean.EntityArticleNewest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AdapterArticleSelection extends RecyclerView.Adapter<AdapterArticleSelection.MineViewHolder> {
    private Context mContext;
    private List<EntityArticleNewest.ResultBean> mlist;
    private RvListener listener;

    public AdapterArticleSelection(Context mContext, List<EntityArticleNewest.ResultBean> mlist, RvListener listener) {
        this.mContext = mContext;
        this.mlist = mlist;
        this.listener = listener;
    }

    @Override
    public MineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //定义一个视图
        View childView = null;

        //布局加载器
        LayoutInflater inflater = LayoutInflater.from(mContext);

        //找到子视图
        childView = inflater.inflate(R.layout.item_article_selection_picture, parent, false);

        //实例化mViewHolder
        AdapterArticleSelection.MineViewHolder mViewHolder = new AdapterArticleSelection.MineViewHolder(childView, listener);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MineViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Log.i("lz", "aaaaaaaaaaaa" + mlist.get(position).getTitlePic());

        Glide.with(mContext)
                .load(mlist.get(position).getTitlePic())
                .error(R.drawable.head_default)
                .into(holder.iv_article_selection);
        holder.tv_reading_number.setText(mlist.get(position).getShowTimes() + "人阅读");
        holder.tv_title.setText(mlist.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if (mlist != null) {
            return mlist.size();
        }
        return 0;
    }

    class MineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_article_selection)
        ImageView iv_article_selection;

        @BindView(R.id.tv_reading_number)
        TextView tv_reading_number;

        @BindView(R.id.tv_title)
        TextView tv_title;

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
