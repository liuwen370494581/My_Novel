package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.R;

/**
 * Created by liuwen on 2017/6/28.
 */
public class MonthFragment extends BaseFragment {

    private TextView mTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        return view;
    }

    @Override
    public void initData() {
        Toast.makeText(getActivity(), "加载了月", Toast.LENGTH_SHORT).show();
        // mTextView.setText("月");
    }
}
