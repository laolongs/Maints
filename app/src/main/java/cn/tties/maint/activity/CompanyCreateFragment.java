package cn.tties.maint.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.mobsandgeeks.saripaar.QuickRule;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.AreaDao;
import cn.tties.maint.dao.IndustryDao;
import cn.tties.maint.entity.AreaEntity;
import cn.tties.maint.entity.IndustryEntity;
import cn.tties.maint.holder.EleAccountsLayoutHolder;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CreateCompanyParams;
import cn.tties.maint.httpclient.params.QueryMaintParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.MbStaffResult;
import cn.tties.maint.util.DateUtils;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;

/**
 * 创建企业.
 */
@ContentView(R.layout.fragment_company_create)
public class CompanyCreateFragment extends BaseFragment {

    private static final String TAG = "CompanyCreateFragment";

    @ViewInject(R.id.edit_company_name)
    @NotEmpty(message = "企业名称不能为空")
    private EditText editCompanyName;

    @ViewInject(R.id.edit_company_short_name)
    @NotEmpty(message = "企业简称不能为空")
    private EditText editCompanyShortName;

    @ViewInject(R.id.edit_company_addr)
    @NotEmpty(message = "企业地址名不能为空")
    private EditText editCompanyAddr;

    @ViewInject(R.id.edit_agent_name)
    @NotEmpty(message = "代理人不能为空")
    private EditText editAgentName;

    @ViewInject(R.id.edit_ele_account)
    private EditText editEleAccount;

    @ViewInject(R.id.edit_ele_volume)
    private EditText editEleVolume;

    @ViewInject(R.id.layout_ele_account)
    private LinearLayout layoutEleAccount;

    @ViewInject(R.id.image_add)
    private ImageView imageAdd;

    @ViewInject(R.id.edit_sms_tel)
    private EditText editSmsTel;

    @ViewInject(R.id.edit_sms_tel2)
    private EditText editSmsTel2;

    @ViewInject(R.id.edit_transformer_capacity)
    @NotEmpty(message = "变压器容量不能为空")
    private EditText editTransformerCapacity;

    @ViewInject(R.id.edit_transformer_count)
    @NotEmpty(message = "变压器数量不能为空")
    private EditText editTransformerCount;

    @ViewInject(R.id.edit_startDate)
    @NotEmpty(message = "开始日期不能为空")
    private EditText editStartDate;

    @ViewInject(R.id.edit_endDate)
    @NotEmpty(message = "结束日期不能为空")
    private EditText editEndDate;
    @ViewInject(R.id.edit_month_money)
    @NotEmpty(message = "月服务费不能为空")
    private EditText editMonthMoney;

    @ViewInject(R.id.edit_business_name)
    @NotEmpty(message = "商务负责人不能为空")
    private EditText editBusinessName;

    @ViewInject(R.id.edit_business_tel)
    @NotEmpty(message = "联系方式不能为空")
    @Pattern(regex = "^[\\d-]*$", message = "联系方式不符合电话号码格式")
    private EditText editBusinessTel;

    @ViewInject(R.id.edit_finance_name)
    @NotEmpty(message = "财务负责人不能为空")
    private EditText editFinanceName;

    @ViewInject(R.id.edit_finance_tel)
    @NotEmpty(message = "联系方式不能为空")
    @Pattern(regex = "^[\\d-]*$", message = "联系方式不符合电话号码格式")
    private EditText editFinanceTel;

    @ViewInject(R.id.edit_tech_name)
    @NotEmpty(message = "技术负责人不能为空")
    private EditText editTechName;

    @ViewInject(R.id.edit_tech_tel)
    @NotEmpty(message = "联系方式不能为空")
    @Pattern(regex = "^[\\d-]*$", message = "联系方式不符合电话号码格式")
    private EditText editTechTel;

    @ViewInject(R.id.spinner_maint)
    private Spinner spinnerMaint;

    @ViewInject(R.id.spinner_industry)
    private Spinner spinnerIndustry;

    @ViewInject(R.id.spinner_area)
    private Spinner spinnerArea;

    List<EditText> eleAccountList;

    List<EditText> volumeList;

    private List<MbStaffResult> queryMaintResultList;

    private List<AreaEntity> areaList;

