package com.example.ruolan.letgo.Enage;

import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.R;
import com.example.ruolan.letgo.bean.ClassifyModel;
import com.example.ruolan.letgo.bean.Dish;
import com.example.ruolan.letgo.bean.IndexModel;
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

    public static List<ClassifyModel> getClassifyDataMan() {

        List<ClassifyModel> list = new ArrayList<>();
        list.add(new ClassifyModel(Config.ORIGIN_MAN_ALL, "全部", "635595"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_XUNHUAN, "玄幻", "212532"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_QIHUAN, "奇幻", "46662"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_WUXiA, "武侠", "21804"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_XINAXIA, "仙侠", "66993"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_DOUSHI, "都市", "100835"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_XIANSHI, "现实", "13178"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_JUNSHI, "军事", "3202"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_LISHI, "历史", "23504"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_YOUXI, "游戏", "29215"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_TIYU, "体育", "2912"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_KEHUAN, "科幻", "50430"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_LINGYI, "灵异", "20587"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_ERCIYUAN, "二次元", "32622"));
        list.add(new ClassifyModel(Config.ORIGIN_MAN_DUANPIAN, "段篇", "10328"));
        return list;
    }

    public static List<ClassifyModel> getClassifyFDataWoman() {
        List<ClassifyModel> list = new ArrayList<>();
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_ALL, "全部", "278172"));
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_GUDAI, "古代言情", "64614"));
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_XIANXIA, "仙侠奇缘", "12575"));
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_XIANDAI, "现代言情", "84113"));
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_LANGMAN, "浪漫青春", "49872"));
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_XUANHUAN, "玄幻言情", "38786"));
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_XUANNING, "悬疑灵异", "5118"));
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_KEHHUAN, "科幻空间", "6562"));
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_YOUXI, "游戏竞技", "2308"));
        list.add(new ClassifyModel(Config.ORIGIN_WOMAN_NCI, "N次元", "9809"));
        return list;
    }


    public static List<IndexModel> getClassifyDetailHeadData() {
        List<IndexModel> list = new ArrayList<>();
        list.add(new IndexModel("全部"));
        list.add(new IndexModel("东方玄幻"));
        list.add(new IndexModel("异世大陆"));
        list.add(new IndexModel("王朝争霸"));
        list.add(new IndexModel("高武世界"));
        return list;
    }


    public static List<Dish> getReflashData() {
        List<Dish> list = new ArrayList<>();
        list.add(new Dish("黑暗王者"));
        list.add(new Dish("顶级老公,太嚣张"));
        list.add(new Dish("网游之神级机械猎人"));
        list.add(new Dish("爆笑宠妃：爷我等你休妻"));
        list.add(new Dish("司马懿吃三国"));
        list.add(new Dish("总裁大人你轻点"));
        return list;
    }
    public static List<Dish> getReflashData_2() {
        List<Dish> list = new ArrayList<>();
        list.add(new Dish("银河帝国"));
        list.add(new Dish("大主宰"));
        list.add(new Dish("军婚缠绵:大总裁,小甜心"));
        list.add(new Dish("阴阳先生"));
        list.add(new Dish("帝豪老公太狂热"));
        list.add(new Dish("踏天无痕"));
        return list;
    }
    public static List<Dish> getReflashData_3() {
        List<Dish> list = new ArrayList<>();
        list.add(new Dish("豪门天价前妻"));
        list.add(new Dish("斗罗大陆"));
        list.add(new Dish("锦绣清宫:四爷的心尖宠妃"));
        list.add(new Dish("龙血武神"));
        list.add(new Dish("随声英雄杀"));
        list.add(new Dish("铁血宏图"));
        return list;
    }

    public static String getRandomColor() {
        List<String> colorList = new ArrayList<String>();
        colorList.add("#303F9F");
        colorList.add("#FF4081");
        colorList.add("#59dbe0");
        colorList.add("#f57f68");
        colorList.add("#87d288");
        colorList.add("#f8b552");
        colorList.add("#990099");
        colorList.add("#90a4ae");
        colorList.add("#7baaf7");
        colorList.add("#4dd0e1");
        colorList.add("#4db6ac");
        colorList.add("#aed581");
        colorList.add("#f2a600");
        colorList.add("#ff8a65");
        colorList.add("#f48fb1");
        return colorList.get((int) (Math.random() * colorList.size()));
    }

}
