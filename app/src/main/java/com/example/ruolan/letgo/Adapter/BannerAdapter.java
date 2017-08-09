package com.example.ruolan.letgo.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwen on 2017/7/11.
 * 头部banner 适配器
 */
public class BannerAdapter extends PagerAdapter {

    private int mSize;
    private Activity mActivity;
    private float mImageCorner = -1F;
    private List<BookModel> mList = new ArrayList<>();
    private List<String> mPicList = new ArrayList<>();

    public BannerAdapter(Activity activity, List<BookModel> list, List<String> picList) {
        mActivity = activity;
        mList = list;
        mPicList = picList;
    }

    public void updateList(List<BookModel> list) {
        if (list.size() != 0) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    public void updateListPic(List<String> picList) {
        if (picList.size() != 0) {
            mPicList = picList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mActivity.getApplicationContext()).inflate(R.layout.recommend_page_item, container, false);
        if (mPicList.size() != 0 || mList.size() != 0) {
            BookModel model = mList.get(position);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            TextView textView = (TextView) view.findViewById(R.id.image_desc);
            textView.setText(model.getBooKName());
            if(mPicList.size() != 0 && mPicList.size() == mList.size()){
                GlideUtils.loadImage(imageView, "http:" + mPicList.get(position), R.mipmap.default_book, R.mipmap.default_book);
                Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Bitmap newimage = getRoundCornerImage(image, 50);
                ImageView imageView2 = new ImageView(view.getContext());
                imageView2.setImageBitmap(newimage);
                container.addView(view);
                return view;
            }

        }
        return view;
    }


    public Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels) {
        Bitmap roundConcerImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundConcerImage);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, rect, paint);
        return roundConcerImage;
    }
}
