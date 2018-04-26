package com.xqlh.heartsmart.ui.home.model;

/**
 * 图标和标题的model
 */

public class IconTitleModel {

    private int iconResource; //图片id
    private String title;
    private String articleTypeID;//


    public IconTitleModel(int iconResource, String title, String articleTypeID) {
        this.iconResource = iconResource;
        this.title = title;
        this.articleTypeID = articleTypeID;
    }

    @Override
    public String toString() {
        return "IconTitleModel{" +
                "iconResource=" + iconResource +
                ", title='" + title + '\'' +
                '}';
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleTypeID() {
        return articleTypeID;
    }

    public void setArticleTypeID(String articleTypeID) {
        this.articleTypeID = articleTypeID;
    }
}
