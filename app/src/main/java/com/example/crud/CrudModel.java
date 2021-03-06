package com.example.crud;

public class CrudModel{
    String _id;
    String userid;
    String title;
    String subtitle;
    String description;
    String duedate;
    String msg;

    //CONSTRUCTOR TO POST CRUD DATA INTO API/DB
    public CrudModel(String title, String subtitle, String description, String duedate) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.duedate = duedate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
