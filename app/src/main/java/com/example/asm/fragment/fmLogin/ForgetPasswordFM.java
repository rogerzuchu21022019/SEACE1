package com.example.asm.fragment.fmLogin;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.asm.databinding.FragmentForgetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPasswordFM extends Fragment implements View.OnClickListener {
    FragmentForgetPasswordBinding fmForgetBinding;
    Animation leftToRight,rightToLeft,downToUp;
    NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmForgetBinding = FragmentForgetPasswordBinding.inflate(getLayoutInflater());
        initAnimation();
        initNavController(container);
        initButton();
        hideKeyBoard();
        return fmForgetBinding.getRoot();
    }

    private void initAnimation() {
        leftToRight = AnimationUtils.loadAnimation(requireContext(),R.anim.left_to_right);
        rightToLeft = AnimationUtils.loadAnimation(requireContext(),R.anim.right_to_left);
        downToUp = AnimationUtils.loadAnimation(requireContext(),R.anim.down_to_up);
        fmForgetBinding.tieResetEmail.setAnimation(leftToRight);
        fmForgetBinding.acbReset.setAnimation(rightToLeft);
        fmForgetBinding.lavReset.setAnimation(downToUp);
    }

    private void initNavController(View viewFMRegister) {
        navController = Navigation.findNavController(viewFMRegister);
    }
    private void initButton() {
        fmForgetBinding.acbReset.setOnClickListener(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmForgetBinding = null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id== R.id.acbReset){
            //TODO SOMETHING
            String email =fmForgetBinding.tieResetEmail.getText().toString();
            if (TextUtils.isEmpty(email)){
                Toast.makeText(requireContext(), "Email is not empty", Toast.LENGTH_LONG).show();
                return;
            }
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                NavDirections action = ForgetPasswordFMDirections.actionRegisterFMToLoginFM();
                                navController.navigate(action);
                                Toast.makeText(requireContext(), "Please check your email", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
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
