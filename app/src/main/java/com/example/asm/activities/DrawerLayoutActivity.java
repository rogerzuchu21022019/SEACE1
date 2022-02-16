package com.example.asm.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.asm.R;
import com.example.asm.databinding.DrawerLayoutActivityBinding;
import com.example.asm.databinding.FragmentBottomSheetBinding;
import com.example.asm.fragment.fmLogin.BottomSheetFM;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class DrawerLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayoutActivityBinding bindingMapping;
    AppBarConfiguration appBarConfiguration;
    DrawerLayout drawerLayout;
    NavigationView navView;
    BottomNavigationView navBot;
    NavController navController;
    TextView tvName, tvEmail;
    ImageView imgAvatar;
    Animation leftToRight,rightToLeft,upToDown,downToUp;
    BottomSheetFM bottomSheetDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDrawerLayout();
        initAnimation();
        initAppBarConfiguration();
        initNavView();
        initBotNav();
        initNavController();
        initNavUI();
        showInformationUserFromFireBaseUser();
        initClick();
    }

    private void initAnimation() {
        leftToRight = AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        rightToLeft = AnimationUtils.loadAnimation(this,R.anim.right_to_left);
        upToDown = AnimationUtils.loadAnimation(this,R.anim.up_to_down);
        downToUp = AnimationUtils.loadAnimation(this,R.anim.down_to_up);
        bindingMapping.appBarLayout.imgList.setAnimation(downToUp);
    }


    private void initDrawerLayout() {
        bindingMapping = DrawerLayoutActivityBinding.inflate(getLayoutInflater());
        setContentView(bindingMapping.getRoot());
        drawerLayout = bindingMapping.drawerLayout;
    }


    private void showInformationUserFromFireBaseUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        initHeaderDrawer();
        String fbName = firebaseUser.getDisplayName();
        String fbEmail = firebaseUser.getEmail();
        Uri strImageUrl = firebaseUser.getPhotoUrl();

        if (fbName == null) {
            tvName.setVisibility(View.GONE);
        } else {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(fbName);
        }
        tvEmail.setText(fbEmail);
        Glide.with(this).load(strImageUrl).error(R.drawable.bg14).into(imgAvatar);
    }

    private void initAppBarConfiguration() {
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.managementFM, R.id.classroomManagerFM, R.id.studentManagerFM,R.id.drawerInstruction,
                R.id.drawerProFile, R.id.drawerLogout)
                .setOpenableLayout(drawerLayout)
                .build();
    }

    private void initNavView() {
        navView = bindingMapping.navView;
        bindingMapping.navView.setItemIconTintList(null);

    }

    private void initBotNav() {
        navBot = bindingMapping.appBarLayout.navBot;
        navBot.setItemIconTintList(null);
    }

    private void initNavController() {
        navController = Navigation.findNavController(this, R.id.fmCVHost);
    }

    private void initNavUI() {
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(navBot, navController);
    }


    private void initClick() {
        bindingMapping.appBarLayout.imgList.setOnClickListener(this);
    }

    private void initHeaderDrawer() {
        tvName = bindingMapping.navView.getHeaderView(0).findViewById(R.id.tvName);
        tvEmail = bindingMapping.navView.getHeaderView(0).findViewById(R.id.tvEmail);
        imgAvatar = bindingMapping.navView.getHeaderView(0).findViewById(R.id.civ);

    }

    @Override
    public boolean onSupportNavigateUp() {
        initNavController();
        return NavigationUI.navigateUp(navController, drawerLayout) ||NavigationUI.navigateUp(navController,appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imgList) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


}