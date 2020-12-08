package com.huang.vitalitycampus.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedule")
public class Schedule {
    @ColumnInfo(name = "sc_id")
    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo(name = "s_id")
    long stuId;
    @ColumnInfo(name = "sc_name")
    String scheduleName;
    @ColumnInfo(name = "sc_time")
    String time;
    @ColumnInfo(name = "sc_location")
    String location;
    @ColumnInfo(name = "sc_teacher")
    String teacherName;
    @ColumnInfo(name = "sc_start_week")
    int startWeek;
    @ColumnInfo(name = "sc_end_week")
    int endWeek;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStuId() {
        return stuId;
    }

    public void setStuId(long stuId) {
        this.stuId = stuId;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        location = location;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }
}
