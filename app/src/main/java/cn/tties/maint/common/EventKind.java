package cn.tties.maint.common;

/**
 * eventbus 事件类
 */
public class EventKind {
    //公司列表
    public static final String EVENT_COMPANYLIST = "EVENT_COMPANYLIST";
    //切换公司
    public static final String EVENT_COMPANY_CHANGE = "EVENT_COMPANY_CHANGE";
    //获取公司id
    public static final String EVENT_COMPANY_COMPANYBEAN = "EVENT_COMPANY_COMPANYBEAN";
    //切换公司下得表号ID
    public static final String EVENT_COMPANY_CHANGEID = "EVENT_COMPANY_CHANGEID";
    //activity创建  新建+更新添加过的二级列表
    public static final String EVENT_COMPANY_ADD = "EVENT_COMPANY_ADD";
    //activity创建  编辑过的二级列表
    public static final String EVENT_COMPANY_EDITOR = "EVENT_COMPANY_EDITOR";


    //fragment创建  新建更新添加过的二级列表
    public static final String EVENT_FRAGMENTCOMPANY_ADD = "EVENT_FRAGMENTCOMPANY_ADD";
    //fragment创建  编辑过的二级列表
    public static final String EVENT_FRAGMENTCOMPANY_EDITOR = "EVENT_FRAGMENTCOMPANY_EDITOR";

    //检查版本
    public static final String EVENT_VERSION_SYCN = "EVENT_VERSION_SYCN";

    //图片选择
    public static final int REQUEST_CODE_SELECT = 100;
    //图片预览
    public static final int REQUEST_CODE_PREVIEW = 101;
    //文件选择
    public static final int FILE_SELECT_CODE = 102;
    //路径
    public static final int PDF_PATH = 103;
}