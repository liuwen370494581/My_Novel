package com.example.ruolan.letgo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.ruolan.letgo.Activity.ClassifyActivity;
import com.example.ruolan.letgo.Activity.RankingActivity;
import com.example.ruolan.letgo.Base.BaseFragment;
import com.example.ruolan.letgo.R;

/**
 * Created by ruolan on 2015/11/29.
 * 精选
 */
public class SelectFragment extends BaseFragment {
    private RelativeLayout reRanking, reClassify;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        reRanking = (RelativeLayout) view.findViewById(R.id.re_ranking);
        reClassify = (RelativeLayout) view.findViewById(R.id.re_classify);
        reRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(RankingActivity.class);
            }
        });
        reClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(ClassifyActivity.class);
            }
        });
        return view;
    }


    @Override
    public void initData() {

    }

}
