package com.kecq.myinfo.model;

/**
 * Created by fyj on 2017/12/18.
 */

public class NoteModel {
    private String noteID; //ID
    private String notePic;//图片地址
    private String noteAddr; //发布地址  手机
    private String noteDate; //发布时间
    private String noteContent; //内容
    private String userID;  //用户ID

    public String getNoteID() {
        return noteID;
    }
    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }
    public String getNotePic() {
        return notePic;
    }
    public void setNotePic(String notePic) {
        this.notePic = notePic;
    }
    public String getNoteAddr() {
        return noteAddr;
    }
    public void setNoteAddr(String noteAddr) {
        this.noteAddr = noteAddr;
    }
    public String getNoteDate() {
        return noteDate;
    }
    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }
    public String getNoteContent() {
        return noteContent;
    }
    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
