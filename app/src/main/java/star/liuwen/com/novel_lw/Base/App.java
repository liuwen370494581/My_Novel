package star.liuwen.com.novel_lw.Base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by liuwen on 2017/6/20.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        if (getSharedPreferences(Config.USER_SETTINGS, MODE_PRIVATE).getInt(Config.USER_THEME, 0) == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        context = getApplicationContext();
    }

}
