package com.example.eunicekuba.bmiproject;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.eunicekuba.bmiproject.fragment.ProgressFragment;

/**
 * Created by eu.nicekuba on 09/04/2016.
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RelativeLayout mParentLayout;
    private ObjectAnimator mParentAnimation;
    private TextInputLayout mInputWeight;
    private Button mButtonCalculate;
    private FrameLayout mFrameContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        mToolbar = (Toolbar) findViewById(R.id.id_main_toolbar);
        mInputWeight = (TextInputLayout) findViewById(R.id.id_input_weight);
        configButton();
        configFragContainer();
        setSupportActionBar(mToolbar);

    }

    private void configFragContainer() {
        mFrameContainer = (FrameLayout) findViewById(R.id.id_frag_container);
    }

    private void configButton() {
        mButtonCalculate = (Button) findViewById(R.id.id_btn_calculate);
        mButtonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configParentAnimation();
                mParentAnimation.start();
            }
        });
    }

    private void configParentAnimation() {
        mParentLayout = (RelativeLayout) findViewById(R.id.id_relative_child);
        mParentAnimation = ObjectAnimator.ofFloat(mParentLayout, "y", mInputWeight.getHeight(), mToolbar.getHeight()- mParentLayout.getHeight());
        mParentAnimation.setInterpolator(new BounceInterpolator());
        mParentAnimation.setDuration(1000);
        mFrameContainer.setVisibility(View.VISIBLE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.id_frag_container, new ProgressFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mParentAnimation = ObjectAnimator.ofFloat(mParentLayout, "y", mParentLayout.getHeight() + mToolbar.getHeight(), mInputWeight.getHeight());
        mParentAnimation.setInterpolator(new BounceInterpolator());
        mParentAnimation.setDuration(1000);
        mParentAnimation.start();
    }
}
