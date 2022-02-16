package com.example.asm.fragment.fmCRUD;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.R;
import com.example.asm.adapters.AppClassRoomAdapter;
import com.example.asm.adapters.AppStudentsApdapter;
import com.example.asm.database.entities.ClassRoom;
import com.example.asm.database.entities.Students;
import com.example.asm.databinding.FragmentManagerClassroomBinding;
import com.example.asm.databinding.ItemAlertCustomBinding;
import com.example.asm.databinding.ItemRvClassroomBinding;
import com.example.asm.interfaces.ClickItemInRecyeclerView;
import com.example.asm.reposotories.AppRepository;
import com.example.asm.viewModel.AppClassRoomViewModel;
import com.example.asm.viewModel.AppStudentViewModel;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ClassroomManagerFM extends Fragment implements View.OnClickListener {
    private static final int UPDATE = 1;
    FragmentManagerClassroomBinding fmCrBinding;
    NavController navController;
    AppClassRoomAdapter adapter;
    AppStudentsApdapter studentsApdapter;
    AppClassRoomViewModel viewModel;
    AppStudentViewModel viewModelStudent;
    AppRepository appRepository;
    public volatile ClassRoom classRoom;
    ItemRvClassroomBinding itemRvClassroomBinding;
    ItemAlertCustomBinding itemAlertCustomBinding;
    AlertDialog alert;
    Animation leftToRight, downToUp;
    List<ClassRoom> loadListDataBase;
    ClickItemInRecyeclerView clickItemInRecyeclerView;
    StudentManagerFM studentManagerFM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmCrBinding = FragmentManagerClassroomBinding.inflate(getLayoutInflater());
        initAnimate();
        initNavController(container);
        initButton();
        initViewModel();
        initAppRepository();
        swipeLeftToDelete();
        swipeRightToEdit();
        showDataFromDataBase();
        return fmCrBinding.getRoot();
    }

    private void initAnimate() {
        leftToRight = AnimationUtils.loadAnimation(requireContext(), R.anim.left_to_right);
        downToUp = AnimationUtils.loadAnimation(requireContext(), R.anim.down_to_up);
        fmCrBinding.lavTitleClassRoomManagement.setAnimation(leftToRight);
        fmCrBinding.tvStudentManagement.setAnimation(downToUp);
    }

    private void showDataFromDataBase() {
        loadListDataBase = appRepository.getAllInformationClassRoom();
        initRecycleView(loadListDataBase);
    }

    private void initAppRepository() {
        appRepository = new AppRepository(requireParentFragment().requireActivity().getApplication());
    }

    private void initNavController(View viewFM3Intro) {
        navController = Navigation.findNavController(viewFM3Intro);
    }

    public void initRecycleView(List<ClassRoom> classRoomList) {
        setListenerWhenClickOnItemInRecycleView();
        adapter = new AppClassRoomAdapter(classRoomList, clickItemInRecyeclerView);
        fmCrBinding.rvClassRoom.setHasFixedSize(true);
        fmCrBinding.rvClassRoom.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        fmCrBinding.rvClassRoom.setAdapter(adapter);
        itemRvClassroomBinding = ItemRvClassroomBinding.inflate(getLayoutInflater());
        fmCrBinding.rvClassRoom.setAnimation(downToUp);
    }

    private void initButton() {
        fmCrBinding.lavAdd.setOnClickListener(this);
        fmCrBinding.lavDeleteAll.setOnClickListener(this);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AppClassRoomViewModel.class);
        viewModel.getAllInformationClassRoomWithObserver().observe(requireActivity(), new Observer<List<ClassRoom>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<ClassRoom> classRooms) {
            }
        });
        viewModelStudent = new ViewModelProvider(this).get(AppStudentViewModel.class);
        viewModelStudent.useForObserverLiveBetweenUIAndDatabase().observe(requireActivity(), new Observer<List<Students>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Students> students) {
                studentManagerFM = new StudentManagerFM();
                studentsApdapter = new AppStudentsApdapter(studentManagerFM.studentsList, clickItemInRecyeclerView);
                studentsApdapter.setStudentsListForAppStudentsAdapter(studentManagerFM.studentsList);
                studentManagerFM.appStudentsApdapter.notifyDataSetChanged();
            }
        });
    }

    private void setListenerWhenClickOnItemInRecycleView() {
        clickItemInRecyeclerView = (view, position) -> {
            String classID = adapter.getClassRoomAtPosition(position).classID;
            String className = adapter.getClassRoomAtPosition(position).className;
            int iCID = adapter.getClassRoomAtPosition(position).iCID;
            Toast.makeText(requireContext(), "" + classID, Toast.LENGTH_SHORT).show();
            Toast.makeText(requireContext(), "" + className, Toast.LENGTH_SHORT).show();
            Toast.makeText(requireContext(), "" + iCID, Toast.LENGTH_SHORT).show();
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmCrBinding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.lavAdd) {
            createCustomAlert();
        }

        if (id == R.id.acbOK) {
            String classID = itemAlertCustomBinding.tieClassID.getText().toString();
            String className = itemAlertCustomBinding.tieClassName.getText().toString();
            if (TextUtils.isEmpty(classID) || TextUtils.isEmpty(className)) {
                Toast.makeText(requireContext(), "Please press input to finish\n insert or click Cancel", Toast.LENGTH_SHORT).show();
                return;
            }
            classRoom = new ClassRoom(className, classID);
            viewModel.insertClassRoom(classRoom);
            loadListDataBase.add(classRoom);
            alert.dismiss();
            showDataFromDataBase();
        }


        if (id == R.id.acbCancel) {
            alert.dismiss();
            showDataFromDataBase();
        }

        if (id == R.id.lavDeleteAll) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
            alertDialog.setTitle("Are you sure?");
            alertDialog.setMessage("If you choice OK, You will DELETE ALL CLASSROOMS and ALL INFORMATIONS of it");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    viewModel.deleteAllFromList();
                    showDataFromDataBase();
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alert = alertDialog.create();
            alert.show();
        }
    }


    public void createCustomAlert() {
        AlertDialog.Builder alertCustom = new AlertDialog.Builder(requireContext());
        itemAlertCustomBinding = ItemAlertCustomBinding.inflate(getLayoutInflater());
        alertCustom.setView(itemAlertCustomBinding.getRoot());
        alert = alertCustom.create();
        alert.show();
        itemAlertCustomBinding.acbCancel.setOnClickListener(this);
        itemAlertCustomBinding.acbOK.setOnClickListener(this);
    }

    private void swipeLeftToDelete() {
        ItemTouchHelper.Callback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                classRoom = adapter.getClassRoomAtPosition(position);
                int iCID = adapter.getClassRoomAtPosition(position).iCID;
                studentManagerFM = new StudentManagerFM();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
                alertDialog.setTitle("Are you sure to delete?");
                alertDialog.setMessage(" If you choice YES, programing will DELETE ALL INFORMATIONS OF CLASSROOM");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (loadListDataBase != null) {
                            if (studentManagerFM.studentsList != null) {
                                viewModelStudent.deleteStudentsByID(iCID);
                            }
                            viewModel.deleteClassRoomByID(iCID);
                            alert.dismiss();
                            showDataFromDataBase();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showDataFromDataBase();
                    }
                });
                alert = alertDialog.create();
                alert.show();


            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView
                    recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                        boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(R.color.design_default_color_error)
                        .addSwipeLeftActionIcon(R.raw.delele)
                        .addSwipeLeftLabel("DELETE")
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };
        new

                ItemTouchHelper(simpleCallback).

                attachToRecyclerView(fmCrBinding.rvClassRoom);

    }

    public void intentfilterDeleteData() {
    }

    private void swipeRightToEdit() {
        ItemTouchHelper.Callback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                createCustomAlert();
                itemAlertCustomBinding.acbOK.setText(R.string.update);
                itemAlertCustomBinding.acbOK.setId(UPDATE);
                String classID = adapter.getClassRoomAtPosition(position).classID;
                String className = adapter.getClassRoomAtPosition(position).className;
                if (TextUtils.isEmpty(classID) || TextUtils.isEmpty(className)) {
                    Toast.makeText(requireContext(), "Please press input", Toast.LENGTH_SHORT).show();
                    return;
                }
                itemAlertCustomBinding.tieClassID.setText(classID);
                itemAlertCustomBinding.tieClassName.setText(className);
                itemAlertCustomBinding.acbOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String classID = itemAlertCustomBinding.tieClassID.getText().toString();
                        String className = itemAlertCustomBinding.tieClassName.getText().toString();
                        if (TextUtils.isEmpty(classID) || TextUtils.isEmpty(className)) {
                            Toast.makeText(requireContext(), "Please press input to finish update\n or click Cancel", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        adapter.getClassRoomAtPosition(position).classID = classID;
                        adapter.getClassRoomAtPosition(position).className = className;
                        viewModel.updateClassRoom(adapter.getClassRoomAtPosition(position));
//                        adapter.notifyDataSetChanged();
                        alert.dismiss();
                        showDataFromDataBase();
                    }
                });
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                        .addSwipeRightActionIcon(R.raw.delele)
                        .addSwipeRightLabel("EDIT")
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(fmCrBinding.rvClassRoom);
    }


}
