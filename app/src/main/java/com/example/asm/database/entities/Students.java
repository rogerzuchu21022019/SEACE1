package com.example.asm.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Table Student", foreignKeys = {
        @ForeignKey(entity = ClassRoom.class,
                parentColumns = "iCID",
                childColumns = "iCID",
                onUpdate = ForeignKey.CASCADE,
                onDelete = ForeignKey.CASCADE)
})
public class Students implements Parcelable,Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sSID")
    public int sSID=0;
    @ColumnInfo(name = "Student ID")
    public String studentID;
    @ColumnInfo(name = "Birthday")
    public String birthday;
    @ColumnInfo(name = "Full Name")
    public String fullName;
    @ColumnInfo(name = "Phone Number")
    public String phoneNumber;
    @ColumnInfo(name = "iCID")
    public int iCID;
    public Students() {
    }

    public Students(String studentID, String birthday, String fullName, String phoneNumber, int iCID) {
        this.studentID = studentID;
        this.birthday = birthday;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.iCID = iCID;
    }

    public int getsSID() {
        return sSID;
    }

    public void setsSID(int sSID) {
        this.sSID = sSID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getiCID() {
        return iCID;
    }

    public void setiCID(int iCID) {
        this.iCID = iCID;
    }

    protected Students(Parcel in) {
        sSID = in.readInt();
        studentID = in.readString();
        birthday = in.readString();
        fullName = in.readString();
        phoneNumber = in.readString();
        iCID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sSID);
        dest.writeString(studentID);
        dest.writeString(birthday);
        dest.writeString(fullName);
        dest.writeString(phoneNumber);
        dest.writeInt(iCID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Students> CREATOR = new Creator<Students>() {
        @Override
        public Students createFromParcel(Parcel in) {
            return new Students(in);
        }

        @Override
        public Students[] newArray(int size) {
            return new Students[size];
        }
    };
}
