package com.example.asm.fragment.fmCRUD;


import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.R;
import com.example.asm.adapters.AppStudentsApdapter;
import com.example.asm.database.entities.Students;
import com.example.asm.databinding.FragmentManagerStudentBinding;
import com.example.asm.databinding.FragmentUpdateBinding;
import com.example.asm.interfaces.ClickItemInRecyeclerView;
import com.example.asm.reposotories.AppRepository;
import com.example.asm.viewModel.AppStudentViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class StudentManagerFM extends Fragment implements View.OnClickListener {
    FragmentManagerStudentBinding fmStudentBinding;
    NavController navController;
    FragmentUpdateBinding fmUpdateBinding;
    AppStudentsApdapter appStudentsApdapter;
    AppRepository loadDataFromDataBase;
    List<Students> studentsList = new ArrayList<>();
    AppStudentViewModel viewModel;
    ClickItemInRecyeclerView clickItem;
    Animation upToDownStuManager, downToUp, leftToRight, rightToLeft;
    public volatile Students students = new Students();
    List<Students> loadData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmStudentBinding = FragmentManagerStudentBinding.inflate(getLayoutInflater());
        initNavController(container);
        initButton();
        initRecycleView(studentsList);
        initViewModel();
        initAppRepository();
        swipeLeftToDelete();
        swipeRightToUpdate();
        initAnimation();
        showDataFromDataBase();
        return fmStudentBinding.getRoot();
    }


    private void initAnimation() {
        upToDownStuManager = AnimationUtils.loadAnimation(requireContext(), R.anim.up_to_down_student_manager);
        downToUp = AnimationUtils.loadAnimation(requireContext(), R.anim.down_to_up);
        leftToRight = AnimationUtils.loadAnimation(requireContext(), R.anim.left_to_right);
        rightToLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.right_to_left);
        fmStudentBinding.lavStudentManagement.setAnimation(downToUp);
        fmStudentBinding.tvTitleStudent.setAnimation(upToDownStuManager);
        fmStudentBinding.rvStudent.setAnimation(rightToLeft);
    }



    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AppStudentViewModel.class);
        viewModel.useForObserverLiveBetweenUIAndDatabase().observe(requireActivity(), new Observer<List<Students>>() {
            @Override
            public void onChanged(List<Students> students) {
                appStudentsApdapter.setStudentsListForAppStudentsAdapter(students);
            }
        });
    }


    public synchronized void showDataFromDataBase() {
        loadData = loadDataFromDataBase.getAllInformationStudent();
        initRecycleView(loadData);
    }

    private void initAppRepository(){
        loadDataFromDataBase = new AppRepository(requireActivity().getApplication());
    }

    public synchronized void initRecycleView(List<Students> studentsList) {
        setListenerWhenClickOnItemInRecycleView();
        appStudentsApdapter = new AppStudentsApdapter(studentsList, clickItem);
        fmStudentBinding.rvStudent.setHasFixedSize(true);
        fmStudentBinding.rvStudent.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        fmStudentBinding.rvStudent.setAdapter(appStudentsApdapter);
    }

    private synchronized void initNavController(View viewStudent) {
        navController = Navigation.findNavController(viewStudent);
    }

    private void setListenerWhenClickOnItemInRecycleView() {
        clickItem = new ClickItemInRecyeclerView() {
            @Override
            public void onItemClickInRecycleView(View view, int position) {
                Toast.makeText(requireContext(), "" + position, Toast.LENGTH_SHORT).show();

            }
        };
    }

    private void initButton() {
        fmStudentBinding.lavAdd.setOnClickListener(this);
        fmStudentBinding.lavDeleteAll.setOnClickListener(this);
    }
    public void deleteByID(int iCID){
        viewModel.deleteStudentsByID(iCID);
        showDataFromDataBase();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmStudentBinding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lavAdd) {
            Students students = new Students();
            NavDirections action = StudentManagerFMDirections.actionStudentManagerFMToInsertFM(students);
            navController.navigate(action);
        }
        if (id == R.id.lavDeleteAll) {
            viewModel.deleteAllStudentFromListDataBase();
            showDataFromDataBase();
        }
    }


    public synchronized void swipeLeftToDelete() {
        ItemTouchHelper.Callback delete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                students = appStudentsApdapter.getPositionForSwipe(position);
                viewModel.deleteItemStudentsInRecycleView(students);
                showDataFromDataBase();
                appStudentsApdapter.notifyItemRemoved(position);
                appStudentsApdapter.notifyItemRangeInserted(position,studentsList.size());

                Snackbar snackbar = Snackbar.make(requireView(),"Delete", 5000);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        studentsList.add(students);
                        initRecycleView(studentsList);
                        viewModel.insertNewStudentInRecycleView(students);
                        showDataFromDataBase();
                        appStudentsApdapter.notifyItemInserted(position);
                        appStudentsApdapter.notifyItemRangeInserted(position,studentsList.size());
                    }
                });
                snackbar.show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(R.color.design_default_color_error)
                        .addSwipeLeftActionIcon(R.drawable.delete)
                        .addSwipeLeftLabel("DELETE")
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(delete).attachToRecyclerView(fmStudentBinding.rvStudent);
    }

    private synchronized void swipeRightToUpdate() {

        ItemTouchHelper.Callback edit = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                fmUpdateBinding = FragmentUpdateBinding.inflate(getLayoutInflater());
                loadDataFromDataBase = new AppRepository(requireActivity().getApplication());
                studentsList = loadDataFromDataBase.getAllInformationStudent();
                fmUpdateBinding.tieFullName.setText(studentsList.get(position).fullName);
                fmUpdateBinding.tiePhone.setText(studentsList.get(position).phoneNumber);
                fmUpdateBinding.tieID.setText(studentsList.get(position).studentID);
                fmUpdateBinding.tvICID.setText(String.valueOf(studentsList.get(position).iCID));
                fmUpdateBinding.acbDate.setText(studentsList.get(position).birthday);
                NavDirections action = StudentManagerFMDirections.actionStudentManagerFMToUpdateFM(studentsList.get(position));
                navController.navigate(action);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                        .addSwipeRightActionIcon(R.drawable.edit)
                        .addSwipeRightLabel("EDIT")
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(edit).attachToRecyclerView(fmStudentBinding.rvStudent);
    }


}
