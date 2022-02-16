package com.example.asm.fragment.fmCRUD;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcherKt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.asm.R;
import com.example.asm.adapters.AppStudentsApdapter;
import com.example.asm.database.entities.ClassRoom;
import com.example.asm.database.entities.Students;
import com.example.asm.databinding.FragmentInsertBinding;
import com.example.asm.reposotories.AppRepository;
import com.example.asm.viewModel.AppStudentViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InsertFM extends Fragment implements View.OnClickListener {
    FragmentInsertBinding fmInsertBinding;
    NavController navController;
    Calendar calendar = Calendar.getInstance();
    String fullName, studentID, phone, birthDay, className, classID;
    AppRepository appRepository;
    List<ClassRoom> classRoomList2;
    AppStudentViewModel viewModel;
Animation leftToRight,rightToLeft,downToUp,upToDown;
    AppStudentsApdapter appStudentsApdapter;
    public int iCID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmInsertBinding = FragmentInsertBinding.inflate(getLayoutInflater());
        initButton();
        initNavController(container);
        hideKeyBoard();
        initAnimation();
        receiverDataFromClassRoomManager();
        initViewModel();
        return fmInsertBinding.getRoot();
    }

    private void initAnimation() {
        leftToRight = AnimationUtils.loadAnimation(requireContext(),R.anim.left_to_right);
        rightToLeft = AnimationUtils.loadAnimation(requireContext(),R.anim.right_to_left);
        upToDown = AnimationUtils.loadAnimation(requireContext(),R.anim.up_to_down_update);
        downToUp = AnimationUtils.loadAnimation(requireContext(),R.anim.down_to_up);
        fmInsertBinding.lavView.setAnimation(downToUp);
        fmInsertBinding.tilAuto.setAnimation(leftToRight);
        fmInsertBinding.tilFullName.setAnimation(rightToLeft);
        fmInsertBinding.tilID.setAnimation(leftToRight);
        fmInsertBinding.tilPhone.setAnimation(rightToLeft);
        fmInsertBinding.tvICID.setAnimation(leftToRight);
        fmInsertBinding.tvICIDDefault.setAnimation(rightToLeft);
        fmInsertBinding.fabPickDate.setAnimation(upToDown);
        fmInsertBinding.acbDate.setAnimation(upToDown);
    }

    private void initNavController(View viewFMIntro1) {
        navController = Navigation.findNavController(viewFMIntro1);

    }


    private void initButton() {
        fmInsertBinding.fabPickDate.setOnClickListener(this);
        fmInsertBinding.acbDate.setOnClickListener(this);
        fmInsertBinding.lavAdd.setOnClickListener(this);
    }

    private void initDatePicker() {
        int style = DatePickerDialog.THEME_HOLO_LIGHT;
        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), style, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fmInsertBinding.acbDate.setText("" + dayOfMonth + "/" + (month + 1) + "/" + year);
                if (TextUtils.isEmpty(fmInsertBinding.acbDate.getText())) {
                    Toast.makeText(requireContext(), "Please pick date and click ok to finish", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        pickerDialog.show();
    }


    // For issue when crash memory leaks
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmInsertBinding = null;
    }


    public void receiverDataFromClassRoomManager() {
        appRepository = new AppRepository(requireActivity().getApplication());
        classRoomList2 = appRepository.getAllInformationClassRoom();
        for (int i = 0; i < classRoomList2.size(); i++) {
            String[] data = new String[classRoomList2.size()];
            for (int j = 0; j < classRoomList2.size(); j++) {
                data[j] = classRoomList2.get(j).className;
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, data);
                fmInsertBinding.autoComplete.setAdapter(arrayAdapter);
                fmInsertBinding.autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        iCID = classRoomList2.get(i).iCID;
                        sendDataToStudentManagement(iCID);
                        fmInsertBinding.tvICID.setText(String.valueOf(iCID));
                    }
                });
            }
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.fabPickDate) {
            fmInsertBinding.fabPickDate.setVisibility(View.GONE);
            fmInsertBinding.acbDate.setVisibility(View.VISIBLE);
            initDatePicker();
        }

        if (id == R.id.acbDate) {
            initDatePicker();
        }
        if (id == R.id.lavAdd) {
            if (sendDataToStudentManagement(iCID) == null) {
                Toast.makeText(requireContext(), "Please press input and click OK to finish", Toast.LENGTH_SHORT).show();
                return;
            }
            NavDirections action = InsertFMDirections.actionInsertFMToStudentManagerFM(sendDataToStudentManagement(iCID));
            navController.navigate(action);
            viewModel.insertNewStudentInRecycleView(sendDataToStudentManagement(iCID));
            receiverDataFromStudentManager();
        }
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AppStudentViewModel.class);
        viewModel.useForObserverLiveBetweenUIAndDatabase().observe(requireActivity(), new Observer<List<Students>>() {
            @Override
            public void onChanged(List<Students> students) {
                appStudentsApdapter.setStudentsListForAppStudentsAdapter(students);
            }
        });
    }

    private Students sendDataToStudentManagement(int iCID) {
        fullName = fmInsertBinding.tieFullName.getText().toString().trim();
        studentID = fmInsertBinding.tieID.getText().toString().trim();
        phone =fmInsertBinding.tilPhone.getPrefixText().toString().trim()+ fmInsertBinding.tiePhone.getText().toString().trim();
        birthDay = fmInsertBinding.acbDate.getText().toString().trim();
        if (TextUtils.isEmpty(fullName) ||
                TextUtils.isEmpty(studentID) ||
                TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(birthDay)) {
            return null;
        }
        Students students = new Students(studentID, birthDay, fullName, phone, iCID);
        return students;
    }


    @SuppressLint("NotifyDataSetChanged")
    private void receiverDataFromStudentManager() {
        fmInsertBinding = FragmentInsertBinding.inflate(getLayoutInflater());
        Toast.makeText(getContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();
        String name = InsertFMArgs.fromBundle(getArguments()).getStudent().fullName;
        String phone = InsertFMArgs.fromBundle(getArguments()).getStudent().phoneNumber;
        String studentID = InsertFMArgs.fromBundle(getArguments()).getStudent().studentID;
        String birthDay = InsertFMArgs.fromBundle(getArguments()).getStudent().birthday;
        int iCID = InsertFMArgs.fromBundle(getArguments()).getStudent().iCID;

        fmInsertBinding.tieFullName.setText(name);
        fmInsertBinding.tiePhone.setText(phone);
        fmInsertBinding.tieID.setText(studentID);
        fmInsertBinding.fabPickDate.setVisibility(View.GONE);
        fmInsertBinding.acbDate.setVisibility(View.VISIBLE);
        fmInsertBinding.acbDate.setText(birthDay);
        fmInsertBinding.tvICID.setText(String.valueOf(iCID));
    }

    public void hideKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
            inputMethodManager.getCurrentInputMethodSubtype();

        } catch (Exception ignored) {

        }
    }


}
