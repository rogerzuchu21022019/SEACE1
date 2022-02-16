package com.example.asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm.R;
import com.example.asm.databinding.FragmentIntro3Binding;


public class Intro3FM extends Fragment {
    FragmentIntro3Binding fm3Binding;
    Animation rightToLeft;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fm3Binding = FragmentIntro3Binding.inflate(getLayoutInflater());
        initAnimation();
        return fm3Binding.getRoot();
    }

    private void initAnimation() {
        rightToLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.right_to_left);
        fm3Binding.lavIntro3.setAnimation(rightToLeft);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fm3Binding = null;
    }

}
