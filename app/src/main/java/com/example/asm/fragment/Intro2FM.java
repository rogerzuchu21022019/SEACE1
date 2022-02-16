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
import com.example.asm.databinding.FragmentIntro2Binding;

public class Intro2FM extends Fragment {
    FragmentIntro2Binding fm2Binding;
    Animation leftToRight;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fm2Binding = FragmentIntro2Binding.inflate(getLayoutInflater());
        initNavController(container);
        initAnimation();
        return fm2Binding.getRoot();
    }

    private void initAnimation() {
        leftToRight = AnimationUtils.loadAnimation(requireContext(), R.anim.left_to_right);
        fm2Binding.lavIntro2.setAnimation(leftToRight);
    }

    private void initNavController(View viewFm2Intro) {

//        navController = Navigation.findNavController(viewFm2Intro);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fm2Binding = null;
    }
}
