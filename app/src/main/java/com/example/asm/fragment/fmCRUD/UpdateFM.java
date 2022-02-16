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
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.asm.R;
import com.example.asm.adapters.AppStudentsApdapter;
import com.example.asm.database.entities.Students;
import com.example.asm.databinding.FragmentUpdateBinding;
import com.example.asm.interfaces.ClickItemInRecyeclerView;
import com.example.asm.reposotories.AppRepository;
import com.example.asm.viewModel.AppStudentViewModel;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

public class UpdateFM extends Fragment implements View.OnClickListener {
    FragmentUpdateBinding fragmentUpdateBinding;
    NavController navController;
    Calendar calendar = Calendar.getInstance();
    AppStudentViewModel viewModel;
    AppStudentsApdapter appStudentsApdapter;
    ClickItemInRecyeclerView clickItemInRecyeclerView;
    AppRepository loadDataBase;
    List<Students> studentsList;
    Students students;
    Animation upToDownUpdate,downToUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentUpdateBinding = FragmentUpdateBinding.inflate(getLayoutInflater());
        initButton();
        initNavController(container);
        hideKeyBoard();
        receiverDataFromStudentManager();
        initViewModel();
        showDataBase();
        initAnimation();
        appStudentsApdapter = new AppStudentsApdapter(studentsList, clickItemInRecyeclerView);
        return fragmentUpdateBinding.getRoot();
    }

    private void initAnimation() {
        upToDownUpdate = AnimationUtils.loadAnimation(requireContext(),R.anim.up_to_down_update);
        downToUp = AnimationUtils.loadAnimation(requireContext(),R.anim.down_to_up);
        fragmentUpdateBinding.tilFullName.setAnimation(downToUp);
        fragmentUpdateBinding.tilID.setAnimation(downToUp);
        fragmentUpdateBinding.tilPhone.setAnimation(downToUp);
        fragmentUpdateBinding.tvICID.setAnimation(downToUp);
        fragmentUpdateBinding.acbDate.setAnimation(downToUp);
        fragmentUpdateBinding.lavView.setAnimation(upToDownUpdate);
    }

    private void initNavController(View viewFMIntro1) {
        navController = Navigation.findNavController(viewFMIntro1);
    }

    private void showDataBase() {
        loadDataBase = new AppRepository(requireActivity().getApplication());
        studentsList = loadDataBase.getAllInformationStudent();
    }


    private void initButton() {
        fragmentUpdateBinding.acbDate.setOnClickListener(this);
        fragmentUpdateBinding.lavUpdate.setOnClickListener(this);
    }

    private void initDatePicker() {
        int style = DatePickerDialog.THEME_HOLO_LIGHT;
        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), style, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fragmentUpdateBinding.acbDate.setText("" + dayOfMonth + "/" + (month + 1) + "/" + year);
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
        fragmentUpdateBinding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.acbDate) {
            initDatePicker();
        }
        if (id == R.id.lavUpdate) {
            students.setFullName(fragmentUpdateBinding.tieFullName.getText().toString().trim());
            students.setStudentID(fragmentUpdateBinding.tieID.getText().toString().trim());
            students.setPhoneNumber(fragmentUpdateBinding.tilPhone.getPrefixText().toString().trim()+ fragmentUpdateBinding.tiePhone.getText().toString().trim());
            students.setBirthday(fragmentUpdateBinding.acbDate.getText().toString().trim());
            students.setiCID(Integer.parseInt(fragmentUpdateBinding.tvICID.getText().toString()));
            Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            viewModel.updateItemStudentsInRecycleView(students);
            if (TextUtils.isEmpty(fragmentUpdateBinding.tieFullName.getText().toString().trim()) ||
                    TextUtils.isEmpty(fragmentUpdateBinding.tiePhone.getText().toString().trim()) ||
                    TextUtils.isEmpty(fragmentUpdateBinding.tieID.getText().toString().trim()) ||
                    TextUtils.isEmpty(fragmentUpdateBinding.acbDate.getText().toString().trim()) ||
                    TextUtils.isEmpty(fragmentUpdateBinding.tvICID.getText().toString().trim())) {
                Toast.makeText(requireContext(), "Please press input and click OK to finish", Toast.LENGTH_SHORT).show();
                return;
            }
            NavDirections action = UpdateFMDirections.actionUpdateFMToStudentManagerFM(students);
            navController.navigate(action);
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


    public void receiverDataFromStudentManager() {
        students = UpdateFMArgs.fromBundle(getArguments()).getStudents();
        String name = UpdateFMArgs.fromBundle(getArguments()).getStudents().fullName;
        String phone = UpdateFMArgs.fromBundle(getArguments()).getStudents().phoneNumber;
        String studentID = UpdateFMArgs.fromBundle(getArguments()).getStudents().studentID;
        String birthDay = UpdateFMArgs.fromBundle(getArguments()).getStudents().birthday;
        int iCID = UpdateFMArgs.fromBundle(getArguments()).getStudents().iCID;

        fragmentUpdateBinding.tieFullName.setText(name);
        fragmentUpdateBinding.tiePhone.setText(phone.substring(3));
        fragmentUpdateBinding.tieID.setText(studentID);
        fragmentUpdateBinding.acbDate.setVisibility(View.VISIBLE);
        fragmentUpdateBinding.acbDate.setText(birthDay);
        fragmentUpdateBinding.tvICID.setText(String.valueOf(iCID));
    }

    public void hideKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
            inputMethodManager.getCurrentInputMethodSubtype();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


}
