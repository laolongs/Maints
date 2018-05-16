/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.tties.maint.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.xutils.x;

import cn.tties.maint.common.Constants;
import cn.tties.maint.httpclient.result.LoginResult;

/**
 * @author Michael Yang（www.yangfuhai.com） update at 2013.08.07
 */
@SuppressWarnings("unchecked")
public class AppUtils {
    private AppUtils() {
                /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    public static LoginResult getUsertInfo() {
        LoginResult ret = ACache.getInstance().getAsObject(Constants.CACHE_USERINFO);
        return ret;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            Context context = x.app();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static int getVersionCode() {
        try {
            Context context = x.app();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName() {
        try {
            Context context = x.app();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    public static long getDownLoad() {
        Long id = ACache.getInstance().getAsObject(Constants.DOWNLOAD_ID);
        return id == null ? 0L : id;
    }

    public static void saveDownLoad(long id) {
        ACache.getInstance().put(Constants.DOWNLOAD_ID, id);
    }

    public static boolean isWifiConnected() {
        Context context = x.app();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static void setListViewHeight(ListView listView) {
        setListViewHeight(listView, 0);
    }

    public static void setListViewHeight(ListView listView, int heightFix) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1) + heightFix);
        listView.setLayoutParams(params);
    }
}