package cn.tties.maint.common;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.AmmeterFragment;
import cn.tties.maint.activity.CompanyCreateFragment;
import cn.tties.maint.activity.CompanyListFragment;
import cn.tties.maint.activity.ContractCheckFragment;
import cn.tties.maint.activity.EleBillUploadFragment;
import cn.tties.maint.activity.EleRoomCheckFragment;
import cn.tties.maint.activity.EquipmentCheckFragment;
import cn.tties.maint.activity.OrderListFragment;
import cn.tties.maint.activity.OverhaulFragment;
import cn.tties.maint.activity.PatrolFragment;
import cn.tties.maint.activity.QuestionFragment;
import cn.tties.maint.activity.SettingFragment;
import cn.tties.maint.activity.StaffCreateFragment;
import cn.tties.maint.activity.StaffListFragment;
import cn.tties.maint.widget.TabClass;

/**
 *
 */
public class Constants {
//    public static final String BASE_URL = "http://106.3.129.212:8680/";//测试环境运维
    public static final String BASE_URL = "http://192.168.2.127:8080/";//本地
//    public static final String BASE_URL = "http://192.168.2.116:8081/";//康维杰
//    public static final String BASE_URL = "http://192.168.2.49:8080/";//李震
//    public static final String BASE_URL = "http://maint.tties.cn/";
    public static final String API_URL = BASE_URL + "api/";
    public static final String CACHE_LOGIN_STATUS = "CACHE_LOGIN_STATUS";
    public static final String CACHE_USERINFO = "CACHE_USERINFO";
    public static final String CACHE_VERSION = "CACHEE_VERSION";
    public static final String DOWNLOAD_ID = "DOWNLOAD_ID";

    public static final String CACHE_COMPANYLIST = "CACHE_COMPANYLIST";
    //用于存当前公司数据
    public static final String CACHE_COMPANYBEAN = "CACHE_COMPANYBEAN";
    //用于存当前公司户号ID
    public static final String CACHE_COMPANYELEID = "CACHE_COMPANYELEID";


    public static List<TabClass> menuList;

    static {
        menuList = new ArrayList<>();
//        menuList.add(new TabClass("createStaff", StaffCreateFragment.class, "创建账号", R.mipmap.ic_create_staff, R.mipmap.ic_create_staff_selected));
//        menuList.add(new TabClass("staffList", StaffListFragment.class, "管理账号", R.mipmap.ic_staff_list, R.mipmap.ic_staff_list_selected));
        //运维人员
        menuList.add(new TabClass("wordOrderMgr", OrderListFragment.class, "工单列表", R.mipmap.workorder_unselect, R.mipmap.workorder_select));
//        menuList.add(new TabClass("createCompany", CompanyCreateFragment.class, "创建企业", R.mipmap.ic_create_company, R.mipmap.ic_create_company_selected));
//        menuList.add(new TabClass("companyList", CompanyListFragment.class, "管理企业", R.mipmap.ic_company_list, R.mipmap.ic_company_list_selected));
//        menuList.add(new TabClass("eleBillMgr", EleBillUploadFragment.class, "电费单上传", R.mipmap.ic_ele_bill, R.mipmap.ic_ele_bill_selected));
//        menuList.add(new TabClass("contractMgr", ContractCheckFragment.class, "合同管理", R.mipmap.ic_contract, R.mipmap.ic_contract_selected));
        //运维人员
        menuList.add(new TabClass("equipmentMgr", EquipmentCheckFragment.class, "管理档案", R.mipmap.file_unselect, R.mipmap.file_select));
        //运维人员
        menuList.add(new TabClass("eleRoomMgr", EleRoomCheckFragment.class, "管理电房", R.mipmap.list_unselect, R.mipmap.list_select));
        //运维人员
//        menuList.add(new TabClass("patrolMgr", PatrolFragment.class, "电房巡视", R.mipmap.ic_room_patrol, R.mipmap.ic_room_patrol_selected));
        //运维人员
//        menuList.add(new TabClass("overhaul", OverhaulFragment.class, "电房检修", R.mipmap.ic_room_overhaul, R.mipmap.ic_room_overhaul_selected));
        //运维人员
        menuList.add(new TabClass("ammeterSettingMgr", AmmeterFragment.class, "电表配置", R.mipmap.list_unselect, R.mipmap.list_select));
        //运维人员
        menuList.add(new TabClass("questionMgr", QuestionFragment.class, "问题处理", R.mipmap.question_unselect, R.mipmap.question_select));
        menuList.add(new TabClass("settingMgr", SettingFragment.class, "设置", R.mipmap.setting_unselect, R.mipmap.setting_select));
    }
}
