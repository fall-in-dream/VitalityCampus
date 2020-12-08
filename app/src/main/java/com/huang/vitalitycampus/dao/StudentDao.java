package com.huang.vitalitycampus.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.huang.vitalitycampus.model.Schedule;
import com.huang.vitalitycampus.model.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    public Long insertStudent(Schedule schedule);

    @Query("select * from student")
    public List<Student> getAllStudent();

    @Query("select * from student where s_account= :account")
    public Student findStudent(String account);

    @Insert
    public long insertStudent(Student student);

    @Query("select * from student where s_account= :studentId and s_password= :password")
    public Student login(String studentId, String password);
}
