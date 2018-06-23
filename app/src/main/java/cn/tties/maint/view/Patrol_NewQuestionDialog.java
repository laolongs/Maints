package cn.tties.maint.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.activity.BaseFragment;
import cn.tties.maint.activity.OrderListFragment;
import cn.tties.maint.activity.QuestionFragment;
import cn.tties.maint.adapter.SafetyImageListViewAdapter;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.enums.FaultType;
import cn.tties.maint.enums.RoleType;
import cn.tties.maint.enums.WorkType;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.Patrol_QuestionParams;
import cn.tties.maint.httpclient.params.SafetyParams;
import cn.tties.maint.httpclient.result.CompanyEquipmentResult;
import cn.tties.maint.httpclient.result.SafetyResult;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.util.StringUtil;


/**
 * 新建问题弹出框
 * author chensi
 */
public class Patrol_NewQuestionDialog{
    private static final String TAG = "Patrol_NewQuestionDialo";
    List<ImageItem> imageItemsList;
    SafetyImageListViewAdapter adapter;
    BaseFragment mFragment;
    int maxCount = 9;
    int mPostion;
    private Button btn_cancel;
    private Button btn_add;
    private EditText edit_describe;
    private EditText edit_temperature;
    private LinearLayout LL_address;
    private TextView  text_address;
    private Spinner level;
    boolean isTemperature;
    CompanyEquipmentResult result;
    int orderworkid;
    int cureleid;
    AlertDialog dialog;
    Integer faultType;
    int numFlag=0;//0 代表新建问题， 1 代表添加问题，2 代表有温度的问题
    public Patrol_NewQuestionDialog(BaseFragment fragment, CompanyEquipmentResult result, int orderworkid, int cureleid,int numFlag) {
        adapter = new SafetyImageListViewAdapter(fragment.getContext(), new ArrayList<ImageItem>(), maxCount);
        adapter.setOnItemClickListener(clickListener);
        this.mFragment = fragment;
        this.result=result;
        imageItemsList = new ArrayList<>();
        this.orderworkid=orderworkid;
        this.cureleid=cureleid;
        this.numFlag=numFlag;
    }
    public void setData(boolean isTemperature){
        this.isTemperature=isTemperature;
    }
    public void setContentView() {
        // 指定布局
        AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getContext());
        View view = View
                .inflate(mFragment.getContext(), R.layout.dialog_patrol_newquestion, null);
        builder.setView(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        edit_describe = (EditText)  view.findViewById(R.id.edit_describe);
        level = (Spinner)  view.findViewById(R.id.level);
        edit_temperature = (EditText) view.findViewById(R.id.edit_temperature);
        LL_address = (LinearLayout) view.findViewById(R.id.LL_address);
        text_address = (TextView) view.findViewById(R.id.text_address);
        btn_cancel = (Button)  view.findViewById(R.id.btn_cancel);
        btn_add = (Button)  view.findViewById(R.id.btn_add);
//        text_address.setText(result.get);
        dialog = builder.create();
        recyclerView.setLayoutManager(new GridLayoutManager(mFragment.getContext(), 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        getSpinnerData(level);
        if(isTemperature){
            edit_temperature.setVisibility(View.VISIBLE);
        }else{
            edit_temperature.setVisibility(View.GONE);
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                //新建问题
                if(numFlag==0){
                    sendNewQuestion();
                }
                //有问题
                if(numFlag==1){

                }
                //有温度问题
                if(numFlag==2){

                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                dialog.dismiss();
            }
        });
    }
    public void setImages(List<ImageItem> list) {
        for (ImageItem oldImageItem : imageItemsList) {
            boolean isExsist = false;
            int index = 0;
            for (ImageItem newImageItem : list) {
                if (newImageItem.path.equals(oldImageItem.path)) {
                    isExsist = true;
                    break;
                }
                index++;
            }
            if (isExsist) {
                list.remove(index);
            }
        }
        if (list.size() == 0) {
            Toast.makeText(x.app(),"图片已选择",Toast.LENGTH_SHORT).show();
        }
        imageItemsList.addAll(list);
        adapter.setImages(imageItemsList);
    }

    SafetyImageListViewAdapter.OnRecyclerViewItemClickListener clickListener = new SafetyImageListViewAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {
            mPostion = position;
            if (position > imageItemsList.size() - 1) {
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxCount);
                Intent intent1 = new Intent(mFragment.getActivity(), ImageGridActivity.class);
                mFragment.startActivityForResult(intent1, EventKind.REQUEST_CODE_SELECT);
            } else {
                Intent intentPreview = new Intent(mFragment.getContext(), ImagePreviewDelActivity.class);
                ImageItem item = new ImageItem();
                item.path = imageItemsList.get(position).path;
                List<ImageItem> list = new ArrayList<>();
                list.add(item);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) list);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                mFragment.startActivityForResult(intentPreview, EventKind.REQUEST_CODE_PREVIEW);
            }
        }
    };
    public void deleteImage() {
        imageItemsList.remove(mPostion);
        adapter.setImages(imageItemsList);
        adapter.notifyDataSetChanged();
    }
    public void clearImage() {
        imageItemsList.clear();
        adapter.notifyDataSetChanged();
    }
    public void loading() {
        dialog.show();
    }
    private void getSpinnerData(Spinner le) {
        List<String> leveltype = new ArrayList<>();
        leveltype.add("缺陷类型");
        leveltype.add(FaultType.FAULT.getInfo());
        leveltype.add(FaultType.DANGEROUS.getInfo());
        leveltype.add(FaultType.NORLMAL.getInfo());
        ArrayAdapter<String> orderTypeAdapter = new ArrayAdapter<String>(x.app(),R.layout.spinner_text, leveltype);
        orderTypeAdapter.setDropDownViewResource(R.layout.spinner_text);
        le.setAdapter(orderTypeAdapter);
        le.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    faultType=null;
                }else{
                    faultType=FaultType.get(i);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void sendNewQuestion(){
        String title = edit_describe.getText().toString();
        int count = imageItemsList == null ? 0 : imageItemsList.size();
        if (StringUtil.isEmpty(title) && count == 0) {
            Toast.makeText(x.app(), "请输入描述或至少上传一张图片", Toast.LENGTH_SHORT).show();
            return;
        }

        Patrol_QuestionParams params=new Patrol_QuestionParams();
        params.setEleAccountId(cureleid);
        params.setHasquestion(true);
        //新建问题无巡视项ID
//        params.setPatrolItemId();
        params.setCompanyEquipmentId(result.getCompanyEquipmentId());
        params.setValue1("");
        params.setWorkOrderId(orderworkid);
        params.setRoomId(result.getRoomId());
        params.setStaffId(MyApplication.getUserInfo().getStaffId());
        params.setContent(title);
        params.setQuestionFaultType(faultType);
        List<File> fileList = new ArrayList<>();
        for (ImageItem imageItem : imageItemsList) {
            if (imageItem != null) {
                File file = new File(imageItem.path);
                fileList.add(file);
            }
        }
        Log.i(TAG, "sendNewQuestion: "+params.toString());
        HttpClientSend.getInstance().sendFile(params, fileList,new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                SafetyResult ret = JsonUtils.deserialize(result, SafetyResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ret.getResult().isFlag()){
                    Toast.makeText(x.app(), "新建问题成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(x.app(), "新建问题失败", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}