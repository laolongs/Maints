package cn.tties.maint.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import cn.tties.maint.holder.MainHolder;
import cn.tties.maint.httpclient.params.CompanyParams;
import cn.tties.maint.httpclient.params.VersionParams;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.EleAccountResult;
import cn.tties.maint.httpclient.send.CompanyListSend;
import cn.tties.maint.httpclient.send.VersionSend;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.view.AllCancelDialog;
import cn.tties.maint.view.AllEditorDialog;
import cn.tties.maint.view.BaseCustomDialog;
import cn.tties.maint.view.ConfirmDialog;
import cn.tties.maint.widget.DrawableUtil;
import cn.tties.maint.widget.MainFragmentManager;
import cn.tties.maint.widget.TabClass;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    public static MainActivity mMainActivityInstance;
    public MainFragmentManager manager;
    ComapnyDialog comapnyDialog;
    protected Integer curEleId;
    protected String curEleNo;
    protected CompanyResult curCompany=new CompanyResult();
    @ViewInject(R.id.mainl)
    private LinearLayout mainl;
    @ViewInject(R.id.listview_lv1)
    private ListView holdListView;
    @ViewInject(R.id.main_company_RL)
    private RelativeLayout companyRl;
    @ViewInject(R.id.main_company_tv)
    private TextView company_tv;
    @ViewInject(R.id.main_ele_RL)
    private RelativeLayout eleRL;
    @ViewInject(R.id.main_ele_tv)
    private TextView ele_tv;
    private MainHolder curholder;
    private ImageView ivCurrent;
    private TextView tvCurrent;
    private LinearLayout llCurrent;
    private int whilte;
    private int blue;
    private EquipmentCheckFragment equipment;
    private boolean iseditor;
    private List<CompanyResult> list;
    private Integer eleid;
    private String eleNo;
    boolean isSelect;//是否允许点击  电表配置  电房巡视  消缺  美化安规  除尘清理
    boolean isClick;//是否允许选择公司 和 户号

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityInstance = this;
        new VersionSend().send(new VersionParams(), false);
        //获取公司信息
        CompanyParams params = new CompanyParams();
        params.setMaintStaffId(MyApplication.getUserInfo().getStaffId());
        new CompanyListSend().send(params);
        ACache.getInstance().put(Constants.CACHE_COMPANYLIST, new ArrayList<>());

        //添加对应得fragment
    }

    private void initfindId() {
        whilte = Color.parseColor("#FFFFFF");
        blue = Color.parseColor("#1B92EE");
        curholder = new MainHolder(mainl);
        curholder.equipment.setOnClickListener(this);
        curholder.wordorder.setOnClickListener(this);

        curholder.ele_configuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSelect){
                    ToastUtil.showShort(MainActivity.this,"请从工单管理去处理");
                    return;
                }
            }
        });
        curholder.ele_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSelect){
                    ToastUtil.showShort(MainActivity.this,"请从工单管理去处理");
                    return;
                }
            }
        });
        curholder.eliminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSelect){
                    ToastUtil.showShort(MainActivity.this,"请从工单管理去处理");
                    return;
                }
            }
        });
        curholder.beautify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSelect){
                    ToastUtil.showShort(MainActivity.this,"请从工单管理去处理");
                    return;
                }
            }
        });
        curholder.dedusting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSelect){
                    ToastUtil.showShort(MainActivity.this,"请从工单管理去处理");
                    return;
                }
            }
        });


        curholder.question.setOnClickListener(this);
        curholder.setting.setOnClickListener(this);
        curholder.equipment_img.setSelected(true);
        curholder.equipment_tv.setSelected(true);
        curholder.equipment.setBackgroundColor(whilte);
        ivCurrent= curholder.equipment_img;
        tvCurrent= curholder.equipment_tv;
        llCurrent=curholder.equipment;
        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent,new EquipmentCheckFragment(eleid,curEleNo,curCompany)).commit();
        companyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isClick){
                    ToastUtil.showShort(MainActivity.this,"当前状态不允许选择企业");
                    return;
                }
                comapnyDialog = new ComapnyDialog(MainActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int companyId = comapnyDialog.radioCompany.getCheckedRadioButtonId();
                        //如果切换公司
                        if (curCompany != list.get(companyId)) {
                            curCompany = list.get(companyId);
                            eleid = curCompany.getEleAccountList().get(0).getEleAccountId();
                            eleNo = curCompany.getEleAccountList().get(0).getEleNo();
                            company_tv.setText(curCompany.getCompanyShortName());
                            ele_tv.setText(eleNo);
                            //公司ID
//                            setCompanyId(curCompany);
                            setEleId(eleid, eleNo,curCompany);
                        }
                        comapnyDialog.dismiss();
                    }
                });
                comapnyDialog.show();
            }
        });
        eleRL.setOnClickListener(new View.OnClickListener() {

            private EleDialog eleDialog;

            @Override
            public void onClick(View view) {
                if(isClick){
                    ToastUtil.showShort(MainActivity.this,"当前状态不允许更改户号");
                    return;
                }
                eleDialog = new EleDialog(MainActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int companyId = eleDialog.radioCompany.getCheckedRadioButtonId();
                        eleid=curCompany.getEleAccountList().get(companyId).getEleAccountId();
                        eleNo=curCompany.getEleAccountList().get(companyId).getEleNo();
                        ele_tv.setText(eleNo);
//                        if(curCompany.getEleAccountList()!=curCompany.getEleAccountList().get(companyId)){
//                            curCompany.getEleAccountList();
//                        }
                        setEleId(eleid, eleNo,curCompany);
                        eleDialog.dismiss();
                    }
                });
                eleDialog.show();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if(!iseditor){
//            setCompanyId(curCompany);
//            setEleId(eleid, eleNo,curCompany);
            ivCurrent.setSelected(false);
            tvCurrent.setSelected(false);
            llCurrent.setBackgroundColor(blue);
            switch (view.getId()) {
                //档案管理
                case R.id.equipment:
                    isSelect=false;
                    isClick=false;
                    //发送电表ID到baseFragment,便于请求公用
                    getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, new EquipmentCheckFragment(eleid,curEleNo, curCompany)).commit();
                    curholder.equipment_img.setSelected(true);
                    curholder.equipment_tv.setSelected(true);
                    curholder.equipment.setBackgroundColor(whilte);
                    ivCurrent = curholder.equipment_img;
                    tvCurrent = curholder.equipment_tv;
                    llCurrent = curholder.equipment;
                    break;
                //工单列表
                case R.id.wordorder:
                    isSelect=false;
                    isClick=false;
                    getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, new OrderListFragment(eleid,curEleNo, curCompany)).commit();
                    curholder.wordorder_img.setSelected(true);
                    curholder.wordorder_tv.setSelected(true);
                    curholder.wordorder.setBackgroundColor(whilte);
                    ivCurrent = curholder.wordorder_img;
                    tvCurrent = curholder.wordorder_tv;
                    llCurrent = curholder.wordorder;
                    break;
                //问题列表
                case R.id.question:
                    isSelect=false;
                    isClick=false;
                    getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, new QuestionFragment()).commit();
                    curholder.question_img.setSelected(true);
                    curholder.question_tv.setSelected(true);
                    curholder.question.setBackgroundColor(whilte);
                    ivCurrent = curholder.question_img;
                    tvCurrent = curholder.question_tv;
                    llCurrent = curholder.question;
                    break;
                //设置
                case R.id.setting:
                    isSelect=false;
                    isClick=false;
                    getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, new SettingFragment()).commit();
                    curholder.setting_img.setSelected(true);
                    curholder.setting_tv.setSelected(true);
                    curholder.setting.setBackgroundColor(whilte);
                    ivCurrent = curholder.setting_img;
                    tvCurrent = curholder.setting_tv;
                    llCurrent = curholder.setting;
                    break;
            }
        }else{
            dialog();
        }
    }
    public void setCompanyList(){
        list = ACache.getInstance().getAsObject(Constants.CACHE_COMPANYLIST);
        company_tv.setText(list.get(0).getCompanyShortName());
        ele_tv.setText(list.get(0).getEleAccountList().get(0).getEleNo());
        Integer companyId = list.get(0).getCompanyId();
        curCompany=list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if(companyId==list.get(i).getCompanyId()){
                eleid = list.get(i).getEleAccountList().get(0).getEleAccountId();
                eleNo = list.get(i).getEleAccountList().get(0).getEleNo();
            }
        }

