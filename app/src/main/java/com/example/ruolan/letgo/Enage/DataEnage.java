package com.example.ruolan.letgo.Enage;

import com.example.ruolan.letgo.Base.Config;
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
        list.add(new RankingModel(R.mipmap.home_book_city, "原创风云榜", Config.ORIGIN_WiIND));
        list.add(new RankingModel(R.mipmap.home_book_city, "24小时热销榜", Config.ORIGIN_24HOUR));
        list.add(new RankingModel(R.mipmap.home_book_city, "会员点击榜", Config.ORIGIN_VIP));
        list.add(new RankingModel(R.mipmap.home_book_city, "推荐票榜", Config.ORIGIN_RECOMMEND));
        list.add(new RankingModel(R.mipmap.home_book_city, "收藏榜", Config.ORIGIN_COLLECT));
        list.add(new RankingModel(R.mipmap.home_book_city, "VIP更新榜", Config.ORIGIN_VIP_UPDATE));
        list.add(new RankingModel(R.mipmap.home_book_city, "VIP收藏榜", Config.ORIGIN_VIP_COLLECT));
        list.add(new RankingModel(R.mipmap.home_book_city, "VIP精品打赏榜", Config.ORIGIN_VIP_MONEY));
        list.add(new RankingModel(R.mipmap.home_book_city, "完本", Config.ORIGIN_FINISH));
        return list;
    }
}
