package com.example.eunicekuba.bmiproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eunicekuba.bmiproject.R;

/**
 * Created by eu.nicekuba on 09/04/2016.
 */
public class ResultFragment extends Fragment{

    private TextView mResult;
    private TextView mWeight;
    private TextView mHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_fragment_result, container, false);
        mResult = (TextView) v.findViewById(R.id.id_txt_result);
        putResultOnTextView();
        return v;
    }

    private void putResultOnTextView() {
        mWeight = (TextView) getActivity().findViewById(R.id.id_text_weight);
        mHeight = (TextView) getActivity().findViewById(R.id.id_text_height);
        Double weight = Double.parseDouble(mWeight.getText().toString());
        Double height = Double.parseDouble(mHeight.getText().toString());
        Double result = weight / (Math.pow(height, 2));
        mResult.setText(result.intValue() +" kg/m²");
    }


}
