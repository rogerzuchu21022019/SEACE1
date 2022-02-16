package com.example.asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm.databinding.FragmentInstructionBinding;


public class InstructionFM extends Fragment {
    FragmentInstructionBinding fmInstructionBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmInstructionBinding = FragmentInstructionBinding.inflate(getLayoutInflater());
        return fmInstructionBinding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmInstructionBinding = null;
    }
}