package com.example.ruolan.letgo.Enage;

import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.RankingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwen on 2017/6/26.
 */
public class DataEnage {


    public static List<RankingModel> getRankListData() {
        List<RankingModel> list = new ArrayList<>();
        list.add(new RankingModel(R.mipmap.home_book_city, "起点月票榜"));
        list.add(new RankingModel(R.mipmap.home_book_city, "掌阅热搜榜"));
        list.add(new RankingModel(R.mipmap.home_book_city, "书旗热搜榜"));
        list.add(new RankingModel(R.mipmap.home_book_city, "17K鲜花榜"));
        list.add(new RankingModel(R.mipmap.home_book_city, "天涯在线书屋榜"));
        list.add(new RankingModel(R.mipmap.home_book_city, "纵横月票榜"));
        list.add(new RankingModel(R.mipmap.home_book_city, "和阅读原创榜"));
        list.add(new RankingModel(R.mipmap.home_book_city, "逐浪点击榜"));
        return list;
    }
}
