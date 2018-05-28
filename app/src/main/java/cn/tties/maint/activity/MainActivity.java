package cn.tties.maint.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.CommonSwipListViewAdapter;
import cn.tties.maint.adapter.EleAccountAdapter;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.bean.UserInfoBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.httpclient.params.CompanyParams;
import cn.tties.maint.httpclient.params.VersionParams;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleAccountResult;
import cn.tties.maint.httpclient.send.CompanyListSend;
import cn.tties.maint.httpclient.send.VersionSend;
import cn.tties.maint.util.ACache;
import cn.tties.maint.view.BaseCustomDialog;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.widget.DrawableUtil;
import cn.tties.maint.widget.MainFragmentManager;
import cn.tties.maint.widget.TabClass;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {
    public static MainActivity mMainActivityInstance;
    public MainFragmentManager manager;
    private int mInitIndex;
    private boolean mFristChecked;
    private HashMap<String, Integer> mTabHashMap;
    private StateListDrawable stateListDrawable;
    //一级  公司
    protected List<EleAccountResult> eleAccountList;
    protected EleAccountAdapter eleAccountAdapter;
    protected List<CompanyResult> companyList;
    ComapnyDialog comapnyDialog;
    protected int mChangeCompanyId = -1;
    protected Integer curEleId;
    protected String curEleNo;
    protected CompanyResult curCompany;
    @ViewInject(R.id.radiogroup)
    private RadioGroup radioGroup;
    @ViewInject(R.id.listview_lv1)
    private ListView holdListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityInstance = this;
        mTabHashMap = new HashMap<>();
        mFristChecked = false;
        new VersionSend().send(new VersionParams(), false);
        //获取公司信息
        CompanyParams params = new CompanyParams();
        params.setMaintStaffId(MyApplication.getUserInfo().getStaffId());
        new CompanyListSend().send(params);
        ACache.getInstance().put(Constants.CACHE_COMPANYLIST, new ArrayList<>());
        //展示公司信息并点击切换
        initEleIdListView();
        initCompanyList();
        //添加对应得fragment
        initView();
    }
    private void initCompanyList() {
        companyList = new ArrayList<>();
        eleAccountList = new ArrayList<>();
        List<CompanyResult> list = ACache.getInstance().getAsObject(Constants.CACHE_COMPANYLIST);
        if (list != null) {
            for (CompanyResult companyResult : list) {
                companyList.add(companyResult);
                if (companyResult.getCompanyId() == mChangeCompanyId) {
                    curCompany = companyResult;
                }
            }
        }
        if (companyList.size() > 0) {
            if (mChangeCompanyId == -1) {
                curCompany = companyList.get(0);
            }
            getEleAccountList();
        }
        mChangeCompanyId = -1;
        EventBusBean busBean = new EventBusBean();
        busBean.setKind(EventKind.EVENT_COMPANY_COMPANYBEAN);
        busBean.setObjs(curCompany);
        EventBus.getDefault().post(busBean);
    }
    protected void getEleAccountList() {
        eleAccountList = curCompany.getEleAccountList();
        if (eleAccountList.size() > 0) {
            curEleId = eleAccountList.get(0).getEleAccountId();
            curEleNo = eleAccountList.get(0).getEleNo();
            //发送公司电表ID到baseFragment,便于请求公用
            EventBusBean busBean = new EventBusBean();
            busBean.setKind(EventKind.EVENT_COMPANY_CHANGEID);
            busBean.setObj(curEleId);
            busBean.setObjs(curCompany);
            busBean.setMessage(curEleNo);
            EventBus.getDefault().post(busBean);
        } else {
            curEleId = null;
        }
        eleAccountAdapter.setCurEleId(curEleId);
        eleAccountAdapter.setCurCompany(curCompany);
        eleAccountAdapter.setEleAccountList(eleAccountList);
        eleAccountAdapter.notifyDataSetChanged();

//        changeEleAccountNextStep();
    }
    protected void initEleIdListView() {
        eleAccountAdapter = new EleAccountAdapter(this, this.eleAccountList);
        holdListView.setAdapter(eleAccountAdapter);
        holdListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {    //切换公司
                    comapnyDialog = new ComapnyDialog(MainActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int companyId = comapnyDialog.radioCompany.getCheckedRadioButtonId();
                            //如果切换公司
                            if (curCompany != companyList.get(companyId)) {
                                curCompany = companyList.get(companyId);
                                //公司ID
                                EventBusBean busBean = new EventBusBean();
                                busBean.setKind(EventKind.EVENT_COMPANY_CHANGE);
                                busBean.setObj(curCompany);
                                EventBus.getDefault().post(busBean);
                                getEleAccountList();

                            }
                            comapnyDialog.dismiss();
                        }
                    });
                    comapnyDialog.show();
                } else {                // 切换用电户号
                    curEleId = eleAccountList.get(position - 1).getEleAccountId();
                    curEleNo = eleAccountList.get(position - 1).getEleNo();
                    eleAccountAdapter.setCurEleId(curEleId);
                    eleAccountAdapter.notifyDataSetChanged();
                    //发送电表ID到baseFragment,便于请求公用
                    EventBusBean busBean = new EventBusBean();
                    busBean.setKind(EventKind.EVENT_COMPANY_CHANGEID);
                    busBean.setObj(curEleId);
                    busBean.setMessage(curEleNo);
                    EventBus.getDefault().post(busBean);
                }
            }
        });
    }
    /**
     * 初始化组件
     */
    private void initView() {
        UserInfoBean userInfoBean = ACache.getInstance().getAsObject(Constants.CACHE_USERINFO);
        List<TabClass> array = new ArrayList<>();
        for (TabClass tabClass : Constants.menuList) {
            if (userInfoBean.getMenuList().contains(tabClass.getAlias())) {
                array.add(tabClass);
            }
        }

        radioGroup.removeAllViews();
        radioGroup.setOnCheckedChangeListener(this);
        manager = MainFragmentManager.getInstance(getSupportFragmentManager(), array, R.id.realtabcontent);
        manager.showFragment(0);
        for (TabClass tabClass : array) {
            RadioButton button = (RadioButton) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_radiobutton, radioGroup, false);
            stateListDrawable = addStateDrawable(tabClass.getImage(), tabClass.getImagePressed());
            button.setCompoundDrawablesWithIntrinsicBounds(stateListDrawable, null, null, null);
            button.setText(tabClass.text);
            radioGroup.addView(button);
            mTabHashMap.put(tabClass.getAlias(), radioGroup.indexOfChild(button));
        }
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

    }

    private StateListDrawable addStateDrawable(int idNormal, int idPressed) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : getResources().getDrawable(idNormal);
        if (null != normal) {
            normal = DrawableUtil.zoomDrawable(normal, 41, 41);
            normal.setBounds(0, 0, normal.getMinimumWidth(), normal.getMinimumHeight());
        }
        Drawable pressed = idPressed == -1 ? null : getResources().getDrawable(idPressed);
        if (null != pressed) {
            pressed = DrawableUtil.zoomDrawable(pressed, 41, 41);
            pressed.setBounds(0, 0, pressed.getMinimumWidth(), pressed.getMinimumHeight());
        }
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, pressed);
        stateListDrawable.addState(new int[]{}, normal);
        return stateListDrawable;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (!mFristChecked) {
            mInitIndex = i - 1;
            mFristChecked = true;
        }
        i -= mInitIndex;
        int remainder =  i % (radioGroup.getChildCount());
        int index = remainder == 0 ? radioGroup.getChildCount() :  remainder ;
        int childCount = radioGroup.getChildCount();
        manager.showFragment(index - 1);
