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
 * Created by liuwen on 2017/6/28.
 */
public class RankShowAdapter extends RecyclerView.Adapter<RankShowAdapter.MyViewHolder> {
    private List<BookModel> mList;
    private List<String> mPicList;
    private Context mContext;
    private LayoutInflater inflater;

    public RankShowAdapter(List<BookModel> list, List<String> picList, Context context) {
        mList = list;
        mPicList = picList;
        mContext = context;
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

    public void addMoreDate(List<BookModel> list) {
        if (list.size() != 0) {
            mList.addAll(mList.size(), list);
            notifyItemRangeChanged(mList.size(), list.size());
        }
    }

    public void addMoreDatePic(List<String> picList) {
        if (picList.size() != 0) {
            mPicList.addAll(mPicList.size(), picList);
            notifyItemRangeChanged(mPicList.size(), picList.size());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_book_ranking, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BookModel model = mList.get(position);
        holder.tvBookName.setText(model.getBooKName());
        holder.tvBooKAuthor.setText(model.getBookAuthor());
        holder.tvBookDesc.setText(model.getBookDesc());
        holder.tvBookUpdateContent.setText(model.getBookUpdateContent());
        holder.tvBookUpdateTime.setText(model.getBookUpdateTime());
        if (mPicList.size() != 0 && mPicList.size() == mList.size()) {
            GlideUtils.loadImage(holder.imgBookUrl, "http:" + mPicList.get(position), R.mipmap.default_book, R.mipmap.default_book);
        }
        //在适配器写这个方法是最好的 避免多次写跳转方法
        holder.lyBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPicList.size() != 0) {
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, model);
                    intent.putExtra(Config.INTENT_BOOK_DETAIL_PIC, mPicList.get(position));
                    intent.putExtra(Config.INTENT_BOOK_TYPE, "RankUi");
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
        TextView tvBooKAuthor;
        TextView tvBookDesc;
        TextView tvBookUpdateContent;
        TextView tvBookUpdateTime;
        ImageView imgBookUrl;
        LinearLayout lyBody;

        public MyViewHolder(View view) {
            super(view);
            tvBookName = (TextView) view.findViewById(R.id.book_name);
            tvBooKAuthor = (TextView) view.findViewById(R.id.author);
            tvBookDesc = (TextView) view.findViewById(R.id.info);
            tvBookUpdateContent = (TextView) view.findViewById(R.id.update_title);
            tvBookUpdateTime = (TextView) view.findViewById(R.id.update_time);
            imgBookUrl = (ImageView) view.findViewById(R.id.book_img);
            lyBody = (LinearLayout) view.findViewById(R.id.ly_01);
        }
    }
}
