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


    /**
     * 获取书籍 增加书籍
     *
     * @return
     */
    public static List<Dish> searchBook() {
        List<Dish> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://home.meishichina.com/show-top-type-recipe.html").get();
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


    public static List<BookModel> searchQiDianRanking() {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.QIDIANURL).get();
            Elements elements = document.select("div.book-mid-info");
            Elements elements1 = document.select("div.book-img-box");
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


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static List<BookModel> searchQiDianRankingPic() {
        List<BookModel> list = new ArrayList<>();
        BookModel model = null;
        try {
            Document document = Jsoup.connect(Config.QIDIANURL).get();
            Elements elements1 = document.select("div.book-img-box");
            for (int j = 0; j < elements1.size(); j++) {
                model.setBookUrl(elements1.get(j).select("a").select("img").attr("src"));
                list.add(model);
                Log.e("MainActivity", "bookUrl" + elements1.get(j).select("a").select("img").attr("src"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
