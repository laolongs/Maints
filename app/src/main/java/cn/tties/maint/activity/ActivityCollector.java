package cn.tties.maint.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity管理.
 */
public class ActivityCollector {

    /**
     *
     */
    private static List<Activity> activities = new ArrayList<Activity>();

    /**
     *
     * @param activity Activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     *
     * @param activity Activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     *
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            if (activity.isFinishing()) {
                continue;
            }
            activity.finish();
        }
    }
}
