package com.xqlh.heartsmart.ui.home.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2018/4/19.
 */

public class MultipleArticleHomeEntity implements MultiItemEntity {

    public static final int BANNER = 1;
    public static final int BUTTON = 2;
    public static final int SELECTION = 3;
    public static final int ARTICLE_NEWEST = 4;

    public int type;
    public String content;

    public MultipleArticleHomeEntity(int type) {
        this.type = type;
    }

    public MultipleArticleHomeEntity(int type, String content) {
        this.type = type;
        this.content = content;
    }


    @Override
    public int getItemType() {
        return 0;
    }
}
