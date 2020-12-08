package com.huang.vitalitycampus.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_check")
public class StudentCheck {
    @ColumnInfo(name = "sc_id")
    @PrimaryKey(autoGenerate = true)
    long id = 0;
    @ColumnInfo(name = "sc_num")
    String number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
