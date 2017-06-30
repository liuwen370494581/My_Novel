package com.example.ruolan.letgo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;

import java.util.List;

/**
 * Created by liuwen on 2017/6/30.
 */
public class ClassifyDetailListAdapter extends BaseAdapter {

    private List<BookModel> mList;
    private List<String> mPicList;
    private Context mContext;


    public ClassifyDetailListAdapter(Context context, List<BookModel> list, List<String> picList) {
        mContext = context;
        mList = list;
        mPicList = picList;
    }

    public void updateData(List<BookModel> list) {
        if (list.size() != 0) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    public void updateDataPic(List<String> picList) {
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
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup group) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_classify_detail, null);
            viewHolder = new ViewHolder();
            viewHolder.tvBookName = (TextView) view.findViewById(R.id.book_name);
            viewHolder.tvBooKAuthor = (TextView) view.findViewById(R.id.author);
            viewHolder.tvBookDesc = (TextView) view.findViewById(R.id.info);
            viewHolder.tvBookUpdateContent = (TextView) view.findViewById(R.id.update_title);
            viewHolder.tvBookUpdateTime = (TextView) view.findViewById(R.id.update_time);
            viewHolder.imgBookUrl = (ImageView) view.findViewById(R.id.book_img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvBookName.setText(mList.get(i).getBooKName());
        viewHolder.tvBooKAuthor.setText(mList.get(i).getBookAuthor());
        viewHolder.tvBookDesc.setText(mList.get(i).getBookDesc());
        //viewHolder.tvBookUpdateContent.setText(mList.get(i).getBookUpdateContent());
        viewHolder.tvBookUpdateTime.setText(mList.get(i).getBookUpdateTime());
        if (mPicList.size() != 0 && mPicList.size() == mList.size()) {
            GlideUtils.loadImage(viewHolder.imgBookUrl, "http:" + mPicList.get(i), R.mipmap.bookimg, R.mipmap.bookimg);
        }
        return view;
    }

    class ViewHolder {
        TextView tvBookName;
        TextView tvBooKAuthor;
        TextView tvBookDesc;
        TextView tvBookUpdateContent;
        TextView tvBookUpdateTime;
        ImageView imgBookUrl;
    }
}
