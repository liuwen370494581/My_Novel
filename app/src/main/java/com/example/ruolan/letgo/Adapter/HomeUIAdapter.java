package com.example.ruolan.letgo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ruolan.letgo.Activity.BookDetailActivity;
import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.Utils.GlideUtils;
import com.example.ruolan.letgo.Utils.ToastUtils;
import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.widget.CornerLabelView;
import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;

import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by liuwen on 2017/9/6.
 */
public class HomeUIAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private final int BANNER_VIEW_TYPE = 0;//轮播图
    private final int CHANNEL_VIEW_TYPE = 1;//频道
    private final int EDIT_VIEW_TYPE = 2;//编辑推荐
    private final int NEW_UPDATE_VIEW_TYPE = 3;// 最近更新
    private final int NEW_BOOK_ViEW_TYPE = 4;//新书推荐
    private final int TIME_LIMIT_VIEW_TYPE = 5; //限时免费
    private final int END_VIEW_TYPE = 6;//结束

    //数据
    private List<HashMap<String, Object>> channelList;//频道数据
    private List<BookModel> editList;
    private List<BookModel> newUpdateList;
    private List<BookModel> newBookList;
    private List<BookModel> timeLimitList;
    private List<BookModel> mBannerList;
    private List<String> mBannerPicList;
    private int itemWidth;


    public HomeUIAdapter(Context context, List<HashMap<String, Object>> channelList, List<BookModel> editList, List<BookModel> newUpdateList, List<BookModel> newBookList, List<BookModel> timeLimitList, List<BookModel> bannerList, List<String> bannerPicList, int itemWidth) {
        mContext = context;
        this.channelList = channelList;
        this.editList = editList;
        this.newUpdateList = newUpdateList;
        this.newBookList = newBookList;
        this.timeLimitList = timeLimitList;
        mBannerList = bannerList;
        mBannerPicList = bannerPicList;
        this.itemWidth = itemWidth;
    }

    public void updateEditList(List<BookModel> list) {
        if (isListNotEmpty(list)) {
            editList = list;
        } else {
            editList.clear();
        }
        notifyDataSetChanged();
    }

    public void updateNewUpdateList(List<BookModel> list) {
        if (isListNotEmpty(list)) {
            newUpdateList = list;
        } else {
            newUpdateList.clear();
        }
        notifyDataSetChanged();
    }

    public void updateNewBookList(List<BookModel> list) {
        if (isListNotEmpty(list)) {
            newBookList = list;
        } else {
            newBookList.clear();
        }
        notifyDataSetChanged();
    }

    public void updateTimeLimitList(List<BookModel> list) {
        if (isListNotEmpty(list)) {
            timeLimitList = list;
        } else {
            timeLimitList.clear();
        }
        notifyDataSetChanged();
    }


    public void updateBannerModelList(List<BookModel> list) {
        if (isListNotEmpty(list)) {
            mBannerList = list;
        } else {
            mBannerList.clear();
        }
        notifyDataSetChanged();
    }

    public void updateBannerList(List<String> list) {
        if (isListNotEmpty(list)) {
            mBannerPicList = list;
        } else {
            mBannerPicList.clear();
        }
        notifyDataSetChanged();
    }


    public static boolean isListNotEmpty(List list) {
        return list != null && !list.isEmpty();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return BANNER_VIEW_TYPE;
        } else if (position == 1) {
            return CHANNEL_VIEW_TYPE;
        } else if (position == 2) {
            return EDIT_VIEW_TYPE;
        } else if (position == 3) {
            return NEW_UPDATE_VIEW_TYPE;
        } else if (position == 4) {
            return NEW_BOOK_ViEW_TYPE;
        } else if (position == 5) {
            return TIME_LIMIT_VIEW_TYPE;
        } else {
            return END_VIEW_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == BANNER_VIEW_TYPE) {
            view = getView(R.layout.head_banner);
            BannerHolder holder = new BannerHolder(view);
            return holder;
        } else if (viewType == CHANNEL_VIEW_TYPE) {
            view = getView(R.layout.head_body_channel);
            ChannelHolder holder = new ChannelHolder(view);
            return holder;
        } else if (viewType == EDIT_VIEW_TYPE) {
            view = getView(R.layout.head_body_edit);
            return new EditHolder(view);
        } else if (viewType == NEW_UPDATE_VIEW_TYPE) {
            view = getView(R.layout.head_body_new_update);
            return new NewUpdateHolder(view);
        } else if (viewType == NEW_BOOK_ViEW_TYPE) {
            view = getView(R.layout.head_body_new_book_recomm);
            return new NewBookHolder(view);
        } else if (viewType == TIME_LIMIT_VIEW_TYPE) {
            view = getView(R.layout.head_body_timelimit);
            return new TimeLimitHolder(view);
        } else {
            view = getView(R.layout.head_body_end);
            return new EndHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerHolder) {
            BannerHolder bannerHolder = (BannerHolder) holder;
            setBanner(bannerHolder);
        } else if (holder instanceof ChannelHolder) {
            ChannelHolder channelHolder = (ChannelHolder) holder;
            ChannelAdapter channelAdapter = new ChannelAdapter(channelHolder.mRecyclerView);
            final GridLayoutManager manager = new GridLayoutManager(mContext, 4, LinearLayoutManager.VERTICAL, false);
            channelHolder.mRecyclerView.setLayoutManager(manager);
            channelAdapter.setData(channelList);
            channelHolder.mRecyclerView.setAdapter(channelAdapter);
            channelAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                @Override
                public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                    ToastUtils.showToast(mContext, channelList.get(position).get("title").toString());
                }
            });
        } else if (holder instanceof EditHolder) {
            EditHolder editHolder = (EditHolder) holder;
            CommAdapter commAdapter = new CommAdapter(editHolder.mRecyclerView);
            GridLayoutManager manager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
            editHolder.mRecyclerView.setLayoutManager(manager);
            commAdapter.setData(editList);
            editHolder.mRecyclerView.setAdapter(commAdapter);
            if (editList.size() != 0) {
                editHolder.ReHead.setVisibility(View.VISIBLE);
            }
            commAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                @Override
                public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, editList.get(position));
                    intent.putExtra(Config.INTENT_BOOK_TYPE, "HomeUi");
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof NewUpdateHolder) {
            NewUpdateHolder newUpdateHolder = (NewUpdateHolder) holder;
            NewUpdateAdapter newUpdateAdapter = new NewUpdateAdapter(newUpdateHolder.mRecyclerView);
            newUpdateHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            newUpdateAdapter.setData(newUpdateList);
            newUpdateHolder.mRecyclerView.setAdapter(newUpdateAdapter);
            if (newUpdateList.size() != 0) {
                newUpdateHolder.ReHead.setVisibility(View.VISIBLE);
            }
            newUpdateAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                @Override
                public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, newUpdateList.get(position));
                    intent.putExtra(Config.INTENT_BOOK_TYPE, "HomeUi");
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof NewBookHolder) {
            NewBookHolder newBookHolder = (NewBookHolder) holder;
            CommAdapter commAdapter = new CommAdapter(newBookHolder.mRecyclerView);
            GridLayoutManager manager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
            newBookHolder.mRecyclerView.setLayoutManager(manager);
            commAdapter.setData(newBookList);
            newBookHolder.mRecyclerView.setAdapter(commAdapter);
            if (newBookList.size() != 0) {
                newBookHolder.ReHead.setVisibility(View.VISIBLE);
            }
            commAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                @Override
                public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, newBookList.get(position));
                    intent.putExtra(Config.INTENT_BOOK_TYPE, "HomeUi");
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof TimeLimitHolder) {
            TimeLimitHolder timeLimitHolder = (TimeLimitHolder) holder;
            CommAdapter commAdapter = new CommAdapter(timeLimitHolder.mRecyclerView);
            GridLayoutManager manager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
            timeLimitHolder.mRecyclerView.setLayoutManager(manager);
            commAdapter.setData(timeLimitList);
            timeLimitHolder.mRecyclerView.setAdapter(commAdapter);
            if (timeLimitList.size() != 0) {
                timeLimitHolder.ReHead.setVisibility(View.VISIBLE);
            }
            commAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
                @Override
                public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                    Intent intent = new Intent(mContext, BookDetailActivity.class);
                    intent.putExtra(Config.INTENT_BOOK_DETAIL_LIST, timeLimitList.get(position));
                    intent.putExtra(Config.INTENT_BOOK_TYPE, "HomeUi");
                    mContext.startActivity(intent);
                }
            });
        } else if (holder instanceof EndHolder) {
            EndHolder endHolder = (EndHolder) holder;
            if (itemWidth != 0) {
                endHolder.lyItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                endHolder.tvTitle.setGravity(Gravity.CENTER);
            }
            if (editList.size() != 0 && newBookList.size() != 0 && newUpdateList.size() != 0 && timeLimitList.size() != 0) {
                endHolder.lyItem.setVisibility(View.VISIBLE);
            }

        }
    }


    @Override
    public int getItemCount() {
        return 7;
    }