//        setCompanyId(curCompany);
        setEleId(eleid, eleNo,curCompany);
        initfindId();
    }
//    public void setCompanyId(CompanyResult curCompany){
//        EventBusBean busBean = new EventBusBean();
//        busBean.setKind(EventKind.EVENT_COMPANY_COMPANYBEAN);
//        busBean.setObj(curCompany);
//        EventBus.getDefault().post(busBean);
//    }
    public void setEleId(int curEleId,String curEleNo,CompanyResult curCompany){
        EventBusBean busBean = new EventBusBean();
        busBean.setKind(EventKind.EVENT_COMPANY_CHANGEID);
        busBean.setEleID(curEleId);
        busBean.setObj(curCompany);
        busBean.setMessage(curEleNo);
        EventBus.getDefault().post(busBean);
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

    class ComapnyDialog extends BaseCustomDialog {

        public RadioGroup radioCompany;

        public ComapnyDialog(Activity context, View.OnClickListener clickListener) {
            super(context, clickListener);
        }

        @Override
        protected void setContentView() {
            // 指定布局
            this.setContentView(R.layout.dialog_company);
            radioCompany = (RadioGroup) findViewById(R.id.radio_company);
            int i = 0;
            for (CompanyResult entity : list) {
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
    class EleDialog extends BaseCustomDialog {

        public RadioGroup radioCompany;

        public EleDialog(Activity context, View.OnClickListener clickListener) {
            super(context, clickListener);
        }

        @Override
        protected void setContentView() {
            // 指定布局
            this.setContentView(R.layout.dialog_equipment_ele);
            radioCompany = (RadioGroup) findViewById(R.id.radio_company);
            int i = 0;
            for (EleAccountResult eleAccountList : curCompany.getEleAccountList()) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(eleAccountList.getEleNo());
                radioButton.setId(i++);
                radioButton.setPadding(15, 15, 15, 15);
                radioButton.setTextColor(ContextCompat.getColor(x.app(), R.color.black));
                if (eleAccountList.getEleAccountId() == eleid) {
                    radioButton.setChecked(true);
                }
                radioCompany.addView(radioButton);
            }
            super.setContentView();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean bean) {
        //全部公司信息
        if (bean.getKind().equals(EventKind.EVENT_COMPANYLIST)) {
            setCompanyList();
        }
        //禁止导航栏跳转
        if (bean.getKind().equals(EventKind.EVENT_COMPANY_ELEISEDITOR)) {
            iseditor = bean.getSuccess();
        }
        //是否来自工单管理
        if (bean.getKind().equals(EventKind.EVENT_COMPANY_ISFORORDER)) {
            isSelect = bean.getSuccess();
        }
    }

    public void dialog(){
        AllEditorDialog dialog1=new AllEditorDialog();
        dialog1.loadDialog("尚未保存当前页面，确认放弃？", new AllEditorDialog.OnClickIsConfirm() {
            @Override
            public void OnClickIsConfirmListener() {
                return;
            }
        }, new AllEditorDialog.OnClickIsCancel() {
            @Override
            public void OnClickIsCancelListener() {
                iseditor=false;

            }
        });
    }

    public void shwoFragment(int fragmentId,int workOrderId,int workType){
            ivCurrent.setSelected(false);
            tvCurrent.setSelected(false);
            llCurrent.setBackgroundColor(blue);
            switch (fragmentId) {
                //电表配置
                case R.id.ele_configuration:
                        isClick=true;
                        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, new AmmeterFragment(eleid,curEleNo, curCompany,workOrderId)).commit();
                        curholder.ele_configuration_img.setSelected(true);
                        curholder.ele_configuration_tv.setSelected(true);
                        curholder.ele_configuration.setBackgroundColor(whilte);
                        ivCurrent = curholder.ele_configuration_img;
                        tvCurrent = curholder.ele_configuration_tv;
                        llCurrent = curholder.ele_configuration;
                    break;
                //电房巡视
                case R.id.ele_tour:

                        isClick=true;
                        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, new PatrolFragment(eleid,curEleNo, curCompany,workOrderId)).commit();
                        curholder.ele_tour_img.setSelected(true);
                        curholder.ele_tour_tv.setSelected(true);
                        curholder.ele_tour.setBackgroundColor(whilte);
                        ivCurrent = curholder.ele_tour_img;
                        tvCurrent = curholder.ele_tour_tv;
                        llCurrent = curholder.ele_tour;
                    break;
                //消缺
                case R.id.eliminate:
                        isClick=true;
                        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, new FaultFragment(eleid,curEleNo, curCompany,workOrderId,workType)).commit();
                        curholder.eliminate_img.setSelected(true);
                        curholder.eliminate_tv.setSelected(true);
                        curholder.eliminate.setBackgroundColor(whilte);
                        ivCurrent = curholder.eliminate_img;
                        tvCurrent = curholder.eliminate_tv;
                        llCurrent = curholder.eliminate;
                    break;
                //美化安规
                case R.id.beautify:
                        isClick=true;
                        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, new PrettiftFragment(eleid,curEleNo, curCompany,workOrderId)).commit();
                        curholder.beautify_img.setSelected(true);
                        curholder.beautify_tv.setSelected(true);
                        curholder.beautify.setBackgroundColor(whilte);
                        ivCurrent = curholder.beautify_img;
                        tvCurrent = curholder.beautify_tv;
                        llCurrent = curholder.beautify;
                    break;
                //除尘清理
                case R.id.dedusting:
                        isClick=true;
                        getSupportFragmentManager().beginTransaction().replace(R.id.realtabcontent, new DustFragment(eleid,curEleNo, curCompany,workOrderId)).commit();
                        curholder.dedusting_img.setSelected(true);
                        curholder.dedusting_tv.setSelected(true);
                        curholder.dedusting.setBackgroundColor(whilte);
                        ivCurrent = curholder.dedusting_img;
                        tvCurrent = curholder.dedusting_tv;
                        llCurrent = curholder.dedusting;
                    break;
            }
    }
}
