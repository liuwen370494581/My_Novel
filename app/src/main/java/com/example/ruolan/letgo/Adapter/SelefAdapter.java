package com.example.ruolan.letgo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.bean.BookModel;

import java.util.List;

/**
 * Created by liuwen on 2017/8/4.
 * 已经废弃
 */
public class SelefAdapter extends RecyclerView.Adapter<SelefAdapter.MyViewHolder> {
    private List<BookModel> mList;
    private Context mContext;
    private LayoutInflater inflater;

    public SelefAdapter(Context context, List<BookModel> list) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(context);
    }


    public void addData(List<BookModel> list) {
        if (isListNotEmpty(list)) {
            mList.addAll(0, list);
            notifyItemRangeInserted(0, list.size());
        }
    }

    public static boolean isListNotEmpty(List list) {
        return list != null && !list.isEmpty();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_selef, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BookModel model = mList.get(position);
        holder.tvBookName.setText(model.getBooKName());
        holder.tvBookUpdateTime.setText(model.getBookUpdateTime());
        holder.tvBookUpdateContent.setText(model.getBookUpdateContent());
        GlideUtils.loadImage(holder.imgBookUrl, "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookName;
        TextView tvBookUpdateContent;
        TextView tvBookUpdateTime;
        ImageView imgBookUrl;
        LinearLayout lyBody;

        public MyViewHolder(View view) {
            super(view);
            tvBookName = (TextView) view.findViewById(R.id.book_name);
            tvBookUpdateContent = (TextView) view.findViewById(R.id.book_update_content);
            tvBookUpdateTime = (TextView) view.findViewById(R.id.book_update_time);
            imgBookUrl = (ImageView) view.findViewById(R.id.book_img);
            lyBody = (LinearLayout) view.findViewById(R.id.ly_body);
        }
    }
}
