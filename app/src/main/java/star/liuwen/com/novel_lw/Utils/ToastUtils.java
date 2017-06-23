package star.liuwen.com.novel_lw.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liuwen on 2017/6/22.
 */
public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    public static void removeToast() {
        if (mToast != null) {
            mToast = null;
        }
    }
}
