package cn.tties.maint.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.activity.ContractCheckFragment;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.CompanyParams;
import cn.tties.maint.httpclient.params.UploadContractParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.util.JsonUtils;


/**
 * 上传合同弹出框
 * author chensi
 */
public class UploadContractDialog extends BaseCustomDialog {
    Spinner spinnerCompany;
    File file;
    BaseFragment mFragment;
    int staffId;
    List<CompanyResult> comapnyList;
    ArrayAdapter companyAdapter;
    EditText editFile;

    public UploadContractDialog(BaseFragment fragment, Integer staffId) {
        super(MyApplication.curActivity, null);
        this.staffId = staffId;
        this.mFragment = fragment;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_upload_contract);

        spinnerCompany = findViewById(R.id.spinner_company);
        comapnyList = new ArrayList<>();
        CompanyParams params = new CompanyParams();
        params.setCreatorId(MyApplication.getUserInfo().getMaintStaffId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> strList = new ArrayList<String>();
                comapnyList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CompanyResult>>() {
                }.getType());
                for (CompanyResult result1 : comapnyList) {
                    strList.add(result1.getCompanyShortName());
                }
                companyAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, strList);
                spinnerCompany.setAdapter(companyAdapter);
            }
        });

        Button btnFile = findViewById(R.id.btn_file);
        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(mFragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(mFragment.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

                    }
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                mFragment.startActivityForResult(intent, EventKind.FILE_SELECT_CODE);
            }
        });
        editFile = findViewById(R.id.edit_filename);
        super.setContentView();
    }

    public void setPath(String path) {
        file = new File(path);
        editFile.setText(file.getName());
    }

    public void loading() {
        mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadContractParams params = new UploadContractParams();
                params.setCompanyId( comapnyList.get(spinnerCompany.getSelectedItemPosition()).getCompanyId());
                List<File> fileList = new ArrayList<>();
                params.setContractName(file.getName());
                fileList.add(file);
                HttpClientSend.getInstance().sendFile(params, fileList, new BaseAlertCallback("上传合同成功", "上传合同失败") {
                    @Override
                    public void onSuccessed(String result) {
                        ContractCheckFragment.contractCheckFragmentInstance.getContractData();
                        UploadContractDialog.this.dismiss();
                    }
                });
            }
        };
        this.show();
    }
}