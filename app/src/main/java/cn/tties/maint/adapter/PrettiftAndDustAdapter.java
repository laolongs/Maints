package cn.tties.maint.adapter;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.CommonListViewInterface;
import cn.tties.maint.enums.FaultType;
import cn.tties.maint.httpclient.result.PrettiftResult;

/**
 *
 * Created by lizhen on 2018/1/9.
 */

public class PrettiftAndDustAdapter extends BaseAdapter {
    List<PrettiftResult.ResultBean> list;
    public void setDataList(List<PrettiftResult.ResultBean> result1) {
        this.list=result1;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHodler hodler = null;
        PrettiftResult.ResultBean resultBean = list.get(i);
        if (view == null) {
            view = View.inflate(x.app(), R.layout.listview_prettiftanddust_item, null);
            hodler = new MyViewHodler();
            hodler.img_type = (ImageView) view.findViewById(R.id.img_type);
            hodler.text_title = (TextView) view.findViewById(R.id.text_title);
            view.setTag(hodler);
        } else {
            hodler = (MyViewHodler) view.getTag();
        }
        if(resultBean.getFaultType()== FaultType.FAULT.getType()){
            hodler.img_type.setImageResource(R.mipmap.img_patrol_general);
        }
        if(resultBean.getFaultType()==FaultType.DANGEROUS.getType()){
            hodler.img_type.setImageResource(R.mipmap.img_patrol_importcacel);
        }
        if(resultBean.getFaultType()==FaultType.NORLMAL.getType()){
            hodler.img_type.setImageResource(R.mipmap.img_patrol_ungency);
        }
        if (resultBean.getTitle()!=null){
            hodler.text_title.setText(resultBean.getTitle());
        }
        return view;
    }

    public class MyViewHodler {
        ImageView img_type;
        TextView text_title;
    }
}
