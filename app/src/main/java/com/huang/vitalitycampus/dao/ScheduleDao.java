package com.huang.vitalitycampus.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.huang.vitalitycampus.model.Schedule;

@Dao
public interface ScheduleDao {

    @Insert
    public Long insertSchedule(Schedule schedule);

}
