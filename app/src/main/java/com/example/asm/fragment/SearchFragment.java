package com.example.asm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asm.databinding.FragmentSearchBinding;


public class SearchFragment extends Fragment {
    FragmentSearchBinding fragmentSearchBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentSearchBinding = FragmentSearchBinding.inflate(getLayoutInflater());
        return fragmentSearchBinding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentSearchBinding = null;
    }
}