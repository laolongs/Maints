package cn.tties.maint.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;

import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.util.ToastUtil;

/**
 * Created by wyouflf on 15/11/4.
 */
public class BaseFragmentActivity extends FragmentActivity {
    private static final String TAG = "BaseFragmentActivity";
    //公司电表ID
    protected Integer curEleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        x.view().inject(this);
        EventBus.getDefault().register(this);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public void oneActivityDo() {
        onResume();
    }
    public String setNullEdit(Object str) {
        if (str == null) {
            return "";
        }
        return String.valueOf(str);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean bean) {
    }

}
