package com.example.asm.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.asm.database.dao.ClassRoomDAO;
import com.example.asm.database.dao.StudentsDAO;
import com.example.asm.database.entities.ClassRoom;
import com.example.asm.database.entities.Students;

@Database(entities = {Students.class, ClassRoom.class},exportSchema = false,version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static final String DB_NAME ="Management";
    public abstract ClassRoomDAO useClassRoomDAO();
    public abstract StudentsDAO useStudentDAO();
    public static volatile AppDataBase instance;
    public static AppDataBase getDataBase(Context context){
        if (instance==null){
            synchronized (AppDataBase.class){
                if (instance==null){
                    instance = Room.databaseBuilder
                            (context.getApplicationContext(),AppDataBase.class,DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
}
