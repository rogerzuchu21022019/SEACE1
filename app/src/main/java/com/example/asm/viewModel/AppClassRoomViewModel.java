package com.example.asm.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.asm.database.entities.ClassRoom;
import com.example.asm.reposotories.AppRepository;

import java.util.List;

public class AppClassRoomViewModel extends AndroidViewModel {
    AppRepository appRepository;
    MutableLiveData<List<ClassRoom>> getClassRoomListMutable;


    public AppClassRoomViewModel(@NonNull Application application) {
        super(application);
        appRepository = new AppRepository(application);
        getClassRoomListMutable = new MutableLiveData<>();
    }

    public MutableLiveData<List<ClassRoom>> getAllInformationClassRoomWithObserver() {
        return getClassRoomListMutable;
    }

    public void getAllInformationClassRoom() {
        List<ClassRoom> classRoomList = appRepository.getAllInformationClassRoom();
        if (classRoomList.size()!= 0) {
            getClassRoomListMutable.postValue(classRoomList);
        }
    }

    public void deleteAllFromList() {
        appRepository.deleteAllFromList();
        getAllInformationClassRoom();
    }

    public void insertClassRoom(ClassRoom... classRoom) {
        appRepository.insertClassRoom(classRoom);
        getAllInformationClassRoom();
    }

    public void updateClassRoom(ClassRoom... classRoom) {
        appRepository.updateClassRoom(classRoom);
        getAllInformationClassRoom();
    }

    public void deleteClassRoom(ClassRoom... classRoom) {
        appRepository.deleteClassRoom(classRoom);
        getAllInformationClassRoom();
    }
    public void deleteClassRoomByID(int iCID){
        appRepository.deleteClassRoomByID(iCID);
        getAllInformationClassRoom();
    }

}
