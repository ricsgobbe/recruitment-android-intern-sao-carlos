package com.example.eunicekuba.bmiproject.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.eunicekuba.bmiproject.R;

import java.util.Random;


/**
 * Created by eu.nicekuba on 09/04/2016.
 */
public class ProgressFragment extends Fragment {

    private TextView mTxtRandomNumb;
    private FrameLayout mResultContainer;
    private FragmentManager supportFragmentManager;
    private View view;
    private FragmentTransaction fragmentTransaction;
    private Runnable mRunnable;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_fragment, container, false);
        mTxtRandomNumb = (TextView) v.findViewById(R.id.id_random_number);
        view = v.findViewById(R.id.id_background_circular);
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        mResultContainer = (FrameLayout) getActivity().findViewById(R.id.id_frag_container);
        generateNumbers();
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mRunnable);
    }

    private void generateNumbers() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            int counter = 0;
            Integer randNumber;
            Random random = new Random(System.currentTimeMillis());

            @Override
            public void run() {
                if (counter < 100) {
                    randNumber = random.nextInt(99);
                    mTxtRandomNumb.setText(randNumber.toString());
                    counter++;
                    mHandler.postDelayed(this, 1);
                } else {
                    fragmentTransaction.remove(ProgressFragment.this);
                    createCircularEffect();
                    fragmentTransaction.replace(R.id.id_frag_container, new ResultFragment());
                    fragmentTransaction.addToBackStack("ResFrag");
                    fragmentTransaction.commit();
                }
            }
        };
        mHandler.post(mRunnable);
    }

    private void createCircularEffect() {
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(mResultContainer, mResultContainer.getWidth() / 2, mResultContainer.getHeight() / 2,
                0, mResultContainer.getWidth());
        circularReveal.setDuration(2000);
        circularReveal.start();
    }

}
