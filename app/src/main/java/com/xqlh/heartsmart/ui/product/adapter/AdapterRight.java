package com.xqlh.heartsmart.ui.product.adapter;

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
import com.xqlh.heartsmart.bean.EntityProductCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/9.
 */

public class AdapterRight extends RecyclerView.Adapter<AdapterRight.MineViewHolder> {
    private Context mContext;
    private List<EntityProductCategory.ResultBean> mcategoryList;
    private RvListener listener;

    public AdapterRight(Context context, List<EntityProductCategory.ResultBean> categoryList, RvListener listener) {
        mContext = context;
        mcategoryList = categoryList;
        this.listener = listener;
    }

    @Override
    public MineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //定义一个视图
        View childView = null;

        //布局加载器
        LayoutInflater inflater = LayoutInflater.from(mContext);

        //找到子视图
        childView = inflater.inflate(R.layout.item_right, parent, false);

        //实例化mViewHolder
        AdapterRight.MineViewHolder mViewHolder = new AdapterRight.MineViewHolder(childView, listener);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MineViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Glide.with(mContext).load(mcategoryList.get(position).getMainPic()).into(holder.iv_right);
        Log.i("lz", "onBindViewHolder: " + mcategoryList.get(position).getMainPic());
        Log.i("lz", "onBindViewHolder: " + mcategoryList.get(position).getID());
        holder.tv_right.setText(mcategoryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mcategoryList != null) {
            return mcategoryList.size();
        }
        return 0;
    }

    class MineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_right)
        ImageView iv_right;

        @BindView(R.id.tv_right)
        TextView tv_right;
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
