package com.example.asm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.database.entities.Students;
import com.example.asm.databinding.ItemRvStudentsBinding;
import com.example.asm.interfaces.ClickItemInRecyeclerView;

import java.util.List;

public class AppStudentsApdapter extends RecyclerView.Adapter<AppStudentsApdapter.AppStudentsViewHolder> {
    ItemRvStudentsBinding itemRvStudentsBinding;
    List<Students> studentsList;
    public Students students;
    LayoutInflater layoutInflater;
    ClickItemInRecyeclerView clickItem;

    public AppStudentsApdapter(List<Students> studentsList,ClickItemInRecyeclerView clickItem) {
        this.studentsList = studentsList;
        this.clickItem = clickItem;
    }

    public void setStudentsListForAppStudentsAdapter(List<Students> studentsList) {
        this.studentsList = studentsList;
        notifyDataSetChanged();
    }

    public Students getPositionForSwipe(int position) {
        return studentsList.get(position);
    }

    @NonNull
    @Override
    public AppStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        itemRvStudentsBinding = ItemRvStudentsBinding.inflate(layoutInflater,parent,false);
        return new AppStudentsViewHolder(itemRvStudentsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppStudentsViewHolder holder, int position) {
        students = studentsList.get(position);
        holder.bindindMappingAllViewXML();
    }

    @Override
    public int getItemCount() {
        if (studentsList != null) {
            return studentsList.size();
        } else {
            return 0;
        }
    }

    public class AppStudentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemRvStudentsBinding itemRvStudentsBinding;

        public AppStudentsViewHolder(@NonNull ItemRvStudentsBinding itemRvStudentsBinding) {
            super(itemRvStudentsBinding.getRoot());
            this.itemRvStudentsBinding = itemRvStudentsBinding;
            itemView.setOnClickListener(this);
        }

        public void bindindMappingAllViewXML() {
            itemRvStudentsBinding.setStudent(students);
        }

        @Override
        public void onClick(View view) {
            clickItem.onItemClickInRecycleView(view,getAbsoluteAdapterPosition());
        }
    }
}
