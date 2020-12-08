package com.huang.vitalitycampus.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "student")
public class Student {

    @ColumnInfo(name = "s_id")
    @PrimaryKey(autoGenerate = true)
    long id = 0;
    @ColumnInfo(name="s_name")
    String name;
    @ColumnInfo(name="s_account")
    String account;
    @ColumnInfo(name="s_password")
    String password;
    @ColumnInfo(name="s_telephone")
    String telephone;
    @ColumnInfo(name="s_icon")
    String headPic;
    @ColumnInfo(name="s_status")
    int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
