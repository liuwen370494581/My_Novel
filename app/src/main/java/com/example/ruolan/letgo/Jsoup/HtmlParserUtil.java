package com.example.ruolan.letgo.bean;

import android.util.Log;

import com.example.ruolan.letgo.Base.Config;
import com.example.ruolan.letgo.MainActivity;
import com.example.ruolan.letgo.Utils.URLDecoderToUTF8;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by liuwen on 2017/6/22.
 */
public class HtmlParserUtil {

    //获取起点数据info 排行榜
    public static List<BookModel> searchQiDianRanking(String url, int indexPage, int typePage) {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(String.format(url, indexPage) + typePage).timeout(40000).get();
            Log.e("MyTag", String.format(url, typePage) + indexPage);
            Elements elements = document.select("div.book-mid-info");
            for (int i = 0; i < elements.size(); i++) {
                BookModel model = new BookModel();
                Log.e(Config.TAG, "bookDetailUrl====" + elements.get(i).select("a").attr("href"));
                Log.e(Config.TAG, "bookName====" + elements.get(i).select("h4").text());
                Log.e(Config.TAG, "bookAuthor===" + elements.get(i).select("p").first().text());
                Log.e(Config.TAG, "bookAuthorUrl===" + elements.get(i).select("p").select("a").attr("href"));
                Log.e(Config.TAG, "bookUpdateContent===" + elements.get(i).select("p").select("a").last().text());
                Log.e(Config.TAG, "bookDesc======" + elements.get(i).select("p.intro").text());
                Log.e(Config.TAG, "bookUpdateTime====" + elements.get(i).select("p").select("span").last().text());
                model.setBookDetailUrl(elements.get(i).select("a").attr("href"));
                model.setBooKName(elements.get(i).select("h4").text());
                model.setBookAuthorUrl(elements.get(i).select("p").select("a").attr("href"));
                String updateContent = elements.get(i).select("p").select("a").last().text().replace("最新更新", "");
                model.setBookDesc(elements.get(i).select("p.intro").text());
                model.setBookAuthor(elements.get(i).select("p").first().text());
                model.setBookUpdateContent(updateContent);
                model.setBookUpdateTime(elements.get(i).select("p").select("span").last().text());
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点图书的img 排行榜
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
            Log.e(Config.TAG, elements1.size() + "" + elements1.text());
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
            Log.e("MyTag", elements1.size() + "count===" + elements1.select("span").text());
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
            // Log.e(Config.TAG, elements.toString());
            for (int i = 0; i < elements.size(); i++) {
                BookModel model = new BookModel();
                model.setBookAuthorUrl(elements.get(i).select("p").select("a").attr("href").replace("http:", ""));
                Log.e("MainActivity", "BookDetailUrl===" + elements.get(i).select("a").attr("href"));
                Log.e("MainActivity", "BookName=== " + elements.get(i).select("a").first().text());
                Log.e("MainActivity", "AuthorUrl=== " + elements.get(i).select("p").select("a").attr("href").replace("http:", ""));
                Log.e("MainActivity", "UpdateContent=== " + elements.get(i).select("p").select("a").last().text());
                Log.e("MainActivity", "UpdateTime=== " + elements.get(i).select("p").select("span").last().text());
                Log.e("MainActivity", "BookAuthor=== " + elements.get(i).select("p").tagName("intro").first().text());
                model.setBookDetailUrl(elements.get(i).select("a").attr("href"));
                model.setBooKName(elements.get(i).select("a").first().text());
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
                Log.e(Config.TAG, "bookUrl===" + elements1.get(j).select("a").select("img").attr("src"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点封面图片 轮播图
    public static List<String> searchQiDianCoverPic() {
        List<String> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.ORIGIN_COVER).timeout(40000).get();
            Elements elements1 = document.select("div.focus-img");
            for (int j = 0; j < 5; j++) {
                Log.e(Config.TAG, "img===" + elements1.get(j).select("a").select("img").attr("src"));
                list.add(elements1.get(j).select("a").select("img").attr("src"));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点封面图片内容 轮播图
    public static List<BookModel> searchQiDianCover() {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.ORIGIN_COVER).timeout(40000).get();
            Elements elements1 = document.select("div.info");
            for (int j = 0; j < 5; j++) {
                BookModel model = new BookModel();
                Log.e(Config.TAG, "bookName===:" + elements1.get(j).select("a").attr("title"));
                // list.add(elements1.get(j).select("a").select("img").attr("src"));
                Log.e(Config.TAG, "bookDetailUrl===" + elements1.get(j).select("a").attr("href").replace("http", ""));
                model.setBookDetailUrl(elements1.get(j).select("a").attr("href").replace("http", ""));
                model.setBooKName(elements1.get(j).select("a").attr("title"));
                list.add(model);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //获取起点的首页编辑推荐
    public static List<BookModel> searchQIDianType() {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.QI_DIAN).timeout(40000).get();
            Elements elements = document.select("div.slideItem");
            for (int i = 0; i < elements.size(); i++) {
                //  Log.e(Config.TAG, "" + elements.get(i).toString());
                Log.e(Config.TAG, "BookDetailUrl===" + elements.get(i).select("a").attr("href"));
                Log.e(Config.TAG, "title===" + elements.get(i).select("a").select("img").attr("title"));
                Log.e(Config.TAG, "url====" + elements.get(i).select("a").select("img").attr("src"));
                BookModel model = new BookModel();
                model.setBooKName(elements.get(i).select("a").select("img").attr("title"));
                model.setBookPic(elements.get(i).select("a").select("img").attr("src"));
                model.setBookDetailUrl(elements.get(i).select("a").attr("href"));
                list.add(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //获取起点的首页限时免费
    public static List<BookModel> searchQIDianAppsFree() {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.QI_DIAN).timeout(40000).get();
            Elements elements = document.select("div.book-img");
            for (int i = 0; i < elements.size(); i++) {
                Log.e(Config.TAG, "BookDetailUrl===" + elements.get(i).select("a").attr("href"));
                Log.e(Config.TAG, "bookImg===" + elements.get(i).select("a").select("img").attr("data-original"));
                Log.e(Config.TAG, "bookName===" + elements.get(i).select("a").select("img").attr("alt"));
                BookModel model = new BookModel();
                model.setBooKName(elements.get(i).select("a").select("img").attr("alt"));
                model.setBookDetailUrl(elements.get(i).select("a").attr("href"));
                model.setBookPic(elements.get(i).select("a").select("img").attr("data-original"));
                list.add(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点首页最新更新
    public static List<BookModel> searchQIDianNewUpdate() {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.QI_DIAN).timeout(40000).get();
            Elements elements1 = document.select("div.update-rec-list").select("ul").select("li");
            for (int i = 0; i < elements1.size(); i++) {
                Log.e(Config.TAG, "bookDetailUrl====" + elements1.get(i).select("a").attr("href"));
                Log.e(Config.TAG, "bookName===" + elements1.get(i).select("h4").text());
                Log.e(Config.TAG, "bookAuthor===" + elements1.get(i).select("p.author").select("a").text());
                Log.e(Config.TAG, "bookImg=====" + elements1.get(i).select("div.book-cover").select("a").select("img").attr("src"));
                Log.e(Config.TAG, "bookDesc====" + elements1.get(i).select("p.intro").text());
                String number[] = elements1.get(i).select("p.digital").select("span").text().split(" ");
                Log.e(Config.TAG, "bookNumberWords===" + number[0]);
                Log.e(Config.TAG, "bookMark===" + number[1]);
                BookModel model = new BookModel();
                model.setBookDetailUrl(elements1.get(i).select("a").attr("href"));
                model.setBooKName(elements1.get(i).select("h4").text());
                model.setBookAuthor(elements1.get(i).select("p.author").select("a").text());
                model.setBookPic(elements1.get(i).select("div.book-cover").select("a").select("img").attr("src"));
                model.setBookDesc(elements1.get(i).select("p.intro").text());
                model.setBookUpdateTime(number[0]);
                model.setBookAuthorUrl(number[1]);
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点首页新书推荐和已完结作品
    public static List<BookModel> searchQIDianNewBookReComm() {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Config.QI_DIAN).timeout(40000).get();
            Elements elements1 = document.select("div.slide-box").select("ul").select("li");
            for (int i = 0; i < elements1.size(); i++) {
                Log.e(Config.TAG, "BooKDetailUrl===" + elements1.get(i).select("li").select("a").attr("href"));
                Log.e(Config.TAG, "bookImg===" + elements1.get(i).select("li").select("a").select("img").attr("data-original"));
                Log.e(Config.TAG, "bookName===" + elements1.get(i).select("li").select("a").select("img").attr("alt"));
                BookModel model = new BookModel();
                model.setBooKName(elements1.get(i).select("li").select("a").select("img").attr("alt"));
                model.setBookDetailUrl(elements1.get(i).select("li").select("a").attr("href"));
                model.setBookPic(elements1.get(i).select("li").select("a").select("img").attr("data-original"));
                list.add(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点搜索的书籍封面
    public static List<String> searchBookPic(String bookName, int page) {
        List<String> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(URLDecoderToUTF8.StringToUTF8(String.format(Config.QI_DIAN_SEARCH, bookName) + page)).timeout(40000).get();
            Elements elements = document.select("div.book-img-text").select("div.book-img-box");
            Log.e(Config.TAG, elements.size() + "");
            for (int i = 0; i < elements.size(); i++) {
                list.add(elements.get(i).select("a").select("img").attr("src"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    //获取起点搜索的书籍详细内容
    public static List<BookModel> searchBook(String bookName, int page) {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(URLDecoderToUTF8.StringToUTF8(String.format(Config.QI_DIAN_SEARCH, bookName) + page)).timeout(40000).get();
            Elements elements = document.select("div.book-img-text").select("div.book-mid-info");
            for (int i = 0; i < elements.size(); i++) {
                BookModel model = new BookModel();
                Log.e(Config.TAG, elements.toString());
                Log.e(Config.TAG, "bookDetailUrl===" + elements.get(i).select("a").attr("href"));
                Log.e(Config.TAG, "bookName====" + elements.get(i).select("h4").text());
                Log.e(Config.TAG, "bookAuthor===" + elements.get(i).select("p").first().text());
                Log.e(Config.TAG, "bookAuthorUrl===" + elements.get(i).select("p").select("a").attr("href"));
                Log.e(Config.TAG, "bookUpdateContent===" + elements.get(i).select("p").select("a").last().text());
                Log.e(Config.TAG, "bookDesc======" + elements.get(i).select("p.intro").text());
                Log.e(Config.TAG, "bookUpdateTime====" + elements.get(i).select("p").select("span").last().text());
                if (!elements.get(i).select("h4").text().equals("-")) {
                    model.setBookDetailUrl(elements.get(i).select("a").attr("href"));
                    model.setBooKName(elements.get(i).select("h4").text());
                    model.setBookAuthor(elements.get(i).select("p").first().text());
                    model.setBookAuthorUrl(elements.get(i).select("p").select("a").attr("href").replace("http:", ""));
                    String updateContent = elements.get(i).select("p").select("a").last().text().replace("最新更新", "");
                    model.setBookUpdateContent(updateContent);
                    model.setBookDesc(elements.get(i).select("p.intro").text());
                    model.setBookUpdateTime(elements.get(i).select("p").select("span").last().text());
                    list.add(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<BookModel> searchAuthorWork(String webUrl) {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect("http:" + webUrl).timeout(40000).get();
            Elements elements = document.select("ul.author-work").select("li.author-item");
            //Log.e(Config.TAG, elements.toString());
            for (int i = 0; i < elements.size(); i++) {
                BookModel model = new BookModel();
                Log.e(Config.TAG, "BookDetailUrl===" + elements.get(i).select("a").attr("href"));
                Log.e(Config.TAG, "AuthorWriteTime====" + elements.get(i).select("div.author-item-time").text());
                Log.e(Config.TAG, "BookPic===" + elements.get(i).select("a").select("img").attr("src"));
                Log.e(Config.TAG, "BookName===" + elements.get(i).select("div.author-item-msg").select("div.author-item-title").select("a").text());
                Log.e(Config.TAG, "BookDesc===" + elements.get(i).select("div.author-item-content").text());
                Log.e(Config.TAG, "bookType===" + elements.get(i).select("div.author-item-exp").select("a").text());
                Log.e(Config.TAG, "bookWordNumber===" + elements.get(i).select("div.author-item-exp").last().text());
                Log.e(Config.TAG, "bookUpdateContent===" + elements.get(i).select("div.author-item-update").select("a").attr("title"));
                Log.e(Config.TAG, "bookUpdateTime===" + elements.get(i).select("div.author-item-update").last().text());
                model.setBookAuthorWriteTime(elements.get(i).select("div.author-item-time").text());
                model.setBookPic(elements.get(i).select("a").select("img").attr("src"));
                model.setBooKName(elements.get(i).select("div.author-item-msg").select("div.author-item-title").select("a").text());
                model.setBookDesc(elements.get(i).select("div.author-item-content").text());
                model.setBookType(elements.get(i).select("div.author-item-exp").last().text());
                model.setBookUpdateContent(elements.get(i).select("div.author-item-update").select("a").attr("title").replace("最近更新", ""));
                String updateContent[] = elements.get(i).select("div.author-item-update").last().text().split("  ·  ");
                model.setBookUpdateTime(updateContent[1]);
                model.setBookDetailUrl(elements.get(i).select("a").attr("href"));
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点搜索的书籍详细内容感兴趣的书籍封面url
    public static List<String> searchInterestingBookPic(String bookName) {
        List<String> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(URLDecoderToUTF8.StringToUTF8(String.format(Config.QI_DIAN_INTERESING, bookName))).timeout(40000).get();
            Elements elements = document.select("div.img-box");
            for (int i = 0; i < elements.size(); i++) {
                Log.e(Config.TAG, "img===" + elements.get(i).select("a").select("img").attr("src"));
                list.add(elements.get(i).select("a").select("img").attr("src"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //获取起点搜索的书籍详细内容感兴趣的书籍
    public static List<BookModel> searchInterestingBook(String bookName) {
        List<BookModel> list = new ArrayList<>();
        try {
            Document document = Jsoup.connect(URLDecoderToUTF8.StringToUTF8(String.format(Config.QI_DIAN_INTERESING, bookName))).timeout(40000).get();
            Elements elements = document.select("div.book-info");
            // Log.e(Config.TAG, elements.toString());
            for (int i = 0; i < elements.size(); i++) {
                BookModel model = new BookModel();
                Log.e(Config.TAG, "bookDetailUrl===" + elements.get(i).select("a").attr("href"));
                Log.e(Config.TAG, "bookName===" + elements.get(i).select("h4").text());
                Log.e(Config.TAG, "booKAuthor===" + elements.get(i).select("p.author").select("a").text());
                Log.e(Config.TAG, "bookAuthorUrl===" + elements.get(i).select("p.author").select("a").attr("href").replace("http:", ""));
                Log.e(Config.TAG, "bookWriteRead===" + elements.get(i).select("p.intro").text());
                model.setBooKName(elements.get(i).select("h4").text());
                model.setBookAuthor(elements.get(i).select("p.author").select("a").text());
                model.setBookAuthorUrl(elements.get(i).select("p.author").select("a").attr("href").replace("http:", ""));
                model.setBookWriteRead(elements.get(i).select("p.intro").text());
                model.setBookDetailUrl(elements.get(i).select("a").attr("href"));
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static BookModel searchDetailBookUI(String bookDetailUrl) {
        BookModel model = new BookModel();
        try {
            Document document = Jsoup.connect("http:" + bookDetailUrl).timeout(40000).get();
            Elements elements = document.select("div.book-info");
            // Log.e(Config.TAG, elements.toString());
            Log.e(Config.TAG, "bookName===" + elements.get(0).select("h1").select("em").text());
            Log.e(Config.TAG, "bookAuthor===" + elements.get(0).select("a.writer").text() + "|" + elements.get(0).select("a.red").first().text() + "|" + elements.get(0).select("span.blue").first().text());
            model.setBooKName(elements.get(0).select("h1").select("em").text());
            model.setBookAuthor(elements.get(0).select("a.writer").text() + "|" + elements.get(0).select("a.red").first().text() + "|" + elements.get(0).select("span.blue").first().text());
            Elements elements1 = document.select("li.update");
            Log.e(Config.TAG, "bookUpdateContent===" + elements1.get(0).select("a").attr("title"));
            Log.e(Config.TAG, "bookUpdateTime===" + elements1.get(0).select("em.time").text());
            model.setBookUpdateContent(elements1.get(0).select("a").attr("title"));
            model.setBookUpdateTime(elements1.get(0).select("em.time").text());
            // Log.e(Config.TAG, elements1.toString());
            Elements elements2 = document.select("div.book-intro");
            Log.e(Config.TAG, "bookDesc===" + elements2.get(0).select("p").text());
            Elements elements3 = document.select("a.red-btn");
            Log.e(Config.TAG, "bookFreeRead===" + elements3.get(0).attr("href"));
            model.setBookDesc(elements2.get(0).select("p").text());
            model.setBookFreeRead(elements3.get(0).attr("href"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    public static BookModel startReadBook(String bookDetailUrl) {
        BookModel model = new BookModel();
        try {
            Document document = Jsoup.connect("http://read.qidian.com/chapter/rJgN8tJ_cVdRGoWu-UQg7Q2/6jr-buLIUJSaGfXRMrUjdw2").timeout(40000).get();
            Elements elements = document.select("h3.j_chapterName");
            Log.e(Config.TAG, "FirstTitle===" + elements.text());
            Elements elements1 = document.select("div.read-content");
            Log.e(Config.TAG, "content===" + elements1.text());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

}
