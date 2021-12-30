package com.example.crud;

public class UserModel {
    String _id, username, emailid, password, confirm, msg;

    //CONSTRUCTOR FOR REGRISTRATION DETAILS
    public UserModel(String username, String emailid, String password, String confirm) {
        this.username = username;
        this.emailid = emailid;
        this.password = password;
        this.confirm = confirm;
    }

    public UserModel(String emailid, String password) {
        this.emailid = emailid;
        this.password = password;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
}
