package com.xqlh.heartsmart.ui.product.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/9.
 */

public class AdapterLeft extends RecyclerView.Adapter<AdapterLeft.MineViewHolder> {
    private Context mContext;
    private List<EntityCategory> mcategoryList;

    public AdapterLeft(Context context, List<EntityCategory> categoryList) {
        mContext = context;
        mcategoryList = categoryList;
    }

    @Override
    public MineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //定义一个视图
        View childView = null;

        //布局加载器
        LayoutInflater inflater = LayoutInflater.from(mContext);

        //找到子视图
        childView = inflater.inflate(R.layout.item_left, parent, false);

        //实例化mViewHolder
        MineViewHolder mViewHolder = new MineViewHolder(childView);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MineViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.tv_left.setText(mcategoryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mcategoryList != null) {
            return mcategoryList.size();
        }
        return 0;
    }

    class MineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_left)
        TextView tv_left;

        public MineViewHolder(View itemView) {
            super(itemView);
            tv_left = itemView.findViewById(R.id.tv_left);
            ButterKnife.bind(this, itemView);
        }
    }
}
