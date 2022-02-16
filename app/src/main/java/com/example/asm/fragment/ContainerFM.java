package com.example.asm.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asm.R;
import com.example.asm.activities.DrawerLayoutActivity;
import com.example.asm.adapters.viewpager.ContainerVP2Adapter;
import com.example.asm.databinding.FragmentContainerBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.relex.circleindicator.CircleIndicator3;

public class ContainerFM extends Fragment {

    private static final int FRAGMENT_INTRO1 = 0;
    private static final int FRAGMENT_INTRO2 = 1;
    private static final int FRAGMENT_INTRO3 = 2;

    private int mFragmentCurrent = FRAGMENT_INTRO1;
    FragmentContainerBinding fmContainerBinding;
    NavController navController;
    ContainerVP2Adapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fmContainerBinding = FragmentContainerBinding.inflate(getLayoutInflater());
        initViewPager2(container);
        initNavController(container);
        hideKeyBoard();
        return fmContainerBinding.getRoot();
    }


    private void initViewPager2(View view) {
        adapter = new ContainerVP2Adapter(this);
        fmContainerBinding.viewPager2.setAdapter(adapter);
        fmContainerBinding.viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        CircleIndicator3 indicator3 = fmContainerBinding.ci3;
        indicator3.setViewPager(fmContainerBinding.viewPager2);
        fmContainerBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 1:
                        mFragmentCurrent = FRAGMENT_INTRO2;
                        openIntro2FM();
                        break;
                    case 2:
                        mFragmentCurrent = FRAGMENT_INTRO3;
                        openIntro3FM();
                        if (fmContainerBinding.viewPager2.getCurrentItem() == 2) {
                            View a = view.findViewById(R.id.mBtnStart);
                            a.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    registerUser();
                                    if (isConnectInternetAndChangeNetwork()) {
                                        Snackbar snackbar = Snackbar.make(requireView(), "Internet is conneted", 5000);
                                        snackbar.show();
                                        isConnectInternet();
                                    } else {
                                        Snackbar snackbar = Snackbar.make(requireView(), "Internet is not connect", 5000);
                                        snackbar.show();
                                    }
                                }
                            });
                        }
                        break;
                    default:
                        mFragmentCurrent = FRAGMENT_INTRO1;
                        openIntro1FM();
                        break;
                }
            }
        });

    }

    private void initNavController(View viewContainerFM) {
        navController = Navigation.findNavController(viewContainerFM);
    }

    private void registerUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            //TODO SOMETHING
            NavDirections action = ContainerFMDirections.actionContainerFMToLoginFM();
            navController.navigate(action);
        } else {
            Intent intent = new Intent(requireActivity(), DrawerLayoutActivity.class);
            startActivity(intent);
        }
    }


    private void isConnectInternet() {
        ConnectivityManager connMgr = (ConnectivityManager)
                requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean iswifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        if (iswifiConn)
            Toast.makeText(requireContext(), "Thiết bị đã kết nối Wifi",Toast.LENGTH_LONG).show();
        if (isMobileConn)
            Toast.makeText(requireContext(), "Thiết bị đã kết nối 3G", Toast.LENGTH_LONG).show();
    }



    private boolean isConnectInternetAndChangeNetwork() {
        ConnectivityManager connection = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connection.getActiveNetworkInfo();

        return (networkInfo!=null&& networkInfo.isConnected());
    }

    //     For issue when crash memory leaks
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fmContainerBinding = null;
    }


    private void openIntro1FM() {
        if (mFragmentCurrent != FRAGMENT_INTRO1) {
            fmContainerBinding.viewPager2.setCurrentItem(0);
            mFragmentCurrent = FRAGMENT_INTRO1;
        }
    }

    private void openIntro2FM() {
        if (mFragmentCurrent != FRAGMENT_INTRO2) {
            fmContainerBinding.viewPager2.setCurrentItem(1);
            mFragmentCurrent = FRAGMENT_INTRO2;
        }
    }

    private void openIntro3FM() {
        if (mFragmentCurrent != FRAGMENT_INTRO3) {
            fmContainerBinding.viewPager2.setCurrentItem(2);
            mFragmentCurrent = FRAGMENT_INTRO3;
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
