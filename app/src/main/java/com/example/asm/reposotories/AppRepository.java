package com.example.asm.reposotories;

import android.app.Application;


import com.example.asm.database.AppDataBase;
import com.example.asm.database.dao.ClassRoomDAO;
import com.example.asm.database.dao.StudentsDAO;
import com.example.asm.database.entities.ClassRoom;
import com.example.asm.database.entities.Students;

import java.util.List;

public class AppRepository {
    AppDataBase appDataBase;
    ClassRoomDAO classRoomDAO;
    StudentsDAO studentsDAO;

    public AppRepository(Application application) {
        appDataBase = AppDataBase.getDataBase(application);
        classRoomDAO = appDataBase.useClassRoomDAO();
        studentsDAO = appDataBase.useStudentDAO();
    }

    public List<ClassRoom> getAllInformationClassRoom() {
        return classRoomDAO.getAllInformationClassRoom();
    }

    public void deleteAllFromList() {
        classRoomDAO.deleteAll();
    }

    public void insertClassRoom(ClassRoom... classRoom) {
        classRoomDAO.insertClassRoom(classRoom);
    }

    public void updateClassRoom(ClassRoom... classRoom) {
        classRoomDAO.updateClassRoom(classRoom);
    }

    public void deleteClassRoomByID(int iICD) {
        classRoomDAO.deleteClassRoomByID(iICD);
    }

    public void deleteClassRoom(ClassRoom... classRoom) {
        classRoomDAO.deleteClassRoom(classRoom);
    }

    public List<Students> getAllInformationStudent() {
        return studentsDAO.getAllInformationStudent();
    }

    public void insertNewStudentInRecycleView(Students... students) {
        studentsDAO.insertNewStudentInRecycleView(students);
    }

    public void insertNewStudentInRecycleViewByPosition(List<Students> studentsList,int position) {
        studentsDAO.insertNewStudentInRecycleViewByPosition(studentsList.get(position));
    }

    public void updateItemStudentsInRecycleView(Students... students) {
        studentsDAO.updateItemStudentsInRecycleView(students);
    }

    public void deleteItemStudentsInRecycleView(Students... students) {
        studentsDAO.deleteItemStudentsInRecycleView(students);
    }

    public void deleteAllStudentFromListDataBase() {
        studentsDAO.deleteAllStudentFromListDataBase();
    }

    public void deleteStudentsByICID(int iCID) {
        studentsDAO.deleteStudentByICID(iCID);
    }


}
