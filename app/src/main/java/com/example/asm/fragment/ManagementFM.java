package com.example.asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.asm.R;
import com.example.asm.database.entities.Students;
import com.example.asm.databinding.FragmentManagementBinding;


public class ManagementFM extends Fragment implements View.OnClickListener {
    FragmentManagementBinding fmMainBinding;
    NavController navController;
    Animation xToYTo0,downToUp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmMainBinding = FragmentManagementBinding.inflate(getLayoutInflater());
        initNavController(container);
        initButton();
        initAnimation();
        return fmMainBinding.getRoot();
    }

    private void initAnimation() {
        xToYTo0 = AnimationUtils.loadAnimation(requireContext(), R.anim.x_to_y_to0);
        downToUp = AnimationUtils.loadAnimation(requireContext(), R.anim.down_to_up);
        fmMainBinding.tvStudentManagement.setAnimation(xToYTo0);
        fmMainBinding.lavManagement.setAnimation(downToUp);
    }

    private void initNavController(View viewFM3Intro) {
        navController = Navigation.findNavController(viewFM3Intro);

    }

    private void initButton() {
        fmMainBinding.acbClassRoomManager.setOnClickListener(this);
        fmMainBinding.acbStudentManager.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmMainBinding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.acbStudentManager) {
            Students students = new Students();
            NavDirections action = ManagementFMDirections.actionManagementFMToStudentManagerFM(students);
            navController.navigate(action);
        }
        if (id == R.id.acbClassRoomManager) {
            NavDirections action = ManagementFMDirections.actionManagementFMToClassroomManagerFM();
            navController.navigate(action);
        }
    }

}
