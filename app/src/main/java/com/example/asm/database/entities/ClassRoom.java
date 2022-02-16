package com.example.asm.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Table ClassRoom")

public class ClassRoom implements Parcelable,Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "iCID")
    public int iCID=0;
    @ColumnInfo(name = "Class Name")
    public String className;
    @ColumnInfo(name = "Class ID")
    public String classID;

    public ClassRoom() {
    }

    public ClassRoom(String className, String classID) {
        this.className = className;
        this.classID = classID;
    }public ClassRoom(String className) {
        this.className = className;
    }

    public ClassRoom(int iCID, String className, String classID) {
        this.iCID = iCID;
        this.className = className;
        this.classID = classID;
    }

    public int getiCID() {
        return iCID;
    }

    public void setiCID(int iCID) {
        this.iCID = iCID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    protected ClassRoom(Parcel in) {
        iCID = in.readInt();
        className = in.readString();
        classID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iCID);
        dest.writeString(className);
        dest.writeString(classID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ClassRoom> CREATOR = new Creator<ClassRoom>() {
        @Override
        public ClassRoom createFromParcel(Parcel in) {
            return new ClassRoom(in);
        }

        @Override
        public ClassRoom[] newArray(int size) {
            return new ClassRoom[size];
        }
    };
}
