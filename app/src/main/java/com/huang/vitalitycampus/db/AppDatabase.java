package com.huang.vitalitycampus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.huang.vitalitycampus.dao.ScheduleDao;
import com.huang.vitalitycampus.dao.StudentCheckDao;
import com.huang.vitalitycampus.dao.StudentDao;
import com.huang.vitalitycampus.model.Schedule;
import com.huang.vitalitycampus.model.Student;
import com.huang.vitalitycampus.model.StudentCheck;

@Database(version = 1, entities = {Student.class, StudentCheck.class, Schedule.class}, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "app_database";

    public static volatile AppDatabase instance;

    public abstract ScheduleDao getScheduleDao();

    public abstract StudentDao getStudentDao();

    public abstract StudentCheckDao getStudentCheckDao();
    public static synchronized AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {

        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME).addCallback(rdc).build();
    }

    private static final RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            ContentValues cv = new ContentValues();
            cv.put("sc_num", "19302030210");
            db.insert("student_check", SQLiteDatabase.CONFLICT_ROLLBACK, cv);
        }

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            Log.d("aaa", "123456");
        }
    };
}
