package com.example.ruolan.letgo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ruolan.letgo.Activity.BookDetailActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;

import java.util.List;

/**
 * Created by liuwen on 2017/8/3.
 */
public class InterestingAdapter extends RecyclerView.Adapter<InterestingAdapter.MyViewHolder> {
    private List<String> mPicList;
    private List<BookModel> mList;
    private Context mContext;
    private LayoutInflater inflater;

    public InterestingAdapter(Context context, List<String> picList, List<BookModel> list) {
        mContext = context;
        mPicList = picList;
        mList = list;
        inflater = LayoutInflater.from(context);
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_interesting, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BookModel model = mList.get(position);
        holder.tvBookName.setText(model.getBooKName());
        holder.tvBookRead.setText(model.getBookWriteRead());
        if (mPicList.size() != 0 && mPicList.size() == mList.size()) {
            GlideUtils.loadImage(holder.imgBookUrl, "http:" + mPicList.get(position), R.mipmap.default_book, R.mipmap.default_book);
        }
        holder.lyBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BookDetailActivity.class);
                if (mPicList.size() != 0) {
                    intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, model);
                    intent.putExtra(Config.INTENT_BOOK_DETAIL_PIC, mPicList.get(position));
                    intent.putExtra(Config.INTENT_BOOK_TYPE, "InterestingUi");
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookName;
        TextView tvBookRead;
        ImageView imgBookUrl;
        LinearLayout lyBody;


        public MyViewHolder(View view) {
            super(view);
            tvBookName = (TextView) view.findViewById(R.id.book_name);
            tvBookRead = (TextView) view.findViewById(R.id.book_readed);
            imgBookUrl = (ImageView) view.findViewById(R.id.book_img);
            lyBody = (LinearLayout) view.findViewById(R.id.ly_01);

        }
    }
}
