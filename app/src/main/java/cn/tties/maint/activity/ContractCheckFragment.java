package cn.tties.maint.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.adapter.ContractCheckListViewAdapter;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.SelectContractListParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.ContractResult;
import cn.tties.maint.util.DownloadUtils;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.PinYinUtils;
import cn.tties.maint.view.UploadContractDialog;
import cn.tties.maint.widget.DrawableUtil;

import static android.app.Activity.RESULT_OK;

/**
 * 合同管理
 */
@ContentView(R.layout.fragment_contract_check)
public class ContractCheckFragment extends BaseFragment {

    private static final String TAG = "ContractCheckFragment";

    @ViewInject(R.id.searchView)
    private SearchView searchView;

    @ViewInject(R.id.text_name)
    private TextView textName;

    @ViewInject(R.id.btn_upload)
    private Button btnUpload;

    @ViewInject(R.id.btn_download)
    private Button btnDownload;

    @ViewInject(R.id.btn_refresh)
    private Button btnRefresh;

    @ViewInject(R.id.btn_view)
    private Button btnView;

    @ViewInject(R.id.cb_check)
    private CheckBox checkBox;

    ContractCheckListViewAdapter adapter;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    private List<ContractResult> contractList;
    private List<ContractResult> searchList;
    private List<String> pinYinList;
    private List<String> pinYinAllList;
    private UploadContractDialog uploadDialog;
    public static ContractCheckFragment contractCheckFragmentInstance;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getContractData();
        contractList = new ArrayList<>();
        contractCheckFragmentInstance = this;
    }

    private void initView() {
        textName.setText("合同管理文件夹 >");

        addDrawable(btnUpload, R.mipmap.ic_uploaded);
        addDrawable(btnDownload, R.mipmap.ic_downloaded);
        addDrawable(btnRefresh, R.mipmap.ic_refersh);
        addDrawable(btnView, R.mipmap.ic_look);

        adapter = new ContractCheckListViewAdapter(this.getContext(), new ArrayList<ContractResult>());
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 5));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked()) {
                    adapter.setCheckAll();
                } else {
                    adapter.clearCheckAll();
                }
                adapter.notifyDataSetChanged();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {//如果这个文字等于空
                    //清除listview的过滤
                    adapter.setMDataList(contractList);
                    adapter.notifyDataSetChanged();
                } else {
                    searchList = new ArrayList<ContractResult>();
                    // 过滤条件
                    String str = newText.toLowerCase();
                    // 循环变量数据源，如果有属性满足过滤条件，则添加到result中
                    for (int i = 0; i < contractList.size(); i++) {
                        ContractResult entity = contractList.get(i);
                        if (entity.getContractName().contains(str)) {
                            searchList.add(entity);
                        } else {
                            String pinyinSet = pinYinList.get(i);
                            if (pinyinSet.startsWith(str)) {
                                searchList.add(entity);
                                continue;
                            }
                            String pinyinAllSet = pinYinAllList.get(i);
                            if (pinyinAllSet.startsWith(str)) {
                                searchList.add(entity);
                            }
                        }
                    }
                    adapter.setMDataList(searchList);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    public void getContractData() {
        SelectContractListParams params = new SelectContractListParams();
        params.setStaffId(MyApplication.getUserInfo().getStaffId());
        HttpClientSend.getInstance().send(params, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                contractList.clear();
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    //错误
                } else {
                    contractList = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<ContractResult>>() {
                    }.getType());
                    adapter.setMDataList(contractList);
                    adapter.notifyDataSetChanged();
                    pinYinList = new ArrayList<String>();
                    pinYinAllList = new ArrayList<String>();
                    for (ContractResult contractResult : contractList) {
                        pinYinList.add(PinYinUtils.getPinYinHeadChar(contractResult.getContractName()));
                        pinYinAllList.add(PinYinUtils.getPinYin(contractResult.getContractName()));
                    }
                }
            }
        });
    }

    private void uploadPdf() {
        uploadDialog = new UploadContractDialog(this, MyApplication.getUserInfo().getStaffId());
        uploadDialog.loading();
    }

    private void downloadPdf() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            }
        }
            if (adapter.getCheck() == null) {
                Toast.makeText(x.app(), "请选择一份合同", Toast.LENGTH_SHORT).show();
                return;
            }
            String url = Constants.BASE_URL +   adapter.getCheck().getContractPath();
            url = url.replaceAll("\\\\","//" );
            downloadFile(url, adapter.getCheck().getContractName());
    }
    private void downloadFile(final String url, String title) {
        if (url != null) {
            Toast.makeText(x.app(), "合同:"+ title + " 已添加到下载", Toast.LENGTH_SHORT).show();
            DownloadUtils.getInstance().download(url, title, "");
            return;
        }
    }

    private void lookPdf() {
        if (adapter.getCheck() == null) {
            Toast.makeText(x.app(), "请选择一份合同", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = Constants.BASE_URL +   adapter.getCheck().getContractPath();
        url = url.replaceAll("\\\\","//" );
        Intent intentPreview = new Intent(this.getContext(), PdfActivity.class);
        intentPreview.putExtra("path", url);
        intentPreview.putExtra("name", adapter.getCheck().getContractName());
        startActivity(intentPreview);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EventKind.FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String path = getPath(ContractCheckFragment.this.getContext(), uri);
                    if (path == null) {
                        Toast.makeText(x.app(), "上传文件不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!path.endsWith(".pdf")) {
                        Toast.makeText(x.app(), "只能上传PDF文件", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    uploadDialog.setPath(path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                return null;
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    @Event(value = {R.id.btn_upload, R.id.btn_download, R.id.btn_refresh, R.id.btn_view})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                uploadPdf();
                break;
            case R.id.btn_download:
                downloadPdf();
                break;
            case R.id.btn_refresh:
                getContractData();
                break;
            case R.id.btn_view:
                lookPdf();
                break;
        }
    }

    private void addDrawable(Button button, int idNormal) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : getResources().getDrawable(idNormal);
        if (null != normal) {
            normal = DrawableUtil.zoomLeftDrawable(normal, 30, 30);
            normal.setBounds(0, 0, 0, 0);
        }
        stateListDrawable.addState(new int[]{}, normal);
        button.setCompoundDrawablesWithIntrinsicBounds(stateListDrawable, null, null, null);
    }
}
