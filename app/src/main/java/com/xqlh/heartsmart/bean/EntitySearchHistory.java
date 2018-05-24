package com.xqlh.heartsmart.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/3 .
 */
public class EntitySearchHistory implements MultiItemEntity {

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_HISTORY = 2;

    //多布局的类型
    public int itemtype;
    //
    private String channelName;

    @Override
    public int getItemType() {
        return itemtype;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

}