    private List<IndustryEntity> industryList;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDataEditText(editStartDate);
        setDataEditText(editEndDate);
        eleAccountList = new ArrayList<EditText>();
        volumeList = new ArrayList<EditText>();
        eleAccountList.add(editEleAccount);
        volumeList.add(editEleVolume);
        validator.put(editEleAccount, new MuiltEleAccountRule());
        validator.put(editEleVolume, new MuiltVolumeRule());
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EleAccountsLayoutHolder holder = new EleAccountsLayoutHolder(null);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutEleAccount.removeView(holder.contentView);
                        eleAccountList.remove(holder.editEleAccount);
                        volumeList.remove(holder.editEleVolume);
                    }
                });
                layoutEleAccount.addView(holder.contentView);
                eleAccountList.add(holder.editEleAccount);
                volumeList.add(holder.editEleVolume);
                validator.put(holder.editEleAccount, new MuiltEleAccountRule());
                validator.put(holder.editEleVolume, new MuiltVolumeRule());
            }
        });
        validator.put(editSmsTel, new MuiltEditSmsTelRule());
        validator.put(editSmsTel2, new MuiltEditSmsTelRule());

        // 获取运维专员列表
        this.getMaintList();
        areaList = AreaDao.getInstance().queryProvince();
        List<String> areaStr = new ArrayList<>();
        for (AreaEntity entity : areaList) {
            areaStr.add(entity.getAreaName());
        }
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, areaStr);
        spinnerArea.setAdapter(areaAdapter);

        industryList = IndustryDao.getInstance().queryAll();
        List<String> industryStr = new ArrayList<>();
        for (IndustryEntity entity : industryList) {
            industryStr.add(entity.getIndustryName());
        }
        ArrayAdapter<String> industryAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, industryStr);
        spinnerIndustry.setAdapter(industryAdapter);
    }

    /**
     * 获取运维专员列表.
     */
    private void getMaintList() {
        HttpClientSend.getInstance().send(new QueryMaintParams(), new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取运维专员失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> strList = new ArrayList<String>();
                queryMaintResultList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<MbStaffResult>>() {
                }.getType());
                for (MbStaffResult queryMaintResult : queryMaintResultList) {
                    strList.add(queryMaintResult.getStaffName());
                }
                ArrayAdapter<String> operationAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, strList);
                spinnerMaint.setAdapter(operationAdapter);
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        CreateCompanyParams createCompanyParams = new CreateCompanyParams();

        createCompanyParams.setCompanyName(editCompanyName.getText().toString());
        createCompanyParams.setCompanyAddr(editCompanyAddr.getText().toString());
        createCompanyParams.setAgentName(editAgentName.getText().toString());
        createCompanyParams.setCompanyShortName(editCompanyShortName.getText().toString());
        String eleAccounts = "";
        for (int i = 0; i < eleAccountList.size(); i++) {
            if (!StringUtil.isEmpty(eleAccountList.get(i).getText().toString())) {
                eleAccounts += eleAccountList.get(i).getText().toString() + ",";
            }
        }
        createCompanyParams.setEleAccounts(eleAccounts);
        String eleVolumes = "";
        for (int i = 0; i < volumeList.size(); i++) {
            if (!StringUtil.isEmpty(volumeList.get(i).getText().toString())) {
                eleVolumes += volumeList.get(i).getText().toString() + ",";
            }
        }
        createCompanyParams.setEleVolumes(eleVolumes);
        String smsTel2 = "".equals(editSmsTel2.getText().toString().trim()) ? "" : "," + editSmsTel2.getText().toString();
        createCompanyParams.setSmsTel(editSmsTel.getText().toString() + smsTel2);
        createCompanyParams.setTransformerCapacity(Integer.parseInt(editTransformerCapacity.getText().toString()));
        createCompanyParams.setTransformerCount(Integer.valueOf(editTransformerCount.getText().toString()));
        createCompanyParams.setStartDate(DateUtils.stringToInt(editStartDate.getText().toString()));
        createCompanyParams.setEndDate(DateUtils.stringToInt(editEndDate.getText().toString()));
        createCompanyParams.setMonthMoney(Double.valueOf(editMonthMoney.getText().toString()));
        createCompanyParams.setBusinessName(editBusinessName.getText().toString());
        createCompanyParams.setBusinessTel(editBusinessTel.getText().toString());
        createCompanyParams.setFinanceName(editFinanceName.getText().toString());
        createCompanyParams.setFinanceTel(editFinanceTel.getText().toString());
        createCompanyParams.setTechName(editTechName.getText().toString());
        createCompanyParams.setTechTel(editTechTel.getText().toString());
        createCompanyParams.setStatus(0);
        createCompanyParams.setCreatorId(MyApplication.getUserInfo().getStaffId());
        int selectMaintIndex = spinnerMaint.getSelectedItemPosition();
        MbStaffResult selectMaint = queryMaintResultList.get(selectMaintIndex);
        createCompanyParams.setMaintStaffId(selectMaint.getStaffId());
        int selectAreaIndex = spinnerArea.getSelectedItemPosition();
        AreaEntity areaEntity = areaList.get(selectAreaIndex);
        createCompanyParams.setAreaId(areaEntity.getAreaId());
        int selectIndustryIndex = spinnerIndustry.getSelectedItemPosition();
        IndustryEntity industryEntity = industryList.get(selectIndustryIndex);
        createCompanyParams.setIndustryId(industryEntity.getIndustryId());
        HttpClientSend.getInstance().send(createCompanyParams, new BaseAlertCallback("新增成功","新增失败") {
            @Override
            public void onSuccessed(String result) {
                // 更新企业列表
                clearInput();
                CompanyListFragment.companyListFragmentInstance.queryCompanyList();
            }
        });
    }

    @Event(value = {R.id.btn_save})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                validator.validate();
        }
    }

    class MuiltEleAccountRule extends QuickRule<EditText> {

        boolean ematyFlag = true;
        boolean sameFlag = false;
        @Override
        public boolean isValid(EditText view) {
            ematyFlag = true;
            sameFlag = true;
            boolean isValid = true;
            // Do some clever validation...
            int i = 0;
            for (EditText editText : eleAccountList) {
                if (!StringUtil.isEmpty(editText.getText().toString())) {
                    ematyFlag = false;
                }
                i++;
            }
            if (ematyFlag) {
                return false;
            }
            HashSet<String> hashSet = new HashSet<String>();
            for (EditText editText : eleAccountList) {
                if (StringUtil.isEmpty(editText.getText().toString())) {
                    continue;
                }
                if (hashSet.contains(editText.getText().toString())) {
                    sameFlag = false;
                    return false;
                } else {
                    hashSet.add(editText.getText().toString());
                }
            }
            return isValid;
        }

        @Override
        public String getMessage(Context context) {
            // Do some clever validation....
            String msg = "至少输入一个用电户号";
            if (!sameFlag) {
                msg = "用电户号输入重复";
            }
            return msg;
        }
    }

    class MuiltVolumeRule extends QuickRule<EditText> {

        boolean volEmptyFlag = false;

        @Override
        public boolean isValid(EditText view) {
            volEmptyFlag = false;
            boolean isValid = true;
            // Do some clever validation...
            int i = 0;
            for (EditText editText : eleAccountList) {
                if (!StringUtil.isEmpty(editText.getText().toString())) {
                    if (StringUtil.isEmpty(volumeList.get(i).getText().toString())) {
                        volEmptyFlag = true;
                        break;
                    }
                }
                i++;
            }
            if (volEmptyFlag) {
                return false;
            }
            return isValid;
        }

        @Override
        public String getMessage(Context context) {
            // Do some clever validation....
            String msg = "电号对应容量未输入";
            return msg;
        }
    }

    private int errCode = 0;

    class MuiltEditSmsTelRule extends QuickRule<EditText> {

        @Override
        public boolean isValid(EditText view) {
            boolean flag = false;
            if (StringUtil.isEmpty(editSmsTel.getText().toString()) && StringUtil.isEmpty(editSmsTel2.getText().toString())) {
                return flag;
            }
            String telRegex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
            if (!StringUtil.isEmpty(view.getText().toString()) && !view.getText().toString().matches(telRegex)) {
                errCode = 1;
                return flag;
            }
            return true;

        }

        @Override
        public String getMessage(Context context) {
            // Do some clever validation....
            if (errCode == 1) {
                return "手机号码格式错误";
            }
            return "至少输入一个短信接收手机号";
        }
    }

    private void clearInput() {
        setClear(editCompanyName);
        setClear(editCompanyAddr);
        setClear(editCompanyShortName);
        setClear(editAgentName);
        for (EditText editText : eleAccountList) {
            validator.removeRules(editText);
        }
        setClear(editEleAccount);
        setClear(editEleVolume);
        validator.put(editEleAccount, new MuiltEleAccountRule());
        validator.put(editEleVolume, new MuiltVolumeRule());
        layoutEleAccount.removeAllViews();

        setClear(editSmsTel);
        setClear(editSmsTel2);
        setClear(editTransformerCapacity);
        setClear(editTransformerCount);
        setClear(editStartDate);
        setClear(editEndDate);
        setClear(editMonthMoney);
        setClear(editBusinessTel);
        setClear(editBusinessName);
        setClear(editFinanceTel);
        setClear(editFinanceName);
        setClear(editTechName);
        setClear(editTechTel);

        eleAccountList = new ArrayList<EditText>();
        eleAccountList.add(editEleAccount);

        volumeList = new ArrayList<EditText>();
        volumeList.add(editEleVolume);

        spinnerMaint.setSelection(0);
        spinnerIndustry.setSelection(0);
        spinnerArea.setSelection(0);
    }
}