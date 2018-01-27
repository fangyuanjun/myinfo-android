package com.kecq.myinfo.model;

import java.util.Date;

import fyj.lib.common.DateHelper;

/**
 * Created by fyj on 2017/12/14.
 */

public class ListModel {

    public  ListModel()
    {

    }

    public  ListModel(String title)
    {
        this.title=title;
    }

    private  String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    private  String pic;

    private Object tag;

    public Date getShowtime() {
        if(showtime==null)
        {
            if(showtimeString!=null)
            {
                return DateHelper.strToDateLong(showtimeString);
            }
        }
        return showtime;
    }

    public void setShowtime(Date showtime) {
        this.showtime = showtime;
    }

    private Date showtime;

    public String getShowtimeString() {
        if(showtimeString==null)
        {
            if(showtime!=null)
            {
                return DateHelper.dateToStrLong(showtime);
            }
        }

        return showtimeString;
    }

    public void setShowtimeString(String showtimeString) {
        this.showtimeString = showtimeString;
    }

    private String showtimeString;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    private  String guid;

    private  String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
