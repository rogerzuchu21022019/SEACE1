package com.example.asm.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.asm.database.entities.Students;
import com.example.asm.reposotories.AppRepository;

import java.util.List;

public class AppStudentViewModel extends AndroidViewModel {
    AppRepository appRepository;
    MutableLiveData<List<Students>> getStudentsList;

    public AppStudentViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
        getStudentsList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Students>> useForObserverLiveBetweenUIAndDatabase() {
        return getStudentsList;
    }

    public void getAllInformationStudent() {
        List<Students> studentsList = appRepository.getAllInformationStudent();
        getStudentsList.postValue(studentsList);
    }

    public void insertNewStudentInRecycleView(Students... students) {
        appRepository.insertNewStudentInRecycleView(students);
    }

    public void insertNewStudentInRecycleView(List<Students> studentsList,int position) {
        appRepository.insertNewStudentInRecycleViewByPosition(studentsList,position);
    }

    public void updateItemStudentsInRecycleView(Students... students) {
        appRepository.updateItemStudentsInRecycleView(students);
    }

    public void deleteItemStudentsInRecycleView(Students... students) {
        appRepository.deleteItemStudentsInRecycleView(students);
    }

    public void deleteAllStudentFromListDataBase() {
        appRepository.deleteAllStudentFromListDataBase();
    }

    public void deleteStudentsByID(int iCID) {
        appRepository.deleteStudentsByICID(iCID);
    }

}
