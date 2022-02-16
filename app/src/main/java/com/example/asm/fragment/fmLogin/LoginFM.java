package com.example.asm.fragment.fmLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.asm.activities.DrawerLayoutActivity;
import com.example.asm.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFM extends Fragment implements View.OnClickListener {
    FragmentLoginBinding fmLogin;
    NavController navController;
    Animation downToUp, upToDown, leftToRight, rightToLeft;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmLogin = FragmentLoginBinding.inflate(getLayoutInflater());
        initAnimation();
        initNavController(container);
        initButton();
        initFBAuth();
        hideKeyBoard();
        return fmLogin.getRoot();
    }

    private void initAnimation() {
        rightToLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.right_to_left);
        leftToRight = AnimationUtils.loadAnimation(requireContext(), R.anim.left_to_right);
        downToUp = AnimationUtils.loadAnimation(requireContext(), R.anim.down_to_up);
        upToDown = AnimationUtils.loadAnimation(requireContext(), R.anim.up_to_down_update);
        fmLogin.imgDelete.setAnimation(downToUp);
        fmLogin.acbLogin.setAnimation(leftToRight);
        fmLogin.tilEmailLogin.setAnimation(rightToLeft);
        fmLogin.tilPassWord.setAnimation(leftToRight);
        fmLogin.tieEmailLogin.setAnimation(leftToRight);
        fmLogin.tiePassWord.setAnimation(rightToLeft);
        fmLogin.lavLogin.setAnimation(downToUp);
    }

    private void initNavController(View viewFMLogin) {
        navController = Navigation.findNavController(viewFMLogin);
    }

    private void initButton() {
        fmLogin.acbLogin.setOnClickListener(this);
        fmLogin.imgDelete.setOnClickListener(this);
        fmLogin.tvForgetPassword.setOnClickListener(this);
        fmLogin.tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imgDelete) {
            fmLogin.tiePassWord.setText("");
        }
        if (id == R.id.acbLogin) {
            initLogin();
        }
        if (id == R.id.tvRegister) {
            NavDirections action = LoginFMDirections.actionLoginFMToRegisterFM();
            navController.navigate(action);
        }
        if (id == R.id.tvForgetPassword) {
            NavDirections action = LoginFMDirections.actionLoginFMToForgotFM();
            navController.navigate(action);
        }
    }

    private void initFBAuth() {
        auth = FirebaseAuth.getInstance();
    }

    private void initLogin() {

        String fbEmail;
        fbEmail = fmLogin.tieEmailLogin.getText().toString().trim();
        String fbPassword;
        fbPassword = fmLogin.tiePassWord.getText().toString().trim();

        if (TextUtils.isEmpty(fbEmail) || TextUtils.isEmpty(fbPassword)) {
            Toast.makeText(requireContext(), "Please press your email and pasword", Toast.LENGTH_SHORT).show();
            return;
        }
        auth = FirebaseAuth.getInstance();
        
        auth.signInWithEmailAndPassword(fbEmail, fbPassword)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(requireContext().getApplicationContext(), "Login Succesfully.", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(requireActivity(),DrawerLayoutActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(requireContext().getApplicationContext(), "Login failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmLogin = null;
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
