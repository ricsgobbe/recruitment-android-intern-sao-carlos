package com.example.eunicekuba.bmiproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eunicekuba.bmiproject.MainActivity;
import com.example.eunicekuba.bmiproject.R;

/**
 * Created by eu.nicekuba on 09/04/2016.
 */
public class ResultFragment extends Fragment {

    private TextView mResult;
    private TextView mWeight;
    private TextView mHeight;
    private TextView mTxtMsg;
    private ImageView mImageResult;
    private Button mButton;
    private  WebFragment frag;
    private Bundle bundle;
    private static final String mNormal = "Normal";
    private static final String mUnderweight = "Underweight";
    private static final String mOverweight = "Overweight";
    private static final String mVryUnderweight = "Very Underwieght";
    private static final String mObese = "Obese";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_fragment_result, container, false);
        mResult = (TextView) v.findViewById(R.id.id_txt_result);
        mTxtMsg = (TextView) v.findViewById(R.id.id_txt_msg_res);
        mImageResult = (ImageView) v.findViewById(R.id.id_img_result);
        mButton = (Button) v.findViewById(R.id.id_web_mv);
        configBtn();
        putResultOnTextView();
        return v;
    }

    private void configBtn() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frag = new WebFragment();
                bundle = new Bundle();
                bundle.putString("URL", "http://www.minhavida.com.br/");
                frag.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.id_frag_container, frag);
                fragmentTransaction.commit();
            }

        });
    }


    private void putResultOnTextView() {
        mWeight = (TextView) getActivity().findViewById(R.id.id_text_weight);
        mHeight = (TextView) getActivity().findViewById(R.id.id_text_height);
        Double weight = Double.parseDouble(mWeight.getText().toString() == "" ? 0+"" : mWeight.getText().toString());
        Double height = Double.parseDouble(mHeight.getText().toString() == "" ? 0+"" : mHeight.getText().toString());
        Double result = weight / (Math.pow(height, 2));
        int value = result.intValue();
        mResult.setText(value + " kg/m²");
        putTextOnMsg(value);
    }

    private void putTextOnMsg(int value) {
        if (value >= 18 && value < 25) {
            mTxtMsg.setText(mNormal);
            mImageResult.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_sentiment_satisfied_white_24dp));
        } else if (value > 25) {
            if (value >= 25 && value < 30) {
                mTxtMsg.setText(mOverweight);
                mImageResult.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_sentiment_neutral_white_24dp));
            } else {
                mTxtMsg.setText(mObese);
                mImageResult.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_sentiment_dissatisfied_white_24dp));
            }
            mButton.setVisibility(View.VISIBLE);
        } else if (value >= 16) {
            mTxtMsg.setText(mUnderweight);
            mImageResult.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_sentiment_neutral_white_24dp));
            mButton.setVisibility(View.VISIBLE);

        }else{
            mTxtMsg.setText(mVryUnderweight);
            mImageResult.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_sentiment_dissatisfied_white_24dp));
            mButton.setVisibility(View.VISIBLE);
        }

    }


}
