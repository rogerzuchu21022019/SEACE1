package com.example.asm.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.asm.database.entities.Students;

import java.util.List;

@Dao
public interface StudentsDAO {
    @Query("select * from `Table Student`")
    List<Students> getAllInformationStudent();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewStudentInRecycleView(Students... students);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewStudentInRecycleViewByPosition(Students... students);

    @Update
    void updateItemStudentsInRecycleView(Students... students);

    @Delete
    void deleteItemStudentsInRecycleView(Students... students);

    @Query("delete from `Table Student`")
    void deleteAllStudentFromListDataBase();

    @Query("delete from `Table Student` where iCID=:iCID")
    void deleteStudentByICID(int iCID);

}
