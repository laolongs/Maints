package cn.tties.maint.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.tties.maint.bean.EquipmentLayoutBean;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.secondLv.DataBean;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.DateUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.util.ToastUtil;
import cn.tties.maint.widget.CustomDatePicker;

/**
 * Created by wyouflf on 15/11/4.
 */
public abstract class BaseFragment extends Fragment implements Validator.ValidationListener {
    //用于存储公司id
    private CompanyResult curCompany;
    //公司电表ID
    private Integer curEleId;
    //公司电表电量
    private String curEleNo;
    private boolean injected = false;

    protected Validator validator;

    List<CompanyResult> companylist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        validator = new Validator(this);
        validator.setValidationListener(this);
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onValidationSucceeded() {
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(x.app());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(x.app(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public String setNullEdit(Object str) {
        if (str == null) {
            return "";
        }
        return String.valueOf(str);
    }

    public void setDataEditText(final EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(editText);
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    showDatePickerDialog(editText);
                }
            }
        });
        if (!StringUtil.isEmpty(editText.getText().toString()) && editText.getText().toString().length() < 10) {
            editText.setText(DateUtils.getEditFormatDateStr(Integer.valueOf(editText.getText().toString())));
        }
    }

    public void showDatePickerDialog(final EditText editText) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        if (StringUtil.isEmpty(editText.getText().toString())) {
            editText.setText(now.split(" ")[0]);
        }
        CustomDatePicker cuys =  new CustomDatePicker(this.getContext(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                editText.setText(time.split(" ")[0]);
            }
        }, "1900-01-01 00:00", "2099-01-01 00:00");
        cuys.showSpecificTime(false);
        cuys.setIsLoop(true); // 允许循环滚动
        cuys.show(editText.getText().toString());
    }

    protected void setClear(EditText editText) {
        editText.setText("");
        editText.setError(null,null);
    }
    public void changeEleAccountNextSteps(Integer curEleId,String curEleNo,CompanyResult curCompany){};
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean bean) {
        if (bean.getKind().equals(EventKind.EVENT_COMPANY_CHANGEID)) {
            //电号ID
            curEleId = bean.getEleID();
            curCompany = bean.getObj();
            //总电量
            curEleNo = bean.getMessage();
            ToastUtil.showShort(getActivity(),""+curEleId);
            changeEleAccountNextSteps(curEleId,curEleNo,curCompany);
        }
        //公司id  //公司bean 便于获取到当前得公司ID;
//        if (bean.getKind().equals(EventKind.EVENT_COMPANY_COMPANYBEAN)) {
//            curCompany = bean.getObj();
////            ToastUtil.showShort(getActivity(),""+curCompany.getCompanyId());
//        }

    }
}