//            设置RadioButton选中后隐藏背景
//        RadioButton childAt= (RadioButton)radioGroup.getChildAt(index - 1);


    }

    @Override
    public void onBackPressed() {
        ConfirmDialog dialog = new ConfirmDialog(this);
        dialog.loadDialog("是否退出登录", null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                manager.clear();
                ActivityCollector.finishAll();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (manager != null) {
            manager.onDestory();
            manager = null;
        }
        super.onDestroy();
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public HashMap<String, Integer> getmTabHashMap() {

        return mTabHashMap;
    }
    class ComapnyDialog extends BaseCustomDialog {

        public RadioGroup radioCompany;

        public ComapnyDialog(Activity context, View.OnClickListener clickListener) {
            super(context, clickListener);
        }

        @Override
        protected void setContentView() {
            // 指定布局
            this.setContentView(R.layout.dialog_equipment_company);

            radioCompany = (RadioGroup) findViewById(R.id.radio_company);
            int i = 0;
            for (CompanyResult entity : companyList) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(entity.getCompanyShortName());
                radioButton.setId(i++);
                radioButton.setPadding(15, 15, 15, 15);
                radioButton.setTextColor(ContextCompat.getColor(x.app(), R.color.black));
                if (entity.getCompanyId() == curCompany.getCompanyId()) {
                    radioButton.setChecked(true);
                }
                radioCompany.addView(radioButton);
            }
            super.setContentView();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean bean) {
        if (bean.getKind().equals(EventKind.EVENT_COMPANYLIST)) {
            initCompanyList();
        }
        //公司id
        if (bean.getKind().equals(EventKind.EVENT_COMPANY_CHANGE)) {
            CompanyResult result = bean.getObj();
            curCompany = result;
            getEleAccountList();
        }
    }
}
