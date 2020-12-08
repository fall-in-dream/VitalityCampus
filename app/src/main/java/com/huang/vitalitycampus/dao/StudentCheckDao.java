package com.huang.vitalitycampus.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.huang.vitalitycampus.model.StudentCheck;

import java.util.List;

@Dao
public interface StudentCheckDao {
    @Query("select * from student_check")
    List<StudentCheck> getAllStudent();

    @Insert
    void createStudent(StudentCheck studentCheck);

    @Query("select * from student_check where sc_num= :studentId")
    StudentCheck findStudent(String studentId);
}
