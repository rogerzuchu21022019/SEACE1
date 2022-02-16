package com.example.asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm.databinding.FragmentProfileBinding;


public class ProfileFM extends Fragment {
    FragmentProfileBinding fragmentProfileBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentProfileBinding = FragmentProfileBinding.inflate(getLayoutInflater());
        return fragmentProfileBinding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentProfileBinding = null;
    }
}