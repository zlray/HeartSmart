package com.xqlh.heartsmart.ui.home.model;

/**
 * Created by Administrator on 2018/4/20.
 */

public class ViewBeautifulArticleModel  {

    private String articlePic; //图片url
    private String title; //title
    private String readingNumber; //阅读数量

    public ViewBeautifulArticleModel(String articlePic, String title, String readingNumber) {
        this.articlePic = articlePic;
        this.title = title;
        this.readingNumber = readingNumber;
    }

    public String getArticlePic() {
        return articlePic;
    }

    public void setArticlePic(String articlePic) {
        this.articlePic = articlePic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReadingNumber() {
        return readingNumber;
    }

    public void setReadingNumber(String readingNumber) {
        this.readingNumber = readingNumber;
    }
}
