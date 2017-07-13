package com.example.ruolan.letgo.bean;

import android.util.Log;

import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.bean.Dish;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuwen on 2017/6/22.
 */
public class HtmlParserUtil {

    public static List<Dish> searchBook() {
        List<Dish> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://home.meishichina.com/show-top-type-recipe.html").timeout(40000).get();
            Elements elements = doc.select("div.pic");
            for (int i = 0; i < elements.size(); i++) {
                Dish dish = new Dish();
                dish.setTitle(elements.get(i).select("a").attr("title"));
                dish.setUrl(elements.get(i).select("a").select("img").attr("data-src"));
                list.add(dish);
                Log.e("MainActivity", "title:" + elements.get(i).select("a").attr("title") + "pic:" + elements.get(i).select("a").select("img").attr("data-src"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点数据info
    public static List<BookModel> searchQiDianRanking(String url, int indexPage, int typePage) {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(String.format(url, indexPage) + typePage).timeout(40000).get();
            Log.e("MyTag", String.format(url, typePage) + indexPage);
            Elements elements = document.select("div.book-mid-info");
            for (int i = 0; i < elements.size(); i++) {
                BookModel model = new BookModel();
                Log.e("MainActivity", "DetailUrl: " + elements.get(i).select("a").attr("href") +
//                                " BookName: " + elements.get(i).select("a").first().text() +
//                                " AuthorUrl" + elements.get(i).select("p").select("a").attr("href") +
//                                " UpdateContent: " + elements.get(i).select("p").select("a").last().text() +
//                                " UpdateTime:" + elements.get(i).select("p").select("span").last().text() +
//                                " BookAuthor:" + elements.get(i).select("p").tagName("intro").first().text()
                                " BookDesc:" + elements.get(i).getElementsByTag("intro").text()
                );
                model.setBookDetailUrl(elements.get(i).select("a").attr("href"));
                model.setBooKName(elements.get(i).select("a").first().text());
                model.setBookAuthorUrl(elements.get(i).select("p").select("a").attr("href"));
                String[] desc = elements.get(i).getElementsByTag("intro").text().split(" ");
                model.setBookDesc(desc[1]);
                model.setBookAuthor(desc[0]);
                model.setBookUpdateContent(desc[3]);
                String[] updateTime = elements.get(i).getElementsByTag("intro").text().split("·");
                model.setBookUpdateTime(updateTime[1]);
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点图书的img
    public static List<String> searchQiDianRankingPic(String url, int indexPage, int typePage) {
        List<String> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(String.format(url, indexPage) + typePage).timeout(40000).get();
            Elements elements1 = document.select("div.book-img-box");
            for (int j = 0; j < elements1.size(); j++) {
                list.add(elements1.get(j).select("a").select("img").attr("src"));
                Log.e("MainActivity", "bookUrl" + elements1.get(j).select("a").select("img").attr("src"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static List<ClassifyModel> searchQIDianClassify(int page) {
        List<ClassifyModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect("http://a.qidian.com/?size=-1&sign=-1&tag=-1&chanId=-1&subCateId=-1&orderId=&update=-1&page=1&month=-1&style=1&action=-1&vip=-1").timeout(40000).get();
            Elements elements1 = document.select("ul.row-" + page);
            Log.e("MyTag", elements1.size() + "" + elements1.text());
            String[] content = elements1.text().split(" ");
            list.add(new ClassifyModel(content[0]));
            list.add(new ClassifyModel(content[1]));
            list.add(new ClassifyModel(content[2]));
            list.add(new ClassifyModel(content[3]));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String searchQIDIANClassifyCount(String url) {
        try {
            Document document = Jsoup.connect(url).timeout(40000).get();
            Elements elements1 = document.select("div.count-text");
            Log.e("MyTag", elements1.size() + "count" + elements1.select("span").text());
            return elements1.select("span").text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析错误返回一个默认值 不然老是报错
        return "625412";
    }


    //获取起点分類排行數據info 分类排行榜没有更新时间和更新内容 所以只显示了字数
    public static List<BookModel> searchQiDianClassify(String url, int indexPage) {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(String.format(url, indexPage)).timeout(40000).get();
            Elements elements = document.select("div.book-mid-info");
            Log.e("MainActivity", elements.size() + "");
            for (int i = 0; i < elements.size(); i++) {
                BookModel model = new BookModel();
                Log.e("MainActivity", "DetailUrl: " + elements.get(i).select("a").attr("href") +
                                " BookName: " + elements.get(i).select("a").first().text() +
                                " AuthorUrl" + elements.get(i).select("p").select("a").attr("href") +
                                " UpdateContent: " + elements.get(i).select("p").select("a").last().text() +
                                " UpdateTime:" + elements.get(i).select("p").select("span").last().text() +
                                " BookAuthor:" + elements.get(i).select("p").tagName("intro").first().text()
//                        " BookDesc:" + elements.get(i).getElementsByTag("intro").text()
                );
                Log.e("MainActivity", elements.size() + "");
                model.setBookDetailUrl(elements.get(i).select("a").attr("href"));
                model.setBooKName(elements.get(i).select("a").first().text());
                model.setBookAuthorUrl(elements.get(i).select("p").select("a").attr("href"));
                String[] desc = elements.get(i).getElementsByTag("intro").text().split(" ");
                model.setBookDesc(desc[1]);
                model.setBookAuthor(desc[0]);
                model.setBookUpdateContent(desc[2]);
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点分類排行的img
    public static List<String> searchQiDianClassifyPic(String url, int indexPage) {
        List<String> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(String.format(url, indexPage)).timeout(40000).get();
            Elements elements1 = document.select("div.book-img-box");
            for (int j = 0; j < elements1.size(); j++) {
                list.add(elements1.get(j).select("a").select("img").attr("src"));
                Log.e("MainActivity", "bookUrl" + elements1.get(j).select("a").select("img").attr("src"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点封面图片
    public static List<String> searchQiDianCoverPic() {
        List<String> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.ORIGIN_COVER).timeout(40000).get();
            Elements elements1 = document.select("div.focus-img");
            for (int j = 0; j < 5; j++) {
                Log.e("MainActivity", "img" + elements1.get(j).select("a").select("img").attr("src"));
                list.add(elements1.get(j).select("a").select("img").attr("src"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点封面图片
    public static List<IndexModel> searchQiDianCover() {
        List<IndexModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.ORIGIN_COVER).timeout(40000).get();
            Elements elements1 = document.select("div.info");
            for (int j = 0; j < 5; j++) {
                IndexModel model = new IndexModel();
                Log.e("MainActivity", "bookName:" + elements1.get(j).select("a").attr("title"));
                // list.add(elements1.get(j).select("a").select("img").attr("src"));
                Log.e("MainActivity", "booUrl:" + elements1.get(j).select("a").attr("href"));
                model.setBookUrl(elements1.get(j).select("a").attr("href"));
                model.setName(elements1.get(j).select("a").attr("title"));
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static List<String> searchQiDianHot() {
        List<String> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.ORIGIN_COVER).timeout(40000).get();
            Elements elements1 = document.select("div.focus-img");
            for (int j = 0; j < 5; j++) {
                Log.e("MainActivity", "img" + elements1.get(j).select("a").select("img").attr("src"));
                list.add(elements1.get(j).select("a").select("img").attr("src"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
