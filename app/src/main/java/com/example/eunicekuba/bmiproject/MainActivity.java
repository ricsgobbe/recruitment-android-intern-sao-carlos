package com.example.eunicekuba.bmiproject;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.eunicekuba.bmiproject.fragment.ResultFragment;
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
    private WebFragment mWebFragment;
    private EditText mWeight;
    private EditText mHeight;
    private TextWatcher mWatcher;
    private boolean mIsFieldsOk;
    private boolean mIsbackspaceOk;
    public static boolean isBackButtonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsFieldsOk = true;
        setContentView(R.layout.layout_main_activity);
        mToolbar = (Toolbar) findViewById(R.id.id_main_toolbar);
        mInputWeight = (RelativeLayout) findViewById(R.id.id_input_weight);
        mWeight = (EditText) findViewById(R.id.id_text_weight);
        mHeight = (EditText) findViewById(R.id.id_text_height);
        configHeight();
        configButton();
        configButtonWeb();
        configFragContainer();
        setSupportActionBar(mToolbar);

    }

    private void configHeight() {
        mWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                        mIsbackspaceOk = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String heigth = s.toString();
                if(!heigth.contains(".") && s.length() == 1 && !mIsbackspaceOk){
                    s.append(".");
                    mIsbackspaceOk = true;
                }
            }
        };
        mHeight.addTextChangedListener(mWatcher);
    }


    private void configFragContainer() {
        mFrameContainer = (FrameLayout) findViewById(R.id.id_frag_container);
    }

    private void configButton() {
        mButtonCalculate = (Button) findViewById(R.id.id_btn_calculate);
        mButtonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator circularReveal;
                checkFields();
                configParentAnimation();
                FragmentManager manager = getSupportFragmentManager();
                mFragmentTransaction = manager.beginTransaction();
                if(mIsFieldsOk){
                    ResultFragment resFrag = new ResultFragment();
                    mFragmentTransaction.replace(R.id.id_frag_container, resFrag);
                    mFragmentTransaction.commit();
                    mParentAnimation.start();
                    isBackButtonOk = true;
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        circularReveal = createCircularEffect();
                        circularReveal.start();
                    }

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
                Bundle bundle = new Bundle();
                bundle.putString("URL","https://en.wikipedia.org/wiki/Body_mass_index");
                mWebFragment = new WebFragment();
                mWebFragment.setArguments(bundle);
                mFragmentTransaction.replace(R.id.id_frag_container, mWebFragment);
                isBackButtonOk = true;
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
        if(!isBackButtonOk){
            super.onBackPressed();
        }
        if(!(mFragmentTransaction == null)){
            if(!(mWebFragment == null)){
                mFragmentTransaction.remove(mWebFragment);
            }
            mParentAnimation = ObjectAnimator.ofFloat(mParentLayout, "y", mToolbar.getHeight() - mParentLayout.getHeight(), mInputWeight.getHeight());
            mParentAnimation.setInterpolator(new BounceInterpolator());
            mParentAnimation.setDuration(1000);
            mFrameContainer.setVisibility(View.INVISIBLE);
            mParentAnimation.start();
            isBackButtonOk = false;
        }

    }


    private void checkFields(){
        mHeight.setError(null);
        mWeight.setError(null);
        mIsFieldsOk = true;

        String height = mHeight.getText().toString();
        String weight = mWeight.getText().toString();

        if(weight.isEmpty()){
            mWeight.setError("Empty field !");
            mIsFieldsOk = false;
        }
        if(height.isEmpty()){
            mHeight.setError("Empty field!");
            mIsFieldsOk = false;
        }
    }
    private Animator createCircularEffect() {
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(mFrameContainer, mFrameContainer.getWidth() / 2, mFrameContainer.getHeight() / 2,
                0, mFrameContainer.getWidth());
        circularReveal.setDuration(2000);
        return circularReveal;
    }

}
