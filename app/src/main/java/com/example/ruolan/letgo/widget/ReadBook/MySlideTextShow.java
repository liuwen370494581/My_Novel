package com.example.ruolan.letgo.widget.ReadBook;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.BackgroundSource;
import com.example.ruolan.letgo.Utils.SetupShared;

/**
 * Created by liuwen on 2017/8/11.
 */
public class MySlideTextShow implements GestureDetector.OnGestureListener {
    private Activity activity;
    private ViewFlipper viewFlipper;
    private TextView tvFreeView;
    private TextView tvBusyView;
    private View itemFreeView;
    private View itemBusyView;


    public MySlideTextShow(Activity activity) {
        this.activity = activity;
        viewFlipper = (ViewFlipper) activity.findViewById(R.id.viewflipper);
        LayoutInflater inflater = LayoutInflater.from(activity);
        for (int i = 0; i < 2; i++) {
            View view = inflater.inflate(R.layout.item_read_book, null);
            TextView mtv = (TextView) view.findViewById(R.id.tvTextShow);
            if (0 == i) {
                tvFreeView = mtv;
                itemFreeView = view;
            } else {
                tvBusyView = mtv;
                itemBusyView = view;
            }
            viewFlipper.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

    }

    public void init() {
        int textSize = SetupShared.getFontSize();
        int textColor = SetupShared.getFontColor();
        int backgroundNum = SetupShared.getBackgroundNum();
        int screenLight = SetupShared.getSeenLight();
        int pageEffect = SetupShared.getEffectNum();

        tvBusyView.setTextSize(textSize);
        tvBusyView.setTextColor(textColor);
        tvFreeView.setTextSize(textSize);
        tvFreeView.setTextColor(textColor);

        itemBusyView
                .setBackgroundResource(BackgroundSource.background[backgroundNum]);
        itemFreeView
                .setBackgroundResource(BackgroundSource.background[backgroundNum]);

        WindowManager.LayoutParams wl = activity.getWindow().getAttributes();
        wl.screenBrightness = screenLight;
        activity.getWindow().setAttributes(wl);
    }

    public void loadBook(String filePath){

    }

    @Override
    public boolean onDown(MotionEvent event) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent event, MotionEvent event1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {

    }

    @Override
    public boolean onFling(MotionEvent event, MotionEvent event1, float v, float v1) {
        return false;
    }
}