//    private void setChannel(ChannelHolder channelHolder) {
//        //动态添加View
//        for (int i = 0; i < channelList.size(); i++) {
//            View view = View.inflate(mContext, R.layout.head_body_type, null);
//            ImageView ivLogo = (ImageView) view.findViewById(R.id.iv_logo);
//            TextView tvChannel = (TextView) view.findViewById(R.id.tv_channel);
//            Glide.with(mContext).load(channelList.get(i).get("pic")).into(ivLogo);
//            tvChannel.setText(channelList.get(i).get("title").toString());
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            view.setLayoutParams(params);
//            params.setMargins(24, 18, 24, 0);
//            view.setTag(i);
//            final int finalI = i;
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, channelList.get(finalI).get("title").toString(), Toast.LENGTH_SHORT).show();
//                }
//            });
//            channelHolder.mLinearLayout.addView(view);
//        }
//    }

    private void setBanner(BannerHolder channelHolder) {
        BannerAdapter mBannerAdapter = new BannerAdapter(mContext, mBannerList, mBannerPicList);
        channelHolder.viewpager.setAdapter(mBannerAdapter);
        channelHolder.viewpager.setLooperPic(true);
        channelHolder.indicator.setViewPager(channelHolder.viewpager);
    }

    /**
     * 用来引入布局的方法
     */
    private View getView(int view) {
        View view1 = View.inflate(mContext, view, null);
        return view1;
    }


    //======================================================这是一条分割线=======================================
    public static class BannerHolder extends RecyclerView.ViewHolder {
        LoopViewPager viewpager; //头部banner
        CircleIndicator indicator;//头部banner

        public BannerHolder(View itemView) {
            super(itemView);
            viewpager = (LoopViewPager) itemView.findViewById(R.id.viewpager);
            indicator = (CircleIndicator) itemView.findViewById(R.id.indicator);
        }
    }


    public static class ChannelHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        RelativeLayout ReHead;

        public ChannelHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.channel_recycler_view);
            ReHead = (RelativeLayout) itemView.findViewById(R.id.re_show);
        }
    }

    public static class EditHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        RelativeLayout ReHead;

        public EditHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.edit_recycler_view);
            ReHead = (RelativeLayout) itemView.findViewById(R.id.re_show);
        }
    }

    public static class NewUpdateHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        RelativeLayout ReHead;

        public NewUpdateHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.new_update_reyccler_view);
            ReHead = (RelativeLayout) itemView.findViewById(R.id.re_show);
        }
    }

    public static class NewBookHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;
        RelativeLayout ReHead;

        public NewBookHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.new_book_recycler_view);
            ReHead = (RelativeLayout) itemView.findViewById(R.id.re_show);
        }
    }

    public static class TimeLimitHolder extends RecyclerView.ViewHolder {

        RecyclerView mRecyclerView;
        RelativeLayout ReHead;

        public TimeLimitHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.time_limit_recycler_view);
            ReHead = (RelativeLayout) itemView.findViewById(R.id.re_show);
        }
    }

    public static class EndHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private LinearLayout lyItem;

        public EndHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_name);
            lyItem = (LinearLayout) itemView.findViewById(R.id.id_item_layout);
        }
    }


    private class NewUpdateAdapter extends BGARecyclerViewAdapter<BookModel> {

        public NewUpdateAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_head_new_update);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            helper.setText(R.id.book_name, model.getBooKName());
            helper.setText(R.id.author, model.getBookAuthor());
            helper.setText(R.id.info, model.getBookDesc());
            helper.setText(R.id.update_title, model.getBookUpdateTime());
            helper.setText(R.id.update_time, model.getBookAuthorUrl());
            GlideUtils.loadImage(helper.getImageView(R.id.book_img), "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
        }
    }


    private class ChannelAdapter extends BGARecyclerViewAdapter<HashMap<String, Object>> {

        public ChannelAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.head_body_type);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, HashMap<String, Object> model) {
            if (itemWidth != 0) {
                helper.getView(R.id.id_item_layout).setLayoutParams(new ViewGroup.LayoutParams(itemWidth / 4, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            helper.setText(R.id.tv_channel, model.get("title").toString());
            Glide.with(mContext).load(model.get("pic")).into(helper.getImageView(R.id.iv_logo));

        }
    }

    private class CommAdapter extends BGARecyclerViewAdapter<BookModel> {


        public CommAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_edit_recycler_view);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BookModel model) {
            CornerLabelView cVTitle = helper.getView(R.id.label);
            helper.setText(R.id.item_edit_tv, model.getBooKName());
            helper.getView(R.id.label).setVisibility(View.VISIBLE);
            if (model.bookTypeLayout == BookModel.BookTypeLayout.freeTimeData) {
                cVTitle.setText1("限时免费");
            } else if (model.bookTypeLayout == BookModel.BookTypeLayout.editData) {
                cVTitle.setText1("编辑推荐");
                cVTitle.setFillColorResource(R.color.statusColor);
            } else if (model.bookTypeLayout == BookModel.BookTypeLayout.newBookData) {
                cVTitle.setText1("新书推荐");
                cVTitle.setFillColorResource(R.color.updateTextColor);
            }
            GlideUtils.loadImage(helper.getImageView(R.id.item_edit_img), "http:" + model.getBookPic(), R.mipmap.default_book, R.mipmap.default_book);
        }
    }

}
