package com.example.asm.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.database.entities.ClassRoom;
import com.example.asm.databinding.ItemRvClassroomBinding;
import com.example.asm.interfaces.ClickItemInRecyeclerView;

import java.util.List;

public class AppClassRoomAdapter extends RecyclerView.Adapter<AppClassRoomAdapter.AppClassRoomViewHolder> {
    ItemRvClassroomBinding itemRvClassroomBinding;
    List<ClassRoom> classRoomList;
    LayoutInflater layoutInflater;
    public ClassRoom classRoom;
    ClickItemInRecyeclerView clickItemInRecyeclerView;

    public AppClassRoomAdapter(List<ClassRoom> classRoomList,ClickItemInRecyeclerView clickItemInRecyeclerView) {
        this.classRoomList = classRoomList;
        this.clickItemInRecyeclerView = clickItemInRecyeclerView;

    }
    @SuppressLint("NotifyDataSetChanged")
    public void setClassRoomListForClassRoomAdapter(List<ClassRoom> classRoomList){
        this.classRoomList = classRoomList;
        notifyDataSetChanged();
    }
    public ClassRoom getClassRoomAtPosition(int postion){
        return classRoomList.get(postion);
    }
    @NonNull
    @Override
    public AppClassRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        itemRvClassroomBinding = ItemRvClassroomBinding.inflate(layoutInflater,parent,false);
        return new AppClassRoomViewHolder(itemRvClassroomBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppClassRoomViewHolder holder, int position) {
        classRoom = classRoomList.get(position);
        holder.itemRvClassroomBinding.setClassRoom(classRoom);
    }

    @Override
    public int getItemCount() {
        if (classRoomList!=null){
            return classRoomList.size();
        }else{
            return 0;
        }
    }

    public class AppClassRoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemRvClassroomBinding itemRvClassroomBinding;
        public AppClassRoomViewHolder(@NonNull ItemRvClassroomBinding itemRvClassroomBinding) {
            super(itemRvClassroomBinding.getRoot());
            this.itemRvClassroomBinding = itemRvClassroomBinding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickItemInRecyeclerView.onItemClickInRecycleView(view,getAbsoluteAdapterPosition());
        }
    }
}
