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

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import org.xutils.x;

import java.io.File;

public class DownloadUtils {

    private DownloadManager downloadManager;

    private static DownloadUtils downloadUtils;

    public static DownloadUtils getInstance() {
        if (downloadUtils == null) {
            downloadUtils = new DownloadUtils();
        }
        return downloadUtils;
    }

    private DownloadManager getDownloadManager() {
        if (downloadManager == null) {
            downloadManager = (DownloadManager) x.app().getSystemService(Context.DOWNLOAD_SERVICE);
        }
        return downloadManager;
    }

    public long download(String downloadUrl, String title, String description) {

        // 创建下载请求
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        /*
         * 设置在通知栏是否显示下载通知(下载进度), 有 3 个值可选:
         *    VISIBILITY_VISIBLE:                   下载过程中可见, 下载完后自动消失 (默认)
         *    VISIBILITY_VISIBLE_NOTIFY_COMPLETED:  下载过程中和下载完成后均可见
         *    VISIBILITY_HIDDEN:                    始终不显示通知
         */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置通知的标题和描述
        request.setTitle(title);
        request.setDescription(description);
        /*
         * 设置允许使用的网络类型, 可选值:
         *     NETWORK_MOBILE:      移动网络
         *     NETWORK_WIFI:        WIFI网络
         *     NETWORK_BLUETOOTH:   蓝牙网络
         * 默认为所有网络都允许
         */
        // request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 添加请求头
        // request.addRequestHeader("User-Agent", "Chrome Mozilla/5.0");

        // 设置下载文件的保存位置
        File saveFile = new File(Environment.getExternalStorageDirectory(), "demo.apk");
        request.setDestinationUri(Uri.fromFile(saveFile));

        /*
         * 2. 获取下载管理器服务的实例, 添加下载任务
         */
        DownloadManager manager = getDownloadManager();

        // 将下载请求加入下载队列, 返回一个下载ID
        long downloadId = manager.enqueue(request);

        // 如果中途想取消下载, 可以调用remove方法, 根据返回的下载ID取消下载, 取消下载后下载保存的文件将被删除
        // manager.remove(downloadId);
        return downloadId;
    }

    public void showDownloadResult(long downloadId) {
        // 创建一个查询对象
        DownloadManager.Query query = new DownloadManager.Query();
        // 根据 下载ID 过滤结果
        query.setFilterById(downloadId);
        // 还可以根据状态过滤结果
        // query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
        // 执行查询, 返回一个 Cursor (相当于查询数据库)
        Cursor cursor = downloadManager.query(query);
        if (!cursor.moveToFirst()) {
            cursor.close();
            return;
        }
        // 下载ID
        long id = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
        // 下载请求的状态
        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
        // 下载文件在本地保存的路径（Android 7.0 以后 COLUMN_LOCAL_FILENAME 字段被弃用, 需要用 COLUMN_LOCAL_URI 字段来获取本地文件路径的 Uri）
//        String localFilename = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
        // 已下载的字节大小
        Long downloadedSoFar = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
        // 下载文件的总字节大小
        Long totalSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
        String res = "";
        if (status == DownloadManager.STATUS_SUCCESSFUL) {
            res = "下载成功";
        } else if (status == DownloadManager.STATUS_FAILED) {
            res = "下载失败";
        }
        Toast.makeText(x.app(),res, Toast.LENGTH_SHORT).show();
        cursor.close();
    }

    private boolean isDowning(String uri) {
        boolean flag = false;
        try {
            DownloadManager.Query query = new DownloadManager.Query();

            query.setFilterByStatus(DownloadManager.STATUS_RUNNING);
            Cursor c = getDownloadManager().query(query);
            String downingURI;
            while (c.moveToNext()) {
                downingURI = c.getString(c
                        .getColumnIndex(DownloadManager.COLUMN_URI));
                if (downingURI.equalsIgnoreCase(uri)) {
                    flag = true;
                    break;
                }
            }
            if (c != null) {
                c.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
}