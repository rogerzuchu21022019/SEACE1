package com.example.asm.fragment;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.asm.activities.DrawerLayoutActivity;
import com.example.asm.databinding.FragmentSplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashFM extends Fragment {
    FragmentSplashBinding fmSplashBinding;
    NavController navController;
    Animation leftToRight,rightToLeft;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmSplashBinding = FragmentSplashBinding.inflate(getLayoutInflater());
        initNavController(container);
        fmSplashBinding.lavImg.setMinAndMaxFrame(20, 40);
        initThreadSplash(container);
        initAnimation();
        return fmSplashBinding.getRoot();
    }

    private void initAnimation() {
        fmSplashBinding.lavRocketUp.animate().translationY(-150).setDuration(3000).setStartDelay(700);
        fmSplashBinding.tvAppASM.animate().translationY(-100).setDuration(3000).setStartDelay(500);
        fmSplashBinding.tvSlogan.animate().translationY(100).setDuration(3000).setStartDelay(1000);
        fmSplashBinding.progressBar.animate().translationY(150).setDuration(2000).setStartDelay(800);
        fmSplashBinding.lavImg1.animate().translationY(200).setDuration(2000).setStartDelay(600);
        fmSplashBinding.lavImg.animate().translationX(2000).setDuration(2000).setStartDelay(2000);
        fmSplashBinding.lavImg2.animate().translationX(-2000).setDuration(2000).setStartDelay(3000);
        leftToRight = AnimationUtils.loadAnimation(requireContext(), R.anim.left_to_right);
        rightToLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.right_to_left);
        fmSplashBinding.lavImg.setAnimation(leftToRight);
        fmSplashBinding.tvSlogan.setAnimation(leftToRight);
        fmSplashBinding.progressBar.setAnimation(leftToRight);
        fmSplashBinding.lavImg1.setAnimation(rightToLeft);
        fmSplashBinding.tvSlogan.setAnimation(rightToLeft);
        fmSplashBinding.progressBar.setAnimation(rightToLeft);
    }

    private void initNavController(View viewSplash) {
        navController = Navigation.findNavController(viewSplash);
    }

    private void initThreadSplash(View viewSplash) {
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    NavDirections action = SplashFMDirections.actionSplashFMToContainerFM();
                    navController.navigate(action);
                }
            }
        }, 4000);

    }


    // For issue when crash memory leaks
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmSplashBinding = null;
    }
}
