package com.example.ruolan.letgo.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuwen on 2017/7/28.
 */
public class DateTimeUtils {

    public static String getCurrentTime_Today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date());
    }
}
