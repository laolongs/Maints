package cn.tties.maint.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.tties.maint.R;
import cn.tties.maint.activity.QuestionFragment;
import cn.tties.maint.bean.UserInfoBean;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.dao.EquipmentDao;
import cn.tties.maint.dao.PatrolItemDao;
import cn.tties.maint.entity.EquipmentEntity;
import cn.tties.maint.entity.PatrolItemEntity;
import cn.tties.maint.enums.OtherPatrolItemType;
import cn.tties.maint.enums.QuestionStatusType;
import cn.tties.maint.httpclient.result.AdviceResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.httpclient.result.DescriptionResult;
import cn.tties.maint.httpclient.result.QuertionResult;
import cn.tties.maint.util.AppUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.AdviceDialog;
import cn.tties.maint.view.ScheduleDialog;

/**
 * 问题Adapter.
 * Created by chensi on 2018/1/11.
 */

public class QuestionSearchAdapter extends BaseAdapter{
    List<CompanyResult> companyList;
    List<Integer> companyArrays=new ArrayList<>();
    public void setCompanyList(List<CompanyResult> companyList){
        this.companyList=companyList;
    }
    @Override
    public int getCount() {
        return companyList==null?0:companyList.size();
    }

    @Override
    public Object getItem(int i) {
        return companyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHodler hodler=null;
        if(view==null){
            view = View.inflate(x.app(), R.layout.listview_question_compaylist_item, null);
            hodler=new MyViewHodler();
            hodler.checkBox=(CheckBox) view.findViewById(R.id.question_check);
            view.setTag(hodler);
        }else{
            hodler=(MyViewHodler) view.getTag();
        }
        hodler.checkBox.setText(companyList.get(i).getCompanyShortName());
        hodler.checkBox.setId(companyList.get(i).getCompanyId());
        hodler.checkBox.setOnCheckedChangeListener(listener);
        return view;
    }
    public class MyViewHodler{
        CheckBox checkBox;
    }
    private CompoundButton.OnCheckedChangeListener listener = new  CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                // 添加已选中的复选框
                companyArrays.add(buttonView.getId());
            }else{
                companyArrays.remove(buttonView.getId());
            }

        }
    };
    public List<Integer> getcompanyArrays(){
       return companyArrays;
    }
}