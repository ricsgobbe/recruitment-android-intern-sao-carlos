package com.example.eunicekuba.bmiproject;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eunicekuba.bmiproject.fragment.ProgressFragment;
import com.example.eunicekuba.bmiproject.fragment.WebFragment;

/**
 * Created by eu.nicekuba on 09/04/2016.
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RelativeLayout mParentLayout;
    private ObjectAnimator mParentAnimation;
    private RelativeLayout mInputWeight;
    private Button mButtonCalculate;
    private Button mButtonWeb;
    private FrameLayout mFrameContainer;
    private FragmentTransaction mFragmentTransaction;
    private ProgressFragment mProgFrag;
    private WebFragment mWebFragment;
    private EditText mWeight;
    private EditText mHeight;
    private boolean mIsFieldsOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsFieldsOk = true;
        setContentView(R.layout.layout_main_activity);
        mToolbar = (Toolbar) findViewById(R.id.id_main_toolbar);
        mInputWeight = (RelativeLayout) findViewById(R.id.id_input_weight);
        mWeight = (EditText) findViewById(R.id.id_text_weight);
        mHeight = (EditText) findViewById(R.id.id_text_height);
        configButton();
        configButtonWeb();
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
                checkFields();
                if(mIsFieldsOk){
                    configParentAnimation();
                    FragmentManager manager = getSupportFragmentManager();
                    mFragmentTransaction = manager.beginTransaction();
                    mProgFrag = new ProgressFragment();
                    mFragmentTransaction.replace(R.id.id_frag_container, mProgFrag);
                    mFragmentTransaction.addToBackStack(null);
                    mFragmentTransaction.commit();
                    mParentAnimation.start();

                }
            }
        });
    }

    private void configButtonWeb(){
        mButtonWeb = (Button) findViewById(R.id.id_btn_web);
        mButtonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configParentAnimation();
                FragmentManager manager = getSupportFragmentManager();
                mFragmentTransaction = manager.beginTransaction();
                mWebFragment = new WebFragment();
                mFragmentTransaction.replace(R.id.id_frag_container, mWebFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                mParentAnimation.start();
            }
        });
    }

    private void configParentAnimation() {
        mParentLayout = (RelativeLayout) findViewById(R.id.id_relative_child);
        mParentAnimation = ObjectAnimator.ofFloat(mParentLayout, "y", mInputWeight.getHeight(), mToolbar.getHeight() - mParentLayout.getHeight());
        mParentAnimation.setInterpolator(new BounceInterpolator());
        mParentAnimation.setDuration(1000);
        mFrameContainer.setVisibility(View.VISIBLE);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mParentAnimation = ObjectAnimator.ofFloat(mParentLayout, "y", mToolbar.getHeight() - mParentLayout.getHeight(), mInputWeight.getHeight());
        mParentAnimation.setInterpolator(new BounceInterpolator());
        mParentAnimation.setDuration(1000);
        mParentAnimation.start();
        mFrameContainer.setVisibility(View.INVISIBLE);
        if(mWebFragment == null){
            mFragmentTransaction.remove(mProgFrag);
        }else{
            mFragmentTransaction.remove(mWebFragment);
        }

    }


    private void checkFields(){
        mHeight.setError(null);
        mWeight.setError(null);
        mIsFieldsOk = true;

        String height = mHeight.getText().toString();
        String weight = mWeight.getText().toString();

        if(height.contains(".")){
            mHeight.setError("Height in centimeter !");
            mIsFieldsOk = false;
        }
        if(weight.isEmpty()){
            mWeight.setError("Empty field !");
            mIsFieldsOk = false;
        }
        if(height.isEmpty()){
            mHeight.setError("Empty field!");
            mIsFieldsOk = false;
        }
    }

}
