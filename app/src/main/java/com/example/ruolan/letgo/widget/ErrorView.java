package com.example.ruolan.letgo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ruolan.letgo.R;

/**
 * Created by liuwen on 2017/8/9.
 */
public class ErrorView extends LinearLayout {
    private Context mContext;
    private ImageView imgRefresh;
    private IRefreshListener listener;

    public ErrorView(Context context) {
        super(context);
        initView(context);

    }


    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.layout_error_view, this, true);
        imgRefresh = (ImageView) findViewById(R.id.image_reflash);
        imgRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onRefresh();
                }

            }
        });
    }

    public void setRefreshListener(IRefreshListener listener) {
        this.listener = listener;
    }

    public interface IRefreshListener {
        void onRefresh();
    }
}
