package com.kecq.myinfo.model;

/**
 * Created by fyj on 2017/12/23.
 */

public class FavoriteModel {

    private String favoriteID;

    private String favoriteTitle;

    public String getFavoriteID() {
        return favoriteID;
    }

    public void setFavoriteID(String favoriteID) {
        this.favoriteID = favoriteID;
    }

    public String getFavoriteTitle() {
        return favoriteTitle;
    }

    public void setFavoriteTitle(String favoriteTitle) {
        this.favoriteTitle = favoriteTitle;
    }

    public String getFavoriteUrl() {
        return favoriteUrl;
    }

    public void setFavoriteUrl(String favoriteUrl) {
        this.favoriteUrl = favoriteUrl;
    }

    private  String favoriteUrl;
}
