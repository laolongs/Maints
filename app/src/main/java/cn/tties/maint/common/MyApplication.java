package cn.tties.maint.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.tties.maint.R;
import cn.tties.maint.bean.UserInfoBean;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.NetWorkUtils;
import cn.tties.maint.util.XUtils3ImageLoader;
import cn.tties.maint.view.CriProgressDialog;
import cn.tties.maint.view.InfoDialog;
import cn.tties.maint.view.SelectDialog;

/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {

    public static Context instance;

    public static Activity curActivity;

    public static Boolean mNetWorkState;

    public static MyApplication myInstance;

    public static CriProgressDialog waitDialog;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        myInstance = this;
        // x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

        // 全局默认信任所有https域名 或 仅添加信任的https域名
        // 使用RequestParams#setHostnameVerifier(..)方法可设置单次请求的域名校验
        x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        instance = getApplicationContext();
        mNetWorkState = NetWorkUtils.isNetworkConnected(x.app());
        daoConfig = new DbManager.DaoConfig()
                .setDbName("energy_db")//创建数据库的名称
                .setDbVersion(1)//数据库版本号
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
//                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ..
                        // db.addColumn(..);
                        // db.dropTable(..);
                        // ..
                    }
                });//数据库更新操作

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                curActivity = activity;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity,
                                                    Bundle outState) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                curActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }

            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {
                curActivity = activity;
            }
        });
        initImageLoad();
    }

    public static void showSuccessInfo(String successStr) {
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.loadDialog(true , successStr, null);
    }

    public static void showFaileInfo(String failStr, String errMsg) {
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.loadDialog(false , failStr, errMsg);
    }

    public static void showWaitDialog() {
        waitDialog = new CriProgressDialog(curActivity);
        waitDialog.loadDialog("请等待...");
    }

    private static DbManager.DaoConfig daoConfig;

    public static DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    public static UserInfoBean getUserInfo() {
        UserInfoBean userInfo = ACache.getInstance().getAsObject(Constants .CACHE_USERINFO);
        return userInfo;
    }

    public static SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(MyApplication.curActivity, R.style
                .transparentFrameWindowStyle,
                listener, names);
        return dialog;
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    private void initImageLoad() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new XUtils3ImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);
    }
}
