package com.example.asm.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.asm.database.entities.ClassRoom;

import java.util.List;

@Dao
public interface ClassRoomDAO {
    @Query("select * from `Table ClassRoom`")
    List<ClassRoom> getAllInformationClassRoom();

    @Query("delete from `Table ClassRoom`")
    void deleteAll();

    @Query("delete from `Table ClassRoom` where iCID =:iCID")
    void deleteClassRoomByID(int iCID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClassRoom(ClassRoom... classRoom);

    @Update
    void updateClassRoom(ClassRoom... classRoom);

    @Delete
    void deleteClassRoom(ClassRoom... classRoom);

}
