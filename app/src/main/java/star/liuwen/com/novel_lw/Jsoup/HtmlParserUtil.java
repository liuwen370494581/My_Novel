package star.liuwen.com.novel_lw.Jsoup;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.novel_lw.Bean.Dish;

/**
 * Created by liuwen on 2017/6/22.
 */
public class HtmlParserUtil {

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
                Log.e("mytag", "title:" + elements.get(i).select("a").attr("title") + "pic:" + elements.get(i).select("a").select("img").attr("data-src"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
