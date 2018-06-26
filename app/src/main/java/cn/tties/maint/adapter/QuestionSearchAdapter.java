package cn.tties.maint.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.tties.maint.R;
import cn.tties.maint.httpclient.result.CompanyResult;

/**
 * 问题Adapter.
 * Created by chensi on 2018/1/11.
 */

public class QuestionSearchAdapter extends BaseAdapter{
    List<CompanyResult> companyList;
    Set<Integer> companyIds =new HashSet<>();
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
                companyIds.add(buttonView.getId());
            }else{
                companyIds.remove(buttonView.getId());
            }

        }
    };
    public List<Integer> getcompanyArrays(){
       return new ArrayList<>(companyIds);
    }
}