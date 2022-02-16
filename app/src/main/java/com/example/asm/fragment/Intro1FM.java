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

import com.example.asm.R;
import com.example.asm.databinding.FragmentIntro1Binding;

public class Intro1FM extends Fragment {
    FragmentIntro1Binding fm1Binding;
    Animation downToUp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fm1Binding = FragmentIntro1Binding.inflate(getLayoutInflater());
        initNavController(container);
        initAnimation();
        return fm1Binding.getRoot();
    }

    private void initAnimation() {
        downToUp = AnimationUtils.loadAnimation(requireContext(), R.anim.down_to_up);
        fm1Binding.lavIntro1.setAnimation(downToUp);
    }

    private void initNavController(View viewFMIntro1) {
//        navController = Navigation.findNavController(viewFMIntro1);
    }



//     For issue when crash memory leaks
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fm1Binding =null;
    }
}
