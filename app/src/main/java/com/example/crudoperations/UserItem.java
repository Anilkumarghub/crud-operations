package com.example.crudoperations;

public class UserItem {

    String userID;
    String userName;
    String des;
    String pDate;

    public UserItem() {
    }

    public UserItem(String userID, String userName, String des, String pDate) {
        this.userID = userID;
        this.userName = userName;
        this.des = des;
        this.pDate = pDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }
}
