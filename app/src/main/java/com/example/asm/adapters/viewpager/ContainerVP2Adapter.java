package com.example.asm.adapters.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.asm.fragment.Intro1FM;
import com.example.asm.fragment.Intro2FM;
import com.example.asm.fragment.Intro3FM;

public class ContainerVP2Adapter extends FragmentStateAdapter {


    public ContainerVP2Adapter(@NonNull Fragment fragment) {
        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new Intro1FM();
        } else if (position == 1) {
            return new Intro2FM();
        } else if (position == 2) {
            return new Intro3FM();
        }
            return new Intro1FM();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
