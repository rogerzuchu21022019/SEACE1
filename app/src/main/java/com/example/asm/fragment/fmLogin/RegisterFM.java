package com.example.asm.fragment.fmLogin;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.asm.R;
import com.example.asm.databinding.FragmentRegisterBinding;
import com.example.asm.fragment.SplashFMDirections;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFM extends Fragment implements View.OnClickListener {
    FragmentRegisterBinding fmRegister;
    NavController navController;
    Animation leftToRight, rightToLeft, downToUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmRegister = FragmentRegisterBinding.inflate(getLayoutInflater());
        initAnimation();
        initNavController(container);
        initButton();
        hideKeyBoard();
        return fmRegister.getRoot();
    }

    private void initAnimation() {
        leftToRight = AnimationUtils.loadAnimation(requireContext(), R.anim.down_to_up);
        rightToLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.right_to_left);
        downToUp = AnimationUtils.loadAnimation(requireContext(), R.anim.down_to_up);
        fmRegister.lavRegister.setAnimation(downToUp);
        fmRegister.tiePassWord.setAnimation(leftToRight);
        fmRegister.tilPressPassword.setAnimation(leftToRight);
        fmRegister.tieEmailRegister.setAnimation(leftToRight);
        fmRegister.tilEmailRegister.setAnimation(rightToLeft);
        fmRegister.tieConfirmPassWord.setAnimation(rightToLeft);
        fmRegister.tilConfirmPassword.setAnimation(leftToRight);
        fmRegister.acbRegister.setAnimation(leftToRight);
    }
    private void initNavController(View viewFMRegister) {
        navController = Navigation.findNavController(viewFMRegister);
    }

    private void initButton() {
        fmRegister.acbRegister.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmRegister = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.acbRegister) {
            clickRegister();
        }

    }

    private void clickRegister() {
        String strEmail = fmRegister.tieEmailRegister.getText().toString().trim();
        String strPassword = fmRegister.tiePassWord.getText().toString().trim();
        String strConfirmPassword = fmRegister.tieConfirmPassWord.getText().toString().trim();

        if (TextUtils.isEmpty(strEmail)||TextUtils.isEmpty(strPassword)||TextUtils.isEmpty(strConfirmPassword)){
            Toast.makeText(requireContext(), "Please press your Information.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (strPassword.equals(strConfirmPassword)) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(strEmail, strConfirmPassword)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                fmRegister.progressBar.setVisibility(View.VISIBLE);
                                Toast.makeText(requireContext(), "Register User Successfully.", Toast.LENGTH_SHORT).show();
                                NavDirections action = RegisterFMDirections.actionRegisterFMToLoginFM();
                                navController.navigate(action);
                            } else {
                                fmRegister.progressBar.setVisibility(View.VISIBLE);
                                initProcessBar();
                                Toast.makeText(requireContext(), "Register failed. Email Existed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(requireContext(), "Confirm Password is correct with Password", Toast.LENGTH_SHORT).show();
            return;
        }

    }
    private void initProcessBar() {
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    fmRegister.progressBar.setVisibility(View.GONE);

                }
            }
        }, 2000);
    }

    public void hideKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
            inputMethodManager.getCurrentInputMethodSubtype();

        } catch (Exception ignored) {

        }
    }
}
