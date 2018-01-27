package com.kecq.myinfo.model;

import java.util.Date;

/**
 * Created by fyj on 2017/12/16.
 */

public class ArticleModel {

    private int articleID;

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public Date getArticleDatetime() {
        return articleDatetime;
    }

    public void setArticleDatetime(Date articleDatetime) {
        this.articleDatetime = articleDatetime;
    }

    public String getArticleThumbPic() {
        return articleThumbPic;
    }

    public void setArticleThumbPic(String articleThumbPic) {
        this.articleThumbPic = articleThumbPic;
    }

    private String articleTitle;

    private  String articleAuthor;

    private  Date articleDatetime;

    private String articleThumbPic;

    private  String articleUrl;

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getBlogID() {
        return blogID;
    }

    public void setBlogID(String blogID) {
        this.blogID = blogID;
    }

    public  String blogID;
    @Override
    public String toString() {
        return articleTitle;
    }
}
